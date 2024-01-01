package com.gsr.gsr_yatm.utilities.generic.tuples;

public class Tuple
{
	public static <A, B> Tuple2<A, B> of(A a, B b)
	{
		return new Tuple2<>(a, b);
	} // end of()
	
	public static <A, B, C> Tuple3<A, B, C> of(A a, B b, C c)
	{
		return new Tuple3<>(a, b, c);
	} // end of()
	
	public static <A, B, C, D> Tuple4<A, B, C, D> of(A a, B b, C c, D d)
	{
		return new Tuple4<>(a, b, c, d);
	} // end of()
	
	public static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(A a, B b, C c, D d, E e)
	{
		return new Tuple5<>(a, b, c, d, e);
	} // end of()
	
	public static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> of(A a, B b, C c, D d, E e, F f)
	{
		return new Tuple6<>(a, b, c, d, e, f);
	} // end of()
	
	public static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(A a, B b, C c, D d, E e, F f, G g)
	{
		return new Tuple7<>(a, b, c, d, e, f, g);
	} // end of()
	
	public static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(A a, B b, C c, D d, E e, F f, G g, H h)
	{
		return new Tuple8<>(a, b, c, d, e, f, g, h);
	} // end of()
	
	public static <A, B, C, D, E, F, G, H, I> Tuple9<A, B, C, D, E, F, G, H, I> of(A a, B b, C c, D d, E e, F f, G g, H h, I i)
	{
		return new Tuple9<>(a, b, c, d, e, f, g, h, i);
	} // end of()
	
} // end class