package com.gsr.gsr_yatm.block.plant.tree;

import java.util.function.Predicate;

import com.gsr.gsr_yatm.block.StrippableRotatedPillarBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.common.util.NonNullSupplier;

public class StrippedSapLogBlock extends StrippableRotatedPillarBlock
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
	
	
	
	public StrippedSapLogBlock(Properties properties, NonNullSupplier<Block> whenStripped)
	{
		super(properties, whenStripped);
		//this.registerDefaultState(this.defaultBlockState().setValue(FLOWING, true).setValue(FACING, Direction.NORTH));
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
		// TODO, if the neighbors or so are right for making of sap, set flowing true, or else false
		if(randomSource.nextInt(12) == 0)
		{	
			serverLevel.setBlock(blockPos, blockState.cycle(FLOWING), UPDATE_ALL);
		}
	} // end randomTick()



	@Override
	public void animateTick(BlockState p_220827_, Level p_220828_, BlockPos p_220829_, RandomSource p_220830_)
	{
		// TODO, spawn drips of appropiate fluid if leaking
	} // end animateTick()
	
} // end class