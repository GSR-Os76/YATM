package com.gsr.gsr_yatm.utilities;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

public class PrimitiveUtil
{
	
	public static @NotNull Supplier<Float> toFloatSupplier(@NotNull Supplier<Double> supplier)
	{
		return () -> 
		{
			double d = supplier.get();
			return (float)d;
		};
	} // end toFloatSupplier()
	
} // end class