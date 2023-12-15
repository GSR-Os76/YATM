package com.gsr.gsr_yatm.block.device.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.IChangedListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.IInventoryChangeListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ILoadListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;

public class BehaviorBuilder<T extends IBehavioralBuilder>
{
	private final @NotNull T m_behavioral;
	private final @NotNull Object m_of;
	private final @NotNull Map<Class<? extends IBehavior>, Runnable> m_behaviorAdders = new HashMap<>();
	

	
	public BehaviorBuilder(@NotNull T behavioral, @NotNull Object of) 
	{
		this.m_behavioral = Objects.requireNonNull(behavioral);
		this.m_of = Objects.requireNonNull(of);
	} // end constructor
	
	
	
	@SuppressWarnings("unchecked")
	public <X extends IBehavior> @NotNull BehaviorBuilder<T> addAs(@NotNull Class<X> type) 
	{
		// cast outside of lambda for fail fast
		X x =(X)this.m_of;
		this.m_behaviorAdders.put(type, () -> this.m_behavioral.addBehavior(x));
		return this;
	} // end behavior()
	
	public @NotNull BehaviorBuilder<T> asSerializableWithKey(@NotNull String key)
	{
		this.m_behaviorAdders.put(ISerializableBehavior.class, () -> this.m_behavioral.addBehavior(new SerializableBehavior((ISerializableBehavior)this.m_of, key)));
		return this;
	} // end asSerializableWithKey()
	
	
	
	protected @NotNull BehaviorBuilder<T> tryAllDefault() 
	{
		if(this.m_of instanceof ITickableBehavior b) 
		{
			this.addAs(ITickableBehavior.class);
		}
		if(this.m_of instanceof ISerializableBehavior b) 
		{
			this.addAs(ISerializableBehavior.class);
		}
		if(this.m_of instanceof IChangedListenerBehavior b) 
		{
			this.addAs(IChangedListenerBehavior.class);
		}
		if(this.m_of instanceof IInventoryChangeListenerBehavior b) 
		{
			this.addAs(IInventoryChangeListenerBehavior.class);
		}
		if(this.m_of instanceof ILoadListenerBehavior b) 
		{
			this.addAs(ILoadListenerBehavior.class);
		}
		
		
		return this;
	} // end tryAllDefault()
	
	
	
	public @NotNull T end() 
	{
		this.m_behaviorAdders.values().forEach(Runnable::run);
		return this.m_behavioral;
	} // end end()
	
} // end class