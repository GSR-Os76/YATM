package com.gsr.gsr_yatm.api.capability;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface ICurrentHandler
{
	// function
	// recieve in current, actually, or see what would happen.
	// returns amount recieved
	public int recieveCurrent(@NotNegative int amount, boolean simulate);
	
	// function
	// from implementers perspective
	// returns amount extracted
	public int extractCurrent(@NotNegative int amount, boolean simulate);
	
	// info
	// the maximum amount that's containable
	public int capacity();
	
	// info
	// the amount currently being contained
	public int stored();

} // end interface