package com.gsr.gsr_yatm.utilities.network.container_data;

public interface ICompositeAccessSpecification
{
	public AccessSpecification get(String key);
	
	public int getCount();
	
} // end interface