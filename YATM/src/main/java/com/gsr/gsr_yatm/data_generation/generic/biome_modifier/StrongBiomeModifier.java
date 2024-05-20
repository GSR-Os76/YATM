package com.gsr.gsr_yatm.data_generation.generic.biome_modifier;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public class StrongBiomeModifier<T extends BiomeModifier> implements IStrongBiomeModifier<T>
{
	private final @NotNull T m_biomeModifier;
	private final @NotNull Codec<T> m_codec;
	
	
	
	public StrongBiomeModifier(@NotNull T biomeModifier, @NotNull Codec<T> codec)
	{
		this.m_biomeModifier = Objects.requireNonNull(biomeModifier);
		this.m_codec = Objects.requireNonNull(codec);
	} // end constructor
	
	
	
	@Override
	public @NotNull T getInnerBiomeModifier() 
	{
		return this.m_biomeModifier;
	} // end getInnerBiomeModifier()
	
	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder)
	{
		this.m_biomeModifier.modify(biome, phase, builder);
	} // end modify()

	@Override
	public Codec<T> codec()
	{
		return this.m_codec;
	} // end codec()

} // end class