package com.gsr.gsr_yatm.item.component.current_heater;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.item.component.IComponent;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CurrentHeaterItem extends Item implements IComponent
{
	private final @NotNegative int m_maxTemperature; 
	private final float m_kelvinPerCurrent;
	
	
	
	public CurrentHeaterItem(@NotNull Properties properties, @NotNegative int maxTemperature, float kelvinPerCurrent)
	{
		super(Objects.requireNonNull(properties));
		this.m_maxTemperature = Contract.NotNegative(maxTemperature);
		this.m_kelvinPerCurrent = kelvinPerCurrent;
	} // end constructor
	
	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new CurrentHeaterItemStack(this.m_maxTemperature, this.m_kelvinPerCurrent);
	} // end initCapabilities()

	
	
	@Override
	public <T> void attachRecievingCapability(@NotNull ItemStack itemStack, @NotNull Capability<T> capType, @NotNull LazyOptional<T> cap)
	{
		if(!this.getValidCapabilities().contains(capType)) 
		{
			throw new IllegalArgumentException("The capability type " + capType + " is not valid for this.");
		}
		
		LazyOptional<ICurrentHandler> t = itemStack.getCapability(YATMCapabilities.CURRENT);
		if(t.isPresent()) 
		{
			((CurrentHeaterItemStack)t.orElse(null)).attach(cap.cast());
		}
	} // end attachRecievingCapability()

	@Override
	public <T> void removeRecievingCapability(@NotNull ItemStack itemStack, @NotNull LazyOptional<T> cap)
	{
		LazyOptional<ICurrentHandler> t = itemStack.getCapability(YATMCapabilities.CURRENT);
		if(t.isPresent()) 
		{
			((CurrentHeaterItemStack)t.orElse(null)).tryRemove(cap);
		}
	} // end removeRecievingCapability()

	@Override
	public @NotNull List<Capability<?>> getValidCapabilities()
	{
		return List.of(YATMCapabilities.HEAT);
	} // end getValidCapabilities()

} // end class