package com.gsr.gsr_yatm.block.device.builder.behavior;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;

public record BehaviorDefinition<T extends IBehavior>(@NotNull Class<T> type, @NotNull T behavior)
{	
	public BehaviorDefinition(@NotNull Class<T> type, @NotNull T behavior) 
	{
		this.type = Objects.requireNonNull(type);
		this.behavior = Objects.requireNonNull(behavior);
	} // end constructor

	
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof BehaviorDefinition<?> bd) 
		{
			return bd.type == this.type && bd.behavior == this.behavior;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.type, this.behavior);
	} // end hashCode()

	@Override
	public String toString()
	{
		return this.type.getName() + ": " + this.behavior.toString();
	} // end toString()
	
} // end record