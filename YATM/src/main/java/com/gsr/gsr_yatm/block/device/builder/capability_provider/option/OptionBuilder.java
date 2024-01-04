package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class OptionBuilder<T> implements IOptionBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Consumer<IInvalidatableCapabilityProvider> m_buildReceiver;
	
	private @Nullable IInvalidatableCapabilityProvider m_option = null;
	
	
	
	public OptionBuilder(@Nullable T parent, @NotNull Consumer<IInvalidatableCapabilityProvider> buildReceiver) 
	{
		this.m_parent = parent;
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
	} // end constructor


	
	@Override
	public <C> @NotNull ICapabilityChainBuilder<T> returnsWhen(@NotNull Capability<C> cap, C result)
	{
		return new CapabilityChainBuilder<>(this.m_parent, this.m_buildReceiver::accept, cap, result);
	} // end returnsWhen()

	@Override
	public <C> @NotNull ICapabilityProviderChainBuilder<T> returns(@NotNull ICapabilityProvider result)
	{
		return new CapabilityProviderChainBuilder<>(this.m_parent, this.m_buildReceiver::accept, result);
	} // end returns()
	
} // end class