package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import java.util.List;
import java.util.Objects;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class EmptyChain implements IInvalidatableCapabilityProvider
{
	private static final @NotNull List<Tuple2<Capability<?>, ?>> TEMPORARY_LIST = Lists.newArrayList();
	
	private final @NotNull List<IInvalidatableCapabilityProvider> m_chain;
	private final @Nullable IInvalidatableCapabilityProvider m_lastly;
	
	

	public EmptyChain(@NotNull List<IInvalidatableCapabilityProvider> chain, @NotNull IInvalidatableCapabilityProvider lastly)
	{
		this.m_chain = ImmutableList.copyOf(Objects.requireNonNull(chain));
		this.m_lastly = lastly;
	} // end constructor



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		for(IInvalidatableCapabilityProvider c : this.m_chain) 
		{
			LazyOptional<T> l = c.getCapability(cap, side);
			if(l.isPresent()) 
			{
				return l;
			}
		}
		return this.m_lastly == null ? LazyOptional.empty() : this.m_lastly.getCapability(cap, side);
	} // end getCapability

	@Override
	public void invalidateCaps()
	{
		this.m_chain.forEach(c -> c.invalidateCaps());
		if(this.m_lastly != null) 
		{
			this.m_lastly.invalidateCaps();
		}
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		this.m_chain.forEach(c -> c.reviveCaps());
		if(this.m_lastly != null) 
		{
			this.m_lastly.reviveCaps();
		}
	} // end reviveCaps()

} // end class