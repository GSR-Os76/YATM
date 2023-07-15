package com.gsr.gsr_yatm.block.device.compute;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.DataPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface IDataProcessingTicker
{
	public @Nullable List<DataPacket> process(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state);
	
} // end interface