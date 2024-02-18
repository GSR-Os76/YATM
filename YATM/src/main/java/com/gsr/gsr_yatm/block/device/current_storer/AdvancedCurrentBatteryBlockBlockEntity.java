package com.gsr.gsr_yatm.block.device.current_storer;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedCurrentBatteryBlockBlockEntity extends CurrentStorerBlockEntity
{

	public AdvancedCurrentBatteryBlockBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.ADVANCED_CURRENT_BATTERY_BLOCK.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	
	
	@Override
	protected @NotNegative int getCurrentCapacity()
	{
		return YATMConfigs.ADVANCED_CURRENT_BATTERY_BLOCK_CURRENT_CAPACITY.get();
	} // end getCurrentCapacity()

	@Override
	protected @NotNegative int getMaxCurrentTransfer()
	{
		return YATMConfigs.ADVANCED_CURRENT_BATTERY_BLOCK_MAX_CURRENT_TRANSFER.get();
	} // end getMaxCurrentTransfer()

	@Override
	protected @NotNegative int getOutputRecheckPeriod()
	{
		return YATMConfigs.ADVANCED_CURRENT_BATTERY_BLOCK_DRAIN_CURRENT_RECHECK_PERIOD.get();
	} // end getOutputRecheckPeriod()

} // end class