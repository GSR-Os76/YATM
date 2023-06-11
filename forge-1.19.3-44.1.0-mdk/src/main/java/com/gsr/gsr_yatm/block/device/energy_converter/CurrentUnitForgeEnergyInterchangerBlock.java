package com.gsr.gsr_yatm.block.device.energy_converter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CurrentUnitForgeEnergyInterchangerBlock extends Block implements EntityBlock
{
	
	public CurrentUnitForgeEnergyInterchangerBlock(Properties properties)
	{
		super(properties);
	} // end constructor
	
	

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new CurrentUnitForgeEnergyInterchangerBlockEntity(blockPos, blockState);
	} // end newBlockEntity()

} // end class