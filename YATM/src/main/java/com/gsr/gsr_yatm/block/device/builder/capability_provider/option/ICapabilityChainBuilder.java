package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ICapabilityChainBuilder<T> extends IBuilder<T>
{
	// elif based on cap chain
	@NotNull <C> ICapabilityChainBuilder<T> elifReturnsWhen(@NotNull Capability<C> cap, @NotNull C result);
	
	@NotNull ICapabilityProviderChainBuilder<T> elifReturns(@NotNull ICapabilityProvider result);

} // end interface