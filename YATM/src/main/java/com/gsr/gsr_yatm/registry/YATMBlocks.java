package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.conduit.ConductorProperties;
import com.gsr.gsr_yatm.block.conduit.CurrentConduitBlock;
import com.gsr.gsr_yatm.block.conduit.FluidConduitBlock;
import com.gsr.gsr_yatm.block.conduit.InsulatedCurrentConduitBlock;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
import com.gsr.gsr_yatm.block.device.bioler.BiolerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerTankBlock;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerBlock;
import com.gsr.gsr_yatm.block.device.current_furnace.FurnacePlusBlock;
import com.gsr.gsr_yatm.block.device.energy_converter.CurrentUnitForgeEnergyInterchangerBlock;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorBlock;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderBlock;
import com.gsr.gsr_yatm.block.device.grinder.GrinderBlock;
import com.gsr.gsr_yatm.block.device.heat_sink.HeatSinkBlock;
import com.gsr.gsr_yatm.block.device.solar.BatterySolarPanelBlock;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelBlock;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelSettings;
import com.gsr.gsr_yatm.block.device.spinning_wheel.SpinningWheelBlock;
import com.gsr.gsr_yatm.block.plant.fungi.PhantasmalShelfFungiBlock;
import com.gsr.gsr_yatm.block.plant.moss.PrismarineCrystalMossBlock;
import com.gsr.gsr_yatm.block.plant.tree.AerialRootsBlock;
import com.gsr.gsr_yatm.block.plant.tree.StrippedSapLogBlock;
import com.gsr.gsr_yatm.block.plant.tree.rubber_bush.RubberBushSaplingBlock;
import com.gsr.gsr_yatm.block.plant.tree.soul_afflicted_rubber_bush.SoulAfflictedRubberBushSaplingBlock;
import com.gsr.gsr_yatm.block.plant.vine.OnceFruitVineBodyBlock;
import com.gsr.gsr_yatm.block.plant.vine.VineMeristemBlock;
import com.gsr.gsr_yatm.utilities.YATMBlockShapes;
import com.gsr.gsr_yatm.utilities.YATMBlockProperties;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMBlocks
{
	private static final RandomSource RANDOM = RandomSource.create();
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, YetAnotherTechMod.MODID);
	
	
	
	// TODO, maybe, add unique rooted dirt block and rooted soul sand.
	// TODO, consider by some mean letting roots root into cracked blocks, and or break them further, or to break crackable blocks
	// TODO, make rubber tree decorate occasionally with bees.
	// TODO, design lateral growth, add lateral meristems to apically grown tree, maybe	
	
	
	
	private static final BlockSetType RUBBER_BLOCK_SET_TYPE = new BlockSetType("rubber");
	
	
	public static final RegistryObject<RubberBushSaplingBlock> RUBBER_MERISTEM = BLOCKS.register("rubber_meristem", () -> new RubberBushSaplingBlock(YATMBlockProperties.SAPLING_PROPERTIES));	
	public static final RegistryObject<FlowerPotBlock> POTTED_RUBBER_MERISTEM = BLOCKS.register("potted_rubber_meristem", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.RUBBER_MERISTEM.get(), YATMBlockProperties.FLOWER_POT_PROPERTIES));
	public static final RegistryObject<LeavesBlock> RUBBER_LEAVES_YOUNG = BLOCKS.register("rubber_leaves_young", () ->  new LeavesBlock(YATMBlockProperties.LEAVES_PROPERTIES));
	public static final RegistryObject<LeavesBlock> RUBBER_LEAVES_FLOWERING = BLOCKS.register("rubber_leaves_flowering", () ->  new LeavesBlock(YATMBlockProperties.LEAVES_PROPERTIES));
	// TODO, maybe make persistent?
	public static final RegistryObject<LeavesBlock> RUBBER_LEAVES_OLD = BLOCKS.register("rubber_leaves_old", () ->  new LeavesBlock(YATMBlockProperties.LEAVES_PROPERTIES));
	public static final RegistryObject<AerialRootsBlock> RUBBER_ROOTS = BLOCKS.register("rubber_roots", () ->  new AerialRootsBlock(YATMBlockProperties.AERIAL_ROOTS_PROPERTIES));
	public static final RegistryObject<RotatedPillarBlock> RUBBER_LOG = BLOCKS.register("rubber_log", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<RotatedPillarBlock> RUBBER_WOOD = BLOCKS.register("rubber_wood", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<StrippedSapLogBlock> PARTIALLY_STRIPPED_RUBBER_LOG = BLOCKS.register("partially_stripped_rubber_log", () -> new StrippedSapLogBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES /*TODO, add random ticks*/));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_RUBBER_LOG = BLOCKS.register("stripped_rubber_log", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_RUBBER_WOOD = BLOCKS.register("stripped_rubber_wood", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<Block> RUBBER_PLANKS = BLOCKS.register("rubber_planks", () -> new Block(YATMBlockProperties.RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<RotatedPillarBlock> FANCY_RUBBER_PLANKS = BLOCKS.register("fancy_rubber_planks", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<StairBlock> RUBBER_STAIRS = BLOCKS.register("rubber_stairs", () -> new StairBlock(() -> RUBBER_PLANKS.get().defaultBlockState(), YATMBlockProperties.RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<SlabBlock> RUBBER_SLAB = BLOCKS.register("rubber_slab", () -> new SlabBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<FenceBlock> RUBBER_FENCE = BLOCKS.register("rubber_fence", () -> new FenceBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<FenceGateBlock> RUBBER_FENCE_GATE = BLOCKS.register("rubber_fence_gate", () -> new FenceGateBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES, SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
	public static final RegistryObject<DoorBlock> RUBBER_DOOR = BLOCKS.register("rubber_door", () -> new DoorBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES, RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<TrapDoorBlock> RUBBER_TRAPDOOR = BLOCKS.register("rubber_trapdoor", () -> new TrapDoorBlock(YATMBlockProperties.RUBBER_WOOD_PROPERTIES, RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<PressurePlateBlock> RUBBER_PRESSURE_PLATE = BLOCKS.register("rubber_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, YATMBlockProperties.RUBBER_WOOD_SWITCH_PROPERTIES, RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<ButtonBlock> RUBBER_BUTTON = BLOCKS.register("rubber_button", () -> new ButtonBlock(YATMBlockProperties.RUBBER_WOOD_SWITCH_PROPERTIES, RUBBER_BLOCK_SET_TYPE, 30, true));
	public static final RegistryObject<CarpetBlock> LEAF_MULCH = BLOCKS.register("leaf_mulch", () -> new CarpetBlock(YATMBlockProperties.LEAF_MULCH_PROPERTIES));

	// sign StandingSignBlock WallSignBlock CeilingHangingSignBlock WallHangingSignBlock
	// boat
	// boat with chest
	
	public static final RegistryObject<SoulAfflictedRubberBushSaplingBlock> SOUL_AFFLICTED_RUBBER_MERISTEM = BLOCKS.register("soul_afflicted_rubber_meristem", () -> new SoulAfflictedRubberBushSaplingBlock(YATMBlockProperties.SAPLING_PROPERTIES));
	public static final RegistryObject<FlowerPotBlock> POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM = BLOCKS.register("potted_soul_afflicted_rubber_meristem", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), YATMBlockProperties.FLOWER_POT_PROPERTIES));
	public static final RegistryObject<LeavesBlock> SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG = BLOCKS.register("soul_afflicted_rubber_leaves_young", () ->  new LeavesBlock(YATMBlockProperties.LEAVES_PROPERTIES));
	public static final RegistryObject<LeavesBlock> SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING = BLOCKS.register("soul_afflicted_rubber_leaves_flowering", () ->  new LeavesBlock(YATMBlockProperties.LEAVES_PROPERTIES));
	public static final RegistryObject<LeavesBlock> SOUL_AFFLICTED_RUBBER_LEAVES_OLD = BLOCKS.register("soul_afflicted_rubber_leaves_old", () ->  new LeavesBlock(YATMBlockProperties.LEAVES_PROPERTIES));
	public static final RegistryObject<AerialRootsBlock> SOUL_AFFLICTED_RUBBER_ROOTS = BLOCKS.register("soul_afflicted_rubber_roots", () ->  new AerialRootsBlock(YATMBlockProperties.AERIAL_ROOTS_PROPERTIES));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_RUBBER_LOG = BLOCKS.register("soul_afflicted_rubber_log", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_RUBBER_WOOD = BLOCKS.register("soul_afflicted_rubber_wood", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<StrippedSapLogBlock> SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG = BLOCKS.register("soul_afflicted_partially_stripped_rubber_log", () -> new StrippedSapLogBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES /*TODO, .randomTicks()*/));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_STRIPPED_RUBBER_LOG = BLOCKS.register("soul_afflicted_stripped_rubber_log", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD = BLOCKS.register("soul_afflicted_stripped_rubber_wood", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_PLANKS = BLOCKS.register("soul_afflicted_rubber_planks", () -> new Block(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS = BLOCKS.register("soul_afflicted_fancy_rubber_planks", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED = BLOCKS.register("soul_afflicted_fancy_rubber_planks_tiled", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<StairBlock> SOUL_AFFLICTED_RUBBER_STAIRS = BLOCKS.register("soul_afflicted_rubber_stairs", () -> new StairBlock(() -> SOUL_AFFLICTED_RUBBER_PLANKS.get().defaultBlockState(), YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<SlabBlock> SOUL_AFFLICTED_RUBBER_SLAB = BLOCKS.register("soul_afflicted_rubber_slab", () -> new SlabBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<FenceBlock> SOUL_AFFLICTED_RUBBER_FENCE = BLOCKS.register("soul_afflicted_rubber_fence", () -> new FenceBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES));
	public static final RegistryObject<FenceGateBlock> SOUL_AFFLICTED_RUBBER_FENCE_GATE = BLOCKS.register("soul_afflicted_rubber_fence_gate", () -> new FenceGateBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES, SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
	public static final RegistryObject<DoorBlock> SOUL_AFFLICTED_RUBBER_DOOR = BLOCKS.register("soul_afflicted_rubber_door", () -> new DoorBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES, RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<TrapDoorBlock> SOUL_AFFLICTED_RUBBER_TRAPDOOR = BLOCKS.register("soul_afflicted_rubber_trapdoor", () -> new TrapDoorBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES, RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<PressurePlateBlock> SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE = BLOCKS.register("soul_afflicted_rubber_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_SWITCH_PROPERTIES, RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<ButtonBlock> SOUL_AFFLICTED_RUBBER_BUTTON = BLOCKS.register("soul_afflicted_rubber_button", () -> new ButtonBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_SWITCH_PROPERTIES, RUBBER_BLOCK_SET_TYPE, 30, true));
	public static final RegistryObject<CarpetBlock> SOUL_AFFLICTED_LEAF_MULCH = BLOCKS.register("soul_afflicted_leaf_mulch", () -> new CarpetBlock(YATMBlockProperties.LEAF_MULCH_PROPERTIES));

	public static final RegistryObject<PhantasmalShelfFungiBlock> PHANTASMAL_SHELF_FUNGUS = BLOCKS.register("phantasmal_shelf_fungus", () -> new PhantasmalShelfFungiBlock(YATMBlockProperties.PHANTASMAL_SHELF_FUNGUS_PROPERTIES));
	
	// TODO, perhaps adjust hitbox to match stages closer
	public static final RegistryObject<CropBlock> COTTON = BLOCKS.register("cotton", () -> new CropBlock(YATMBlockProperties.CROP));
	
	public static final RegistryObject<PrismarineCrystalMossBlock> PRISMARINE_CRYSTAL_MOSS = BLOCKS.register("prismarine_crystal_moss", () -> new PrismarineCrystalMossBlock(YATMBlockProperties.PRISMARINE_CRYSTAL_MOSS, YATMBlockShapes.PRISMARINE_CRYSTAL_MOSS));
	
	public static final RegistryObject<OnceFruitVineBodyBlock> SPIDER_VINE = BLOCKS.register("spider_vine", () -> new OnceFruitVineBodyBlock(YATMBlockProperties.SPIDER_VINE, YATMBlocks::getSpiderVineMeristem, () -> new ItemStack(YATMItems.SPIDER_VINE_FRUITS.get(), RANDOM.nextIntBetweenInclusive(1, 3))));
	public static final RegistryObject<VineMeristemBlock> SPIDER_VINE_MERISTEM = BLOCKS.register("spider_vine_meristem", () -> new VineMeristemBlock(YATMBlockProperties.SPIDER_VINE, YATMBlocks::getSpiderVine));
	
	private static final OnceFruitVineBodyBlock getSpiderVine()
	{
		return YATMBlocks.SPIDER_VINE.get();
	} // end getSpiderVine()
	private static final VineMeristemBlock getSpiderVineMeristem()
	{
		return YATMBlocks.SPIDER_VINE_MERISTEM.get();
	} // end getSpiderVine()
	
	//TODO, maybe add some sort of soul stone 
	
	public static final RegistryObject<Block> RUBBER_BLOCK = BLOCKS.register("rubber_block", () -> new Block(YATMBlockProperties.RUBBER_BLOCK_PROPERTIES));
	public static final RegistryObject<RootedDirtBlock> ROOTED_SOUL_SOIL = BLOCKS.register("rooted_soul_soil", () -> new RootedDirtBlock(YATMBlockProperties.ROOTED_SOUL_SOIL_PROPERTIES));
		
	// TODO, make up properties
	public static final RegistryObject<SpinningWheelBlock> SPINNING_WHEEL = BLOCKS.register("spinning_wheel", () -> new SpinningWheelBlock(BlockBehaviour.Properties.of(), YATMBlockShapes.SPINNING_WHEEL));
	
	public static final RegistryObject<HeatSinkBlock> LARGE_COPPER_HEAT_SINK = BLOCKS.register("large_copper_heat_sink", () -> new HeatSinkBlock(BlockBehaviour.Properties.of()));
	
	public static final RegistryObject<BiolerBlock> STEEL_BIOLER = BLOCKS.register("steel_bioler", () -> new BiolerBlock(YATMBlockProperties.STEEL_MACHINE_PROPERTIES, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_CURRENT_CAPACITY, DeviceTierConstants.STEEL_MAX_CURRENT, DeviceTierConstants.STEEL_TANK_CAPACITY, DeviceTierConstants.STEEL_MAXIMUM_FLUID_TRANSFER_RATE));
	public static final RegistryObject<BoilerBlock> STEEL_BOILER = BLOCKS.register("steel_boiler", () -> new BoilerBlock(YATMBlockProperties.STEEL_MACHINE_PROPERTIES, YATMBlockShapes.BOILER_SHAPE, DeviceTierConstants.STEEL_MAXIMUM_TEMPERATURE, DeviceTierConstants.STEEL_TANK_CAPACITY, DeviceTierConstants.STEEL_MAXIMUM_FLUID_TRANSFER_RATE));
	public static final RegistryObject<BoilerTankBlock> STEEL_BOILER_TANK = BLOCKS.register("steel_boiler_tank", () -> new BoilerTankBlock(YATMBlockProperties.STEEL_MACHINE_PROPERTIES, 256, 32000));
	public static final RegistryObject<CrystallizerBlock> STEEL_CRYSTALLIZER = BLOCKS.register("steel_crystallizer", () -> new CrystallizerBlock(YATMBlockProperties.STEEL_MACHINE_PROPERTIES, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_TANK_CAPACITY, DeviceTierConstants.STEEL_MAXIMUM_FLUID_TRANSFER_RATE));
	public static final RegistryObject<FurnacePlusBlock> STEEL_FURNACE_PLUS = BLOCKS.register("steel_furnace_plus", () -> new FurnacePlusBlock(YATMBlockProperties.STEEL_MACHINE_PROPERTIES, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_DEVICE));
	public static final RegistryObject<ExtractorBlock> STEEL_EXTRACTOR = BLOCKS.register("steel_extractor", () -> new ExtractorBlock(YATMBlockProperties.STEEL_MACHINE_PROPERTIES, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_CURRENT_CAPACITY, DeviceTierConstants.STEEL_MAX_CURRENT, DeviceTierConstants.STEEL_TANK_CAPACITY, DeviceTierConstants.STEEL_MAXIMUM_FLUID_TRANSFER_RATE));
	public static final RegistryObject<ExtruderBlock> STEEL_EXTRUDER = BLOCKS.register("steel_extruder", () -> new ExtruderBlock(YATMBlockProperties.STEEL_MACHINE_PROPERTIES, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_CURRENT_CAPACITY, DeviceTierConstants.STEEL_MAX_CURRENT));
	public static final RegistryObject<GrinderBlock> STEEL_GRINDER = BLOCKS.register("steel_grinder", () -> new GrinderBlock(YATMBlockProperties.STEEL_MACHINE_PROPERTIES, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_CURRENT_CAPACITY, DeviceTierConstants.STEEL_MAX_CURRENT));
	
	public static final RegistryObject<CurrentUnitForgeEnergyInterchangerBlock> C_U_F_E_I = BLOCKS.register("current_unit_forge_energy_interchanger", () -> new CurrentUnitForgeEnergyInterchangerBlock(BlockBehaviour.Properties.of()));
	
	public static final RegistryObject<BatterySolarPanelBlock> CRUDE_BATTERY_SOLAR_PANEL = BLOCKS.register("crude_battery_solar_panel", () -> new BatterySolarPanelBlock(YATMBlockProperties.SOLAR_PANEL_PROPERTIES, YATMBlockShapes.CUBE, 1024, 8, 64, SolarPanelSettings.CRUDE));
	public static final RegistryObject<BatterySolarPanelBlock> ADVANCED_BATTERY_SOLAR_PANEL = BLOCKS.register("advanced_battery_solar_panel", () -> new BatterySolarPanelBlock(YATMBlockProperties.SOLAR_PANEL_PROPERTIES, YATMBlockShapes.CUBE, 65536, 64, 512, SolarPanelSettings.ADVANCED));
	public static final RegistryObject<BatterySolarPanelBlock> SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL = BLOCKS.register("suns_complement_battery_solar_panel", () -> new BatterySolarPanelBlock(YATMBlockProperties.SOLAR_PANEL_PROPERTIES, YATMBlockShapes.CUBE, 524288, 512, 4096, SolarPanelSettings.SUNS_COMPLEMENT));
	public static final RegistryObject<SolarPanelBlock> CRUDE_SOLAR_PANEL = BLOCKS.register("crude_solar_panel", () -> new SolarPanelBlock(YATMBlockProperties.SOLAR_PANEL_PROPERTIES, YATMBlockShapes.SOLAR_PANEL, 8, 8, 64, SolarPanelSettings.CRUDE));
	public static final RegistryObject<SolarPanelBlock> ADVANCED_SOLAR_PANEL = BLOCKS.register("advanced_solar_panel", () -> new SolarPanelBlock(YATMBlockProperties.SOLAR_PANEL_PROPERTIES, YATMBlockShapes.SOLAR_PANEL, 64, 64, 512, SolarPanelSettings.ADVANCED));
	public static final RegistryObject<SolarPanelBlock> SUNS_COMPLEMENT_SOLAR_PANEL = BLOCKS.register("suns_complement_solar_panel", () -> new SolarPanelBlock(YATMBlockProperties.SOLAR_PANEL_PROPERTIES, YATMBlockShapes.SOLAR_PANEL, 512, 512, 4096, SolarPanelSettings.SUNS_COMPLEMENT));
	
	
	
	
	public static final RegistryObject<CurrentConduitBlock> ONE_CU_WIRE = BLOCKS.register("one_cu_wire", () -> new CurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.ONE_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<CurrentConduitBlock> EIGHT_CU_WIRE = BLOCKS.register("eight_cu_wire", () -> new CurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.EIGHT_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<CurrentConduitBlock> SIXTYFOUR_CU_WIRE = BLOCKS.register("sixtyfour_cu_wire", () -> new CurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.SIXTYFOUR_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<CurrentConduitBlock> FIVEHUNDREDTWELVE_CU_WIRE = BLOCKS.register("fivehundredtwelve_cu_wire", () -> new CurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.FIVEHUNDREDTWELVE_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<CurrentConduitBlock> FOURTHOUSANDNINTYSIX_CU_WIRE = BLOCKS.register("fourthousandnintysix_cu_wire", () -> new CurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.FOURTHOUSANDNINTYSIX_CU_WIRE_CONDUCTOR_PROPERTIES));
	
	public static final RegistryObject<InsulatedCurrentConduitBlock> ENAMELED_ONE_CU_WIRE = BLOCKS.register("enameled_one_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.ONE_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<InsulatedCurrentConduitBlock> ENAMELED_EIGHT_CU_WIRE = BLOCKS.register("enameled_eight_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.EIGHT_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<InsulatedCurrentConduitBlock> ENAMELED_SIXTYFOUR_CU_WIRE = BLOCKS.register("enameled_sixtyfour_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.SIXTYFOUR_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<InsulatedCurrentConduitBlock> ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE = BLOCKS.register("enameled_fivehundredtwelve_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.FIVEHUNDREDTWELVE_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<InsulatedCurrentConduitBlock> ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE = BLOCKS.register("enameled_fourthousandnintysix_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.WIRE_SHAPE, ConductorProperties.FOURTHOUSANDNINTYSIX_CU_WIRE_CONDUCTOR_PROPERTIES));
	
	public static final RegistryObject<InsulatedCurrentConduitBlock> INSULATED_ONE_CU_WIRE = BLOCKS.register("insulated_one_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.ONE_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<InsulatedCurrentConduitBlock> INSULATED_EIGHT_CU_WIRE = BLOCKS.register("insulated_eight_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.EIGHT_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<InsulatedCurrentConduitBlock> INSULATED_SIXTYFOUR_CU_WIRE = BLOCKS.register("insulated_sixtyfour_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.SIXTYFOUR_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<InsulatedCurrentConduitBlock> INSULATED_FIVEHUNDREDTWELVE_CU_WIRE = BLOCKS.register("insulated_fivehundredtwelve_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.FIVEHUNDREDTWELVE_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<InsulatedCurrentConduitBlock> INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE = BLOCKS.register("insulated_fourthousandnintysix_cu_wire", () -> new InsulatedCurrentConduitBlock(YATMBlockProperties.WIRE_PROPERTIES, YATMBlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.FOURTHOUSANDNINTYSIX_CU_WIRE_CONDUCTOR_PROPERTIES));

	public static final RegistryObject<FluidConduitBlock> STEEL_FLUID_CONDUIT = BLOCKS.register("steel_fluid_conduit", () -> new FluidConduitBlock(YATMBlockProperties.STEEL_PIPE_PROPERTIES, YATMBlockShapes.STEEL_FLUID_CONDUIT_SHAPE));
	
	
	
	public static final RegistryObject<LiquidBlock> BIO_LIQUID_BLOCK = BLOCKS.register("bio", () -> new LiquidBlock(YATMFluids.BIO, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> CHORUS_LIQUID_BLOCK = BLOCKS.register("chorus", () -> new LiquidBlock(YATMFluids.CHORUS, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> CHORUS_BIO_LIQUID_BLOCK = BLOCKS.register("chorus_bio", () -> new LiquidBlock(YATMFluids.CHORUS_BIO, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> ENDER_LIQUID_BLOCK = BLOCKS.register("ender", () -> new LiquidBlock(YATMFluids.ENDER, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> ESSENCE_OF_DECAY_LIQUID_BLOCK = BLOCKS.register("essence_of_decay", () -> new LiquidBlock(YATMFluids.ESSENCE_OF_DECAY, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> ESSENCE_OF_SOULS_LIQUID_BLOCK = BLOCKS.register("essence_of_souls", () -> new LiquidBlock(YATMFluids.ESSENCE_OF_SOULS, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> LATEX_LIQUID_BLOCK = BLOCKS.register("latex", () -> new LiquidBlock(YATMFluids.LATEX, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> SOUL_SAP_LIQUID_BLOCK = BLOCKS.register("soul_sap", () -> new LiquidBlock(YATMFluids.SOUL_SAP, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> SOUL_SYRUP_LIQUID_BLOCK = BLOCKS.register("soul_syrup", () -> new LiquidBlock(YATMFluids.SOUL_SYRUP, YATMBlockProperties.LIQUID_PROPERTIES));

	public static final RegistryObject<LiquidBlock> CELESTIAL_LIGHT_LIQUID_BLOCK = BLOCKS.register("celestial_light", () -> new LiquidBlock(YATMFluids.CELESTIAL_LIGHT, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> LUNAR_LIGHT_LIQUID_BLOCK = BLOCKS.register("lunar_light", () -> new LiquidBlock(YATMFluids.LUNAR_LIGHT, YATMBlockProperties.LIQUID_PROPERTIES));
	public static final RegistryObject<LiquidBlock> SOLAR_LIGHT_LIQUID_BLOCK = BLOCKS.register("solar_light", () -> new LiquidBlock(YATMFluids.SOLAR_LIGHT, YATMBlockProperties.LIQUID_PROPERTIES));	

	
	
	public static void addFlowersToPots()
	{
		FlowerPotBlock minecraftFlowerPot = (FlowerPotBlock)Blocks.FLOWER_POT;
		minecraftFlowerPot.addPlant(YATMBlocks.RUBBER_MERISTEM.getKey().location(), YATMBlocks.POTTED_RUBBER_MERISTEM);
		minecraftFlowerPot.addPlant(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.getKey().location(), YATMBlocks.POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM);
	} // end addFlowersToPots()

} // end class