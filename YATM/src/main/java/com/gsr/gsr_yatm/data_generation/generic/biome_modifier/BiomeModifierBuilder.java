package com.gsr.gsr_yatm.data_generation.generic.biome_modifier;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BiomeModifierBuilder implements IBiomeModifierBuilder
{
	private static @NotNull final HolderOwner<Biome> HOLDER_OWNER = new HolderOwner<Biome> () {};
	private final @NotNull HolderSet<Biome> m_biomes;
	
	private IStrongBiomeModifier<?> m_biomeModifier;
	
	
	
	private BiomeModifierBuilder(@NotNull HolderSet<Biome> biomes) 
	{
		this.m_biomes = Objects.requireNonNull(biomes);
	} // end constructor
	
	
	
	@SuppressWarnings({ "deprecation"})
	public static @NotNull BiomeModifierBuilder of(@NotNull TagKey<Biome> biomes) 
	{
		// TODO, find non-deprecated way to create holder set from a TagKey.
		return new BiomeModifierBuilder(HolderSet.emptyNamed(HOLDER_OWNER, Objects.requireNonNull(biomes)));
	} // end of()
	
	public static @NotNull BiomeModifierBuilder of(@NotNull Biome biome) 
	{
		return new BiomeModifierBuilder(HolderSet.direct(Holder.direct(Objects.requireNonNull(biome))));
	} // end of()
	
	
	
	public @NotNull AddFeaturesBiomeModifierBuilder<BiomeModifierBuilder> addFeature(@NotNull PlacedFeature f) 
	{
		return new AddFeaturesBiomeModifierBuilder<BiomeModifierBuilder>(this, this::recordBiomeModifier).biomes(this.m_biomes).features(HolderSet.direct(Holder.direct(Objects.requireNonNull(f))));
	} // end addFeature()
	
	
	
	public void recordBiomeModifier(@NotNull IStrongBiomeModifier<?> biomeModifier) 
	{
		if(this.m_biomeModifier != null)
		{
			throw new IllegalStateException("Biome modifier was already generated.");
		}
		this.m_biomeModifier = Objects.requireNonNull(biomeModifier);
	} // end recordBiomeModifier()

	@Override
	public @NotNull IStrongBiomeModifier<?> build()
	{
		return this.m_biomeModifier;
	} // end build()
	
} // end class  