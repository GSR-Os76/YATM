package com.gsr.gsr_yatm.registry;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.YATMFoods;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.armor.YATMArmorMaterials;
import com.gsr.gsr_yatm.creative.CreativeFluidSourceItem;
import com.gsr.gsr_yatm.creative.CreativeFluidStorerItem;
import com.gsr.gsr_yatm.creative.CreativeFluidVoidItem;
import com.gsr.gsr_yatm.entity.boat.YATMBoatType;
import com.gsr.gsr_yatm.item.BonusType;
import com.gsr.gsr_yatm.item.DrinkableItem;
import com.gsr.gsr_yatm.item.EfficiencyUpgradeItem;
import com.gsr.gsr_yatm.item.ShulkwartHornItem;
import com.gsr.gsr_yatm.item.ShulkwartSporesBlockItem;
import com.gsr.gsr_yatm.item.SpeedUpgradeItem;
import com.gsr.gsr_yatm.item.YATMBoatItem;
import com.gsr.gsr_yatm.item.component.current_heater.CurrentHeaterItem;
import com.gsr.gsr_yatm.item.component.current_storer.CurrentStorerItem;
import com.gsr.gsr_yatm.item.component.fluid_pass_through.FluidPassThroughBlockItem;
import com.gsr.gsr_yatm.item.fluid.DrinkableFluidBottleItem;
import com.gsr.gsr_yatm.item.fluid.EssenceOfSoulsBottleItem;
import com.gsr.gsr_yatm.item.fluid.EssenceOfSoulsBucketItem;
import com.gsr.gsr_yatm.item.tool.DrillItem;
import com.gsr.gsr_yatm.item.tool.PoweredToolItemStack;
import com.gsr.gsr_yatm.item.tool.SawItem;
import com.gsr.gsr_yatm.utilities.PrimitiveUtil;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YetAnotherTechMod.MODID);

	
	// possibly generate similar sets through a method, providing greater resilience to changes to creation.
	public static final RegistryObject<BlockItem> RUBBER_LOG = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_log", () -> new BlockItem(YATMBlocks.RUBBER_LOG.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_WOOD = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_wood", () -> new BlockItem(YATMBlocks.RUBBER_WOOD.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> PARTIALLY_STRIPPED_RUBBER_LOG = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("partially_stripped_rubber_log", () -> new BlockItem(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> STRIPPED_RUBBER_LOG = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("stripped_rubber_log", () -> new BlockItem(YATMBlocks.STRIPPED_RUBBER_LOG.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> STRIPPED_RUBBER_WOOD = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("stripped_rubber_wood", () -> new BlockItem(YATMBlocks.STRIPPED_RUBBER_WOOD.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_PLANKS = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_planks", () -> new BlockItem(YATMBlocks.RUBBER_PLANKS.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> FANCY_RUBBER_PLANKS = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("fancy_rubber_planks", () -> new BlockItem(YATMBlocks.FANCY_RUBBER_PLANKS.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_STAIRS = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_stairs", () -> new BlockItem(YATMBlocks.RUBBER_STAIRS.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_SLAB = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_slab", () -> new BlockItem(YATMBlocks.RUBBER_SLAB.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_FENCE = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_fence", () -> new BlockItem(YATMBlocks.RUBBER_FENCE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_FENCE_GATE = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_fence_gate", () -> new BlockItem(YATMBlocks.RUBBER_FENCE_GATE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_DOOR = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_door", () -> new BlockItem(YATMBlocks.RUBBER_DOOR.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_TRAPDOOR = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_trapdoor", () -> new BlockItem(YATMBlocks.RUBBER_TRAPDOOR.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_PRESSURE_PLATE = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_pressure_plate", () -> new BlockItem(YATMBlocks.RUBBER_PRESSURE_PLATE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_BUTTON = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_button", () -> new BlockItem(YATMBlocks.RUBBER_BUTTON.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> LEAF_MULCH = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("leaf_mulch", () -> new BlockItem(YATMBlocks.LEAF_MULCH.get(), new Item.Properties()))));


	
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_LOG = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_log", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_WOOD = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_wood", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_partially_stripped_rubber_log", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_STRIPPED_RUBBER_LOG = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_stripped_rubber_log", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_stripped_rubber_wood", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_PLANKS = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_planks", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_PLANKS.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_fancy_rubber_planks", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_fancy_rubber_planks_tiled", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_STAIRS = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_stairs", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_SLAB = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_slab", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_FENCE = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_fence", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_FENCE_GATE = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_fence_gate", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE_GATE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_DOOR = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_door", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_TRAPDOOR = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_trapdoor", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_pressure_plate", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_BUTTON = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_button", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_BUTTON.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_LEAF_MULCH = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_leaf_mulch", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get(), new Item.Properties()))));

	public static final RegistryObject<BlockItem> RUBBER_BLOCK = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_block", () -> new BlockItem(YATMBlocks.RUBBER_BLOCK.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> FOLIAR_STEEL_BLOCK = buildingTabEnqueue(yatmGeTabEnqueue(ITEMS.register("foliar_steel_block", () -> new BlockItem(YATMBlocks.FOLIAR_STEEL_BLOCK.get(), new Item.Properties()))));
	
	public static final RegistryObject<BlockItem> CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("candle_lantern", () -> new BlockItem(YATMBlocks.CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> WHITE_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("white_candle_lantern", () -> new BlockItem(YATMBlocks.WHITE_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> LIGHT_GRAY_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("light_gray_candle_lantern", () -> new BlockItem(YATMBlocks.LIGHT_GRAY_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> GRAY_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("gray_candle_lantern", () -> new BlockItem(YATMBlocks.GRAY_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> BLACK_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("black_candle_lantern", () -> new BlockItem(YATMBlocks.BLACK_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> BROWN_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("brown_candle_lantern", () -> new BlockItem(YATMBlocks.BROWN_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> RED_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("red_candle_lantern", () -> new BlockItem(YATMBlocks.RED_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> ORANGE_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("orange_candle_lantern", () -> new BlockItem(YATMBlocks.ORANGE_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> YELLOW_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("yellow_candle_lantern", () -> new BlockItem(YATMBlocks.YELLOW_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> LIME_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("lime_candle_lantern", () -> new BlockItem(YATMBlocks.LIME_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> GREEN_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("green_candle_lantern", () -> new BlockItem(YATMBlocks.GREEN_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> CYAN_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("cyan_candle_lantern", () -> new BlockItem(YATMBlocks.CYAN_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> LIGHT_BLUE_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("light_blue_candle_lantern", () -> new BlockItem(YATMBlocks.LIGHT_BLUE_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> BLUE_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("blue_candle_lantern", () -> new BlockItem(YATMBlocks.BLUE_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> PURPLE_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("purple_candle_lantern", () -> new BlockItem(YATMBlocks.PURPLE_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> MAGENTA_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("magenta_candle_lantern", () -> new BlockItem(YATMBlocks.MAGENTA_CANDLE_LANTERN.get(), new Item.Properties())))));
	public static final RegistryObject<BlockItem> PINK_CANDLE_LANTERN = functionalTabEnqueue(colorTabEnqueue(yatmGeTabEnqueue(ITEMS.register("pink_candle_lantern", () -> new BlockItem(YATMBlocks.PINK_CANDLE_LANTERN.get(), new Item.Properties())))));
	
	public static final RegistryObject<BlockItem> FOLIAR_STEEL_ORE = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("foliar_steel_ore", () -> new BlockItem(YATMBlocks.FOLIAR_STEEL_ORE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> DEEPSLATE_FOLIAR_STEEL_ORE = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("deepslate_foliar_steel_ore", () -> new BlockItem(YATMBlocks.DEEPSLATE_FOLIAR_STEEL_ORE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> ROOTED_SOUL_SOIL = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rooted_soul_soil", () -> new BlockItem(YATMBlocks.ROOTED_SOUL_SOIL.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> CARCASS_ROOT_ROOTED_DIRT = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("carcass_root_rooted_dirt", () -> new BlockItem(YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> CARCASS_ROOT_ROOTED_NETHERRACK = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("carcass_root_rooted_netherrack", () -> new BlockItem(YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get(), new Item.Properties()))));
	
	
	
	public static final RegistryObject<BlockItem> RUBBER_ROOTS = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_roots", () -> new BlockItem(YATMBlocks.RUBBER_ROOTS.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_ROOTS = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_roots", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_ROOTS.get(), new Item.Properties()))));
	
	public static final RegistryObject<BlockItem> RUBBER_LEAVES_YOUNG = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_leaves_young", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_YOUNG.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_LEAVES_FLOWERING = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_leaves_flowering", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_FLOWERING.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> RUBBER_LEAVES_OLD = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_leaves_old", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_OLD.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_leaves_young", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_leaves_flowering", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_LEAVES_OLD = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_leaves_old", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), new Item.Properties()))));
	
	public static final RegistryObject<BlockItem> RUBBER_MERISTEM = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_meristem", () -> new BlockItem(YATMBlocks.RUBBER_MERISTEM.get(), new Item.Properties()))));	
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_MERISTEM = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_meristem", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), new Item.Properties()))));

	public static final RegistryObject<ItemNameBlockItem> CARCASS_ROOT_CUTTING = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("carcass_root_cutting", () -> new ItemNameBlockItem(YATMBlocks.CARCASS_ROOT_FOLIAGE.get(), new Item.Properties()))));
	
	// TODO, and redraw items for the pitcher clusters and phantasmal shelf fungus, and maybe spider vine fruit branch thingy.
	public static final RegistryObject<BlockItem> ADAMUM_MERISTEM = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("adamum_meristem", () -> new BlockItem(YATMBlocks.ADAMUM.get(), new Item.Properties().fireResistant()))));
	public static final RegistryObject<BlockItem> AURUM_DEMINUTUS_FIDDLE_HEAD = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("aurum_deminutus_fiddle_head", () -> new BlockItem(YATMBlocks.AURUM.get(), new Item.Properties().fireResistant()))));
	public static final RegistryObject<BlockItem> CARBUM_MERISTEM = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("carbum_meristem", () -> new BlockItem(YATMBlocks.CARBUM.get(), new Item.Properties().fireResistant()))));
	public static final RegistryObject<BlockItem> CUPRUM_BULB = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("cuprum_bulb", () -> new BlockItem(YATMBlocks.CUPRUM.get(), new Item.Properties().fireResistant()))));
	public static final RegistryObject<BlockItem> FERRUM_ROOTSTOCK = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("ferrum_rootstock", () -> new BlockItem(YATMBlocks.FERRUM.get(), new Item.Properties().fireResistant()))));
	public static final RegistryObject<BlockItem> FOLIUM_RHIZOME = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("folium_rhizome", () -> new BlockItem(YATMBlocks.FOLIUM.get(), new Item.Properties().fireResistant()))));
	public static final RegistryObject<BlockItem> INFERNALUM_RHIZOME = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("infernalum_rhizome", () -> new BlockItem(YATMBlocks.INFERNALUM.get(), new Item.Properties().fireResistant()))));
	public static final RegistryObject<BlockItem> LAPUM_MERISTEM = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("lapum_meristem", () -> new BlockItem(YATMBlocks.LAPUM.get(), new Item.Properties().fireResistant()))));
	public static final RegistryObject<BlockItem> RUBERUM_CORM = redstoneTabEnqueue(naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("ruberum_corm", () -> new BlockItem(YATMBlocks.RUBERUM.get(), new Item.Properties().fireResistant())))));
	public static final RegistryObject<BlockItem> SAMARAGDUM_NODULE = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("samaragdum_nodule", () -> new BlockItem(YATMBlocks.SAMARAGDUM.get(), new Item.Properties().fireResistant()))));
	public static final RegistryObject<BlockItem> VICUM_MERISTEM = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("vicum_meristem", () -> new BlockItem(YATMBlocks.VICUM.get(), new Item.Properties()))));
	


	public static final RegistryObject<BlockItem> CANDLELILY = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("candlelily", () -> new BlockItem(YATMBlocks.CANDLELILY.get(), new Item.Properties()))));
	
	public static final RegistryObject<BlockItem> BLEACHED_ICE_CORAL_OLD = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("bleached_ice_coral_old", () -> new BlockItem(YATMBlocks.BLEACHED_ICE_CORAL_OLD.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> BLEACHED_ICE_CORAL_ADOLESCENT = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("bleached_ice_coral_adolescent", () -> new BlockItem(YATMBlocks.BLEACHED_ICE_CORAL_ADOLESCENT.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> BLEACHED_ICE_CORAL_YOUNG = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("bleached_ice_coral_young", () -> new BlockItem(YATMBlocks.BLEACHED_ICE_CORAL_YOUNG.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> BLEACHED_ICE_CORAL_POLYP = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("bleached_ice_coral_polyp", () -> new BlockItem(YATMBlocks.BLEACHED_ICE_CORAL_POLYP.get(), new Item.Properties()))));
	
	// public static final RegistryObject<Item> FIRE_EATER_LILY_FOLIAGE = generalTabEnqueue(ITEMS.register("fire_eater_lily_foliage", () -> new Item(new Item.Properties())));
	public static final RegistryObject<BlockItem> FIRE_EATER_LILY_DECORATIVE = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("fire_eater_lily_decorative", () -> new BlockItem(YATMBlocks.FIRE_EATER_LILY_DECORATIVE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> FIRE_EATER_LILY_UNLIT_DECORATIVE = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("fire_eater_lily_unlit_decorative", () -> new BlockItem(YATMBlocks.FIRE_EATER_LILY_UNLIT_DECORATIVE.get(), new Item.Properties()))));
	
	public static final RegistryObject<BlockItem> PHANTASMAL_SHELF_FUNGUS = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("phantasmal_shelf_fungus", () -> new BlockItem(YATMBlocks.PHANTASMAL_SHELF_FUNGUS.get(), new Item.Properties()))));
	
	public static final RegistryObject<BlockItem> PITCHER_CLUSTER = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("pitcher_cluster", () -> new BlockItem(YATMBlocks.PITCHER_CLUSTER.get(), new Item.Properties()))));

	public static final RegistryObject<BlockItem> VARIEGATED_CACTUS = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("variegated_cactus", () -> new BlockItem(YATMBlocks.VARIEGATED_CACTUS.get(), new Item.Properties()))));
	
	
	
	public static final RegistryObject<ItemNameBlockItem> PRISMARINE_CRYSTAL_MOSS_SPORES = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("prismarine_crystal_moss_spores", () -> new ItemNameBlockItem(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get(), new Item.Properties()))));
	
	public static final RegistryObject<ShulkwartSporesBlockItem> SHULKWART_SPORES = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("shulkwart_spores", () -> new ShulkwartSporesBlockItem(new Item.Properties()
			, YATMBlocks.SHULKWART.get()
			, YATMBlocks.WHITE_SHULKWART.get()
			, YATMBlocks.ORANGE_SHULKWART.get()
			, YATMBlocks.MAGENTA_SHULKWART.get()
			, YATMBlocks.LIGHT_BLUE_SHULKWART.get()
			, YATMBlocks.YELLOW_SHULKWART.get()
			, YATMBlocks.LIME_SHULKWART.get()
			, YATMBlocks.PINK_SHULKWART.get()
			, YATMBlocks.GRAY_SHULKWART.get()
			, YATMBlocks.LIGHT_GRAY_SHULKWART.get()
			, YATMBlocks.CYAN_SHULKWART.get()
			, YATMBlocks.PURPLE_SHULKWART.get()
			, YATMBlocks.BLUE_SHULKWART.get()
			, YATMBlocks.BROWN_SHULKWART.get()
			, YATMBlocks.GREEN_SHULKWART.get()
			, YATMBlocks.RED_SHULKWART.get()
			, YATMBlocks.BLACK_SHULKWART.get()))));
	
	public static final RegistryObject<ItemNameBlockItem> ICE_CORAL_POLYP = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("ice_coral_polyp", () -> new ItemNameBlockItem(YATMBlocks.ICE_CORAL.get(), new Item.Properties()))));
	
	public static final RegistryObject<ItemNameBlockItem> BASIN_OF_TEARS_SEED = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("basin_of_tears_seed", () -> new ItemNameBlockItem(YATMBlocks.BASIN_OF_TEARS_VEGETATION.get(), new Item.Properties()))));
	
	public static final RegistryObject<ItemNameBlockItem> COTTON_SEEDS = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("cotton_seeds", () -> new ItemNameBlockItem(YATMBlocks.COTTON.get(), new Item.Properties()))));

	public static final RegistryObject<ItemNameBlockItem> FIRE_EATER_LILY_BULB = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("fire_eater_lily_bulb", () -> new ItemNameBlockItem(YATMBlocks.FIRE_EATER_LILY.get(), new Item.Properties()))));
	
	public static final RegistryObject<ItemNameBlockItem> SPIDER_VINE_FRUITS = naturalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("spider_vine_fruits", () -> new ItemNameBlockItem(YATMBlocks.SPIDER_VINE_MERISTEM.get(), new Item.Properties()))));
	
	
	
	public static final RegistryObject<BlockItem> HANGING_POT_HOOK = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("hanging_pot_hook", () -> new BlockItem(YATMBlocks.HANGING_POT_HOOK.get(), new Item.Properties()))));
	
	public static final RegistryObject<BlockItem> GRAFTING_TABLE = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("grafting_table", () -> new BlockItem(YATMBlocks.GRAFTING_TABLE.get(), new Item.Properties()))));
	
	public static final RegistryObject<BlockItem> SAP_COLLECTOR = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("sap_collector", () -> new BlockItem(YATMBlocks.SAP_COLLECTOR.get(), new Item.Properties()))));
	// add celestial altar
	// add liquid celestial light, liquid lunar light, and the liquid solar liquids;
	public static final RegistryObject<BlockItem> SPINNING_WHEEL = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("spinning_wheel", () -> new BlockItem(YATMBlocks.SPINNING_WHEEL.get(), new Item.Properties()))));
	
	public static final RegistryObject<SignItem> RUBBER_SIGN = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_sign", () -> new SignItem(new Item.Properties().stacksTo(16), YATMBlocks.RUBBER_SIGN.get(), YATMBlocks.RUBBER_WALL_SIGN.get()))));
	public static final RegistryObject<HangingSignItem> RUBBER_HANGING_SIGN = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_hanging_sign", () -> new HangingSignItem(YATMBlocks.RUBBER_HANGING_SIGN.get(), YATMBlocks.RUBBER_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)))));
	public static final RegistryObject<SignItem> SOUL_AFFLICTED_RUBBER_SIGN = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_sign", () -> new SignItem(new Item.Properties().stacksTo(16), YATMBlocks.SOUL_AFFLICTED_RUBBER_SIGN.get(), YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_SIGN.get()))));
	public static final RegistryObject<HangingSignItem> SOUL_AFFLICTED_RUBBER_HANGING_SIGN = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_hanging_sign", () -> new HangingSignItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_HANGING_SIGN.get(), YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)))));
	
	public static final RegistryObject<BlockItem> LARGE_COPPER_HEAT_SINK = /* generalTabEnqueue */(ITEMS.register("large_copper_heat_sink", () -> new BlockItem(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), new Item.Properties())));

	public static final RegistryObject<BlockItem> DATA_STORAGE_BLOCK = /* generalTabEnqueue */(ITEMS.register("data_storage_block", () -> new BlockItem(YATMBlocks.DATA_STORAGE_BLOCK.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> DATA_SCAN_COLLECTOR = /* generalTabEnqueue */(ITEMS.register("data_scan_collector", () -> new BlockItem(YATMBlocks.DATA_SCAN_COLLECTOR.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> DESTRUCTIVE_DATA_SCANNER = /* generalTabEnqueue */(ITEMS.register("destructive_data_scanner", () -> new BlockItem(YATMBlocks.DESTRUCTIVE_DATA_SCANNER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> DATA_PROCESSOR = /* generalTabEnqueue */(ITEMS.register("data_processor", () -> new BlockItem(YATMBlocks.DATA_PROCESSOR.get(), new Item.Properties())));
	
	public static final RegistryObject<BlockItem> STEEL_BIOREACTOR = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_bioreactor", () -> new BlockItem(YATMBlocks.STEEL_BIOREACTOR.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> STEEL_BOILER = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_boiler", () -> new BlockItem(YATMBlocks.STEEL_BOILER.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> STEEL_CRUCIBLE = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_crucible", () -> new BlockItem(YATMBlocks.STEEL_CRUCIBLE.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> STEEL_CRYSTALLIZER = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_crystallizer", () -> new BlockItem(YATMBlocks.STEEL_CRYSTALLIZER.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> STEEL_CURRENT_FURNACE = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_current_furnace", () -> new BlockItem(YATMBlocks.STEEL_CURRENT_FURNACE.get(), new Item.Properties()))));	
	public static final RegistryObject<BlockItem> STEEL_EXTRACTOR = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_extractor", () -> new BlockItem(YATMBlocks.STEEL_EXTRACTOR.get(), new Item.Properties()))));
//	public static final RegistryObject<BlockItem> STEEL_EXTRUDER = /* generalTabEnqueue */(ITEMS.register("steel_extruder", () -> new BlockItem(YATMBlocks.STEEL_EXTRUDER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_GRINDER = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_grinder", () -> new BlockItem(YATMBlocks.STEEL_GRINDER.get(), new Item.Properties()))));
	public static final RegistryObject<BlockItem> STEEL_INJECTOR = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_injector", () -> new BlockItem(YATMBlocks.STEEL_INJECTOR.get(), new Item.Properties()))));

	public static final RegistryObject<BlockItem> STEEL_TANK = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_tank", () -> new BlockItem(YATMBlocks.STEEL_TANK.get(), new Item.Properties()))));
	
	public static final RegistryObject<BlockItem> C_U_F_E_I = /* generalTabEnqueue */(ITEMS.register("current_unit_forge_energy_interchanger", () -> new BlockItem(YATMBlocks.C_U_F_E_I.get(), new Item.Properties())));

	public static final RegistryObject<BlockItem> CRUDE_BATTERY_SOLAR_PANEL = /* generalTabEnqueue */(ITEMS.register("crude_battery_solar_panel", () -> new BlockItem(YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ADVANCED_BATTERY_SOLAR_PANEL = /* generalTabEnqueue */(ITEMS.register("advanced_battery_crude_solar_panel", () -> new BlockItem(YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL = /* generalTabEnqueue */(ITEMS.register("suns_complement_battery_solar_panel", () -> new BlockItem(YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> CRUDE_SOLAR_PANEL = /* generalTabEnqueue */(ITEMS.register("crude_solar_panel", () -> new BlockItem(YATMBlocks.CRUDE_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ADVANCED_SOLAR_PANEL = /* generalTabEnqueue */(ITEMS.register("advanced_crude_solar_panel", () -> new BlockItem(YATMBlocks.ADVANCED_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SUNS_COMPLEMENT_SOLAR_PANEL = /* generalTabEnqueue */(ITEMS.register("suns_complement_solar_panel", () -> new BlockItem(YATMBlocks.SUNS_COMPLEMENT_SOLAR_PANEL.get(), new Item.Properties())));

	public static final RegistryObject<FluidPassThroughBlockItem> CHANNEL_VINES = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("channel_vines", () -> new FluidPassThroughBlockItem(YATMBlocks.CHANNEL_VINES.get(), new Item.Properties()))));
	
	// TODO, how to acquire?
	public static final RegistryObject<BlockItem> CONDUIT_VINES = functionalTabEnqueue(yatmGeTabEnqueue(ITEMS.register("conduit_vines", () -> new BlockItem(YATMBlocks.CONDUIT_VINES.get(), new Item.Properties()))));
	

	
	public static final RegistryObject<Item> FOLIAR_STEEL_SHRED = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("foliar_steel_shred", () -> new Item(new Item.Properties()))));
	public static final RegistryObject<Item> FOLIAR_STEEL = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("foliar_steel", () -> new Item(new Item.Properties()))));
	public static final RegistryObject<Item> RUBBER_BAR = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_bar", () -> new Item(new Item.Properties()))));
	
	public static final RegistryObject<Item> ENDOTHERMIC_TISSUE = /* generalTabEnqueue */(ITEMS.register("endothermic_tissue", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> RAW_EXOTHEMIC_GLAND = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("raw_exothermic_gland", () -> new Item(new Item.Properties()))));
	public static final RegistryObject<Item> KINETIC_DRIVER = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("kinetic_driver", () -> new Item(new Item.Properties()))));
	
	public static final RegistryObject<Item> COTTON_BOLLS = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("cotton_bolls", () -> new Item(new Item.Properties().craftRemainder(YATMItems.COTTON_SEEDS.get())))));
	public static final RegistryObject<Item> RAW_COTTON_FIBER = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("raw_cotton_fiber", () -> new Item(new Item.Properties()))));
	
	public static final RegistryObject<ShulkwartHornItem> SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), (DyeColor)null))));
	public static final RegistryObject<ShulkwartHornItem> WHITE_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("white_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.WHITE))));
	public static final RegistryObject<ShulkwartHornItem> ORANGE_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("orange_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.ORANGE))));
	public static final RegistryObject<ShulkwartHornItem> MAGENTA_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("magenta_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.MAGENTA))));
	public static final RegistryObject<ShulkwartHornItem> LIGHT_BLUE_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("light_blue_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.LIGHT_BLUE))));
	public static final RegistryObject<ShulkwartHornItem> YELLOW_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("yellow_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.YELLOW))));
	public static final RegistryObject<ShulkwartHornItem> LIME_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("lime_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.LIME))));
	public static final RegistryObject<ShulkwartHornItem> PINK_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("pink_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.PINK))));
	public static final RegistryObject<ShulkwartHornItem> GRAY_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("gray_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.GRAY))));
	public static final RegistryObject<ShulkwartHornItem> LIGHT_GRAY_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("light_gray_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.LIGHT_GRAY))));
	public static final RegistryObject<ShulkwartHornItem> CYAN_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("cyan_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.CYAN))));
	public static final RegistryObject<ShulkwartHornItem> PURPLE_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("purple_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.PURPLE))));
	public static final RegistryObject<ShulkwartHornItem> BLUE_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("blue_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.BLUE))));
	public static final RegistryObject<ShulkwartHornItem> BROWN_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("brown_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.BROWN))));
	public static final RegistryObject<ShulkwartHornItem> GREEN_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("green_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.GREEN))));
	public static final RegistryObject<ShulkwartHornItem> RED_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("red_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.RED))));
	public static final RegistryObject<ShulkwartHornItem> BLACK_SHULKWART_HORN = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("black_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.BLACK))));
	
	public static final RegistryObject<Item> WOOD_PULP = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("wood_pulp", () -> new Item(new Item.Properties()))));
	
	public static final RegistryObject<Item> NETHERITE_NUGGET = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("netherite_nugget", () -> new Item(new Item.Properties()))));
	public static final RegistryObject<Item> STAR_SEED = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("star_seed", () -> new Item(new Item.Properties()))));
	public static final RegistryObject<Item> STAR_GERMLING = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("star_germling", () -> new Item(new Item.Properties()))));
	public static final RegistryObject<Item> STAR_SPROUT = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("star_sprout", () -> new Item(new Item.Properties()))));
	public static final RegistryObject<Item> STAR_ADOLESCENT = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("star_adolescent", () -> new Item(new Item.Properties()))));
	
	public static final RegistryObject<Item> WOODEN_DRILL_BIT = /* generalTabEnqueue */(ITEMS.register("wooden_drill_bit", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STONE_DRILL_BIT = /* generalTabEnqueue */(ITEMS.register("stone_drill_bit", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> IRON_DRILL_BIT = /* generalTabEnqueue */(ITEMS.register("iron_drill_bit", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STEEL_DRILL_BIT = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_drill_bit", () -> new Item(new Item.Properties()))));
	public static final RegistryObject<Item> GOLD_DRILL_BIT = /* generalTabEnqueue */(ITEMS.register("gold_drill_bit", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> DIAMOND_DRILL_BIT = /* generalTabEnqueue */(ITEMS.register("diamond_drill_bit", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> NETHERITE_DRILL_BIT = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("netherite_drill_bit", () -> new Item(new Item.Properties()))));

	public static final RegistryObject<Item> WOODEN_SAW_BLADE = /* generalTabEnqueue */(ITEMS.register("wooden_saw_blade", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STONE_SAW_BLADE = /* generalTabEnqueue */(ITEMS.register("stone_saw_blade", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> IRON_SAW_BLADE = /* generalTabEnqueue */(ITEMS.register("iron_saw_blade", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STEEL_SAW_BLADE = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_saw_blade", () -> new Item(new Item.Properties()))));
	public static final RegistryObject<Item> GOLD_SAW_BLADE = /* generalTabEnqueue */(ITEMS.register("gold_saw_blade", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> DIAMOND_SAW_BLADE = /* generalTabEnqueue */(ITEMS.register("diamond_saw_blade", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> NETHERITE_SAW_BLADE = ingredientTabEnqueue(yatmGeTabEnqueue(ITEMS.register("netherite_saw_blade", () -> new Item(new Item.Properties()))));
	
	

	public static final RegistryObject<DrinkableFluidBottleItem> BIO_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("bio_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.BIOFLUID), YATMFluids.BIO, 20))));
	public static final RegistryObject<DrinkableFluidBottleItem> CHORUS_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("chorus_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.CHORUS), YATMFluids.CHORUS, 20))));
	public static final RegistryObject<DrinkableFluidBottleItem> CHORUS_BIO_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("chorus_bio_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.CHORUS_BIOFLUID), YATMFluids.CHORUS_BIO, 20))));
	public static final RegistryObject<DrinkableItem> DILUTED_TEAR_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("diluted_tear_bottle", () -> new DrinkableItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.DILUTED_TEAR), 6))));
	public static final RegistryObject<DrinkableFluidBottleItem> ENDER_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("ender_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.ENDER), YATMFluids.ENDER, 20))));
	public static final RegistryObject<DrinkableFluidBottleItem> ESSENCE_OF_DECAY_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("essence_of_decay_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.ESSENCE_OF_DECAY), YATMFluids.ESSENCE_OF_DECAY, 20))));
	public static final RegistryObject<EssenceOfSoulsBottleItem> ESSENCE_OF_SOULS_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("essence_of_souls_bottle", () -> new EssenceOfSoulsBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.ESSENCE_OF_SOULS), YATMFluids.ESSENCE_OF_SOULS, 20))));
	public static final RegistryObject<DrinkableFluidBottleItem> LATEX_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("latex_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.LATEX), YATMFluids.LATEX, 20))));
	public static final RegistryObject<DrinkableFluidBottleItem> SOUL_SAP_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_sap_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.SOUL_SAP), YATMFluids.SOUL_SAP, 40))));
	public static final RegistryObject<DrinkableFluidBottleItem> SOUL_SYRUP_BOTTLE = foodTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_syrup_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.SOUL_SYRUP), YATMFluids.SOUL_SYRUP, 60))));
	
	
	
	public static final RegistryObject<BucketItem> BIO_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("bio_bucket", () -> new BucketItem(YATMFluids.BIO, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	public static final RegistryObject<BucketItem> CHORUS_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("chorus_bucket", () -> new BucketItem(YATMFluids.CHORUS, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	public static final RegistryObject<BucketItem> CHORUS_BIO_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("chorus_bio_bucket", () -> new BucketItem(YATMFluids.CHORUS_BIO, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	public static final RegistryObject<BucketItem> ENDER_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("ender_bucket", () -> new BucketItem(YATMFluids.ENDER, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	public static final RegistryObject<BucketItem> ESSENCE_OF_DECAY_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("essence_of_decay_bucket", () -> new BucketItem(YATMFluids.ESSENCE_OF_DECAY, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	public static final RegistryObject<EssenceOfSoulsBucketItem> ESSENCE_OF_SOULS_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("essence_of_souls_bucket", () -> new EssenceOfSoulsBucketItem(YATMFluids.ESSENCE_OF_SOULS, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	public static final RegistryObject<BucketItem> LATEX_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("latex_bucket", () -> new BucketItem(YATMFluids.LATEX, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	public static final RegistryObject<BucketItem> SILICON_OXIDE_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("silicon_oxide_bucket", () -> new BucketItem(YATMFluids.SILICON_OXIDE, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	public static final RegistryObject<BucketItem> SOUL_SAP_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_sap_bucket", () -> new BucketItem(YATMFluids.SOUL_SAP, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	public static final RegistryObject<BucketItem> SOUL_SYRUP_BUCKET = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_syrup_bucket", () -> new BucketItem(YATMFluids.SOUL_SYRUP, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));
	
	// TODO, maybe add a special boat, longer, and with a soul lantern
	public static final RegistryObject<YATMBoatItem> RUBBER_BOAT = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_boat", () -> new YATMBoatItem(false, YATMBoatType.RUBBER, new Item.Properties().stacksTo(1)))));
	public static final RegistryObject<YATMBoatItem> RUBBER_CHEST_BOAT = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("rubber_chest_boat", () -> new YATMBoatItem(true, YATMBoatType.RUBBER, new Item.Properties().stacksTo(1)))));
	public static final RegistryObject<YATMBoatItem> SOUL_AFFLICTED_RUBBER_BOAT = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_boat", () -> new YATMBoatItem(false, YATMBoatType.SOUL_AFFLICTED_RUBBER, new Item.Properties().stacksTo(1)))));
	public static final RegistryObject<YATMBoatItem> SOUL_AFFLICTED_RUBBER_CHEST_BOAT = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_afflicted_rubber_chest_boat", () -> new YATMBoatItem(true, YATMBoatType.SOUL_AFFLICTED_RUBBER, new Item.Properties().stacksTo(1)))));

	// TODO, some sort of hydraulic accumulator
	// TODO, blocks matching against most components
	public static final RegistryObject<CurrentHeaterItem> EMBER_GLAND = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("ember_gland", () -> new CurrentHeaterItem(new Item.Properties().stacksTo(1), YATMConfigs.EMBER_GLAND_HEAT, PrimitiveUtil.toFloatSupplier(YATMConfigs.EMBER_GLAND_KELVIN_PER_CURRENT)))));
	public static final RegistryObject<CurrentHeaterItem> FLAME_GLAND = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("flame_gland", () -> new CurrentHeaterItem(new Item.Properties().stacksTo(1), YATMConfigs.FLAME_GLAND_HEAT, PrimitiveUtil.toFloatSupplier(YATMConfigs.FLAME_GLAND_KELVIN_PER_CURRENT)))));
	public static final RegistryObject<CurrentHeaterItem> TORCH_GLAND = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("torch_gland", () -> new CurrentHeaterItem(new Item.Properties().stacksTo(1), YATMConfigs.TORCH_GLAND_HEAT, PrimitiveUtil.toFloatSupplier(YATMConfigs.TORCH_GLAND_KELVIN_PER_CURRENT)))));
	
	// TODO, draw
	public static final RegistryObject<CurrentStorerItem> CURRENT_TUBER = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("current_tuber", () -> new CurrentStorerItem(new Item.Properties().durability(1), YATMConfigs.CURRENT_TUBER))));
	public static final RegistryObject<CurrentStorerItem> CURRENT_BATTERY = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("current_battery", () -> new CurrentStorerItem(new Item.Properties().durability(1), YATMConfigs.CURRENT_BATTERY))));
	public static final RegistryObject<CurrentStorerItem> ADVANCED_CURRENT_BATTERY = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("advanced_current_battery", () -> new CurrentStorerItem(new Item.Properties().durability(1), YATMConfigs.ADVANCED_CURRENT_BATTERY))));
	
	public static final RegistryObject<SpeedUpgradeItem> SPEED_UPGRADE = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("speed_upgrade", () -> new SpeedUpgradeItem(new Item.Properties().stacksTo(4), 1f, BonusType.ADDITIVE))));
	public static final RegistryObject<EfficiencyUpgradeItem> EFFICIENCY_UPGRADE = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("efficiency_upgrade", () -> new EfficiencyUpgradeItem(new Item.Properties().stacksTo(4), 1f, BonusType.ADDITIVE))));
	// TODO, tank capacity upgrade
	// TODO, current capacity upgrade
	// TODO, drain/fill fluid/current upgrades.
	
	public static final RegistryObject<DrillItem> STEEL_DRILL_WOOD = /* generalTabEnqueue */(ITEMS.register("steel_drill_wood", () -> new DrillItem(Tiers.WOOD, new Item.Properties().craftRemainder(YATMItems.WOODEN_DRILL_BIT.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<DrillItem> STEEL_DRILL_STONE = /* generalTabEnqueue */(ITEMS.register("steel_drill_stone", () -> new DrillItem(Tiers.STONE, new Item.Properties().craftRemainder(YATMItems.STONE_DRILL_BIT.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<DrillItem> STEEL_DRILL_IRON = /* generalTabEnqueue */(ITEMS.register("steel_drill_iron", () -> new DrillItem(Tiers.IRON, new Item.Properties().craftRemainder(YATMItems.IRON_DRILL_BIT.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<DrillItem> STEEL_DRILL_STEEL = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_drill_steel", () -> new DrillItem(Tiers.IRON, new Item.Properties().craftRemainder(YATMItems.STEEL_DRILL_BIT.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8))));
	public static final RegistryObject<DrillItem> STEEL_DRILL_GOLD = /* generalTabEnqueue */(ITEMS.register("steel_drill_gold", () -> new DrillItem(Tiers.GOLD, new Item.Properties().craftRemainder(YATMItems.GOLD_DRILL_BIT.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<DrillItem> STEEL_DRILL_DIAMOND = /* generalTabEnqueue */(ITEMS.register("steel_drill_diamond", () -> new DrillItem(Tiers.DIAMOND, new Item.Properties().craftRemainder(YATMItems.DIAMOND_DRILL_BIT.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<DrillItem> STEEL_DRILL_NETHERITE = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_drill_netherite", () -> new DrillItem(Tiers.NETHERITE, new Item.Properties().craftRemainder(YATMItems.NETHERITE_DRILL_BIT.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 6, 6))));
	
	public static final RegistryObject<SawItem> STEEL_SAW_WOOD = /* generalTabEnqueue */(ITEMS.register("steel_saw_wood", () -> new SawItem(Tiers.WOOD, new Item.Properties().craftRemainder(YATMItems.WOODEN_SAW_BLADE.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<SawItem> STEEL_SAW_STONE = /* generalTabEnqueue */(ITEMS.register("steel_saw_stone", () -> new SawItem(Tiers.STONE, new Item.Properties().craftRemainder(YATMItems.STONE_SAW_BLADE.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<SawItem> STEEL_SAW_IRON = /* generalTabEnqueue */(ITEMS.register("steel_saw_iron", () -> new SawItem(Tiers.IRON, new Item.Properties().craftRemainder(YATMItems.IRON_SAW_BLADE.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<SawItem> STEEL_SAW_STEEL = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_saw_steel", () -> new SawItem(Tiers.IRON, new Item.Properties().craftRemainder(YATMItems.STEEL_SAW_BLADE.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8))));
	public static final RegistryObject<SawItem> STEEL_SAW_GOLD = /* generalTabEnqueue */(ITEMS.register("steel_saw_gold", () -> new SawItem(Tiers.GOLD, new Item.Properties().craftRemainder(YATMItems.GOLD_SAW_BLADE.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<SawItem> STEEL_SAW_DIAMOND = /* generalTabEnqueue */(ITEMS.register("steel_saw_diamond", () -> new SawItem(Tiers.DIAMOND, new Item.Properties().craftRemainder(YATMItems.DIAMOND_SAW_BLADE.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 8, 8)));
	public static final RegistryObject<SawItem> STEEL_SAW_NETHERITE = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_saw_netherite", () -> new SawItem(Tiers.NETHERITE, new Item.Properties().craftRemainder(YATMItems.NETHERITE_SAW_BLADE.get()).durability(PoweredToolItemStack.BASE_CURRENT_CAPACITY).setNoRepair(), 1, 6, 6))));
	
	public static final RegistryObject<Item> STEEL_WRENCH = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("steel_wrench", () -> new Item(new Item.Properties()))));
	
	// ambulatory accelerator foliar steel armor upgrade for spring speed
	
	public static final RegistryObject<ArmorItem> SOUL_ADORNED_NETHERITE_HELMET = combatTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_adorned_netherite_helmet", () -> new ArmorItem(YATMArmorMaterials.SOUL_ADORNED_NETHERITE, ArmorItem.Type.HELMET, new Item.Properties().fireResistant()))));
	public static final RegistryObject<ArmorItem> SOUL_ADORNED_NETHERITE_CHESTPLATE = combatTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_adorned_netherite_chestplate", () -> new ArmorItem(YATMArmorMaterials.SOUL_ADORNED_NETHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant()))));
	public static final RegistryObject<ArmorItem> SOUL_ADORNED_NETHERITE_LEGGINGS = combatTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_adorned_netherite_leggings", () -> new ArmorItem(YATMArmorMaterials.SOUL_ADORNED_NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant()))));
	public static final RegistryObject<ArmorItem> SOUL_ADORNED_NETHERITE_BOOTS = combatTabEnqueue(yatmGeTabEnqueue(ITEMS.register("soul_adorned_netherite_boots", () -> new ArmorItem(YATMArmorMaterials.SOUL_ADORNED_NETHERITE, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant()))));
	
	public static final RegistryObject<ArmorItem> DECAY_NETHERITE_HELMET = combatTabEnqueue(yatmGeTabEnqueue(ITEMS.register("decay_netherite_helmet", () -> new ArmorItem(YATMArmorMaterials.DECAY_NETHERITE, ArmorItem.Type.HELMET, new Item.Properties().fireResistant()))));
	public static final RegistryObject<ArmorItem> DECAY_NETHERITE_CHESTPLATE = combatTabEnqueue(yatmGeTabEnqueue(ITEMS.register("decay_netherite_chestplate", () -> new ArmorItem(YATMArmorMaterials.DECAY_NETHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant()))));
	public static final RegistryObject<ArmorItem> DECAY_NETHERITE_LEGGINGS = combatTabEnqueue(yatmGeTabEnqueue(ITEMS.register("decay_netherite_leggings", () -> new ArmorItem(YATMArmorMaterials.DECAY_NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant()))));
	public static final RegistryObject<ArmorItem> DECAY_NETHERITE_BOOTS = combatTabEnqueue(yatmGeTabEnqueue(ITEMS.register("decay_netherite_boots", () -> new ArmorItem(YATMArmorMaterials.DECAY_NETHERITE, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant()))));
	
	public static final RegistryObject<BlockItem> CREATIVE_CURRENT_SOURCE = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("creative_current_source", () -> new BlockItem(YATMBlocks.CREATIVE_CURRENT_SOURCE.get(), new Item.Properties()))));
	
	public static final RegistryObject<CreativeFluidSourceItem> CREATIVE_FLUID_SOURCE = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("creative_fluid_source", () -> new CreativeFluidSourceItem(new Item.Properties().stacksTo(1)))));
	public static final RegistryObject<CreativeFluidStorerItem> CREATIVE_FLUID_STORER = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("creative_fluid_storer", () -> new CreativeFluidStorerItem(new Item.Properties().stacksTo(1)))));
	public static final RegistryObject<CreativeFluidVoidItem> CREATIVE_FLUID_VOID = toolTabEnqueue(yatmGeTabEnqueue(ITEMS.register("creative_fluid_void", () -> new CreativeFluidVoidItem(new Item.Properties().stacksTo(1)))));
	
	
	

	
	
	public static void addCompostables() 
	{	
		// TODO, review and add in missings
		ComposterBlock.COMPOSTABLES.put(YATMItems.RUBBER_MERISTEM.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.RUBBER_LEAVES_YOUNG.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.RUBBER_LEAVES_FLOWERING.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.LEAF_MULCH.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.COTTON_SEEDS.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.COTTON_BOLLS.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.RAW_COTTON_FIBER.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.FIRE_EATER_LILY_BULB.get(), 0.3F);		
		
		/*
		 * ComposterBlock.COMPOSTABLES.put(YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), 0.3F); 
		 * ComposterBlock.COMPOSTABLES.put(YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), 0.3F);
		 * ComposterBlock.COMPOSTABLES.put(YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), 0.3F);
		 * 
		 * ComposterBlock.COMPOSTABLES.put(YATMItems.PHANTASMAL_SHELF_FUNGUS.get(), 0.85F);
		 */
	} // end addCompostables
	
	
	
	public static <T extends Item> RegistryObject<T> yatmGeTabEnqueue(RegistryObject<T> item) 
	{
		return YATMCreativeModeTabs.yatmGeneralTabEnqueue(item);
	} // end generalTabEnqueue()
	
	
	
	public static <T extends Item> RegistryObject<T> buildingTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.buildingBlocksTabEnqueue(item);		
	} // end buildingTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> colorTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.coloredBlocksTabEnqueue(item);		
	} // end coloredBlocksTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> naturalTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.naturalBlocksTabEnqueue(item);		
	} // end naturalBlocksTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> functionalTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.functionalBlocksTabEnqueue(item);		
	} // end functionalBlocksTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> redstoneTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.redstoneBlocksTabEnqueue(item);		
	} // end functionalBlocksTabEnqueue()
	
	
	
	public static <T extends Item> RegistryObject<T> toolTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.toolAndUtilitiesTabEnqueue(item);		
	} // end toolAndUtilitiesTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> combatTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.combatTabEnqueue(item);		
	} // end combatTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> foodTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.foodAndDrinksTabEnqueue(item);		
	} // end foodAndDrinksTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> ingredientTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.ingredientsTabEnqueue(item);		
	} // end ingredientsTabEnqueue()
} // end class