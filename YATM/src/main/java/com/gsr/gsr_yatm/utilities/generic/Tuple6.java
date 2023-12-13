package com.gsr.gsr_yatm.utilities.generic;

import org.jetbrains.annotations.Nullable;

/** Type safe data holder for 6 values*/
public record Tuple6<A, B, C, D, E, F>(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e, @Nullable F f)
{
	public Tuple6(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e, @Nullable F f) 
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	} // end constructor
	
	public Tuple6(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e) 
	{
		this(a, b, c, d, e, null);
	} // end constructor
	
	public Tuple6(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d) 
	{
		this(a, b, c, d, null);
	} // end constructor
	
	public Tuple6(@Nullable A a, @Nullable B b, @Nullable C c) 
	{
		this(a, b, c, null);
	} // end constructor
	
	public Tuple6(@Nullable A a, @Nullable B b) 
	{
		this(a, b, null);
	} // end constructor
	
	public Tuple6(@Nullable A a) 
	{
		this(a, null);
	} // end constructor
	
} // end RecipeContext()