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
	<X extends IBehavior> IBehaviorBuilder<T> addAs(@NotNull Class<X> type);

	@NotNull IBehaviorBuilder<T> asSerializableWithKey(@NotNull String key);

	@NotNull IBehaviorBuilder<T> allDefaults();
	
	
	
	default @NotNull IBehaviorBuilder<T> tickable()
	{
		return this.addAs(ITickableBehavior.class);
	} // end tickable()
	
	default @NotNull IBehaviorBuilder<T> serializable()
	{
		return this.addAs(ISerializableBehavior.class);
	} // end serializable()
	
	default @NotNull IBehaviorBuilder<T> changeListener()
	{
		return this.addAs(IChangedListenerBehavior.class);
	} // end changeListener()
	
	default @NotNull IBehaviorBuilder<T> invListener()
	{
		return this.addAs(IInventoryChangeListenerBehavior.class);
	} // end invListener()
	
	default @NotNull IBehaviorBuilder<T> loadListener()
	{
		return this.addAs(ILoadListenerBehavior.class);
	} // end loadListener()

} // end interface