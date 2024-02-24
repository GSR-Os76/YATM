package com.gsr.gsr_yatm.block.device.conduit;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.ITickingBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IConduitBlockEntity extends ITickingBlockEntity
{

	void recheckNeighborAttachments(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state);
	
	

} // end interface