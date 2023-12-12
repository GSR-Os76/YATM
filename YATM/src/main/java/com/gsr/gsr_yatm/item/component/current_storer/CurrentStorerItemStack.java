package com.gsr.gsr_yatm.item.component.current_storer;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IComponent;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandlerWrapper;
import com.gsr.gsr_yatm.utilities.capability.current.ItemStackCurrentHandler;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CurrentStorerItemStack implements ICapabilityProvider, IComponent, ICurrentHandler
{
	private final @NotNull ItemStack m_self;
	private final @NotNull LazyOptional<?> m_thisCap = LazyOptional.of(() -> this);
	private final @NotNegative int m_capacity;
	private final @NotNull ICurrentHandler m_inner;
		
	private @Nullable LazyOptional<ICurrentHandler> m_attachedCap;
	private @Nullable ICurrentHandler m_attachment;

	
	
	public CurrentStorerItemStack(@NotNull ItemStack self, @NotNegative int capacity)
	{
		this.m_self = Objects.requireNonNull(self);
		this.m_capacity = Contract.notNegative(capacity);	
		
		this.m_inner = CurrentHandlerWrapper.Builder.of(new ItemStackCurrentHandler(this.m_self, this.m_capacity))
				.onCurrentExtracted((i) -> this.updateDamage())
				.onCurrentRecieved((i) -> this.updateDamage()).build();
	} // end constructor 
	
	private void updateDamage() 
	{
		this.m_self.setDamageValue(
				Math.min(this.m_inner.capacity(), 
						Math.max(1, 
								(this.m_inner.capacity() + 1) - this.m_inner.stored()
								)
						)
				);
	} // end updateDamage()
	
	
	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
		int toInner = amount;
		int fromC = 0;
		if(this.m_attachment != null) 
		{
			fromC = this.m_attachment.recieveCurrent(amount, simulate);
			toInner = amount - fromC;
		}
		return this.m_inner.recieveCurrent(toInner, simulate) + fromC;
	} // end recieveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		int taken = this.m_inner.extractCurrent(amount, simulate);
		if(this.m_attachment != null && taken != amount) 
		{
			taken += this.m_attachment.extractCurrent(amount - taken, simulate);
		}
		return taken;
	} // end extractCurrent()
	@Override
	public int capacity()
	{
		return this.m_inner.capacity();
	} // end capacity()

	@Override
	public int stored()
	{
		return this.m_inner.stored();
	} // end stored()
	
	
	
	@Override
	public <T> void attachRecievingCapability(@NotNull Capability<T> capType, @NotNull LazyOptional<T> cap)
	{
		if(!this.getValidCapabilities().contains(capType)) 
		{
			throw new IllegalArgumentException("The capability type " + capType + " is not valid for this.");
		}
		
		LazyOptional<ICurrentHandler> c = cap.cast();
		if(c.isPresent()) 
		{
			this.m_attachedCap = c;
			this.m_attachment = c.orElse(null);
			c.addListener(this::removeRecievingCapability);
		}
	} // end attachRecievingCapability()

	@Override
	public void removeRecievingCapability(@NotNull LazyOptional<?> cap)
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
		return List.of(YATMCapabilities.CURRENT);
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