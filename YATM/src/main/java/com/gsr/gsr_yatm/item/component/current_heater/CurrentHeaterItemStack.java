package com.gsr.gsr_yatm.item.component.current_heater;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IComponent;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CurrentHeaterItemStack implements ICapabilityProvider, IComponent, ICurrentHandler
{
	private final @NotNull LazyOptional<?> m_thisCap = LazyOptional.of(() -> this);
	private final @NotNegative int m_maxTemperature; 
	private final float m_kelvinPerCurrent;
		
	private @Nullable LazyOptional<IHeatHandler> m_attachedCap;
	private @Nullable IHeatHandler m_attachment;

	
	
	public CurrentHeaterItemStack(@NotNegative int maxTemperature, float kelvinPerCurrent)
	{
		this.m_maxTemperature = Contract.notNegative(maxTemperature);
		this.m_kelvinPerCurrent = kelvinPerCurrent;		
	} // end constructor 
	
	
	
	@Override
	public int receiveCurrent(int amount, boolean simulate)
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
	
	
	
	@Override
	public <T> void attachReceivingCapability(@NotNull Capability<T> capType, @NotNull LazyOptional<T> cap)
	{
		if(!this.getValidCapabilities().contains(capType)) 
		{
			throw new IllegalArgumentException("The capability type " + capType + " is not valid for this.");
		}
		
		LazyOptional<IHeatHandler> c = cap.cast();
		if(c.isPresent()) 
		{
			this.m_attachedCap = c;
			this.m_attachment = c.orElse(null);
			c.addListener(this::removeReceivingCapability);
		}
	} // end attachRecievingCapability()

	@Override
	public void removeReceivingCapability(@NotNull LazyOptional<?> cap)
	{
		if(cap == this.m_attachedCap) 
		{
			this.m_attachedCap = null;
			this.m_attachment = null;
		}
	} // end removeRecievingCapability()

	@Override
	public @NotNull List<Capability<?>> getValidCapabilities()
	{
		return List.of(YATMCapabilities.HEAT);
	} // end getValidCapabilities()

	

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == YATMCapabilities.CURRENT || cap == YATMCapabilities.COMPONENT) 
		{
			return this.m_thisCap.cast();
		}
		return LazyOptional.empty();
	} // end getCapabilitiy()
	
} // end class