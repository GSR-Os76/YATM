package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;

import net.minecraftforge.common.capabilities.Capability;

public class CapabilityProviderChainBuilder<T> implements ICapabilityProviderChainBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Consumer<IInvalidatableCapabilityProvider> m_buildReceiver;
	private final @NotNull List<IInvalidatableCapabilityProvider> m_chain = Lists.newArrayList();
	
	
	
	public CapabilityProviderChainBuilder(@Nullable T parent, @NotNull Consumer<IInvalidatableCapabilityProvider> buildReceiver, @NotNull IInvalidatableCapabilityProvider result) 
	{
		this.m_parent = parent;
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
		this.m_chain.add(result);
	} // end constructor
	
	

	@Override
	public <C> @NotNull ICapabilityChainBuilder<T> elifEmptyReturnsWhen(@NotNull Capability<C> cap, @NotNull C result)
	{
		return new CapabilityChainBuilder<>(this.m_parent, this::sendBuild, cap, result);
	} // end elifEmptyReturnsWhen()

	@Override
	public <C> @NotNull ICapabilityProviderChainBuilder<T> elifEmptyReturns(@NotNull IInvalidatableCapabilityProvider result)
	{
		this.m_chain.add(result);
		return this;
	} // end elifEmptyReturns()
	
	@Override
	public @Nullable T end()
	{
		this.sendBuild(null);
		return this.m_parent;
	} // end end()
	
	private void sendBuild(@NotNull IInvalidatableCapabilityProvider lastly) 
	{
		this.m_buildReceiver.accept(new EmptyChain(this.m_chain, lastly));
	} // end sendBuild()
	
} // end class