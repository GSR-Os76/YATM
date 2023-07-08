package com.gsr.gsr_yatm.block.device.solar;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SolarPanelBlockEntity extends AbstractSolarPanelBlockEntity
{
	private static final ImmutableList<Direction> POWERABLE_FACES = ImmutableList.of(Direction.DOWN);
	
	
	
	public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(YATMBlockEntityTypes.SOLAR_PANEL.get(), blockPos, blockState, POWERABLE_FACES);
	} // end constructor
	
	public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int maxSafeCurrentTransfer, int maxCurrentTransfer, SolarPanelSettings settings)
	{
		super(YATMBlockEntityTypes.SOLAR_PANEL.get(), blockPos, blockState, SolarPanelBlockEntity.POWERABLE_FACES, currentCapacity, maxSafeCurrentTransfer, maxCurrentTransfer, settings);
	} // end constructor

	
	
	@Override
	public void serverTick(Level level, BlockPos pos, BlockState blockState)
	{
		// hopefully this successfully clears it
		this.m_internalCurrentStorer.extractCurrent(Integer.MAX_VALUE, false);
		super.serverTick(level, pos, blockState);
	} // end serverTick()
	
} // end class