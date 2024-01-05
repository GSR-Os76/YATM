package com.gsr.gsr_yatm.data_generation;

import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMCreativeModeTabs;
import com.gsr.gsr_yatm.registry.YATMFluids;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.registry.YATMMobEffects;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

public class YATMLanguageProviderUnitedStatesEnglish extends YATMLanguageProvider
{

	public YATMLanguageProviderUnitedStatesEnglish(PackOutput output)
	{
		super(output, "en_us");
	} // end constructor

	
	
	@Override
	protected void addTranslations()
	{
		this.add(YATMCreativeModeTabs.YATM_GENERAL, "Yet Another Tech Mod General");
		
		this.add(YATMMenuTypes.GRAFTING_TABLE.get(), "Grafting Table");		
		
		this.add(YATMMenuTypes.BIOREACTOR.get(), "Bioreactor");		
		this.add(YATMMenuTypes.BOILER.get(), "Boiler");		
		this.add(YATMMenuTypes.CRUCIBLE.get(), "Crucible");	
		this.add(YATMMenuTypes.CRYSTALLIZER.get(), "Crystallizer");		
//		this.add(YATMMenuTypes.EXTRUDER.get(), "Extruder");		
		this.add(YATMMenuTypes.EXTRACTOR.get(), "Extractor");		
		this.add(YATMMenuTypes.GRINDER.get(), "Grinder");		
		this.add(YATMMenuTypes.INJECTOR.get(), "Injector");		
		this.add(YATMMenuTypes.STILL.get(), "Still");		
		
		this.add(YATMMenuTypes.BATTERY_SOLAR_PANEL.get(), "Solar Panel");		
		this.add(YATMMenuTypes.SOLAR_PANEL.get(), "Solar Panel");		
		
		this.add(YATMMenuTypes.CREATIVE_CURRENT_SOURCE.get(), "Creative Current Source");
		
		
		
		this.add("biome.gsr_yatm.old_growth_soul_sand_valley", "Old Growth Soul Sand Valley");
		this.add("biome.gsr_yatm.rubber_forest", "Rubber Forest");
		
		
		
		this.add(YATMBlocks.RUBBER_MERISTEM.get(), "Rubber Meristem");
		this.add(YATMBlocks.POTTED_RUBBER_MERISTEM.get(), "Potted Rubber Meristem");
		this.add(YATMBlocks.RUBBER_LEAVES_YOUNG.get(), "Rubber Leaves Young");
		this.add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get(), "Rubber Leaves Flowering");
		this.add(YATMBlocks.RUBBER_LEAVES_OLD.get(), "Rubber Leaves Old");	
		this.add(YATMBlocks.RUBBER_ROOTS.get(), "Rubber Roots");
		this.add(YATMBlocks.RUBBER_LOG.get(), "Rubber Log");
		this.add(YATMBlocks.RUBBER_WOOD.get(), "Rubber Wood");
		this.add(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get(), "Partially Stripped Rubber Log");
		this.add(YATMBlocks.STRIPPED_RUBBER_LOG.get(), "Stripped Rubber Log");
		this.add(YATMBlocks.STRIPPED_RUBBER_WOOD.get(), "Stripped Rubber Wood");
		this.add(YATMBlocks.RUBBER_PLANKS.get(), "Rubber Planks");
		this.add(YATMBlocks.FANCY_RUBBER_PLANKS.get(), "Fancy Rubber Planks");
		this.add(YATMBlocks.RUBBER_STAIRS.get(), "Rubber Stairs");
		this.add(YATMBlocks.RUBBER_SLAB.get(), "Rubber Slab");
		this.add(YATMBlocks.RUBBER_FENCE.get(), "Rubber Fence");
		this.add(YATMBlocks.RUBBER_FENCE_GATE.get(), "Fancy Fence Gate");
		this.add(YATMBlocks.RUBBER_DOOR.get(), "Rubber Door");
		this.add(YATMBlocks.RUBBER_TRAPDOOR.get(), "Rubber Trapdoor");
		this.add(YATMBlocks.RUBBER_PRESSURE_PLATE.get(), "Rubber Pressure Plate");
		this.add(YATMBlocks.RUBBER_BUTTON.get(), "Rubber Button");
		this.add(YATMBlocks.RUBBER_SIGN.get(), "Rubber Sign");
		this.add(YATMBlocks.RUBBER_HANGING_SIGN.get(), "Rubber Hanging Sign");
		this.add(YATMBlocks.LEAF_MULCH.get(), "Leaf Mulch");

		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), "Soul Afflicted Rubber Meristem");
		this.add(YATMBlocks.POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM.get(), "Potted Soul Afflicted Rubber Meristem");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), "Soul Afflicted Rubber Leaves Young");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), "Soul Afflicted Rubber Leaves Flowering");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_ROOTS.get(), "Soul Afflicted Rubber Roots");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), "Soul Afflicted Rubber Leaves Old");	
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get(), "Soul Afflicted Rubber Log");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get(), "Soul Afflicted Rubber Wood");
		this.add(YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get(), "Soul Afflicted Partially Stripped Rubber Log");
		this.add(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get(), "Soul Afflicted Stripped Rubber Log");
		this.add(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get(), "Soul Afflicted Stripped Rubber Wood");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_PLANKS.get(), "Soul Afflicted Rubber Planks");
		this.add(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get(), "Soul Afflicted Fancy Rubber Planks (Untiled Whirl)");
		this.add(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get(), "Soul Afflicted Fancy Rubber Planks (Tiled Whirl)");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get(), "Soul Afflicted Rubber Stairs");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get(), "Soul Afflicted Rubber Slab");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get(), "Soul Afflicted Rubber Fence");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE_GATE.get(), "Fancy Fence Gate");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get(), "Rubber Door");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get(), "Soul Afflicted Rubber Trapdoor");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get(), "Soul Afflicted Rubber Pressure Plate");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_BUTTON.get(), "Soul Afflicted Rubber Button");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_SIGN.get(), "Soul Afflicted Rubber Sign");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_HANGING_SIGN.get(), "Soul Afflicted Rubber Hanging Sign");
		this.add(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get(), "Soul Afflicted Leaf Mulch");
		
		this.add(YATMBlocks.ADAMUM.get(), "Adamum");		
		this.add(YATMBlocks.POTTED_ADAMUM.get(), "Potted Adamum");
		
		this.add(YATMBlocks.AURUM.get(), "Aurum");		
		this.add(YATMBlocks.POTTED_AURUM.get(), "Potted Aurum");
		
		this.add(YATMBlocks.BASIN_OF_TEARS_FLORAL.get(), "Crying Flower");	
		this.add(YATMBlocks.BASIN_OF_TEARS_VEGETATION.get(), "Crying Plant Vegetation");
		
		this.add(YATMBlocks.CANDLELILY.get(), "Candlelily");		
		this.add(YATMBlocks.POTTED_CANDLELILY.get(), "Potted Candlelily");
		
		this.add(YATMBlocks.CARBUM.get(), "Carbum");		
		this.add(YATMBlocks.POTTED_CARBUM.get(), "Potted Carbum");
		
		this.add(YATMBlocks.CARCASS_ROOT_FOLIAGE.get(), "Carcass Root");		
		this.add(YATMBlocks.POTTED_CARCASS_ROOT_FOLIAGE.get(), "Potted Carcass Root");		
		this.add(YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get(), "Carcass Root Rooted Dirt");		
		this.add(YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get(), "Carcass Root Rooted Netherrack");	
		
		this.add(YATMBlocks.COTTON.get(), "Cotton");
		
		this.add(YATMBlocks.CUPRUM.get(), "Cuprum");		
		this.add(YATMBlocks.POTTED_CUPRUM.get(), "Potted Cuprum");
		
		this.add(YATMBlocks.FERRUM.get(), "Ferrum");			
		this.add(YATMBlocks.POTTED_FERRUM.get(), "Potted Ferrum");
		
		this.add(YATMBlocks.FIRE_EATER_LILY.get(), "Fire Eater Lily");	
		this.add(YATMBlocks.FIRE_EATER_LILY_DECORATIVE.get(), "Fire Eater Lily");		
		this.add(YATMBlocks.FIRE_EATER_LILY_UNLIT_DECORATIVE.get(), "Fire Eater Lily Unlit");		
		this.add(YATMBlocks.POTTED_FIRE_EATER_LILY.get(), "Potted Fire Eater Lily");		
		this.add(YATMBlocks.POTTED_FIRE_EATER_LILY_UNLIT.get(), "Potted Fire Eater Lily Unlit");		
		
		this.add(YATMBlocks.FOLIUM.get(), "Folium");		
		this.add(YATMBlocks.POTTED_FOLIUM.get(), "Potted Folium");		

		this.add(YATMBlocks.ICE_CORAL.get(), "Ice Coral");
		this.add(YATMBlocks.BLEACHED_ICE_CORAL_OLD.get(), "Bleached Ice Coral (Old)");
		this.add(YATMBlocks.BLEACHED_ICE_CORAL_ADOLESCENT.get(), "Bleached Ice Coral (Adolescent)");
		this.add(YATMBlocks.BLEACHED_ICE_CORAL_YOUNG.get(), "Bleached Ice Coral (Young)");
		this.add(YATMBlocks.BLEACHED_ICE_CORAL_POLYP.get(), "Bleached Ice Coral (Polyp)");
		this.add(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_OLD.get(), "Potted Bleached Ice Coral (Old)");
		this.add(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_ADOLESCENT.get(), "Potted Bleached Ice Coral (Adolescent)");
		this.add(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_YOUNG.get(), "Potted Bleached Ice Coral (Young)");
		this.add(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_POLYP.get(), "Potted Bleached Ice Coral (Polyp)");
		
		this.add(YATMBlocks.INFERNALUM.get(), "Infernalum");		
		this.add(YATMBlocks.POTTED_INFERNALUM.get(), "Potted Infernalum");
		
		this.add(YATMBlocks.LAPUM.get(), "Lapum");		
		this.add(YATMBlocks.POTTED_LAPUM.get(), "Potted Lapum");
		
		this.add(YATMBlocks.DWARF_PERSIMMON.get(), "Dwarf Persimmon");
		
		this.add(YATMBlocks.PHANTASMAL_SHELF_FUNGUS.get(), "Phantasmal Shelf Fungi");
		
		this.add(YATMBlocks.PITCHER_CLUSTER.get(), "Pitcher Cluster");
		
		this.add(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get(), "Prismarine Crystal Moss");	
		
		this.add(YATMBlocks.RUBERUM.get(), "Ruberum");		
		this.add(YATMBlocks.POTTED_RUBERUM.get(), "Potted Ruberum");
		
		this.add(YATMBlocks.SAMARAGDUM.get(), "Samaragdum");		
		this.add(YATMBlocks.POTTED_SAMARAGDUM.get(), "Potted Samaragdum");
		
		this.add(YATMBlocks.FALLEN_SHULKWART_SPORES.get(), "Fallen Shulkwart Spores");		
		this.add(YATMBlocks.SHULKWART.get(), "Shulkwart");		
		this.add(YATMBlocks.WHITE_SHULKWART.get(), "White Shulkwart");		
		this.add(YATMBlocks.ORANGE_SHULKWART.get(), "Orange Shulkwart");		
		this.add(YATMBlocks.MAGENTA_SHULKWART.get(), "Magenta Shulkwart");		
		this.add(YATMBlocks.LIGHT_BLUE_SHULKWART.get(), "Light Blue Shulkwart");		
		this.add(YATMBlocks.YELLOW_SHULKWART.get(), "Yellow Shulkwart");		
		this.add(YATMBlocks.LIME_SHULKWART.get(), "Lime Shulkwart");		
		this.add(YATMBlocks.PINK_SHULKWART.get(), "Pink Shulkwart");		
		this.add(YATMBlocks.GRAY_SHULKWART.get(), "Gray Shulkwart");		
		this.add(YATMBlocks.LIGHT_GRAY_SHULKWART.get(), "Light Gray Shulkwart");		
		this.add(YATMBlocks.CYAN_SHULKWART.get(), "Cyan Shulkwart");		
		this.add(YATMBlocks.PURPLE_SHULKWART.get(), "Purple Shulkwart");		
		this.add(YATMBlocks.BLUE_SHULKWART.get(), "Blue Shulkwart");		
		this.add(YATMBlocks.BROWN_SHULKWART.get(), "Brown Shulkwart");		
		this.add(YATMBlocks.GREEN_SHULKWART.get(), "Green Shulkwart");		
		this.add(YATMBlocks.RED_SHULKWART.get(), "Red Shulkwart");		
		this.add(YATMBlocks.BLACK_SHULKWART.get(), "Black Shulkwart");	
		
		this.add(YATMBlocks.SPIDER_VINE.get(), "Spider Vine");		
		this.add(YATMBlocks.SPIDER_VINE_MERISTEM.get(), "Spider Vine Meristem");	
		
		this.add(YATMBlocks.VARIEGATED_CACTUS.get(), "Variegated Cactus");
		this.add(YATMBlocks.POTTED_VARIEGATED_CACTUS.get(), "Potted Variegated Cactus");
		
		this.add(YATMBlocks.VICUM.get(), "Vicum");
		this.add(YATMBlocks.POTTED_VICUM.get(), "Potted Vicum");
		
		
		
		this.add(YATMBlocks.HANGING_POT_HOOK.get(), "Hanging Pot");
	
		this.add(YATMBlocks.CANDLE_LANTERN.get(), "Candle Lantern");

		this.add(YATMBlocks.WHITE_CANDLE_LANTERN.get(), "White Candle Lantern");
		this.add(YATMBlocks.ORANGE_CANDLE_LANTERN.get(), "Orange Candle Lantern");
		this.add(YATMBlocks.MAGENTA_CANDLE_LANTERN.get(), "Magenta Candle Lantern");
		this.add(YATMBlocks.LIGHT_BLUE_CANDLE_LANTERN.get(), "Light Blue Candle Lantern");
		this.add(YATMBlocks.YELLOW_CANDLE_LANTERN.get(), "Yellow Candle Lantern");
		this.add(YATMBlocks.LIME_CANDLE_LANTERN.get(), "Lime Candle Lantern");
		this.add(YATMBlocks.PINK_CANDLE_LANTERN.get(), "Pink Candle Lantern");
		this.add(YATMBlocks.GRAY_CANDLE_LANTERN.get(), "Gray Candle Lantern");
		this.add(YATMBlocks.LIGHT_GRAY_CANDLE_LANTERN.get(), "Light Gray Candle Lantern");
		this.add(YATMBlocks.CYAN_CANDLE_LANTERN.get(), "Cyan Candle Lantern");
		this.add(YATMBlocks.PURPLE_CANDLE_LANTERN.get(), "Purple Candle Lantern");
		this.add(YATMBlocks.BLUE_CANDLE_LANTERN.get(), "Blue Candle Lantern");
		this.add(YATMBlocks.BROWN_CANDLE_LANTERN.get(), "Brown Candle Lantern");
		this.add(YATMBlocks.GREEN_CANDLE_LANTERN.get(), "Green Candle Lantern");
		this.add(YATMBlocks.RED_CANDLE_LANTERN.get(), "Red Candle Lantern");
		this.add(YATMBlocks.BLACK_CANDLE_LANTERN.get(), "Black Candle Lantern");
		
		this.add(YATMBlocks.FOLIAR_STEEL_ORE.get(), "Foliar Steel Ore");
		this.add(YATMBlocks.DEEPSLATE_FOLIAR_STEEL_ORE.get(), "Deepslate Foliar Steel Ore");
		this.add(YATMBlocks.FOLIAR_STEEL_BLOCK.get(), "Foliar Steel Block");
		this.add(YATMBlocks.RUBBER_BLOCK.get(), "Rubber Block");
		this.add(YATMBlocks.ROOTED_SOUL_SOIL.get(), "Rooted Soul Soil");
		
		
		
		this.add(YATMBlocks.GRAFTING_TABLE.get(), "Grafting Table");
		
		this.add(YATMBlocks.SAP_COLLECTOR.get(), "Sap Collector");
		this.add(YATMBlocks.SAP_COLLECTOR_LATEX.get(), "Sap Collector (Latex)");
		this.add(YATMBlocks.SAP_COLLECTOR_SOUL_SAP.get(), "Sap Collector {Soul Sap}");
		
		this.add(YATMBlocks.SPINNING_WHEEL.get(), "Spinning Wheel");
		
		this.add(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), "Large Copper Heat Sink");
		
		this.add(YATMBlocks.STEEL_BIOREACTOR.get(), "Foliar Steel Bioreactor");
		this.add(YATMBlocks.STEEL_BOILER.get(), "Foliar Steel Boiler");
		this.add(YATMBlocks.STEEL_CRUCIBLE.get(), "Foliar Steel Crucible");
		this.add(YATMBlocks.STEEL_CRYSTALLIZER.get(), "Foliar Steel Crystallizer");
		this.add(YATMBlocks.STEEL_CURRENT_FURNACE.get(), "Steel Current Furnace");
		this.add(YATMBlocks.STEEL_EXTRACTOR.get(), "Foliar Steel Extractor");
//		this.add(YATMBlocks.STEEL_EXTRUDER.get(), "Foliar Steel Extruder");
		this.add(YATMBlocks.STEEL_GRINDER.get(), "Foliar Steel Grinder");
		this.add(YATMBlocks.STEEL_INJECTOR.get(), "Foliar Steel Injector");
		this.add(YATMBlocks.STEEL_STILL.get(), "Foliar Steel Still");
		
		this.add(YATMBlocks.C_U_F_E_I.get(), "Energy Converter (CU to FE)");
		
		this.add(YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get(), "Crude Battery Solar Panel");
		this.add(YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get(), "Advanced Battery Solar Panel");
		this.add(YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get(), "Sun's Complement Battery Solar Panel");
		this.add(YATMBlocks.CRUDE_SOLAR_PANEL.get(), "Crude Solar Panel");
		this.add(YATMBlocks.ADVANCED_SOLAR_PANEL.get(), "Advanced Solar Panel");
		this.add(YATMBlocks.SUNS_COMPLEMENT_SOLAR_PANEL.get(), "Sun's Complement Solar Panel");
		
		this.add(YATMBlocks.CONDUIT_VINES.get(), "Conduit Vines");
		
		this.add(YATMBlocks.STEEL_TANK.get(), "Foliar Steel Tank");
		
		this.add(YATMBlocks.CHANNEL_VINES.get(), "Channel Vines");
		
		this.add(YATMBlocks.BIO_LIQUID_BLOCK.get(), "Biofluid");
		this.add(YATMBlocks.CHORUS_LIQUID_BLOCK.get(), "Chorus");
		this.add(YATMBlocks.CHORUS_BIO_LIQUID_BLOCK.get(), "Chorus Biofluid");
		this.add(YATMBlocks.ENDER_LIQUID_BLOCK.get(), "Ender");		
		this.add(YATMBlocks.ESSENCE_OF_DECAY_LIQUID_BLOCK.get(), "Essence Of Decay");
		this.add(YATMBlocks.ESSENCE_OF_SOULS_LIQUID_BLOCK.get(), "Essence Of Souls");
		this.add(YATMBlocks.LATEX_LIQUID_BLOCK.get(), "Latex");
		this.add(YATMBlocks.SOUL_SAP_LIQUID_BLOCK.get(), "Soul Sap");
		this.add(YATMBlocks.SOUL_SYRUP_LIQUID_BLOCK.get(), "Soul Syrup");
		
		this.add(YATMBlocks.CELESTIAL_LIGHT_LIQUID_BLOCK.get(), "Celestial Light");
		this.add(YATMBlocks.LUNAR_LIGHT_LIQUID_BLOCK.get(), "Lunar light");
		this.add(YATMBlocks.SOLAR_LIGHT_LIQUID_BLOCK.get(), "Solar Light");
		
		this.add(YATMBlocks.CREATIVE_CURRENT_SOURCE.get(), "Create Current Source");
		
		
		
		// ITEMS \\
		this.add(YATMItems.RUBBER_BOAT.get(), "Rubber Boat");
		this.add(YATMItems.RUBBER_CHEST_BOAT.get(), "Rubber Chest Boat");
		this.add(YATMItems.SOUL_AFFLICTED_RUBBER_BOAT.get(), "Soul Afflicted Rubber Boat");
		this.add(YATMItems.SOUL_AFFLICTED_RUBBER_CHEST_BOAT.get(), "Soul Afflicted Rubber Chest Boat");
					
		this.add(YATMItems.BASIN_OF_TEARS_SEED.get(), "Crying Plant Seeds");
		this.add(YATMItems.DILUTED_TEAR_BOTTLE.get(), "Diluted Tear Bottle");		

		this.add(YATMItems.CARCASS_ROOT_CUTTING.get(), "Carcass Root Cutting");
		
		this.add(YATMItems.COTTON_SEEDS.get(), "Cotton Seeds");
		this.add(YATMItems.COTTON_BOLLS.get(), "Cotton Bolls");
		this.add(YATMItems.RAW_COTTON_FIBER.get(), "Raw Cotton Fiber");
		
		this.add(YATMItems.FIRE_EATER_LILY_BULB.get(), "Fire Eater Lily Bulb");
		
		this.add(YATMItems.ICE_CORAL_POLYP.get(), "Ice Coral Polyp");

		this.add(YATMItems.PERSIMMON.get(), "Persimmon");
		
		this.add(YATMItems.PRISMARINE_CRYSTAL_MOSS_SPORES.get(), "Prismarine Crystal Moss Spores");

		this.add(YATMItems.SHULKWART_SPORES.get(), "Shulkwart Spores");
		this.add(YATMItems.SHULKWART_HORN.get(), "Shulkwart Horn");
		this.add(YATMItems.WHITE_SHULKWART_HORN.get(), "White Shulkwart Horn");
		this.add(YATMItems.ORANGE_SHULKWART_HORN.get(), "Orange Shulkwart Horn");
		this.add(YATMItems.MAGENTA_SHULKWART_HORN.get(), "Magenta Shulkwart Horn");
		this.add(YATMItems.LIGHT_BLUE_SHULKWART_HORN.get(), "Light Blue Shulkwart Horn");
		this.add(YATMItems.YELLOW_SHULKWART_HORN.get(), "Yellow Shulkwart Horn");
		this.add(YATMItems.LIME_SHULKWART_HORN.get(), "Lime Shulkwart Horn");
		this.add(YATMItems.PINK_SHULKWART_HORN.get(), "Pink Shulkwart Horn");
		this.add(YATMItems.GRAY_SHULKWART_HORN.get(), "Gray Shulkwart Horn");
		this.add(YATMItems.LIGHT_GRAY_SHULKWART_HORN.get(), "Light Gray Shulkwart Horn");
		this.add(YATMItems.CYAN_SHULKWART_HORN.get(), "Cyan Shulkwart Horn");
		this.add(YATMItems.PURPLE_SHULKWART_HORN.get(), "Purple Shulkwart Horn");
		this.add(YATMItems.BLUE_SHULKWART_HORN.get(), "Blue Shulkwart Horn");
		this.add(YATMItems.BROWN_SHULKWART_HORN.get(), "Brown Shulkwart Horn");
		this.add(YATMItems.GREEN_SHULKWART_HORN.get(), "Green Shulkwart Horn");
		this.add(YATMItems.RED_SHULKWART_HORN.get(), "Red Shulkwar Hornt");
		this.add(YATMItems.BLACK_SHULKWART_HORN.get(), "Black Shulkwart Horn");
		
		this.add(YATMItems.BRANCH_OF_GLARING_FRUIT.get(), "Branch Of Glaring Fruit");
		
		this.add(YATMItems.RAW_EXOTHEMIC_GLAND.get(), "Raw Exothermal Gland");
		
		this.add(YATMItems.ADVANCED_CURRENT_BATTERY.get(), "Advanced Current Battery");
		this.add(YATMItems.CURRENT_BATTERY.get(), "Current Battery");
		this.add(YATMItems.CURRENT_TUBER.get(), "Current Tuber");
		this.add(YATMItems.EMBER_GLAND.get(), "Ember Gland");
		this.add(YATMItems.FLAME_GLAND.get(), "Flame Gland");
		this.add(YATMItems.TORCH_GLAND.get(), "Torch Gland");
		
		
		this.add(YATMItems.BIO_BUCKET.get(), "Biofluid Bucket");
		this.add(YATMItems.CHORUS_BUCKET.get(), "Chorus Fluid Bucket");
		this.add(YATMItems.CHORUS_BIO_BUCKET.get(), "Chorus Biofluid Bucket");
		this.add(YATMItems.ENDER_BUCKET.get(), "Liquid Ender Bucket");
		this.add(YATMItems.ESSENCE_OF_DECAY_BUCKET.get(), "Essence Of Decay Bucket");
		this.add(YATMItems.ESSENCE_OF_SOULS_BUCKET.get(), "Essence Of Souls Bucket");		
		this.add(YATMItems.LATEX_BUCKET.get(), "Latex Bucket");
		this.add(YATMItems.SILICON_OXIDE_BUCKET.get(), "Silicon Oxide Bucket");
		this.add(YATMItems.SOUL_SAP_BUCKET.get(), "Soul Sap Bucket");
		this.add(YATMItems.SOUL_SYRUP_BUCKET.get(), "Soul Syrup Bucket");
		
		this.add(YATMItems.BIO_BOTTLE.get(), "Biofluid Bottle");
		this.add(YATMItems.CHORUS_BOTTLE.get(), "Chorus Fluid Bottle");
		this.add(YATMItems.CHORUS_BIO_BOTTLE.get(), "Chorus Biofluid Bottle");
		this.add(YATMItems.ENDER_BOTTLE.get(), "Liquid Ender Bottle");
		this.add(YATMItems.ESSENCE_OF_DECAY_BOTTLE.get(), "Essence Of Decay Bottle");
		this.add(YATMItems.ESSENCE_OF_SOULS_BOTTLE.get(), "Essence Of Souls Bottle");		
		this.add(YATMItems.LATEX_BOTTLE.get(), "Latex Bottle");
		this.add(YATMItems.SOUL_SAP_BOTTLE.get(), "Soul Sap Bottle");
		this.add(YATMItems.SOUL_SYRUP_BOTTLE.get(), "Soul Syrup Bottle");

				
			
		this.add(YATMItems.FOLIAR_STEEL.get(), "Foliar Steel");
		this.add(YATMItems.FOLIAR_STEEL_SHRED.get(), "Foliar Steel Nugget");
		this.add(YATMItems.RUBBER_BAR.get(), "Rubber Bar");

		this.add(YATMItems.WOOD_PULP.get(), "Wood Pulp");
		
		
		this.add(YATMItems.NETHERITE_NUGGET.get(), "Netherite Nugget");
		this.add(YATMItems.STAR_SEED.get(), "Star Seed");
		this.add(YATMItems.STAR_GERMLING.get(), "Start Germling");
		this.add(YATMItems.STAR_SPROUT.get(), "Star Sprout");
		this.add(YATMItems.STAR_ADOLESCENT.get(), "Star Adolescent");
		
		this.add(YATMItems.KINETIC_DRIVER.get(), "Kinetic Driver");
		
		this.add(YATMItems.EFFICIENCY_UPGRADE.get(), "Efficiency Upgrade (+100%)");
		this.add(YATMItems.SPEED_UPGRADE.get(), "Speed Upgrade (+100%)");
		
		this.add(YATMItems.WOODEN_DRILL_BIT.get(), "Wooden Drill Bit");
		this.add(YATMItems.STONE_DRILL_BIT.get(), "Stone Drill Bit");
		this.add(YATMItems.IRON_DRILL_BIT.get(), "Iron Drill Bit");
		this.add(YATMItems.STEEL_DRILL_BIT.get(), "Foliar Steel Drill Bit");
		this.add(YATMItems.GOLD_DRILL_BIT.get(), "Gold Drill Bit");
		this.add(YATMItems.DIAMOND_DRILL_BIT.get(), "Diamond Drill Bit");
		this.add(YATMItems.NETHERITE_DRILL_BIT.get(), "Netherite Drill Bit");
		
		this.add(YATMItems.WOODEN_SAW_BLADE.get(), "Wooden Saw Blade");
		this.add(YATMItems.STONE_SAW_BLADE.get(), "Stone Saw Blade");
		this.add(YATMItems.IRON_SAW_BLADE.get(), "Iron Saw Blade");
		this.add(YATMItems.STEEL_SAW_BLADE.get(), "Foliar Steel Saw Blade");
		this.add(YATMItems.GOLD_SAW_BLADE.get(), "Gold Saw Blade");
		this.add(YATMItems.DIAMOND_SAW_BLADE.get(), "Diamond Saw Blade");
		this.add(YATMItems.NETHERITE_SAW_BLADE.get(), "Netherite Saw Blade");
		
		this.add(YATMItems.STEEL_DRILL_WOOD.get(), "Foliar Steel Drill (Wood)");
		this.add(YATMItems.STEEL_DRILL_STONE.get(), "Foliar Steel Drill (Stone)");
		this.add(YATMItems.STEEL_DRILL_IRON.get(), "Foliar Steel Drill (Iron)");
		this.add(YATMItems.STEEL_DRILL_STEEL.get(), "Foliar Steel Drill (Foliar Steel)");
		this.add(YATMItems.STEEL_DRILL_GOLD.get(), "Foliar Steel Drill (Gold)");
		this.add(YATMItems.STEEL_DRILL_DIAMOND.get(), "Foliar Steel Drill (Diamond)");
		this.add(YATMItems.STEEL_DRILL_NETHERITE.get(), "Foliar Steel Drill (Netherite)");
		
		this.add(YATMItems.STEEL_SAW_WOOD.get(), "Foliar Steel Saw (Wood)");
		this.add(YATMItems.STEEL_SAW_STONE.get(), "Foliar Steel Saw (Stone)");
		this.add(YATMItems.STEEL_SAW_IRON.get(), "Foliar Steel Saw (Iron)");
		this.add(YATMItems.STEEL_SAW_STEEL.get(), "Foliar Steel Saw (Foliar Steel)");
		this.add(YATMItems.STEEL_SAW_GOLD.get(), "Foliar Steel Saw (Gold)");
		this.add(YATMItems.STEEL_SAW_DIAMOND.get(), "Foliar Steel Saw (Diamond)");
		this.add(YATMItems.STEEL_SAW_NETHERITE.get(), "Foliar Steel Saw (Netherite)");
		
		this.add(YATMItems.STEEL_WRENCH.get(), "Foliar Steel Wrench");
		
		this.add(YATMItems.DECAY_NETHERITE_HELMET.get(), "Decay Netherite Helmet");
		this.add(YATMItems.DECAY_NETHERITE_CHESTPLATE.get(), "Decay Netherite Chest Plate");
		this.add(YATMItems.DECAY_NETHERITE_LEGGINGS.get(), "Decay Netherite Leggings");
		this.add(YATMItems.DECAY_NETHERITE_BOOTS.get(), "Decay Netherite Boots");
		
		this.add(YATMItems.FOLIAR_STEEL_HELMET.get(), "Foliar Steel Helmet");
		this.add(YATMItems.FOLIAR_STEEL_CHESTPLATE.get(), "Foliar Steel Chest Plate");
		this.add(YATMItems.FOLIAR_STEEL_LEGGINGS.get(), "Foliar Steel Leggings");
		this.add(YATMItems.FOLIAR_STEEL_BOOTS.get(), "Foliar Steel Boots");
		
		this.add(YATMItems.SOUL_ADORNED_NETHERITE_HELMET.get(), "Soul Adored Netherite Helmet");
		this.add(YATMItems.SOUL_ADORNED_NETHERITE_CHESTPLATE.get(), "Soul Adored Netherite Chest Plate");
		this.add(YATMItems.SOUL_ADORNED_NETHERITE_LEGGINGS.get(), "Soul Adored Netherite Leggings");
		this.add(YATMItems.SOUL_ADORNED_NETHERITE_BOOTS.get(), "Soul Adored Netherite Boots");
		
		this.add(YATMItems.CREATIVE_FLUID_SOURCE.get(), "Creative Fluid Source");
		this.add(YATMItems.CREATIVE_FLUID_STORER.get(), "Creative Fluid Storer");
		this.add(YATMItems.CREATIVE_FLUID_VOID.get(), "Creative Fluid Void");
		
		
		// MOB EFFECTS \\
		this.add(YATMMobEffects.CHORUS_INSTANT_TELEPORTATION.get(), "Instant Teleportation");
		this.add(YATMMobEffects.ENDER_INSTANT_TELEPORTATION.get(), "Instant Teleportation");		
		this.add(YATMMobEffects.MOLT_FATIGUE.get(), "Molt Fatigue");
		this.add(YATMMobEffects.SOUL_AFFLICTION.get(), "Soul Affliction");
		
		
		
		// FLUIDS\\
		this.add(YATMFluids.BIO.get(), "Biofluid");
		this.add(YATMFluids.CHORUS.get(), "Chorus");
		this.add(YATMFluids.CHORUS_BIO.get(), "Chorus Biofluid");
		this.add(YATMFluids.ENDER.get(), "Ender");		
		this.add(YATMFluids.ESSENCE_OF_DECAY.get(), "Essence Of Decay");
		this.add(YATMFluids.ESSENCE_OF_SOULS.get(), "Essence Of Souls");
		this.add(YATMFluids.LATEX.get(), "Latex");
		this.add(YATMFluids.SILICON_OXIDE.get(), "Silicon Oxide");
		this.add(YATMFluids.SOUL_SAP.get(), "Soul Sap");
		this.add(YATMFluids.SOUL_SYRUP.get(), "Soul Syrup");
		
		this.add(YATMFluids.CELESTIAL_LIGHT.get(), "Celestial Light");
		this.add(YATMFluids.LUNAR_LIGHT.get(), "Lunar light");
		this.add(YATMFluids.SOLAR_LIGHT.get(), "Solar Light");
		
		
		
		this.addMinecraftTranslations();		
		
	} // end addTranslations()

	private void addMinecraftTranslations() 
	{
		this.add(Fluids.EMPTY, "Empty");
		this.add(Fluids.FLOWING_LAVA, "Lava");
		this.add(Fluids.LAVA, "Lava");
		this.add(Fluids.FLOWING_WATER, "Water");
		this.add(Fluids.WATER, "Water");
	} // end addMinecraftTranslations()
	
} // end class