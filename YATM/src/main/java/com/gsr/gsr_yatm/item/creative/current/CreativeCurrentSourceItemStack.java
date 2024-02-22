package com.gsr.gsr_yatm.item.creative.current;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class CreativeCurrentSourceItemStack implements ICapabilityProvider, ICurrentHandler
{
	private final @NotNull LazyOptional<ICurrentHandler> m_self = LazyOptional.of(() -> this);

	
	
	@Override
	public @NotNegative int receiveCurrent(@NotNegative int amount, boolean simulate)
	{
		return 0;
	} // end recieveCurrent()

	@Override
	public @NotNegative int extractCurrent(@NotNegative int amount, boolean simulate)
	{
		return amount;
	} // end extractCurrent()

	@Override
	public @NotNegative int capacity()
	{
		return Integer.MAX_VALUE;
	} // end capacity()

	@Override
	public @NotNegative int stored()
	{
		return Integer.MAX_VALUE;
	} // end stored()
	
	

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == YATMCapabilities.CURRENT)
		{
			return this.m_self.cast();
		}
		return LazyOptional.empty();
	} // end getCapability()

} // end class