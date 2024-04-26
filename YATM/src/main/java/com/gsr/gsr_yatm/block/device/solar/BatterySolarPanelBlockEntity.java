package com.gsr.gsr_yatm.block.device.solar;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class BatterySolarPanelBlockEntity extends AbstractSolarPanelBlockEntity
{
	private static final ImmutableList<Direction> POWERABLE_FACES = ImmutableList.of(Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
	
	
	
	public BatterySolarPanelBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(/*YATMBlockEntityTypes.BATTERY_SOLAR_PANEL.get()*/null, blockPos, blockState, POWERABLE_FACES);
	} // end constructor
	
	public BatterySolarPanelBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int maxSafeCurrentTransfer, int maxCurrentTransfer, SolarPanelSettings settings)
	{
		super(/*YATMBlockEntityTypes.BATTERY_SOLAR_PANEL.get()*/null, blockPos, blockState, POWERABLE_FACES, currentCapacity, maxSafeCurrentTransfer, maxCurrentTransfer, settings);
	} // end constructor

} // end class