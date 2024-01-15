package com.gsr.gsr_yatm.block.flammable;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.plant.tree.TappedLogBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.util.TriPredicate;

public class FlammableTappedLogBlock extends TappedLogBlock
{
	private final int m_flammability;
	private final int m_fireSpreadSpeed;
	
	
	
	public FlammableTappedLogBlock(@NotNull Properties properties, @NotNull Supplier<Fluid> fluid, @NotNull TriPredicate<Level, BlockState, BlockPos> sapProviderTest, @NotNull Supplier<ParticleOptions> dripParticle, int flammability, int fireSpreadSpeed)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(fluid), Objects.requireNonNull(sapProviderTest), Objects.requireNonNull(dripParticle));
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
	
} // end constructor