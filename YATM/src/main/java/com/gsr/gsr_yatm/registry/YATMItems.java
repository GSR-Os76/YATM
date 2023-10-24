package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YATMFoods;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.armor.YATMArmorMaterials;
import com.gsr.gsr_yatm.creative.CreativeFluidSourceItem;
import com.gsr.gsr_yatm.creative.CreativeFluidStorerItem;
import com.gsr.gsr_yatm.creative.CreativeFluidVoidItem;
import com.gsr.gsr_yatm.entity.boat.YATMBoatType;
import com.gsr.gsr_yatm.item.ShulkwartHornItem;
import com.gsr.gsr_yatm.item.ShulkwartSporesBlockItem;
import com.gsr.gsr_yatm.item.YATMBoatItem;
import com.gsr.gsr_yatm.item.fluid.DrinkableFluidBottleItem;
import com.gsr.gsr_yatm.item.fluid.EssenceOfSoulsBottleItem;
import com.gsr.gsr_yatm.item.fluid.FluidBottleItem;
import com.gsr.gsr_yatm.item.fluid.SoulSapBucketItem;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YetAnotherTechMod.MODID);

	
	// possibly generate similar sets through a method, providing greater resilience to changes to creation.
	public static final RegistryObject<BlockItem> RUBBER_MERISTEM_ITEM = generalTabEnqueue(ITEMS.register("rubber_meristem", () -> new BlockItem(YATMBlocks.RUBBER_MERISTEM.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_LEAVES_YOUNG_ITEM = generalTabEnqueue(ITEMS.register("rubber_leaves_young", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_YOUNG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_LEAVES_FLOWERING_ITEM = generalTabEnqueue(ITEMS.register("rubber_leaves_flowering", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_FLOWERING.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_LEAVES_OLD_ITEM = generalTabEnqueue(ITEMS.register("rubber_leaves_old", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_OLD.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_ROOTS_ITEM = generalTabEnqueue(ITEMS.register("rubber_roots", () -> new BlockItem(YATMBlocks.RUBBER_ROOTS.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_LOG_ITEM = generalTabEnqueue(ITEMS.register("rubber_log", () -> new BlockItem(YATMBlocks.RUBBER_LOG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_WOOD_ITEM = generalTabEnqueue(ITEMS.register("rubber_wood", () -> new BlockItem(YATMBlocks.RUBBER_WOOD.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STRIPPED_RUBBER_LOG_ITEM = generalTabEnqueue(ITEMS.register("stripped_rubber_log", () -> new BlockItem(YATMBlocks.STRIPPED_RUBBER_LOG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> PARTIALLY_STRIPPED_RUBBER_LOG_ITEM = generalTabEnqueue(ITEMS.register("partially_stripped_rubber_log", () -> new BlockItem(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STRIPPED_RUBBER_WOOD_ITEM = generalTabEnqueue(ITEMS.register("stripped_rubber_wood", () -> new BlockItem(YATMBlocks.STRIPPED_RUBBER_WOOD.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_PLANKS_ITEM = generalTabEnqueue(ITEMS.register("rubber_planks", () -> new BlockItem(YATMBlocks.RUBBER_PLANKS.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> FANCY_RUBBER_PLANKS_ITEM = generalTabEnqueue(ITEMS.register("fancy_rubber_planks", () -> new BlockItem(YATMBlocks.FANCY_RUBBER_PLANKS.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_STAIRS_ITEM = generalTabEnqueue(ITEMS.register("rubber_stairs", () -> new BlockItem(YATMBlocks.RUBBER_STAIRS.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_SLAB_ITEM = generalTabEnqueue(ITEMS.register("rubber_slab", () -> new BlockItem(YATMBlocks.RUBBER_SLAB.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_FENCE_ITEM = generalTabEnqueue(ITEMS.register("rubber_fence", () -> new BlockItem(YATMBlocks.RUBBER_FENCE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_FENCE_GATE_ITEM = generalTabEnqueue(ITEMS.register("rubber_fence_gate", () -> new BlockItem(YATMBlocks.RUBBER_FENCE_GATE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_DOOR_ITEM = generalTabEnqueue(ITEMS.register("rubber_door", () -> new BlockItem(YATMBlocks.RUBBER_DOOR.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_TRAPDOOR_ITEM = generalTabEnqueue(ITEMS.register("rubber_trapdoor", () -> new BlockItem(YATMBlocks.RUBBER_TRAPDOOR.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_PRESSURE_PLATE_ITEM = generalTabEnqueue(ITEMS.register("rubber_pressure_plate", () -> new BlockItem(YATMBlocks.RUBBER_PRESSURE_PLATE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_BUTTON_ITEM = generalTabEnqueue(ITEMS.register("rubber_button", () -> new BlockItem(YATMBlocks.RUBBER_BUTTON.get(), new Item.Properties())));
	public static final RegistryObject<SignItem> RUBBER_SIGN_ITEM = generalTabEnqueue(ITEMS.register("rubber_sign", () -> new SignItem(new Item.Properties().stacksTo(16), YATMBlocks.RUBBER_SIGN.get(), YATMBlocks.RUBBER_WALL_SIGN.get())));
	public static final RegistryObject<HangingSignItem> RUBBER_HANGING_SIGN_ITEM = generalTabEnqueue(ITEMS.register("rubber_hanging_sign", () -> new HangingSignItem(YATMBlocks.RUBBER_HANGING_SIGN.get(), YATMBlocks.RUBBER_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16))));
	public static final RegistryObject<YATMBoatItem> RUBBER_BOAT_ITEM = generalTabEnqueue(ITEMS.register("rubber_boat", () -> new YATMBoatItem(false, YATMBoatType.RUBBER, new Item.Properties().stacksTo(1))));
	public static final RegistryObject<YATMBoatItem> RUBBER_CHEST_BOAT_ITEM = generalTabEnqueue(ITEMS.register("rubber_chest_boat", () -> new YATMBoatItem(true, YATMBoatType.RUBBER, new Item.Properties().stacksTo(1))));
	public static final RegistryObject<BlockItem> LEAF_MULCH_ITEM = generalTabEnqueue(ITEMS.register("leaf_mulch", () -> new BlockItem(YATMBlocks.LEAF_MULCH.get(), new Item.Properties())));


	
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_meristem", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_leaves_young", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_leaves_flowering", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_LEAVES_OLD_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_leaves_old", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_ROOTS_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_roots", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_ROOTS.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_LOG_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_log", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_WOOD_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_wood", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_STRIPPED_RUBBER_LOG_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_stripped_rubber_log", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_partially_stripped_rubber_log", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_stripped_rubber_wood", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_PLANKS_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_planks", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_PLANKS.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_fancy_rubber_planks", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_fancy_rubber_planks_tiled", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_STAIRS_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_stairs", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_SLAB_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_slab", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_FENCE_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_fence", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_FENCE_GATE_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_fence_gate", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE_GATE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_DOOR_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_door", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_TRAPDOOR_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_trapdoor", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_pressure_plate", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_BUTTON_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_button", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_BUTTON.get(), new Item.Properties())));
	public static final RegistryObject<SignItem> SOUL_AFFLICTED_RUBBER_SIGN_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_sign", () -> new SignItem(new Item.Properties().stacksTo(16), YATMBlocks.SOUL_AFFLICTED_RUBBER_SIGN.get(), YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_SIGN.get())));
	public static final RegistryObject<HangingSignItem> SOUL_AFFLICTED_RUBBER_HANGING_SIGN_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_hanging_sign", () -> new HangingSignItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_HANGING_SIGN.get(), YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16))));
	// TODO, maybe add a special boat, longer, and with a soul lantern
	public static final RegistryObject<YATMBoatItem> SOUL_AFFLICTED_RUBBER_BOAT_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_boat", () -> new YATMBoatItem(false, YATMBoatType.SOUL_AFFLICTED_RUBBER, new Item.Properties().stacksTo(1))));
	public static final RegistryObject<YATMBoatItem> SOUL_AFFLICTED_RUBBER_CHEST_BOAT_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_chest_boat", () -> new YATMBoatItem(true, YATMBoatType.SOUL_AFFLICTED_RUBBER, new Item.Properties().stacksTo(1))));	
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_LEAF_MULCH_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_leaf_mulch", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get(), new Item.Properties())));

	
	
	public static final RegistryObject<ItemNameBlockItem> AURUM_DEMINUTUS_FIDDLE_HEAD = generalTabEnqueue(ITEMS.register("aurum_deminutus_fiddle_head", () -> new ItemNameBlockItem(YATMBlocks.AURUM.get(), new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> AURUM_DEMINUTUS_FROND = generalTabEnqueue(ITEMS.register("aurum_deminutus_frond", () -> new Item(new Item.Properties().fireResistant())));
	
	public static final RegistryObject<ItemNameBlockItem> BASIN_OF_TEARS_SEED = generalTabEnqueue(ITEMS.register("basin_of_tears_seed", () -> new ItemNameBlockItem(YATMBlocks.BASIN_OF_TEARS_VEGETATION.get(), new Item.Properties())));
	public static final RegistryObject<Item> DILUTED_TEAR_BOTTLE = generalTabEnqueue(ITEMS.register("diluted_tear_bottle", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE))));
	public static final RegistryObject<Item> TEAR_LEAF = /* generalTabEnqueue */(ITEMS.register("tear_leaf", () -> new Item(new Item.Properties().food(YATMFoods.TEAR_LEAF))));
	
	public static final RegistryObject<BlockItem> CANDLELILY_ITEM = generalTabEnqueue(ITEMS.register("candlelily", () -> new BlockItem(YATMBlocks.CANDLELILY.get(), new Item.Properties())));
	
	public static final RegistryObject<ItemNameBlockItem> CARBUM_MERISTEM = generalTabEnqueue(ITEMS.register("carbum_meristem", () -> new ItemNameBlockItem(YATMBlocks.CARBUM.get(), new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> CARBUM_LEAF = generalTabEnqueue(ITEMS.register("carbum_leaf", () -> new Item(new Item.Properties())));
	
	public static final RegistryObject<ItemNameBlockItem> CARCASS_ROOT_CUTTING = generalTabEnqueue(ITEMS.register("carcass_root_cutting", () -> new ItemNameBlockItem(YATMBlocks.CARCASS_ROOT_FOLIAGE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> CARCASS_ROOT_ROOTED_DIRT_ITEM = generalTabEnqueue(ITEMS.register("carcass_root_rooted_dirt", () -> new BlockItem(YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> CARCASS_ROOT_ROOTED_NETHERRACK_ITEM = generalTabEnqueue(ITEMS.register("carcass_root_rooted_netherrack", () -> new BlockItem(YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get(), new Item.Properties())));
	
	public static final RegistryObject<ItemNameBlockItem> COTTON_SEEDS = generalTabEnqueue(ITEMS.register("cotton_seeds", () -> new ItemNameBlockItem(YATMBlocks.COTTON.get(), new Item.Properties())));
	public static final RegistryObject<Item> COTTON_BOLLS = generalTabEnqueue(ITEMS.register("cotton_bolls", () -> new Item(new Item.Properties().craftRemainder(YATMItems.COTTON_SEEDS.get()))));
	public static final RegistryObject<Item> RAW_COTTON_FIBER = generalTabEnqueue(ITEMS.register("raw_cotton_fiber", () -> new Item(new Item.Properties())));
	
	public static final RegistryObject<ItemNameBlockItem> FERRUM_ROOTSTOCK = generalTabEnqueue(ITEMS.register("ferrum_rootstock", () -> new ItemNameBlockItem(YATMBlocks.FERRUM.get(), new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> FERRUM_BRANCH = generalTabEnqueue(ITEMS.register("ferrum_branch", () -> new Item(new Item.Properties())));
	
	public static final RegistryObject<ItemNameBlockItem> FIRE_EATER_LILY_BULB = generalTabEnqueue(ITEMS.register("fire_eater_lily_bulb", () -> new ItemNameBlockItem(YATMBlocks.FIRE_EATER_LILY.get(), new Item.Properties())));
	public static final RegistryObject<Item> FIRE_EATER_LILY_FOLIAGE = generalTabEnqueue(ITEMS.register("fire_eater_lily_foliage", () -> new Item(new Item.Properties())));

	public static final RegistryObject<ItemNameBlockItem> FOLIUM_RHIZOME = generalTabEnqueue(ITEMS.register("folium_rhizome", () -> new ItemNameBlockItem(YATMBlocks.FOLIUM.get(), new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> FOLIAR_STEEL = generalTabEnqueue(ITEMS.register("foliar_steel", () -> new Item(new Item.Properties())));

	public static final RegistryObject<ItemNameBlockItem> ICE_CORAL_POLYP = generalTabEnqueue(ITEMS.register("ice_coral_polyp", () -> new ItemNameBlockItem(YATMBlocks.ICE_CORAL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> BLEACHED_ICE_CORAL_OLD_ITEM = generalTabEnqueue(ITEMS.register("bleached_ice_coral_old", () -> new BlockItem(YATMBlocks.BLEACHED_ICE_CORAL_OLD.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> BLEACHED_ICE_CORAL_ADOLESCENT_ITEM = generalTabEnqueue(ITEMS.register("bleached_ice_coral_adolescent", () -> new BlockItem(YATMBlocks.BLEACHED_ICE_CORAL_ADOLESCENT.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> BLEACHED_ICE_CORAL_YOUNG_ITEM = generalTabEnqueue(ITEMS.register("bleached_ice_coral_young", () -> new BlockItem(YATMBlocks.BLEACHED_ICE_CORAL_YOUNG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> BLEACHED_ICE_CORAL_POLYP_ITEM = generalTabEnqueue(ITEMS.register("bleached_ice_coral_polyp", () -> new BlockItem(YATMBlocks.BLEACHED_ICE_CORAL_POLYP.get(), new Item.Properties())));
	
	public static final RegistryObject<BlockItem> PHANTASMAL_SHELF_FUNGUS_ITEM = generalTabEnqueue(ITEMS.register("phantasmal_shelf_fungus", () -> new BlockItem(YATMBlocks.PHANTASMAL_SHELF_FUNGUS.get(), new Item.Properties())));
	
	public static final RegistryObject<BlockItem> PITCHER_CLUSTER_ITEM = generalTabEnqueue(ITEMS.register("pitcher_cluster", () -> new BlockItem(YATMBlocks.PITCHER_CLUSTER.get(), new Item.Properties())));

	public static final RegistryObject<ItemNameBlockItem> PRISMARINE_CRYSTAL_MOSS_SPORES = generalTabEnqueue(ITEMS.register("prismarine_crystal_moss_spores", () -> new ItemNameBlockItem(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get(), new Item.Properties())));
	
	public static final RegistryObject<ShulkwartSporesBlockItem> SHULKWART_SPORES = generalTabEnqueue(ITEMS.register("shulkwart_spores", () -> new ShulkwartSporesBlockItem(new Item.Properties()
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
			, YATMBlocks.BLACK_SHULKWART.get())));
	public static final RegistryObject<ShulkwartHornItem> SHULKWART_HORN = generalTabEnqueue(ITEMS.register("shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), (DyeColor)null)));
	public static final RegistryObject<ShulkwartHornItem> WHITE_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("white_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.WHITE)));
	public static final RegistryObject<ShulkwartHornItem> ORANGE_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("orange_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.ORANGE)));
	public static final RegistryObject<ShulkwartHornItem> MAGENTA_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("magenta_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.MAGENTA)));
	public static final RegistryObject<ShulkwartHornItem> LIGHT_BLUE_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("light_blue_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.LIGHT_BLUE)));
	public static final RegistryObject<ShulkwartHornItem> YELLOW_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("yellow_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.YELLOW)));
	public static final RegistryObject<ShulkwartHornItem> LIME_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("lime_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.LIME)));
	public static final RegistryObject<ShulkwartHornItem> PINK_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("pink_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.PINK)));
	public static final RegistryObject<ShulkwartHornItem> GRAY_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("gray_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.GRAY)));
	public static final RegistryObject<ShulkwartHornItem> LIGHT_GRAY_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("light_gray_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.LIGHT_GRAY)));
	public static final RegistryObject<ShulkwartHornItem> CYAN_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("cyan_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.CYAN)));
	public static final RegistryObject<ShulkwartHornItem> PURPLE_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("purple_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.PURPLE)));
	public static final RegistryObject<ShulkwartHornItem> BLUE_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("blue_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.BLUE)));
	public static final RegistryObject<ShulkwartHornItem> BROWN_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("brown_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.BROWN)));
	public static final RegistryObject<ShulkwartHornItem> GREEN_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("green_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.GREEN)));
	public static final RegistryObject<ShulkwartHornItem> RED_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("red_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.RED)));
	public static final RegistryObject<ShulkwartHornItem> BLACK_SHULKWART_HORN = generalTabEnqueue(ITEMS.register("black_shulkwart_horn", () -> new ShulkwartHornItem(new Item.Properties(), DyeColor.BLACK)));
	
	public static final RegistryObject<ItemNameBlockItem> SPIDER_VINE_FRUITS = generalTabEnqueue(ITEMS.register("spider_vine_fruits", () -> new ItemNameBlockItem(YATMBlocks.SPIDER_VINE_MERISTEM.get(), new Item.Properties())));
	
	public static final RegistryObject<BlockItem> VARIEGATED_CACTUS_ITEM = generalTabEnqueue(ITEMS.register("variegated_cactus", () -> new BlockItem(YATMBlocks.VARIEGATED_CACTUS.get(), new Item.Properties())));
	
	public static final RegistryObject<ItemNameBlockItem> VICUM_MERISTEM = generalTabEnqueue(ITEMS.register("vicum_meristem", () -> new ItemNameBlockItem(YATMBlocks.VICUM.get(), new Item.Properties())));
	public static final RegistryObject<Item> VICUM_LEAF = generalTabEnqueue(ITEMS.register("vicum_leaf", () -> new Item(new Item.Properties())));
	
	
	
	public static final RegistryObject<Item> ENDOTHERMIC_TISSUE = /* generalTabEnqueue */(ITEMS.register("endothermic_tissue", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> EXOTHEMIC_GLAND = /* generalTabEnqueue */(ITEMS.register("exothermic_gland", () -> new Item(new Item.Properties())));
	
	
	
	public static final RegistryObject<BlockItem> HANGING_POT_HOOK_ITEM = generalTabEnqueue(ITEMS.register("hanging_pot_hook", () -> new BlockItem(YATMBlocks.HANGING_POT_HOOK.get(), new Item.Properties())));
	
	
	
	public static final RegistryObject<BlockItem> FOLIAR_STEEL_ORE_ITEM = generalTabEnqueue(ITEMS.register("foliar_steel_ore", () -> new BlockItem(YATMBlocks.FOLIAR_STEEL_ORE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> DEEPSLATE_FOLIAR_STEEL_ORE_ITEM = generalTabEnqueue(ITEMS.register("deepslate_foliar_steel_ore", () -> new BlockItem(YATMBlocks.DEEPSLATE_FOLIAR_STEEL_ORE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> FOLIAR_STEEL_BLOCK_ITEM = generalTabEnqueue(ITEMS.register("foliar_steel_block", () -> new BlockItem(YATMBlocks.FOLIAR_STEEL_BLOCK.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_BLOCK_ITEM = generalTabEnqueue(ITEMS.register("rubber_block", () -> new BlockItem(YATMBlocks.RUBBER_BLOCK.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ROOTED_SOUL_SOIL_ITEM = generalTabEnqueue(ITEMS.register("rooted_soul_soil", () -> new BlockItem(YATMBlocks.ROOTED_SOUL_SOIL.get(), new Item.Properties())));
	
	
	
	public static final RegistryObject<BlockItem> SAP_COLLECTOR_ITEM = generalTabEnqueue(ITEMS.register("sap_collector", () -> new BlockItem(YATMBlocks.SAP_COLLECTOR.get(), new Item.Properties())));
	// public static final RegistryObject<BlockItem> SAP_COLLECTOR_LATEX_ITEM = generalTabEnqueue(ITEMS.register("sap_collector_latex", () -> new BlockItem(YATMBlocks.SAP_COLLECTOR_LATEX.get(), new Item.Properties())));
	// public static final RegistryObject<BlockItem> SAP_COLLECTOR_SOUL_SAP_ITEM = generalTabEnqueue(ITEMS.register("sap_collector_soul_sap", () -> new BlockItem(YATMBlocks.SAP_COLLECTOR_SOUL_SAP.get(), new Item.Properties())));
	
	// add celestial altar
	// add liquid celestial light, liquid lunar light, and the liquid solar liquids;
	// add their ingots from alloying them with molten netherite
	// for these at least tools I feel are worthy to make
	
	// TODO, possibly add spools of colored thread for trimming leather armor all different ways
	// TODO, check if leather armor's trimmable in vanilla at all
	public static final RegistryObject<BlockItem> SPINNING_WHEEL_ITEM = generalTabEnqueue(ITEMS.register("spinning_wheel", () -> new BlockItem(YATMBlocks.SPINNING_WHEEL.get(), new Item.Properties())));

	// TODO, possibly move away from wires and current and GUIs, they bore me and are kinda tedious, make machines not inventory based, don't break immersion
	
	public static final RegistryObject<BlockItem> LARGE_COPPER_HEAT_SINK_ITEM = /* generalTabEnqueue */(ITEMS.register("large_copper_heat_sink", () -> new BlockItem(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), new Item.Properties())));

	public static final RegistryObject<BlockItem> DATA_STORAGE_BLOCK_ITEM = /* generalTabEnqueue */(ITEMS.register("data_storage_block", () -> new BlockItem(YATMBlocks.DATA_STORAGE_BLOCK.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> DATA_SCAN_COLLECTOR_ITEM = /* generalTabEnqueue */(ITEMS.register("data_scan_collector", () -> new BlockItem(YATMBlocks.DATA_SCAN_COLLECTOR.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> DESTRUCTIVE_DATA_SCANNER_ITEM = /* generalTabEnqueue */(ITEMS.register("destructive_data_scanner", () -> new BlockItem(YATMBlocks.DESTRUCTIVE_DATA_SCANNER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> DATA_PROCESSOR_ITEM = /* generalTabEnqueue */(ITEMS.register("data_processor", () -> new BlockItem(YATMBlocks.DATA_PROCESSOR.get(), new Item.Properties())));
	
	
	
	public static final RegistryObject<BlockItem> STEEL_BIOLER_ITEM = generalTabEnqueue(ITEMS.register("steel_bioler", () -> new BlockItem(YATMBlocks.STEEL_BIOLER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_BOILER_TANK_ITEM = /* generalTabEnqueue */(ITEMS.register("steel_boiler_tank", () -> new BlockItem(YATMBlocks.STEEL_BOILER_TANK.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_BOILER_ITEM = generalTabEnqueue(ITEMS.register("steel_boiler", () -> new BlockItem(YATMBlocks.STEEL_BOILER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_CRUCIBLE_ITEM = generalTabEnqueue(ITEMS.register("steel_crucible", () -> new BlockItem(YATMBlocks.STEEL_CRUCIBLE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_CRYSTALLIZER_ITEM = generalTabEnqueue(ITEMS.register("steel_crystallizer", () -> new BlockItem(YATMBlocks.STEEL_CRYSTALLIZER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_FURNACE_PLUS_ITEM = generalTabEnqueue(ITEMS.register("steel_furnace_plus", () -> new BlockItem(YATMBlocks.STEEL_FURNACE_PLUS.get(), new Item.Properties())));	
	public static final RegistryObject<BlockItem> STEEL_EXTRACTOR_ITEM = generalTabEnqueue(ITEMS.register("steel_extractor", () -> new BlockItem(YATMBlocks.STEEL_EXTRACTOR.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> STEEL_EXTRUDER_ITEM = /* generalTabEnqueue */(ITEMS.register("steel_extruder", () -> new BlockItem(YATMBlocks.STEEL_EXTRUDER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_GRINDER_ITEM = generalTabEnqueue(ITEMS.register("steel_grinder", () -> new BlockItem(YATMBlocks.STEEL_GRINDER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_INJECTOR_ITEM = generalTabEnqueue(ITEMS.register("steel_injector", () -> new BlockItem(YATMBlocks.STEEL_INJECTOR.get(), new Item.Properties())));

	public static final RegistryObject<BlockItem> C_U_F_E_I_ITEM = generalTabEnqueue(ITEMS.register("current_unit_forge_energy_interchanger", () -> new BlockItem(YATMBlocks.C_U_F_E_I.get(), new Item.Properties())));

	public static final RegistryObject<BlockItem> CRUDE_BATTERY_SOLAR_PANEL_ITEM = generalTabEnqueue(ITEMS.register("crude_battery_solar_panel", () -> new BlockItem(YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ADVANCED_BATTERY_SOLAR_PANEL_ITEM = generalTabEnqueue(ITEMS.register("advanced_battery_crude_solar_panel", () -> new BlockItem(YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL_ITEM = generalTabEnqueue(ITEMS.register("suns_complement_battery_solar_panel", () -> new BlockItem(YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> CRUDE_SOLAR_PANEL_ITEM = generalTabEnqueue(ITEMS.register("crude_solar_panel", () -> new BlockItem(YATMBlocks.CRUDE_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ADVANCED_SOLAR_PANEL_ITEM = generalTabEnqueue(ITEMS.register("advanced_crude_solar_panel", () -> new BlockItem(YATMBlocks.ADVANCED_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SUNS_COMPLEMENT_SOLAR_PANEL_ITEM = generalTabEnqueue(ITEMS.register("suns_complement_solar_panel", () -> new BlockItem(YATMBlocks.SUNS_COMPLEMENT_SOLAR_PANEL.get(), new Item.Properties())));


	
	public static final RegistryObject<BlockItem> CONDUIT_VINES_ITEM = generalTabEnqueue(ITEMS.register("conduit_vines", () -> new BlockItem(YATMBlocks.CONDUIT_VINES.get(), new Item.Properties())));
	
//	public static final RegistryObject<BlockItem> ONE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("one_cu_wire", () -> new BlockItem(YATMBlocks.ONE_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> EIGHT_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("eight_cu_wire", () -> new BlockItem(YATMBlocks.EIGHT_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> SIXTYFOUR_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("sixtyfour_cu_wire", () -> new BlockItem(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> FIVEHUNDREDTWELVE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("fivehundredtwelve_cu_wire", () -> new BlockItem(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("fourthousandnintysix_cu_wire", () -> new BlockItem(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> ENAMELED_ONE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_one_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_ONE_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> ENAMELED_EIGHT_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_eight_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> ENAMELED_SIXTYFOUR_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_sixtyfour_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_fivehundredtwelve_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_fourthousandnintysix_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> INSULATED_ONE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_one_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_ONE_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> INSULATED_EIGHT_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_eight_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_EIGHT_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> INSULATED_SIXTYFOUR_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_sixtyfour_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> INSULATED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_fivehundredtwelve_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get(), new Item.Properties())));
//	public static final RegistryObject<BlockItem> INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_fourthousandnintysix_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), new Item.Properties())));
//	
//	public static final RegistryObject<BlockItem> STEEL_FLUID_CONDUIT_ITEM = generalTabEnqueue(ITEMS.register("steel_fluid_conduit", () -> new BlockItem(YATMBlocks.STEEL_FLUID_CONDUIT.get(), new Item.Properties())));
		
	
	
	// public static final RegistryObject<FluidExchangerItem> STEEL_FLUID_EXCHANGER = generalTabEnqueue(ITEMS.register("steel_fluid_exchanger_component", () -> new FluidExchangerItem(new Item.Properties().stacksTo(1), 128)));
	
//	public static final RegistryObject<CurrentRegulatorItem> ONE_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("one_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().durability(1), 1, 8)));
//	public static final RegistryObject<CurrentRegulatorItem> EIGHT_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("eight_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().durability(1), 8, 64)));
//	public static final RegistryObject<CurrentRegulatorItem> SIXTYFOUR_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("sixtyfour_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().durability(1), 64, 512)));
//	public static final RegistryObject<CurrentRegulatorItem> FIVEHUNDREDTWELVE_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("fivehundredtwelve_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().durability(1), 512, 4096)));
//	public static final RegistryObject<CurrentRegulatorItem> FOURTHOUSANDNINTYSIX_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("fourthousandnintysix_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().durability(1), 4096,  32768)));
//
//	public static final RegistryObject<CurrentFuseItem> ONE_CU_CURRENT_FUSE = generalTabEnqueue(ITEMS.register("one_cu_current_fuse_component", () -> new CurrentFuseItem(new Item.Properties().durability(1), 1)));
//	public static final RegistryObject<CurrentFuseItem> EIGHT_CU_CURRENT_FUSE = generalTabEnqueue(ITEMS.register("eight_cu_current_fuse_component", () -> new CurrentFuseItem(new Item.Properties().durability(1), 8)));
//	public static final RegistryObject<CurrentFuseItem> SIXTYFOUR_CU_CURRENT_FUSE = generalTabEnqueue(ITEMS.register("sixtyfour_cu_current_fuse_component", () -> new CurrentFuseItem(new Item.Properties().durability(1), 64)));
//	public static final RegistryObject<CurrentFuseItem> FIVEHUNDREDTWELVE_CU_CURRENT_FUSE = generalTabEnqueue(ITEMS.register("fivehundredtwelve_cu_current_fuse_component", () -> new CurrentFuseItem(new Item.Properties().durability(1), 512)));
//	public static final RegistryObject<CurrentFuseItem> FOURTHOUSANDNINTYSIX_CU_CURRENT_FUSE = generalTabEnqueue(ITEMS.register("fourthousandnintysix_cu_current_fuse_component", () -> new CurrentFuseItem(new Item.Properties().durability(1), 4096)));
//
//	public static final RegistryObject<CurrentBreakerItem> ONE_CU_CURRENT_BREAKER = generalTabEnqueue(ITEMS.register("one_cu_current_breaker_component", () -> new CurrentBreakerItem(new Item.Properties().durability(1), 1)));
//	public static final RegistryObject<CurrentBreakerItem> EIGHT_CU_CURRENT_BREAKER = generalTabEnqueue(ITEMS.register("eight_cu_current_breaker_component", () -> new CurrentBreakerItem(new Item.Properties().durability(1), 8)));
//	public static final RegistryObject<CurrentBreakerItem> SIXTYFOUR_CU_CURRENT_BREAKER = generalTabEnqueue(ITEMS.register("sixtyfour_cu_current_breaker_component", () -> new CurrentBreakerItem(new Item.Properties().durability(1), 64)));
//	public static final RegistryObject<CurrentBreakerItem> FIVEHUNDREDTWELVE_CU_CURRENT_BREAKER = generalTabEnqueue(ITEMS.register("fivehundredtwelve_cu_current_breaker_component", () -> new CurrentBreakerItem(new Item.Properties().durability(1), 512)));
//	public static final RegistryObject<CurrentBreakerItem> FOURTHOUSANDNINTYSIX_CU_CURRENT_BREAKER = generalTabEnqueue(ITEMS.register("fourthousandnintysix_cu_current_breaker_component", () -> new CurrentBreakerItem(new Item.Properties().durability(1), 4096)));

	
	
	// liquid latex that burns you if you're in the sun light
	public static final RegistryObject<BucketItem> BIO_BUCKET = generalTabEnqueue(ITEMS.register("bio_bucket", () -> new BucketItem(YATMFluids.BIO, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> CHORUS_BUCKET = generalTabEnqueue(ITEMS.register("chorus_bucket", () -> new BucketItem(YATMFluids.CHORUS, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> CHORUS_BIO_BUCKET = generalTabEnqueue(ITEMS.register("chorus_bio_bucket", () -> new BucketItem(YATMFluids.CHORUS_BIO, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> ENDER_BUCKET = generalTabEnqueue(ITEMS.register("ender_bucket", () -> new BucketItem(YATMFluids.ENDER, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> ESSENCE_OF_DECAY_BUCKET = generalTabEnqueue(ITEMS.register("essence_of_decay_bucket", () -> new BucketItem(YATMFluids.ESSENCE_OF_DECAY, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> ESSENCE_OF_SOULS_BUCKET = generalTabEnqueue(ITEMS.register("essence_of_souls_bucket", () -> new BucketItem(YATMFluids.ESSENCE_OF_SOULS, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> LATEX_BUCKET = generalTabEnqueue(ITEMS.register("latex_bucket", () -> new BucketItem(YATMFluids.LATEX, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	// TODO, move custom bucket and conversion logic to be with essence of souls instead not this
	public static final RegistryObject<SoulSapBucketItem> SOUL_SAP_BUCKET = generalTabEnqueue(ITEMS.register("soul_sap_bucket", () -> new SoulSapBucketItem(YATMFluids.SOUL_SAP, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> SOUL_SYRUP_BUCKET = generalTabEnqueue(ITEMS.register("soul_syrup_bucket", () -> new BucketItem(YATMFluids.SOUL_SYRUP, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	
	public static final RegistryObject<DrinkableFluidBottleItem> BIO_BOTTLE = generalTabEnqueue(ITEMS.register("bio_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.BIOFLUID), YATMFluids.BIO, 20)));
	public static final RegistryObject<DrinkableFluidBottleItem> CHORUS_BOTTLE = generalTabEnqueue(ITEMS.register("chorus_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.CHORUS), YATMFluids.CHORUS, 20)));
	public static final RegistryObject<DrinkableFluidBottleItem> CHORUS_BIO_BOTTLE = generalTabEnqueue(ITEMS.register("chorus_bio_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.CHORUS_BIOFLUID), YATMFluids.CHORUS_BIO, 20)));
	public static final RegistryObject<DrinkableFluidBottleItem> ENDER_BOTTLE = generalTabEnqueue(ITEMS.register("ender_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.ENDER), YATMFluids.ENDER, 20)));
	public static final RegistryObject<DrinkableFluidBottleItem> ESSENCE_OF_DECAY_BOTTLE = generalTabEnqueue(ITEMS.register("essence_of_decay_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.ESSENCE_OF_DECAY), YATMFluids.ESSENCE_OF_DECAY, 20)));
	public static final RegistryObject<EssenceOfSoulsBottleItem> ESSENCE_OF_SOULS_BOTTLE = generalTabEnqueue(ITEMS.register("essence_of_souls_bottle", () -> new EssenceOfSoulsBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.ESSENCE_OF_SOULS), YATMFluids.ESSENCE_OF_SOULS, 20)));
	public static final RegistryObject<FluidBottleItem> LATEX_BOTTLE = generalTabEnqueue(ITEMS.register("latex_bottle", () -> new FluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), YATMFluids.LATEX)));
	public static final RegistryObject<DrinkableFluidBottleItem> SOUL_SAP_BOTTLE = generalTabEnqueue(ITEMS.register("soul_sap_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.SOUL_SAP), YATMFluids.SOUL_SAP, 40)));
	public static final RegistryObject<DrinkableFluidBottleItem> SOUL_SYRUP_BOTTLE = generalTabEnqueue(ITEMS.register("soul_syrup_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.SOUL_SYRUP), YATMFluids.SOUL_SYRUP, 60)));
	//Item.Properties()).craftRemainder(GLASS_BOTTLE).food(YATMFoods.).stacksTo(16)
	
	// TODO, add biofuel from wood or sugar or such thigns.
	// TODO, add silicon oxide as liquid, for growing out amethyst and nether quart
	// TODO, add silicon for making advanced solar panels
	// TODO, add solar panel recipes in

//	public static final RegistryObject<Item> IRON_WIRE_DIE = generalTabEnqueue(ITEMS.register("iron_wire_die", () -> new Item(new Item.Properties())));
//	public static final RegistryObject<Item> STEEL_WIRE_DIE = generalTabEnqueue(ITEMS.register("steel_wire_die", () -> new Item(new Item.Properties())));
//		
//	public static final RegistryObject<Item> SILVER_INGOT = generalTabEnqueue(ITEMS.register("silver_ingot", () -> new Item(new Item.Properties())));
//	public static final RegistryObject<Item> STEEL_INGOT = generalTabEnqueue(ITEMS.register("steel_ingot", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> RUBBER_BAR = generalTabEnqueue(ITEMS.register("rubber_bar", () -> new Item(new Item.Properties())));
//	public static final RegistryObject<Item> RUBBER_SCRAP_BALL = generalTabEnqueue(ITEMS.register("rubber_scrap_ball", () -> new Item(new Item.Properties())));
	
//	public static final RegistryObject<Item> SILVER_NUGGET = generalTabEnqueue(ITEMS.register("silver_nugget", () -> new Item(new Item.Properties())));
//	public static final RegistryObject<Item> COPPER_NUGGET = generalTabEnqueue(ITEMS.register("copper_nugget", () -> new Item(new Item.Properties())));
//	public static final RegistryObject<InsulatingItem> WAX_BIT_ITEM = generalTabEnqueue(ITEMS.register("wax_bit", () -> new InsulatingItem(new Item.Properties(), InsulatingItem.WAX_BIT_WAXING_TABLE)));
//	public static final RegistryObject<InsulatingItem> RUBBER_SCRAP = generalTabEnqueue(ITEMS.register("rubber_scrap", () -> new InsulatingItem(new Item.Properties(), InsulatingItem.RUBBER_SCRAP_WAXING_TABLE)));
	
	public static final RegistryObject<Item> WOOD_PULP = generalTabEnqueue(ITEMS.register("wood_pulp", () -> new Item(new Item.Properties())));
		
	public static final RegistryObject<Item> NETHERITE_NUGGET = generalTabEnqueue(ITEMS.register("netherite_nugget", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STAR_SEED = generalTabEnqueue(ITEMS.register("star_seed", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STAR_GERMLING = generalTabEnqueue(ITEMS.register("star_germling", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STAR_SPROUT = generalTabEnqueue(ITEMS.register("star_sprout", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STAR_ADOLESCENT = generalTabEnqueue(ITEMS.register("star_adolescent", () -> new Item(new Item.Properties())));
	
	// TODO, carcass root plant, pretty standard plant above ground, roots ground underneat, roots grow nodules which can drop mundane animal parts
	
	// TODO, netherite like thorns, grow in height but only from a very specifc growing block, growing points can't reproduce naturally.
	// TODO, netherite thorns as arrow styled like scraps, and like a great arrow which damages terrain and such styled like the ingot netherite
	// possible just make aurum fronds throwable and recoverable weapon
	public static final RegistryObject<Item> AURUM_FAN = /* generalTabEnqueue( */ITEMS.register("aurum_fan", () -> new Item(new Item.Properties().fireResistant()))/* ) */;
	
	public static final RegistryObject<ArmorItem> SOUL_ADORNED_NETHERITE_HELMET = generalTabEnqueue(ITEMS.register("soul_adorned_netherite_helmet", () -> new ArmorItem(YATMArmorMaterials.SOUL_ADORNED_NETHERITE, ArmorItem.Type.HELMET, new Item.Properties().fireResistant())));
	public static final RegistryObject<ArmorItem> SOUL_ADORNED_NETHERITE_CHESTPLATE = generalTabEnqueue(ITEMS.register("soul_adorned_netherite_chestplate", () -> new ArmorItem(YATMArmorMaterials.SOUL_ADORNED_NETHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant())));
	public static final RegistryObject<ArmorItem> SOUL_ADORNED_NETHERITE_LEGGINGS = generalTabEnqueue(ITEMS.register("soul_adorned_netherite_leggings", () -> new ArmorItem(YATMArmorMaterials.SOUL_ADORNED_NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant())));
	public static final RegistryObject<ArmorItem> SOUL_ADORNED_NETHERITE_BOOTS = generalTabEnqueue(ITEMS.register("soul_adorned_netherite_boots", () -> new ArmorItem(YATMArmorMaterials.SOUL_ADORNED_NETHERITE, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant())));
	
	// TODO, update item id, i just don't want to remove all current instances from my test world so waiting
	public static final RegistryObject<ArmorItem> DECAY_NETHERITE_HELMET = generalTabEnqueue(ITEMS.register("decaying_soul_adorned_netherite_helmet", () -> new ArmorItem(YATMArmorMaterials.DECAYING_SOUL_ADORNED_NETHERITE, ArmorItem.Type.HELMET, new Item.Properties().fireResistant())));
	public static final RegistryObject<ArmorItem> DECAY_NETHERITE_CHESTPLATE = generalTabEnqueue(ITEMS.register("decaying_soul_adorned_netherite_chestplate", () -> new ArmorItem(YATMArmorMaterials.DECAYING_SOUL_ADORNED_NETHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant())));
	public static final RegistryObject<ArmorItem> DECAY_NETHERITE_LEGGINGS = generalTabEnqueue(ITEMS.register("decaying_soul_adorned_netherite_leggings", () -> new ArmorItem(YATMArmorMaterials.DECAYING_SOUL_ADORNED_NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant())));
	public static final RegistryObject<ArmorItem> DECAY_NETHERITE_BOOTS = generalTabEnqueue(ITEMS.register("decaying_soul_adorned_netherite_boots", () -> new ArmorItem(YATMArmorMaterials.DECAYING_SOUL_ADORNED_NETHERITE, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant())));
	
	public static final RegistryObject<CreativeFluidVoidItem> CREATIVE_FLUID_VOID = generalTabEnqueue(ITEMS.register("creative_fluid_void", () -> new CreativeFluidVoidItem(new Item.Properties().stacksTo(1))));
	public static final RegistryObject<CreativeFluidStorerItem> CREATIVE_FLUID_STORER = generalTabEnqueue(ITEMS.register("creative_fluid_storer", () -> new CreativeFluidStorerItem(new Item.Properties().stacksTo(1))));
	public static final RegistryObject<CreativeFluidSourceItem> CREATIVE_FLUID_SOURCE = generalTabEnqueue(ITEMS.register("creative_fluid_source", () -> new CreativeFluidSourceItem(new Item.Properties().stacksTo(1))));
	
	
	

	
	
	public static void addCompostables() 
	{	
		ComposterBlock.COMPOSTABLES.put(YATMItems.RUBBER_MERISTEM_ITEM.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.RUBBER_LEAVES_YOUNG_ITEM.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.RUBBER_LEAVES_FLOWERING_ITEM.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.LEAF_MULCH_ITEM.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.COTTON_SEEDS.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.COTTON_BOLLS.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.RAW_COTTON_FIBER.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.FIRE_EATER_LILY_BULB.get(), 0.3F);		
		ComposterBlock.COMPOSTABLES.put(YATMItems.FIRE_EATER_LILY_FOLIAGE.get(), 0.3F);
		
		// TODO, possibly add the magically ones, here or elsewhere
		/*
		 * ComposterBlock.COMPOSTABLES.put(YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get(), 0.3F); 
		 * ComposterBlock.COMPOSTABLES.put(YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG_ITEM.get(), 0.3F);
		 * ComposterBlock.COMPOSTABLES.put(YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING_ITEM.get(), 0.3F);
		 * 
		 * ComposterBlock.COMPOSTABLES.put(YATMItems.PHANTASMAL_SHELF_FUNGUS_ITEM.get(), 0.85F);
		 */
	} // end addCompostables
	
	
	
	public static <T extends Item> RegistryObject<T> generalTabEnqueue(RegistryObject<T> item) 
	{
		return YATMCreativeModTabs.generalTabEnqueue(item);
	} // end generalTabEnqueue()
	
} // end class
