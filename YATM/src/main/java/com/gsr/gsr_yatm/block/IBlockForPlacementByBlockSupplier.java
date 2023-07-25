package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface IBlockForPlacementByBlockSupplier
{
	public @Nullable BlockState get(@NotNull Level placerLevel, @NotNull BlockState placerState, @NotNull BlockPos placerPosition, @NotNull Level placingLevel, @NotNull BlockPos placingPosition);
	
	public default @Nullable BlockState get(@NotNull Level placingLevel, @NotNull BlockPos placingPosition)
	{
		return this.get(null, null, null, placingLevel, placingPosition);
	} // end get()
	
} // end interface