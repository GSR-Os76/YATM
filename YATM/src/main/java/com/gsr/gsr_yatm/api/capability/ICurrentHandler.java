package com.gsr.gsr_yatm.api.capability;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface ICurrentHandler
{
	// receive in current, actually, or see what would happen.
	// returns amount received
	public @NotNegative int receiveCurrent(@NotNegative int amount, boolean simulate);
	
	// from implementers perspective
	// returns amount extracted
	public @NotNegative int extractCurrent(@NotNegative int amount, boolean simulate);
	
	// the maximum amount that's containable
	public @NotNegative int capacity();
	
	// the amount currently being contained
	public @NotNegative int stored();

} // end interface