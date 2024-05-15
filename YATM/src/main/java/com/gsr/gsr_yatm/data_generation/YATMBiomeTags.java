package com.gsr.gsr_yatm.data_generation;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMBiomeTags extends BiomeTagsProvider
{
	private static final String FORGE_ID = "forge";
	
	private static final ITagManager<Biome> TM = ForgeRegistries.BIOMES.tags();
	
	
	
	public static final TagKey<Biome> IS_FROZEN_OCEAN = TM.createTagKey(new ResourceLocation(YATMBiomeTags.FORGE_ID, "is_frozen_ocean"));
	
	
	public static final TagKey<Biome> HAS_FEATURE_CENTIPEDE_VINES = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "has_feature/centipede_vines"));
	public static final TagKey<Biome> HAS_FEATURE_DWARF_PERSIMMON = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "has_feature/dwarf_persimmon"));
	public static final TagKey<Biome> HAS_FEATURE_ICE_CORAL = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "has_feature/ice_coral"));
	public static final TagKey<Biome> HAS_FEATURE_SOUL_AFFLICTED_RUBBER_TREE = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "has_feature/soul_afflicted_rubber_tree"));
	public static final TagKey<Biome> HAS_FEATURE_SPIDER_PLANT = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "has_feature/spider_plant"));
	
	
	
	public YATMBiomeTags(PackOutput packOutput, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(packOutput, completableFuture, YetAnotherTechMod.MODID, existingFileHelper);
	} // end constructor

	
	
	@Override
	protected void addTags(Provider provider)
	{
		this.tag(YATMBiomeTags.IS_FROZEN_OCEAN).add(Biomes.FROZEN_OCEAN).add(Biomes.DEEP_FROZEN_OCEAN);

		this.tag(YATMBiomeTags.HAS_FEATURE_CENTIPEDE_VINES).addTag(BiomeTags.IS_OVERWORLD);
		this.tag(YATMBiomeTags.HAS_FEATURE_DWARF_PERSIMMON).add(Biomes.FLOWER_FOREST).add(Biomes.BIRCH_FOREST).add(Biomes.OLD_GROWTH_BIRCH_FOREST).add(Biomes.DARK_FOREST);
		this.tag(YATMBiomeTags.HAS_FEATURE_ICE_CORAL).addTag(YATMBiomeTags.IS_FROZEN_OCEAN);
		this.tag(YATMBiomeTags.HAS_FEATURE_SOUL_AFFLICTED_RUBBER_TREE).add(Biomes.SOUL_SAND_VALLEY);
		this.tag(YATMBiomeTags.HAS_FEATURE_SPIDER_PLANT).add(Biomes.LUSH_CAVES);

	} // end addTags()
	
} // end class