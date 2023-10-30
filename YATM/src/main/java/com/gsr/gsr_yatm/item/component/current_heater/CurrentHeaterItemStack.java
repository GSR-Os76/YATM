package com.gsr.gsr_yatm.item.component.current_heater;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CurrentHeaterItemStack implements ICapabilityProvider,  ICurrentHandler
{
	private final @NotNull LazyOptional<ICurrentHandler> m_thisCap = LazyOptional.of(() -> this);
	private final @NotNegative int m_maxTemperature; 
	private final float m_kelvinPerCurrent;
		
	private LazyOptional<IHeatHandler> m_attachedCap;
	private IHeatHandler m_attachment;

	
	
	public CurrentHeaterItemStack(@NotNegative int maxTemperature, float kelvinPerCurrent)
	{
		this.m_maxTemperature = Contract.NotNegative(maxTemperature);
		this.m_kelvinPerCurrent = kelvinPerCurrent;
	} // end constructor 
	
	
	
	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
		if(this.m_attachment == null) 
		{
			return 0;
		}
		
		int maxUsable = Math.max(this.getMaxUsableCurrent(), amount);
		if(!simulate) 
		{
			this.m_attachment.heat(Math.max(0, Math.min(this.m_maxTemperature, (int)(maxUsable * this.m_kelvinPerCurrent))));
		}
		return maxUsable;
	} // end recieveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		return 0;
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return 0;
	} // end capacity()

	@Override
	public int stored()
	{
		return 0;
	} // end stored()

	public int getMaxUsableCurrent() 
	{
		if(this.m_attachedCap == null || this.m_kelvinPerCurrent == 0) 
		{
			return 0;
		}
		
		return (int)Math.ceil(
				(
						(this.m_kelvinPerCurrent > 0f ? this.m_maxTemperature : 0) 
						- this.m_attachment.getTemperature()
				) 
				/ this.m_kelvinPerCurrent);
	} // end getMaxUsableCurrent
	
	
	
	public <T> void attach(@NotNull LazyOptional<IHeatHandler> cap) 
	{
		if(cap.isPresent()) 
		{
			cap.addListener((la) -> CurrentHeaterItemStack.this.tryRemove(la));
			this.m_attachedCap = cap;
			this.m_attachment = cap.orElse(null);
		}
	} // end attach()
	
	public void tryRemove(@NotNull LazyOptional<?> cap)
	{
		if(cap == this.m_attachedCap) 
		{
			this.m_attachedCap = null;
			this.m_attachment = null;
		}
	} // end tryRemove()
	
	
	



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == YATMCapabilities.CURRENT) 
		{
			return this.m_thisCap.cast();
		}
		return LazyOptional.empty();
	} // end getCapabilitiy()
	
} // end class