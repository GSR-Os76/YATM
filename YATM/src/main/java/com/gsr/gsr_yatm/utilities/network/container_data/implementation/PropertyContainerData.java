package com.gsr.gsr_yatm.utilities.network.container_data.implementation;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.utilities.generic.Property;

import net.minecraft.world.inventory.ContainerData;

public class PropertyContainerData implements ContainerData
{
	public static final int LENGTH_PER_PROPERTY = 1;
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
		return this.m_properties.size() * PropertyContainerData.LENGTH_PER_PROPERTY;
	} // end getCount()

} // end class