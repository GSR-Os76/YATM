package com.gsr.gsr_yatm.utilities.capability;

import java.util.function.BiFunction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityUtil
{
	public static final IInvalidatableCapabilityProvider EMPTY_PROVIDER = new IInvalidatableCapabilityProvider()
	{

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
		{
			return LazyOptional.empty();
		} // end getCapability()

		@Override
		public void invalidateCaps() { } // end invalidateCaps()

		@Override
		public void reviveCaps() { } // end reviveCaps()
	};

	public static @NotNull ICapabilityProvider of(@NotNull BiFunction<@NotNull Capability<?>, @Nullable Direction, @NotNull LazyOptional<?>> getCapability)
	{
		return new ICapabilityProvider()
		{
			@Override
			public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
			{
				return getCapability.apply(cap, side).cast();
			} // end getCapability()
		};
	} // end of()
		
} // end class