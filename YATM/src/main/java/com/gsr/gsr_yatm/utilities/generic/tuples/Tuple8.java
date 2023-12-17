package com.gsr.gsr_yatm.utilities.generic.tuples;

import org.jetbrains.annotations.Nullable;

/** Type safe data holder for 8 values*/
public record Tuple8<A, B, C, D, E, F, G, H>(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e, @Nullable F f, @Nullable G g, @Nullable H h)
{
	public Tuple8(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e, @Nullable F f, @Nullable G g, @Nullable H h) 
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
	} // end constructor
	
	public Tuple8(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e, @Nullable F f, @Nullable G g) 
	{
		this(a, b, c, d, e, f, g, null);
	} // end constructor
	
	public Tuple8(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e, @Nullable F f) 
	{
		this(a, b, c, d, e, f, null);
	} // end constructor
	
	public Tuple8(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e) 
	{
		this(a, b, c, d, e, null);
	} // end constructor
	
	public Tuple8(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d) 
	{
		this(a, b, c, d, null);
	} // end constructor
	
	public Tuple8(@Nullable A a, @Nullable B b, @Nullable C c) 
	{
		this(a, b, c, null);
	} // end constructor
	
	public Tuple8(@Nullable A a, @Nullable B b) 
	{
		this(a, b, null);
	} // end constructor
	
	public Tuple8(@Nullable A a) 
	{
		this(a, null);
	} // end constructor
	
} // end RecipeContext()