package com.gsr.gsr_yatm.utilities.capability.heat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import oshi.util.tuples.Pair;

public class OnChangedHeatHandler implements IHeatHandler, INBTSerializable<CompoundTag>
{
	
	private static final String TEMPERATURE_TAG_NAME = "temp";
	
	private final @NotNull BiFunction<Integer, Integer, Pair<Integer, Integer>> m_heatEquation;
	private final @NotNull Consumer<Integer> m_onChanged;
	private final @NotNegative int m_maxTemperature;
	
	private @NotNegative int m_temperature;
	
	
	
	public OnChangedHeatHandler(@NotNegative int temperature, @NotNull Consumer<Integer> onChanged) 
	{
		this(Contract.notNegative(temperature), Objects.requireNonNull(onChanged), IHeatHandler::levelTemperatures, Integer.MAX_VALUE);
	} // end constructor()
	
	public OnChangedHeatHandler(@NotNegative int temperature, @NotNull Consumer<Integer> onChanged, @NotNull BiFunction<Integer, Integer, Pair<Integer, Integer>> heatEquation, @NotNegative int maxTemperature) 
	{
		this.m_temperature = Contract.notNegative(temperature);
		this.m_onChanged = Objects.requireNonNull(onChanged);
		this.m_heatEquation = Objects.requireNonNull(heatEquation);
		this.m_maxTemperature = Contract.notNegative(maxTemperature);
	} // end constructor()
	
	
	
	public @NotNegative int maxTemperature() 
	{
		return this.m_maxTemperature;
	} // end maxTemperature()
	
	
	
	@Override
	public @NotNegative int heat(@NotNegative int temperature)
	{
		Pair<Integer, Integer> temps = this.m_heatEquation.apply(this.m_temperature, temperature);
		this.setTemperature(temps.getA());
		return temps.getB();
	} // end heat()

	@Override
	public @NotNegative int getTemperature()
	{
		return this.m_temperature;
	} // end getTemperature()

	@Override
	public void setTemperature(@NotNegative int temperature)
	{
		// TODO, canidate for configurable overload exploding
		this.m_temperature = Math.min(this.m_maxTemperature, Contract.notNegative(temperature));
		this.m_onChanged.accept(this.m_temperature);
	} // end setTemperature()

	
	
	
	
	@Override
	public @Nullable CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(OnChangedHeatHandler.TEMPERATURE_TAG_NAME, this.m_temperature);
		return tag;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(@NotNull CompoundTag nbt)
	{
		this.setTemperature(nbt.getInt(OnChangedHeatHandler.TEMPERATURE_TAG_NAME));
	} // end deserializeNBT()
	
} // end class