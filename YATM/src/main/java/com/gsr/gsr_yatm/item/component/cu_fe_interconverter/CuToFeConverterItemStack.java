package com.gsr.gsr_yatm.item.component.cu_fe_interconverter;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IComponent;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

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



	public CuToFeConverterItemStack(@NotNull ItemStack self, @NotNegative float cuToFeRatio)
	{
		this.m_cuToFeRatio = cuToFeRatio;
		this.m_feToCuRatio = cuToFeRatio == 0 ? 0 : 1f / cuToFeRatio;
	} // end constructor


	@Override
	public int receiveCurrent(int amount, boolean simulate)
	{
		return this.m_attachment == null ? 0 :this.m_attachment.receiveEnergy((int)(((float)amount) * this.m_cuToFeRatio), simulate);
	} // end receiveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		return this.m_attachment == null ? 0 : this.m_attachment.extractEnergy((int)(((float)amount) * this.m_cuToFeRatio), simulate);
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return this.m_attachment == null ? 0 : (int)(((float)this.m_attachment.getMaxEnergyStored()) * this.m_feToCuRatio);
	} // end capacity()

	@Override
	public int stored()
	{
		return this.m_attachment == null ? 0 : (int)(((float)this.m_attachment.getEnergyStored()) * this.m_feToCuRatio);
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
		if (cap == YATMCapabilities.COMPONENT || cap == YATMCapabilities.CURRENT || cap == ForgeCapabilities.ENERGY)
		{
			return this.m_thisCap.cast();
		}
		return LazyOptional.empty();
	} // end getCapabilitiy()

} // end class