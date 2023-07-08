package com.gsr.gsr_yatm.block.device;

public record DeviceTierConstants(int maxTemperature, int tankCapacity, int maxFluidTransferRate, int currentCapacity, int maxSafeCurrentTransfer, int maxCurrentTransfer)
{
	public static final int STEEL_MAXIMUM_TEMPERATURE = 4000;
	public static final int STEEL_TANK_CAPACITY = 4000;
	public static final int STEEL_MAXIMUM_FLUID_TRANSFER_RATE = 16;
	public static final int STEEL_CURRENT_CAPACITY = 8192;
	public static final int STEEL_MAX_CURRENT = 64;
	
	public static final DeviceTierConstants STEEL_DEVICE = new DeviceTierConstants(4000, 4000, 16, 8192, 64, 512);
	
	public static final DeviceTierConstants TIER_ONE_GENERATOR = new DeviceTierConstants(0, 0, 0, 1024, 8, 64);
	public static final DeviceTierConstants TIER_TWO_GENERATOR = new DeviceTierConstants(0, 0, 0, 65536, 64, 512);
	public static final DeviceTierConstants TIER_THREE_GENERATOR = new DeviceTierConstants(0, 0, 0, 524288, 512, 4096);
	

} // end constructor