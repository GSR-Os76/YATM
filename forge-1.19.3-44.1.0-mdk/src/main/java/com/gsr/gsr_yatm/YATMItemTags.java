package com.gsr.gsr_yatm;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMItemTags extends ItemTagsProvider
{
	private static final String MINECRAFT_ID = "minecraft";
	private static final String FORGE_ID = "forge";
	
	private static final ITagManager<Item> TM = ForgeRegistries.ITEMS.tags();
	
	
	
	public static final TagKey<Item> MINECRAFT_BEACON_PAYMENT_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "beacon_payment_items"));
	public static final TagKey<Item> MINECRAFT_FLOWER_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "flowers"));
	public static final TagKey<Item> MINECRAFT_PIGLIN_LOVED_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "piglin_loved"));
	public static final TagKey<Item> MINECRAFT_SAPLINGS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "saplings"));
	
	public static final TagKey<Item> FORGE_COPPER_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/copper"));
	public static final TagKey<Item> FORGE_NETHERITE_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/netherite"));
	public static final TagKey<Item> FORGE_SILVER_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/silver"));
	public static final TagKey<Item> FORGE_STEEL_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/steel"));
	public static final TagKey<Item> FORGE_COPPER_NUGGETS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "nuggets/copper"));
	public static final TagKey<Item> FORGE_NETHERITE_NUGGETS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "nuggets/netherite"));
	public static final TagKey<Item> FORGE_SILVER_NUGGETS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "nuggets/silver"));


	public static final TagKey<Item> UNOXIDIXED_COPPER_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "unoxidized_copper_blocks"));
	public static final TagKey<Item> SIXTYFOUR_CU_WIRE_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "conduits/sixtyfour_cu"));
	public static final TagKey<Item> WIRE_DIES_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "dies/wire"));
	
	
	
	
	public YATMItemTags(PackOutput packOutput, CompletableFuture<Provider> completableFuture, CompletableFuture<TagLookup<Block>> tagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(packOutput, completableFuture, tagsProvider, modId, existingFileHelper);
	} // end constructor

	
	
	@Override
	protected void addTags(Provider provider)
	{
		this.copy(YATMBlockTags.MINECRAFT_FLOWERS_KEY, MINECRAFT_FLOWER_KEY);
		this.tag(MINECRAFT_BEACON_PAYMENT_KEY).add(YATMItems.STEEL_INGOT.get()).add(YATMItems.SILVER_INGOT.get());
		this.tag(MINECRAFT_PIGLIN_LOVED_KEY).add(YATMItems.SIXTYFOUR_CU_CURRENT_REGULATOR_ITEM.get()).addTag(SIXTYFOUR_CU_WIRE_KEY);
		this.copy(YATMBlockTags.MINECRAFT_SAPLINGS_KEY, MINECRAFT_SAPLINGS_KEY);
		
		this.tag(FORGE_SILVER_INGOTS_KEY).add(YATMItems.SILVER_INGOT.get());
		this.tag(FORGE_STEEL_INGOTS_KEY).add(YATMItems.STEEL_INGOT.get());
		this.tag(FORGE_COPPER_NUGGETS_KEY).add(YATMItems.COPPER_NUGGET.get());
		this.tag(FORGE_NETHERITE_NUGGETS_KEY).add(YATMItems.NETHERITE_NUGGET.get());
		this.tag(FORGE_SILVER_NUGGETS_KEY).add(YATMItems.SILVER_NUGGET.get());
		
		this.copy(YATMBlockTags.UNOXIDIXED_COPPER_BLOCKS_KEY, UNOXIDIXED_COPPER_BLOCKS_KEY);
		this.copy(YATMBlockTags.SIXTYFOUR_CU_WIRE_KEY, SIXTYFOUR_CU_WIRE_KEY);
		this.tag(WIRE_DIES_KEY).add(YATMItems.IRON_WIRE_DIE.get()).add(YATMItems.STEEL_WIRE_DIE.get());
		
	} // end addTags

} // end class
