package com.gsr.gsr_yatm.block.device.behaviors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.item.component.IComponent;
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

public class OutputComponentManager implements ICapabilityProvider
{
	private final @NotNull IItemHandler m_inventory;
	private final @NotNegative int m_slot;
	private final @NotNull Supplier<@NotNull List<@NotNull Direction>> m_attachmentDirections;
	private final @NotNegative int m_recheckPeriod;
	
	private @NotNegative int m_recheckCounter = 0;
	
	private @Nullable ItemStack m_componentStack = null;
	private @Nullable IComponent m_component = null;
	private @NotNull List<LazyOptional<?>> m_attachments = new ArrayList<>();
	private @NotNull Map<Capability<?>, LazyOptional<?>> m_sterileCaps = new HashMap<>();
	
	
	
	public OutputComponentManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull Supplier<@NotNull List<@NotNull Direction>> attachmentDirections, @NotNegative int recheckPeriod) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_slot = Contract.notNegative(slot);
		this.m_attachmentDirections = Objects.requireNonNull(attachmentDirections);
		this.m_recheckPeriod = Contract.notNegative(recheckPeriod);
	} // end constructor
	
	
	
	public void updateComponent() 
	{
		if(this.m_component != null) 
		{
			this.removeSterileCaps();
			this.removeAttachments();
			this.m_component = null;
			this.m_componentStack = null;
		}
		ItemStack drainResultStack = this.m_inventory.getStackInSlot(this.m_slot);
		
		if(drainResultStack.getItem() instanceof IComponent fc) 
		{
			this.m_component = fc;
			this.m_componentStack = drainResultStack;
		}
	} // end updateComponent()
	
	
	
	public void tick(@NotNull Level level, @NotNull BlockPos position) 
	{
		if(++this.m_recheckCounter >= YATMConfigs.CRUCIBLE_DRAIN_RECHECK_PERIOD.get()) 
		{
			this.m_attachmentDirections.get().forEach((d) ->  this.tryAttach(level, position, d));
		}
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
			this.m_component.attachRecievingCapability(this.m_componentStack, cap, l);
			this.m_attachments.add(l);
			l.addListener(this::removeInvalidatedAttachment);
		}
	} // end captureAttachment()


	
	protected void removeAttachments()
	{
		for(LazyOptional<?> l : this.m_attachments) 
		{
			this.m_component.removeRecievingCapability(this.m_componentStack, l);
		}
		this.m_attachments = new ArrayList<>();
	} // end removeDrainResultAttachments()
	
	private void removeInvalidatedAttachment(LazyOptional<?> lazyOptional)
	{
		if(this.m_attachments.contains(lazyOptional)) 
		{
			this.m_attachments.remove(lazyOptional);
			this.m_component.removeRecievingCapability(this.m_componentStack, lazyOptional);
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