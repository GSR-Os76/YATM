package com.gsr.gsr_yatm.block.device.solar;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class SolarPanelBlockEntity extends AbstractSolarPanelBlockEntity
{
	private static final ImmutableList<Direction> POWERABLE_FACES = ImmutableList.of(Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
	
	
	
	public SolarPanelBlockEntity(BlockPos position, BlockState state)
	{
		super(YATMBlockEntityTypes.SOLAR_PANEL.get(), position, state, POWERABLE_FACES);
	} // end constructor
	
	public SolarPanelBlockEntity(BlockPos position, BlockState state, int currentCapacity, int maxSafeCurrentTransfer, int maxCurrentTransfer, SolarPanelSettings settings)
	{
		super(YATMBlockEntityTypes.SOLAR_PANEL.get(), position, state, POWERABLE_FACES, currentCapacity, maxSafeCurrentTransfer, maxCurrentTransfer, settings);
	} // end constructor

} // end class