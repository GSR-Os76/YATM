package com.gsr.gsr_yatm.utilities.generic;

import java.util.Objects;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BackedFunction<T, R>
{
	private @Nullable Function<T, R> m_function;
	private R m_result;
	
	
	public BackedFunction(@NotNull Function<T, R> function) 
	{
		this.m_function = Objects.requireNonNull(function);
	} // end constructor
	
	
	
	public R apply(T t) 
	{
		if(this.m_function != null) 
		{
			this.m_result = this.m_function.apply(t);
			this.m_function = null;
		}
		return this.m_result;
	} // end apply()
	
	public R get() 
	{
		if(this.m_function != null) 
		{
			throw new IllegalStateException("Function must be applied before calling get().");
		}
		return this.m_result;
	} // end get()
	
} // end class