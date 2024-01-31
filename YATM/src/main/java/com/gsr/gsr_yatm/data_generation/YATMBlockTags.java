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
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMBlockTags extends BlockTagsProvider
{
	private static final String FORGE_ID = "forge";
	
	private static final ITagManager<Block> TM = ForgeRegistries.BLOCKS.tags();
	
	
	
	public static final TagKey<Block> FORGE_BASALT_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "basalt"));
	public static final TagKey<Block> FORGE_CACTUSES_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "cactuses"));
	public static final TagKey<Block> FORGE_CALCITE_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "calcite"));
	public static final TagKey<Block> FORGE_CLAY_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "clay"));
	public static final TagKey<Block> FORGE_DEEPSLATE_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "deepslate"));
	public static final TagKey<Block> FORGE_ROOTED_DIRT_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "rooted_dirt"));
	public static final TagKey<Block> FORGE_SILT_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "silt"));
	public static final TagKey<Block> FORGE_SLABS_QUARTZ = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "slabs/quartz"));
	public static final TagKey<Block> FORGE_SLABS_SANDSTONE = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "slabs/sandstone"));
	public static final TagKey<Block> FORGE_SOUL_SOIL_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "soul_soil"));
	public static final TagKey<Block> FORGE_STAIRS_QUARTZ = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "stairs/quartz"));
	public static final TagKey<Block> FORGE_STAIRS_SANDSTONE = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "stairs/sandstone"));
	public static final TagKey<Block> FORGE_STORAGE_BLOCKS_FOLIAR_STEEL_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "storage_blocks/foliar_steel"));
	public static final TagKey<Block> FORGE_STORAGE_BLOCKS_RUBBER_KEY = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "storage_blocks/rubber"));
	public static final TagKey<Block> FORGE_WALLS_SANDSTONE = TM.createTagKey(new ResourceLocation(YATMBlockTags.FORGE_ID, "walls/sandstone"));
	


	public static final TagKey<Block> ADAMUM_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/adamum_can_grow_in"));
	public static final TagKey<Block> ADAMUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/adamum_can_grow_on"));	
	public static final TagKey<Block> AERIAL_RUBBER_ROOTS_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/aerial_rubber_roots_can_grow_on"));
	public static final TagKey<Block> AERIAL_RUBBER_ROOTS_CAN_GROW_THROUGH_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/aerial_rubber_roots_can_grow_through"));
	public static final TagKey<Block> AURUM_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/aurum_can_grow_in"));
	public static final TagKey<Block> AURUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/aurum_can_grow_on"));
	public static final TagKey<Block> BASIN_OF_TEARS_FLOWERS_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/basin_of_tears_flowers_can_grow_in"));
	public static final TagKey<Block> BASIN_OF_TEARS_FLOWERS_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/basin_of_tears_flowers_can_grow_on"));
	public static final TagKey<Block> BASIN_OF_TEARS_VEGETATION_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/basin_of_tears_vegetation_can_grow_in"));
	public static final TagKey<Block> BASIN_OF_TEARS_VEGETATION_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/basin_of_tears_vegetation_can_grow_on"));
	public static final TagKey<Block> CANDLE_LANTERNS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "candle_lanterns"));	
	public static final TagKey<Block> CANDLELILY_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/candlelily_can_grow_in"));	
	public static final TagKey<Block> CANDLELILY_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/candlelily_can_grow_on"));
	public static final TagKey<Block> CARBUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/carbum_can_grow_on"));	
	public static final TagKey<Block> CARCASS_ROOT_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/carcass_root_can_grow_on"));
	public static final TagKey<Block> CARCASS_ROOT_ROOTED_DIRT_ROOTS_FROM_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/carcass_root_rooted_dirt_roots_from"));
	public static final TagKey<Block> CARCASS_ROOT_ROOTED_NETHERRACK_ROOTS_FROM_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/carcass_root_rooted_netherack_roots_from"));
	public static final TagKey<Block> CHANNEL_NEUTRAL_ONLY_ATTACHMENT_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "change_neutral_only_attachment"));
	public static final TagKey<Block> CUPRUM_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/cuprum_can_grow_in"));
	public static final TagKey<Block> CUPRUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/cuprum_can_grow_on"));
	
	public static final TagKey<Block> DWARF_PERSIMMON_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/dwarf_persimmon_can_grow_in"));
	public static final TagKey<Block> DWARF_PERSIMMON_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/dwarf_persimmon_can_grow_on"));
	
	public static final TagKey<Block> FERRUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/ferrum_can_grow_on"));
	public static final TagKey<Block> FIRE_EATER_LILY_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/fire_eater_lily_can_grow_in"));
	public static final TagKey<Block> FIRE_EATER_LILY_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/fire_eater_lily_can_grow_on"));
	public static final TagKey<Block> FOLIAR_STEEL_ORES_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "foliar_steel_ores"));
	public static final TagKey<Block> FOLIUM_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/folium_can_grow_in"));
	public static final TagKey<Block> FOLIUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/folium_can_grow_on"));	
	public static final TagKey<Block> FORMS_ADAMUM_TOP_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/adamum_top"));	
	public static final TagKey<Block> FORMS_ADAMUM_MIDDLE_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/adamum_middle"));	
	public static final TagKey<Block> FORMS_ADAMUM_BOTTOM_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/adamum_bottom"));	
	public static final TagKey<Block> FORMS_AURUM_HIGHER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/aurum_higher"));	
	public static final TagKey<Block> FORMS_AURUM_LOWER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/aurum_lower"));
	public static final TagKey<Block> FORMS_BUDDING_AMETHYST_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/budding_amethyst"));	
	public static final TagKey<Block> FORMS_CARBUM_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/carbum"));
	public static final TagKey<Block> FORMS_CUPRUM_HIGHER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/cuprum_higher"));	
	public static final TagKey<Block> FORMS_CUPRUM_LOWER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/cuprum_lower"));	
	public static final TagKey<Block> FORMS_FERRUM_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/ferrum"));	
	public static final TagKey<Block> FORMS_FOLIUM_HIGHER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/folium_higher"));	
	public static final TagKey<Block> FORMS_FOLIUM_LOWER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/folium_lower"));
	public static final TagKey<Block> FORMS_INFERNALUM_TOP_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/infernalum_top"));	
	public static final TagKey<Block> FORMS_INFERNALUM_MIDDLE_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/infernalum_middle"));	
	public static final TagKey<Block> FORMS_INFERNALUM_BOTTOM_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/infernalum_bottom"));	
	public static final TagKey<Block> FORMS_LAPUM_INNER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/lapum_inner"));	
	public static final TagKey<Block> FORMS_LAPUM_OUTER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/lapum_outer"));	
	public static final TagKey<Block> FORMS_RUBERUM_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/ruberum"));		
	public static final TagKey<Block> FORMS_SAMARAGDUM_INNER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/samaragdum_inner"));	
	public static final TagKey<Block> FORMS_SAMARAGDUM_OUTER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/samaragdum_outer"));	
	public static final TagKey<Block> FORMS_VICUM_INNER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/vicum_inner"));	
	public static final TagKey<Block> FORMS_VICUM_OUTER_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "form/vicum_outer"));	
	public static final TagKey<Block> GOLEM_LIKE_PLANTS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "golem_like_plants"));
	/* TODO, possibly make the below forge tag instead */ 
	public static final TagKey<Block> HEAT_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "heat_blocks"));	
	public static final TagKey<Block> ICE_CORAL_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/ice_coral_can_grow_in"));
	public static final TagKey<Block> ICE_CORAL_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/ice_coral_can_grow_on"));
	public static final TagKey<Block> INFERNALUM_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/infernalum_can_grow_in"));
	public static final TagKey<Block> INFERNALUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/infernalum_can_grow_on"));	
	public static final TagKey<Block> LAPUM_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/lapum_can_grow_in"));
	public static final TagKey<Block> LAPUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/lapum_can_grow_on"));	
	public static final TagKey<Block> LEAVES_LITER_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "leaves_litter_on"));
	public static final TagKey<Block> MACHINES_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "machines"));
	public static final TagKey<Block> PHANTASMAL_SHELF_FUNGI_SPREAD_TO_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "phantasmal_shelf_fungi_spread_to"));;
	public static final TagKey<Block> PITCHER_CLUSTERS_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/pitcher_clusters_can_grow_on"));
	public static final TagKey<Block> RUBBER_MERISTEM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/rubber_meristem_can_grow_on"));
	public static final TagKey<Block> RUBBER_ROOTS_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/rubber_roots_can_grow_in"));
	public static final TagKey<Block> RUBBER_TREE_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_tree_blocks"));
	public static final TagKey<Block> RUBBER_TREE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_tree_logs"));
	public static final TagKey<Block> RUBBER_TREE_PLANKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_planks"));
	public static final TagKey<Block> RUBBER_TREES_NATURALLY_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/rubber_trees_naturally_grow_on"));
	public static final TagKey<Block> RUBERUM_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/lapum_can_grow_in"));
	public static final TagKey<Block> RUBERUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/lapum_can_grow_on"));	
	public static final TagKey<Block> SAMARAGDUM_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/samaragdum_can_grow_in"));
	public static final TagKey<Block> SAMARAGDUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/samaragdum_can_grow_on"));
	public static final TagKey<Block> SAP_COLLECTORS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "sap_collectors"));
	public static final TagKey<Block> SHULKWART_GROWS_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/shulkwart_grow_on"));
	public static final TagKey<Block> SOIL_MINERALS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soil_minerals"));
	public static final TagKey<Block> SOUL_AFFLICTING_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/soul_afflicting_blocks"));
	public static final TagKey<Block> SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_rubber_tree_blocks"));
	public static final TagKey<Block> SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_afflicted_rubber_tree_logs"));
	public static final TagKey<Block> SOUL_AFFLICTED_RUBBER_TREES_NATURALLY_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/soul_afflicted_rubber_trees_naturally_grow_on"));
	public static final TagKey<Block> SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_afflicted_rubber_planks"));
	public static final TagKey<Block> SOUL_GROUND_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_ground"));
	//	public static final TagKey<Block> SIXTYFOUR_CU_WIRE_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "conduits/sixtyfour_cu"));
	public static final TagKey<Block> UNOXIDIXED_COPPER_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "unoxidized_copper_blocks"));
	public static final TagKey<Block> VERIEGATED_CACTUS_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/veriegated_cactus_can_grow_on"));
	public static final TagKey<Block> VICUM_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/vicum_can_grow_in"));
	public static final TagKey<Block> VICUM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/vicum_can_grow_on"));
	
	
	
	public static final ITag<Block> RUBBER_MERISTEM_CAN_GROW_ON = TM.getTag(YATMBlockTags.RUBBER_MERISTEM_CAN_GROW_ON_KEY);
	public static final ITag<Block> SHULKWART_GROWS_ON = TM.getTag(YATMBlockTags.SHULKWART_GROWS_ON_KEY);
	public static final ITag<Block> SOUL_AFFLICTING_BLOCKS = TM.getTag(YATMBlockTags.SOUL_AFFLICTING_BLOCKS_KEY);
	
	
	
	
	public YATMBlockTags(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(output, lookupProvider, YetAnotherTechMod.MODID, existingFileHelper);
	} // end constructor


	
	@Override
	protected void addTags(Provider provider)
	{
		this.tag(BlockTags.CANDLES).addTag(YATMBlockTags.CANDLE_LANTERNS_KEY);
		this.tag(BlockTags.CLIMBABLE).add(YATMBlocks.CONDUIT_VINES.get()).add(YATMBlocks.SPIDER_VINE.get());
		this.tag(BlockTags.DIRT).add(YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get());
		this.tag(BlockTags.FLOWERS).add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.FIRE_EATER_LILY_DECORATIVE.get()); // maybe eventually add soul rubber flowers too, but seems like they should be special, and I dunno how to modify minecraft entity beehaviours
		this.tag(BlockTags.FLOWER_POTS).add(YATMBlocks.POTTED_RUBBER_MERISTEM.get()).add(YATMBlocks.POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM.get())
		.add(YATMBlocks.POTTED_ADAMUM.get())
		.add(YATMBlocks.POTTED_AURUM.get())
		.add(YATMBlocks.POTTED_CANDLELILY.get())
		.add(YATMBlocks.POTTED_CARBUM.get())
		.add(YATMBlocks.POTTED_CARCASS_ROOT_FOLIAGE.get())
		.add(YATMBlocks.POTTED_CUPRUM.get())
		.add(YATMBlocks.POTTED_FERRUM.get())
		.add(YATMBlocks.POTTED_FIRE_EATER_LILY.get())
		.add(YATMBlocks.POTTED_FIRE_EATER_LILY_UNLIT.get())
		.add(YATMBlocks.POTTED_FOLIUM.get())
		.add(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_POLYP.get()).add(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_YOUNG.get()).add(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_ADOLESCENT.get()).add(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_OLD.get())
		.add(YATMBlocks.POTTED_INFERNALUM.get())
		.add(YATMBlocks.POTTED_LAPUM.get())
		.add(YATMBlocks.POTTED_RUBERUM.get())
		.add(YATMBlocks.POTTED_SAMARAGDUM.get())		
		.add(YATMBlocks.POTTED_VARIEGATED_CACTUS.get())
		.add(YATMBlocks.POTTED_VICUM.get());
		this.tag(BlockTags.LOGS).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY);
		this.tag(BlockTags.LOGS_THAT_BURN).addTag(YATMBlockTags.RUBBER_TREE_LOGS_KEY);
		// TODO, maybe add misc plants to axe tag		
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(YATMBlocks.GRAFTING_TABLE.get()).addTag(YATMBlockTags.SAP_COLLECTORS_KEY).add(YATMBlocks.SPINNING_WHEEL.get());
		this.tag(BlockTags.MINEABLE_WITH_HOE).add(YATMBlocks.RUBBER_LEAVES_YOUNG.get()).add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.RUBBER_LEAVES_OLD.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get());
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get()).addTag(YATMBlockTags.FOLIAR_STEEL_ORES_KEY).add(YATMBlocks.FOLIAR_STEEL_BLOCK.get()).addTag(YATMBlockTags.GOLEM_LIKE_PLANTS_KEY).add(YATMBlocks.LARGE_COPPER_HEAT_SINK.get()).addTag(YATMBlockTags.MACHINES_KEY).add(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get()).add(YATMBlocks.STEEL_TANK.get()).add(YATMBlocks.CHANNEL_VINES.get()).add(YATMBlocks.ADAMUM.get()).add(YATMBlocks.CUPRUM.get()).add(YATMBlocks.INFERNALUM.get()).add(YATMBlocks.LAPUM.get()).add(YATMBlocks.RUBERUM.get()).add(YATMBlocks.SAMARAGDUM.get()).addTag(YATMBlockTags.CANDLE_LANTERNS_KEY);
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get()).add(YATMBlocks.ROOTED_SOUL_SOIL.get());
		this.tag(BlockTags.NEEDS_IRON_TOOL).add(YATMBlocks.AURUM.get()).add(YATMBlocks.FOLIAR_STEEL_BLOCK.get()).addTag(YATMBlockTags.FOLIAR_STEEL_ORES_KEY).add(YATMBlocks.FOLIUM.get()).addTag(YATMBlockTags.MACHINES_KEY).add(YATMBlocks.STEEL_TANK.get()).add(YATMBlocks.CHANNEL_VINES.get()).add(YATMBlocks.RUBERUM.get()).add(YATMBlocks.SAMARAGDUM.get());
		this.tag(BlockTags.NEEDS_STONE_TOOL).add(YATMBlocks.FERRUM.get()).add(YATMBlocks.LARGE_COPPER_HEAT_SINK.get());
		this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(YATMBlocks.ADAMUM.get()).add(YATMBlocks.INFERNALUM.get());
		this.tag(BlockTags.PLANKS).addTag(YATMBlockTags.RUBBER_TREE_PLANKS_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY);
		this.tag(BlockTags.SAPLINGS).add(YATMBlocks.RUBBER_MERISTEM.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get());
		this.tag(BlockTags.SOUL_FIRE_BASE_BLOCKS).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE_GATE.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get()).addTag(YATMBlockTags.FORGE_SOUL_SOIL_KEY);
		this.tag(BlockTags.WOODEN_BUTTONS).add(YATMBlocks.RUBBER_BUTTON.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_BUTTON.get());
		this.tag(BlockTags.WOODEN_DOORS).add(YATMBlocks.RUBBER_DOOR.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get());
		this.tag(BlockTags.WOODEN_FENCES).add(YATMBlocks.RUBBER_FENCE.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get());
		this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(YATMBlocks.RUBBER_PRESSURE_PLATE.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get());
		this.tag(BlockTags.WOODEN_SLABS).add(YATMBlocks.RUBBER_SLAB.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(YATMBlocks.RUBBER_STAIRS.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get());
		this.tag(BlockTags.WOODEN_TRAPDOORS).add(YATMBlocks.RUBBER_TRAPDOOR.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get());
		
		
		
		this.tag(YATMBlockTags.FORGE_BASALT_KEY).add(Blocks.BASALT).add(Blocks.POLISHED_BASALT).add(Blocks.SMOOTH_BASALT);
		this.tag(YATMBlockTags.FORGE_CACTUSES_KEY).add(Blocks.CACTUS).add(YATMBlocks.VARIEGATED_CACTUS.get());
		this.tag(YATMBlockTags.FORGE_CALCITE_KEY).add(Blocks.CALCITE);
		this.tag(YATMBlockTags.FORGE_CLAY_KEY).add(Blocks.CLAY);
		this.tag(YATMBlockTags.FORGE_DEEPSLATE_KEY).addTag(Tags.Blocks.COBBLESTONE_DEEPSLATE).add(Blocks.DEEPSLATE).add(Blocks.DEEPSLATE_BRICKS).add(Blocks.DEEPSLATE_TILES).add(Blocks.CHISELED_DEEPSLATE).add(Blocks.CRACKED_DEEPSLATE_BRICKS).add(Blocks.CRACKED_DEEPSLATE_TILES).add(Blocks.POLISHED_DEEPSLATE);
		this.tag(YATMBlockTags.FORGE_ROOTED_DIRT_KEY).add(Blocks.ROOTED_DIRT).add(Blocks.MUDDY_MANGROVE_ROOTS).add(YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get());
		this.tag(YATMBlockTags.FORGE_SILT_KEY) /*TODO*/;
		this.tag(YATMBlockTags.FORGE_SLABS_QUARTZ).add(Blocks.QUARTZ_SLAB).add(Blocks.SMOOTH_QUARTZ_SLAB);
		this.tag(YATMBlockTags.FORGE_SLABS_SANDSTONE).add(Blocks.SANDSTONE_SLAB).add(Blocks.CUT_SANDSTONE_SLAB).add(Blocks.SMOOTH_SANDSTONE_SLAB).add(Blocks.RED_SANDSTONE_SLAB).add(Blocks.CUT_RED_SANDSTONE_SLAB).add(Blocks.SMOOTH_RED_SANDSTONE_SLAB);
		this.tag(YATMBlockTags.FORGE_SOUL_SOIL_KEY).add(Blocks.SOUL_SOIL).add(YATMBlocks.ROOTED_SOUL_SOIL.get());
		this.tag(YATMBlockTags.FORGE_STAIRS_QUARTZ).add(Blocks.QUARTZ_STAIRS).add(Blocks.SMOOTH_QUARTZ_STAIRS);
		this.tag(YATMBlockTags.FORGE_STAIRS_SANDSTONE).add(Blocks.SANDSTONE_STAIRS).add(Blocks.SMOOTH_SANDSTONE_STAIRS).add(Blocks.RED_SANDSTONE_STAIRS).add(Blocks.SMOOTH_RED_SANDSTONE_STAIRS);
		this.tag(YATMBlockTags.FORGE_STORAGE_BLOCKS_RUBBER_KEY).add(YATMBlocks.RUBBER_BLOCK.get());
		this.tag(YATMBlockTags.FORGE_STORAGE_BLOCKS_FOLIAR_STEEL_KEY).add(YATMBlocks.FOLIAR_STEEL_BLOCK.get());
		this.tag(YATMBlockTags.FORGE_WALLS_SANDSTONE).add(Blocks.SANDSTONE_WALL).add(Blocks.RED_SANDSTONE_WALL);
		this.tag(Tags.Blocks.NETHERRACK).add(Blocks.CRIMSON_NYLIUM).add(Blocks.WARPED_NYLIUM).add(YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get());
		this.tag(Tags.Blocks.ORES).addTag(YATMBlockTags.FOLIAR_STEEL_ORES_KEY);
		this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(YATMBlocks.DEEPSLATE_FOLIAR_STEEL_ORE.get());
		this.tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(YATMBlocks.FOLIAR_STEEL_ORE.get());
		this.tag(Tags.Blocks.ORE_RATES_SINGULAR).addTag(YATMBlockTags.FOLIAR_STEEL_ORES_KEY);
		this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(YATMBlockTags.FORGE_STORAGE_BLOCKS_RUBBER_KEY).addTag(YATMBlockTags.FORGE_STORAGE_BLOCKS_FOLIAR_STEEL_KEY);
		this.tag(Tags.Blocks.STORAGE_BLOCKS_COPPER).add(Blocks.CUT_COPPER).add(Blocks.EXPOSED_COPPER).add(Blocks.EXPOSED_CUT_COPPER).add(Blocks.WEATHERED_COPPER).add(Blocks.WEATHERED_CUT_COPPER).add(Blocks.OXIDIZED_COPPER).add(Blocks.OXIDIZED_CUT_COPPER);
		this.tag(Tags.Blocks.STORAGE_BLOCKS_QUARTZ).add(Blocks.CHISELED_QUARTZ_BLOCK).add(Blocks.QUARTZ_BRICKS).add(Blocks.QUARTZ_PILLAR).add(Blocks.SMOOTH_QUARTZ);
		this.tag(Tags.Blocks.NEEDS_WOOD_TOOL).add(YATMBlocks.CARBUM.get()).add(YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get()).add(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get()).add(YATMBlocks.VICUM.get()).add(YATMBlocks.CUPRUM.get()).add(YATMBlocks.LAPUM.get());
				
		
		
		this.tag(YATMBlockTags.ADAMUM_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.ADAMUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.AERIAL_RUBBER_ROOTS_CAN_GROW_ON_KEY).addTag(YATMBlockTags.RUBBER_TREE_LOGS_KEY).add(YATMBlocks.RUBBER_LEAVES_OLD.get()).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get());
		this.tag(YATMBlockTags.AERIAL_RUBBER_ROOTS_CAN_GROW_THROUGH_KEY).add(Blocks.AIR).add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.RUBBER_LEAVES_YOUNG.get()).add(YATMBlocks.RUBBER_MERISTEM.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get()).add(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get());
		this.tag(YATMBlockTags.AURUM_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.AURUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.BASIN_OF_TEARS_FLOWERS_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.BASIN_OF_TEARS_FLOWERS_CAN_GROW_ON_KEY).addTag(Tags.Blocks.NETHERRACK).addTag(Tags.Blocks.GRAVEL);
		this.tag(YATMBlockTags.BASIN_OF_TEARS_VEGETATION_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.BASIN_OF_TEARS_VEGETATION_CAN_GROW_ON_KEY).addTag(Tags.Blocks.NETHERRACK).addTag(Tags.Blocks.GRAVEL);
		this.tag(YATMBlockTags.CANDLE_LANTERNS_KEY).add(YATMBlocks.CANDLE_LANTERN.get()).add(YATMBlocks.WHITE_CANDLE_LANTERN.get()).add(YATMBlocks.ORANGE_CANDLE_LANTERN.get()).add(YATMBlocks.MAGENTA_CANDLE_LANTERN.get()).add(YATMBlocks.LIGHT_BLUE_CANDLE_LANTERN.get()).add(YATMBlocks.YELLOW_CANDLE_LANTERN.get()).add(YATMBlocks.LIME_CANDLE_LANTERN.get()).add(YATMBlocks.PINK_CANDLE_LANTERN.get()).add(YATMBlocks.GRAY_CANDLE_LANTERN.get()).add(YATMBlocks.LIGHT_GRAY_CANDLE_LANTERN.get()).add(YATMBlocks.CYAN_CANDLE_LANTERN.get()).add(YATMBlocks.PURPLE_CANDLE_LANTERN.get()).add(YATMBlocks.BLUE_CANDLE_LANTERN.get()).add(YATMBlocks.BROWN_CANDLE_LANTERN.get()).add(YATMBlocks.GREEN_CANDLE_LANTERN.get()).add(YATMBlocks.RED_CANDLE_LANTERN.get()).add(YATMBlocks.BLACK_CANDLE_LANTERN.get());
		this.tag(YATMBlockTags.CANDLELILY_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.CANDLELILY_CAN_GROW_ON_KEY).addTag(YATMBlockTags.SOUL_GROUND_KEY);
		this.tag(YATMBlockTags.CARBUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.CARCASS_ROOT_CAN_GROW_ON_KEY)/*.add(Blocks.END_STONE)*/.addTag(Tags.Blocks.NETHERRACK).addTag(BlockTags.DIRT);
		this.tag(YATMBlockTags.CARCASS_ROOT_ROOTED_DIRT_ROOTS_FROM_KEY).addTag(BlockTags.DIRT);
		this.tag(YATMBlockTags.CARCASS_ROOT_ROOTED_NETHERRACK_ROOTS_FROM_KEY).addTag(Tags.Blocks.NETHERRACK);
		this.tag(YATMBlockTags.CHANNEL_NEUTRAL_ONLY_ATTACHMENT_KEY).add(YATMBlocks.CHANNEL_VINES.get());
		this.tag(YATMBlockTags.CUPRUM_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.CUPRUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		// this.tag(YATMBlockTags.DATA_BLOCKS).add(YATMBlocks.DATA_STORAGE_BLOCK.get()).add(YATMBlocks.DATA_SCAN_COLLECTOR.get()).add(YATMBlocks.DESTRUCTIVE_DATA_SCANNER.get()).add(YATMBlocks.DATA_PROCESSOR.get())
		this.tag(YATMBlockTags.DWARF_PERSIMMON_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.DWARF_PERSIMMON_CAN_GROW_ON_KEY).addTag(BlockTags.DIRT);
		this.tag(YATMBlockTags.FERRUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_ON_KEY).addTag(BlockTags.DIRT).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER).add(Blocks.MAGMA_BLOCK);
		this.tag(YATMBlockTags.FOLIAR_STEEL_ORES_KEY).add(YATMBlocks.FOLIAR_STEEL_ORE.get()).add(YATMBlocks.DEEPSLATE_FOLIAR_STEEL_ORE.get());	
		this.tag(YATMBlockTags.FOLIUM_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.FOLIUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.FORMS_ADAMUM_TOP_KEY).addTag(Tags.Blocks.OBSIDIAN);		
		this.tag(YATMBlockTags.FORMS_ADAMUM_MIDDLE_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_DIAMOND);
		this.tag(YATMBlockTags.FORMS_ADAMUM_BOTTOM_KEY).addTag(Tags.Blocks.OBSIDIAN);		
		this.tag(YATMBlockTags.FORMS_AURUM_HIGHER_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_GOLD).addTag(Tags.Blocks.STORAGE_BLOCKS_RAW_GOLD);		
		this.tag(YATMBlockTags.FORMS_AURUM_LOWER_KEY).add(Blocks.GILDED_BLACKSTONE);
		this.tag(YATMBlockTags.FORMS_BUDDING_AMETHYST_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_AMETHYST);
		this.tag(YATMBlockTags.FORMS_CARBUM_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_COAL);
		this.tag(YATMBlockTags.FORMS_CUPRUM_HIGHER_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_COPPER).addTag(Tags.Blocks.STORAGE_BLOCKS_RAW_COPPER);
		this.tag(YATMBlockTags.FORMS_CUPRUM_LOWER_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_COPPER).addTag(Tags.Blocks.STORAGE_BLOCKS_RAW_COPPER);
		this.tag(YATMBlockTags.FORMS_FERRUM_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_IRON).addTag(Tags.Blocks.STORAGE_BLOCKS_RAW_IRON);		
		this.tag(YATMBlockTags.FORMS_FOLIUM_HIGHER_KEY).addTag(YATMBlockTags.FORGE_STORAGE_BLOCKS_FOLIAR_STEEL_KEY);	
		this.tag(YATMBlockTags.FORMS_FOLIUM_LOWER_KEY).addTag(YATMBlockTags.FORGE_STORAGE_BLOCKS_FOLIAR_STEEL_KEY);		
		this.tag(YATMBlockTags.FORMS_INFERNALUM_TOP_KEY).addTag(Tags.Blocks.ORES_NETHERITE_SCRAP);		
		this.tag(YATMBlockTags.FORMS_INFERNALUM_MIDDLE_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_NETHERITE);
		this.tag(YATMBlockTags.FORMS_INFERNALUM_BOTTOM_KEY).addTag(Tags.Blocks.ORES_NETHERITE_SCRAP);		
		this.tag(YATMBlockTags.FORMS_LAPUM_INNER_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_LAPIS);
		this.tag(YATMBlockTags.FORMS_LAPUM_OUTER_KEY).addTag(YATMBlockTags.FORGE_CALCITE_KEY);	
		this.tag(YATMBlockTags.FORMS_RUBERUM_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_REDSTONE);
		this.tag(YATMBlockTags.FORMS_SAMARAGDUM_INNER_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_EMERALD);
		this.tag(YATMBlockTags.FORMS_SAMARAGDUM_OUTER_KEY).addTag(YATMBlockTags.FORGE_DEEPSLATE_KEY);
		this.tag(YATMBlockTags.FORMS_VICUM_INNER_KEY).addTag(Tags.Blocks.STORAGE_BLOCKS_QUARTZ);
		this.tag(YATMBlockTags.FORMS_VICUM_OUTER_KEY).addTag(YATMBlockTags.FORGE_BASALT_KEY);	
		this.tag(YATMBlockTags.GOLEM_LIKE_PLANTS_KEY).add(YATMBlocks.AURUM.get()).add(YATMBlocks.CARBUM.get()).add(YATMBlocks.FERRUM.get()).add(YATMBlocks.FOLIUM.get()).add(YATMBlocks.VICUM.get());		
		this.tag(YATMBlockTags.HEAT_BLOCKS_KEY).add(Blocks.MAGMA_BLOCK).add(Blocks.FIRE).add(Blocks.CAMPFIRE).add(Blocks.LAVA).add(Blocks.LAVA_CAULDRON);
		this.tag(YATMBlockTags.ICE_CORAL_CAN_GROW_IN_KEY).add(Blocks.AIR).add(Blocks.WATER);
		this.tag(YATMBlockTags.ICE_CORAL_CAN_GROW_ON_KEY).addTag(BlockTags.DIRT).addTag(BlockTags.SAND).addTag(Tags.Blocks.GRAVEL);
		this.tag(YATMBlockTags.INFERNALUM_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.INFERNALUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.LAPUM_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.LAPUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.LEAVES_LITER_ON_KEY).addTag(BlockTags.DIRT).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL);
		this.tag(YATMBlockTags.MACHINES_KEY).add(YATMBlocks.STEEL_BIOREACTOR.get()).add(YATMBlocks.STEEL_BOILER.get()).add(YATMBlocks.STEEL_CRUCIBLE.get()).add(YATMBlocks.STEEL_CRYSTALLIZER.get()).add(YATMBlocks.STEEL_HEAT_FURNACE.get()).add(YATMBlocks.STEEL_EXTRACTOR.get()).add(YATMBlocks.STEEL_GRINDER.get()).add(YATMBlocks.STEEL_INJECTOR.get());
		this.tag(YATMBlockTags.PHANTASMAL_SHELF_FUNGI_SPREAD_TO_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY);
		this.tag(YATMBlockTags.PITCHER_CLUSTERS_CAN_GROW_ON_KEY).addTag(BlockTags.DIRT);
		// TODO, fully stripped probably shouldn't be growable on, logically
		this.tag(YATMBlockTags.RUBBER_MERISTEM_CAN_GROW_ON_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.RUBBER_TREES_NATURALLY_GROW_ON_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREES_NATURALLY_GROW_ON_KEY);
		this.tag(YATMBlockTags.RUBBER_ROOTS_CAN_GROW_IN_KEY).addTag(BlockTags.DIRT).addTag(BlockTags.SAND).add(Blocks.CLAY).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL);
		this.tag(YATMBlockTags.RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.RUBBER_TREE_LOGS_KEY).add(YATMBlocks.RUBBER_LEAVES_OLD.get()).add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.RUBBER_LEAVES_YOUNG.get());
		this.tag(YATMBlockTags.RUBBER_TREE_LOGS_KEY).add(YATMBlocks.RUBBER_LOG.get()).add(YATMBlocks.RUBBER_WOOD.get()).add(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get()).add(YATMBlocks.STRIPPED_RUBBER_LOG.get()).add(YATMBlocks.STRIPPED_RUBBER_WOOD.get());
		this.tag(YATMBlockTags.RUBBER_TREE_PLANKS_KEY).add(YATMBlocks.RUBBER_PLANKS.get()).add(YATMBlocks.FANCY_RUBBER_PLANKS.get());
		this.tag(YATMBlockTags.RUBBER_TREES_NATURALLY_GROW_ON_KEY).add(Blocks.DIRT).add(Blocks.GRASS_BLOCK).add(Blocks.FARMLAND).add(Blocks.MUD).add(Blocks.CLAY);
		this.tag(YATMBlockTags.RUBERUM_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.RUBERUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.SAMARAGDUM_CAN_GROW_IN_KEY).add(Blocks.AIR);
		this.tag(YATMBlockTags.SAMARAGDUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.SAP_COLLECTORS_KEY).add(YATMBlocks.SAP_COLLECTOR.get()).add(YATMBlocks.SAP_COLLECTOR_LATEX.get()).add(YATMBlocks.SAP_COLLECTOR_SOUL_SAP.get());
		this.tag(YATMBlockTags.SHULKWART_GROWS_ON_KEY).add(Blocks.CHORUS_PLANT);
		this.tag(YATMBlockTags.SOIL_MINERALS_KEY).addTag(Tags.Blocks.GRAVEL).addTag(Tags.Blocks.SAND).addTag(YATMBlockTags.FORGE_SILT_KEY).addTag(YATMBlockTags.FORGE_CLAY_KEY);
		this.tag(YATMBlockTags.SOUL_AFFLICTING_BLOCKS_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.SOUL_GROUND_KEY);
		// TODO, require non stripped
		// TODO, I get the impression this is for the natural and living tree parts, so roots should be included
		this.tag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY).addTag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_ROOTS.get());
		this.tag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get()).add(YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get()).add(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get()).add(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get());
		this.tag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREES_NATURALLY_GROW_ON_KEY).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL).addTag(YATMBlockTags.RUBBER_TREES_NATURALLY_GROW_ON_KEY);
		this.tag(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_PLANKS.get()).add(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get()).add(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get());
		this.tag(YATMBlockTags.SOUL_GROUND_KEY).addTag(YATMBlockTags.FORGE_SOUL_SOIL_KEY).add(Blocks.SOUL_SAND);
//		this.tag(YATMBlockTags.SIXTYFOUR_CU_WIRE_KEY).add(YATMBlocks.SIXTYFOUR_CU_WIRE.get()).add(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get()).add(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get());
		this.tag(YATMBlockTags.UNOXIDIXED_COPPER_BLOCKS_KEY).add(Blocks.COPPER_BLOCK).add(Blocks.CUT_COPPER);
		this.tag(YATMBlockTags.VERIEGATED_CACTUS_CAN_GROW_ON_KEY).add(YATMBlocks.VARIEGATED_CACTUS.get()).add(Blocks.CACTUS).addTag(BlockTags.SAND); // TODO, add conditions properly to match cactus probably 
		this.tag(YATMBlockTags.VICUM_CAN_GROW_ON_KEY).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER);
		this.tag(YATMBlockTags.VICUM_CAN_GROW_IN_KEY).add(Blocks.AIR);
		
	} // end addTags()
	
} // end class