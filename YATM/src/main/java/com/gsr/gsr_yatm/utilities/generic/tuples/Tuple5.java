package com.gsr.gsr_yatm.utilities.generic.tuples;

import org.jetbrains.annotations.Nullable;

/** Type safe data holder for 5 values*/
public record Tuple5<A, B, C, D, E>(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e)
{
	public Tuple5(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e) 
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	} // end constructor
	
} // end RecipeContext()