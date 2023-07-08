package com.gsr.gsr_yatm.data_generation;

import java.util.concurrent.CompletableFuture;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMEntityTypeTags extends EntityTypeTagsProvider
{
	@SuppressWarnings("unused")
	private static final String MINECRAFT_ID = "minecraft";
	private static final String FORGE_ID = "minecraft";
	private static final ITagManager<EntityType<?>> TM = ForgeRegistries.ENTITY_TYPES.tags();
	
	public static final TagKey<EntityType<?>> FORGE_WITHER_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "withers"));

	public static final ITag<EntityType<?>> FORGE_WITHER_TAG = TM.getTag(FORGE_WITHER_KEY);

	
	
	public YATMEntityTypeTags(PackOutput packOutput, CompletableFuture<Provider> completableFuture, ExistingFileHelper fileHelper)
	{
		super(packOutput, completableFuture, YetAnotherTechMod.MODID, fileHelper);
	} // end constructor

	

	@Override
	protected void addTags(Provider provider)
	{
		this.tag(FORGE_WITHER_KEY).add(EntityType.WITHER).add(EntityType.WITHER_SKELETON);
	} // end addTags()
	
} // end class