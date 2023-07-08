package com.gsr.gsr_yatm.utilities.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Property<T>
{
	private final Supplier<T> m_getter;
	private final Consumer<T> m_setter;
	
	
	
	public Property(Supplier<T> getter, Consumer<T> setter) 
	{
		this.m_getter = getter;
		this.m_setter = setter;
	} // end constructor
	
	
	
	public T get() 
	{
		return this.m_getter.get();
	} // end get()
	
	public void set(T value) 
	{
		this.m_setter.accept(value);
	} // end set()
	
} // end class