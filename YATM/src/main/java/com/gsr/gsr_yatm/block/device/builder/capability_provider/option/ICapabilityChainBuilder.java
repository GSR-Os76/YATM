package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ICapabilityChainBuilder<T> extends IBuilder<T>
{
	// elif based on cap chain
	@NotNull <C> ICapabilityChainBuilder<T> elifReturnsWhen(@NotNull Capability<C> cap, @NotNull C result);
	
	default @NotNull ICapabilityProviderChainBuilder<T> elifReturns(@NotNull ICapabilityProvider result)
	{
		return this.elifReturns(IInvalidatableCapabilityProvider.of(result));
	} // end elifReturns()

	@NotNull ICapabilityProviderChainBuilder<T> elifReturns(@NotNull IInvalidatableCapabilityProvider result);

} // end interface