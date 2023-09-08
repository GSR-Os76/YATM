package com.gsr.gsr_yatm.utilities.network;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.Event;

public class SimpleEventContainerData implements IEventContainerData
{
	private final int[] m_values;
	private final List<Event<Integer>> m_events;
	
	
	
	public SimpleEventContainerData(int slotCount)
	{
		this.m_values = new int[Contract.NotNegative(slotCount)];
		ImmutableList.Builder<Event<Integer>> b = new ImmutableList.Builder<>();
		for (int i = 0; i < Contract.NotNegative(slotCount); i++)
		{
			b.add(new Event<>());
		}
		this.m_events = b.build();
	} // end constructor



	@Override
	public int get(@NotNegative int index)
	{
		return this.m_values[index];
	} // end get()

	@Override
	public void set(@NotNegative int index, int value)
	{
		if(value != this.m_values[index]) 
		{
			this.m_values[index] = value;
			this.m_events.get(index).accept(value);	
		}		
	} // end set()

	@Override
	public @NotNegative int getCount()
	{
		return this.m_values.length;
	} // end getCount()
	
	
	@Override
	public void subscribe(@NotNegative int index, @NotNull Consumer<Integer> subscriber) 
	{
		this.m_events.get(index).subscribe(Objects.requireNonNull(subscriber));
	} // end subscribe()
	
	@Override
	public void unsubscribe(@NotNegative int index, @NotNull Consumer<Integer> subscriber) 
	{
		this.m_events.get(index).unsubscribe(Objects.requireNonNull(subscriber));
	} // end unsubcribe()
		
} // end SimpleEventContainerData