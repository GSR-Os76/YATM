package com.gsr.gsr_yatm.block.device;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

@Deprecated(forRemoval=true)
public record DeviceTierConstants(@NotNegative int maxTemperature, @NotNegative int tankCapacity, @NotNegative int maxFluidTransferRate, @NotNegative int currentCapacity, @NotNegative int maxSafeCurrentTransfer, @NotNegative int maxCurrentTransfer)
{
	public static final DeviceTierConstants EMPTY = new DeviceTierConstants(0, 0, 0, 0, 0, 0);
	// TODO, maybe lower temp to 2000K, when it;s appropriate
	public static final DeviceTierConstants STEEL_DEVICE = new DeviceTierConstants(4000, 4000, 16, 8192, 64, 512);
	public static final DeviceTierConstants STEEL_TANK = new DeviceTierConstants(4000, 16000, 1000, 0, 0, 0);
	
	public static final int STEEL_MAXIMUM_TEMPERATURE = DeviceTierConstants.STEEL_DEVICE.maxTemperature();
	public static final int STEEL_TANK_CAPACITY = DeviceTierConstants.STEEL_DEVICE.tankCapacity();
	public static final int STEEL_MAXIMUM_FLUID_TRANSFER_RATE = DeviceTierConstants.STEEL_DEVICE.maxFluidTransferRate();
	public static final int STEEL_CURRENT_CAPACITY = DeviceTierConstants.STEEL_DEVICE.currentCapacity();
	public static final int STEEL_MAX_CURRENT_TRANSFER = DeviceTierConstants.STEEL_DEVICE.maxCurrentTransfer;
	
	public static final DeviceTierConstants TIER_ONE_GENERATOR = new DeviceTierConstants(0, 0, 0, 1024, 8, 64);
	public static final DeviceTierConstants TIER_TWO_GENERATOR = new DeviceTierConstants(0, 0, 0, 65536, 64, 512);
	public static final DeviceTierConstants TIER_THREE_GENERATOR = new DeviceTierConstants(0, 0, 0, 524288, 512, 4096);

} // end constructor