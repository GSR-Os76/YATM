package com.gsr.gsr_yatm.utilities.capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityUtil
{
	public static final ICapabilityProvider EMPTY_PROVIDER = new ICapabilityProvider()
	{

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
		{
			return LazyOptional.empty();
		} // end getCapability()

	};
		
} // end class