package com.gsr.gsr_yatm.block.device.behaviors.implementation.utility;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class TickableBehaviorConditioner implements ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ITickableBehavior.class);
	} // end behaviorTypes()

	private final @NotNull Supplier<@NotNull Boolean> m_condition;
	private final @NotNull ITickableBehavior m_tickable;
	
	
	
	public TickableBehaviorConditioner(@NotNull Supplier<@NotNull Boolean> condition, @NotNull ITickableBehavior tickable) 
	{
		this.m_condition = Objects.requireNonNull(condition);
		this.m_tickable = Objects.requireNonNull(tickable);
	} // end constructor
	
	
	@Override
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		if(this.m_condition.get())
		{
			return this.m_tickable.tick(level, position);
		}
		return false;
	} // end tick()

} // end class