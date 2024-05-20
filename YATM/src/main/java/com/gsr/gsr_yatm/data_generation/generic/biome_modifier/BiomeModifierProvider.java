package com.gsr.gsr_yatm.data_generation.generic.biome_modifier;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ExistingFileHelper.IResourceType;
import net.minecraftforge.common.world.BiomeModifier;

public abstract class BiomeModifierProvider implements DataProvider
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
			return "forge/biome_modifier";
		} // end getPrefix()
	};
	
	private final @NotNull Map<ResourceLocation, IBiomeModifierBuilder> m_modifiers = Maps.newHashMap();
	private final @NotNull String m_namespace;
	private final @NotNull ExistingFileHelper m_existingFileHelper;
	private final @NotNull PackOutput m_packOutput;
	
	
	
	public BiomeModifierProvider(@NotNull String namespace, @NotNull PackOutput packOutput, @NotNull ExistingFileHelper existingFileHelper) 
	{
		this.m_namespace = Objects.requireNonNull(namespace);
		this.m_existingFileHelper = Objects.requireNonNull(existingFileHelper);
		this.m_packOutput = Objects.requireNonNull(packOutput);
	} // end constructor
	
	
	
	@Override
	public CompletableFuture<?> run(CachedOutput cachedOutput)
	{
		this.addBiomeModifiers();
		return CompletableFuture.allOf(this.m_modifiers.entrySet().stream().map((e) -> this.run(cachedOutput, e)).toArray((i) -> new CompletableFuture[i]));
	} // end run()
	
	public CompletableFuture<?> run(CachedOutput cachedOutput, Map.Entry<ResourceLocation, IBiomeModifierBuilder> e)
	{ 
		Path path = this.m_packOutput.getOutputFolder()
				.resolve(BiomeModifierProvider.RESOURCE_TYPE.getPackType().getDirectory()) 
				.resolve(e.getKey().getNamespace())
				.resolve(e.getKey()
						.withPrefix(BiomeModifierProvider.RESOURCE_TYPE.getPrefix())
						.withSuffix(BiomeModifierProvider.RESOURCE_TYPE.getSuffix())
						.getPath())
				;
		JsonElement jsonelement = this.jsonify(e.getValue().build());
		return DataProvider.saveStable(cachedOutput, jsonelement, path);
	} // end run()
	
	// necessary to capture generic type info usefully
	private <T extends BiomeModifier, U extends IStrongBiomeModifier<T>> JsonElement jsonify(U bm) 
	{
		return bm.codec().encodeStart(JsonOps.INSTANCE, bm.getInnerBiomeModifier()).getOrThrow(false, LOGGER::error);
	} // end jsonify()
	
	
	
	public abstract void addBiomeModifiers();
	
	protected BiomeModifierBuilder modify(TagKey<Biome> biome, ResourceLocation location) 
	{
		// possibly move this into run, so modifiers can potentially reference other modifiers provided later by the same provider
		if(this.m_existingFileHelper.isEnabled() && this.m_existingFileHelper.exists(location, BiomeModifierProvider.RESOURCE_TYPE)) 
		{
			throw new IllegalStateException("Duplicate biome modifier: " + location.toString());
		} 
		
		BiomeModifierBuilder b = BiomeModifierBuilder.of(biome);
		this.m_modifiers.put(location, b);
		this.m_existingFileHelper.trackGenerated(location, BiomeModifierProvider.RESOURCE_TYPE);
		return b;
	} // end modify()
	
	
	
	@Override
	public String getName()
	{
		return "Biome Modifiers for mod id " + this.m_namespace;
	} // end getName()

} // end class