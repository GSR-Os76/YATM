package com.gsr.gsr_yatm.block.plant.tree;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.registries.tags.ITag;

public class SelfLayeringSaplingBlock extends SaplingBlock
{
	public static final Collection<Direction> USABLE_NEIGHBORS = new ImmutableList.Builder<Direction>().add(Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST).build();

	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL.or(new Predicate<Direction>()
	{

		@Override
		public boolean test(Direction direction)
		{
			return direction != null && direction == Direction.UP;
		}// end test()

	}));// new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.EAST,
		// Direction.WEST, Direction.UP});
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 63);
	
	private final ITag<Block> m_mayGrowOn;
	private List<AbstractTreeGrower> m_apicalGrowers;
	private List<AbstractTreeGrower> m_lateralGrowers;

	
	
	public SelfLayeringSaplingBlock(List<AbstractTreeGrower> apicalGrowers, List<AbstractTreeGrower> lateralGrowers, ITag<Block> mayGrowOn, Properties properties)
	{
		super(apicalGrowers.get(0), properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP).setValue(AGE, 0));
		this.m_mayGrowOn = mayGrowOn;
		this.m_apicalGrowers = apicalGrowers;
		this.m_lateralGrowers = lateralGrowers;
	} // end constructor



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FACING, AGE));
	} // end createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		Direction preferredGrowDirection = context.getClickedFace();
		BlockPos target = context.getClickedPos().relative(preferredGrowDirection.getOpposite());
		if (this.mayPlaceOn(context.getLevel().getBlockState(target), context.getLevel(), target))
		{
			return super.getStateForPlacement(context).setValue(FACING, preferredGrowDirection == Direction.DOWN ? Direction.UP : preferredGrowDirection);
		}

		for (Direction dir : USABLE_NEIGHBORS)
		{
			target = context.getClickedPos().relative(dir);
			if (this.mayPlaceOn(context.getLevel().getBlockState(target), context.getLevel(), target))
			{
				return super.getStateForPlacement(context).setValue(FACING, dir.getOpposite());
			}
		}

		return super.getStateForPlacement(context).setValue(FACING, Direction.UP);
	} // end getStateForPlacement()

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos)
	{
		return this.m_mayGrowOn != null ? this.m_mayGrowOn.contains(blockState.getBlock()) : super.mayPlaceOn(blockState, blockGetter, blockPos);
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos)
	{
		if (blockState.getBlock() == this)
		{
			Direction dir = blockState.getValue(FACING);
			BlockPos target = blockPos.relative(dir.getOpposite());
			if (levelReader.getBlockState(target).canSustainPlant(levelReader, target, dir, this))
			{
				return true;
			}

		}
		else
		{
			for (Direction dir : USABLE_NEIGHBORS)
			{
				if (this.mayPlaceOn(blockState, levelReader, blockPos.relative(dir)))
				{
					return true;
				}
			}
		}
		return false;
	} // end canSurvive()



	@Override
	public void advanceTree(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, RandomSource randomSource)
	{
		if (blockState.getValue(STAGE) == 0)
		{
			serverLevel.setBlock(blockPos, blockState.cycle(STAGE), 4);
		}
		else
		{
			if(blockState.getValue(FACING) == Direction.UP) 
			{
				growClosest(this.m_apicalGrowers, blockState.getValue(AGE), serverLevel, serverLevel.getChunkSource().getGenerator(), blockPos, blockState, randomSource);
			}
			else 
			{
				growClosest(this.m_lateralGrowers, blockState.getValue(AGE), serverLevel, serverLevel.getChunkSource().getGenerator(), blockPos, blockState, randomSource);
			}
			// this.treeGrower.growTree(serverLevel,
			// serverLevel.getChunkSource().getGenerator(), blockPos, blockState,
			// randomSource);
		}
	} // end advanceTree()
	
	private static void growClosest(List<AbstractTreeGrower> growers, int index, ServerLevel serverLevel, ChunkGenerator cg, BlockPos blockPos, BlockState blockState, RandomSource randomSource) 
	{
		// TODO, add in checks
		growers.get(index).growTree(serverLevel, cg, blockPos, blockState, randomSource);
	} // end growClosest



} // end class