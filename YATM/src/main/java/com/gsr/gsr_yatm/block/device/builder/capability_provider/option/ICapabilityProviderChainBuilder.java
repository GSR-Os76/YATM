package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ICapabilityProviderChainBuilder<T> extends IBuilder<T>
{
	@NotNull <C> ICapabilityChainBuilder<T> elifEmptyReturnsWhen(@NotNull Capability<C> cap, @NotNull C result);
	
	default @NotNull <C> ICapabilityProviderChainBuilder<T> elifEmptyReturns(@NotNull ICapabilityProvider result)
	{
		return this.elifEmptyReturns(IInvalidatableCapabilityProvider.of(result::getCapability));
	} // end elifEmptyReturns()

	@NotNull <C> ICapabilityProviderChainBuilder<T> elifEmptyReturns(@NotNull IInvalidatableCapabilityProvider result);

} // end interface