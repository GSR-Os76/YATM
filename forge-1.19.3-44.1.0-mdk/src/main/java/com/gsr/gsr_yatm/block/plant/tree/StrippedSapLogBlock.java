package com.gsr.gsr_yatm.block.plant.tree;

import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class StrippedSapLogBlock extends RotatedPillarBlock
{
	public static final BooleanProperty FLOWING = BooleanProperty.create("flowing");
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL.or(new Predicate<Direction>()
	{
		@Override
		public boolean test(Direction direction)
		{
			return direction != null && direction == Direction.DOWN;
		}// end test()
	}));
	
	
	
	public StrippedSapLogBlock(Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FLOWING, true).setValue(FACING, Direction.NORTH));
	} // end constructor



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FLOWING, FACING));
	} // end createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		Direction facing = context.getClickedFace().getAxis() == Axis.Y ? context.getHorizontalDirection().getOpposite() : Direction.DOWN;
		return super.getStateForPlacement(context).setValue(FACING, facing);		
	} // end getStateForPlacement()



	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource)
	{
		// TODO, if the neighbors or so are right for making of sap, set flowing true, else false
		serverLevel.setBlock(blockPos, blockState.cycle(FLOWING), UPDATE_ALL);
	} // end randomTick()

	
} // end class