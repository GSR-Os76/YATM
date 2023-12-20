package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ICapabilityProviderChainBuilder<T> extends IBuilder<T>
{
	@NotNull <C> ICapabilityChainBuilder<T> elifEmptyReturnsWhen(@NotNull Capability<C> cap, @NotNull C result);
	
	@NotNull <C> ICapabilityProviderChainBuilder<? extends IOptionBuilder<T>> elifEmptyReturns(@NotNull ICapabilityProvider result);

} // end interface