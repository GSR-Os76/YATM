package com.gsr.gsr_yatm.utilities;

import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

public class PeriodTracker
{
	private final @NotNegative int m_period;
	private @NotNegative int m_counter;
	
	
	
	public PeriodTracker(@NotNegative int period)
	{
		this.m_period = Contract.notNegative(period);
		this.m_counter = this.m_period;
	} // end constructor
	
	public PeriodTracker(@NotNegative int period, @NotNegative int initialCount)
	{
		this.m_period = Contract.notNegative(period);
		this.m_counter =  Contract.notNegative(initialCount);
	} // end constructor
	
	
	
	public void complete()
	{
		this.m_counter = this.m_period;
	}
	
	public void reset()
	{
		this.m_counter = 0;
	} // end reset()
	
	/**Returns true if the period cycled over.*/
	public boolean tick() 
	{
		if(++this.m_counter >= this.m_period) 
		{
			// p5
			// 5t0t1t2t3t4t0t1t2t3t4t0
			this.m_counter = 0;
			return true;
		}
		return false;
	} // end tick()

} // end class