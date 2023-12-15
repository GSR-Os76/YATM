package com.gsr.gsr_yatm.block.device.behaviors.implementation.component;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IComponent;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.IInventoryChangeListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class InputComponentManager<C> implements ICapabilityProvider, IInventoryChangeListenerBehavior, ISerializableBehavior
{
	private final @NotNull IItemHandler m_inventory;
	private final @NotNegative int m_slot;
	private final @NotNull Capability<C> m_capability;
	private final @NotNull C m_inwardAttachment;
	
	private @Nullable IComponent m_component = null;
	private @Nullable LazyOptional<C> m_componentAttachment = null;
	
	
	
	public InputComponentManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull C inwardAttachment, @NotNull Capability<C> capability) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_slot = Contract.notNegative(slot);
		this.m_inwardAttachment = Objects.requireNonNull(inwardAttachment);
		this.m_capability = Objects.requireNonNull(capability);
	} // end constructor
	
	
	
	@Override
	public @NotNull List<@NotNegative Integer> getSlotIndices()
	{
		return List.of(this.m_slot);
	} // end getSlotIndex()
	
	@Override
	public void onSlotChanged(@NotNegative int slot)
	{
		this.updateComponent();
	} // end onChange()
	
	
	
	public void updateComponent() 
	{
		if(this.m_component != null) 
		{
			this.invalidateCaps();
			this.m_component = null;
		}
		
		ItemStack held = this.m_inventory.getStackInSlot(this.m_slot);
		IComponent hc = held.getCapability(YATMCapabilities.COMPONENT).orElse(null);
		if(hc != null && hc.getValidCapabilities().contains(this.m_capability))
		{
			this.m_component = hc;
			this.m_componentAttachment = LazyOptional.of(() -> this.m_inwardAttachment);
			this.m_component.attachRecievingCapability(this.m_capability, this.m_componentAttachment);
		}
	} // end updateComponent()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(this.m_component != null) 
		{
			LazyOptional<T> c = this.m_inventory.getStackInSlot(this.m_slot).getCapability(cap);
			if(c.isPresent()) 
			{
				return c;
			}
		}
		return LazyOptional.empty();
	} // end getCapability()
	
	public void invalidateCaps()
	{
		if(this.m_componentAttachment != null) 
		{
			this.m_componentAttachment.invalidate();
		}
	} // end invalidateCaps()



	@Override
	public @Nullable CompoundTag serializeNBT()
	{
		return null;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(@NotNull CompoundTag nbt)
	{
		this.updateComponent();
	}
	
} // end class