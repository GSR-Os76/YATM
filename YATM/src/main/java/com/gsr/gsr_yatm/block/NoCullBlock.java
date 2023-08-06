package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class NoCullBlock extends Block
{

	public NoCullBlock(@NotNull Properties properties)
	{
		super(properties);
	} // end constructor

	
	
	@Override
	public boolean supportsExternalFaceHiding(BlockState state)
	{
		return false;
	} // end supportsExternalFaceHiding()
	
} // end class