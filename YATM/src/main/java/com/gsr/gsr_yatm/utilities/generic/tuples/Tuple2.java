package com.gsr.gsr_yatm.utilities.generic.tuples;

import org.jetbrains.annotations.Nullable;

/** Type safe data holder for 2 values*/
public record Tuple2<A, B>(@Nullable A a, @Nullable B b)
{
	public Tuple2(@Nullable A a, @Nullable B b) 
	{
		this.a = a;
		this.b = b;
	} // end constructor

	public Tuple2(@Nullable A a) 
	{
		this(a, (B)null);
	} // end constructor
	
} // end RecipeContext()