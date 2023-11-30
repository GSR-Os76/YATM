package com.gsr.gsr_yatm.api.capability;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface ICurrentHandler
{
	// recieve in current, actually, or see what would happen.
	// returns amount recieved
	public @NotNegative int recieveCurrent(@NotNegative int amount, boolean simulate);
	
	// from implementers perspective
	// returns amount extracted
	public @NotNegative int extractCurrent(@NotNegative int amount, boolean simulate);
	
	// the maximum amount that's containable
	public @NotNegative int capacity();
	
	// the amount currently being contained
	public @NotNegative int stored();

} // end interface