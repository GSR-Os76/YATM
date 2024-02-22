package com.gsr.gsr_yatm.item.armor;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class PoweredArmorItemStack implements ICapabilityProvider, ICurrentHandler
{
	private final @NotNull LazyOptional<?> m_cap = LazyOptional.of(() -> this);
	private final @NotNull ItemStack m_self;
	private final @NotNegative int m_cuPerDurability;
	
	
	
	public PoweredArmorItemStack(@NotNull ItemStack self, @NotNegative int cuPerDurability) 
	{
		this.m_self = Objects.requireNonNull(self);
		this.m_cuPerDurability = Contract.notNegative(cuPerDurability);
	} // end constructor()
	
	
	
	@Override
	public @NotNegative int receiveCurrent(@NotNegative int amount, boolean simulate)
	{
		int rV = Math.min(this.m_self.getDamageValue(), amount / this.m_cuPerDurability);
		int cV = amount * this.m_cuPerDurability;
		if(!simulate) 
		{
			this.m_self.setDamageValue(this.m_self.getDamageValue() - rV);
		}
		return cV;
	} // end recieveCurrent()

	@Override
	public @NotNegative int extractCurrent(@NotNegative int amount, boolean simulate)
	{
		return 0;
	} // end extractCurrent()

	@Override
	public @NotNegative int capacity()
	{
		return (this.m_self.getMaxDamage() - 1)  * this.m_cuPerDurability;
	} // end capacity()

	@Override
	public @NotNegative int stored()
	{
		return (this.m_self.getMaxDamage() - 1 - this.m_self.getDamageValue()) * this.m_cuPerDurability;
	} // end stored()

	
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == YATMCapabilities.CURRENT) 
		{
			return this.m_cap.cast();
		}
		return LazyOptional.empty();
	} // end getCapability()

} // end class