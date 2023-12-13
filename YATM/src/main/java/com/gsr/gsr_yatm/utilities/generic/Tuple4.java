package com.gsr.gsr_yatm.utilities.generic;

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

	public Tuple4(@Nullable A a, @Nullable B b, @Nullable C c) 
	{
		this(a, b, c, null);
	} // end constructor
	
	public Tuple4(@Nullable A a, @Nullable B b) 
	{
		this(a, b, null);
	} // end constructor
	
	public Tuple4(@Nullable A a) 
	{
		this(a, null);
	} // end constructor
	
} // end RecipeContext()