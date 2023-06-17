package com.gsr.gsr_yatm.creative;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class CreativeFluidStorerItem extends Item
{

	public CreativeFluidStorerItem(Properties properties)
	{
		super(properties);
	} // end constructor
	
	

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		if(this.getClass() == CreativeFluidStorerItem.class) 
		{
			return new FluidHandlerItemStack(stack, Integer.MAX_VALUE);
		}
		return super.initCapabilities(stack, nbt);
	} // end initCapabilities()

} // end class