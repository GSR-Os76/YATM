package com.gsr.gsr_yatm.block.sign;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class YATMStandingSignBlock extends StandingSignBlock
{

	public YATMStandingSignBlock(@NotNull Properties properties, @NotNull WoodType woodType)
	{
		super(Objects.requireNonNull(woodType), Objects.requireNonNull(properties));
	} // end constructor

	
	
	@Override
	public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new YATMSignBlockEntity(position, state);
	} // end newBlockEntity()

} // end class