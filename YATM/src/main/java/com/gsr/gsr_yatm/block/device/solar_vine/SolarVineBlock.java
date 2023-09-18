package com.gsr.gsr_yatm.block.device.solar_vine;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SolarVineBlock extends Block
{
	public static final BooleanProperty ROOT_UP = YATMBlockStateProperties.HAS_UP;
	public static final BooleanProperty ROOT_DOWN = YATMBlockStateProperties.HAS_DOWN;
	
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	
	
	
	public SolarVineBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties));
		this.m_shape = Objects.requireNonNull(shape);
		this.registerDefaultState(this.defaultBlockState().setValue(SolarVineBlock.ROOT_UP, false).setValue(SolarVineBlock.ROOT_DOWN, false));
	} // end constructor



	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(SolarVineBlock.ROOT_UP, SolarVineBlock.ROOT_DOWN));
	} // end createBlockStateDefinition()

	@Override
	public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos();
		return this.getStateForPlace(level, position);
	} // end getStateForPlacement()
	
	protected @NotNull BlockState getStateForPlace(@NotNull Level level, @NotNull BlockPos position) 
	{
		return this.defaultBlockState()
				.setValue(SolarVineBlock.ROOT_UP, this.shouldRootUp(level, position))
				.setValue(SolarVineBlock.ROOT_DOWN, this.shouldRootDown(level, position));
	} // end getStateForPlace()
	
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos position, Block oldNeighbor, BlockPos neighborPosition, boolean p_60514_)
	{
		if((state.getValue(SolarVineBlock.ROOT_UP) != this.shouldRootUp(level, position)) 
			|| (state.getValue(SolarVineBlock.ROOT_DOWN) != this.shouldRootDown(level, position))) 
		{
			level.setBlock(position, this.getStateForPlace(level, position), Block.UPDATE_CLIENTS);
		} 
	} // end neighborChanged()
	
	protected boolean shouldRootUp(@NotNull Level level, @NotNull BlockPos position) 
	{
		BlockPos abovePos = position.above();
		BlockState above = level.getBlockState(abovePos);
		return !above.is(this) && Block.isFaceFull(above.getBlockSupportShape(level, abovePos), Direction.DOWN);
	} // end shouldRootUp()
	
	protected boolean shouldRootDown(@NotNull Level level, @NotNull BlockPos position) 
	{
		BlockPos belowPos = position.below();
		BlockState below = level.getBlockState(belowPos);
		return !below.is(this) && Block.isFaceFull(below.getBlockSupportShape(level, belowPos), Direction.UP);
	} // end shouldRootDown()
	
} // end class