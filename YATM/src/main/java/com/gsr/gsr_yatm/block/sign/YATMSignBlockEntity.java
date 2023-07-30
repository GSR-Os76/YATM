package com.gsr.gsr_yatm.block.sign;

import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class YATMSignBlockEntity extends SignBlockEntity
{

	public YATMSignBlockEntity(BlockPos position, BlockState state)
	{
		super(YATMBlockEntityTypes.YATM_SIGN.get(), position, state);
	} // end constructor

} // end class