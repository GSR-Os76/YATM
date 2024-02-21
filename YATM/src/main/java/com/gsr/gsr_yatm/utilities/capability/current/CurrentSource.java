package com.gsr.gsr_yatm.utilities.capability.current;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;

public class CurrentSource implements ICurrentHandler
{
	private int m_output;
	
	
	
	public CurrentSource() 
	{
		this(0);
	} // end currentSource()
	
	public CurrentSource(int output) 
	{
		this.m_output = output;
	} // end currentSource()
	
	
	
	public void setOutput(int output) 
	{
		this.m_output = output;
	} // end setOutput()
	
	
	
	@Override
	public int receiveCurrent(int amount, boolean simulate)
	{
		return 0;
	} // end recieveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		return Math.min(amount, this.m_output);
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return this.m_output;
	} // end capacity

	@Override
	public int stored()
	{
		return this.m_output;
	} // end stored()

} // end class