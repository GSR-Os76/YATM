package com.gsr.gsr_yatm.data_generation;

import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMCreativeModTabs;
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
		this.add(YATMCreativeModTabs.YATM_GENERAL, "Yet Another Tech Mod General");
		
		this.add(YATMMenuTypes.BIOLER.get(), "Bioler");		
		this.add(YATMMenuTypes.BOILER.get(), "Boiler");		
		this.add(YATMMenuTypes.CRYSTALLIZER.get(), "Crystallizer");		
		this.add(YATMMenuTypes.EXTRUDER.get(), "Extruder");		
		this.add(YATMMenuTypes.EXTRACTOR.get(), "Extractor");		
		this.add(YATMMenuTypes.GRINDER.get(), "Grinder");		
		
		this.add(YATMMenuTypes.BATTERY_SOLAR_PANEL.get(), "Solar Panel");		
		this.add(YATMMenuTypes.SOLAR_PANEL.get(), "Solar Panel");		
		
		
		
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
		this.add(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get(), "Soul Afflicted Leaf Mulch");
		
		this.add(YATMBlocks.PHANTASMAL_SHELF_FUNGUS.get(), "Phantasmal Shelf Fungi");		
		this.add(YATMBlocks.COTTON.get(), "Cotton");		
		this.add(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get(), "Prismarine Crystal Moss");		
		this.add(YATMBlocks.SPIDER_VINE.get(), "Spider Vine");		
		this.add(YATMBlocks.SPIDER_VINE_MERISTEM.get(), "Spider Vine Meristem");		
		
		
		this.add(YATMBlocks.RUBBER_BLOCK.get(), "Rubber Block");
		this.add(YATMBlocks.ROOTED_SOUL_SOIL.get(), "Rooted Soul Soil");
		
		
		this.add(YATMBlocks.SPINNING_WHEEL.get(), "Spinning Wheel");
		
		this.add(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), "Large Copper Heat Sink");
		
		this.add(YATMBlocks.STEEL_BIOLER.get(), "Steel Bioler");
		this.add(YATMBlocks.STEEL_BOILER.get(), "Steel Boiler");
		this.add(YATMBlocks.STEEL_BOILER_TANK.get(), "Steel Boiler Tank");
		this.add(YATMBlocks.STEEL_CRYSTALLIZER.get(), "Steel Crystallizer");
		this.add(YATMBlocks.STEEL_EXTRACTOR.get(), "Steel Extractor");
		this.add(YATMBlocks.STEEL_EXTRUDER.get(), "Steel Extruder");
		this.add(YATMBlocks.STEEL_GRINDER.get(), "Steel Grinder");
		
		this.add(YATMBlocks.C_U_F_E_I.get(), "Energy Converter (CU to FE)");
		
		this.add(YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get(), "Crude Battery Solar Panel");
		this.add(YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get(), "Advanced Battery Solar Panel");
		this.add(YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get(), "Sun's Complement Battery Solar Panel");
		this.add(YATMBlocks.CRUDE_SOLAR_PANEL.get(), "Crude Solar Panel");
		this.add(YATMBlocks.ADVANCED_SOLAR_PANEL.get(), "Advanced Solar Panel");
		this.add(YATMBlocks.SUNS_COMPLEMENT_SOLAR_PANEL.get(), "Sun's Complement Solar Panel");
		
		
		
		this.add(YATMBlocks.ONE_CU_WIRE.get(), "1cu Wire");
		this.add(YATMBlocks.EIGHT_CU_WIRE.get(), "8cu Wire");
		this.add(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), "64cu Wire");
		this.add(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), "512cu Wire");
		this.add(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), "4096cu Wire");
		
		this.add(YATMBlocks.ENAMELED_ONE_CU_WIRE.get(), "Enameled 1cu Wire");
		this.add(YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get(), "Enameled 8cu Wire");
		this.add(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get(), "Enameled 64cu Wire");
		this.add(YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get(), "Enameled 512cu Wire");
		this.add(YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), "Enameled 4096cu Wire");
		
		this.add(YATMBlocks.INSULATED_ONE_CU_WIRE.get(), "Insulated 1cu Wire");
		this.add(YATMBlocks.INSULATED_EIGHT_CU_WIRE.get(), "Insulated 8cu Wire");
		this.add(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get(), "Insulated 64cu Wire");
		this.add(YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get(), "Insulated 512cu Wire");
		this.add(YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), "Insulated 4096cu Wire");
		
		this.add(YATMBlocks.STEEL_FLUID_CONDUIT.get(), "Steel Fluid Conduit");
		
		
		// ITEMS \\
		this.add(YATMItems.STEEL_FLUID_EXCHANGER.get(), "Steel Fluid Exchanger");
		
		this.add(YATMItems.ONE_CU_CURRENT_REGULATOR.get(), "1cu Current Regulator");
		this.add(YATMItems.EIGHT_CU_CURRENT_REGULATOR.get(), "8cu Current Regulator");
		this.add(YATMItems.SIXTYFOUR_CU_CURRENT_REGULATOR.get(), "64cu Current Regulator");
		this.add(YATMItems.FIVEHUNDREDTWELVE_CU_CURRENT_REGULATOR.get(), "512cu Current Regulator");
		this.add(YATMItems.FOURTHOUSANDNINTYSIX_CU_CURRENT_REGULATOR.get(), "4096cu Current Regulator");
		
		this.add(YATMItems.ONE_CU_CURRENT_FUSE.get(), "1cu Current Fuse");
		this.add(YATMItems.EIGHT_CU_CURRENT_FUSE.get(), "8cu Current Fuse");
		this.add(YATMItems.SIXTYFOUR_CU_CURRENT_FUSE.get(), "64cu Current Fuse");
		this.add(YATMItems.FIVEHUNDREDTWELVE_CU_CURRENT_FUSE.get(), "512cu Current Fuse");
		this.add(YATMItems.FOURTHOUSANDNINTYSIX_CU_CURRENT_FUSE.get(), "4096cu Current Fuse");
		
		this.add(YATMItems.ONE_CU_CURRENT_BREAKER.get(), "1cu Current Breaker");
		this.add(YATMItems.EIGHT_CU_CURRENT_BREAKER.get(), "8cu Current Breaker");
		this.add(YATMItems.SIXTYFOUR_CU_CURRENT_BREAKER.get(), "64cu Current Breaker");
		this.add(YATMItems.FIVEHUNDREDTWELVE_CU_CURRENT_BREAKER.get(), "512cu Current Breaker");
		this.add(YATMItems.FOURTHOUSANDNINTYSIX_CU_CURRENT_BREAKER.get(), "4096cu Current Breaker");
		
		
		
		this.add(YATMItems.BIO_BUCKET.get(), "Biofluid Bucket");
		this.add(YATMItems.CHORUS_BUCKET.get(), "Chorus Fluid Bucket");
		this.add(YATMItems.CHORUS_BIO_BUCKET.get(), "Chorus Biofluid Bucket");
		this.add(YATMItems.ENDER_BUCKET.get(), "Liquid Ender Bucket");
		this.add(YATMItems.ESSENCE_OF_DECAY_BUCKET.get(), "Essence Of Decay Bucket");
		this.add(YATMItems.ESSENCE_OF_SOULS_BUCKET.get(), "Essence Of Souls Bucket");		
		this.add(YATMItems.LATEX_BUCKET.get(), "Latex Bucket");
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

				
		
		this.add(YATMItems.IRON_WIRE_DIE.get(), "Iron Die (Wire)");
		this.add(YATMItems.STEEL_WIRE_DIE.get(), "Steel Die (Wire)");
		
		this.add(YATMItems.SILVER_INGOT.get(), "Silver Ingot");
		this.add(YATMItems.STEEL_INGOT.get(), "Steel Ingot");
		this.add(YATMItems.RUBBER_BAR.get(), "Rubber Bar");
		this.add(YATMItems.RUBBER_SCRAP_BALL.get(), "Rubber Scrap Ball");
		
		this.add(YATMItems.SILVER_NUGGET.get(), "Silver Nugget");
		this.add(YATMItems.COPPER_NUGGET.get(), "Copper Nugget");
		this.add(YATMItems.NETHERITE_NUGGET.get(), "Netherite Nugget");
		this.add(YATMItems.WAX_BIT_ITEM.get(), "Wax Bit");
		this.add(YATMItems.RUBBER_SCRAP.get(), "Rubber Scraps");
		
		this.add(YATMItems.WOOD_PULP.get(), "Wood Pulp");
		
		this.add(YATMItems.COTTON_SEEDS.get(), "Cotton Seeds");
		this.add(YATMItems.COTTON_BOLLS.get(), "Cotton Bolls");
		this.add(YATMItems.RAW_COTTON_FIBER.get(), "Raw Cotton Fiber");
		
		this.add(YATMItems.PRISMARINE_CRYSTAL_MOSS_SPORES.get(), "Prismarine Crystal Moss Spores");
		
		this.add(YATMItems.SPIDER_VINE_FRUITS.get(), "Branch Of Glaring Fruit");
		
		this.add(YATMItems.STAR_SEED.get(), "Star Seed");
		this.add(YATMItems.STAR_GERMLING.get(), "Start Germling");
		this.add(YATMItems.STAR_SPROUT.get(), "Star Sprout");
		this.add(YATMItems.STAR_ADOLESCENT.get(), "Star Adolescent");
		
		this.add(YATMItems.CREATIVE_FLUID_SOURCE.get(), "Creative Fluid Source");
		this.add(YATMItems.CREATIVE_FLUID_STORER.get(), "Creative Fluid Storer");
		this.add(YATMItems.CREATIVE_FLUID_VOID.get(), "Creative Fluid Void");
		
		
		
		this.add(YATMMobEffects.CHORUS_INSTANT_TELEPORTATION.get(), "Instant Teleportation");
		this.add(YATMMobEffects.ENDER_INSTANT_TELEPORTATION.get(), "Instant Teleportation");		
		this.add(YATMMobEffects.SOUL_AFFLICTION.get(), "Soul Affliction");
		
		
		
		this.add(YATMFluids.BIO.get(), "Biofluid");
		this.add(YATMFluids.CHORUS.get(), "Chorus");
		this.add(YATMFluids.CHORUS_BIO.get(), "Chorus Biofluid");
		this.add(YATMFluids.ENDER.get(), "Ender");		
		this.add(YATMFluids.ESSENCE_OF_DECAY.get(), "Essence Of Decay");
		this.add(YATMFluids.ESSENCE_OF_SOULS.get(), "Essence Of Souls");
		this.add(YATMFluids.LATEX.get(), "Latex");
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