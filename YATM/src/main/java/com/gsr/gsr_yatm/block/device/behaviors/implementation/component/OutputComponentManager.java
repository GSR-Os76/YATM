package com.gsr.gsr_yatm.block.device.behaviors.implementation.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IComponent;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.IInventoryChangeListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ILoadListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class OutputComponentManager implements ICapabilityProvider, IInventoryChangeListenerBehavior, ILoadListenerBehavior, ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(IInventoryChangeListenerBehavior.class, ILoadListenerBehavior.class, ITickableBehavior.class);
	} // end behaviorTypes()
	
	private final @NotNull IItemHandler m_inventory;
	private final @NotNegative int m_slot;
	private final @NotNull Supplier<@NotNull List<@NotNull Direction>> m_attachmentDirections;
	private final @NotNegative int m_recheckPeriod;
	
	private @NotNegative int m_recheckCounter = 0;
	
	private @Nullable ItemStack m_componentStack = null;
	private @Nullable IComponent m_component = null;
	private @NotNull List<LazyOptional<?>> m_attachments = new ArrayList<>();
	private @NotNull Map<Capability<?>, LazyOptional<?>> m_sterileCaps = new HashMap<>();
	private boolean m_needsUpdate = false;
	
	
	public OutputComponentManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull Supplier<@NotNull List<@NotNull Direction>> attachmentDirections, @NotNegative int recheckPeriod) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_slot = Contract.notNegative(slot);
		this.m_attachmentDirections = Objects.requireNonNull(attachmentDirections);
		this.m_recheckPeriod = Contract.notNegative(recheckPeriod);
	} // end constructor
	
	
	
	@Override
	public void onLoad()
	{
		this.updateComponent();
	} // end onLoad()
	
	@Override
	public @NotNull List<@NotNegative Integer> getSlotIndices()
	{
		return List.of(this.m_slot);
	} // end getSlotIndex()
	
	@Override
	public void onSlotChanged(@NotNegative int slot)
	{
		this.m_needsUpdate = true;
	} // end onChange()
	
	
	public void updateComponent() 
	{
		if(this.m_component != null) 
		{
			this.removeSterileCaps();
			this.removeAttachments();
			this.m_component = null;
			this.m_componentStack = null;
		}
		ItemStack held = this.m_inventory.getStackInSlot(this.m_slot);
		IComponent fc = held.getCapability(YATMCapabilities.COMPONENT).orElse(null);
		if(fc != null) 
		{
			this.m_component = fc;
			this.m_componentStack = held;
		}
	} // end updateComponent()
	
	
	
	@Override
	public boolean tick(@NotNull Level level, @NotNull BlockPos position) 
	{
		if(this.m_needsUpdate) 
		{
			this.updateComponent();
		}
		
		if(++this.m_recheckCounter >= this.m_recheckPeriod) 
		{
			this.m_attachmentDirections.get().forEach((d) ->  this.tryAttach(level, position, d));
		}
		return false;
	} // end tick()
	
	
	
	public void tryAttach(@NotNull Level level, @NotNull BlockPos position, @NotNull Direction neighbor) 
	{
		BlockEntity be = level.getBlockEntity(position.relative(neighbor));
		if(this.m_component == null || be == null) 
		{
			return;
		}
		
		for(Capability<?> cap : this.m_component.getValidCapabilities()) 
		{
			this.captureAttachment(be, cap, neighbor.getOpposite());
		}
	} // end tryAttach()
	
	private <T> void captureAttachment(@NotNull BlockEntity be, @NotNull Capability<T> cap, @NotNull Direction face) 
	{
		LazyOptional<T> l = be.getCapability(cap, face);
		if(l.isPresent() && !this.m_attachments.contains(l)) 
		{
			this.m_component.attachRecievingCapability(cap, l);
			this.m_attachments.add(l);
			l.addListener(this::removeInvalidatedAttachment);
		}
	} // end captureAttachment()


	
	protected void removeAttachments()
	{
		for(LazyOptional<?> l : this.m_attachments) 
		{
			this.m_component.removeRecievingCapability(l);
		}
		this.m_attachments = new ArrayList<>();
	} // end removeDrainResultAttachments()
	
	private void removeInvalidatedAttachment(LazyOptional<?> lazyOptional)
	{
		if(this.m_attachments.contains(lazyOptional)) 
		{
			this.m_attachments.remove(lazyOptional);
			this.m_component.removeRecievingCapability(lazyOptional);
		}
	} // removeInvalidatedAttachment()
	
	private void removeSterileCaps() 
	{
		this.m_sterileCaps.values().forEach((c) -> c.invalidate());
		this.m_sterileCaps = new HashMap<>();		
	} // end removeSterileCaps()
	
	

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(this.m_component != null && this.m_component.getValidCapabilities().contains(cap)) 
		{
			return this.m_sterileCaps.computeIfAbsent(cap, SlotUtil::createSterileCapability).cast();
		}
		return LazyOptional.empty();
	} // end getCapability()
	
	public void invalidateCaps() 
	{
		this.removeSterileCaps();
		this.removeAttachments();
	} // end invalidateCaps()

} // end class