package com.gsr.gsr_yatm.block;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.plant.tree.AerialRootsBlock;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class WaterloggableBlock extends ShapeBlock implements SimpleWaterloggedBlock
{
	public static final @NotNull BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	
	
	public WaterloggableBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(WaterloggableBlock.WATERLOGGED, false));
	} // end constructor

	

	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(WaterloggableBlock.WATERLOGGED));
	} // end createBlockStateDefinition()

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		return super.getStateForPlacement(context).setValue(WaterloggableBlock.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
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