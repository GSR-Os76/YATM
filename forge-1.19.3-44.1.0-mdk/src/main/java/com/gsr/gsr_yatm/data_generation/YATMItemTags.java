package com.gsr.gsr_yatm.data_generation;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMItemTags extends ItemTagsProvider
{
	private static final String MINECRAFT_ID = "minecraft";
	private static final String FORGE_ID = "forge";
	
	private static final ITagManager<Item> TM = ForgeRegistries.ITEMS.tags();
	
	
	
	public static final TagKey<Item> MINECRAFT_BEACON_PAYMENT_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "beacon_payment_items"));
	public static final TagKey<Item> MINECRAFT_FLOWER_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "flowers"));
	public static final TagKey<Item> MINECRAFT_LOGS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "logs"));
	public static final TagKey<Item> MINECRAFT_LOGS_THAT_BURN_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "logs_that_burn"));
	public static final TagKey<Item> MINECRAFT_PIGLIN_LOVED_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "piglin_loved"));
	public static final TagKey<Item> MINECRAFT_PLANKS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "planks"));
	public static final TagKey<Item> MINECRAFT_SAPLINGS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "saplings"));
	public static final TagKey<Item> MINECRAFT_WOODEN_BUTTONS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "wooden_buttons"));
	public static final TagKey<Item> MINECRAFT_WOODEN_DOORS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "wooden_doors"));
	public static final TagKey<Item> MINECRAFT_WOODEN_FENCES_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "wooden_fences"));
	public static final TagKey<Item> MINECRAFT_WOODEN_PRESSURE_PLATES_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "wooden_pressure_plates"));
	public static final TagKey<Item> MINECRAFT_WOODEN_SLABS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "wooden_slabs"));
	public static final TagKey<Item> MINECRAFT_WOODEN_STAIRS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "wooden_stairs"));
	public static final TagKey<Item> MINECRAFT_WOODEN_TRAPDOORS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "wooden_trapdoors"));

	
	
	
	public static final TagKey<Item> FORGE_RUBBER_STORAGE_BLOCK_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "storage_blocks/rubber"));
	
	// public static final TagKey<Item> FORGE_COPPER_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/copper"));
	// public static final TagKey<Item> FORGE_GOLD_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/gold"));;
	// public static final TagKey<Item> FORGE_IRON_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/iron"));;
	// public static final TagKey<Item> FORGE_NETHERITE_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/netherite"));
	public static final TagKey<Item> FORGE_RUBBER_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/rubber"));
	public static final TagKey<Item> FORGE_SILVER_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/silver"));
	public static final TagKey<Item> FORGE_STEEL_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/steel"));
	
	public static final TagKey<Item> FORGE_COPPER_NUGGETS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "nuggets/copper"));
	public static final TagKey<Item> FORGE_NETHERITE_NUGGETS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "nuggets/netherite"));
	public static final TagKey<Item> FORGE_SILVER_NUGGETS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "nuggets/silver"));
	
	
	
	public static final TagKey<Item> GROWS_PHANTASMAL_SHELF_FUNGI_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "grows_phantasmal_shelf_fungi"));
	public static final TagKey<Item> RUBBER_TREE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_tree_logs"));
	public static final TagKey<Item> RUBBER_TREE_PLANKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_planks"));
	public static final TagKey<Item> UNOXIDIXED_COPPER_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "unoxidized_copper_blocks"));
	public static final TagKey<Item> SIXTYFOUR_CU_WIRES_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "conduits/sixtyfour_cu"));
	public static final TagKey<Item> SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_afflicted_rubber_tree_logs"));
	public static final TagKey<Item> SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_afflicted_rubber_planks"));
	public static final TagKey<Item> SPREADS_PHANTASMAL_SHELF_FUNGI_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "spreads_phantasmal_shelf_fungi"));
	public static final TagKey<Item> WIRE_DIES_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "dies/wire"));
	
	
	
	public static final ITag<Item> GROWS_PHANTASMAL_SHELF_FUNGI = TM.getTag(GROWS_PHANTASMAL_SHELF_FUNGI_KEY);
	public static final ITag<Item> SPREADS_PHANTASMAL_SHELF_FUNGI = TM.getTag(SPREADS_PHANTASMAL_SHELF_FUNGI_KEY);
	
	
	
	public YATMItemTags(PackOutput packOutput, CompletableFuture<Provider> completableFuture, CompletableFuture<TagLookup<Block>> tagsProvider, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(packOutput, completableFuture, tagsProvider, YetAnotherTechMod.MODID, existingFileHelper);
	} // end constructor

	
	
	@Override
	protected void addTags(Provider provider)
	{
		this.copy(YATMBlockTags.MINECRAFT_FLOWERS_KEY, MINECRAFT_FLOWER_KEY);
		this.tag(MINECRAFT_BEACON_PAYMENT_KEY).add(YATMItems.STEEL_INGOT.get()).add(YATMItems.SILVER_INGOT.get());
		this.copy(YATMBlockTags.MINECRAFT_LOGS_KEY, MINECRAFT_LOGS_KEY);
		this.copy(YATMBlockTags.MINECRAFT_LOGS_THAT_BURN_KEY, MINECRAFT_LOGS_THAT_BURN_KEY);
		this.tag(MINECRAFT_PIGLIN_LOVED_KEY).add(YATMItems.SIXTYFOUR_CU_CURRENT_REGULATOR.get()).add(YATMItems.SIXTYFOUR_CU_CURRENT_FUSE.get()).add(YATMItems.SIXTYFOUR_CU_CURRENT_BREAKER.get()).addTag(SIXTYFOUR_CU_WIRES_KEY);
		this.copy(YATMBlockTags.MINECRAFT_PLANKS_KEY, MINECRAFT_PLANKS_KEY);
		this.copy(YATMBlockTags.MINECRAFT_SAPLINGS_KEY, MINECRAFT_SAPLINGS_KEY);
		this.copy(YATMBlockTags.MINECRAFT_WOODEN_BUTTONS_KEY, MINECRAFT_WOODEN_BUTTONS_KEY);
		this.copy(YATMBlockTags.MINECRAFT_WOODEN_DOORS_KEY, MINECRAFT_WOODEN_DOORS_KEY);
		this.copy(YATMBlockTags.MINECRAFT_WOODEN_FENCES_KEY, MINECRAFT_WOODEN_FENCES_KEY);
		this.copy(YATMBlockTags.MINECRAFT_WOODEN_PRESSURE_PLATES_KEY, MINECRAFT_WOODEN_PRESSURE_PLATES_KEY);
		this.copy(YATMBlockTags.MINECRAFT_WOODEN_SLABS_KEY, MINECRAFT_WOODEN_SLABS_KEY);
		this.copy(YATMBlockTags.MINECRAFT_WOODEN_STAIRS_KEY, MINECRAFT_WOODEN_STAIRS_KEY);
		this.copy(YATMBlockTags.MINECRAFT_WOODEN_TRAPDOORS_KEY, MINECRAFT_WOODEN_TRAPDOORS_KEY);
		
		
		this.copy(YATMBlockTags.FORGE_RUBBER_STORAGE_BLOCK_KEY, FORGE_RUBBER_STORAGE_BLOCK_KEY);
		
		this.tag(FORGE_RUBBER_INGOTS_KEY).add(YATMItems.RUBBER_BAR.get());
		this.tag(FORGE_SILVER_INGOTS_KEY).add(YATMItems.SILVER_INGOT.get());
		this.tag(FORGE_STEEL_INGOTS_KEY).add(YATMItems.STEEL_INGOT.get());
		this.tag(FORGE_COPPER_NUGGETS_KEY).add(YATMItems.COPPER_NUGGET.get());
		this.tag(FORGE_NETHERITE_NUGGETS_KEY).add(YATMItems.NETHERITE_NUGGET.get());
		this.tag(FORGE_SILVER_NUGGETS_KEY).add(YATMItems.SILVER_NUGGET.get());
		
		
		
		this.tag(GROWS_PHANTASMAL_SHELF_FUNGI_KEY).addTag(SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY);
		this.copy(YATMBlockTags.RUBBER_TREE_LOGS_KEY, RUBBER_TREE_LOGS_KEY);
		this.copy(YATMBlockTags.RUBBER_TREE_PLANKS_KEY, RUBBER_TREE_PLANKS_KEY);
		this.copy(YATMBlockTags.UNOXIDIXED_COPPER_BLOCKS_KEY, UNOXIDIXED_COPPER_BLOCKS_KEY);
		this.copy(YATMBlockTags.SIXTYFOUR_CU_WIRE_KEY, SIXTYFOUR_CU_WIRES_KEY);
		this.copy(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY, SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY);
		this.copy(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY);
		this.tag(SPREADS_PHANTASMAL_SHELF_FUNGI_KEY).add(YATMItems.SOUL_SYRUP_BOTTLE.get());
		
		this.tag(WIRE_DIES_KEY).add(YATMItems.IRON_WIRE_DIE.get()).add(YATMItems.STEEL_WIRE_DIE.get());		
	} // end addTags

} // end class