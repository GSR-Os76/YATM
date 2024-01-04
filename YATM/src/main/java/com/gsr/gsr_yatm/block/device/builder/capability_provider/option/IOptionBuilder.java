package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import org.jetbrains.annotations.NotNull;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IOptionBuilder<T>
{
	@NotNull <C> ICapabilityChainBuilder<T> returnsWhen(@NotNull Capability<C> cap, C result);

	@NotNull <C> ICapabilityProviderChainBuilder<T> returns(@NotNull ICapabilityProvider result);

} // end interface