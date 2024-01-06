package com.gsr.gsr_yatm.block.device.behaviors;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ITickableBehavior extends IBehavior
{
	
	boolean tick(@NotNull Level level, @NotNull BlockPos position);
	
} // end class