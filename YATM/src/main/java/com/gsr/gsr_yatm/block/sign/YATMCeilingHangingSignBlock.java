package com.gsr.gsr_yatm.block.sign;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class YATMCeilingHangingSignBlock extends CeilingHangingSignBlock
{

	public YATMCeilingHangingSignBlock(@NotNull Properties properties, @NotNull WoodType woodType)
	{
		super(Objects.requireNonNull(woodType), Objects.requireNonNull(properties));
	} // end constructor

	
	
	@Override
	public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new YATMHangingSignBlockEntity(position, state);
	} // end newBlockEntity()
	
} // end class