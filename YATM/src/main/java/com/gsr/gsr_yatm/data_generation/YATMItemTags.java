package com.gsr.gsr_yatm.data_generation;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMItemTags extends ItemTagsProvider
{
	private static final String FORGE_ID = "forge";
	
	private static final ITagManager<Item> TM = ForgeRegistries.ITEMS.tags();
	
	
	
	// it should be better insured copies have the same resource locations where appropriate.
	
//	public static final TagKey<Item> FORGE_COPPER_NUGGETS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "nuggets/copper"));
	public static final TagKey<Item> FORGE_NETHERITE_NUGGETS_KEY = TM.createTagKey(new ResourceLocation(YATMItemTags.FORGE_ID, "nuggets/netherite"));
	public static final TagKey<Item> FORGE_ROOTED_DIRT_KEY = TM.createTagKey(new ResourceLocation(YATMItemTags.FORGE_ID, "rooted_dirt"));
	public static final TagKey<Item> FORGE_RUBBER_INGOTS_KEY = TM.createTagKey(new ResourceLocation(YATMItemTags.FORGE_ID, "ingots/rubber"));
	public static final TagKey<Item> FORGE_STORAGE_BLOCKS_RUBBER_KEY = TM.createTagKey(new ResourceLocation(YATMItemTags.FORGE_ID, "storage_blocks/rubber"));
	public static final TagKey<Item> FORGE_STORAGE_BLOCKS_FOLIAR_STEEL_KEY = TM.createTagKey(new ResourceLocation(YATMItemTags.FORGE_ID, "storage_blocks/foliar_steel"));
	
	//	public static final TagKey<Item> FORGE_SILVER_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/silver"));
//	public static final TagKey<Item> FORGE_SILVER_NUGGETS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "nuggets/silver"));
//	public static final TagKey<Item> FORGE_STEEL_INGOTS_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "ingots/steel"));

	
	
	public static final TagKey<Item> DECAY_NETHERITE_ARMOR_PIECES_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "armor_pieces/decay_netherite"));
	public static final TagKey<Item> DEVICE_ADJUSTERS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "device_adjusters"));
	public static final TagKey<Item> DRILLS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "drills"));
	public static final TagKey<Item> FOLIAR_STEEL_ORES_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "foliar_steel_ores"));
	public static final TagKey<Item> GOLEM_LIKE_PLANT_FORMERS = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "golem_like_plant_formers"));
	public static final TagKey<Item> GROWS_PHANTASMAL_SHELF_FUNGI_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "grows_phantasmal_shelf_fungi"));
	public static final TagKey<Item> LATEX_EXTRACTABLE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "latex_extractable_logs"));
	public static final TagKey<Item> RUBBER_TREE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_tree_logs"));
	public static final TagKey<Item> RUBBER_TREE_PLANKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_planks"));
	public static final TagKey<Item> UNOXIDIXED_COPPER_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "unoxidized_copper_blocks"));
	public static final TagKey<Item> SAWS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "saws"));
	public static final TagKey<Item> SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_afflicted_rubber_tree_logs"));
	public static final TagKey<Item> SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_afflicted_rubber_planks"));
	public static final TagKey<Item> SOUL_ADORNED_NETHERITE_ARMOR_PIECES_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "armor_pieces/soul_adorned_netherite"));
	public static final TagKey<Item> SOUL_SAP_EXTRACTABLE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_sap_extractable_logs"));
	public static final TagKey<Item> SPREADS_PHANTASMAL_SHELF_FUNGI_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "spreads_phantasmal_shelf_fungi"));
	public static final TagKey<Item> WIRE_DIES_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "dies/wire"));
	
	
	public static final ITag<Item> DECAY_NETHERITE_ARMOR_PIECES = TM.getTag(YATMItemTags.DECAY_NETHERITE_ARMOR_PIECES_KEY);
	public static final ITag<Item> SOUL_ADORNED_NETHERITE_ARMOR_PIECES = TM.getTag(YATMItemTags.SOUL_ADORNED_NETHERITE_ARMOR_PIECES_KEY);
	

	
	public YATMItemTags(PackOutput packOutput, CompletableFuture<Provider> completableFuture, CompletableFuture<TagLookup<Block>> tagsProvider, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(packOutput, completableFuture, tagsProvider, YetAnotherTechMod.MODID, existingFileHelper);
	} // end constructor

	
	
	@Override
	protected void addTags(Provider provider)
	{
		this.copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
		this.tag(ItemTags.BEACON_PAYMENT_ITEMS).add(YATMItems.FOLIAR_STEEL.get());
		this.tag(ItemTags.BOATS).add(YATMItems.RUBBER_BOAT_ITEM.get()).add(YATMItems.SOUL_AFFLICTED_RUBBER_BOAT_ITEM.get());
		this.tag(ItemTags.CHEST_BOATS).add(YATMItems.RUBBER_CHEST_BOAT_ITEM.get()).add(YATMItems.SOUL_AFFLICTED_RUBBER_CHEST_BOAT_ITEM.get());
		this.copy(BlockTags.LOGS, ItemTags.LOGS);
		this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		this.tag(ItemTags.PIGLIN_LOVED)/*
										 * .add(YATMItems.SIXTYFOUR_CU_CURRENT_REGULATOR.get()).add(YATMItems.
										 * SIXTYFOUR_CU_CURRENT_FUSE.get()).add(YATMItems.SIXTYFOUR_CU_CURRENT_BREAKER.
										 * get()).addTag(YATMItemTags.SIXTYFOUR_CU_WIRES_KEY)
										 */.add(YATMItems.AURUM_DEMINUTUS_FIDDLE_HEAD.get());
		this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
		this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
		this.tag(ItemTags.TRIMMABLE_ARMOR).addTag(DECAY_NETHERITE_ARMOR_PIECES_KEY).addTag(SOUL_ADORNED_NETHERITE_ARMOR_PIECES_KEY);
		this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
		this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
		
		
		
		
		this.copy(YATMBlockTags.FORGE_ROOTED_DIRT_KEY, YATMItemTags.FORGE_ROOTED_DIRT_KEY);
		this.copy(YATMBlockTags.FORGE_STORAGE_BLOCKS_RUBBER_KEY, YATMItemTags.FORGE_STORAGE_BLOCKS_RUBBER_KEY);
		this.copy(YATMBlockTags.FORGE_STORAGE_BLOCKS_FOLIAR_STEEL_KEY, YATMItemTags.FORGE_STORAGE_BLOCKS_FOLIAR_STEEL_KEY);
		this.tag(YATMItemTags.FORGE_RUBBER_INGOTS_KEY).add(YATMItems.RUBBER_BAR.get());
		this.tag(YATMItemTags.FORGE_NETHERITE_NUGGETS_KEY).add(YATMItems.NETHERITE_NUGGET.get());
		
		
		
		this.tag(YATMItemTags.DECAY_NETHERITE_ARMOR_PIECES_KEY).add(YATMItems.DECAY_NETHERITE_HELMET.get()).add(YATMItems.DECAY_NETHERITE_CHESTPLATE.get()).add(YATMItems.DECAY_NETHERITE_LEGGINGS.get()).add(YATMItems.DECAY_NETHERITE_BOOTS.get());
		this.tag(YATMItemTags.DEVICE_ADJUSTERS_KEY).add(YATMItems.STEEL_WRENCH.get());
		this.tag(YATMItemTags.DRILLS_KEY).add(YATMItems.STEEL_DRILL_WOOD.get()).add(YATMItems.STEEL_DRILL_STONE.get()).add(YATMItems.STEEL_DRILL_IRON.get()).add(YATMItems.STEEL_DRILL_STEEL.get()).add(YATMItems.STEEL_DRILL_GOLD.get()).add(YATMItems.STEEL_DRILL_DIAMOND.get()).add(YATMItems.STEEL_DRILL_NETHERITE.get());
		this.copy(YATMBlockTags.FOLIAR_STEEL_ORES_KEY, YATMItemTags.FOLIAR_STEEL_ORES_KEY);
		this.tag(YATMItemTags.GOLEM_LIKE_PLANT_FORMERS).add(YATMItems.ESSENCE_OF_SOULS_BOTTLE.get());
		this.tag(YATMItemTags.GROWS_PHANTASMAL_SHELF_FUNGI_KEY).addTag(YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY);		
		this.tag(YATMItemTags.LATEX_EXTRACTABLE_LOGS_KEY).add(YATMItems.RUBBER_LOG_ITEM.get()).add(YATMItems.RUBBER_WOOD_ITEM.get());
		this.copy(YATMBlockTags.RUBBER_TREE_LOGS_KEY, YATMItemTags.RUBBER_TREE_LOGS_KEY);
		this.copy(YATMBlockTags.RUBBER_TREE_PLANKS_KEY, YATMItemTags.RUBBER_TREE_PLANKS_KEY);
		this.copy(YATMBlockTags.UNOXIDIXED_COPPER_BLOCKS_KEY, YATMItemTags.UNOXIDIXED_COPPER_BLOCKS_KEY);
		this.tag(YATMItemTags.SAWS_KEY).add(YATMItems.STEEL_SAW_WOOD.get()).add(YATMItems.STEEL_SAW_STONE.get()).add(YATMItems.STEEL_SAW_IRON.get()).add(YATMItems.STEEL_SAW_STEEL.get()).add(YATMItems.STEEL_SAW_GOLD.get()).add(YATMItems.STEEL_SAW_DIAMOND.get()).add(YATMItems.STEEL_SAW_NETHERITE.get());
		this.copy(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY);
		this.copy(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY);
		this.tag(YATMItemTags.SOUL_ADORNED_NETHERITE_ARMOR_PIECES_KEY).add(YATMItems.SOUL_ADORNED_NETHERITE_HELMET.get()).add(YATMItems.SOUL_ADORNED_NETHERITE_CHESTPLATE.get()).add(YATMItems.SOUL_ADORNED_NETHERITE_LEGGINGS.get()).add(YATMItems.SOUL_ADORNED_NETHERITE_BOOTS.get());
		this.tag(YATMItemTags.SOUL_SAP_EXTRACTABLE_LOGS_KEY).add(YATMItems.SOUL_AFFLICTED_RUBBER_LOG_ITEM.get()).add(YATMItems.SOUL_AFFLICTED_RUBBER_WOOD_ITEM.get());

		this.tag(YATMItemTags.SPREADS_PHANTASMAL_SHELF_FUNGI_KEY).add(YATMItems.SOUL_SYRUP_BOTTLE.get());
//		this.tag(YATMItemTags.WIRE_DIES_KEY).add(YATMItems.IRON_WIRE_DIE.get()).add(YATMItems.STEEL_WIRE_DIE.get());		
	} // end addTags

} // end class