package com.gsr.gsr_yatm.block.candle_lantern;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CandleLanternBlock extends AbstractCandleBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	   
	public static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of(new Vec3(8d/16d, 5.5d/16d, 8d/16d), new Vec3(7d/16d, 6.5d/16d, 9d/16d), new Vec3(5.5d/16d, 5.5d/16d, 8.5d/16d));
	
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	
	
	 
	public CandleLanternBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties));
		
		this.registerDefaultState(this.defaultBlockState().setValue(CandleLanternBlock.HANGING, false)
				.setValue(CandleLanternBlock.LIT, false).setValue(CandleLanternBlock.WATERLOGGED, false));
		this.m_shape = Objects.requireNonNull(shape);
	} // end constructor

	
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(CandleLanternBlock.HANGING, CandleLanternBlock.LIT, CandleLanternBlock.WATERLOGGED));
	} // end createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		BlockState state = super.getStateForPlacement(context)
				.setValue(CandleLanternBlock.HANGING, context.getNearestLookingVerticalDirection() == Direction.UP)
				.setValue(CandleLanternBlock.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
		return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
	} // end getStateForPlacement()
	

	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult)
	{
		if (state.getValue(CandleLanternBlock.LIT) && player.getItemInHand(hand).isEmpty() && player.getAbilities().mayBuild)
		{
			AbstractCandleBlock.extinguish(player, state, level, position);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.use(state, level, position, player, hand, hitResult);		
	} // end user()
	
	

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull BlockState updateShape(@NotNull BlockState state, Direction direction, BlockState stateSecond, @NotNull LevelAccessor level, @NotNull BlockPos position, BlockPos positionSecond)
	{
		if (state.getValue(CandleLanternBlock.WATERLOGGED))
		{
			level.scheduleTick(position, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return super.updateShape(state, direction, stateSecond, level, position, positionSecond);
	} // end updateShape()

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(@NotNull BlockState state)
	{
		return state.getValue(CandleLanternBlock.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	} // end getFluidState()
	
	@Override
	public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull FluidState fluidState)
	{
		if (!state.getValue(CandleLanternBlock.WATERLOGGED) && fluidState.getType() == Fluids.WATER)
		{
			BlockState toState = state.setValue(CandleLanternBlock.WATERLOGGED, true);
			if (state.getValue(CandleLanternBlock.LIT))
			{
				AbstractCandleBlock.extinguish((Player)null, toState, level, position);
			}
			else
			{
				level.setBlock(position, toState, Block.UPDATE_ALL);
			}

			level.scheduleTick(position, fluidState.getType(), fluidState.getType().getTickDelay(level));
			return true;
		}
		return false;		
	} // end placeLiquid()
	
	
	
	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		Direction supportSide = state.getValue(CandleLanternBlock.HANGING) ? Direction.DOWN : Direction.UP;
		return Block.canSupportCenter(level, position.relative(supportSide.getOpposite()), supportSide);
	} // end canSurvive()
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Block formerNeighbor, @NotNull BlockPos neighborPos, boolean p_60514_)
	{
		if(!this.canSurvive(state, level, position)) 
		{
			BlockUtilities.breakBlock(level, state, position);
		}
		super.neighborChanged(state, level, position, formerNeighbor, neighborPos, p_60514_);		
	} // end neighborChanged()
	
	
	
	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos position, @NotNull CollisionContext context)
	{
		return this.m_shape.getShape(state, blockGetter, position, context);
	} // end getShape()
	
	@Override
	protected @NotNull Iterable<Vec3> getParticleOffsets(@NotNull BlockState state)
	{
		return CandleLanternBlock.PARTICLE_OFFSETS;
	} // end getParticleOffsets()	
	
	
} // end class