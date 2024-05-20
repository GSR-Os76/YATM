package com.gsr.gsr_yatm.data_generation.generic.biome_modifier;

import org.jetbrains.annotations.NotNull;

public interface IBiomeModifierBuilder
{
	public @NotNull IStrongBiomeModifier<?> build();
} // end interface