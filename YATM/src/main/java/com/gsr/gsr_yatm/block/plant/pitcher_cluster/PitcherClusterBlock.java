package com.gsr.gsr_yatm.block.plant.pitcher_cluster;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.BlockUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PitcherClusterBlock extends Block implements IYATMPlantableBlock, BonemealableBlock
{

	public PitcherClusterBlock(@NotNull Properties properties)
	{
		super(Objects.requireNonNull(properties));
	} // end constructor

	
	
	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.PITCHER_CLUSTERS_CAN_GROW_ON_KEY);
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
	public boolean canPlantOn(@NotNull LevelReader level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull Direction face)
	{
		BlockPos above = position.above();
		return face == Direction.UP 
				&& this.canSurvive(level.getBlockState(above), level, above)
				&& level.getBlockState(position.above()).is(Blocks.AIR);
	} // end canPlantOn()

	
	
	
	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state, boolean p_50900_)
	{
//		BlockPos.MutableBlockPos mPos = new BlockPos.MutableBlockPos().set(position);
//		for(int x = -2; x <= 2; x++) 
//		{
//			mPos.setWithOffset(position, x, 0, 0);
//			for(int z = -2; z <= 2; z++) 
//			{
//				for(int y = -1; y <= 1; y++) 
//				{
//					mPos.setWithOffset(position, x, y, z);
//					if(level.getBlockState(mPos).is(Blocks.AIR) 
//							&& this.canPlantOn(level, level.getBlockState(mPos.below()), mPos.below(), Direction.UP)) 
//					{
//						return true;
//					}
//				}
//			}
//		}
		return true;
	} // end isValidBonemealTarget()
	
	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return true;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		int placed = 0;
		for(int i = 0; i < 12; i++) 
		{
			BlockPos toCheck = position.offset(random.nextIntBetweenInclusive(-2, 2), random.nextIntBetweenInclusive(-1, 1), random.nextIntBetweenInclusive(-2, 2));
			BlockPos belowToCheck = toCheck.below();
			if(level.getBlockState(toCheck).is(Blocks.AIR) 
					&& this.canPlantOn(level, level.getBlockState(belowToCheck), belowToCheck, Direction.UP))
			{
				level.setBlock(toCheck, this.defaultBlockState(), Block.UPDATE_ALL);
				placed++;
			}
			if(placed >= 5) 
			{
				break;
			}
		}
	} // end performBonemeal()
	
} // end class