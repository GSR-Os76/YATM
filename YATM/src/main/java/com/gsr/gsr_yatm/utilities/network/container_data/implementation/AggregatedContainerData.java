package com.gsr.gsr_yatm.utilities.network.container_data.implementation;

import java.util.List;

import net.minecraft.world.inventory.ContainerData;

public class AggregatedContainerData implements ContainerData
{
	private final ContainerData[] m_subContainers;
	
	
	
	public AggregatedContainerData(List<ContainerData> subContainers) 
	{
		this.m_subContainers = subContainers.toArray(new ContainerData[subContainers.size()]);
	} // end constructor
	
	
	
	@Override
	public int get(int index)
	{
		int pastCount = 0;
		for(ContainerData cd : this.m_subContainers) 
		{
			if(index >= pastCount && index < pastCount + cd.getCount()) 
			{
				return cd.get(index - pastCount);
			}
			pastCount += cd.getCount();
		}
		throw new IllegalArgumentException("Unexpected value of: " + index);
	} // end get()

	@Override
	public void set(int index, int value)
	{
		int pastCount = 0;
		for(ContainerData cd : this.m_subContainers) 
		{
			if(index >= pastCount && index < pastCount + cd.getCount()) 
			{
				cd.set(index - pastCount, value);
			}
			pastCount += cd.getCount();
		}
		// TODO, consider, throw new IllegalArgumentException("Unexpected value of: " + index);
	} // end set()

	@Override
	public int getCount()
	{
		int count = 0;
		for(ContainerData cd : this.m_subContainers) 
		{
			count += cd.getCount();
		}
		return count;
	} // end getCount()

} // end class