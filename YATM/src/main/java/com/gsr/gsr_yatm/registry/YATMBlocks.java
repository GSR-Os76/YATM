package com.gsr.gsr_yatm.registry;

import java.util.function.Supplier;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.DecayingBlock;
import com.gsr.gsr_yatm.block.NoCullBlock;
import com.gsr.gsr_yatm.block.WaterloggableBlock;
import com.gsr.gsr_yatm.block.candle_lantern.CandleLanternBlock;
import com.gsr.gsr_yatm.block.conduit.channel_vine.ChannelVineBlock;
import com.gsr.gsr_yatm.block.conduit.current.ConduitVineBlock;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
import com.gsr.gsr_yatm.block.device.bioler.BiolerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerTankBlock;
import com.gsr.gsr_yatm.block.device.compute.DataProcessorBlock;
import com.gsr.gsr_yatm.block.device.compute.DestructiveDataScannerBlock;
import com.gsr.gsr_yatm.block.device.compute.scan_collector.DataCollectorBlock;
import com.gsr.gsr_yatm.block.device.compute.storage.DataStorageBlock;
import com.gsr.gsr_yatm.block.device.creative.current_source.CreativeCurrentSourceBlock;
import com.gsr.gsr_yatm.block.device.crucible.CrucibleBlock;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerBlock;
import com.gsr.gsr_yatm.block.device.current_furnace.CurrentFurnaceBlock;
import com.gsr.gsr_yatm.block.device.energy_converter.CurrentUnitForgeEnergyInterchangerBlock;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorBlock;
import com.gsr.gsr_yatm.block.device.grafting.GraftingTableBlock;
import com.gsr.gsr_yatm.block.device.grinder.GrinderBlock;
import com.gsr.gsr_yatm.block.device.heat_sink.HeatSinkBlock;
import com.gsr.gsr_yatm.block.device.injector.InjectorBlock;
import com.gsr.gsr_yatm.block.device.sap_collector.FilledSapCollectorBlock;
import com.gsr.gsr_yatm.block.device.sap_collector.SapCollectorBlock;
import com.gsr.gsr_yatm.block.device.solar.BatterySolarPanelBlock;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelBlock;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelSettings;
import com.gsr.gsr_yatm.block.device.spinning_wheel.SpinningWheelBlock;
import com.gsr.gsr_yatm.block.device.tank.TankBlock;
import com.gsr.gsr_yatm.block.hanging_pot.HangingPotHookBlock;
import com.gsr.gsr_yatm.block.plant.CustomSeedCropBlock;
import com.gsr.gsr_yatm.block.plant.adamum.AdamumBlock;
import com.gsr.gsr_yatm.block.plant.aurum.AurumBlock;
import com.gsr.gsr_yatm.block.plant.basin_of_tears.BasinOfTearsFloralBlock;
import com.gsr.gsr_yatm.block.plant.basin_of_tears.BasinOfTearsVegetationBlock;
import com.gsr.gsr_yatm.block.plant.candlelily.CandlelilyBlock;
import com.gsr.gsr_yatm.block.plant.carbum.CarbumBlock;
import com.gsr.gsr_yatm.block.plant.carcass_root.CarcassRootFoliageBlock;
import com.gsr.gsr_yatm.block.plant.carcass_root.CarcassRootRootBlock;
import com.gsr.gsr_yatm.block.plant.carcass_root.CarcassRootRootSupplier;
import com.gsr.gsr_yatm.block.plant.cuprum.CuprumBlock;
import com.gsr.gsr_yatm.block.plant.ferrum.FerrumBlock;
import com.gsr.gsr_yatm.block.plant.fire_eater_lily.FireEaterLilyBlock;
import com.gsr.gsr_yatm.block.plant.fire_eater_lily.FireEaterLilyDecorativeBlock;
import com.gsr.gsr_yatm.block.plant.fire_eater_lily.FireEaterLilyUnlitDecorativeBlock;
import com.gsr.gsr_yatm.block.plant.folium.FoliumBlock;
import com.gsr.gsr_yatm.block.plant.fungi.PhantasmalShelfFungiBlock;
import com.gsr.gsr_yatm.block.plant.ice_coral.IceCoralBlock;
import com.gsr.gsr_yatm.block.plant.infernalum.InfernalumBlock;
import com.gsr.gsr_yatm.block.plant.lapum.LapumBlock;
import com.gsr.gsr_yatm.block.plant.parasite.ShulkwartBlock;
import com.gsr.gsr_yatm.block.plant.pitcher_cluster.PitcherClusterBlock;
import com.gsr.gsr_yatm.block.plant.prismarine_crystal_moss.PrismarineCrystalMossBlock;
import com.gsr.gsr_yatm.block.plant.ruberum.RuberumBlock;
import com.gsr.gsr_yatm.block.plant.samaragdum.SamaragdumBlock;
import com.gsr.gsr_yatm.block.plant.tree.AerialRootsBlock;
import com.gsr.gsr_yatm.block.plant.tree.TappedLogBlock;
import com.gsr.gsr_yatm.block.plant.tree.rubber_bush.RubberBushSaplingBlock;
import com.gsr.gsr_yatm.block.plant.tree.soul_afflicted_rubber_bush.SoulAfflictedRubberBushSaplingBlock;
import com.gsr.gsr_yatm.block.plant.variegated_cactus.VariegatedCactusBlock;
import com.gsr.gsr_yatm.block.plant.vicum.VicumBlock;
import com.gsr.gsr_yatm.block.plant.vine.OnceFruitVineBodyBlock;
import com.gsr.gsr_yatm.block.plant.vine.VineMeristemBlock;
import com.gsr.gsr_yatm.block.sign.YATMCeilingHangingSignBlock;
import com.gsr.gsr_yatm.block.sign.YATMStandingSignBlock;
import com.gsr.gsr_yatm.block.sign.YATMWallHangingSignBlock;
import com.gsr.gsr_yatm.block.sign.YATMWallSignBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.YATMBlockProperties;
import com.gsr.gsr_yatm.utilities.itemstack.RandomCountItemStackSupplier;
import com.gsr.gsr_yatm.utilities.shape.YATMBlockShapes;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
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
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
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
	// TODO, probably add in more potted plant things, or possibly less.
	
	
	private static final BlockSetType RUBBER_BLOCK_SET_TYPE = /* BlockSetType.register( */new BlockSetType("gsr_yatm:rubber")/* ) */;
	public static final WoodType RUBBER_WOOD_TYPE = new WoodType("gsr_yatm:rubber", YATMBlocks.RUBBER_BLOCK_SET_TYPE);

	private static final BlockSetType SOUL_AFFLICTED_RUBBER_BLOCK_SET_TYPE = /* BlockSetType.register( */new BlockSetType("gsr_yatm:soul_afflicted_rubber")/* ) */;
	public static final WoodType SOUL_AFFLICTED_RUBBER_WOOD_TYPE = new WoodType("gsr_yatm:soul_afflicted_rubber", YATMBlocks.SOUL_AFFLICTED_RUBBER_BLOCK_SET_TYPE);

	
	
	public static final RegistryObject<RotatedPillarBlock> RUBBER_LOG = BLOCKS.register("rubber_log", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD));
	public static final RegistryObject<RotatedPillarBlock> RUBBER_WOOD = BLOCKS.register("rubber_wood", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD));
	public static final RegistryObject<TappedLogBlock> PARTIALLY_STRIPPED_RUBBER_LOG = BLOCKS.register("partially_stripped_rubber_log", () -> new TappedLogBlock(YATMFluids.LATEX::get, (l, bs, p) -> bs.is(YATMBlockTags.RUBBER_TREE_BLOCKS_KEY), YATMBlockProperties.PARTIALLY_STRIPPED_RUBBER_WOOD, YATMParticleTypes.DRIPPING_TAPPED_LOG_LATEX::get));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_RUBBER_LOG = BLOCKS.register("stripped_rubber_log", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_RUBBER_WOOD = BLOCKS.register("stripped_rubber_wood", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD));
	public static final RegistryObject<Block> RUBBER_PLANKS = BLOCKS.register("rubber_planks", () -> new Block(YATMBlockProperties.RUBBER_WOOD));
	public static final RegistryObject<RotatedPillarBlock> FANCY_RUBBER_PLANKS = BLOCKS.register("fancy_rubber_planks", () -> new RotatedPillarBlock(YATMBlockProperties.RUBBER_WOOD));
	public static final RegistryObject<StairBlock> RUBBER_STAIRS = BLOCKS.register("rubber_stairs", () -> new StairBlock(() -> RUBBER_PLANKS.get().defaultBlockState(), YATMBlockProperties.RUBBER_WOOD));
	public static final RegistryObject<SlabBlock> RUBBER_SLAB = BLOCKS.register("rubber_slab", () -> new SlabBlock(YATMBlockProperties.RUBBER_WOOD));
	public static final RegistryObject<FenceBlock> RUBBER_FENCE = BLOCKS.register("rubber_fence", () -> new FenceBlock(YATMBlockProperties.RUBBER_WOOD));
	public static final RegistryObject<FenceGateBlock> RUBBER_FENCE_GATE = BLOCKS.register("rubber_fence_gate", () -> new FenceGateBlock(YATMBlockProperties.RUBBER_WOOD, SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
	public static final RegistryObject<DoorBlock> RUBBER_DOOR = BLOCKS.register("rubber_door", () -> new DoorBlock(YATMBlockProperties.RUBBER_WOOD, YATMBlocks.RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<TrapDoorBlock> RUBBER_TRAPDOOR = BLOCKS.register("rubber_trapdoor", () -> new TrapDoorBlock(YATMBlockProperties.RUBBER_WOOD, YATMBlocks.RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<PressurePlateBlock> RUBBER_PRESSURE_PLATE = BLOCKS.register("rubber_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, YATMBlockProperties.RUBBER_WOOD_SWITCH, YATMBlocks.RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<ButtonBlock> RUBBER_BUTTON = BLOCKS.register("rubber_button", () -> new ButtonBlock(YATMBlockProperties.RUBBER_WOOD_SWITCH, YATMBlocks.RUBBER_BLOCK_SET_TYPE, 30, true));
	public static final RegistryObject<YATMStandingSignBlock> RUBBER_SIGN = BLOCKS.register("rubber_sign", () -> new YATMStandingSignBlock(YATMBlockProperties.RUBBER_WOOD_SIGN, YATMBlocks.RUBBER_WOOD_TYPE));
	public static final RegistryObject<YATMWallSignBlock> RUBBER_WALL_SIGN = BLOCKS.register("rubber_wall_sign", () -> new YATMWallSignBlock(YATMBlockProperties.RUBBER_WOOD_SIGN, YATMBlocks.RUBBER_WOOD_TYPE));
	public static final RegistryObject<YATMCeilingHangingSignBlock> RUBBER_HANGING_SIGN = BLOCKS.register("rubber_hanging_sign", () -> new YATMCeilingHangingSignBlock(YATMBlockProperties.RUBBER_WOOD_SIGN, YATMBlocks.RUBBER_WOOD_TYPE));
	public static final RegistryObject<YATMWallHangingSignBlock> RUBBER_WALL_HANGING_SIGN = BLOCKS.register("rubber_wall_hanging_sign", () -> new YATMWallHangingSignBlock(YATMBlockProperties.RUBBER_WOOD_SIGN, YATMBlocks.RUBBER_WOOD_TYPE));
	public static final RegistryObject<CarpetBlock> LEAF_MULCH = BLOCKS.register("leaf_mulch", () -> new CarpetBlock(YATMBlockProperties.LEAF_MULCH));

	
	
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_RUBBER_LOG = BLOCKS.register("soul_afflicted_rubber_log", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_RUBBER_WOOD = BLOCKS.register("soul_afflicted_rubber_wood", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<TappedLogBlock> SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG = BLOCKS.register("soul_afflicted_partially_stripped_rubber_log", () -> new TappedLogBlock(YATMFluids.SOUL_SAP::get, (l, bs, p) -> bs.is(YATMBlockTags.SOUL_AFFLICTED_RUBBER_TREE_BLOCKS_KEY), YATMBlockProperties.PARTIALLY_STRIPPED_SOUL_AFFLICTED_RUBBER_WOOD, YATMParticleTypes.DRIPPING_TAPPED_LOG_SOUL_SAP::get));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_STRIPPED_RUBBER_LOG = BLOCKS.register("soul_afflicted_stripped_rubber_log", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD = BLOCKS.register("soul_afflicted_stripped_rubber_wood", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_PLANKS = BLOCKS.register("soul_afflicted_rubber_planks", () -> new Block(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS = BLOCKS.register("soul_afflicted_fancy_rubber_planks", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<RotatedPillarBlock> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED = BLOCKS.register("soul_afflicted_fancy_rubber_planks_tiled", () -> new RotatedPillarBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<StairBlock> SOUL_AFFLICTED_RUBBER_STAIRS = BLOCKS.register("soul_afflicted_rubber_stairs", () -> new StairBlock(() -> SOUL_AFFLICTED_RUBBER_PLANKS.get().defaultBlockState(), YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<SlabBlock> SOUL_AFFLICTED_RUBBER_SLAB = BLOCKS.register("soul_afflicted_rubber_slab", () -> new SlabBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<FenceBlock> SOUL_AFFLICTED_RUBBER_FENCE = BLOCKS.register("soul_afflicted_rubber_fence", () -> new FenceBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD));
	public static final RegistryObject<FenceGateBlock> SOUL_AFFLICTED_RUBBER_FENCE_GATE = BLOCKS.register("soul_afflicted_rubber_fence_gate", () -> new FenceGateBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD, SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
	public static final RegistryObject<DoorBlock> SOUL_AFFLICTED_RUBBER_DOOR = BLOCKS.register("soul_afflicted_rubber_door", () -> new DoorBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD, YATMBlocks.SOUL_AFFLICTED_RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<TrapDoorBlock> SOUL_AFFLICTED_RUBBER_TRAPDOOR = BLOCKS.register("soul_afflicted_rubber_trapdoor", () -> new TrapDoorBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD, YATMBlocks.SOUL_AFFLICTED_RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<PressurePlateBlock> SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE = BLOCKS.register("soul_afflicted_rubber_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_SWITCH, YATMBlocks.SOUL_AFFLICTED_RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<ButtonBlock> SOUL_AFFLICTED_RUBBER_BUTTON = BLOCKS.register("soul_afflicted_rubber_button", () -> new ButtonBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_SWITCH, YATMBlocks.SOUL_AFFLICTED_RUBBER_BLOCK_SET_TYPE, 30, true));
	public static final RegistryObject<YATMStandingSignBlock> SOUL_AFFLICTED_RUBBER_SIGN = BLOCKS.register("soul_afflicted_rubber_sign", () -> new YATMStandingSignBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_SIGN, YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD_TYPE));
	public static final RegistryObject<YATMWallSignBlock> SOUL_AFFLICTED_RUBBER_WALL_SIGN = BLOCKS.register("soul_afflicted_rubber_wall_sign", () -> new YATMWallSignBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_SIGN, YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD_TYPE));
	public static final RegistryObject<YATMCeilingHangingSignBlock> SOUL_AFFLICTED_RUBBER_HANGING_SIGN = BLOCKS.register("soul_afflicted_rubber_hanging_sign", () -> new YATMCeilingHangingSignBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_SIGN, YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD_TYPE));
	public static final RegistryObject<YATMWallHangingSignBlock> SOUL_AFFLICTED_RUBBER_WALL_HANGING_SIGN = BLOCKS.register("soul_afflicted_rubber_wall_hanging_sign", () -> new YATMWallHangingSignBlock(YATMBlockProperties.SOUL_AFFLICTED_RUBBER_WOOD_SIGN, YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD_TYPE));
	public static final RegistryObject<CarpetBlock> SOUL_AFFLICTED_LEAF_MULCH = BLOCKS.register("soul_afflicted_leaf_mulch", () -> new CarpetBlock(YATMBlockProperties.LEAF_MULCH));

	// despite it rendering in world when placed down by commands as expected, the one rendered by renderer is different
	public static final RegistryObject<NoCullBlock> DEFAULT_HANGING_POT_SUPPORT_CHAINS = BLOCKS.register("default_hanging_pot_support_chains", () -> new NoCullBlock(BlockBehaviour.Properties.of().noLootTable().noOcclusion()));
	public static final RegistryObject<HangingPotHookBlock> HANGING_POT_HOOK = BLOCKS.register("hanging_pot_hook", () -> new HangingPotHookBlock(YATMBlockProperties.HANGING_POT_HOOK, YATMBlockShapes.HANGING_POT_HOOK));
		
	public static final RegistryObject<CandleLanternBlock> CANDLE_LANTERN = BLOCKS.register("candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> WHITE_CANDLE_LANTERN = BLOCKS.register("white_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> ORANGE_CANDLE_LANTERN = BLOCKS.register("orange_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> MAGENTA_CANDLE_LANTERN = BLOCKS.register("magenta_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> LIGHT_BLUE_CANDLE_LANTERN = BLOCKS.register("light_blue_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> YELLOW_CANDLE_LANTERN = BLOCKS.register("yellow_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> LIME_CANDLE_LANTERN = BLOCKS.register("lime_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> PINK_CANDLE_LANTERN = BLOCKS.register("pink_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> GRAY_CANDLE_LANTERN = BLOCKS.register("gray_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> LIGHT_GRAY_CANDLE_LANTERN = BLOCKS.register("light_gray_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> CYAN_CANDLE_LANTERN = BLOCKS.register("cyan_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> PURPLE_CANDLE_LANTERN = BLOCKS.register("purple_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> BLUE_CANDLE_LANTERN = BLOCKS.register("blue_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> BROWN_CANDLE_LANTERN = BLOCKS.register("brown_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> GREEN_CANDLE_LANTERN = BLOCKS.register("green_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> RED_CANDLE_LANTERN = BLOCKS.register("red_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
	public static final RegistryObject<CandleLanternBlock> BLACK_CANDLE_LANTERN = BLOCKS.register("black_candle_lantern", () -> new CandleLanternBlock(YATMBlockProperties.CANDLE_LANTERN, YATMBlockShapes.CANDLE_LANTERN));
		
		//TODO, maybe add some sort of soul stone 
	public static final RegistryObject<DropExperienceBlock> FOLIAR_STEEL_ORE = BLOCKS.register("foliar_steel_ore", () -> new DropExperienceBlock(YATMBlockProperties.stoneOre()));
	public static final RegistryObject<DropExperienceBlock> DEEPSLATE_FOLIAR_STEEL_ORE = BLOCKS.register("deepslate_foliar_steel_ore", () -> new DropExperienceBlock(YATMBlockProperties.deepslateOre()));
	public static final RegistryObject<Block> FOLIAR_STEEL_BLOCK = BLOCKS.register("foliar_steel_block", () -> new Block(YATMBlockProperties.FOLIAR_STEEL_BLOCK));
	public static final RegistryObject<Block> RUBBER_BLOCK = BLOCKS.register("rubber_block", () -> new Block(YATMBlockProperties.RUBBER_BLOCK));
	public static final RegistryObject<RootedDirtBlock> ROOTED_SOUL_SOIL = BLOCKS.register("rooted_soul_soil", () -> new RootedDirtBlock(YATMBlockProperties.ROOTED_SOUL_SOIL));
	public static final RegistryObject<CarcassRootRootBlock> CARCASS_ROOT_ROOTED_DIRT = BLOCKS.register("carcass_root_rooted_dirt", () -> new CarcassRootRootBlock(YATMBlockProperties.CARCASS_ROOT_ROOTED_DIRT, YATMBlockShapes.CUBE, () -> YATMBlocks.CARCASS_ROOT_FOLIAGE.get().defaultBlockState()));
	public static final RegistryObject<CarcassRootRootBlock> CARCASS_ROOT_ROOTED_NETHERRACK = BLOCKS.register("carcass_root_rooted_netherrack", () -> new CarcassRootRootBlock(YATMBlockProperties.CARCASS_ROOT_ROOTED_NETHERRACK, YATMBlockShapes.CUBE, () -> YATMBlocks.CARCASS_ROOT_FOLIAGE.get().defaultBlockState()));
	
	
	
	public static final RegistryObject<AerialRootsBlock> RUBBER_ROOTS = BLOCKS.register("rubber_roots", () ->  new AerialRootsBlock(YATMBlockProperties.AERIAL_ROOTS));
	public static final RegistryObject<AerialRootsBlock> SOUL_AFFLICTED_RUBBER_ROOTS = BLOCKS.register("soul_afflicted_rubber_roots", () ->  new AerialRootsBlock(YATMBlockProperties.AERIAL_ROOTS));
	
	public static final RegistryObject<LeavesBlock> RUBBER_LEAVES_YOUNG = BLOCKS.register("rubber_leaves_young", () ->  new LeavesBlock(YATMBlockProperties.LEAVES));
	public static final RegistryObject<LeavesBlock> RUBBER_LEAVES_FLOWERING = BLOCKS.register("rubber_leaves_flowering", () ->  new LeavesBlock(YATMBlockProperties.LEAVES));
	public static final RegistryObject<LeavesBlock> RUBBER_LEAVES_OLD = BLOCKS.register("rubber_leaves_old", () ->  new LeavesBlock(YATMBlockProperties.LEAVES));
	public static final RegistryObject<LeavesBlock> SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG = BLOCKS.register("soul_afflicted_rubber_leaves_young", () ->  new LeavesBlock(YATMBlockProperties.LEAVES));
	public static final RegistryObject<LeavesBlock> SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING = BLOCKS.register("soul_afflicted_rubber_leaves_flowering", () ->  new LeavesBlock(YATMBlockProperties.LEAVES));
	public static final RegistryObject<LeavesBlock> SOUL_AFFLICTED_RUBBER_LEAVES_OLD = BLOCKS.register("soul_afflicted_rubber_leaves_old", () ->  new LeavesBlock(YATMBlockProperties.LEAVES));
	
	public static final RegistryObject<RubberBushSaplingBlock> RUBBER_MERISTEM = BLOCKS.register("rubber_meristem", () -> new RubberBushSaplingBlock(YATMBlockProperties.SAPLING));	
	public static final RegistryObject<FlowerPotBlock> POTTED_RUBBER_MERISTEM = BLOCKS.register("potted_rubber_meristem", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.RUBBER_MERISTEM.get(), YATMBlockProperties.FLOWER_POT));
	public static final RegistryObject<SoulAfflictedRubberBushSaplingBlock> SOUL_AFFLICTED_RUBBER_MERISTEM = BLOCKS.register("soul_afflicted_rubber_meristem", () -> new SoulAfflictedRubberBushSaplingBlock(YATMBlockProperties.SAPLING));
	public static final RegistryObject<FlowerPotBlock> POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM = BLOCKS.register("potted_soul_afflicted_rubber_meristem", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), YATMBlockProperties.FLOWER_POT));
	
	
	public static final RegistryObject<AdamumBlock> ADAMUM = BLOCKS.register("adamum", () -> new AdamumBlock(YATMBlockProperties.ADAMUM, YATMBlockShapes.ADAMUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_ADAMUM = BLOCKS.register("potted_adamum", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.ADAMUM.get(), YATMBlockProperties.FLOWER_POT));
	
	public static final RegistryObject<AurumBlock> AURUM = BLOCKS.register("aurum_deminutus", () -> new AurumBlock(YATMBlockProperties.AURUM, YATMBlockShapes.AURUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_AURUM = BLOCKS.register("potted_aurum_deminutus", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, YATMBlocks.AURUM, YATMBlockProperties.FLOWER_POT));

	public static final RegistryObject<CarbumBlock> CARBUM = BLOCKS.register("carbum", () -> new CarbumBlock(YATMBlockProperties.CARBUM, YATMBlockShapes.CARBUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_CARBUM = BLOCKS.register("potted_carbum", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, YATMBlocks.CARBUM, YATMBlockProperties.FLOWER_POT));

	public static final RegistryObject<CuprumBlock> CUPRUM = BLOCKS.register("cuprum", () -> new CuprumBlock(YATMBlockProperties.CUPRUM, YATMBlockShapes.CUPRUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_CUPRUM = BLOCKS.register("potted_cuprum", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.CUPRUM.get(), YATMBlockProperties.FLOWER_POT));
	
	public static final RegistryObject<FerrumBlock> FERRUM = BLOCKS.register("ferrum", () -> new FerrumBlock(YATMBlockProperties.FERRUM, YATMBlockShapes.FERRUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_FERRUM = BLOCKS.register("potted_ferrum", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, YATMBlocks.FERRUM, YATMBlockProperties.FLOWER_POT));

	public static final RegistryObject<FoliumBlock> FOLIUM = BLOCKS.register("folium", () -> new FoliumBlock(YATMBlockProperties.FOLIUM, YATMBlockShapes.FOLIUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_FOLIUM = BLOCKS.register("potted_folium", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, YATMBlocks.FOLIUM, YATMBlockProperties.FLOWER_POT));

	public static final RegistryObject<InfernalumBlock> INFERNALUM = BLOCKS.register("infernalum", () -> new InfernalumBlock(YATMBlockProperties.INFERNALUM, YATMBlockShapes.INFERNALUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_INFERNALUM = BLOCKS.register("potted_infernalum", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, YATMBlocks.INFERNALUM, YATMBlockProperties.FLOWER_POT));
	
	public static final RegistryObject<LapumBlock> LAPUM = BLOCKS.register("lapum", () -> new LapumBlock(YATMBlockProperties.LAPUM, YATMBlockShapes.LAPUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_LAPUM = BLOCKS.register("potted_lapum", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, YATMBlocks.LAPUM, YATMBlockProperties.FLOWER_POT));

	public static final RegistryObject<RuberumBlock> RUBERUM = BLOCKS.register("ruberum", () -> new RuberumBlock(YATMBlockProperties.RUBERUM, YATMBlockShapes.RUBERUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_RUBERUM = BLOCKS.register("potted_ruberum", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, YATMBlocks.RUBERUM, YATMBlockProperties.FLOWER_POT));

	public static final RegistryObject<SamaragdumBlock> SAMARAGDUM = BLOCKS.register("samaragdum", () -> new SamaragdumBlock(YATMBlockProperties.SAMARAGDUM, YATMBlockShapes.SAMARAGDUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_SAMARAGDUM = BLOCKS.register("potted_samaragdum", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, YATMBlocks.SAMARAGDUM, YATMBlockProperties.FLOWER_POT));

	public static final RegistryObject<VicumBlock> VICUM = BLOCKS.register("vicum", () -> new VicumBlock(YATMBlockProperties.VICUM, YATMBlockShapes.VICUM));
	public static final RegistryObject<FlowerPotBlock> POTTED_VICUM = BLOCKS.register("potted_vicum", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.VICUM.get(), YATMBlockProperties.FLOWER_POT));

	
	
	// TODO, some dripping tree, a small tree with notable extrafloral nectaries, bees will interact with them, they will drip, small feeling tree
	
	public static final RegistryObject<BasinOfTearsFloralBlock> BASIN_OF_TEARS_FLORAL = BLOCKS.register("basin_of_tears_floral", () -> new BasinOfTearsFloralBlock(YATMBlockProperties.BASIN_OF_TEARS, YATMBlockShapes.CUBE/*BASIN_OF_TEARS_FLORAL*/));
	public static final RegistryObject<BasinOfTearsVegetationBlock> BASIN_OF_TEARS_VEGETATION = BLOCKS.register("basin_of_tears_vegetation", () -> new BasinOfTearsVegetationBlock(YATMBlockProperties.BASIN_OF_TEARS_VEGETATIVE, YATMBlockShapes.BASIN_OF_TEARS_VEGETATION, () -> YATMBlocks.BASIN_OF_TEARS_FLORAL.get().defaultBlockState().setValue(BasinOfTearsFloralBlock.FLOWER_COUNT, RandomSource.create().nextIntBetweenInclusive(1, 4))));
	
	// maybe do crop and final
	public static final RegistryObject<CandlelilyBlock> CANDLELILY = BLOCKS.register("candlelily", () -> new CandlelilyBlock(YATMBlockProperties.CANDLELILY, YATMBlockShapes.CANDLELILY));	
	public static final RegistryObject<FlowerPotBlock> POTTED_CANDLELILY = BLOCKS.register("potted_candlelily", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.CANDLELILY.get(), YATMBlockProperties.FLOWER_POT));
	
	public static final RegistryObject<CarcassRootFoliageBlock> CARCASS_ROOT_FOLIAGE = BLOCKS.register("carcass_root_foliage", () -> new CarcassRootFoliageBlock(YATMBlockProperties.CARCASS_ROOT_FOLIAGE, YATMBlockShapes.CUBE, YATMItems.CARCASS_ROOT_CUTTING::get, () -> new ItemStack(YATMItems.CARCASS_ROOT_CUTTING.get()), new CarcassRootRootSupplier()));
	public static final RegistryObject<FlowerPotBlock> POTTED_CARCASS_ROOT_FOLIAGE = BLOCKS.register("potted_carcass_root_foliage", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.CARCASS_ROOT_FOLIAGE.get(), YATMBlockProperties.FLOWER_POT));
	
	// TODO, perhaps adjust hitbox to match stages closer
	public static final RegistryObject<CustomSeedCropBlock> COTTON = BLOCKS.register("cotton", () -> new CustomSeedCropBlock(YATMBlockProperties.CROP, YATMItems.COTTON_SEEDS::get));
	
	// maybe make more like pitcher plants and torch flower, turnable into inert decoration afterwards
	public static final RegistryObject<FireEaterLilyBlock> FIRE_EATER_LILY = BLOCKS.register("fire_eater_lily", () -> new FireEaterLilyBlock(YATMBlockProperties.FIRE_EATER_LILY, YATMBlockShapes.FIRE_EATER_LILY));
	public static final RegistryObject<FireEaterLilyDecorativeBlock> FIRE_EATER_LILY_DECORATIVE = BLOCKS.register("fire_eater_lily_decorative", () -> new FireEaterLilyDecorativeBlock(YATMBlockProperties.FIRE_EATER_LILY, YATMBlockShapes.FIRE_EATER_LILY_OLD_LIT));
	public static final RegistryObject<FireEaterLilyUnlitDecorativeBlock> FIRE_EATER_LILY_UNLIT_DECORATIVE = BLOCKS.register("fire_eater_lily_unlit_decorative", () -> new FireEaterLilyUnlitDecorativeBlock(YATMBlockProperties.FIRE_EATER_LILY_UNLIT, YATMBlockShapes.FIRE_EATER_LILY_OLD_UNLIT));
	public static final RegistryObject<FlowerPotBlock> POTTED_FIRE_EATER_LILY = BLOCKS.register("potted_fire_eater_lily", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.FIRE_EATER_LILY_DECORATIVE.get(), YATMBlockProperties.FLOWER_POT));
	public static final RegistryObject<FlowerPotBlock> POTTED_FIRE_EATER_LILY_UNLIT = BLOCKS.register("potted_fire_eater_lily_unlit", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.FIRE_EATER_LILY_UNLIT_DECORATIVE.get(), YATMBlockProperties.FLOWER_POT));
	
	public static final RegistryObject<IceCoralBlock> ICE_CORAL = BLOCKS.register("ice_coral", () -> new IceCoralBlock(YATMBlockProperties.ICE_CORAL, YATMBlockShapes.ICE_CORAL));
	public static final RegistryObject<WaterloggableBlock> BLEACHED_ICE_CORAL_OLD = BLOCKS.register("bleached_ice_coral_old", () -> new WaterloggableBlock(YATMBlockProperties.BLEACHED_ICE_CORAL, (bs, bg, bp, cc) -> YATMBlockShapes.ICE_CORAL_OLD));
	public static final RegistryObject<WaterloggableBlock> BLEACHED_ICE_CORAL_ADOLESCENT = BLOCKS.register("bleached_ice_coral_adolescent", () -> new WaterloggableBlock(YATMBlockProperties.BLEACHED_ICE_CORAL, (bs, bg, bp, cc) -> YATMBlockShapes.ICE_CORAL_ADOLESCENT));
	public static final RegistryObject<WaterloggableBlock> BLEACHED_ICE_CORAL_YOUNG = BLOCKS.register("bleached_ice_coral_young", () -> new WaterloggableBlock(YATMBlockProperties.BLEACHED_ICE_CORAL, (bs, bg, bp, cc) -> YATMBlockShapes.ICE_CORAL_YOUNG));
	public static final RegistryObject<WaterloggableBlock> BLEACHED_ICE_CORAL_POLYP = BLOCKS.register("bleached_ice_coral_polyp", () -> new WaterloggableBlock(YATMBlockProperties.BLEACHED_ICE_CORAL, (bs, bg, bp, cc) -> YATMBlockShapes.ICE_CORAL_POLYP));
	public static final RegistryObject<FlowerPotBlock> POTTED_BLEACHED_ICE_CORAL_OLD = BLOCKS.register("potted_bleached_ice_coral_old", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.BLEACHED_ICE_CORAL_OLD.get(), YATMBlockProperties.FLOWER_POT));
	public static final RegistryObject<FlowerPotBlock> POTTED_BLEACHED_ICE_CORAL_ADOLESCENT = BLOCKS.register("potted_bleached_ice_coral_adolescent", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.BLEACHED_ICE_CORAL_ADOLESCENT.get(), YATMBlockProperties.FLOWER_POT));
	public static final RegistryObject<FlowerPotBlock> POTTED_BLEACHED_ICE_CORAL_YOUNG = BLOCKS.register("potted_bleached_ice_coral_young", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.BLEACHED_ICE_CORAL_YOUNG.get(), YATMBlockProperties.FLOWER_POT));
	public static final RegistryObject<FlowerPotBlock> POTTED_BLEACHED_ICE_CORAL_POLYP = BLOCKS.register("potted_bleached_ice_coral_polyp", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.BLEACHED_ICE_CORAL_POLYP.get(), YATMBlockProperties.FLOWER_POT));
	
	public static final RegistryObject<PhantasmalShelfFungiBlock> PHANTASMAL_SHELF_FUNGUS = BLOCKS.register("phantasmal_shelf_fungus", () -> new PhantasmalShelfFungiBlock(YATMBlockProperties.PHANTASMAL_SHELF_FUNGUS, YATMBlockShapes.PHANTASMAL_SHELF_FUNGUS, YATMItems.PHANTASMAL_SHELF_FUNGUS_ITEM::get));
	
	// TODO, properties
	public static final RegistryObject<PitcherClusterBlock> PITCHER_CLUSTER = BLOCKS.register("pitcher_cluster", () -> new PitcherClusterBlock(YATMBlockProperties.CROP, YATMBlockShapes.PITCHER_CLUSTER));
	
	public static final RegistryObject<PrismarineCrystalMossBlock> PRISMARINE_CRYSTAL_MOSS = BLOCKS.register("prismarine_crystal_moss", () -> new PrismarineCrystalMossBlock(YATMBlockProperties.PRISMARINE_CRYSTAL_MOSS, YATMBlockShapes.PRISMARINE_CRYSTAL_MOSS, () -> YATMItems.PRISMARINE_CRYSTAL_MOSS_SPORES.get()));
	
	public static final RegistryObject<DecayingBlock> FALLEN_SHULKWART_SPORES = BLOCKS.register("fallen_shulkwart_spores", () -> new DecayingBlock(YATMBlockProperties.FALLEN_SHULKWART_SPORES, YATMBlockShapes.DOWNWARD_LICHEN_LIKE, 64));
	public static final RegistryObject<ShulkwartBlock> SHULKWART = YATMBlocks.shulkwart("shulkwart", (DyeColor)null, () -> YATMItems.SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> WHITE_SHULKWART = YATMBlocks.shulkwart("white_shulkwart", DyeColor.WHITE, () -> YATMItems.WHITE_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> ORANGE_SHULKWART = YATMBlocks.shulkwart("orange_shulkwart", DyeColor.ORANGE, () -> YATMItems.ORANGE_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> MAGENTA_SHULKWART = YATMBlocks.shulkwart("magenta_shulkwart", DyeColor.MAGENTA, () -> YATMItems.MAGENTA_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> LIGHT_BLUE_SHULKWART = YATMBlocks.shulkwart("light_blue_shulkwart", DyeColor.LIGHT_BLUE, () -> YATMItems.LIGHT_BLUE_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> YELLOW_SHULKWART = YATMBlocks.shulkwart("yellow_shulkwart", DyeColor.YELLOW, () -> YATMItems.YELLOW_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> LIME_SHULKWART = YATMBlocks.shulkwart("lime_shulkwart", DyeColor.LIME, () -> YATMItems.LIME_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> PINK_SHULKWART = YATMBlocks.shulkwart("pink_shulkwart", DyeColor.PINK, () -> YATMItems.PINK_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> GRAY_SHULKWART = YATMBlocks.shulkwart("gray_shulkwart", DyeColor.GRAY, () -> YATMItems.GRAY_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> LIGHT_GRAY_SHULKWART = YATMBlocks.shulkwart("light_gray_shulkwart", DyeColor.LIGHT_GRAY, () -> YATMItems.LIGHT_GRAY_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> CYAN_SHULKWART = YATMBlocks.shulkwart("cyan_shulkwart", DyeColor.CYAN, () -> YATMItems.CYAN_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> PURPLE_SHULKWART = YATMBlocks.shulkwart("purple_shulkwart", DyeColor.PURPLE, () -> YATMItems.PURPLE_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> BLUE_SHULKWART = YATMBlocks.shulkwart("blue_shulkwart", DyeColor.BLUE, () -> YATMItems.BLUE_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> BROWN_SHULKWART = YATMBlocks.shulkwart("brown_shulkwart", DyeColor.BROWN, () -> YATMItems.BROWN_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> GREEN_SHULKWART = YATMBlocks.shulkwart("green_shulkwart", DyeColor.GREEN, () -> YATMItems.GREEN_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> RED_SHULKWART = YATMBlocks.shulkwart("red_shulkwart", DyeColor.RED, () -> YATMItems.RED_SHULKWART_HORN.get());
	public static final RegistryObject<ShulkwartBlock> BLACK_SHULKWART = YATMBlocks.shulkwart("black_shulkwart", DyeColor.BLACK, () -> YATMItems.BLACK_SHULKWART_HORN.get());
	
	public static final RegistryObject<OnceFruitVineBodyBlock> SPIDER_VINE = BLOCKS.register("spider_vine", () -> new OnceFruitVineBodyBlock(YATMBlockProperties.SPIDER_VINE, YATMBlocks::getSpiderVineMeristem, () -> new ItemStack(YATMItems.SPIDER_VINE_FRUITS.get(), RANDOM.nextIntBetweenInclusive(1, 3))));
	public static final RegistryObject<VineMeristemBlock> SPIDER_VINE_MERISTEM = BLOCKS.register("spider_vine_meristem", () -> new VineMeristemBlock(YATMBlockProperties.SPIDER_VINE, YATMBlocks::getSpiderVine));
	
	public static final RegistryObject<VariegatedCactusBlock> VARIEGATED_CACTUS = BLOCKS.register("variegated_cactus", () -> new VariegatedCactusBlock(YATMBlockProperties.CACTUS, () -> Blocks.CACTUS.defaultBlockState()));
	public static final RegistryObject<FlowerPotBlock> POTTED_VARIEGATED_CACTUS = BLOCKS.register("potted_variegated_cactus", () -> new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.FLOWER_POT, () -> YATMBlocks.VARIEGATED_CACTUS.get(), YATMBlockProperties.FLOWER_POT));

	
	
	private static final OnceFruitVineBodyBlock getSpiderVine()
	{
		return YATMBlocks.SPIDER_VINE.get();
	} // end getSpiderVine()
	
	private static final VineMeristemBlock getSpiderVineMeristem()
	{
		return YATMBlocks.SPIDER_VINE_MERISTEM.get();
	} // end getSpiderVine()	
	
	
	public static final RegistryObject<GraftingTableBlock> GRAFTING_TABLE = BLOCKS.register("grafting_table", () -> new GraftingTableBlock(YATMBlockProperties.GRAFTING_TABLE, YATMBlockShapes.CUBE));
	
	public static final RegistryObject<SapCollectorBlock> SAP_COLLECTOR = BLOCKS.register("sap_collector", () -> new SapCollectorBlock(YATMBlockProperties.SAP_COLLECTOR, YATMBlockShapes.SAP_COLLECTOR_SHAPES));
	public static final RegistryObject<FilledSapCollectorBlock> SAP_COLLECTOR_LATEX = BLOCKS.register("sap_collector_latex", () -> new FilledSapCollectorBlock(YATMBlockProperties.SAP_COLLECTOR, YATMBlockShapes.SAP_COLLECTOR_SHAPES, YATMBlocks.SAP_COLLECTOR.get()::defaultBlockState, YATMFluids.LATEX::get));
	public static final RegistryObject<FilledSapCollectorBlock> SAP_COLLECTOR_SOUL_SAP = BLOCKS.register("sap_collector_soul_sap", () -> new FilledSapCollectorBlock(YATMBlockProperties.SAP_COLLECTOR, YATMBlockShapes.SAP_COLLECTOR_SHAPES, YATMBlocks.SAP_COLLECTOR.get()::defaultBlockState, YATMFluids.SOUL_SAP::get));
	
	// TODO, make up properties
	public static final RegistryObject<SpinningWheelBlock> SPINNING_WHEEL = BLOCKS.register("spinning_wheel", () -> new SpinningWheelBlock(YATMBlockProperties.EMPTY, YATMBlockShapes.SPINNING_WHEEL));
	
	public static final RegistryObject<HeatSinkBlock> LARGE_COPPER_HEAT_SINK = BLOCKS.register("large_copper_heat_sink", () -> new HeatSinkBlock(BlockBehaviour.Properties.of()));
	
	
	
	public static final RegistryObject<DataStorageBlock> DATA_STORAGE_BLOCK = BLOCKS.register("data_storage_block", () -> new DataStorageBlock(YATMBlockProperties.DATA_DEVICE));
	public static final RegistryObject<DataCollectorBlock> DATA_SCAN_COLLECTOR = BLOCKS.register("data_scan_collector", () -> new DataCollectorBlock(YATMBlockProperties.DATA_DEVICE));
	public static final RegistryObject<DestructiveDataScannerBlock> DESTRUCTIVE_DATA_SCANNER = BLOCKS.register("destructive_data_scanner", () -> new DestructiveDataScannerBlock(YATMBlockProperties.DATA_DEVICE));
	public static final RegistryObject<DataProcessorBlock> DATA_PROCESSOR = BLOCKS.register("data_processor", () -> new DataProcessorBlock(YATMBlockProperties.DATA_DEVICE));

	
	
	public static final RegistryObject<BiolerBlock> STEEL_BIOLER = BLOCKS.register("steel_bioler", () -> new BiolerBlock(YATMBlockProperties.STEEL_PLANT_MACHINE, YATMBlockShapes.BIOLER, DeviceTierConstants.STEEL_CURRENT_CAPACITY, DeviceTierConstants.STEEL_MAX_CURRENT_TRANSFER, DeviceTierConstants.STEEL_TANK_CAPACITY, DeviceTierConstants.STEEL_MAXIMUM_FLUID_TRANSFER_RATE));
	public static final RegistryObject<BoilerTankBlock> STEEL_BOILER_TANK = BLOCKS.register("steel_boiler_tank", () -> new BoilerTankBlock(YATMBlockProperties.STEEL_MACHINE, 256, 32000));
	public static final RegistryObject<BoilerBlock> STEEL_BOILER = BLOCKS.register("steel_boiler", () -> new BoilerBlock(YATMBlockProperties.STEEL_PLANT_MACHINE, YATMBlockShapes.BOILER, DeviceTierConstants.STEEL_DEVICE));
	public static final RegistryObject<CrucibleBlock> STEEL_CRUCIBLE = BLOCKS.register("steel_crucible", () -> new CrucibleBlock(YATMBlockProperties.STEEL_PLANT_MACHINE, YATMBlockShapes.CUBE));
	public static final RegistryObject<CrystallizerBlock> STEEL_CRYSTALLIZER = BLOCKS.register("steel_crystallizer", () -> new CrystallizerBlock(YATMBlockProperties.STEEL_PLANT_MACHINE, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_TANK_CAPACITY, DeviceTierConstants.STEEL_MAXIMUM_FLUID_TRANSFER_RATE));
	public static final RegistryObject<CurrentFurnaceBlock> STEEL_CURRENT_FURNACE = BLOCKS.register("steel_current_furnace", () -> new CurrentFurnaceBlock(YATMBlockProperties.STEEL_MACHINE, YATMBlockShapes.CUBE));
	public static final RegistryObject<ExtractorBlock> STEEL_EXTRACTOR = BLOCKS.register("steel_extractor", () -> new ExtractorBlock(YATMBlockProperties.STEEL_PLANT_MACHINE, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_CURRENT_CAPACITY, DeviceTierConstants.STEEL_MAX_CURRENT_TRANSFER, DeviceTierConstants.STEEL_TANK_CAPACITY, DeviceTierConstants.STEEL_MAXIMUM_FLUID_TRANSFER_RATE));
//	public static final RegistryObject<ExtruderBlock> STEEL_EXTRUDER = BLOCKS.register("steel_extruder", () -> new ExtruderBlock(YATMBlockProperties.STEEL_MACHINE, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_CURRENT_CAPACITY, DeviceTierConstants.STEEL_MAX_CURRENT_TRANSFER));
	public static final RegistryObject<GrinderBlock> STEEL_GRINDER = BLOCKS.register("steel_grinder", () -> new GrinderBlock(YATMBlockProperties.STEEL_MACHINE, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_CURRENT_CAPACITY, DeviceTierConstants.STEEL_MAX_CURRENT_TRANSFER));
	public static final RegistryObject<InjectorBlock> STEEL_INJECTOR = BLOCKS.register("steel_injector", () -> new InjectorBlock(YATMBlockProperties.STEEL_PLANT_MACHINE, YATMBlockShapes.CUBE, DeviceTierConstants.STEEL_DEVICE));
	
	// TODO, refactor property out
	public static final RegistryObject<CurrentUnitForgeEnergyInterchangerBlock> C_U_F_E_I = BLOCKS.register("current_unit_forge_energy_interchanger", () -> new CurrentUnitForgeEnergyInterchangerBlock(BlockBehaviour.Properties.of()));
	
	public static final RegistryObject<BatterySolarPanelBlock> CRUDE_BATTERY_SOLAR_PANEL = BLOCKS.register("crude_battery_solar_panel", () -> new BatterySolarPanelBlock(YATMBlockProperties.SOLAR_PANEL, YATMBlockShapes.CUBE, 1024, 8, 64, SolarPanelSettings.CRUDE));
	public static final RegistryObject<BatterySolarPanelBlock> ADVANCED_BATTERY_SOLAR_PANEL = BLOCKS.register("advanced_battery_solar_panel", () -> new BatterySolarPanelBlock(YATMBlockProperties.SOLAR_PANEL, YATMBlockShapes.CUBE, 65536, 64, 512, SolarPanelSettings.ADVANCED));
	public static final RegistryObject<BatterySolarPanelBlock> SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL = BLOCKS.register("suns_complement_battery_solar_panel", () -> new BatterySolarPanelBlock(YATMBlockProperties.SOLAR_PANEL, YATMBlockShapes.CUBE, 524288, 512, 4096, SolarPanelSettings.SUNS_COMPLEMENT));
	public static final RegistryObject<SolarPanelBlock> CRUDE_SOLAR_PANEL = BLOCKS.register("crude_solar_panel", () -> new SolarPanelBlock(YATMBlockProperties.SOLAR_PANEL, YATMBlockShapes.SOLAR_PANEL, 8, 8, 64, SolarPanelSettings.CRUDE));
	public static final RegistryObject<SolarPanelBlock> ADVANCED_SOLAR_PANEL = BLOCKS.register("advanced_solar_panel", () -> new SolarPanelBlock(YATMBlockProperties.SOLAR_PANEL, YATMBlockShapes.SOLAR_PANEL, 64, 64, 512, SolarPanelSettings.ADVANCED));
	public static final RegistryObject<SolarPanelBlock> SUNS_COMPLEMENT_SOLAR_PANEL = BLOCKS.register("suns_complement_solar_panel", () -> new SolarPanelBlock(YATMBlockProperties.SOLAR_PANEL, YATMBlockShapes.SOLAR_PANEL, 512, 512, 4096, SolarPanelSettings.SUNS_COMPLEMENT));
	
	
	
	public static final RegistryObject<ConduitVineBlock> CONDUIT_VINES = BLOCKS.register("conduit_vines", () -> new ConduitVineBlock(YATMBlockProperties.CONDUIT_VINES, YATMBlockShapes.CONDUIT_VINES));
	
	public static final RegistryObject<TankBlock> STEEL_TANK = BLOCKS.register("steel_tank", () -> new TankBlock(YATMBlockProperties.STEEL_TANK, YATMBlockShapes.STEEL_TANK, DeviceTierConstants.STEEL_TANK));
	
	public static final RegistryObject<ChannelVineBlock> CHANNEL_VINES = BLOCKS.register("channel_vines", () -> new ChannelVineBlock(YATMBlockProperties.CHANNEL_VINES, YATMBlockShapes.CHANNEL_VINES));

	// TODO, maybe add a regstone emitter, that's output stength can be dynamically configured
	// TODO, maybe add a redstone clock, that's period can be configured.
	
	public static final RegistryObject<LiquidBlock> BIO_LIQUID_BLOCK = BLOCKS.register("bio", () -> new LiquidBlock(YATMFluids.BIO, YATMBlockProperties.liquid(MapColor.COLOR_BROWN)));
	public static final RegistryObject<LiquidBlock> CHORUS_LIQUID_BLOCK = BLOCKS.register("chorus", () -> new LiquidBlock(YATMFluids.CHORUS, YATMBlockProperties.liquid(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<LiquidBlock> CHORUS_BIO_LIQUID_BLOCK = BLOCKS.register("chorus_bio", () -> new LiquidBlock(YATMFluids.CHORUS_BIO, YATMBlockProperties.liquid(MapColor.COLOR_BROWN/*should be a purple brown*/)));
	public static final RegistryObject<LiquidBlock> ENDER_LIQUID_BLOCK = BLOCKS.register("ender", () -> new LiquidBlock(YATMFluids.ENDER, YATMBlockProperties.liquid(MapColor.COLOR_GREEN)));
	public static final RegistryObject<LiquidBlock> ESSENCE_OF_DECAY_LIQUID_BLOCK = BLOCKS.register("essence_of_decay", () -> new LiquidBlock(YATMFluids.ESSENCE_OF_DECAY, YATMBlockProperties.liquid(MapColor.COLOR_BLACK)));
	public static final RegistryObject<LiquidBlock> ESSENCE_OF_SOULS_LIQUID_BLOCK = BLOCKS.register("essence_of_souls", () -> new LiquidBlock(YATMFluids.ESSENCE_OF_SOULS, YATMBlockProperties.liquid(MapColor.COLOR_LIGHT_BLUE)));
	public static final RegistryObject<LiquidBlock> LATEX_LIQUID_BLOCK = BLOCKS.register("latex", () -> new LiquidBlock(YATMFluids.LATEX, YATMBlockProperties.liquid(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<LiquidBlock> SILICON_OXIDE_LIQUID_BLOCK = BLOCKS.register("silicon_oxide", () -> new LiquidBlock(YATMFluids.SILICON_OXIDE, YATMBlockProperties.liquid(MapColor.COLOR_ORANGE).lightLevel((bs) -> 12)));
	public static final RegistryObject<LiquidBlock> SOUL_SAP_LIQUID_BLOCK = BLOCKS.register("soul_sap", () -> new LiquidBlock(YATMFluids.SOUL_SAP, YATMBlockProperties.liquid(MapColor.COLOR_CYAN)));
	public static final RegistryObject<LiquidBlock> SOUL_SYRUP_LIQUID_BLOCK = BLOCKS.register("soul_syrup", () -> new LiquidBlock(YATMFluids.SOUL_SYRUP, YATMBlockProperties.liquid(MapColor.COLOR_CYAN)));

	public static final RegistryObject<LiquidBlock> CELESTIAL_LIGHT_LIQUID_BLOCK = BLOCKS.register("celestial_light", () -> new LiquidBlock(YATMFluids.CELESTIAL_LIGHT, YATMBlockProperties.liquid(MapColor.COLOR_PURPLE/*should be a dark almost black color*/)));
	public static final RegistryObject<LiquidBlock> LUNAR_LIGHT_LIQUID_BLOCK = BLOCKS.register("lunar_light", () -> new LiquidBlock(YATMFluids.LUNAR_LIGHT, YATMBlockProperties.liquid(MapColor.COLOR_BLUE/*should be dark*/)));
	public static final RegistryObject<LiquidBlock> SOLAR_LIGHT_LIQUID_BLOCK = BLOCKS.register("solar_light", () -> new LiquidBlock(YATMFluids.SOLAR_LIGHT, YATMBlockProperties.liquid(MapColor.COLOR_ORANGE)));	

	public static final RegistryObject<CreativeCurrentSourceBlock> CREATIVE_CURRENT_SOURCE = BLOCKS.register("creative_current_source", () -> new CreativeCurrentSourceBlock(YATMBlockProperties.CREATIVE, YATMBlockShapes.CUBE));	
	
	
	
	private static RegistryObject<ShulkwartBlock> shulkwart(String identifier, DyeColor color, Supplier<Item> horn)
	{
		return YATMBlocks.BLOCKS.register(identifier, () -> new ShulkwartBlock(YATMBlockProperties.shulkwart(color), YATMBlockShapes.SHULKWART, YATMItems.SHULKWART_SPORES::get , YATMBlocks.FALLEN_SHULKWART_SPORES::get, new RandomCountItemStackSupplier(horn, 1, 3, RandomSource.createNewThreadLocalInstance())));
	} // end createShulkwart
	

	
	public static void addFlowersToPots()
	{
		FlowerPotBlock minecraftFlowerPot = (FlowerPotBlock)Blocks.FLOWER_POT;
		minecraftFlowerPot.addPlant(YATMBlocks.RUBBER_MERISTEM.getKey().location(), YATMBlocks.POTTED_RUBBER_MERISTEM);
		minecraftFlowerPot.addPlant(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.getKey().location(), YATMBlocks.POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM);
		minecraftFlowerPot.addPlant(YATMBlocks.ADAMUM.getKey().location(), YATMBlocks.POTTED_ADAMUM);
		minecraftFlowerPot.addPlant(YATMBlocks.AURUM.getKey().location(), YATMBlocks.POTTED_AURUM);
		minecraftFlowerPot.addPlant(YATMBlocks.CANDLELILY.getKey().location(), YATMBlocks.POTTED_CANDLELILY);
		minecraftFlowerPot.addPlant(YATMBlocks.CARBUM.getKey().location(), YATMBlocks.POTTED_CARBUM);
		minecraftFlowerPot.addPlant(YATMBlocks.CARCASS_ROOT_FOLIAGE.getKey().location(), YATMBlocks.POTTED_CARCASS_ROOT_FOLIAGE);
		minecraftFlowerPot.addPlant(YATMBlocks.CUPRUM.getKey().location(), YATMBlocks.POTTED_CUPRUM);
		minecraftFlowerPot.addPlant(YATMBlocks.FERRUM.getKey().location(), YATMBlocks.POTTED_FERRUM);
		minecraftFlowerPot.addPlant(YATMBlocks.FIRE_EATER_LILY_DECORATIVE.getKey().location(), YATMBlocks.POTTED_FIRE_EATER_LILY);
		minecraftFlowerPot.addPlant(YATMBlocks.FIRE_EATER_LILY_UNLIT_DECORATIVE.getKey().location(), YATMBlocks.POTTED_FIRE_EATER_LILY_UNLIT);
		minecraftFlowerPot.addPlant(YATMBlocks.FOLIUM.getKey().location(), YATMBlocks.POTTED_FOLIUM);
		minecraftFlowerPot.addPlant(YATMBlocks.BLEACHED_ICE_CORAL_OLD.getKey().location(), YATMBlocks.POTTED_BLEACHED_ICE_CORAL_OLD);
		minecraftFlowerPot.addPlant(YATMBlocks.BLEACHED_ICE_CORAL_ADOLESCENT.getKey().location(), YATMBlocks.POTTED_BLEACHED_ICE_CORAL_ADOLESCENT);
		minecraftFlowerPot.addPlant(YATMBlocks.BLEACHED_ICE_CORAL_YOUNG.getKey().location(), YATMBlocks.POTTED_BLEACHED_ICE_CORAL_YOUNG);
		minecraftFlowerPot.addPlant(YATMBlocks.BLEACHED_ICE_CORAL_POLYP.getKey().location(), YATMBlocks.POTTED_BLEACHED_ICE_CORAL_POLYP);		
		minecraftFlowerPot.addPlant(YATMBlocks.INFERNALUM.getKey().location(), YATMBlocks.POTTED_INFERNALUM);
		minecraftFlowerPot.addPlant(YATMBlocks.LAPUM.getKey().location(), YATMBlocks.POTTED_LAPUM);		
		minecraftFlowerPot.addPlant(YATMBlocks.RUBERUM.getKey().location(), YATMBlocks.POTTED_RUBERUM);
		minecraftFlowerPot.addPlant(YATMBlocks.SAMARAGDUM.getKey().location(), YATMBlocks.POTTED_SAMARAGDUM);		
		minecraftFlowerPot.addPlant(YATMBlocks.VARIEGATED_CACTUS.getKey().location(), YATMBlocks.POTTED_VARIEGATED_CACTUS);
		minecraftFlowerPot.addPlant(YATMBlocks.VICUM.getKey().location(), YATMBlocks.POTTED_VICUM);
		
	} // end addFlowersToPots()
	
	public static void addSapCollectorVariants()
	{
		SapCollectorBlock sapCollector = YATMBlocks.SAP_COLLECTOR.get();
		sapCollector.addVariant(YATMFluids.LATEX.get(), YATMBlocks.SAP_COLLECTOR_LATEX.get().defaultBlockState());
		sapCollector.addVariant(YATMFluids.SOUL_SAP.get(), YATMBlocks.SAP_COLLECTOR_SOUL_SAP.get().defaultBlockState());

	} // end addSapCollectorVariants()

} // end class