package com.gsr.gsr_yatm.utilities.network;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.world.inventory.ContainerData;

public class PropertyContainerData implements ContainerData
{
	private final List<Property<Integer>> m_properties;
	
	
	
	public PropertyContainerData(List<Property<Integer>> properties) 
	{
		this.m_properties = ImmutableList.copyOf(properties);
	} // end constructor
	
	
	
	@Override
	public int get(int index)
	{
		return this.m_properties.get(index).get();
	} // end get()

	@Override
	public void set(int index, int value)
	{
		this.m_properties.get(index).set(value);
	} // end set()

	@Override
	public int getCount()
	{
		return this.m_properties.size();
	} // end getCount()

} // end class