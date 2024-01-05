package com.gsr.gsr_yatm.block.device.builder.capability_provider;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class BuiltCapabilityProvider implements IInvalidatableCapabilityProvider
{
	private final @NotNull List<Tuple2<Predicate<Direction>, IInvalidatableCapabilityProvider>> m_options;
	private final @NotNull Runnable m_onInvalidate;
	private final @NotNull Runnable m_onRevive;
	private final @NotNull ICapabilityProvider m_lastly;
	
	
	
	public BuiltCapabilityProvider(@NotNull List<Tuple2<Predicate<Direction>, IInvalidatableCapabilityProvider>> options, @NotNull List<Runnable> invalidationListeners, @NotNull List<Runnable> reviveListeners, @NotNull ICapabilityProvider lastly)
	{
		this.m_options = ImmutableList.copyOf(Objects.requireNonNull(options));
		List<Runnable> i = ImmutableList.<Runnable>builder().addAll(Objects.requireNonNull(invalidationListeners)).addAll(options.stream().map((t) -> (Runnable)t.b()::invalidateCaps).iterator()).build();
		this.m_onInvalidate = () -> i.forEach(Runnable::run);
		List<Runnable> r = ImmutableList.<Runnable>builder().addAll(Objects.requireNonNull(reviveListeners)).addAll(options.stream().map((t) -> (Runnable)t.b()::reviveCaps).iterator()).build();
		this.m_onRevive = () -> r.forEach(Runnable::run);
		this.m_lastly = Objects.requireNonNull(lastly);
	} // end constructor

	
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		for(Tuple2<Predicate<Direction>, IInvalidatableCapabilityProvider> o : this.m_options) 
		{
			if(o.a().test(side)) 
			{
				return o.b().getCapability(cap, side);
			}
		}
		return this.m_lastly.getCapability(cap, side);
	} // end getCapability()

	@Override
	public void invalidateCaps()
	{
		this.m_onInvalidate.run();
		this.m_options.forEach((t) -> t.b().invalidateCaps());
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		this.m_onRevive.run();
		this.m_options.forEach((t) -> t.b().reviveCaps());
	} // end reviveCaps()

} // end class()