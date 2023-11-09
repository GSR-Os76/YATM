package com.gsr.gsr_yatm.utilities.contract;

import com.gsr.gsr_yatm.utilities.contract.exceptions.OutOfRangeException;

public class Contract
{
	public static int notNegative(int integer) 
	{
		if(integer < 0) 
		{
			throw new OutOfRangeException();
		}
		return integer;
	} // end NotNegative()
	
	public static long notNegative(long integer) 
	{
		if(integer < 0) 
		{
			throw new OutOfRangeException();
		}
		return integer;
	} // end NotNegative()

	
	
	public static int inInclusiveRange(int from, int to, int test) 
	{
		return Contract.inInclusiveRange(from, to, test, "");
	} // end inInclusiveRange()
	
	public static int inInclusiveRange(int from, int to, int test, String eMessage) 
	{
		if(test > Math.max(to, test) || test < Math.min(to, test)) 
		{
			throw new OutOfRangeException(eMessage);
		}
		return test;
	} // end inInclusiveRange()
	
} // end class