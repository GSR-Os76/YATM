package com.gsr.gsr_yatm.block.device.builder.behavior;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;

public class BehaviorBuilder<T> implements IBehaviorBuilder<T>
{
	private final @NotNull T m_parent;
	private final @NotNull IBehavior m_of;
	private final @NotNull Consumer<List<BehaviorDefinition<?>>> m_buildReceiver;
	
	private final @NotNull List<BehaviorDefinition<?>> m_behaviors = Lists.newArrayList();
	

	
	public BehaviorBuilder(@NotNull T parent, @NotNull IBehavior of, @NotNull Consumer<List<BehaviorDefinition<?>>> buildReceiver) 
	{
		this.m_parent = Objects.requireNonNull(parent);
		this.m_of = Objects.requireNonNull(of);
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
	} // end constructor
	
	
	
	@Override
	public <X extends IBehavior> IBehaviorBuilder<T> addAs(@NotNull Class<X> type) 
	{
		this.tryAdd(type, this.m_of);
		return this;
	} // end behavior()
	
	@Override
	public @NotNull IBehaviorBuilder<T> asSerializableWithKey(@NotNull String key)
	{
		this.tryAdd(ISerializableBehavior.class, new SerializableBehavior((ISerializableBehavior)this.m_of, key));
		return this;
	} // end asSerializableWithKey()
	
	@Override
	public @NotNull IBehaviorBuilder<T> allDefaults()
	{
		this.m_of.behaviorTypes().forEach(this::addAs);
		return this; 
	} // end allDefaults()
	
	@SuppressWarnings("unchecked")
	protected <X extends IBehavior> void tryAdd(@NotNull Class<X> type, IBehavior behavior) 
	{
//		try
//		{
//			YetAnotherTechMod.LOGGER.info("instanceofCheck: " + (behavior instanceof X));
		// TODO, casting throws no exception when it's invalid, problematically, instanceof as well doesn't work 
			this.m_behaviors.add(new BehaviorDefinition<X>(type, (X) behavior));
//		}
//		catch(ClassCastException e) { }
	} // end behavior()
	
	
	
	@Override
	public @NotNull T end() 
	{
		this.m_buildReceiver.accept(ImmutableList.copyOf(this.m_behaviors));
		return this.m_parent;
	} // end end()
	
} // end class