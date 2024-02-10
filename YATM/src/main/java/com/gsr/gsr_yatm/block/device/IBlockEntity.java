package com.gsr.gsr_yatm.block.device;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IBlockEntity
{
	default @NotNull BlockEntity self() 
	{
		return (BlockEntity)this;
	} // end self()
	
} // end interface()