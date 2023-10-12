package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
/*
m

- Changed a tag from "gsr_yatm:mediums/aurum_grows_on" to "gsr_yatm:mediums/aurum_can_grow_on".
- Changed a tag from "gsr_yatm:mediums/carcass_root_grows_on" to "gsr_yatm:mediums/mediums/carcass_root_can_grow_on".
- Added forge gravel tag to tag "gsr_yatm:mediums/ice_coral_can_grow_on".
- Rename: com.gsr.gsr_yatm.block.plant.fern -> com.gsr.gsr_yatm.block.plant.aurum.
- Rename: com.gsr.gsr_yatm.block.plant.moss -> com.gsr.gsr_yatm.block.plant.prismarine_crystal_moss.
*/
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
		// TODO, sound
		return;
	} // end breakBlockNoDrops()
	
} // end class