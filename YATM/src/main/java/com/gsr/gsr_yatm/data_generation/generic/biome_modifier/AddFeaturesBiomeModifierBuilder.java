package com.gsr.gsr_yatm.data_generation.generic.biome_modifier;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;

import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;

public class AddFeaturesBiomeModifierBuilder<T> implements IBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Consumer<@NotNull IStrongBiomeModifier<AddFeaturesBiomeModifier>> m_resultAcceptor;
	private HolderSet<Biome> m_biomes;
	private HolderSet<PlacedFeature> m_features;
	private Decoration m_step;
	
	
	
	public AddFeaturesBiomeModifierBuilder(@Nullable T parent, @NotNull Consumer<@NotNull IStrongBiomeModifier<AddFeaturesBiomeModifier>> resultAcceptor) 
	{
		this.m_parent = parent;
		this.m_resultAcceptor = Objects.requireNonNull(resultAcceptor);
	} // end constructor
	
	
	
	public @NotNull AddFeaturesBiomeModifierBuilder<T> biomes(@NotNull HolderSet<Biome> biomes)
	{
		this.m_biomes = Objects.requireNonNull(biomes);
		return this;
	} // end build()
	
	public @NotNull AddFeaturesBiomeModifierBuilder<T> features(@NotNull HolderSet<PlacedFeature> features)
	{
		this.m_features = Objects.requireNonNull(features);
		return this;
	} // end build()
	
	public @NotNull AddFeaturesBiomeModifierBuilder<T> step(@NotNull Decoration step)
	{
		this.m_step = Objects.requireNonNull(step);
		return this;
	} // end build()
	
	
	
	protected @NotNull IStrongBiomeModifier<AddFeaturesBiomeModifier> build()
	{	
		return new StrongBiomeModifier<AddFeaturesBiomeModifier>(new AddFeaturesBiomeModifier(Objects.requireNonNull(this.m_biomes), Objects.requireNonNull(this.m_features), Objects.requireNonNull(this.m_step)), AddFeaturesBiomeModifier.CODEC);
	} // end build()

	@Override
	public @Nullable T end()
	{
		this.m_resultAcceptor.accept(this.build());
		return this.m_parent;
	} // end end()
	
} // end class