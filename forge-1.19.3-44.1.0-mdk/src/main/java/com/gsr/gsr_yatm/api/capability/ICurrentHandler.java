package com.gsr.gsr_yatm.api.capability;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface ICurrentHandler
{
	// function
	// recieve in current, actually, or see what would happen.
	public int recieveCurrent(int amount, boolean simulate);
	
	// function
	// from implementers perspective
	public int extractCurrent(int amount, boolean simulate);
	
	// info
	// the maximum amount that's containable
	public int storageCapacity();
	
	// info
	// the amount currently being contained
	public int storedCapacity();

	// info
	// gets the total number of virtual ICurrentHandlers in the given device
//	public int getVirtualDeviceCount();
	
	// info
	// gets which virtual device a given face connects too
//	public int getVirtualDeviceId(boolean simulate);

	
	// add virtual devices
} // end interface