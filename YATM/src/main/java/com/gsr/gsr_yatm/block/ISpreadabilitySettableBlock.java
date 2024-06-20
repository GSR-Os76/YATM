package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ISpreadabilitySettableBlock
{
	public @NotNull BlockState setSpreadability(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, boolean isSettable);
	
} // end class