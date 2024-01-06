package com.gsr.gsr_yatm.utilities.network.container_data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.joml.Math;

import com.google.common.collect.ImmutableMap;

public class CompositeAccessSpecification implements ICompositeAccessSpecification
{
	private final int m_count;
	private final Map<String, AccessSpecification> m_lookup;
	
	
	public CompositeAccessSpecification(@NotNull List<Map.Entry<String, AccessSpecification>> entries) 
	{
		ImmutableMap.Builder<String, AccessSpecification> builder = new ImmutableMap.Builder<String, AccessSpecification>();
		int max = 0;
		for(Map.Entry<String, AccessSpecification> m : entries) 
		{
			builder.put(m);
			max = Math.max(max, m.getValue().startIndex());
			max = Math.max(max, m.getValue().endIndex());

		}		
		this.m_count = max + 1;
		this.m_lookup = builder.build();
	} // end constructor
	
	public static CompositeAccessSpecification of(@NotNull List<Map.Entry<String, Integer>> entries) 
	{
		ArrayList<Map.Entry<String, AccessSpecification>> c = new ArrayList<>();
		int index = 0;
		for(Map.Entry<String, Integer> m : entries) 
		{
			int oldInd = index;
			index += m.getValue();
			c.add(Map.entry(m.getKey(), new AccessSpecification(oldInd, index - 1)));
		}		
		return new CompositeAccessSpecification(c);
	} // end of()
	
	
	
	@Override
	public AccessSpecification get(String key) 
	{
		return this.m_lookup.get(key);
	} // end get()

	@Override
	public int getCount()
	{
		return this.m_count;
	} // end getCount()
	
} // end class