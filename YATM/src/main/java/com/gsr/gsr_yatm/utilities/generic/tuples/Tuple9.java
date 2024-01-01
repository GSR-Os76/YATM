package com.gsr.gsr_yatm.utilities.generic.tuples;

import org.jetbrains.annotations.Nullable;

/** Type safe data holder for 9 values*/
public record Tuple9<A, B, C, D, E, F, G, H, I>(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e, @Nullable F f, @Nullable G g, @Nullable H h, @Nullable I i)
{
	public Tuple9(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d, @Nullable E e, @Nullable F f, @Nullable G g, @Nullable H h, @Nullable I i) 
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
	} // end constructor
	
} // end RecipeContext()