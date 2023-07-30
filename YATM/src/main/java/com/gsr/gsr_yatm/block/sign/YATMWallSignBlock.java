package com.gsr.gsr_yatm.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class YATMWallSignBlock extends WallSignBlock
{

	public YATMWallSignBlock(Properties properties, WoodType woodType)
	{
		super(properties, woodType);
	} // end constructor

	
	
	@Override
	public BlockEntity newBlockEntity(BlockPos position, BlockState state)
	{
		return new YATMSignBlockEntity(position, state);
	} // end newBlockEntity()
	
} // end class