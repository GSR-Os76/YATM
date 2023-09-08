package com.gsr.gsr_yatm.utilities.contract;

import com.gsr.gsr_yatm.utilities.contract.exceptions.OutOfRangeException;

public class Contract
{
	public static int NotNegative(int integer) 
	{
		if(integer < 0) 
		{
			throw new OutOfRangeException();
		}
		return integer;
	} // end NotNegative()

} // end class