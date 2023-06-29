package com.gsr.gsr_yatm.block.plant.tree;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AerialRootsBlock extends Block implements SimpleWaterloggedBlock 
{	
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	
	
	public AerialRootsBlock(Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
	} // end constructor
	


	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(WATERLOGGED);
	} // end createBlockStateDefinition()
	
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return super.getStateForPlacement(context).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	} // end getStateForPlacement()

	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockStateSecond, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPosSecond)
	{
		if (blockState.getValue(WATERLOGGED))
		{
			levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}

		return super.updateShape(blockState, direction, blockStateSecond, levelAccessor, blockPos, blockPosSecond);
	} // end updateShape()

	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState updateShape)
	{
		return updateShape.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(updateShape);
	} // end getFluidState

} // end class