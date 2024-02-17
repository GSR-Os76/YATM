package com.gsr.gsr_yatm.utilities.capability;

import java.util.function.BiFunction;
import java.util.function.Supplier;

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

	
	
	public static <X> @NotNull IInvalidatableCapabilityProvider providerOrCapabilty(@NotNull Supplier<Boolean> condition, @NotNull IInvalidatableCapabilityProvider provider, @NotNull Capability<X> capability, @NotNull X x)
	{
		return new IInvalidatableCapabilityProvider() 
		{
			LazyOptional<X> l = LazyOptional.of(() -> x);
			
			@Override
			public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
			{
				if(condition.get()) 
				{
					return provider.getCapability(cap, side);
				}
				else if (capability == cap) 
				{
					return l.cast();
				}
				return LazyOptional.empty();
			} // end getCapability()

			@Override
			public void invalidateCaps()
			{
				provider.invalidateCaps();
				l.invalidate();
			} // end invalidateCaps()()

			@Override
			public void reviveCaps()
			{
				provider.reviveCaps();
				l = LazyOptional.of(() -> x);
			} // end reviveCaps()
			
		};
	} // end providerOrCapability()



	public static <X> @NotNull IInvalidatableCapabilityProvider providerOrCapabiltyOrDefault(@NotNull Supplier<Boolean> condition, @NotNull IInvalidatableCapabilityProvider provider, @NotNull Capability<X> capability, @NotNull X x, @NotNull ICapabilityProvider last)
	{
		return new IInvalidatableCapabilityProvider() 
		{
			LazyOptional<X> l = LazyOptional.of(() -> x);
			
			@Override
			public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
			{
				if(condition.get()) 
				{
					return provider.getCapability(cap, side);
				}
				else if (capability == cap) 
				{
					return l.cast();
				}
				return last.getCapability(cap);
			} // end getCapability()

			@Override
			public void invalidateCaps()
			{
				provider.invalidateCaps();
				l.invalidate();
			} // end invalidateCaps()()

			@Override
			public void reviveCaps()
			{
				provider.reviveCaps();
				l = LazyOptional.of(() -> x);
			} // end reviveCaps()
			
		};
	} // end providerOrCapability()
		
} // end class