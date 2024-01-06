package com.gsr.gsr_yatm.block.device.builder.capability_provider;

import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.option.IOptionBuilder;
import com.gsr.gsr_yatm.utilities.generic.SetUtil;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ICapabilityProviderBuilder<T> extends IBuilder<T>
{
	@NotNull ICapabilityProviderBuilder<T> onInvalidate(@NotNull Runnable onInvalidate);
	
	@NotNull ICapabilityProviderBuilder<T> onRevive(@NotNull Runnable onRevive);

	default @NotNull IOptionBuilder<? extends ICapabilityProviderBuilder<T>> face(@Nullable Direction face)
	{
		return this.face(() -> SetUtil.of(face));
	} // end face()
	
//	@NotNull IOptionBuilder<? extends ICapabilityProviderBuilder<T>> face(@Nullable Supplier<Direction> face);
	
	@NotNull IOptionBuilder<? extends ICapabilityProviderBuilder<T>> face(@Nullable Supplier<Set<Direction>> faces);

	@NotNull T last(@NotNull ICapabilityProvider last);
	
} // end interface