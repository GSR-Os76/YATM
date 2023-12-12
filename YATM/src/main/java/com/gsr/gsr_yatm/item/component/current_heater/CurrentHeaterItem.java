package com.gsr.gsr_yatm.item.component.current_heater;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CurrentHeaterItem extends Item
{
	private final @NotNull Supplier<@NotNegative Integer> m_maxTemperature; 
	private final @NotNull Supplier<Float> m_kelvinPerCurrent;
	
	
	
	public CurrentHeaterItem(@NotNull Properties properties, @NotNull Supplier<@NotNegative Integer> maxTemperature, @NotNull Supplier<Float> kelvinPerCurrent)
	{
		super(Objects.requireNonNull(properties));
		this.m_maxTemperature = Objects.requireNonNull(maxTemperature);
		this.m_kelvinPerCurrent = Objects.requireNonNull(kelvinPerCurrent);
	} // end constructor
	
	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new CurrentHeaterItemStack(Contract.notNegative(this.m_maxTemperature.get()), this.m_kelvinPerCurrent.get());
	} // end initCapabilities()

} // end class