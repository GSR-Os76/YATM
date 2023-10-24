package com.gsr.gsr_yatm.utilities.capability.heat;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import oshi.util.tuples.Pair;

public class HeatHandler implements IHeatHandler
{
	private @NotNegative int m_temperature;
	
	
	
	public HeatHandler(@NotNegative int temperature) 
	{
		this.m_temperature = Contract.NotNegative(temperature);
	} // end constructor()
	
	
	
	@Override
	public @NotNegative int heat(@NotNegative int temperature)
	{
		Pair<Integer, Integer> temps = IHeatHandler.levelTemperatures(this.m_temperature, temperature);
		this.m_temperature = temps.getA();
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
	} // end setTemperature()
	
} // end class