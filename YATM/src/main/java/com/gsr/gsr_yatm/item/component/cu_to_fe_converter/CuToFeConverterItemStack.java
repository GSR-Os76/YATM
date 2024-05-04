package com.gsr.gsr_yatm.item.component.cu_to_fe_converter;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IComponent;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class CuToFeConverterItemStack implements ICapabilityProvider, IComponent, ICurrentHandler
{
	private final @NotNull LazyOptional<?> m_thisCap = LazyOptional.of(() -> this);
	private final float m_cuToFeRatio;
	private final float m_feToCuRatio;
	
	private @Nullable LazyOptional<IEnergyStorage> m_attachedCap;
	private @Nullable IEnergyStorage m_attachment;
	private float m_rCuPart = 0f;
	private float m_eCuPart = 0f;


	
	public CuToFeConverterItemStack(@NotNull ItemStack self, float cuToFeRatio)
	{
		this.m_cuToFeRatio = cuToFeRatio;
		this.m_feToCuRatio = cuToFeRatio == 0f ? 0f : 1f / cuToFeRatio;
	} // end constructor

	

	@Override
	public int receiveCurrent(int amount, boolean simulate)
	{
		if(this.m_attachment == null) 
		{
			return 0;
		}
		else 
		{
			int consumedFe = this.m_attachment.receiveEnergy(this.toFe(amount), simulate);
			
			float consumedCuRough = ((consumedFe) * this.m_feToCuRatio) + this.m_rCuPart;
			this.m_rCuPart = consumedCuRough % 1f;
			int consumedCu = (int)consumedCuRough;
			if(consumedCu > amount) 
			{
				this.m_rCuPart += consumedCu - amount;
				return amount;
			}
			return consumedCu;
		}
	} // end receiveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		if(this.m_attachment == null) 
		{
			return 0;
		}
		else 
		{
			int gainedFe = this.m_attachment.extractEnergy(this.toFe(amount), simulate);
			
			float gainedCuRough = ((gainedFe) * this.m_feToCuRatio) + this.m_eCuPart;
			this.m_eCuPart = gainedCuRough % 1f;
			int gainedCu = (int)gainedCuRough;
			if(gainedCu > amount) 
			{
				this.m_eCuPart += gainedCu - amount;
				return amount;
			}
			return gainedCu;
		}
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return this.m_attachment == null 
				? 0 
				: this.toCu(this.m_attachment.getMaxEnergyStored());
	} // end capacity()

	@Override
	public int stored()
	{
		return this.m_attachment == null 
				? 0 
				: this.toCu(this.m_attachment.getEnergyStored());
	} // end stored()


	
	@Override
	public <T> void attachReceivingCapability(@NotNull Capability<T> capType, @NotNull LazyOptional<T> cap)
	{
		if (!this.getValidCapabilities().contains(capType))
		{
			throw new IllegalArgumentException("The capability type " + capType + " is not valid for this.");
		}

		LazyOptional<IEnergyStorage> l = cap.cast();
		if (l.isPresent())
		{
			this.m_attachedCap = l;
			this.m_attachment = l.orElse(null);
			l.addListener(this::removeReceivingCapability);
		}
	} // end attachRecievingCapability()

	@Override
	public void removeReceivingCapability(@NotNull LazyOptional<?> cap)
	{
		if (cap == this.m_attachedCap)
		{
			this.m_attachedCap = null;
			this.m_attachment = null;
		}
	} // end removeRecievingCapability()

	@Override
	public @NotNull List<Capability<?>> getValidCapabilities()
	{
		return List.of(ForgeCapabilities.ENERGY);
	} // end getValidCapabilities()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == YATMCapabilities.COMPONENT || cap == YATMCapabilities.CURRENT)
		{
			return this.m_thisCap.cast();
		}
		return LazyOptional.empty();
	} // end getCapabilitiy()

	
	
	private int toFe(int cu) 
	{
		return (int)(((float)cu) * this.m_cuToFeRatio);
	} // end toFe()
	
	private int toCu(int fe) 
	{
		return (int)(((float)fe) * this.m_feToCuRatio);
	} // end toCu()
	
} // end class