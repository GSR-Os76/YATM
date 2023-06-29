package com.gsr.gsr_yatm.data_generation;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMBiomeTags extends BiomeTagsProvider
{
	@SuppressWarnings("unused")
	private static final String MINECRAFT_ID = "minecraft";
	@SuppressWarnings("unused")
	private static final String FORGE_ID = "forge";
	
	private static final ITagManager<Biome> TM = ForgeRegistries.BIOMES.tags();
	
	
	
	public static final TagKey<Biome> HAS_FEATURE_SOUL_AFFLICTED_RUBBER_BUSH = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "has_feature/soul_afflicted_rubber_bush"));
	

	
	public YATMBiomeTags(PackOutput packOutput, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(packOutput, completableFuture, YetAnotherTechMod.MODID, existingFileHelper);
	} // end constructor

	
	
	@Override
	protected void addTags(Provider provider)
	{
		this.tag(HAS_FEATURE_SOUL_AFFLICTED_RUBBER_BUSH).add(Biomes.SOUL_SAND_VALLEY);
	} // end addTags()
	
} // end class