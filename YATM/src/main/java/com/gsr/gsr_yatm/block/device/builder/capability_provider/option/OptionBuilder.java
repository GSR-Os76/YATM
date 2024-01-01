package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import java.util.Objects;
import java.util.function.Consumer;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class OptionBuilder<T> implements IOptionBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Consumer<IOption> m_buildReceiver;
	
	
	
	public OptionBuilder(@Nullable T parent, @NotNull Consumer<IOption> buildReceiver) 
	{
		this.m_parent = parent;
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
		throw new NotImplementedException();
	} // end constructor


	
	@Override
	public <C> @NotNull ICapabilityChainBuilder<? extends IOptionBuilder<T>> returnsWhen(@NotNull Capability<C> cap, C result)
	{
		// TODO Auto-generated method stub
		return null;
	} // end returnsWhen()

	@Override
	public <C> @NotNull ICapabilityProviderChainBuilder<? extends IOptionBuilder<T>> returns(@NotNull ICapabilityProvider result)
	{
		// TODO Auto-generated method stub
		return null;
	} // end returns()
	
	@Override
	public @Nullable T end()
	{
		// TODO Auto-generated method stub
		return null;
	} // end end()
	
} // end class