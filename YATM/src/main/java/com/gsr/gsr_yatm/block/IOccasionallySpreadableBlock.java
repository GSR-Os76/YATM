package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IOccasionallySpreadableBlock
{
	public boolean canSpread(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos);
} // end interface