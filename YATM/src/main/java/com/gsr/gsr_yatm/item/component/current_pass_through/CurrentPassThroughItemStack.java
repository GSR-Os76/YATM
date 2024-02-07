package com.gsr.gsr_yatm.item.component.current_pass_through;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IComponent;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CurrentPassThroughItemStack implements ICapabilityProvider, IComponent, ICurrentHandler
{
	private final @NotNull LazyOptional<?> m_thisCap = LazyOptional.of(() -> this);
	
	private LazyOptional<ICurrentHandler> m_attachedCap;
	private ICurrentHandler m_attachment;

	
	
	@Override
	public @NotNegative int recieveCurrent(@NotNegative int amount, boolean simulate)
	{
		return this.m_attachment == null ? 0 : this.m_attachment.recieveCurrent(amount, simulate);
	} // end recieveCurrent()

	@Override
	public @NotNegative int extractCurrent(@NotNegative int amount, boolean simulate)
	{
		return this.m_attachment == null ? 0 : this.m_attachment.extractCurrent(amount, simulate);
	} // end extractCurrent()

	@Override
	public @NotNegative int capacity()
	{
		return this.m_attachment == null ? 0 : this.m_attachment.capacity();
	} // end capacity()

	@Override
	public @NotNegative int stored()
	{
		return this.m_attachment == null ? 0 : this.m_attachment.stored();
	} // end stored()
	


	@Override
	public <T> void attachRecievingCapability(@NotNull Capability<T> capType, @NotNull LazyOptional<T> cap)
	{
		if(!this.getValidCapabilities().contains(capType)) 
		{
			throw new IllegalArgumentException("The capability type " + capType + " is not valid for this.");
		}
		
		LazyOptional<ICurrentHandler> c = cap.cast();
		if (c.isPresent())
		{
			this.m_attachedCap = c;
			this.m_attachment = c.orElse(null);
			c.addListener(this::removeRecievingCapability);
		}
	} // end attachRecievingCapability()

	@Override
	public void removeRecievingCapability(@NotNull LazyOptional<?> cap)
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
		return List.of(YATMCapabilities.CURRENT);
	} // end getValidCapabilities()
	
	

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == YATMCapabilities.CURRENT || cap == YATMCapabilities.COMPONENT)
		{
			return this.m_thisCap.cast();
		}
		return LazyOptional.empty();
	} // end getCapabilitiy()

} // end class