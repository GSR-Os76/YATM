package com.gsr.gsr_yatm.block.device;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import oshi.util.tuples.Pair;

public interface IDeviceBlockEntity extends ITickingBlockEntity, IInventoryDroppingBlockEntity
{
	public static @NotNull Pair<Integer, Integer> deviceHeatEquation(@NotNegative int self, @NotNegative int other)
	{
		return IHeatHandler.levelTemperatures(self, self <= other ? other : Math.max(other, self - Math.max(DeviceBlockEntity.MINIMUM_CHANGE_PER_AMBIENT_COOLING, ((int)((self - other) * DeviceBlockEntity.AMBIENT_COOLING_FACTOR)))));
	} // end deviceHeatEquation()

} // end interface