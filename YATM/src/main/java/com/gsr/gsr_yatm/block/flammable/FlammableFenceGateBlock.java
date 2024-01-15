package com.gsr.gsr_yatm.block.flammable;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FlammableFenceGateBlock extends FenceGateBlock
{
	private final int m_flammability;
	private final int m_fireSpreadSpeed;
	
	
	
	public FlammableFenceGateBlock(@NotNull Properties properties, SoundEvent openSound, SoundEvent closeSound, int flammability, int fireSpreadSpeed)
	{
		super(Objects.requireNonNull(properties), openSound, closeSound);
		this.m_flammability = flammability;
		this.m_fireSpreadSpeed = fireSpreadSpeed;
	} // end constructor

	
	
	// the getBurnOdds equivalent
	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
	{
		return this.m_flammability;
	} // end getFlammability()

	// the getIgniteOdds equivalent
	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
	{
		return this.m_fireSpreadSpeed;
	} // end getFireSpreadSpeed()
	
} // end class