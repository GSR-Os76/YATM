package com.gsr.gsr_yatm.block.device.builder.capability_provider.option;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class CapabiltyChain implements IInvalidatableCapabilityProvider
{
	private static final @NotNull List<Tuple2<Capability<?>, ?>> TEMPORARY_LIST = Lists.newArrayList();
	
	private final @NotNull List<Tuple2<Capability<?>, ?>> m_chain;
	private final @NotNull Map<Capability<?>, LazyOptional<?>> m_chainOptionals = Maps.newHashMap();
	private final @Nullable IInvalidatableCapabilityProvider m_lastly;
	private boolean m_invalidated = false;
	
	

	public CapabiltyChain(@NotNull List<Tuple2<Capability<?>, ?>> chain, @Nullable IInvalidatableCapabilityProvider lastly)
	{
		for(Tuple2<Capability<?>, ?> t : chain) 
		{
			if(!CapabiltyChain.TEMPORARY_LIST.stream().anyMatch((t2) -> t2.a() == Objects.requireNonNull(t).a())) 
			{
				CapabiltyChain.TEMPORARY_LIST.add(t);
			}
		}
		this.m_chain = ImmutableList.copyOf(CapabiltyChain.TEMPORARY_LIST);
		CapabiltyChain.TEMPORARY_LIST.clear();
		this.m_lastly = lastly;
	} // end constructor()
	
	

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		LazyOptional<T> r = this.getLazyOptional(cap);
		return r.isPresent() || this.m_lastly == null ? r : this.m_lastly.getCapability(cap, side);
	} // end getCapability()

	@Override
	public void invalidateCaps()
	{
		this.m_invalidated = true;
		this.m_chainOptionals.forEach((c, l) -> l.invalidate());
		if(this.m_lastly != null) 
		{
			this.m_lastly.invalidateCaps();
		}
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		this.m_invalidated = false;
		this.m_chainOptionals.clear();
		if(this.m_lastly != null) 
		{
			this.m_lastly.reviveCaps();
		}
	} // end reviveCaps()

	
	
	public <T> @NotNull LazyOptional<T> getLazyOptional(@NotNull Capability<T> cap)
	{
		LazyOptional<?> l = this.m_chainOptionals.get(cap);
		if(l == null) 
		{
			Tuple2<Capability<?>, ?> m = this.m_chain.stream().filter((t) -> t.a() == cap).findFirst().orElse(null);
			if(m != null) 
			{
				l = LazyOptional.of(() -> m.b());
				if(this.m_invalidated) 
				{
					l.invalidate();
				}
				this.m_chainOptionals.put(cap, l);
			}
		}
		return l == null ? LazyOptional.empty() : l.cast();
	} // end getLazyOptional()

} // end class