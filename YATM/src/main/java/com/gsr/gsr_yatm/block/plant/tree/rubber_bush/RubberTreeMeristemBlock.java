package com.gsr.gsr_yatm.block.plant.tree.rubber_bush;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.plant.tree.soul_afflicted_rubber_bush.SoulAfflictedRubberTreeMeristemBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RubberTreeMeristemBlock extends SaplingBlock
{

	public RubberTreeMeristemBlock(@NotNull Properties properties)
	{
		super(new RubberTreeGrower(), Objects.requireNonNull(properties));
	} // end constructor

	
	
	@Override
	public void advanceTree(@NotNull ServerLevel level, @NotNull BlockPos position, @NotNull BlockState state, RandomSource random)
	{
		if(level.getBlockState(position.below()).is(YATMBlockTags.SOUL_AFFLICTING_BLOCKS_KEY)) 
		{
			level.setBlock(position, YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get().defaultBlockState().setValue(SoulAfflictedRubberTreeMeristemBlock.STAGE, state.getValue(RubberTreeMeristemBlock.STAGE)).setValue(SoulAfflictedRubberTreeMeristemBlock.RECENTLY_AFFLICTED, true), Block.UPDATE_ALL);
			return;
		}
		super.advanceTree(level, position, state, random);
	} // end advanceTree()
	
	@Override
	public boolean isBonemealSuccess(Level level, @NotNull RandomSource random, BlockPos position, BlockState state)
	{
		return random.nextInt(YATMConfigs.RUBBER_TREE_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()
	
	@Override
	protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos position)
	{
		return state.is(YATMBlockTags.RUBBER_MERISTEM_CAN_GROW_ON_KEY);
	} // end mayPlaceOn()

} // end class