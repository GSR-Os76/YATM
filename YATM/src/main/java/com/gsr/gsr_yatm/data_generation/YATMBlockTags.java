package com.gsr.gsr_yatm.data_generation;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.registry.YATMBlocks;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMBlockTags extends BlockTagsProvider
{
	private static final String FORGE_ID = "forge";
	
	private static final ITagManager<Block> TM = ForgeRegistries.BLOCKS.tags();
	
	
	
	public static final TagKey<Block> FORGE_RUBBER_STORAGE_BLOCK_KEY = TM.createTagKey(new ResourceLocation(FORGE_ID, "storage_blocks/rubber"));
	
	
	
	public static final TagKey<Block> AURUM_GROWS_ON = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/aurum_grows_on"));
	public static final TagKey<Block> FORMS_AURUM_DEMINUTUS_LOWER = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/aurum_deminutus_lower"));
	public static final TagKey<Block> FORMS_AURUM_DEMINUTUS_HIGHER = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/aurum_deminutus_higher"));	
	public static final TagKey<Block> LEAVES_LITER_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "leaves_litter_on"));
	public static final TagKey<Block> PHANTASMAL_SHELF_FUNGI_SPREAD_TO_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "phantasmal_shelf_fungi_spread_to"));;
	public static final TagKey<Block> RUBBER_TREE_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_tree_blocks"));
	public static final TagKey<Block> RUBBER_TREE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_tree_logs"));
	public static final TagKey<Block> RUBBER_TREE_PLANKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_planks"));
	public static final TagKey<Block> SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_rubber_tree_blocks"));
	public static final TagKey<Block> SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_afflicted_rubber_tree_logs"));
	public static final TagKey<Block> SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_afflicted_rubber_planks"));
	// TODO, probably pluralize the word "meristem"
	public static final TagKey<Block> RUBBER_MERISTEM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/rubber_meristem_can_grow_on"));
	public static final TagKey<Block> RUBBER_TREES_NATURALLY_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/rubber_trees_naturally_grow_on"));
	public static final TagKey<Block> SOUL_AFFLICTED_RUBBER_TREES_NATURALLY_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/soul_afflicted_rubber_trees_naturally_grow_on"));
	
	public static final TagKey<Block> RUBBER_ROOTS_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/rubber_roots_can_grow_in"));
	public static final TagKey<Block> AERIAL_RUBBER_ROOTS_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/aerial_rubber_roots_can_grow_on"));
	public static final TagKey<Block> AERIAL_RUBBER_ROOTS_CAN_GROW_THROUGH_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/aerial_rubber_roots_can_grow_through"));
	
	public static final TagKey<Block> SOUL_AFFLICTING_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/soul_afflicting_blocks"));
	
	public static final TagKey<Block> SHULKWART_GROWS_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/shulkwart_grow_on"));
	
	public static final TagKey<Block> SIXTYFOUR_CU_WIRE_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "conduits/sixtyfour_cu"));
	
	public static final TagKey<Block> UNOXIDIXED_COPPER_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "unoxidized_copper_blocks"));
	
	
	
	public static final ITag<Block> RUBBER_MERISTEM_CAN_GROW_ON = TM.getTag(RUBBER_MERISTEM_CAN_GROW_ON_KEY);
	public static final ITag<Block> SHULKWART_GROWS_ON = TM.getTag(SHULKWART_GROWS_ON_KEY);
	public static final ITag<Block> SOUL_AFFLICTING_BLOCKS = TM.getTag(SOUL_AFFLICTING_BLOCKS_KEY);
	
	
	
	
	public YATMBlockTags(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(output, lookupProvider, YetAnotherTechMod.MODID, existingFileHelper);
	} // end constructor


	
	@Override
	protected void addTags(Provider provider)
	{
		// TODO, separate soul and regular into individual their log tags, add those tags to here, like vanilla does
		this.tag(BlockTags.FLOWERS).add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get()); // maybe eventually add soul rubber flowers too, but seems like they should be special, and I dunno how to modify minecraft entity beehaviours
		// TODO, make sure these potted blocks constant names and ids are consistent in pattern with minecraft.
		this.tag(BlockTags.FLOWER_POTS).add(YATMBlocks.POTTED_RUBBER_MERISTEM.get()).add(YATMBlocks.POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM.get());
		this.tag(BlockTags.LOGS).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY);//.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get());
		this.tag(BlockTags.LOGS_THAT_BURN).addTag(YATMBlockTags.RUBBER_TREE_LOGS_KEY);
		this.tag(BlockTags.PLANKS).addTag(YATMBlockTags.RUBBER_TREE_PLANKS_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY);
		this.tag(BlockTags.SAPLINGS).add(YATMBlocks.RUBBER_MERISTEM.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get());
		// TODO, maybe remove those blocks without solid tops at all the times?
		this.tag(BlockTags.SOUL_FIRE_BASE_BLOCKS).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get());
		
		this.tag(BlockTags.WOODEN_BUTTONS).add(YATMBlocks.RUBBER_BUTTON.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_BUTTON.get());
		this.tag(BlockTags.WOODEN_DOORS).add(YATMBlocks.RUBBER_DOOR.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get());
		this.tag(BlockTags.WOODEN_FENCES).add(YATMBlocks.RUBBER_FENCE.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get());
		this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(YATMBlocks.RUBBER_PRESSURE_PLATE.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get());
		this.tag(BlockTags.WOODEN_SLABS).add(YATMBlocks.RUBBER_SLAB.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(YATMBlocks.RUBBER_STAIRS.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get());
		this.tag(BlockTags.WOODEN_TRAPDOORS).add(YATMBlocks.RUBBER_TRAPDOOR.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get());
		
		
		
		this.tag(YATMBlockTags.FORGE_RUBBER_STORAGE_BLOCK_KEY).add(YATMBlocks.RUBBER_BLOCK.get());
		
		
		
		this.tag(YATMBlockTags.AURUM_GROWS_ON).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.FORMS_AURUM_DEMINUTUS_LOWER).add(Blocks.GILDED_BLACKSTONE);
		this.tag(YATMBlockTags.FORMS_AURUM_DEMINUTUS_HIGHER).add(Blocks.GOLD_BLOCK);
		this.tag(YATMBlockTags.LEAVES_LITER_ON_KEY).addTag(BlockTags.DIRT).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL);
		this.tag(YATMBlockTags.PHANTASMAL_SHELF_FUNGI_SPREAD_TO_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY);
		// TODO, fully stripped probably shouldn't be growable on, logically
		this.tag(YATMBlockTags.RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.RUBBER_TREE_LOGS_KEY).add(YATMBlocks.RUBBER_LEAVES_OLD.get()).add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.RUBBER_LEAVES_YOUNG.get());
		this.tag(YATMBlockTags.RUBBER_TREE_LOGS_KEY).add(YATMBlocks.RUBBER_LOG.get()).add(YATMBlocks.RUBBER_WOOD.get()).add(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get()).add(YATMBlocks.STRIPPED_RUBBER_LOG.get()).add(YATMBlocks.STRIPPED_RUBBER_WOOD.get());
		this.tag(YATMBlockTags.RUBBER_TREE_PLANKS_KEY).add(YATMBlocks.RUBBER_PLANKS.get()).add(YATMBlocks.FANCY_RUBBER_PLANKS.get());
		this.tag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get());
		this.tag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get()).add(YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get()).add(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get()).add(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get());
		this.tag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_PLANKS.get()).add(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get()).add(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get());
		
		this.tag(YATMBlockTags.RUBBER_MERISTEM_CAN_GROW_ON_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.RUBBER_TREES_NATURALLY_GROW_ON_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREES_NATURALLY_GROW_ON_KEY);
		this.tag(YATMBlockTags.RUBBER_TREES_NATURALLY_GROW_ON_KEY).add(Blocks.DIRT).add(Blocks.GRASS_BLOCK).add(Blocks.FARMLAND).add(Blocks.MUD).add(Blocks.CLAY);
		this.tag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREES_NATURALLY_GROW_ON_KEY).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL).addTag(YATMBlockTags.RUBBER_TREES_NATURALLY_GROW_ON_KEY);
		this.tag(YATMBlockTags.RUBBER_ROOTS_CAN_GROW_IN_KEY).addTag(BlockTags.DIRT).addTag(BlockTags.SAND).add(Blocks.CLAY)
		
		.add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL);
		this.tag(YATMBlockTags.AERIAL_RUBBER_ROOTS_CAN_GROW_ON_KEY).addTag(YATMBlockTags.RUBBER_TREE_LOGS_KEY).add(YATMBlocks.RUBBER_LEAVES_OLD.get()).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get());
		this.tag(YATMBlockTags.AERIAL_RUBBER_ROOTS_CAN_GROW_THROUGH_KEY).add(Blocks.AIR).add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.RUBBER_LEAVES_YOUNG.get()).add(YATMBlocks.RUBBER_MERISTEM.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get()).add(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get());
		
		this.tag(YATMBlockTags.SHULKWART_GROWS_ON_KEY).add(Blocks.CHORUS_PLANT);
		
		//.addTag(BlockTags.FLOWERS).add(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH);

		//		AERIAL_RUBBER_ROOTS_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/aerial_rubber_roots_can_grow_on"));
//		public static final TagKey<Block> AERIAL_RUBBER_ROOTS_CAN_GROW_IN_KEY
		
		// add soil fire base blocks, and soul fire related things, if nearby chance afflict too, not just on
		this.tag(SOUL_AFFLICTING_BLOCKS_KEY).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL).addTag(SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY);
		this.tag(SIXTYFOUR_CU_WIRE_KEY).add(YATMBlocks.SIXTYFOUR_CU_WIRE.get()).add(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get()).add(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get());
		this.tag(UNOXIDIXED_COPPER_BLOCKS_KEY).add(Blocks.COPPER_BLOCK).add(Blocks.CUT_COPPER);
	} // end addTags()
	
} // end class