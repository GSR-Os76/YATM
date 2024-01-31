package com.gsr.gsr_yatm.utilities.capability;

import java.util.function.BiFunction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;
import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;

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

	public static <C> @NotNull ICapabilityProvider whenOrDefault(Capability<C> capability, C c, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		return new IInvalidatableCapabilityProvider()
		{
			private @NotNull LazyOptional<C> cL = LazyOptional.of(() -> c);
			@Override
			public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
			{
				if(cap == capability) 
				{
					this.cL.cast();
				}
				return defaultCapabilityProvider.getCapability(cap);
			} // end getCapability()

			@Override
			public void invalidateCaps()
			{
				cL.invalidate();
			} // end invalidateCaps()

			@Override
			public void reviveCaps()
			{
				cL = LazyOptional.of(() -> c);
			} // end reviveCaps()
		};
	} // end capability
		
} // end class