package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;

import net.minecraftforge.common.capabilities.Capability;

public class CapabilityChainBuilder<T> implements ICapabilityChainBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Consumer<IInvalidatableCapabilityProvider> m_buildReceiver;
	
	private final @NotNull List<Tuple2<Capability<?>, ?>> m_capabilityChain = Lists.newArrayList();

	
	
	public <C> CapabilityChainBuilder(@Nullable T parent, @NotNull Consumer<IInvalidatableCapabilityProvider> buildReceiver, @NotNull Capability<C> cap, C result) 
	{
		this.m_parent = parent;
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
		this.m_capabilityChain.add(Tuple.of(Objects.requireNonNull(cap), Objects.requireNonNull(result)));
	} // end constructor

	
	
	@Override
	public <C> @NotNull ICapabilityChainBuilder<T> elifReturnsWhen(@NotNull Capability<C> cap, @NotNull C result)
	{
		this.m_capabilityChain.add(Tuple.of(cap, result));
		return this;
	} // end elifReturnsWhen()

	@Override
	public @NotNull ICapabilityProviderChainBuilder<T> elifReturns(@NotNull IInvalidatableCapabilityProvider result)
	{
		return new CapabilityProviderChainBuilder<>(this.m_parent, (o) -> this.m_buildReceiver.accept(new CapabiltyChain(this.m_capabilityChain, o)), result);
	} // end elifReturns()
	
	@Override
	public @Nullable T end()
	{
		this.m_buildReceiver.accept(new CapabiltyChain(this.m_capabilityChain, null));
		return this.m_parent;
	} // end end()
	
} // end class()