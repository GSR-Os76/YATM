package com.gsr.gsr_yatm.block.plant.pitcher_cluster;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.IYATMPlantable;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;

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

public class PitcherClusterBlock extends Block implements BonemealableBlock, IYATMPlantable
{

	public PitcherClusterBlock(@NotNull Properties properties)
	{
		super(properties);
	} // end constructor

	
	
	
	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos position, BlockState state, boolean p_50900_)
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
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos position, BlockState state)
	{
		return true;
	} // end isBonemealSuccess()

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos position, BlockState state)
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
	
	
	
	@Override
	public boolean canPlantOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Direction face)
	{
		return face == Direction.UP 
				&& state.is(YATMBlockTags.PITCHER_CLUSTERS_CAN_GROW_ON_KEY) 
				&& level.getBlockState(pos.above()).is(Blocks.AIR);
	} // end canPlantOn()
	
} // end class