package com.gsr.gsr_yatm.utilities.generic.tuples;

import org.jetbrains.annotations.Nullable;

/** Type safe data holder for 4 values*/
public record Tuple4<A, B, C, D>(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d)
{
	public Tuple4(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d) 
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	} // end constructor
	
} // end RecipeContext()