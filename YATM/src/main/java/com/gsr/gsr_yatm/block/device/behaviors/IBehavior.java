package com.gsr.gsr_yatm.block.device.behaviors;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

public interface IBehavior
{
	@NotNull Set<Class<? extends IBehavior>> behaviorTypes();
} // end interface