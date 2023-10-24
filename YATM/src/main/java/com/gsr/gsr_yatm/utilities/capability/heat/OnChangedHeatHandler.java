package com.gsr.gsr_yatm.utilities.capability.heat;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import oshi.util.tuples.Pair;

public class OnChangedHeatHandler implements IHeatHandler
{
	private @NotNegative int m_temperature;
	private @NotNull Consumer<Integer> m_onChanged;
	
	
	
	public OnChangedHeatHandler(@NotNegative int temperature, @NotNull Consumer<Integer> onChanged) 
	{
		this.m_temperature = Contract.NotNegative(temperature);
		this.m_onChanged = Objects.requireNonNull(onChanged);
	} // end constructor()
	
	
	
	@Override
	public @NotNegative int heat(@NotNegative int temperature)
	{
		Pair<Integer, Integer> temps = IHeatHandler.levelTemperatures(this.m_temperature, temperature);
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
		this.m_temperature = Contract.NotNegative(temperature);
		this.m_onChanged.accept(this.m_temperature);
	} // end setTemperature()
	
} // end class