package com.gsr.gsr_yatm.utilities.contract.exceptions;

public class OutOfRangeException extends IllegalArgumentException
{
	/**
	 * 
	 */
	// TODO, research
	private static final long serialVersionUID = 1L;

	
	
	public OutOfRangeException()
	{
		super();
	} // end constructor

	public OutOfRangeException(String message)
	{
		super(message);
	} // end constructor

	public OutOfRangeException(String message, Throwable cause)
	{
		super(message, cause);
	} // end constructor
	
	public OutOfRangeException(Throwable cause)
	{
		super(cause);
	} // end constructor

} // end class