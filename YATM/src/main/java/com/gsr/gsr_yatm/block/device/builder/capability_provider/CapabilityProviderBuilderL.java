package com.gsr.gsr_yatm.block.device.builder.capability_provider;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

public class CapabilityProviderBuilderL extends CapabilityProviderBuilder<IInvalidatableCapabilityProvider>
{
	private static Consumer<IInvalidatableCapabilityProvider> s_voider = (i) -> {};
	
	
	
	public CapabilityProviderBuilderL()
	{
		super(null, s_voider);
	} // end constructor



	@Override
	public @Nullable IInvalidatableCapabilityProvider end()
	{
		return this.build();
	} // end end()
	
} // end class