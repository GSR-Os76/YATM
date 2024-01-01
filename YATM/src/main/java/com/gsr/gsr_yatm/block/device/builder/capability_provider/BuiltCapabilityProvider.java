package com.gsr.gsr_yatm.block.device.builder.capability_provider;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.option.IOption;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class BuiltCapabilityProvider implements IInvalidatableCapabilityProvider
{
	private final @NotNull List<Tuple2<Predicate<Direction>, IOption>> m_options;
	private final @NotNull Runnable m_onInvalidate;
	private final @NotNull Runnable m_onRevive;

	
	
	public BuiltCapabilityProvider(@NotNull List<Tuple2<Predicate<Direction>, IOption>> options, @NotNull List<Runnable> invalidationListeners, @NotNull List<Runnable> reviveListeners)
	{
		this.m_options = ImmutableList.copyOf(Objects.requireNonNull(options));
		List<Runnable> i = ImmutableList.copyOf(Objects.requireNonNull(invalidationListeners));
		this.m_onInvalidate = () -> ImmutableList.copyOf(i).forEach(Runnable::run);
		List<Runnable> r = ImmutableList.copyOf(Objects.requireNonNull(reviveListeners));
		this.m_onRevive = () -> ImmutableList.copyOf(r).forEach(Runnable::run);
		throw new NotImplementedException();
	} // end constructor

	
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		// TODO Auto-generated method stub
		return null;
	} // end getCapabilty()

	@Override
	public void invalidateCaps()
	{
		this.m_onInvalidate.run();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		this.m_onRevive.run();
	} // end reviveCaps()

} // end class()