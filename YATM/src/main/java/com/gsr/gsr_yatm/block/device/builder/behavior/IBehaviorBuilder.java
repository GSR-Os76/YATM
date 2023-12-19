package com.gsr.gsr_yatm.block.device.builder.behavior;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.IChangedListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.IInventoryChangeListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ILoadListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.block.device.builder.IBuilder;

public interface IBehaviorBuilder<T> extends IBuilder<T>
{	
	/** must do nothing if X can't match*/
	<X extends IBehavior> IBehaviorBuilder<T> addAs(@NotNull Class<X> type);

	@NotNull IBehaviorBuilder<T> asSerializableWithKey(@NotNull String key);

	
	
	default @NotNull IBehaviorBuilder<T> tryAllDefault() 
	{
		// TODO, could replace with a set of types, and allow adding to it
		this.addAs(ITickableBehavior.class);
		this.addAs(ISerializableBehavior.class);
		this.addAs(IChangedListenerBehavior.class);
		this.addAs(IInventoryChangeListenerBehavior.class);
		this.addAs(ILoadListenerBehavior.class);
		
		return this;
	} // end tryAllDefault()
	
} // end interface