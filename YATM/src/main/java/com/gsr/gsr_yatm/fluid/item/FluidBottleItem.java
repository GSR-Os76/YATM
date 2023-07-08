package com.gsr.gsr_yatm.fluid.item;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class FluidBottleItem extends Item
{
	public static final int BOTTLE_CAPACITY = 333;
	private final Supplier<? extends Fluid> m_fluid;
	
	
	
	public FluidBottleItem(Properties properties, Supplier<? extends Fluid> fluid)
	{
		super(properties);
		this.m_fluid = fluid;
	} // end constructor



	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new FluidBottleItemStack(this.m_fluid.get(), stack);
	} // end initCapabilities()
	
} // end class