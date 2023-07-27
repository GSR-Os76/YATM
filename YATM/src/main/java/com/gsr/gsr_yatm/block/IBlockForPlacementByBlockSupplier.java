package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface IBlockForPlacementByBlockSupplier
{
	public @Nullable BlockState get(@NotNull ServerLevel placerLevel, @NotNull BlockState placerState, @NotNull BlockPos placerPosition, @NotNull ServerLevel placingLevel, @NotNull BlockPos placingPosition);
	
} // end interface