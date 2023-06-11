package com.gsr.gsr_yatm.block.device.heat_sink;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class HeatSinkBlock extends Block
{
	private static final DirectionProperty FACING = DirectionProperty.create("facing");
	
	
	
	public HeatSinkBlock(Properties p_49795_)
	{
		super(p_49795_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
	} // end constructor

	
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	} // end createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_)
	{		
		Direction direction = p_49820_.getClickedFace().getOpposite();
		return this.defaultBlockState().setValue(FACING, direction);
	} // end getStateForPlacement()

	
	
} // end class