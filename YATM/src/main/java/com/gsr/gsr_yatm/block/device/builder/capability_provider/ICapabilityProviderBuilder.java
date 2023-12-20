package com.gsr.gsr_yatm.block.device.builder.capability_provider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.option.IOptionBuilder;

import net.minecraft.core.Direction;

public interface ICapabilityProviderBuilder<T> extends IBuilder<T>
{
	@NotNull IOptionBuilder<? extends ICapabilityProviderBuilder<T>> face(@Nullable Direction face);
	
} // end interface