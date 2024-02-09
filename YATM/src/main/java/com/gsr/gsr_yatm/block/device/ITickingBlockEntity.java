package com.gsr.gsr_yatm.block.device;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ITickingBlockEntity extends IBlockEntity
{
	public static void tick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull ITickingBlockEntity blockEntity)
	{
		if (level.isClientSide)
		{
			blockEntity.clientTick(level, position, state);
		}
		else
		{
			blockEntity.serverTick(level, position, state);
		}
	} // end tick()

	default void clientTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state) { } // end clientTick()
	
	default void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state) { } // end serverTick()

} // end interface