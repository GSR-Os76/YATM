package com.gsr.gsr_yatm.block.device.compute.scan_collector;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.BasicBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class DataScanCollectorBlockEntity extends BasicBlockEntity
{

	public DataScanCollectorBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(YATMBlockEntityTypes.DATA_SCAN_COLLECTOR.get(), blockPos, blockState);
	} // end constructor

	@Override
	protected @NotNull CompoundTag setupToNBT()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setupFromNBT(@NotNull CompoundTag tag)
	{
		// TODO Auto-generated method stub
		
	}

} // end class