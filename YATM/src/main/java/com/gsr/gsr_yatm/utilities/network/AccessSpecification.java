package com.gsr.gsr_yatm.utilities.network;

public record AccessSpecification(int startIndex, int endIndex)
{

	public static AccessSpecification join(AccessSpecification lowp, AccessSpecification highp)
	{
		// TODO Auto-generated method stub
		return new AccessSpecification(lowp.startIndex, highp.endIndex);
	} // end join()

} // end record