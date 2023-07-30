package com.gsr.gsr_yatm.block.sign;

import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class YATMHangingSignBlockEntity extends SignBlockEntity
{
	public static final int MAX_TEXT_LINE_WIDTH = 60;
	public static final int TEXT_LINE_HEIGHT = 9;

	public YATMHangingSignBlockEntity(BlockPos position, BlockState state)
	{
		super(YATMBlockEntityTypes.YATM_HANGING_SIGN.get(), position, state);
	} // end constructor



	@Override
	public int getTextLineHeight()
	{
		return TEXT_LINE_HEIGHT;
	} // end getTextLineHeight()

	@Override
	public int getMaxTextLineWidth()
	{
		return MAX_TEXT_LINE_WIDTH;
	} // end getMaxTextLineWidth()

} // end class