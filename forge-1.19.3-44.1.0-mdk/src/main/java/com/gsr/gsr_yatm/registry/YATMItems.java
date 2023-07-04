package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YATMCreativeModTabs;
import com.gsr.gsr_yatm.YATMFoods;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.creative.CreativeFluidSourceItem;
import com.gsr.gsr_yatm.creative.CreativeFluidStorerItem;
import com.gsr.gsr_yatm.creative.CreativeFluidVoidItem;
import com.gsr.gsr_yatm.fluid.item.DrinkableFluidBottleItem;
import com.gsr.gsr_yatm.fluid.item.FluidBottleItem;
import com.gsr.gsr_yatm.fluid.item.SoulSapBucketItem;
import com.gsr.gsr_yatm.item.InsulatingItem;
import com.gsr.gsr_yatm.item.component.CurrentRegulatorItem;
import com.gsr.gsr_yatm.item.component.fluid.FluidExchangerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YetAnotherTechMod.MODID);

	
	
	public static final RegistryObject<BlockItem> RUBBER_MERISTEM_ITEM = generalTabEnqueue(ITEMS.register("rubber_meristem", () -> new BlockItem(YATMBlocks.RUBBER_MERISTEM.get(), new Item.Properties())));
	// public static final RegistryObject<BlockItem> POTTED_RUBBER_MERISTEM_ITEM = generalTabEnqueue(ITEMS.register("potted_rubber_meristem", () -> new BlockItem(YATMBlocks.POTTED_RUBBER_MERISTEM.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_LEAVES_YOUNG_ITEM = generalTabEnqueue(ITEMS.register("rubber_leaves_young", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_YOUNG.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_LEAVES_FLOWERING_ITEM = generalTabEnqueue(ITEMS.register("rubber_leaves_flowering", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_FLOWERING.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> RUBBER_LEAVES_OLD_ITEM = generalTabEnqueue(ITEMS.register("rubber_leaves_old", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_OLD.get(), new Item.Properties())));
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
	

	
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_meristem", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), new Item.Properties())));
	// public static final RegistryObject<BlockItem> POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM = generalTabEnqueue(ITEMS.register("potted_soul_afflicted_rubber_meristem", () -> new BlockItem(YATMBlocks.POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM.get(), new Item.Properties())));
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
	public static final RegistryObject<BlockItem> SOUL_AFFLICTED_LEAF_MULCH_ITEM = generalTabEnqueue(ITEMS.register("soul_afflicted_leaf_mulch", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get(), new Item.Properties())));
	
	public static final RegistryObject<BlockItem> PHANTASMAL_SHELF_FUNGUS_ITEM = generalTabEnqueue(ITEMS.register("phantasmal_shelf_fungus", () -> new BlockItem(YATMBlocks.PHANTASMAL_SHELF_FUNGUS.get(), new Item.Properties())));
		
	
	
	public static final RegistryObject<BlockItem> RUBBER_BLOCK_ITEM = generalTabEnqueue(ITEMS.register("rubber_block", () -> new BlockItem(YATMBlocks.RUBBER_BLOCK.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ROOTED_SOUL_SOIL_ITEM = generalTabEnqueue(ITEMS.register("rooted_soul_soil", () -> new BlockItem(YATMBlocks.ROOTED_SOUL_SOIL.get(), new Item.Properties())));
	
	// add celestial altar
	// add liquid celestial light, liquid lunar light, and the liquid solar liquids;
	// add their ingots from alloying them with molten netherite
	// for these at least tools I feel are worthy to make
	
	
	public static final RegistryObject<BlockItem> LARGE_COPPER_HEAT_SINK_ITEM = generalTabEnqueue(ITEMS.register("large_copper_heat_sink", () -> new BlockItem(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), new Item.Properties())));

	public static final RegistryObject<BlockItem> STEEL_BIOLER_ITEM = generalTabEnqueue(ITEMS.register("steel_bioler", () -> new BlockItem(YATMBlocks.STEEL_BIOLER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_BOILER_TANK_ITEM = generalTabEnqueue(ITEMS.register("steel_boiler_tank", () -> new BlockItem(YATMBlocks.STEEL_BOILER_TANK.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_BOILER_ITEM = generalTabEnqueue(ITEMS.register("steel_boiler", () -> new BlockItem(YATMBlocks.STEEL_BOILER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_CRYSTALLIZER_ITEM = generalTabEnqueue(ITEMS.register("steel_crystallizer", () -> new BlockItem(YATMBlocks.STEEL_CRYSTALLIZER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_EXTRACTOR_ITEM = generalTabEnqueue(ITEMS.register("steel_extractor", () -> new BlockItem(YATMBlocks.STEEL_EXTRACTOR.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_EXTRUDER_ITEM = generalTabEnqueue(ITEMS.register("steel_extruder", () -> new BlockItem(YATMBlocks.STEEL_EXTRUDER.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> STEEL_GRINDER_ITEM = generalTabEnqueue(ITEMS.register("steel_grinder", () -> new BlockItem(YATMBlocks.STEEL_GRINDER.get(), new Item.Properties())));
	
	public static final RegistryObject<BlockItem> C_U_F_E_I_ITEM = generalTabEnqueue(ITEMS.register("current_unit_forge_energy_interchanger", () -> new BlockItem(YATMBlocks.C_U_F_E_I.get(), new Item.Properties())));

	public static final RegistryObject<BlockItem> CRUDE_SOLAR_PANEL_ITEM = generalTabEnqueue(ITEMS.register("crude_solar_panel", () -> new BlockItem(YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ADVANCED_SOLAR_PANEL_ITEM = generalTabEnqueue(ITEMS.register("advanced_crude_solar_panel", () -> new BlockItem(YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SUNS_COMPLEMENT_SOLAR_PANEL_ITEM = generalTabEnqueue(ITEMS.register("suns_complement_solar_panel", () -> new BlockItem(YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get(), new Item.Properties())));


	
	public static final RegistryObject<BlockItem> ONE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("one_cu_wire", () -> new BlockItem(YATMBlocks.ONE_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> EIGHT_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("eight_cu_wire", () -> new BlockItem(YATMBlocks.EIGHT_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> SIXTYFOUR_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("sixtyfour_cu_wire", () -> new BlockItem(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> FIVEHUNDREDTWELVE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("fivehundredtwelve_cu_wire", () -> new BlockItem(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("fourthousandnintysix_cu_wire", () -> new BlockItem(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ENAMELED_ONE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_one_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_ONE_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ENAMELED_EIGHT_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_eight_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ENAMELED_SIXTYFOUR_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_sixtyfour_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_fivehundredtwelve_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("enameled_fourthousandnintysix_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> INSULATED_ONE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_one_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_ONE_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> INSULATED_EIGHT_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_eight_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_EIGHT_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> INSULATED_SIXTYFOUR_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_sixtyfour_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> INSULATED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_fivehundredtwelve_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get(), new Item.Properties())));
	public static final RegistryObject<BlockItem> INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM = generalTabEnqueue(ITEMS.register("insulated_fourthousandnintysix_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), new Item.Properties())));
	
	public static final RegistryObject<BlockItem> STEEL_FLUID_CONDUIT_ITEM = generalTabEnqueue(ITEMS.register("steel_fluid_conduit", () -> new BlockItem(YATMBlocks.STEEL_FLUID_CONDUIT.get(), new Item.Properties())));
		
	
	
	public static final RegistryObject<FluidExchangerItem> STEEL_FLUID_EXCHANGER = generalTabEnqueue(ITEMS.register("steel_fluid_exchanger_component", () -> new FluidExchangerItem(new Item.Properties().stacksTo(1), 128)));
	
	public static final RegistryObject<CurrentRegulatorItem> ONE_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("one_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 1, 8)));
	public static final RegistryObject<CurrentRegulatorItem> EIGHT_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("eight_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 8, 64)));
	public static final RegistryObject<CurrentRegulatorItem> SIXTYFOUR_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("sixtyfour_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 64, 512)));
	public static final RegistryObject<CurrentRegulatorItem> FIVEHUNDREDTWELVE_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("fivehundredtwelve_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 512, 4096)));
	public static final RegistryObject<CurrentRegulatorItem> FOURTHOUSANDNINTYSIX_CU_CURRENT_REGULATOR = generalTabEnqueue(ITEMS.register("fourthousandnintysix_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 4096,  32768)));

	// liquid latex that burns you if you're in the sun light
	public static final RegistryObject<BucketItem> BIO_BUCKET = generalTabEnqueue(ITEMS.register("bio_bucket", () -> new BucketItem(YATMFluids.BIO, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> CHORUS_BUCKET = generalTabEnqueue(ITEMS.register("chorus_bucket", () -> new BucketItem(YATMFluids.CHORUS, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> CHORUS_BIO_BUCKET = generalTabEnqueue(ITEMS.register("chorus_bio_bucket", () -> new BucketItem(YATMFluids.CHORUS_BIO, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> ENDER_BUCKET = generalTabEnqueue(ITEMS.register("ender_bucket", () -> new BucketItem(YATMFluids.ENDER, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> ESSENCE_OF_DECAY_BUCKET = generalTabEnqueue(ITEMS.register("essence_of_decay_bucket", () -> new BucketItem(YATMFluids.ESSENCE_OF_DECAY, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> ESSENCE_OF_SOULS_BUCKET = generalTabEnqueue(ITEMS.register("essence_of_souls_bucket", () -> new BucketItem(YATMFluids.ESSENCE_OF_SOULS, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> LATEX_BUCKET = generalTabEnqueue(ITEMS.register("latex_bucket", () -> new BucketItem(YATMFluids.LATEX, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<SoulSapBucketItem> SOUL_SAP_BUCKET = generalTabEnqueue(ITEMS.register("soul_sap_bucket", () -> new SoulSapBucketItem(YATMFluids.SOUL_SAP, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	public static final RegistryObject<BucketItem> SOUL_SYRUP_BUCKET = generalTabEnqueue(ITEMS.register("soul_syrup_bucket", () -> new BucketItem(YATMFluids.SOUL_SYRUP, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
	
	public static final RegistryObject<DrinkableFluidBottleItem> BIO_BOTTLE = generalTabEnqueue(ITEMS.register("bio_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.BIOFLUID), YATMFluids.BIO, 20)));
	public static final RegistryObject<DrinkableFluidBottleItem> CHORUS_BOTTLE = generalTabEnqueue(ITEMS.register("chorus_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.CHORUS), YATMFluids.CHORUS, 20)));
	public static final RegistryObject<DrinkableFluidBottleItem> CHORUS_BIO_BOTTLE = generalTabEnqueue(ITEMS.register("chorus_bio_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.CHORUS_BIOFLUID), YATMFluids.CHORUS_BIO, 20)));
	public static final RegistryObject<DrinkableFluidBottleItem> ENDER_BOTTLE = generalTabEnqueue(ITEMS.register("ender_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.ENDER), YATMFluids.ENDER, 20)));
	public static final RegistryObject<DrinkableFluidBottleItem> ESSENCE_OF_DECAY_BOTTLE = generalTabEnqueue(ITEMS.register("essence_of_decay_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.ESSENCE_OF_DECAY), YATMFluids.ESSENCE_OF_DECAY, 20)));
	public static final RegistryObject<DrinkableFluidBottleItem> ESSENCE_OF_SOULS_BOTTLE = generalTabEnqueue(ITEMS.register("essence_of_souls_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.ESSENCE_OF_SOULS), YATMFluids.ESSENCE_OF_SOULS, 20)));
	public static final RegistryObject<FluidBottleItem> LATEX_BOTTLE = generalTabEnqueue(ITEMS.register("latex_bottle", () -> new FluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), YATMFluids.LATEX)));
	public static final RegistryObject<DrinkableFluidBottleItem> SOUL_SAP_BOTTLE = generalTabEnqueue(ITEMS.register("soul_sap_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.SOUL_SAP), YATMFluids.SOUL_SAP, 40)));
	public static final RegistryObject<DrinkableFluidBottleItem> SOUL_SYRUP_BOTTLE = generalTabEnqueue(ITEMS.register("soul_syrup_bottle", () -> new DrinkableFluidBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(YATMFoods.SOUL_SYRUP), YATMFluids.SOUL_SYRUP, 60)));
	//Item.Properties()).craftRemainder(GLASS_BOTTLE).food(YATMFoods.).stacksTo(16)
	

	public static final RegistryObject<Item> IRON_WIRE_DIE = generalTabEnqueue(ITEMS.register("iron_wire_die", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STEEL_WIRE_DIE = generalTabEnqueue(ITEMS.register("steel_wire_die", () -> new Item(new Item.Properties())));
	
	
	
	public static final RegistryObject<Item> SILVER_INGOT = generalTabEnqueue(ITEMS.register("silver_ingot", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> STEEL_INGOT = generalTabEnqueue(ITEMS.register("steel_ingot", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> RUBBER_BAR = generalTabEnqueue(ITEMS.register("rubber_bar", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> RUBBER_SCRAP_BALL = generalTabEnqueue(ITEMS.register("rubber_scrap_ball", () -> new Item(new Item.Properties())));
	
	public static final RegistryObject<Item> SILVER_NUGGET = generalTabEnqueue(ITEMS.register("silver_nugget", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> COPPER_NUGGET = generalTabEnqueue(ITEMS.register("copper_nugget", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> NETHERITE_NUGGET = generalTabEnqueue(ITEMS.register("netherite_nugget", () -> new Item(new Item.Properties())));
	
	public static final RegistryObject<InsulatingItem> WAX_BIT_ITEM = generalTabEnqueue(ITEMS.register("wax_bit", () -> new InsulatingItem(new Item.Properties(), InsulatingItem.WAX_BIT_WAXING_TABLE)));
	public static final RegistryObject<InsulatingItem> RUBBER_SCRAP = generalTabEnqueue(ITEMS.register("rubber_scrap", () -> new InsulatingItem(new Item.Properties(), InsulatingItem.RUBBER_SCRAP_WAXING_TABLE)));
	
	public static final RegistryObject<Item> WOOD_PULP = generalTabEnqueue(ITEMS.register("wood_pulp", () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> LEAF_MULCH = generalTabEnqueue(ITEMS.register("leaf_mulch", () -> new Item(new Item.Properties())));
	
	public static final RegistryObject<CreativeFluidVoidItem> CREATIVE_FLUID_VOID = generalTabEnqueue(ITEMS.register("creative_fluid_void", () -> new CreativeFluidVoidItem(new Item.Properties().stacksTo(1))));
	public static final RegistryObject<CreativeFluidStorerItem> CREATIVE_FLUID_STORER = generalTabEnqueue(ITEMS.register("creative_fluid_storer", () -> new CreativeFluidStorerItem(new Item.Properties().stacksTo(1))));
	public static final RegistryObject<CreativeFluidSourceItem> CREATIVE_FLUID_SOURCE = generalTabEnqueue(ITEMS.register("creative_fluid_source", () -> new CreativeFluidSourceItem(new Item.Properties().stacksTo(1))));
	
	
	
//	public static <T extends Item> List<RegistryObject<T>> alternativeSortingMode()
//	{
//		ArrayList<RegistryObject<T>> inv = new ArrayList<>();
//		
//		inv.add(RUBBER_MERISTEM_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_meristem", () -> new BlockItem(YATMBlocks.RUBBER_MERISTEM.get(), new Item.Properties())));
//		inv.add(RUBBER_LEAVES_YOUNG_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_leaves_young", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_YOUNG.get(), new Item.Properties())));
//		inv.add(RUBBER_LEAVES_LEAVES_FLOWERING_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_leaves_flowering", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_FLOWERING.get(), new Item.Properties())));
//		inv.add(RUBBER_LEAVES_OLD_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_leaves_old", () -> new BlockItem(YATMBlocks.RUBBER_LEAVES_OLD.get(), new Item.Properties())));
//		inv.add(RUBBER_LOG_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_log", () -> new BlockItem(YATMBlocks.RUBBER_LOG.get(), new Item.Properties())));
//		inv.add(RUBBER_WOOD_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_wood", () -> new BlockItem(YATMBlocks.RUBBER_WOOD.get(), new Item.Properties())));
//		inv.add(STRIPPED_RUBBER_LOG_ITEM); // = generalTabEnqueue(ITEMS.register("stripped_rubber_log", () -> new BlockItem(YATMBlocks.STRIPPED_RUBBER_LOG.get(), new Item.Properties())));
//		inv.add(PARTIALLY_STRIPPED_RUBBER_LOG_ITEM); // = generalTabEnqueue(ITEMS.register("partially_stripped_rubber_log", () -> new BlockItem(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get(), new Item.Properties())));
//		inv.add(STRIPPED_RUBBER_WOOD_ITEM); // = generalTabEnqueue(ITEMS.register("stripped_rubber_wood", () -> new BlockItem(YATMBlocks.STRIPPED_RUBBER_WOOD.get(), new Item.Properties())));
//		inv.add(RUBBER_PLANKS_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_planks", () -> new BlockItem(YATMBlocks.RUBBER_PLANKS.get(), new Item.Properties())));
//		inv.add(FANCY_RUBBER_PLANKS_ITEM); // = generalTabEnqueue(ITEMS.register("fancy_rubber_planks", () -> new BlockItem(YATMBlocks.FANCY_RUBBER_PLANKS.get(), new Item.Properties())));
//		inv.add(RUBBER_STAIRS_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_stairs", () -> new BlockItem(YATMBlocks.RUBBER_STAIRS.get(), new Item.Properties())));
//		inv.add(RUBBER_SLAB_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_slab", () -> new BlockItem(YATMBlocks.RUBBER_SLAB.get(), new Item.Properties())));
//		inv.add(RUBBER_FENCE_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_fence", () -> new BlockItem(YATMBlocks.RUBBER_FENCE.get(), new Item.Properties())));
//		inv.add(RUBBER_FENCE_GATE_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_fence_gate", () -> new BlockItem(YATMBlocks.RUBBER_FENCE_GATE.get(), new Item.Properties())));
//		inv.add(RUBBER_DOOR_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_door", () -> new BlockItem(YATMBlocks.RUBBER_DOOR.get(), new Item.Properties())));
//		inv.add(RUBBER_TRAPDOOR_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_trapdoor", () -> new BlockItem(YATMBlocks.RUBBER_TRAPDOOR.get(), new Item.Properties())));
//		inv.add(RUBBER_PRESSURE_PLATE_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_pressure_plate", () -> new BlockItem(YATMBlocks.RUBBER_PRESSURE_PLATE.get(), new Item.Properties())));
//		inv.add(RUBBER_BUTTON_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_button", () -> new BlockItem(YATMBlocks.RUBBER_BUTTON.get(), new Item.Properties())));
//		
//		inv.add(LATEX_BUCKET); //				
//		inv.add(RUBBER_BLOCK_ITEM); // = generalTabEnqueue(ITEMS.register("rubber_block", () -> new BlockItem(YATMBlocks.RUBBER_BLOCK.get(), new Item.Properties())));
//		inv.add(RUBBER_BAR); //
//		inv.add(RUBBER_SCRAP_BALL); //
//		inv.add(RUBBER_SCRAP); //
//		
//		inv.add(SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_meristem", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_leaves_young", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_LEAVES_LEAVES_FLOWERING_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_leaves_flowering", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_LEAVES_OLD_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_leaves_old", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_LOG_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_log", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_WOOD_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_wood", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_STRIPPED_RUBBER_LOG_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_stripped_rubber_log", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_partially_stripped_rubber_log", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_stripped_rubber_wood", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_PLANKS_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_planks", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_PLANKS.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_fancy_rubber_planks", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_fancy_rubber_planks_tiled", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_STAIRS_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_stairs", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_SLAB_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_slab", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_FENCE_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_fence", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_FENCE_GATE_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_fence_gate", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE_GATE.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_DOOR_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_door", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_TRAPDOOR_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_trapdoor", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_pressure_plate", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get(), new Item.Properties())));
//		inv.add(SOUL_AFFLICTED_RUBBER_BUTTON_ITEM); // = generalTabEnqueue(ITEMS.register("soul_afflicted_rubber_button", () -> new BlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_BUTTON.get(), new Item.Properties())));
//		
//		inv.add(SOUL_SAP_BUCKET); //
//		inv.add(SOUL_SAP_BOTTLE); //
//
//		inv.add(ESSENCE_OF_DECAY_BUCKET); //
//		inv.add(ESSENCE_OF_DECAY_BOTTLE); //
//		
//		
//		inv.add(LARGE_COPPER_HEAT_SINK_ITEM); // = generalTabEnqueue(ITEMS.register("large_copper_heat_sink", () -> new BlockItem(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), new Item.Properties())));
//
//		inv.add(STEEL_BOILER_TANK_ITEM); // = generalTabEnqueue(ITEMS.register("steel_boiler_tank", () -> new BlockItem(YATMBlocks.STEEL_BOILER_TANK.get(), new Item.Properties())));
//		inv.add(STEEL_BOILER_ITEM); // = generalTabEnqueue(ITEMS.register("steel_boiler", () -> new BlockItem(YATMBlocks.STEEL_BOILER.get(), new Item.Properties())));
//		inv.add(STEEL_EXTRACTOR_ITEM); // = generalTabEnqueue(ITEMS.register("steel_extractor", () -> new BlockItem(YATMBlocks.STEEL_EXTRACTOR.get(), new Item.Properties())));
//		inv.add(STEEL_EXTRUDER_ITEM); // = generalTabEnqueue(ITEMS.register("steel_extruder", () -> new BlockItem(YATMBlocks.STEEL_EXTRUDER.get(), new Item.Properties())));
//		inv.add(STEEL_FLUID_CONDUIT_ITEM); // = generalTabEnqueue(ITEMS.register("steel_fluid_conduit", () -> new BlockItem(YATMBlocks.STEEL_FLUID_CONDUIT.get(), new Item.Properties())));
//		inv.add(STEEL_FLUID_EXCHANGER); // = generalTabEnqueue(ITEMS.register("steel_fluid_exchanger_component", () -> new FluidExchangerItem(new Item.Properties().stacksTo(1), 128)));
//		inv.add(STEEL_WIRE_DIE);
//		inv.add(STEEL_INGOT); // = generalTabEnqueue(ITEMS.register("steel_ingot", () -> new Item(new Item.Properties())));
//		
//		inv.add(ONE_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("one_cu_wire", () -> new BlockItem(YATMBlocks.ONE_CU_WIRE.get(), new Item.Properties())));
//		inv.add(ENAMELED_ONE_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("enameled_one_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_ONE_CU_WIRE.get(), new Item.Properties())));
//		inv.add(INSULATED_ONE_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("insulated_one_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_ONE_CU_WIRE.get(), new Item.Properties())));
//		inv.add(ONE_CU_CURRENT_REGULATOR); // = generalTabEnqueue(ITEMS.register("one_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 1, 8)));
//		
//		inv.add(EIGHT_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("eight_cu_wire", () -> new BlockItem(YATMBlocks.EIGHT_CU_WIRE.get(), new Item.Properties())));
//		inv.add(ENAMELED_EIGHT_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("enameled_eight_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get(), new Item.Properties())));
//		inv.add(INSULATED_EIGHT_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("insulated_eight_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_EIGHT_CU_WIRE.get(), new Item.Properties())));
//		inv.add(EIGHT_CU_CURRENT_REGULATOR); // = generalTabEnqueue(ITEMS.register("eight_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 8, 64)));
//		
//		inv.add(SIXTYFOUR_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("sixtyfour_cu_wire", () -> new BlockItem(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), new Item.Properties())));
//		inv.add(ENAMELED_SIXTYFOUR_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("enameled_sixtyfour_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get(), new Item.Properties())));
//		inv.add(INSULATED_SIXTYFOUR_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("insulated_sixtyfour_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get(), new Item.Properties())));
//		inv.add(SIXTYFOUR_CU_CURRENT_REGULATOR); // = generalTabEnqueue(ITEMS.register("sixtyfour_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 64, 512)));
//		
//		inv.add(FIVEHUNDREDTWELVE_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("fivehundredtwelve_cu_wire", () -> new BlockItem(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), new Item.Properties())));
//		inv.add(ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("enameled_fivehundredtwelve_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get(), new Item.Properties())));
//		inv.add(INSULATED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("insulated_fivehundredtwelve_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get(), new Item.Properties())));
//		inv.add(FIVEHUNDREDTWELVE_CU_CURRENT_REGULATOR); // = generalTabEnqueue(ITEMS.register("fivehundredtwelve_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 512, 4096)));
//		
//		inv.add(FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("fourthousandnintysix_cu_wire", () -> new BlockItem(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), new Item.Properties())));
//		inv.add(ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("enameled_fourthousandnintysix_cu_wire", () -> new BlockItem(YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), new Item.Properties())));
//		inv.add(INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM); // = generalTabEnqueue(ITEMS.register("insulated_fourthousandnintysix_cu_wire", () -> new BlockItem(YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), new Item.Properties())));
//		inv.add(FOURTHOUSANDNINTYSIX_CU_CURRENT_REGULATOR); // = generalTabEnqueue(ITEMS.register("fourthousandnintysix_cu_current_regulator_component", () -> new CurrentRegulatorItem(new Item.Properties().stacksTo(1), 4096,  32768)));
//
//		inv.add(IRON_WIRE_DIE); // = generalTabEnqueue(ITEMS.register("iron_wire_die", () -> new Item(new Item.Properties())));
//		
//		
//		
//		inv.add(SILVER_INGOT); // = generalTabEnqueue(ITEMS.register("silver_ingot", () -> new Item(new Item.Properties())));
//		inv.add(SILVER_NUGGET); // = generalTabEnqueue(ITEMS.register("silver_nugget", () -> new Item(new Item.Properties())));
//		
//		inv.add(COPPER_NUGGET); // = generalTabEnqueue(ITEMS.register("copper_nugget", () -> new Item(new Item.Properties())));
//		inv.add(NETHERITE_NUGGET); // = generalTabEnqueue(ITEMS.register("netherite_nugget", () -> new Item(new Item.Properties())));
//		
//		inv.add(WAX_BIT_ITEM); // = generalTabEnqueue(ITEMS.register("wax_bit", () -> new InsulatingItem(new Item.Properties(), InsulatingItem.WAX_BIT_WAXING_TABLE)));
//		
//		inv.add(WOOD_PULP); // = generalTabEnqueue(ITEMS.register("wood_pulp", () -> new Item(new Item.Properties())));
//		inv.add(LEAF_MULCH); // = generalTabEnqueue(ITEMS.register("leaf_mulch", () -> new Item(new Item.Properties())));
//		
//		
//		inv.add(C_U_F_E_I_ITEM); // = generalTabEnqueue(ITEMS.register("current_unit_forge_energy_interchanger", () -> new BlockItem(YATMBlocks.C_U_F_E_I.get(), new Item.Properties())));
//
//		
//		inv.add(CREATIVE_FLUID_VOID); // = generalTabEnqueue(ITEMS.register("creative_fluid_void", () -> new CreativeFluidVoidItem(new Item.Properties())));
//		inv.add(CREATIVE_FLUID_STORER); // = generalTabEnqueue(ITEMS.register("creative_fluid_storer", () -> new CreativeFluidStorerItem(new Item.Properties())));
//		inv.add(CREATIVE_FLUID_SOURCE); // = generalTabEnqueue(ITEMS.register("creative_fluid_source", () -> new CreativeFluidSourceItem(new Item.Properties())));
//		
//		return inv;
//	} // end alternativeSortingMode()
	
	
	public static void addCompostables() 
	{	
		ComposterBlock.COMPOSTABLES.put(YATMItems.RUBBER_MERISTEM_ITEM.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.RUBBER_LEAVES_YOUNG_ITEM.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(YATMItems.RUBBER_LEAVES_FLOWERING_ITEM.get(), 0.3F);
		
		// TODO, possibly add thses magically ones, here or elsewhere
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
		return YATMCreativeModTabs.queueForGeneralCreativeTab(item);
	} // end generalTabEnqueue()
	
} // end class
