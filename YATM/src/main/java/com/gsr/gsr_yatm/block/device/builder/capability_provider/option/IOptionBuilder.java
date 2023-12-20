package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IOptionBuilder<T> extends IBuilder<T>
{
	@NotNull <C> ICapabilityChainBuilder<? extends IOptionBuilder<T>> returnsWhen(@NotNull Capability<C> cap, C result);

	@NotNull <C> ICapabilityProviderChainBuilder<? extends IOptionBuilder<T>> returns(@NotNull ICapabilityProvider result);

} // end interface