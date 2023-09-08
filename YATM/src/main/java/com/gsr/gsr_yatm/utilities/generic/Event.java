package com.gsr.gsr_yatm.utilities.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

public class Event<T> implements Consumer<T>
{
	private final List<Consumer<T>> m_subscribers = new ArrayList<>(); 
	
	
	@Override
	public void accept(T t)
	{
		this.m_subscribers.forEach((s) -> s.accept(t));
	} // end accept
	
	
	
	public void subscribe(@NotNull Consumer<T> subscriber) 
	{
		this.m_subscribers.add(Objects.requireNonNull(subscriber));
	} // end subscribe()
	
	/** Removes the first occurrence of the specified subscriber from the event.*/
	public void unsubscribe(@NotNull Consumer<T> subscriber) 
	{
		this.m_subscribers.remove(Objects.requireNonNull(subscriber));
	} // end unsubscribe()

} // end class