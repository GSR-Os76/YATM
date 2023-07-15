package com.gsr.gsr_yatm.block.device.compute;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DataHandlerBlockEntity extends BlockEntity
{
	public static final String SETUP_TAG_NAME = "setup";
	
	
	
	public DataHandlerBlockEntity(BlockEntityType<?> type, BlockPos position, BlockState state)
	{
		super(type, position, state);
	} // end constructor

	

} // end class