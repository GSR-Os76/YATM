package com.gsr.gsr_yatm.utilities.network;

public interface ICompositeAccessSpecification
{
	public AccessSpecification get(String key);
	
	public int getCount();
	
} // end interface