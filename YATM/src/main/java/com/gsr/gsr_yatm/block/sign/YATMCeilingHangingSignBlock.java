package com.gsr.gsr_yatm.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class YATMCeilingHangingSignBlock extends CeilingHangingSignBlock
{

	public YATMCeilingHangingSignBlock(Properties properties, WoodType woodType)
	{
		super(properties, woodType);
	} // end constructor

	
	
	@Override
	public BlockEntity newBlockEntity(BlockPos position, BlockState state)
	{
		return new YATMHangingSignBlockEntity(position, state);
	} // end newBlockEntity()
	
} // end class