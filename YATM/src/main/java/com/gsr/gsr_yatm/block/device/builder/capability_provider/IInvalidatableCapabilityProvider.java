package com.gsr.gsr_yatm.block.device.builder.capability_provider;

import java.util.function.BiFunction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public interface IInvalidatableCapabilityProvider extends ICapabilityProvider
{
	void invalidateCaps();
	
	void reviveCaps();

	
	
	public static @NotNull IInvalidatableCapabilityProvider of(@NotNull BiFunction<@NotNull Capability<?>, @Nullable Direction, @NotNull LazyOptional<?>> getCapability, @NotNull Runnable invalidateCaps, @NotNull Runnable reviveCaps)
	{
		return new IInvalidatableCapabilityProvider() 
		{
			@Override
			public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
			{
				return getCapability.apply(cap, side).cast();
			} // end getCapability()

			@Override
			public void invalidateCaps()
			{
				invalidateCaps.run();
			} // end invalidateCaps()

			@Override
			public void reviveCaps()
			{
				reviveCaps.run();
			} // end reviveCaps()
		}; 
	} // end of()
	
} // end interface