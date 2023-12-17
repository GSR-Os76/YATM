package com.gsr.gsr_yatm.utilities.generic.tuples;

import org.jetbrains.annotations.Nullable;

/** Type safe data holder for 3 values*/
public record Tuple3<A, B, C>(@Nullable A a, @Nullable B b, @Nullable C c)
{
	public Tuple3(@Nullable A a, @Nullable B b, @Nullable C c) 
	{
		this.a = a;
		this.b = b;
		this.c = c;
	} // end constructor

	public Tuple3(@Nullable A a, @Nullable B b) 
	{
		this(a, b, null);
	} // end constructor
	
	public Tuple3(@Nullable A a) 
	{
		this(a, null);
	} // end constructor
	
} // end RecipeContext()