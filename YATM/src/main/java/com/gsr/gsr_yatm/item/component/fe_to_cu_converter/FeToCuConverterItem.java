package com.gsr.gsr_yatm.item.component.fe_to_cu_converter;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class FeToCuConverterItem extends Item
{
	private final @NotNull Supplier<Float> m_cuToFeRatio;
	
	
	
	public FeToCuConverterItem(@NotNull Properties properties, @NotNull Supplier<Float> cuToFeRatio)
	{
		super(Objects.requireNonNull(properties));
		this.m_cuToFeRatio = Objects.requireNonNull(cuToFeRatio);
	} // end constructor
	
	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new FeToCuConverterItemStack(stack, this.m_cuToFeRatio.get());
	} // end initCapabilities()
	
} // end class