package com.gsr.gsr_yatm.utilities.network.container_data.implementation;

import com.gsr.gsr_yatm.utilities.network.container_data.AccessSpecification;

import net.minecraft.world.inventory.ContainerData;

public class SubsettingContainerData implements ContainerData
{
	private final AccessSpecification m_accessSpec;
	private final ContainerData m_of;
	
	
	
	public SubsettingContainerData(AccessSpecification accessSpec, ContainerData of) 
	{
		this.m_accessSpec = accessSpec;
		this.m_of = of;
	} // end constructor
	
	
	
	@Override
	public int get(int index)
	{
		return this.m_of.get(index + this.m_accessSpec.startIndex());
	} // end get()

	@Override
	public void set(int index, int value)
	{
		this.m_of.set(index + this.m_accessSpec.startIndex(), value);
	} // end set()

	@Override
	public int getCount()
	{
		return (this.m_accessSpec.endIndex() - this.m_accessSpec.startIndex()) + 1;
	} // end getCount()
	
} // end class