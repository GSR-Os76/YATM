package com.gsr.gsr_yatm.api.capability;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import oshi.util.tuples.Pair;

@AutoRegisterCapability
public interface IHeatHandler
{
	// should not ever be greater then 1 or less than 0
	public static final float LEVELING_FACTOR = .3f;
	
	
	
	/** Returns the temperature value given in with any change that occurred. */
	public @NotNegative int heat(@NotNegative int temperature);
	
	
	/** Returns the heat handlers temperature.*/
	public @NotNegative int getTemperature();
	
	/** Sets the heat handlers temperature.*/
	public void setTemperature(@NotNegative int temperature);
	
	
	
	public static @NotNegative int getAmbientTemp()
	{
		return 298;
	} // end getAmbientTemp()
	
	public static @NotNull Pair<Integer, Integer> levelTemperatures(@NotNegative int temperatureA, @NotNegative int temperatureB)
	{
		Contract.notNegative(temperatureA);
		Contract.notNegative(temperatureB);
		if(temperatureA == temperatureB) 
		{
			return new Pair<>(temperatureA, temperatureB);
		}
		
		int difference = Math.abs(temperatureA - temperatureB);
		boolean aHigher = temperatureA > temperatureB;

		int lf = (int)Math.ceil(IHeatHandler.LEVELING_FACTOR * difference);			
		temperatureA += ((lf / 2) + ((aHigher && lf % 2 == 1) ? 0 : 1)) * (aHigher ? -1 : 1);
		temperatureB += ((lf / 2) + ((aHigher && lf % 2 == 1) ? 1 : 0)) * (aHigher ? 1 : -1);		
			
		return new Pair<>(temperatureA, temperatureB);
	} // end levelTemperatures()

} // end interface