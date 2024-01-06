package com.gsr.gsr_yatm.block.device.builder;

import org.jetbrains.annotations.Nullable;

public interface IBuilder<T>
{
	@Nullable T end();
} // end interface