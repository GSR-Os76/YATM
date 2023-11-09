package com.gsr.gsr_yatm.api.capability;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface ICurrentHandler
{
	// function
	// recieve in current, actually, or see what would happen.
	// probably returns accepted
	public int recieveCurrent(@NotNegative int amount, boolean simulate);
	
	// function
	// from implementers perspective
	public int extractCurrent(@NotNegative int amount, boolean simulate);
	
	// info
	// the maximum amount that's containable
	public int capacity();
	
	// info
	// the amount currently being contained
	public int stored();

} // end interface