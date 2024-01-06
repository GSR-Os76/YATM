package com.gsr.gsr_yatm.block.device.builder.capability_provider;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.builder.capability_provider.option.IOptionBuilder;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.option.OptionBuilder;
import com.gsr.gsr_yatm.utilities.capability.CapabilityUtil;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CapabilityProviderBuilder<T> implements ICapabilityProviderBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Consumer<IInvalidatableCapabilityProvider> m_buildReceiver;
	
	private final @NotNull List<Runnable> m_invalidationListeners = Lists.newArrayList();
	private final @NotNull List<Runnable> m_reviveListeners = Lists.newArrayList();
	private final @NotNull List<Tuple2<Predicate<Direction>, IInvalidatableCapabilityProvider>> m_options = Lists.newArrayList();
	private @NotNull ICapabilityProvider m_last = CapabilityUtil.EMPTY_PROVIDER;
	
	
	
	public CapabilityProviderBuilder(@Nullable T parent, @NotNull Consumer<IInvalidatableCapabilityProvider> buildReceiver) 
	{
		this.m_parent = parent;
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
	} // end constructor

	
	
	@Override
	public @NotNull ICapabilityProviderBuilder<T> onInvalidate(@NotNull Runnable onInvalidate)
	{
		this.m_invalidationListeners.add(onInvalidate);
		return this;
	} // end onInvalidate()

	@Override
	public @NotNull ICapabilityProviderBuilder<T> onRevive(@NotNull Runnable onRevive)
	{
		this.m_reviveListeners.add(onRevive);
		return this;
	} // end onRevive()

	@Override
	public @NotNull IOptionBuilder<? extends ICapabilityProviderBuilder<T>> face(@Nullable Supplier<Set<Direction>> faces)
	{
		return new OptionBuilder<>(this, (o) -> this.m_options.add(Tuple.of(faces.get()::contains, o)));
	} // end face()

	@Override
	public @NotNull T last(@NotNull ICapabilityProvider last)
	{
		this.m_last = last;
		return this.end();
	} // end last()
	
	@Override
	public @Nullable T end()
	{
		this.m_buildReceiver.accept(new BuiltCapabilityProvider(this.m_options, this.m_invalidationListeners, this.m_reviveListeners, this.m_last));
		return this.m_parent;
	} // end end()
	
} // end class