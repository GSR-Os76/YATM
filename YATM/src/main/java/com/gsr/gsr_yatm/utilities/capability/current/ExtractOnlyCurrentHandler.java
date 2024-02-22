package com.gsr.gsr_yatm.utilities.capability.current;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;

public class ExtractOnlyCurrentHandler implements ICurrentHandler
{
	private final ICurrentHandler m_wrapped;
	
	
	
	public ExtractOnlyCurrentHandler(ICurrentHandler wrapped) 
	{
		this.m_wrapped = wrapped;
	} // end constructor



	@Override
	public int receiveCurrent(int amount, boolean simulate)
	{
		return 0;
	} // end recieveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		return this.m_wrapped.extractCurrent(amount, simulate);
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return this.m_wrapped.capacity();
	} // end capacity()

	@Override
	public int stored()
	{
		return this.m_wrapped.stored();
	} // end stored()
	
} // end class