package com.gsr.gsr_yatm.data_generation.generic.configured_feature;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Maps;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ExistingFileHelper.IResourceType;

public abstract class ConfiguredFeatureProvider implements DataProvider
{
	private static final @NotNull IResourceType RESOURCE_TYPE = new IResourceType() {

		@Override
		public PackType getPackType()
		{
			return PackType.SERVER_DATA;
		} // end getPackType()

		@Override
		public String getSuffix()
		{
			return ".json";
		} // end getSuffix()

		@Override
		public String getPrefix()
		{
			return "worldgen/configured_feature/";
		} // end getPrefix()
	};
	
	private final @NotNull Map<ResourceLocation, ConfiguredFeature<?, ?>> m_configuredFeatures = Maps.newHashMap();
	private final @NotNull String m_namespace;
	private final @NotNull ExistingFileHelper m_existingFileHelper;
	private final @NotNull PackOutput m_packOutput;
	
	
	
	public ConfiguredFeatureProvider(@NotNull String namespace, @NotNull PackOutput packOutput, @NotNull ExistingFileHelper existingFileHelper) 
	{
		this.m_namespace = Objects.requireNonNull(namespace);
		this.m_existingFileHelper = Objects.requireNonNull(existingFileHelper);
		this.m_packOutput = Objects.requireNonNull(packOutput);
	} // end constructor
	
	
	
	@Override
	public CompletableFuture<?> run(@NotNull CachedOutput cachedOutput)
	{
		this.addConfiguredFeatures();
		return CompletableFuture.allOf(
				this.m_configuredFeatures
				.entrySet()
				.stream()
				.map((e) -> DataProvider.saveStable(
						cachedOutput, 
						ConfiguredFeature.DIRECT_CODEC.encodeStart(JsonOps.INSTANCE, e.getValue()).getOrThrow(),
						this.m_packOutput.getOutputFolder()
						.resolve(ConfiguredFeatureProvider.RESOURCE_TYPE.getPackType().getDirectory()) 
						.resolve(e.getKey().getNamespace())
						.resolve(e.getKey()
								.withPrefix(ConfiguredFeatureProvider.RESOURCE_TYPE.getPrefix())
								.withSuffix(ConfiguredFeatureProvider.RESOURCE_TYPE.getSuffix())
								.getPath())
						)
					)
				.toArray((i) -> new CompletableFuture[i])
				);
	} // end run()
	
	
	
	public abstract void addConfiguredFeatures();
	
	protected void add(@NotNull ResourceLocation location, @NotNull ConfiguredFeature<?, ?> configuredFeature) 
	{
		if(this.m_existingFileHelper.isEnabled() && this.m_existingFileHelper.exists(location, ConfiguredFeatureProvider.RESOURCE_TYPE)) 
		{
			throw new IllegalStateException("Duplicate configured feature: " + location.toString());
		}
		this.m_configuredFeatures.put(location, configuredFeature);
		this.m_existingFileHelper.trackGenerated(location, ConfiguredFeatureProvider.RESOURCE_TYPE);
	} // end add()]
	
	
	
	@Override
	public String getName()
	{
		return "Configured Features for mod id " + this.m_namespace;
	} // end getName()

} // end class