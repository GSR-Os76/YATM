package com.gsr.gsr_yatm.block.plant.tree;

import java.util.Objects;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

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
	
	
	
	public AerialRootsBlock(@NotNull Properties properties)
	{
		super(Objects.requireNonNull(properties));
		this.registerDefaultState(this.defaultBlockState().setValue(AerialRootsBlock.WATERLOGGED, false));
	} // end constructor
	


	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(AerialRootsBlock.WATERLOGGED);
	} // end createBlockStateDefinition()
	
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return super.getStateForPlacement(context).setValue(AerialRootsBlock.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	} // end getStateForPlacement()

	@SuppressWarnings("deprecation")
	public @NotNull BlockState updateShape(@NotNull BlockState state, Direction direction, BlockState stateSecond, @NotNull LevelAccessor level, @NotNull BlockPos position, BlockPos positionSecond)
	{
		if (state.getValue(AerialRootsBlock.WATERLOGGED))
		{
			level.scheduleTick(position, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return super.updateShape(state, direction, stateSecond, level, position, positionSecond);
	} // end updateShape()

	@SuppressWarnings("deprecation")
	public @NotNull FluidState getFluidState(@NotNull BlockState state)
	{
		return state.getValue(AerialRootsBlock.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	} // end getFluidState()

} // end class