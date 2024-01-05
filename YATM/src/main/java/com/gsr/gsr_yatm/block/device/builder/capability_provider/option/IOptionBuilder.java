package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IOptionBuilder<T>
{
	@NotNull <C> ICapabilityChainBuilder<T> returnsWhen(@NotNull Capability<C> cap, C result);

	default @NotNull <C> ICapabilityProviderChainBuilder<T> returns(@NotNull ICapabilityProvider result)
	{
		return returns(IInvalidatableCapabilityProvider.of(result));
	} // end returns()
	
	@NotNull <C> ICapabilityProviderChainBuilder<T> returns(@NotNull IInvalidatableCapabilityProvider result);

} // end interface