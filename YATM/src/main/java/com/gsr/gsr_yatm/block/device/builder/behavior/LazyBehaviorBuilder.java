package com.gsr.gsr_yatm.block.device.builder.behavior;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;

public class LazyBehaviorBuilder<T> implements IBehaviorBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Supplier<? extends IBehavior> m_of;
	private final @NotNull Consumer<List<BehaviorDefinition<?>>> m_buildReceiver;
	private final @NotNull Consumer<Runnable> m_runReceiver;
	
	private final @NotNull List<Runnable> m_runs = Lists.newArrayList();
	private final @NotNull List<Supplier<BehaviorDefinition<?>>> m_behaviors = Lists.newArrayList();
	

	
	public LazyBehaviorBuilder(@Nullable T parent, @NotNull Supplier<? extends IBehavior> of, @NotNull Consumer<List<BehaviorDefinition<?>>> buildReceiver, @NotNull Consumer<Runnable> runReceiver) 
	{
		this.m_parent = parent;
		this.m_of = Objects.requireNonNull(of);
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
		this.m_runReceiver = Objects.requireNonNull(runReceiver);
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
		this.tryAdd(ISerializableBehavior.class, () -> new SerializableBehavior(((ISerializableBehavior)this.m_of.get()), key));
		return this;
	} // end asSerializableWithKey()
	
	@Override
	public @NotNull IBehaviorBuilder<T> allDefaults()
	{
		this.m_runs.add(() -> this.m_of.get().behaviorTypes().forEach(this::addAs));
		return this; 
	} // end allDefaults()
	
	@SuppressWarnings("unchecked")
	protected <X extends IBehavior> void tryAdd(@NotNull Class<X> type, @NotNull Supplier<? extends IBehavior> behavior) 
	{
		Objects.requireNonNull(type);
		Objects.requireNonNull(behavior);
		this.m_runs.add(() -> 
//		{
//			try
//			{
				
				this.m_behaviors.add(() -> new BehaviorDefinition<X>(type, (X) behavior.get()))//;
//			} 
//			catch (ClassCastException e) { }
//		}
		);
	} // end behavior()
	
	
	
	public @Nullable T end() 
	{
		this.m_runReceiver.accept(() -> 
		{
			this.m_runs.forEach(Runnable::run);
			this.m_buildReceiver.accept(this.m_behaviors.stream().map(Supplier::get).collect(ImmutableList.toImmutableList()));
		});
		return this.m_parent;
	} // end end()

} // end class