package com.gsr.gsr_yatm.creative;

import org.jetbrains.annotations.Nullable;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class CreativeFluidStorerItem extends Item
{

	public CreativeFluidStorerItem(Properties properties)
	{
		super(properties);
	} // end constructor
	
	
	
	@Override
	public Component getName(ItemStack itemStack)
	{
		LazyOptional<IFluidHandlerItem> fh = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
		IFluidHandlerItem c = fh.orElse(null);
		FluidStack toNameTake = c == null ? FluidStack.EMPTY : c.getFluidInTank(0);
		
		MutableComponent name = Component.translatable(getDescriptionId(itemStack));
		if(toNameTake != null && toNameTake != FluidStack.EMPTY) 
		{
			name.append(Component.literal(" ("));
			// Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this)
			name.append(Component.translatable(Util.makeDescriptionId("fluid", ForgeRegistries.FLUIDS.getKey(toNameTake.getFluid()))));//ForgeRegistries.FLUIDS.getKey(toNameTake).toString()));
			name.append(Component.literal(": " + toNameTake.getAmount() +"/2^³¹-1mbs)"));
		}
		
		return name;
	} // end getName()

	
	
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