package com.gsr.gsr_yatm.item.component.fluid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.IComponent;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class FluidExchangerItem extends Item implements IComponent
{
	private final int m_maxTransferRate;
	
	
	
	public FluidExchangerItem(Properties p_41383_, int maxTransferRate)
	{
		super(p_41383_);
		this.m_maxTransferRate = maxTransferRate;
	} // end constructor



	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		if(this.getClass() == FluidExchangerItem.class) 
		{
			return new FluidHandlerItemStack(stack, this.m_maxTransferRate) {

				@Override
				public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing)
				{
					if(capability == ForgeCapabilities.FLUID_HANDLER) 
					{
						return super.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, facing).cast();
					}
					return super.getCapability(capability, facing);
				} // end getCapability
				
			};
		}		
		return super.initCapabilities(stack, nbt);		
	} // end initCapabilities()

} // end class