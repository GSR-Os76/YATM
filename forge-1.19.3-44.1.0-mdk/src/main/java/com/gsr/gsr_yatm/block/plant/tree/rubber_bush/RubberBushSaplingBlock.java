package com.gsr.gsr_yatm.block.plant.tree.rubber_bush;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.YATMBlockTags;
import com.gsr.gsr_yatm.YATMBlocks;
import com.gsr.gsr_yatm.block.plant.tree.SelfLayeringSaplingBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RubberBushSaplingBlock extends SelfLayeringSaplingBlock
{

	public RubberBushSaplingBlock(Properties properties)
	{
		super(ImmutableList.of(new ApicalRubberBushGrower()), ImmutableList.of(new FirstLateralRubberBushGrower(), new SecondLateralRubberBushGrower()), YATMBlockTags.RUBBER_MERISTEM_CAN_GROW_ON, properties);
	} // end constructor

	
	
	@Override
	public boolean isBonemealSuccess(Level level, RandomSource tandomSource, BlockPos blockPos, BlockState blockState)
	{
		return true;
	} // end is BoneMealSuccess()

	

	@Override
	public void advanceTree(ServerLevel level, BlockPos bp, BlockState bs, RandomSource rs)
	{
		//if on soul block become soul afflicted meristem instead
		Block on = level.getBlockState(bp.relative(bs.getValue(FACING).getOpposite())).getBlock();
		if(YATMBlockTags.SOUL_AFFLICTING_BLOCKS.contains(on)) 
		{
			level.setBlock(bp, 
					YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get()
					.defaultBlockState().setValue(STAGE, bs.getValue(STAGE)).setValue(FACING, bs.getValue(FACING)).setValue(SoulAfflictedRubberBushSaplingBlock.RECENTLY_AFFLICTED, true)
					, UPDATE_ALL);
			return;
		}
		super.advanceTree(level, bp, bs, rs);
	} // end advanceTree()
	
	
	
} // end class