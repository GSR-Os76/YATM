package com.gsr.gsr_yatm.block.device.heat_sink;

import com.gsr.gsr_yatm.YATMBlockStateProperties;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class HeatSinkBlock extends Block
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING;
	
	
	
	public HeatSinkBlock(Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
	} // end constructor

	
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	} // end createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{		
		Direction direction = context.getClickedFace();//.getOpposite();
		return this.defaultBlockState().setValue(FACING, direction);
	} // end getStateForPlacement()
	
} // end class