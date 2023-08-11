package com.gsr.gsr_yatm.block.device.sap_collector;

import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface ISapCollector
{
	public default void onFluidFilled(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull FluidStack fluid) {}

	public default void onFluidDrained(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull FluidStack fluid) {}
	
	public default @NotNull Collection<Fluid> recievableFluids() 
	{ 
		return List.of();
	} // end recieveableFluids()
	
} // end interface