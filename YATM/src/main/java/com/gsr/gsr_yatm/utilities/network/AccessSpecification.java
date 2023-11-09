package com.gsr.gsr_yatm.utilities.network;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

public record AccessSpecification(@NotNegative int startIndex, @NotNegative int endIndex)
{

	public AccessSpecification(@NotNegative int startIndex, @NotNegative int endIndex) 
	{
		this.endIndex = Math.max(Contract.notNegative(startIndex), Contract.notNegative(endIndex));
		this.startIndex = Math.min(startIndex, endIndex);		
	} // end constructor
	
	
	
	public static @NotNull AccessSpecification join(@NotNull AccessSpecification lowp, @NotNull AccessSpecification highp)
	{
		return new AccessSpecification(lowp.startIndex, highp.endIndex);
	} // end join()

	public @NotNegative int count()
	{
		return (endIndex - startIndex) + 1;
	} // end count()

} // end record