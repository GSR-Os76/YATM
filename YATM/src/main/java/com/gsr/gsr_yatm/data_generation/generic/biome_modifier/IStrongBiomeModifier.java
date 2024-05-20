package com.gsr.gsr_yatm.data_generation.generic.biome_modifier;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public interface IStrongBiomeModifier<T extends BiomeModifier> extends BiomeModifier
{
	@NotNull T getInnerBiomeModifier();
	
	@Override
	void modify(Holder<Biome> biome, Phase phase, Builder builder);

	@Override
	Codec<T> codec();
} // end interface