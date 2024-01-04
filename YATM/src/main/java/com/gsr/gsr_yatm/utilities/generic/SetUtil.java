package com.gsr.gsr_yatm.utilities.generic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetUtil
{
	
	// TODO, make these immutable, and check safety
	/**Can be null.*/
	public static @NotNull <T> Set<T> of(@Nullable T... values)
	{
		HashSet<T> s = new HashSet<>();
		Stream.of(values).forEach(s::add);
		return s;
	} // end of()
	
	public static @NotNull <T> Set<T> of(@Nullable Collection<T> values)
	{
		HashSet<T> s = new HashSet<>();
		values.forEach(s::add);
		return s;
	} // end of()
	
	public static @NotNull <T> Set<T> of(@Nullable Stream<T> values)
	{
		HashSet<T> s = new HashSet<>();
		values.forEach(s::add);
		return s;
	} // end of()

} // end class