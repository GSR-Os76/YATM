package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtilities
{
	public static void breakBlock(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position) 
	{
		BlockUtilities.breakBlockNoDrops(level, state, position);
		Block.dropResources(state, level, position);
		return;
	} // end simulateBreak()

	public static void breakBlockNoDrops(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
		level.setBlock(position, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
		// TODO, particles
		level.playSound((Player)null, position, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
		return;
	} // end breakBlockNoDrops()
	
} // end class