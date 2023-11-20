package com.gsr.gsr_yatm.data_generation;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.FaceBlock;
import com.gsr.gsr_yatm.block.candle_lantern.CandleLanternBlock;
import com.gsr.gsr_yatm.block.conduit.IConduit;
import com.gsr.gsr_yatm.block.device.AttachmentState;
import com.gsr.gsr_yatm.block.device.bioler.BiolerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerTankBlock;
import com.gsr.gsr_yatm.block.device.creative.current_source.CreativeCurrentSourceBlock;
import com.gsr.gsr_yatm.block.device.heat_sink.HeatSinkBlock;
import com.gsr.gsr_yatm.block.device.solar.BatterySolarPanelBlock;
import com.gsr.gsr_yatm.block.device.spinning_wheel.SpinningWheelBlock;
import com.gsr.gsr_yatm.block.plant.adamum.AdamumBlock;
import com.gsr.gsr_yatm.block.plant.aurum.AurumBlock;
import com.gsr.gsr_yatm.block.plant.carbum.CarbumBlock;
import com.gsr.gsr_yatm.block.plant.carcass_root.CarcassRootFoliageBlock;
import com.gsr.gsr_yatm.block.plant.carcass_root.CarcassRootRootBlock;
import com.gsr.gsr_yatm.block.plant.cuprum.CuprumBlock;
import com.gsr.gsr_yatm.block.plant.ferrum.FerrumBlock;
import com.gsr.gsr_yatm.block.plant.fire_eater_lily.FireEaterLilyBlock;
import com.gsr.gsr_yatm.block.plant.folium.FoliumBlock;
import com.gsr.gsr_yatm.block.plant.fungi.PhantasmalShelfFungiBlock;
import com.gsr.gsr_yatm.block.plant.ice_coral.IceCoralBlock;
import com.gsr.gsr_yatm.block.plant.infernalum.InfernalumBlock;
import com.gsr.gsr_yatm.block.plant.lapum.LapumBlock;
import com.gsr.gsr_yatm.block.plant.prismarine_crystal_moss.PrismarineCrystalMossBlock;
import com.gsr.gsr_yatm.block.plant.ruberum.RuberumBlock;
import com.gsr.gsr_yatm.block.plant.samaragdum.SamaragdumBlock;
import com.gsr.gsr_yatm.block.plant.tree.SelfLayeringSaplingBlock;
import com.gsr.gsr_yatm.block.plant.tree.TappedLogBlock;
import com.gsr.gsr_yatm.block.plant.vicum.VicumBlock;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.utilities.DirectionUtil;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder.PartBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class YATMBlockStateProvider extends BlockStateProvider
{
	public static final String BLOCK_MODEL_FOLDER = "block/";
	
	public static final String CUTOUT_RENDER_TYPE = "cutout";
	
	public static final ModelFile DEFAULT_ITEM_MODEL_PARENT = new ModelFile.UncheckedModelFile("minecraft:item/generated");
	
	
	public static final ModelFile CACTUS_MODEL = new ModelFile.UncheckedModelFile("minecraft:block/cactus");
	public static final ModelFile CARPET = new ModelFile.UncheckedModelFile("minecraft:block/carpet");
	public static final ModelFile CROP = new ModelFile.UncheckedModelFile("minecraft:block/crop");
	public static final ModelFile CROSS = new ModelFile.UncheckedModelFile("minecraft:block/cross");
	public static final ModelFile FLOWER_POT_CROSS = new ModelFile.UncheckedModelFile("minecraft:block/flower_pot_cross");
	public static final ModelFile GLOW_LICHEN = new ModelFile.UncheckedModelFile("minecraft:block/glow_lichen");
	public static final ModelFile MANGROVE_ROOTS = new ModelFile.UncheckedModelFile("minecraft:block/mangrove_roots");
	public static final ModelFile POTTED_CACTUS = new ModelFile.UncheckedModelFile("minecraft:block/potted_cactus");
	
	public static final ResourceLocation STONE_TEXTURE = new ResourceLocation("minecraft", "block/stone");
	
	
	
	public static final ModelFile PATCH_CROSS_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/patch_cross"));
	public static final ModelFile PHANTASMAL_SHELF_FUNGUS_SMALL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/phantasmal_shelf_fungi_small"));
	public static final ModelFile PHANTASMAL_MEDIUM_SHELF_FUNGUS = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/phantasmal_shelf_fungi_medium"));
	public static final ModelFile PHANTASMAL_LARGE_SHELF_FUNGUS = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/phantasmal_shelf_fungi_large"));
	
	public static final ModelFile PITCHER_CLUSTER = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/pitcher_cluster"));
	
	public static final ModelFile SHULKWART_SPORE_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/shulkwart_spore"));
	public static final ModelFile SHULKWARK_CRYPTIC_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/shulkwart_cryptic"));
	private ModelFile m_shulkwartFruitOneModel;
	private ModelFile m_shulkwartFruitTwoModel;
	private ModelFile m_shulkwartFruitThreeModel;
	public static final ModelFile SHULKWART_HORN = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/shulkwart_horn"));
	
	
	
	public static final ModelFile LARGE_HEAT_SINK_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/large_heat_sink"));
	
	public static final ModelFile HANGING_POT_HOOK_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/hanging_pot_hook"));
	public static final ModelFile DEFAULT_HANGING_POT_SUPPORT_CHAINS_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/default_hanging_pot_support_chains"));
	
	public static final ModelFile LANTERN_CHAIN_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/lantern_chain"));
	public static final ModelFile TEMPLATE_CANDLE_LANTERN_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/template_candle_lantern"));
	public static final ModelFile TEMPLATE_CANDLE_LANTERN_HANGING_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/template_candle_lantern_hanging"));
	
	public static final ModelFile GRAFTING_TABLE_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/grafting_table"));
	
	public static final ModelFile SAP_COLLECTOR_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/sap_collector"));
	public static final ModelFile SAP_COLLECTOR_FILLED_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/sap_collector_filled"));
	
	public static final ModelFile SPINNING_WHEEL_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/spinning_wheel"));
	
	
	public static final ModelFile BIOLER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/bioler"));
	public static final ModelFile BOILER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/boiler"));
	public static final ModelFile BOILER_WHEN_HAS_TANK_PART_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/boiler_has_tank_multipart"));
	public static final ModelFile BOILER_TANK_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/boiler_tank"));
	public static final ModelFile CRUCIBLE_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/crucible"));
	public static final ModelFile CRUCIBLE_LIT_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/crucible_lit"));
	public static final ModelFile CRYSTALLIZER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/crystallizer"));
	public static final ModelFile CURRENT_FURNACE_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/current_furnace"));
	public static final ModelFile EXTRACTOR_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/extractor"));
	public static final ModelFile INJECTOR_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/injector"));
	
	public static final ModelFile SOLAR_PANEL_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/solar_panel"));
	
	public static final ModelFile CONDUIT_VINES_PARALLEL_CROSSLINK_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/conduit_vines_parallel_crosslink"));
	
	public static final ModelFile CHANNEL_VINES_CENTER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/channel_vine_center"));
	public static final ModelFile CHANNEL_VINES_BRANCH_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/channel_vine_branch"));
	public static final ModelFile CHANNEL_VINES_BRANCH_PULL_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/channel_vine_branch_pull"));
	public static final ModelFile CHANNEL_VINES_BRANCH_PUSH_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/channel_vine_branch_push"));
	
	// private static final List<Direction> HIGH_DIRECTIONS = ImmutableList.of(Direction.UP, Direction.NORTH, Direction.EAST);
	public static final ModelFile WIRE_BRANCH_HIGH_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/wire_branch_high"));
	public static final ModelFile WIRE_BRANCH_LOW_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/wire_branch_low"));
	public static final ModelFile WIRE_CENTER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/wire_center"));
	public static final ModelFile WIRE_STRAIGHT_VERTICAL_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/wire_straight_vertical"));

	public static final ModelFile INSULATED_WIRE_BRANCH_HIGH_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_wire_branch_high"));
	public static final ModelFile INSULATED_WIRE_BRANCH_LOW_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_wire_branch_low"));
	public static final ModelFile INSULATED_WIRE_CENTER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_wire_center"));
	public static final ModelFile INSULATED_WIRE_STRAIGHT_VERTICAL_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_wire_straight_vertical"));
	
	public static final ModelFile STEEL_TANK = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/tank"));
	public static final ModelFile STEEL_TANK_DRAINING = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/tank_draining"));
	
	public static final ModelFile CREATIVE_CURRENT_SOURCE = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/creative_current_source"));
	public static final ModelFile CREATIVE_INPUT_PLATE = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/creative_input_plate"));
	public static final ModelFile CREATIVE_NEUTRAL_PLATE = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/creative_neutral_plate"));
	public static final ModelFile CREATIVE_OUTPUT_PLATE = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/creative_output_plate"));
	
	
	
	public YATMBlockStateProvider(@NotNull PackOutput output, @NotNull String modid, @NotNull ExistingFileHelper exFileHelper)
	{
		super(Objects.requireNonNull(output), Objects.requireNonNull(modid), Objects.requireNonNull(exFileHelper));
	} // end constructor



	@Override
	protected void registerStatesAndModels()
	{
		this.addRubberSet();
		this.addSoulAfflictedRubberSet();
		
		this.addAdamum();
		this.addAurum();
		this.addBasinOfTears();
		this.addCandlelily();
		this.addCarbum();
		this.addCarcassRoot();
		this.addCuprum();
		this.createFourStageCrop(YATMBlocks.COTTON.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cotton/cotton_germinating"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cotton/cotton_flowering"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cotton/cotton_maturing"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cotton/cotton_mature"));
		this.addFerrum();
		this.addFireEaterLily();
		this.addFolium();
		this.addIceCoral();
		this.addInfernalum();
		this.addLapum();
		this.createPhantasmalShelfFungus(YATMBlocks.PHANTASMAL_SHELF_FUNGUS.get(), YATMItems.PHANTASMAL_SHELF_FUNGUS_ITEM.get());
		this.createBlockWithItem(YATMBlocks.PITCHER_CLUSTER.get(), YATMBlockStateProvider.PITCHER_CLUSTER);
		this.createPrismarineCrystalMossLike(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get(), 
				//new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/moss/prismarine/prismarine_crystal_moss_germinating"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/moss/prismarine/prismarine_crystal_moss_young"), 
				//new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/moss/prismarine/prismarine_crystal_moss_maturing"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/moss/prismarine/prismarine_crystal_moss_mature"));
		this.addRuberum();
		this.addSamaragdum();
		this.addShulkwarts();
		this.addSpiderVine();
		this.addVariegatedCactus();
		this.addVicum();
		
		this.createBlock(YATMBlocks.HANGING_POT_HOOK.get(), YATMBlockStateProvider.HANGING_POT_HOOK_MODEL);
		this.createBlock(YATMBlocks.DEFAULT_HANGING_POT_SUPPORT_CHAINS.get(), YATMBlockStateProvider.DEFAULT_HANGING_POT_SUPPORT_CHAINS_MODEL);
		
		this.addCandleLanterns();
		
		this.createAllBlock(YATMBlocks.FOLIAR_STEEL_ORE.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/foliar_steel_ore"));
		this.createAllBlock(YATMBlocks.DEEPSLATE_FOLIAR_STEEL_ORE.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/deepslate_foliar_steel_ore"));
		this.createAllBlock(YATMBlocks.FOLIAR_STEEL_BLOCK.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/foliar_steel_block"));
		this.createAllBlock(YATMBlocks.RUBBER_BLOCK.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/rubber_block"));
		this.createAllBlock(YATMBlocks.ROOTED_SOUL_SOIL.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/rooted_soul_soil"));
		
		this.createFacingBlock(YATMBlocks.GRAFTING_TABLE.get(), YATMItems.GRAFTING_TABLE_ITEM.get(), YATMBlockStateProvider.GRAFTING_TABLE_MODEL);
		this.addSapCollectors();
		this.createSpinningWheel(YATMBlocks.SPINNING_WHEEL.get(), YATMItems.SPINNING_WHEEL_ITEM.get());
		this.addHeatSinks();
		this.addComputeBlocks();
		this.addBiolers();
		this.addBoilers();
		this.addBoilerTanks();
		this.addCrucibles();
		this.addCrystallizers();
		this.addCurrentFurnaces();
		this.addExtractors();
		this.addInjectors();
		
		this.createAllBlock(YATMBlocks.C_U_F_E_I.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/energy_converter/energy_converter"));
		this.addSolarPanels();		
		
		this.addConduits();
		
		this.addTanks();
		this.addChannelVines();
		
		this.addFluidBlocks();
		
		this.addCreativeBlocks();
	} // end registerStatesAndModels()

	
	
	private void addRubberSet() 
	{
		ResourceLocation meristemTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_meristem");
		this.createSelfLayeringSapling(YATMBlocks.RUBBER_MERISTEM.get(), YATMItems.RUBBER_MERISTEM_ITEM.get(), "block/rubber_meristem", meristemTexture);
		this.createPottedCross(YATMBlocks.POTTED_RUBBER_MERISTEM.get(), meristemTexture);
		this.createAllBlock(YATMBlocks.RUBBER_LEAVES_YOUNG.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_leaves_young"));
		this.createAllBlock(YATMBlocks.RUBBER_LEAVES_FLOWERING.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_leaves_flowering"));
		this.createAllBlock(YATMBlocks.RUBBER_LEAVES_OLD.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_leaves_old"));
		this.createRootsBlock(YATMBlocks.RUBBER_ROOTS.get(), YATMItems.RUBBER_ROOTS_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_roots_side"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_roots_ends"), true);
		ResourceLocation rubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side");
		ResourceLocation rubberLogTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_top");
		ResourceLocation strippedRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_stripped");
		ResourceLocation partiallyStrippedRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped");
		//ResourceLocation partiallyStrippedRubberLogSideTextureMirrored = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped_mirrored");
		ResourceLocation partiallyStrippedLeakingRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped_latex");
		//ResourceLocation partiallyStrippedLeakingRubberLogSideTextureMirrored = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped_latex_mirrored");
		ResourceLocation strippedRubberLogTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_top_stripped");
		this.createPillar(YATMBlocks.RUBBER_LOG.get(), rubberLogSideTexture, rubberLogTopTexture, "block/rubber_log");
		
		this.createPillar(YATMBlocks.RUBBER_WOOD.get(), rubberLogSideTexture, rubberLogSideTexture, "block/rubber_wood");
		this.createPillar(YATMBlocks.STRIPPED_RUBBER_LOG.get(), strippedRubberLogSideTexture, strippedRubberLogTopTexture, "block/stripped_rubber_log");
		this.createPillar(YATMBlocks.STRIPPED_RUBBER_WOOD.get(), strippedRubberLogSideTexture, strippedRubberLogSideTexture, "block/stripped_rubber_wood");
		this.createPartiallyStrippedLog(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get(), partiallyStrippedRubberLogSideTexture, partiallyStrippedLeakingRubberLogSideTexture, rubberLogSideTexture, rubberLogTopTexture, "block/partially_stripped_rubber_log", true);
		ResourceLocation rubberPlanksTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_planks");
		this.createAllBlock(YATMBlocks.RUBBER_PLANKS.get(), rubberPlanksTexture, "block/rubber_planks");
		ResourceLocation fancyRubberPlanksTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/fancy_rubber_planks");
		this.createPillar(YATMBlocks.FANCY_RUBBER_PLANKS.get(), fancyRubberPlanksTexture, fancyRubberPlanksTexture, "block/fancy_rubber_planks");
		this.createSlab(YATMBlocks.RUBBER_SLAB.get(), "block/rubber_slab", rubberPlanksTexture, new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/rubber_planks")));
		this.createStair(YATMBlocks.RUBBER_STAIRS.get(), "block/rubber", rubberPlanksTexture);
		this.createFence(YATMBlocks.RUBBER_FENCE.get(), "block/rubber", "item/rubber_fence", rubberPlanksTexture);
		this.createFenceGate(YATMBlocks.RUBBER_FENCE_GATE.get(), "item/rubber_fence_gate", rubberPlanksTexture);
		ResourceLocation rubberDoorTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_door_top");
		ResourceLocation rubberDoorBottomTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_door_bottom");
		this.createDoor(YATMBlocks.RUBBER_DOOR.get(), YATMItems.RUBBER_DOOR_ITEM.get(), rubberDoorBottomTexture, rubberDoorTopTexture);
		this.createTrapDoor(YATMBlocks.RUBBER_TRAPDOOR.get(), "item/rubber_trapdoor", new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_trapdoor_top"));
		this.createPressurePlate(YATMBlocks.RUBBER_PRESSURE_PLATE.get(), "item/rubber_pressure_plate", rubberPlanksTexture);
		this.createButton(YATMBlocks.RUBBER_BUTTON.get(),"item/rubber_button", rubberPlanksTexture);
		
		this.createSign(YATMBlocks.RUBBER_SIGN.get(), rubberPlanksTexture);
		this.createSign(YATMBlocks.RUBBER_WALL_SIGN.get(), rubberPlanksTexture);
		this.createSign(YATMBlocks.RUBBER_HANGING_SIGN.get(), rubberLogSideTexture);
		this.createSign(YATMBlocks.RUBBER_WALL_HANGING_SIGN.get(), rubberLogSideTexture);
		
		
		this.createCarpet(YATMBlocks.LEAF_MULCH.get(), YATMItems.LEAF_MULCH_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/leaf_mulch"));
		// sign
		// the newer and fancier sign
	} // end addRubberSet()
	
	private void addSoulAfflictedRubberSet() 
	{
		ResourceLocation meristemTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_meristem");
		this.createSelfLayeringSapling(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get(), "block/soul_afflicted_rubber_meristem", meristemTexture);
		this.createPottedCross(YATMBlocks.POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM.get(), meristemTexture);
		this.createAllBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_young"));
		this.createAllBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_flowering"));
		this.createAllBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_old"));
		this.createRootsBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_ROOTS.get(), YATMItems.SOUL_AFFLICTED_RUBBER_ROOTS_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_roots_side"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_roots_ends"), true);
		ResourceLocation soulAfflictedRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_log_side");
		ResourceLocation soulAfflictedRubberLogTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_log_top");
		ResourceLocation soulAfflictedStrippedRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_log_side_stripped");
		ResourceLocation soulAfflictedPartiallyStrippedRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_log_side_partially_stripped");
		ResourceLocation soulAfflictedPartiallyStrippedLeakingRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_log_side_partially_stripped_sap");
		ResourceLocation soulAfflictedStrippedRubberLogTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_log_top_stripped");
		this.createPillar(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get(), soulAfflictedRubberLogSideTexture, soulAfflictedRubberLogTopTexture, "block/soul_afflicted_rubber_log");
		this.createPillar(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get(), soulAfflictedRubberLogSideTexture, soulAfflictedRubberLogSideTexture, "block/soul_afflicted_rubber_wood");
		this.createPillar(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get(), soulAfflictedStrippedRubberLogSideTexture, soulAfflictedStrippedRubberLogTopTexture, "block/soul_afflicted_stripped_rubber_log");
		this.createPillar(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get(), soulAfflictedStrippedRubberLogSideTexture, soulAfflictedStrippedRubberLogSideTexture, "block/soul_afflicted_stripped_rubber_wood");
		this.createPartiallyStrippedLog(YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get(), soulAfflictedPartiallyStrippedRubberLogSideTexture, soulAfflictedPartiallyStrippedLeakingRubberLogSideTexture, soulAfflictedRubberLogSideTexture, soulAfflictedRubberLogTopTexture, "block/soul_afflicted_partially_stripped_rubber_log", true);
		ResourceLocation soulAfflictedRubberPlanksTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_planks");
		this.createAllBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_PLANKS.get(), soulAfflictedRubberPlanksTexture, "block/soul_afflicted_rubber_planks");
		ResourceLocation soulAfflictedFancyRubberPlanksTiledTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_fancy_rubber_planks_tiled");
		this.createPillar(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get(), soulAfflictedFancyRubberPlanksTiledTexture, soulAfflictedFancyRubberPlanksTiledTexture, "block/soul_afflicted_fancy_rubber_planks_tiled");
		ResourceLocation soulAfflictedFancyRubberPlanksTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_fancy_rubber_planks");
		this.createPillar(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get(), soulAfflictedFancyRubberPlanksTexture, soulAfflictedFancyRubberPlanksTexture, "block/soul_afflicted_fancy_rubber_planks");
		this.createSlab(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get(), "block/soul_afflicted_rubber_slab", soulAfflictedRubberPlanksTexture, new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/soul_afflicted_rubber_planks")));
		this.createStair(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get(), "block/soul_afflicted_rubber", soulAfflictedRubberPlanksTexture);
		this.createFence(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get(), "block/soul_afflicted_rubber", "item/soul_afflicted_rubber_fence", soulAfflictedRubberPlanksTexture);
		this.createFenceGate(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE_GATE.get(), "item/soul_afflicted_rubber_fence_gate", soulAfflictedRubberPlanksTexture);
		ResourceLocation soulAfflictedRubberDoorTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_door_top");
		ResourceLocation soulAfflictedRubberDoorBottomTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_door_bottom");
		this.createDoor(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get(), YATMItems.SOUL_AFFLICTED_RUBBER_DOOR_ITEM.get(), soulAfflictedRubberDoorBottomTexture, soulAfflictedRubberDoorTopTexture);
		this.createTrapDoor(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get(), "item/soul_afflicted_rubber_trapdoor", new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_trapdoor_top"));
		this.createPressurePlate(YATMBlocks.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get(), "item/soul_afflicted_rubber_pressure_plate", soulAfflictedRubberPlanksTexture);
		this.createButton(YATMBlocks.SOUL_AFFLICTED_RUBBER_BUTTON.get(),"item/soul_afflicted_rubber_button", soulAfflictedRubberPlanksTexture);
		
		this.createSign(YATMBlocks.SOUL_AFFLICTED_RUBBER_SIGN.get(), soulAfflictedRubberPlanksTexture);
		this.createSign(YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_SIGN.get(), soulAfflictedRubberPlanksTexture);
		this.createSign(YATMBlocks.SOUL_AFFLICTED_RUBBER_HANGING_SIGN.get(), soulAfflictedStrippedRubberLogSideTexture);
		this.createSign(YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_HANGING_SIGN.get(), soulAfflictedStrippedRubberLogSideTexture);
		
		this.createCarpet(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get(), YATMItems.SOUL_AFFLICTED_LEAF_MULCH_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_leaf_mulch"));
	} // end addSoulAfflictedRubberSet()

	private void addAdamum()
	{
		this.createAdamum(YATMBlocks.ADAMUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/adamum/meristem"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/adamum/young"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/adamum/adolescent"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/adamum/old"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/adamum/old_tuber"));
		// this.createStoneSoilPottedCross(YATMBlocks.POTTED_ADAMUM.get(), adolescentTexture);
	} // end addAurum()
	
	private void addAurum()
	{
		ResourceLocation adolescentTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/aurum/adolescent");
		this.createAurumDeminutus(YATMBlocks.AURUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/aurum/fiddle_heads"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/aurum/young"), 
				adolescentTexture, 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/aurum/adolescent_double_lower"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/aurum/adolescent_double_higher"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/aurum/mature_double_lower"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/aurum/mature_double_higher"));
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_AURUM.get(), adolescentTexture);
	} // end addAurum()
	
	private void addBasinOfTears()
	{
		this.createFourAgeCross(YATMBlocks.BASIN_OF_TEARS_VEGETATION.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/basin_of_tears/foliage_sprouts"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/basin_of_tears/foliage_young"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/basin_of_tears/foliage_adolescent"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/basin_of_tears/foliage_old"));
	} // end addBasinOfTears()
	
	private void addCandlelily() 
	{
		ResourceLocation texture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/candlelily/candlelily");
		this.createCross(YATMBlocks.CANDLELILY.get(), texture);
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_CANDLELILY.get(), texture);
	} // end addCandlelily()
	
	private void addCarbum() 
	{
		ResourceLocation oldTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carbum/old");
		this.createCarbum(YATMBlocks.CARBUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carbum/meristem"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carbum/young"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carbum/adolescent"),
				oldTexture);
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_CARBUM.get(), oldTexture);
	} // end addCarbum()
	
	private void addCarcassRoot() 
	{
		ResourceLocation youngTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carcass_root/young");
		this.createCarcassRootFoliage(YATMBlocks.CARCASS_ROOT_FOLIAGE.get(), youngTexture, new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carcass_root/old_lower"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carcass_root/old_higher"));
		this.createPottedCross(YATMBlocks.POTTED_CARCASS_ROOT_FOLIAGE.get(), youngTexture);
		this.createCarcassRootRooted(YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get(), YATMItems.CARCASS_ROOT_ROOTED_DIRT_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carcass_root/rooted_dirt_young"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carcass_root/rooted_dirt_old"));
		this.createCarcassRootRooted(YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get(), YATMItems.CARCASS_ROOT_ROOTED_NETHERRACK_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carcass_root/rooted_netherrack_young"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/carcass_root/rooted_netherrack_old"));
		
	} // end addCarcassRoot()
	
	private void addCuprum() 
	{
		ResourceLocation adolescentTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cuprum/adolescent");
		this.createCuprum(YATMBlocks.CUPRUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cuprum/sprout"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cuprum/young"),
				adolescentTexture,
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cuprum/old_lower"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cuprum/old_higher"));
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_CUPRUM.get(), adolescentTexture);
	} // end addCuprum()
	
	private void addFerrum() 
	{
		ResourceLocation oldTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ferrum/old");
		this.createFerrum(YATMBlocks.FERRUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ferrum/young"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ferrum/adolescent"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ferrum/mature"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ferrum/mature_fruiting"),
				oldTexture);
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_FERRUM.get(), oldTexture);
	} // end addFerrum()
	
	private void addFireEaterLily() 
	{
		ResourceLocation oldLitTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/fire_eater_lily/fire_eater_lily_old_lit");
		this.createFireEaterLily(YATMBlocks.FIRE_EATER_LILY.get(), 
				oldLitTexture, 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/fire_eater_lily/fire_eater_lily_old_unlit"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/fire_eater_lily/fire_eater_lily_adolescent_lit"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/fire_eater_lily/fire_eater_lily_adolescent_unlit"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/fire_eater_lily/fire_eater_lily_young_lit"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/fire_eater_lily/fire_eater_lily_young_unlit"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/fire_eater_lily/fire_eater_lily_bulb_lit"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/fire_eater_lily/fire_eater_lily_bulb_unlit"));
		this.createPottedCross(YATMBlocks.POTTED_FIRE_EATER_LILY.get(), oldLitTexture);
	} // end addFireEaterLily()

	private void addFolium()
	{		
		ResourceLocation adolescentTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/folium/adolescent");
		this.createFolium(YATMBlocks.FOLIUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/folium/sprout"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/folium/young"), 
				adolescentTexture, 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/folium/mature_lower"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/folium/mature_higher"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/folium/old_lower"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/folium/old_higher"));
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_FOLIUM.get(), adolescentTexture);
	} // end addFolium()
	
	private void addIceCoral()
	{
		this.createIceCoral(YATMBlocks.ICE_CORAL.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/old"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/adolescent"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/young"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/polyp"));
		
		this.createCross(YATMBlocks.BLEACHED_ICE_CORAL_OLD.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/bleached_old"));
		this.createCross(YATMBlocks.BLEACHED_ICE_CORAL_ADOLESCENT.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/bleached_adolescent"));
		this.createCross(YATMBlocks.BLEACHED_ICE_CORAL_YOUNG.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/bleached_young"));
		this.createCross(YATMBlocks.BLEACHED_ICE_CORAL_POLYP.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/bleached_polyp"));
		this.createPottedCross(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_OLD.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/bleached_old"));
		this.createPottedCross(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_ADOLESCENT.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/bleached_adolescent"));
		this.createPottedCross(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_YOUNG.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/bleached_young"));
		this.createPottedCross(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_POLYP.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ice_coral/bleached_polyp"));
	} // end addIceCoral()
	
	private void addInfernalum() 
	{
		ResourceLocation oldTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/infernalum/old");
		this.createInfernalum(YATMBlocks.INFERNALUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/infernalum/sprouts"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/infernalum/young"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/infernalum/adolescent"),
				oldTexture);
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_INFERNALUM.get(), oldTexture);
	} // end addInfernalum()
	
	//addLapum
	private void addLapum() 
	{
		ResourceLocation oldTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/lapum/old");
		this.createLapum(YATMBlocks.LAPUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/lapum/meristem"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/lapum/young"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/lapum/adolescent"),
				oldTexture);
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_LAPUM.get(), oldTexture);
	} // end addLapum()
	
	private void addRuberum() 
	{
		ResourceLocation oldTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ruberum/old");

		this.createRuberum(YATMBlocks.RUBERUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ruberum/sprout"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ruberum/young"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ruberum/adolescent"),
				oldTexture,
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ruberum/sprout_on"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ruberum/young_on"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ruberum/adolescent_on"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/ruberum/old_on"));
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_RUBERUM.get(), oldTexture);
	} // end addRuberum()
	
	private void addSamaragdum() 
	{
		ResourceLocation oldTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/samaragdum/old");
		ResourceLocation oldCrossTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/samaragdum/old_cross");

		this.createSamaragdum(YATMBlocks.SAMARAGDUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/samaragdum/germinating"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/samaragdum/young"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/samaragdum/adolescent"),
				oldTexture,
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/samaragdum/germinating_cross"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/samaragdum/young_cross"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/samaragdum/adolescent_cross"),
				oldCrossTexture);
		this.createCustomGroundPottedCross(YATMBlocks.POTTED_SAMARAGDUM.get(), oldTexture, oldCrossTexture);
	} // end addSamaragdum()
	
	private void addShulkwarts()
	{
		this.initializeShulkwartCommonModels();
		this.createShulkwart(YATMBlocks.SHULKWART.get(), YATMItems.SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.WHITE_SHULKWART.get(), YATMItems.WHITE_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/white_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.ORANGE_SHULKWART.get(), YATMItems.ORANGE_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/orange_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.MAGENTA_SHULKWART.get(), YATMItems.MAGENTA_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/magenta_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.LIGHT_BLUE_SHULKWART.get(), YATMItems.LIGHT_BLUE_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/light_blue_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.YELLOW_SHULKWART.get(), YATMItems.YELLOW_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/yellow_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.LIME_SHULKWART.get(), YATMItems.LIME_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/lime_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.PINK_SHULKWART.get(), YATMItems.PINK_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/pink_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.GRAY_SHULKWART.get(), YATMItems.GRAY_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/gray_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.LIGHT_GRAY_SHULKWART.get(), YATMItems.LIGHT_GRAY_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/light_gray_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.CYAN_SHULKWART.get(), YATMItems.CYAN_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/cyan_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.PURPLE_SHULKWART.get(), YATMItems.PURPLE_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/purple_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.BLUE_SHULKWART.get(), YATMItems.BLUE_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/blue_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.BROWN_SHULKWART.get(), YATMItems.BROWN_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/brown_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.GREEN_SHULKWART.get(), YATMItems.GREEN_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/green_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.RED_SHULKWART.get(), YATMItems.RED_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/red_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.BLACK_SHULKWART.get(), YATMItems.BLACK_SHULKWART_HORN.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/black_shulkwart_fruit"));
		this.createLichenLike(YATMBlocks.FALLEN_SHULKWART_SPORES.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/fallen_shulkwart_spores"));
	} // end addShulkWarts()
	
	private void addSpiderVine() 
	{
		this.createOnceFruitingCross(YATMBlocks.SPIDER_VINE.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/spider_vine/spider_vine"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/spider_vine/spider_vine_fruiting"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/spider_vine/spider_vine_harvested"));
		this.createCross(YATMBlocks.SPIDER_VINE_MERISTEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/spider_vine/spider_vine_meristem"));
	} // end addSpiderVine()
	
	private void addVariegatedCactus()
	{
		ResourceLocation topTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/variegated_cactus/variegated_cactus_top");
		ResourceLocation sideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/variegated_cactus/variegated_cactus_side");
		this.createCactus(YATMBlocks.VARIEGATED_CACTUS.get(), YATMItems.VARIEGATED_CACTUS_ITEM.get(), 
				topTexture, 
				sideTexture, 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/variegated_cactus/variegated_cactus_bottom"));
		this.createPottedCactus(YATMBlocks.POTTED_VARIEGATED_CACTUS.get(), topTexture, sideTexture);
	} // end addVariegatedCactus()
	
	private void addVicum()
	{
		ResourceLocation oldTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/vicum/old");
		this.createVicum(YATMBlocks.VICUM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/vicum/meristem"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/vicum/young"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/vicum/adolescent"),
				oldTexture);
		this.createStoneSoilPottedCross(YATMBlocks.POTTED_VICUM.get(), oldTexture);
	} // end addVicum()
	
	private void addCandleLanterns() 
	{
		BiConsumer<CandleLanternBlock, String> creator = (b, color) -> 
		YATMBlockStateProvider.this.createCandleLantern(b, 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/candle_lantern/" + color), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/candle_lantern/" + color + "_lit"));
		
		creator.accept(YATMBlocks.CANDLE_LANTERN.get(), "plain");
		creator.accept(YATMBlocks.WHITE_CANDLE_LANTERN.get(), "white");
		creator.accept(YATMBlocks.ORANGE_CANDLE_LANTERN.get(), "orange");
		creator.accept(YATMBlocks.MAGENTA_CANDLE_LANTERN.get(), "magenta");
		creator.accept(YATMBlocks.LIGHT_BLUE_CANDLE_LANTERN.get(), "light_blue");
		creator.accept(YATMBlocks.YELLOW_CANDLE_LANTERN.get(), "yellow");
		creator.accept(YATMBlocks.LIME_CANDLE_LANTERN.get(), "lime");
		creator.accept(YATMBlocks.PINK_CANDLE_LANTERN.get(), "pink");
		creator.accept(YATMBlocks.GRAY_CANDLE_LANTERN.get(), "gray");
		creator.accept(YATMBlocks.LIGHT_GRAY_CANDLE_LANTERN.get(), "light_gray");
		creator.accept(YATMBlocks.CYAN_CANDLE_LANTERN.get(), "cyan");
		creator.accept(YATMBlocks.PURPLE_CANDLE_LANTERN.get(), "purple");
		creator.accept(YATMBlocks.BLUE_CANDLE_LANTERN.get(), "blue");
		creator.accept(YATMBlocks.BROWN_CANDLE_LANTERN.get(), "brown");
		creator.accept(YATMBlocks.GREEN_CANDLE_LANTERN.get(), "green");
		creator.accept(YATMBlocks.RED_CANDLE_LANTERN.get(), "red");
		creator.accept(YATMBlocks.BLACK_CANDLE_LANTERN.get(), "black");
	} // end addCandleLanterns()
	
	private void addHeatSinks() 
	{
		this.createLargeHeatSink(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), YATMItems.LARGE_COPPER_HEAT_SINK_ITEM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/large_copper_heat_sink_one"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/large_copper_heat_sink_two"));

	} // end addHeatSinks()
	
	private void addSapCollectors() 
	{
		this.createBlockWithItem(YATMBlocks.SAP_COLLECTOR.get(), YATMBlockStateProvider.SAP_COLLECTOR_MODEL);
		this.createFilledSapCollector(YATMBlocks.SAP_COLLECTOR_LATEX.get(), new ResourceLocation("gsr_yatm:block/latex_still"));
		this.createFilledSapCollector(YATMBlocks.SAP_COLLECTOR_SOUL_SAP.get(), new ResourceLocation("gsr_yatm:block/soul_sap_still"));
	} // end addSapCollectors()
	
	private void addComputeBlocks() 
	{
		this.createFaceFacingBlock(YATMBlocks.DATA_SCAN_COLLECTOR.get(), YATMItems.DATA_SCAN_COLLECTOR_ITEM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/compute/data_scan_collector/data_scan_collector_face"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/compute/data_scan_collector/data_scan_collector_side"));
	} // end addComputeBlocks()
	
	private void addBiolers() 
	{
//		this.createBioler(YATMBlocks.STEEL_BIOLER.get(), YATMItems.STEEL_BIOLER_ITEM.get(),
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/steel_bioler_side_ports"), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/bioler_port_sides"), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/bioler_bottom"), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/bioler_top"), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/bioler_inside"));
//		
		this.createFacingBlock(YATMBlocks.STEEL_BIOLER.get(), YATMItems.STEEL_BIOLER_ITEM.get(), YATMBlockStateProvider.BIOLER_MODEL);
		
	} // end addBioler()
	
	private void addBoilers() 
	{
//		this.createBoiler(YATMBlocks.STEEL_BOILER.get(), YATMItems.STEEL_BOILER_ITEM.get(), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_face_plate_and_ports"), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_top"), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_port_sides"), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_face_side_off"), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_face_side_lit"),
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_tank_top_and_bottom"), 
//				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_has_tank_multipart_side"));

		String litName = YATMBlockStateProvider.getModelLocationNameFor(YATMBlocks.STEEL_BOILER.get()) + "_lit";
		this.models().getBuilder(litName).parent(YATMBlockStateProvider.BOILER_MODEL).texture("grate", new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/grate_lit"));
		ModelFile litModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, litName));
		this.createLitFacingBlock(YATMBlocks.STEEL_BOILER.get(), YATMItems.STEEL_BOILER_ITEM.get(), YATMBlockStateProvider.BOILER_MODEL, litModel);
	} // end addBoilers()
	
	private void addBoilerTanks() 
	{
		this.createBoilerTank(YATMBlocks.STEEL_BOILER_TANK.get(), YATMItems.STEEL_BOILER_TANK_ITEM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_tank_side"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_tank_side_has_boiler"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_tank_top_and_bottom"));
	} // end addBoilers()
	
	private void addCrucibles() 
	{
		this.createLitFacingBlock(YATMBlocks.STEEL_CRUCIBLE.get(), YATMItems.STEEL_CRUCIBLE_ITEM.get(), YATMBlockStateProvider.CRUCIBLE_MODEL, YATMBlockStateProvider.CRUCIBLE_LIT_MODEL);
		
	} // end addCrucibles()
	
	private void addCrystallizers() 
	{
		this.createFacingBlock(YATMBlocks.STEEL_CRYSTALLIZER.get(), YATMItems.STEEL_CRYSTALLIZER_ITEM.get(), YATMBlockStateProvider.CRYSTALLIZER_MODEL);
		
	} // end addCrystallizers()
	
	private void addCurrentFurnaces() 
	{
		this.createFacingBlock(YATMBlocks.STEEL_CURRENT_FURNACE.get(), YATMItems.STEEL_CURRENT_FURNACE_ITEM.get(), YATMBlockStateProvider.CURRENT_FURNACE_MODEL);
		
	} // end addCurrentFurnaces()
	
	private void addExtractors() 
	{
		this.createFacingBlock(YATMBlocks.STEEL_EXTRACTOR.get(), YATMItems.STEEL_EXTRACTOR_ITEM.get(), YATMBlockStateProvider.EXTRACTOR_MODEL);
		
	} // end addExtractors()
	
	private void addInjectors() 
	{
		this.createFacingBlock(YATMBlocks.STEEL_INJECTOR.get(), YATMItems.STEEL_INJECTOR_ITEM.get(), YATMBlockStateProvider.INJECTOR_MODEL);
		
	} // end addInjectors()
	
	private void addSolarPanels() 
	{
		// TODO, update face textures
		this.createHorizontalFacingTopBlock(YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get(), YATMItems.CRUDE_BATTERY_SOLAR_PANEL_ITEM.get(),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/eight_cu_solar_panel_side"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/crude_solar_panel_top"));
		this.createHorizontalFacingTopBlock(YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get(), YATMItems.ADVANCED_BATTERY_SOLAR_PANEL_ITEM.get(),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/sixtyfour_cu_solar_panel_side"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/advanced_solar_panel_top"));
		this.createHorizontalFacingTopBlock(YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get(), YATMItems.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL_ITEM.get(),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/fivehundredtwelve_cu_solar_panel_side"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/suns_complement_solar_panel_top"));
	
		this.createSolarPanel(YATMBlocks.CRUDE_SOLAR_PANEL.get(), YATMItems.CRUDE_SOLAR_PANEL_ITEM.get(),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/one_cu_solar_panel_side"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/crude_solar_panel_top"));
		this.createSolarPanel(YATMBlocks.ADVANCED_SOLAR_PANEL.get(), YATMItems.ADVANCED_SOLAR_PANEL_ITEM.get(),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/eight_cu_solar_panel_side"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/advanced_solar_panel_top"));
		this.createSolarPanel(YATMBlocks.SUNS_COMPLEMENT_SOLAR_PANEL.get(), YATMItems.SUNS_COMPLEMENT_SOLAR_PANEL_ITEM.get(),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/sixtyfour_cu_solar_panel_side"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/solar_panel/suns_complement_solar_panel_top"));
		
	} // end addSolarPanels()
	
	private void addConduits() 
	{
		this.createConduitVine(YATMBlocks.CONDUIT_VINES.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/conduit_vines"), YATMBlockStateProvider.CONDUIT_VINES_PARALLEL_CROSSLINK_MODEL);
//		
//		this.createWire(YATMBlocks.ONE_CU_WIRE.get(), YATMItems.ONE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/one_cu_wire"));
//		this.createWire(YATMBlocks.EIGHT_CU_WIRE.get(), YATMItems.EIGHT_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/eight_cu_wire"));
//		this.createWire(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/sixtyfour_cu_wire"));
//		this.createWire(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/fivehundredtwelve_cu_wire"));
//		this.createWire(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/fourthousandnintysix_cu_wire"));
//		
//		this.createWire(YATMBlocks.ENAMELED_ONE_CU_WIRE.get(), YATMItems.ENAMELED_ONE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/one_cu_wire"));
//		this.createWire(YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get(), YATMItems.ENAMELED_EIGHT_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/eight_cu_wire"));
//		this.createWire(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get(), YATMItems.ENAMELED_SIXTYFOUR_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/sixtyfour_cu_wire"));
//		this.createWire(YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMItems.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/fivehundredtwelve_cu_wire"));
//		this.createWire(YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), YATMItems.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/fourthousandnintysix_cu_wire"));
//		
//		this.createInsulatedWire(YATMBlocks.INSULATED_ONE_CU_WIRE.get(), YATMItems.INSULATED_ONE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_one_cu_wire"));
//		this.createInsulatedWire(YATMBlocks.INSULATED_EIGHT_CU_WIRE.get(), YATMItems.INSULATED_EIGHT_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_eight_cu_wire"));
//		this.createInsulatedWire(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get(), YATMItems.INSULATED_SIXTYFOUR_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_sixtyfour_cu_wire"));
//		this.createInsulatedWire(YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMItems.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_fivehundredtwelve_cu_wire"));
//		this.createInsulatedWire(YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), YATMItems.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_fourthousandnintysix_cu_wire"));
//		
	} // end addConduits()
	
	private void addTanks() 
	{
		this.createTank(YATMBlocks.STEEL_TANK.get(), YATMBlockStateProvider.STEEL_TANK, YATMBlockStateProvider.STEEL_TANK_DRAINING);
	
	} // end addTanks()
	
	private void addChannelVines() 
	{
		this.createChannelVines(YATMBlocks.CHANNEL_VINES.get(), YATMBlockStateProvider.CHANNEL_VINES_CENTER_MODEL, YATMBlockStateProvider.CHANNEL_VINES_BRANCH_MODEL, YATMBlockStateProvider.CHANNEL_VINES_BRANCH_PULL_MODEL, YATMBlockStateProvider.CHANNEL_VINES_BRANCH_PUSH_MODEL);
	
	} // end addChannelVines()
	
	private void addFluidBlocks() 
	{
		this.createFluidBlock(YATMBlocks.BIO_LIQUID_BLOCK.get());
		this.createFluidBlock(YATMBlocks.CHORUS_LIQUID_BLOCK.get());
		this.createFluidBlock(YATMBlocks.CHORUS_BIO_LIQUID_BLOCK.get());
		this.createFluidBlock(YATMBlocks.ENDER_LIQUID_BLOCK.get());
		this.createFluidBlock(YATMBlocks.ESSENCE_OF_DECAY_LIQUID_BLOCK.get());
		this.createFluidBlock(YATMBlocks.ESSENCE_OF_SOULS_LIQUID_BLOCK.get());
		this.createFluidBlock(YATMBlocks.LATEX_LIQUID_BLOCK.get());
		this.createFluidBlock(YATMBlocks.SOUL_SAP_LIQUID_BLOCK.get());
		this.createFluidBlock(YATMBlocks.SOUL_SYRUP_LIQUID_BLOCK.get());
	} // end addFluidBlocks()
	
	private void addCreativeBlocks() 
	{
		this.createCreativeCurrentSource(YATMBlocks.CREATIVE_CURRENT_SOURCE.get(), YATMBlockStateProvider.CREATIVE_CURRENT_SOURCE, YATMBlockStateProvider.CREATIVE_NEUTRAL_PLATE, YATMBlockStateProvider.CREATIVE_OUTPUT_PLATE);
	} // end addCreativeBlocks()
	
	

	private void createPillar(Block block, ResourceLocation side, ResourceLocation topBottom, String nameForModel)
	{
		this.models().cubeBottomTop(nameForModel, side, topBottom, topBottom);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameForModel));
		this.simpleBlockItem(block, model);
		this.getVariantBuilder(block).forAllStates((bs) -> forPillarAxis(bs, model));
	} // end createLog()

	private void createAllBlock(Block block, ResourceLocation texture, String name)
	{
		this.models().cubeAll(name, texture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.simpleBlockItem(block, model);
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end createAllBlock()

	private void createAllBlock(Block block, ResourceLocation texture)
	{
		String name = getModelLocationNameFor(block);
		this.models().cubeAll(name, texture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.simpleBlockItem(block, model);
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end createAllBlock()
	
	private void createBlock(Block block, ModelFile model)
	{
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[] { new ConfiguredModel(model) });
	} // end createAllBlock()
	
	private void createBlockWithItem(Block block, ModelFile model)
	{
		this.simpleBlockItem(block, model);
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[] { new ConfiguredModel(model) });
	} // end createAllBlock()
	
	private void createHorizontalFacingTopBlock(Block block, Item item, ResourceLocation sideTexture, ResourceLocation topTexture) 
	{
		String name = getModelLocationNameFor(block);
		this.models().cubeBottomTop(name, sideTexture, sideTexture, topTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
		this.getVariantBuilder(block).forAllStates((bs) -> 
		{
			return new ConfiguredModel[] { new ConfiguredModel(model, rotationForDirectionFromNorth(bs.getValue(BatterySolarPanelBlock.FACING)).x, rotationForDirectionFromNorth(bs.getValue(BatterySolarPanelBlock.FACING)).y, false) };
		});
	} // end createHorizontalFacingTopBlock()
	
	private void createSolarPanel(Block block, Item item, ResourceLocation sideTexture, ResourceLocation topTexture) 
	{
		String name = getModelLocationNameFor(block);
		this.models().getBuilder(name).parent(YATMBlockStateProvider.SOLAR_PANEL_MODEL).texture("top", topTexture).texture("side", sideTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
		this.getVariantBuilder(block).forAllStates((bs) -> 
		{
			return new ConfiguredModel[] { new ConfiguredModel(model, rotationForDirectionFromNorth(bs.getValue(BatterySolarPanelBlock.FACING)).x, rotationForDirectionFromNorth(bs.getValue(BatterySolarPanelBlock.FACING)).y, false) };
		});
	} // end createSolarPanel()
	
	private void createFourAgeCross(@NotNull Block block, @NotNull ResourceLocation ageZero, @NotNull ResourceLocation ageOne, @NotNull ResourceLocation ageTwo, @NotNull ResourceLocation ageThree) 
	{
		String name = getModelLocationNameFor(block);
		String nameZero = name + "_zero";
		String nameOne = name + "_one";
		String nameTwo = name + "_two";
		String nameThree = name + "_three";
		this.models().cross(nameZero, ageZero).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameOne, ageOne).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameTwo, ageTwo).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameThree, ageThree).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelZero = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameZero));
		ModelFile modelOne = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOne));
		ModelFile modelTwo = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameTwo));
		ModelFile modelThree = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameThree));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forFourAge(bs, modelZero, modelOne, modelTwo, modelThree));

	} // end createFourAgeCros()
	
	
	private void createFourStageCrop(@NotNull CropBlock block, @NotNull ResourceLocation textureOne, @NotNull ResourceLocation textureTwo, @NotNull ResourceLocation textureThree, @NotNull ResourceLocation textureFour) 
	{
		String name = getModelLocationNameFor(block);
		String nameOne = name + "_one";
		String nameTwo = name + "_two";
		String nameThree = name + "_three";
		String nameFour = name + "_four";
		this.models().getBuilder(nameOne).parent(YATMBlockStateProvider.CROP).texture("crop", textureOne).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameTwo).parent(YATMBlockStateProvider.CROP).texture("crop", textureTwo).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameThree).parent(YATMBlockStateProvider.CROP).texture("crop", textureThree).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameFour).parent(YATMBlockStateProvider.CROP).texture("crop", textureFour).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelOne = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOne));
		ModelFile modelTwo = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameTwo));
		ModelFile modelThree = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameThree));
		ModelFile modelFour = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameFour));
		
		this.getVariantBuilder(block).forAllStates((bs) -> forCrop(bs, modelOne, modelOne, modelTwo, modelTwo, modelThree, modelThree, modelFour, modelFour));
	} // end createCrop()
	
	private void createPrismarineCrystalMossLike(@NotNull PrismarineCrystalMossBlock block, @NotNull ResourceLocation textureOne, @NotNull ResourceLocation textureTwo)//, ResourceLocation textureThree, ResourceLocation textureFour) 
	{
		String name = getModelLocationNameFor(block);
		String nameOne = name + "_one";
		String nameTwo = name + "_two";
		//String nameThree = name + "_three";
		//String nameFour = name + "_four";
		this.models().getBuilder(nameOne).parent(YATMBlockStateProvider.GLOW_LICHEN).texture("glow_lichen", textureOne).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameTwo).parent(YATMBlockStateProvider.GLOW_LICHEN).texture("glow_lichen", textureTwo).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		//this.models().getBuilder(nameThree).parent(GLOW_LICHEN).texture("glow_lichen", textureThree).renderType(CUTOUT_RENDER_TYPE);
		//this.models().getBuilder(nameFour).parent(GLOW_LICHEN).texture("glow_lichen", textureFour).renderType(CUTOUT_RENDER_TYPE);
		ModelFile modelOne = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOne));
		ModelFile modelTwo = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameTwo));
		//ModelFile modelThree = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameThree));
		//ModelFile modelFour = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameFour));
		
		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		PrismarineCrystalMossBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.forEach((d, p) -> forPrismarineCrystalMossLikeFace(d, builder, modelOne, modelTwo));//, modelThree, modelFour));
	} // end createCrop()
	
	private void createCarbum(@NotNull CarbumBlock block, 
			@NotNull ResourceLocation meristemTexture,
			@NotNull ResourceLocation youngTexture, 
			@NotNull ResourceLocation adolescentTexture,
			@NotNull ResourceLocation oldTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameM = name + "_meristem";
		String nameY = name + "_young";
		String nameA = name + "_adolescent";
		String nameO = name + "_old";
		this.models().cross(nameM, meristemTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameA, adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameO, oldTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelM = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameM));
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forEightAge(bs, modelM, modelM, modelY, modelY, modelA, modelA, modelA, modelO));		
	} // end createCarbum()
	
	private void createCarcassRootFoliage(@NotNull CarcassRootFoliageBlock block, @NotNull ResourceLocation youngTexture, @NotNull ResourceLocation oldLowerTexture, @NotNull ResourceLocation oldHigherTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameY = name + "_young";
		String nameOL = name + "_old_lower";
		String nameOH = name + "_old_higher";
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameOL, oldLowerTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameOH, oldHigherTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelOL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOL));
		ModelFile modelOH = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOH));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forCarcassRootFoliage(bs, modelY, modelOL, modelOH));
		
	} // end createCarcassRootFoliage()
		
	private void createCarcassRootRooted(@NotNull CarcassRootRootBlock block, @NotNull Item item, @NotNull ResourceLocation youngTexture, @NotNull ResourceLocation oldTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameY = name + "_young";
		String nameO = name + "_old";
		this.models().cubeAll(nameY, youngTexture);
		this.models().cubeAll(nameO, oldTexture);
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forCarcassRootRooted(bs, modelY, modelO));
		this.simpleBlockItem(block, modelY);
	} // end createCarcassRootRooted()
	
	private void createCuprum(@NotNull CuprumBlock block, 
			@NotNull ResourceLocation sproutTexture, 
			@NotNull ResourceLocation youngTexture,
			@NotNull ResourceLocation adolescentTexture,
			@NotNull ResourceLocation oldLowerTexture,
			@NotNull ResourceLocation oldHigherTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameS = name + "_sprout";
		String nameY = name + "_young";
		String nameA = name + "_adolescent";
		String nameOL = name + "_old_lower";
		String nameOH = name + "_old_higher";
		this.models().cross(nameS, sproutTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameA, adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameOL, oldLowerTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameOH, oldHigherTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelS = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameS));
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelOL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOL));
		ModelFile modelOH = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOH));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forCuprum(bs, modelS, modelY, modelA, modelOL, modelOH));
	} // end createCuprum()
	
	private void createFerrum(@NotNull FerrumBlock block, 
			@NotNull ResourceLocation youngTexture, 
			@NotNull ResourceLocation adolescentTexture,
			@NotNull ResourceLocation matureTexture,
			@NotNull ResourceLocation matureFruitingTexture,
			@NotNull ResourceLocation oldTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameY = name + "_young";
		String nameA = name + "_adolescent";
		String nameM = name + "_mature";
		String nameMF = name + "_mature_fruiting";
		String nameO = name + "_old";
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameA, adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameM, matureTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameMF, matureFruitingTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameO, oldTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelM = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameM));
		ModelFile modelMF = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameMF));
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forFerrum(bs, modelY, modelA, modelM, modelMF, modelO));
	} // end createFerrum()
	
	private void createFireEaterLily(@NotNull FireEaterLilyBlock block, 
			@NotNull ResourceLocation oldLitTexture, 
			@NotNull ResourceLocation oldUnlitTexture,
			@NotNull ResourceLocation adolescentLitTexture,
			@NotNull ResourceLocation adolescentUnlitTexture,
			@NotNull ResourceLocation youngLitTexture,
			@NotNull ResourceLocation youngUnlitTexture,
			@NotNull ResourceLocation bulbLitTexture,
			@NotNull ResourceLocation bulbUnlitTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameOL = name + "_old_lit";
		String nameOU = name + "_old_unlit";
		String nameAL = name + "_adolescent_lit";
		String nameAU = name + "_adolescent_unlit";
		String nameYL = name + "_young_lit";
		String nameYU = name + "_young_unlit";
		String nameBL = name + "_bulb_lit";
		String nameBU = name + "_bulb_unlit";
		this.models().cross(nameOL, oldLitTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameOU, oldUnlitTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameAL, adolescentLitTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameAU, adolescentUnlitTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameYL, youngLitTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameYU, youngUnlitTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameBL, bulbLitTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameBU, bulbUnlitTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelOL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOL));
		ModelFile modelOU = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOU));
		ModelFile modelAL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameAL));
		ModelFile modelAU = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameAU));
		ModelFile modelYL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameYL));
		ModelFile modelYU = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameYU));
		ModelFile modelBL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameBL));
		ModelFile modelBU = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameBU));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forFireEaterLily(bs, modelOL, modelOU, modelAL, modelAU, modelYL, modelYU, modelBL, modelBU));
	} // end createFireEaterLily()

	private void createFolium(@NotNull FoliumBlock block, ResourceLocation sproutTexture, ResourceLocation youngTexture, @NotNull ResourceLocation adolescentTexture, @NotNull ResourceLocation matureLowerTexture, @NotNull ResourceLocation matureHigherTexture, @NotNull ResourceLocation oldLowerTexture, @NotNull ResourceLocation oldHigherTexture)
	{
		String name = getModelLocationNameFor(block);			
		String sName = name + "_sprout";
		String yName = name + "_young";
		String aName = name + "_adolescent";
		String mLName = name + "_mature_lower";
		String mHName = name + "_mature_higher";
		String oLName = name + "_old_lower";
		String oHName = name + "_old_higher";
		
		this.models().getBuilder(sName).parent(YATMBlockStateProvider.CROSS).texture("cross", sproutTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(yName).parent(YATMBlockStateProvider.CROSS).texture("cross", youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(aName).parent(YATMBlockStateProvider.CROSS).texture("cross", adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(mLName).parent(YATMBlockStateProvider.CROSS).texture("cross", matureLowerTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(mHName).parent(YATMBlockStateProvider.CROSS).texture("cross", matureHigherTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(oLName).parent(YATMBlockStateProvider.CROSS).texture("cross", oldLowerTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(oHName).parent(YATMBlockStateProvider.CROSS).texture("cross", oldHigherTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		ModelFile sModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, sName));
		ModelFile yModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, yName));
		ModelFile aModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, aName));
		ModelFile mLModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, mLName));
		ModelFile mHModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, mHName));
		ModelFile oLModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, oLName));
		ModelFile oHModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, oHName));
		this.getVariantBuilder(block).forAllStates((bs) -> forAurumLike(bs, sModel, yModel, aModel, mLModel, mHModel, oLModel, oHModel));
	} // end createFolium()
	
	private void createIceCoral(@NotNull IceCoralBlock block, 
			@NotNull ResourceLocation oldTexture, 
			@NotNull ResourceLocation adolescentTexture,
			@NotNull ResourceLocation youngTexture,
			@NotNull ResourceLocation polypTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameO = name + "_old";
		String nameA = name + "_adolescent";
		String nameY = name + "_young";
		String nameP = name + "_polyp";
		this.models().cross(nameO, oldTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameA, adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameP, polypTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelP = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameP));
		this.getVariantBuilder(block).forAllStatesExcept((bs) -> YATMBlockStateProvider.forEightAge(bs, modelP, modelP, modelY, modelY, modelA, modelA, modelA, modelO), IceCoralBlock.WATERLOGGED);
	} // end createIceCoral()
	
	private void createInfernalum(@NotNull InfernalumBlock block, 
			@NotNull ResourceLocation sproutsTexture, 
			@NotNull ResourceLocation youngTexture,
			@NotNull ResourceLocation adolescentTexture,
			@NotNull ResourceLocation oldTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameS = name + "_sprouts";
		String nameY = name + "_young";
		String nameA = name + "_adolescent";
		String nameO = name + "_old";
		this.models().cross(nameS, sproutsTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameA, adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameO, oldTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelS = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameS));
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forEightAge(bs, modelS, modelS, modelY, modelY, modelA, modelA, modelA, modelO));
	} // end createInfernalum()
	
	private void createLapum(@NotNull LapumBlock block, 
			@NotNull ResourceLocation meristemTexture, 
			@NotNull ResourceLocation youngTexture,
			@NotNull ResourceLocation adolescentTexture,
			@NotNull ResourceLocation oldTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameM = name + "_meristem";
		String nameY = name + "_young";
		String nameA = name + "_adolescent";
		String nameO = name + "_old";
		this.models().cross(nameM, meristemTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameA, adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameO, oldTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelM = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameM));
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forEightAge(bs, modelM, modelM, modelY, modelY, modelA, modelA, modelA, modelO));
	} // end createLapum()
	
	private void createRuberum(@NotNull RuberumBlock block, 
			@NotNull ResourceLocation sproutTexture, 
			@NotNull ResourceLocation youngTexture,
			@NotNull ResourceLocation adolescentTexture,
			@NotNull ResourceLocation oldTexture,
			@NotNull ResourceLocation sproutOnTexture, 
			@NotNull ResourceLocation youngOnTexture,
			@NotNull ResourceLocation adolescentOnTexture,
			@NotNull ResourceLocation oldOnTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameS = name + "_sprout";
		String nameY = name + "_young";
		String nameA = name + "_adolescent";
		String nameO = name + "_old";
		String nameSO = name + "_sprout_on";
		String nameYO = name + "_young_on";
		String nameAO = name + "_adolescent_on";
		String nameOO = name + "_old_on";
		this.models().cross(nameS, sproutTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameA, adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameO, oldTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameSO, sproutOnTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameYO, youngOnTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameAO, adolescentOnTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameOO, oldOnTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelS = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameS));
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		ModelFile modelSO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameSO));
		ModelFile modelYO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameYO));
		ModelFile modelAO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameAO));
		ModelFile modelOO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOO));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forRuberum(bs, modelS, modelY, modelA, modelO, modelSO, modelYO, modelAO, modelOO));
	} // end createRuberum()
	
	private void createSamaragdum(@NotNull SamaragdumBlock block, 
			@NotNull ResourceLocation germinatingTexture, 
			@NotNull ResourceLocation youngTexture,
			@NotNull ResourceLocation adolescentTexture,
			@NotNull ResourceLocation oldTexture,
			@NotNull ResourceLocation germinatingCrossTexture, 
			@NotNull ResourceLocation youngCrossTexture,
			@NotNull ResourceLocation adolescentCrossTexture,
			@NotNull ResourceLocation oldCrossTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameG = name + "_germinating";
		String nameY = name + "_young";
		String nameA = name + "_adolescent";
		String nameO = name + "_old";
		//this.models().getBuilder(nameG).parent(YATMBlockStateProvider.GLOW_LICHEN).texture("glow_lichen", germinatingTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameG).parent(YATMBlockStateProvider.PATCH_CROSS_MODEL).texture("patch", germinatingTexture).texture("cross", germinatingCrossTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameY).parent(YATMBlockStateProvider.PATCH_CROSS_MODEL).texture("patch", youngTexture).texture("cross", youngCrossTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameA).parent(YATMBlockStateProvider.PATCH_CROSS_MODEL).texture("patch", adolescentTexture).texture("cross", adolescentCrossTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameO).parent(YATMBlockStateProvider.PATCH_CROSS_MODEL).texture("patch", oldTexture).texture("cross", oldCrossTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelG = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameG));
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forEightAge(bs, modelG, modelG, modelY, modelY, modelA, modelA, modelA, modelO));
	} // end createSamaragdum()
	
	private void createCustomGroundPottedCross(Block block, ResourceLocation ground, ResourceLocation texture)
	{
		String name = getModelLocationNameFor(block);			
		this.models().getBuilder(name).parent(YATMBlockStateProvider.FLOWER_POT_CROSS).texture("dirt", ground).texture("plant", texture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[] { new ConfiguredModel(model) });
	} // end createCustomGroundPottedCross()
	
	private void initializeShulkwartCommonModels() 
	{
		String name = "block/shulkwart_fruit";
		String nameOne = name + "_one";
		String nameTwo = name + "_two";
		String nameThree = name + "_three";
		String textureLocation = "block/plant/shulkwart/shulkwart_fruit";
		String textureLocationOne = textureLocation + "_one";
		String textureLocationTwo = textureLocation + "_two";
		String textureLocationThree = textureLocation + "_three";
		ResourceLocation locationOne = new ResourceLocation(YetAnotherTechMod.MODID, textureLocationOne);
		ResourceLocation locationTwo = new ResourceLocation(YetAnotherTechMod.MODID, textureLocationTwo);
		ResourceLocation locationThree = new ResourceLocation(YetAnotherTechMod.MODID, textureLocationThree);
		this.models().cross(nameOne, locationOne).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameTwo, locationTwo).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameThree, locationThree).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.m_shulkwartFruitOneModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOne));
		this.m_shulkwartFruitTwoModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameTwo));
		this.m_shulkwartFruitThreeModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameThree));
	} // end initializeShulkwartCommonModels()
	
	private void createShulkwart(Block block, Item horn, ResourceLocation finalTexture) 
	{
		String name = getModelLocationNameFor(block) + "_grown";
		this.models().cross(name, finalTexture).renderType(CUTOUT_RENDER_TYPE);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((bs) -> forCrop(bs, SHULKWART_SPORE_MODEL, SHULKWARK_CRYPTIC_MODEL, SHULKWARK_CRYPTIC_MODEL, SHULKWARK_CRYPTIC_MODEL, this.m_shulkwartFruitOneModel, this.m_shulkwartFruitTwoModel, this.m_shulkwartFruitThreeModel, model));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(horn).toString()).parent(SHULKWART_HORN).texture("0", finalTexture);

	} // end createShulkwart()
	
	private void createVicum(@NotNull VicumBlock block, 
			@NotNull ResourceLocation meristemTexture,
			@NotNull ResourceLocation youngTexture, 
			@NotNull ResourceLocation adolescentTexture,
			@NotNull ResourceLocation oldTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String nameM = name + "_meristem";
		String nameY = name + "_young";
		String nameA = name + "_adolescent";
		String nameO = name + "_old";
		this.models().cross(nameM, meristemTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameA, adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameO, oldTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelM = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameM));
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forEightAge(bs, modelM, modelM, modelY, modelY, modelA, modelA, modelA, modelO));		
	} // end createCarbum()
	
	private void createLichenLike(Block block, ResourceLocation texture) 
	{
		String name = getModelLocationNameFor(block);
		this.models().getBuilder(name).parent(GLOW_LICHEN).texture("glow_lichen", texture).renderType(CUTOUT_RENDER_TYPE);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(model, 90, 0, false)});
	} // end createLichenLike()
	
	private void createOnceFruitingCross(Block block, ResourceLocation textureImmature, ResourceLocation textureFruiting, ResourceLocation textureHarvested) 
	{
		String name = getModelLocationNameFor(block);
		String nameIm = name + "_immature";
		String nameFr = name + "_fruiting";
		String nameHa = name + "_harvested";
		this.models().cross(nameIm, textureImmature).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameFr, textureFruiting).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		this.models().cross(nameHa, textureHarvested).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile modelIm = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameIm));
		ModelFile modelFr = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameFr));
		ModelFile modelHa = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameHa));
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forOnceFruiting(bs, modelIm, modelFr, modelHa));
		//YATMBlockStateProperties.ONCE_FRUITING_STAGE
	}
	
	private void createCross(Block block, ResourceLocation texture) 
	{
		String name = getModelLocationNameFor(block);
		this.models().cross(name, texture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(model)});
	} // end createCross
	
	private void createSpinningWheel(SpinningWheelBlock block, Item item) 
	{
		// String name = getModelLocationNameFor(block);
		//this.models().getBuilder(name).parent(SPINNING_WHEEL_MODEL);//.texture("top", topTexture).texture("side", sideTexture);
		//ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(YATMBlockStateProvider.SPINNING_WHEEL_MODEL);
		this.getVariantBuilder(block).forAllStates((bs) -> 
		{
			return new ConfiguredModel[] { new ConfiguredModel(YATMBlockStateProvider.SPINNING_WHEEL_MODEL, rotationForDirectionFromNorth(bs.getValue(SpinningWheelBlock.FACING)).x, rotationForDirectionFromNorth(bs.getValue(SpinningWheelBlock.FACING)).y, false) };
		});
	} // end createSolarPanel()
	
	@SuppressWarnings("unused")
	private void createEndsBlock(Block block, Item item, ResourceLocation sideTexture, ResourceLocation endTexture, boolean cutOut)
	{
		String name = getModelLocationNameFor(block);
		if(cutOut) 
		{
			this.models().cubeBottomTop(name, sideTexture, endTexture, endTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		}
		else
		{
			this.models().cubeBottomTop(name, sideTexture, endTexture, endTexture);
		}
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		
		if (item != null)
		{
			this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
		}
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end createAllBlock()

	private void createRootsBlock(Block block, Item item, ResourceLocation sideTexture, ResourceLocation endTexture, boolean cutOut)
	{
		String name = getModelLocationNameFor(block);
		if(cutOut) 
		{
			//this.models().cubeBottomTop(name, sideTexture, endTexture, endTexture)
			this.models().getBuilder(name).parent(YATMBlockStateProvider.MANGROVE_ROOTS).texture("side", sideTexture).texture("top", endTexture)
			.renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		}
		else
		{
			this.models().getBuilder(name).parent(MANGROVE_ROOTS).texture("side", sideTexture).texture("top", endTexture);
		}
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		
		if (item != null)
		{
			this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
		}
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end createAllBlock()

	private void createSelfLayeringSapling(SelfLayeringSaplingBlock block, Item item, String nameForModel, ResourceLocation texture) 
	{
		this.models().cross(nameForModel, texture).renderType(CUTOUT_RENDER_TYPE);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameForModel));
		this.getVariantBuilder(block).forAllStates((bs) -> forSelfLayeringSapling(bs, model));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString())
        .parent(DEFAULT_ITEM_MODEL_PARENT)
        .texture("layer0", texture);
	} // end createSelfLayerinSapling
	
	private void createPottedCross(Block block, ResourceLocation texture) 
	{
		String name = getModelLocationNameFor(block);			
		this.models().getBuilder(name).parent(YATMBlockStateProvider.FLOWER_POT_CROSS).texture("plant", texture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end addPottedPlant()
	
	private void createStoneSoilPottedCross(Block block, ResourceLocation texture)
	{
		String name = getModelLocationNameFor(block);			
		this.models().getBuilder(name).parent(YATMBlockStateProvider.FLOWER_POT_CROSS).texture("dirt", STONE_TEXTURE).texture("plant", texture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end createStoneSoilPottedCross()
	
	private void createPottedCactus(Block block, ResourceLocation topTexture, ResourceLocation sideTexture) 
	{
		String name = getModelLocationNameFor(block);			
		this.models().getBuilder(name).parent(YATMBlockStateProvider.POTTED_CACTUS).texture("cactus_top", topTexture).texture("cactus", sideTexture); // .renderType(CUTOUT_RENDER_TYPE);		
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end addPottedPlant()
	
	// createAdamum
	public void createAdamum(AdamumBlock block, 
			ResourceLocation meristemTexture, 
			ResourceLocation youngTexture, 
			ResourceLocation adolescentTexture, 
			ResourceLocation oldTexture, 
			ResourceLocation oldTuberTexture)
	{
		String name = getModelLocationNameFor(block);			
		String nameM = name + "_meristem";
		String nameY = name + "_young";
		String nameA = name + "_adolescent";
		String nameO = name + "_old";
		String nameOT = name + "_old_tuber";
		this.models().cross(nameM, meristemTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().cross(nameY, youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().cross(nameA, adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().cross(nameO, oldTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().cross(nameOT, oldTuberTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		ModelFile modelM = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameM));
		ModelFile modelY = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameY));
		ModelFile modelA = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameA));
		ModelFile modelO = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameO));
		ModelFile modelOT = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOT));
		this.getVariantBuilder(block).forAllStates((bs) -> forAdamum(bs, modelM, modelY, modelA, modelO, modelOT));
	} // end createAdamum()
	
	public void createAurumDeminutus(AurumBlock block, ResourceLocation fiddleHeadTexture, ResourceLocation youngTexture, ResourceLocation adolescentTexture, ResourceLocation adolescentDoubleLowerTexture, ResourceLocation adolescentDoubleHigherTexture, ResourceLocation matureDoubleLowerTexture, ResourceLocation matureDoubleHigherTexture)
	{
		String name = getModelLocationNameFor(block);			
		String fName = name + "_fiddle_head";
		String yName = name + "_young";
		String aName = name + "_adolescent";
		String aDLName = name + "_adolescent_double_lower";
		String aDHName = name + "_adolescent_double_higher";
		String mDLName = name + "_mature_double_lower";
		String mDHName = name + "_mature_double_higher";
		this.models().getBuilder(fName).parent(YATMBlockStateProvider.CROSS).texture("cross", fiddleHeadTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(yName).parent(YATMBlockStateProvider.CROSS).texture("cross", youngTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(aName).parent(YATMBlockStateProvider.CROSS).texture("cross", adolescentTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(aDLName).parent(YATMBlockStateProvider.CROSS).texture("cross", adolescentDoubleLowerTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(aDHName).parent(YATMBlockStateProvider.CROSS).texture("cross", adolescentDoubleHigherTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(mDLName).parent(YATMBlockStateProvider.CROSS).texture("cross", matureDoubleLowerTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		this.models().getBuilder(mDHName).parent(YATMBlockStateProvider.CROSS).texture("cross", matureDoubleHigherTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);		
		ModelFile fModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, fName));
		ModelFile yModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, yName));
		ModelFile aModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, aName));
		ModelFile aDLModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, aDLName));
		ModelFile aDHModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, aDHName));
		ModelFile mDLModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, mDLName));
		ModelFile mDHModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, mDHName));
		this.getVariantBuilder(block).forAllStates((bs) -> forAurumLike(bs, fModel, yModel, aModel, aDLModel, aDHModel, mDLModel, mDHModel));
	} // end createAurumDeminutus()
	
	private void createPhantasmalShelfFungus(PhantasmalShelfFungiBlock block, Item item) 
	{
		this.getVariantBuilder(block).forAllStates((bs) -> YATMBlockStateProvider.forShelfFungi(bs, YATMBlockStateProvider.PHANTASMAL_SHELF_FUNGUS_SMALL, YATMBlockStateProvider.PHANTASMAL_MEDIUM_SHELF_FUNGUS, YATMBlockStateProvider.PHANTASMAL_LARGE_SHELF_FUNGUS));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(YATMBlockStateProvider.PHANTASMAL_SHELF_FUNGUS_SMALL);
	} // end createShelfFungus()
	
	private void createCactus(@NotNull Block block, @Nullable Item item, @NotNull ResourceLocation topTexture, @NotNull ResourceLocation sideTexture, @NotNull ResourceLocation bottomTexture) 
	{		
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);	
		this.models().getBuilder(name).parent(YATMBlockStateProvider.CACTUS_MODEL).texture("top", topTexture).texture("side", sideTexture).texture("bottom", bottomTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);	
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(model)});
		if(item != null) 
		{
			this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
		}
	} // end createCactus()
	
	private void createStair(StairBlock block, String name, ResourceLocation texture) 
	{ 
		this.stairsBlock(block, name, texture, texture, texture);
		this.simpleBlockItem(block, new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name + "_stairs")));
		//facing, half, shape, waterlogged
		//west, bottom, straight, false
	} // end createStair()
	
	private void createSlab(Block block, String name, ResourceLocation texture, ModelFile doubleSlab) 
	{
		String bottomName = name + "_bottom";
		String topName = name + "_top";
		this.models().slab(bottomName, texture, texture, texture);
		this.models().slabTop(topName, texture, texture, texture);
		ModelFile bottomSlab = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, bottomName));
		ModelFile topSlab = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, topName));
		this.slabBlock((SlabBlock)block, bottomSlab, topSlab, doubleSlab);		
		this.simpleBlockItem(block, bottomSlab);
		//this.getVariantBuilder(block).forAllStates((bs) -> forSlab(bs, bottomSlab, topSlab, doubleSlab));
	} // end createSlab()
	
	private void createFence(Block block, String name, String itemName, ResourceLocation texture) 
	{
		this.fenceBlock((FenceBlock)block, name, texture);
		this.itemModels().fenceInventory(itemName, texture);
	} // end createFence()
	
	private void createFenceGate(Block block, String itemName, ResourceLocation texture) 
	{
		this.fenceGateBlock((FenceGateBlock)block, texture);
		this.itemModels().fenceGate(itemName, texture);
	} // end createFenceGate()
	
	private void createDoor(Block block, Item item, ResourceLocation bottomTexture, ResourceLocation topTexture) 
	{
		this.doorBlock((DoorBlock)block, bottomTexture, topTexture);
		this.itemModels().basicItem(item);
	} // end createDoor()
	
	private void createTrapDoor(Block block, String itemName, ResourceLocation texture) 
	{
		this.trapdoorBlock((TrapDoorBlock)block, texture, true);
		this.itemModels().trapdoorBottom(itemName, texture);
	} // end createTrapDoor()
	
	private void createPressurePlate(Block block, String itemName, ResourceLocation texture) 
	{
		this.pressurePlateBlock((PressurePlateBlock)block, texture);
		this.itemModels().pressurePlate(itemName, texture);
	} // end createPressurePlate()
	
 	private void createButton(Block block, String itemName, ResourceLocation texture) 
	{
		this.buttonBlock((ButtonBlock)block, texture);
		this.itemModels().buttonInventory(itemName, texture);
	} // end createButton()
	
 	private void createSign(Block block, ResourceLocation particleTexture) 
 	{
 		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
 		this.models().sign(name, particleTexture);
 		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(model)});
 	} // end createSign()
 	
 	
 	
	private void createPartiallyStrippedLog(@NotNull Block block, @NotNull ResourceLocation strippedSide, @NotNull ResourceLocation strippedSideLeaking, @NotNull ResourceLocation plainSide, @NotNull ResourceLocation topBottom, @NotNull String name, boolean leakingForItem)
	{
		String dryName = name + "_dry";
		String leakingName = name + "_leaking";
		this.models().cube(dryName, topBottom, topBottom, strippedSide, plainSide, plainSide, plainSide).texture("particle", strippedSide);
		this.models().cube(leakingName, topBottom, topBottom, strippedSideLeaking, plainSide, plainSide, plainSide).texture("particle", strippedSideLeaking);
		ModelFile dryModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, dryName));
		ModelFile leakingModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, leakingName));
		this.simpleBlockItem(block, leakingForItem ? leakingModel : dryModel);
		this.getVariantBuilder(block).forAllStates((bs) -> forPartiallyStrippedBlock(bs, dryModel, leakingModel));
	} // end createPartiallyStrippedLog
	
	private void createCarpet(@NotNull Block block, @Nullable Item item, @NotNull ResourceLocation texture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);			
		this.models().getBuilder(name).parent(YATMBlockStateProvider.CARPET).texture("wool", texture);		
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		if (item != null)
		{
			this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
		}
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end createCarpet()
	
	
	
	private void createFilledSapCollector(@NotNull Block block, @NotNull ResourceLocation fluidTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		this.models().getBuilder(name).parent(YATMBlockStateProvider.SAP_COLLECTOR_FILLED_MODEL).texture("fluid", fluidTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(model)});
	} // end createFilledSapCollector()
	
	private void createCandleLantern(@NotNull CandleLanternBlock block,@NotNull ResourceLocation texture, @NotNull ResourceLocation litTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		String lName = name + "_lit";
		String hName = name + "_hanging";
		String lhName = lName + "_hanging";
		
		this.models().getBuilder(name).parent(YATMBlockStateProvider.TEMPLATE_CANDLE_LANTERN_MODEL).texture("lantern", texture);
		this.models().getBuilder(lName).parent(YATMBlockStateProvider.TEMPLATE_CANDLE_LANTERN_MODEL).texture("lantern", litTexture);
		this.models().getBuilder(hName).parent(YATMBlockStateProvider.TEMPLATE_CANDLE_LANTERN_HANGING_MODEL).texture("lantern", texture);
		this.models().getBuilder(lhName).parent(YATMBlockStateProvider.TEMPLATE_CANDLE_LANTERN_HANGING_MODEL).texture("lantern", litTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		ModelFile lModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, lName));
		ModelFile hModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, hName));
		ModelFile lhModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, lhName));
		
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] 
				{new ConfiguredModel(bs.getValue(CandleLanternBlock.LIT) 
					? bs.getValue(CandleLanternBlock.HANGING)
						? lhModel
						: lModel
					: bs.getValue(CandleLanternBlock.HANGING)
						? hModel
						: model
						)
				});
	} // end createCandleLantern()
	
	private void createLargeHeatSink(@NotNull HeatSinkBlock block, @NotNull Item item, @NotNull ResourceLocation oneTexture, @NotNull ResourceLocation twoTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		this.models().getBuilder(name).parent(YATMBlockStateProvider.LARGE_HEAT_SINK_MODEL).texture("1", oneTexture).texture("2", twoTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(model, rotationForDirectionFromUp(bs.getValue(HeatSinkBlock.FACING)).x, rotationForDirectionFromUp(bs.getValue(HeatSinkBlock.FACING)).y, false)});
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
	} // end createHeatSink()
	
	private void createFacingBlock(@NotNull Block block, @NotNull Item item, @NotNull ModelFile model) 
	{
		this.getVariantBuilder(block).forAllStates((bs) ->  new ConfiguredModel[] { new ConfiguredModel(model, rotationForDirectionFromNorth(bs.getValue(YATMBlockStateProperties.FACING_HORIZONTAL)).x, rotationForDirectionFromNorth(bs.getValue(YATMBlockStateProperties.FACING_HORIZONTAL)).y, false) });
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
	} // end createFacingBlock()
	
	private void createLitFacingBlock(@NotNull Block block, @NotNull Item item, @NotNull ModelFile unlitModel, @NotNull ModelFile litModel) 
	{
		this.getVariantBuilder(block).forAllStates(
				(bs) ->  new ConfiguredModel[] 
						{ 
								new ConfiguredModel(bs.getValue(YATMBlockStateProperties.LIT) ? litModel : unlitModel, rotationForDirectionFromNorth(bs.getValue(YATMBlockStateProperties.FACING_HORIZONTAL)).x, rotationForDirectionFromNorth(bs.getValue(YATMBlockStateProperties.FACING_HORIZONTAL)).y, false) 
								});
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(unlitModel);
	} // end createFacingBlock()	
	
	private void createFaceFacingBlock(@NotNull Block block, @NotNull Item item, @NotNull ResourceLocation faceTexture, @NotNull ResourceLocation sideTexture)
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		this.models().cube(name, sideTexture, sideTexture, faceTexture, sideTexture, sideTexture, sideTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		
		this.getVariantBuilder(block).forAllStates((bs) -> 
		{
			return new ConfiguredModel[] { new ConfiguredModel(model, rotationForDirectionFromNorth(bs.getValue(BiolerBlock.FACING)).x, rotationForDirectionFromNorth(bs.getValue(BiolerBlock.FACING)).y, false) };
		});
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
	} // end createFaceFacingBlock()
	
	
	@SuppressWarnings("unused")
	private void createBioler(@NotNull BiolerBlock block, @NotNull Item item, @NotNull ResourceLocation portTexture, @NotNull ResourceLocation sideTexture, @NotNull ResourceLocation bottomTexture, @NotNull ResourceLocation topTexture, @NotNull ResourceLocation insideTexture) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		this.models().getBuilder(name).parent(YATMBlockStateProvider.BIOLER_MODEL)
		.texture("0", portTexture)
		.texture("1", sideTexture)
		.texture("2", bottomTexture)
		.texture("3", topTexture)
		.texture("4", insideTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] { new ConfiguredModel(model, rotationForDirectionFromNorth(bs.getValue(BiolerBlock.FACING)).x, rotationForDirectionFromNorth(bs.getValue(BiolerBlock.FACING)).y, false) });
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
	} // end createBioler()
	
	@SuppressWarnings("unused")
	private void createBoiler(@NotNull BoilerBlock block, @NotNull Item item, @NotNull ResourceLocation facePlateAndPortsTexture, @NotNull ResourceLocation topTexture, @NotNull ResourceLocation sidesTexture, @NotNull ResourceLocation faceTexture, @NotNull ResourceLocation litFaceTexture, ResourceLocation hasTankTopTexture, ResourceLocation hasTankPartSideTexture)
	{
		String baseName = YATMBlockStateProvider.getModelLocationNameFor(block);
		String litName = baseName + "_lit";
		String hasTankPartName = baseName + "_has_tank_part";
		this.models().getBuilder(baseName).parent(YATMBlockStateProvider.BOILER_MODEL).texture("0", facePlateAndPortsTexture).texture("1", topTexture).texture("2", sidesTexture).texture("3", faceTexture);
		this.models().getBuilder(litName).parent(YATMBlockStateProvider.BOILER_MODEL).texture("0", facePlateAndPortsTexture).texture("1", topTexture).texture("2", sidesTexture).texture("3", litFaceTexture);
		this.models().getBuilder(hasTankPartName).parent(YATMBlockStateProvider.BOILER_WHEN_HAS_TANK_PART_MODEL).texture("0", hasTankTopTexture).texture("1", hasTankPartSideTexture);
		ModelFile unlitModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, baseName));
		ModelFile litModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, litName));
		ModelFile hasTankPartModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, hasTankPartName));
		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		Direction.Plane.HORIZONTAL.forEach((dir) ->
				builder.part()
				.modelFile(unlitModel)
				.rotationY(YATMBlockStateProvider.rotationForDirectionFromNorth(dir).y)
				.addModel()
				.condition(BoilerBlock.FACING, dir)
				.condition(BoilerBlock.LIT, false)
		);
		Direction.Plane.HORIZONTAL.forEach((dir) -> 
				builder.part()
				.modelFile(litModel)
				.rotationY(YATMBlockStateProvider.rotationForDirectionFromNorth(dir).y)
				.addModel()
				.condition(BoilerBlock.FACING, dir)
				.condition(BoilerBlock.LIT, true)
		);
		builder.part().modelFile(hasTankPartModel).addModel().condition(BoilerBlock.HAS_TANK, true);
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(unlitModel);
	} // end createBoiler()
	
	private void createBoilerTank(@NotNull BoilerTankBlock block, @NotNull Item item, @NotNull ResourceLocation sideTexture, @NotNull ResourceLocation hasBoilerSideTexture, @NotNull ResourceLocation endsTexture)
	{
		String baseName = YATMBlockStateProvider.getModelLocationNameFor(block);
		String hasBoilerName = baseName + "_has_boiler";
		this.models().getBuilder(baseName).parent(YATMBlockStateProvider.BOILER_TANK_MODEL).texture("0", endsTexture).texture("1", sideTexture);
		this.models().getBuilder(hasBoilerName).parent(YATMBlockStateProvider.BOILER_TANK_MODEL).texture("0", endsTexture).texture("1", hasBoilerSideTexture);
		ModelFile unpairedModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, baseName));
		ModelFile pairedModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, hasBoilerName));
		this.getVariantBuilder(block)
		.partialState().with(BoilerTankBlock.HAS_BOILER, false).addModels(new ConfiguredModel(unpairedModel))
		.partialState().with(BoilerTankBlock.HAS_BOILER, true).addModels(new ConfiguredModel(pairedModel));

		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(unpairedModel);
	} // end createBoilerTank()
	
	
	private void createConduitVine(@NotNull Block block, @NotNull ResourceLocation faceTexture, @NotNull ModelFile parallelCrosslink) 
	{
		String faceName = YATMBlockStateProvider.getModelLocationNameFor(block) + "_face";
		this.models().getBuilder(faceName).parent(YATMBlockStateProvider.GLOW_LICHEN).texture("glow_lichen", faceTexture).renderType(YATMBlockStateProvider.CUTOUT_RENDER_TYPE);
		ModelFile faceModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, faceName));
		
		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		IConduit.DIRECTION_PROPERTIES_BY_DIRECTION.forEach(new BiConsumer<>() 
		{
			@Override
			public void accept(Direction dir, BooleanProperty val)
			{
				Vector2i rot = YATMBlockStateProvider.rotationForDirectionFromNorth(dir);
				builder.part()
				.modelFile(faceModel)
				.rotationX(rot.x)
				.rotationY(rot.y)
				.uvLock(false)
				.addModel()
				.condition(val, true);
				
			} // end accept()			
		} // end anonymous type
		);
		List.of(Direction.Axis.values()).forEach(new Consumer<>() 
		{
			@Override
			public void accept(Direction.Axis axis)
			{
				Vector2i rot = YATMBlockStateProvider.rotationForDirectionFromUp(DirectionUtil.positiveDirectionOnAxis(axis));
				PartBuilder partBuilder = builder.part()
				.modelFile(parallelCrosslink)
				.rotationX(rot.x)
				.rotationY(rot.y)
				.uvLock(false)
				.addModel();
				List.of(Direction.values()).forEach((d) -> partBuilder.condition(
						FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(d), 
						d.getAxis() == axis));
				
			} // end accept()	
		} // end anonymous type
		);
	} // end createConduitVines
	
	private void createTank(@NotNull Block block, @NotNull ModelFile tankModel, @NotNull ModelFile tankDrainingModel) 
	{
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(bs.getValue(YATMBlockStateProperties.DRAINING) ? tankDrainingModel : tankModel)});
		this.simpleBlockItem(block, tankModel);
	} // end createTank()
	
	private void createChannelVines(@NotNull Block block, @NotNull ModelFile center, @NotNull ModelFile branch, @NotNull ModelFile branchPull, @NotNull ModelFile branchPush)
	{
		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		builder.part().modelFile(center).addModel().end();
		YATMBlockStateProperties.BRANCHES_BY_DIRECTION.forEach(new BiConsumer<>() 
		{
			@Override
			public void accept(Direction dir, EnumProperty<AttachmentState> val)
			{
				for(AttachmentState as : AttachmentState.values()) 
				{
					if(as == AttachmentState.NONE) 
					{
						continue;
					}
					
					Vector2i rot = YATMBlockStateProvider.rotationForDirectionFromNorth(dir);
					builder.part()
					.modelFile(switch(as) 
					{
						case NEUTRAL -> branch;
						case PULL -> branchPull;
						case PUSH -> branchPush;
						default -> throw new IllegalArgumentException("Unexpected value of: " + as);
					})
					.rotationX(rot.x)
					.rotationY(rot.y)
					.uvLock(false)
					.addModel()
					.condition(val, as);	
				}
				
				
			} // end accept()			
		} // end anonymous type
		);
	} // end createChannelVines()
	
	private void createFluidBlock(@NotNull LiquidBlock block) 
	{
		String name = YATMBlockStateProvider.getModelLocationNameFor(block);
		this.models().getBuilder(name).texture("particle", name + "_still");
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((b) -> new ConfiguredModel[] {new ConfiguredModel(model)});
	} // end createFluidBlock()
	
	private void createCreativeCurrentSource(@NotNull Block block, @NotNull ModelFile model, @NotNull ModelFile neutralPlate, @NotNull ModelFile outputPlate)
	{
		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		builder.part().modelFile(model).addModel().end();
		CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.forEach(new BiConsumer<>() 
		{
			@Override
			public void accept(Direction dir, EnumProperty<AttachmentState> p)
			{
				for(AttachmentState as : AttachmentState.values()) 
				{
					if(as == AttachmentState.NONE || as == AttachmentState.PULL) 
					{
						continue;
					}
					
					Vector2i rot = YATMBlockStateProvider.rotationForDirectionFromUp(dir);
					builder.part()
					.modelFile(switch(as) 
					{
						case NEUTRAL -> neutralPlate;
						case PUSH -> outputPlate;
						default -> throw new IllegalArgumentException("Unexpected value of: " + as);
					})
					.rotationX(rot.x)
					.rotationY(rot.y)
					.uvLock(false)
					.addModel()
					.condition(p, as);
					}
			} // end accept()			
		} // end anonymous type
		);
	}
	
	
	private static ConfiguredModel[] forFourAge(BlockState bs, ModelFile ageZeroModel, ModelFile ageOneModel, ModelFile ageTwoModel, ModelFile ageThreeModel) 
	{
		ModelFile model = switch(bs.getValue(YATMBlockStateProperties.AGE_FOUR)) 
		{
			case 0 -> ageZeroModel;
			case 1 -> ageOneModel;
			case 2 -> ageTwoModel;
			case 3 -> ageThreeModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(YATMBlockStateProperties.AGE_FOUR));	
		};
		return new ConfiguredModel[] {new ConfiguredModel(model)};
	} // end forOnceFruiting()
	
	private static ConfiguredModel[] forEightAge(BlockState bs, ModelFile ageZeroModel, ModelFile ageOneModel, ModelFile ageTwoModel, ModelFile ageThreeModel, ModelFile ageFourModel, ModelFile ageFiveModel, ModelFile ageSixModel, ModelFile ageSevenModel) 
	{
		ModelFile model = switch(bs.getValue(YATMBlockStateProperties.AGE_EIGHT)) 
		{
			case 0 -> ageZeroModel;
			case 1 -> ageOneModel;
			case 2 -> ageTwoModel;
			case 3 -> ageThreeModel;
			case 4 -> ageFourModel;
			case 5 -> ageFiveModel;
			case 6 -> ageSixModel;
			case 7 -> ageSevenModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(YATMBlockStateProperties.AGE_EIGHT));	
		};
		return new ConfiguredModel[] {new ConfiguredModel(model)};
	} // end forOnceFruiting()
	
	private static ConfiguredModel[] forOnceFruiting(BlockState bs, ModelFile modelImmature, ModelFile modelFruiting, ModelFile modelHarvested) 
	{
		ModelFile model = switch(bs.getValue(YATMBlockStateProperties.ONCE_FRUITING_STAGE)) 
		{
			case IMMATURE -> modelImmature;
			case FRUITING -> modelFruiting;
			case HARVESTED -> modelHarvested;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(YATMBlockStateProperties.ONCE_FRUITING_STAGE));	
		};
		return new ConfiguredModel[] {new ConfiguredModel(model)};
	} // end forOnceFruiting()
	
	private static ConfiguredModel[] forCrop(BlockState bs, ModelFile ageZeroModel, ModelFile ageOneModel, ModelFile ageTwoModel, ModelFile ageThreeModel, ModelFile ageFourModel, ModelFile ageFiveModel, ModelFile ageSixModel, ModelFile ageSevenModel)
	{
		ModelFile model = switch(bs.getValue(CropBlock.AGE)) 
		{
			case 0 -> ageZeroModel;
			case 1 -> ageOneModel;
			case 2 -> ageTwoModel;
			case 3 -> ageThreeModel;
			case 4 -> ageFourModel;
			case 5 -> ageFiveModel;
			case 6 -> ageSixModel;
			case 7 -> ageSevenModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(CropBlock.AGE));
		};
		return new ConfiguredModel[] {new ConfiguredModel(model)};
	} // end forCrop()
	
	
	
	private static void forPrismarineCrystalMossLikeFace(Direction face, MultiPartBlockStateBuilder builder, ModelFile ageZeroModel, ModelFile ageOneModel)//, ModelFile ageTwoModel)//, ModelFile ageThreeModel)
	{
		IntegerProperty faceAge = PrismarineCrystalMossBlock.AGE_PROPERTIES_BY_DIRECTION.get(face);
		for(int i = 0; i <= PrismarineCrystalMossBlock.MAX_AGE; i++) 
		{
			Vector2i rot = YATMBlockStateProvider.rotationForDirectionFromNorth(face);
			builder.part()
			.modelFile(getModelForPrismarineCrystalMossLikeAge(i, ageZeroModel, ageOneModel))
			.rotationX(rot.x)
			.rotationY(rot.y)
			.uvLock(false)
			.addModel()
			.condition(faceAge, i)
			.condition(PrismarineCrystalMossBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(face), true);
		
		}
		
	} // end forPrismarineCrystalMossLike()
	
	private static ModelFile getModelForPrismarineCrystalMossLikeAge(int age, ModelFile ageZeroModel, ModelFile ageOneModel)//, ModelFile ageTwoModel, ModelFile ageThreeModel)
	{
		return switch(age) 
		{
			case 0 -> ageZeroModel;
			case 1 -> ageOneModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + age);
		};
		
		
	} // end forPrismarineCrystalMossLike()
	
	public static ConfiguredModel[] forAdamum(BlockState bs, 
			ModelFile meristemModel, 
			ModelFile youngModel, 
			ModelFile adolescentModel, 
			ModelFile oldModel, 
			ModelFile oldTuberModel) 
	{
		ModelFile model = switch(bs.getValue(AdamumBlock.AGE)) 
		{
			case 0, 1-> meristemModel;
			case 2, 3 -> youngModel;
			case 4, 5, 6 -> adolescentModel;
			case 7 -> bs.getValue(AdamumBlock.HAS_FRUIT) ? oldTuberModel : oldModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(AdamumBlock.AGE));
		};
		return new ConfiguredModel[] {new ConfiguredModel(model)};
	} // end forAdamum()
	
	public static ConfiguredModel[] forAurumLike(BlockState bs, 
			ModelFile zeroModel, 
			ModelFile oneModel, 
			ModelFile twoModel, 
			ModelFile threeLowerModel, ModelFile threeHigherModel, 
			ModelFile fourLowerModel, ModelFile fourHigherModel) 
	{
		boolean isLower = bs.getValue(YATMBlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER;
		ModelFile model = switch(bs.getValue(YATMBlockStateProperties.AGE_FIVE)) 
		{
			case 0 -> zeroModel;
			case 1 -> oneModel;
			case 2 -> twoModel;
			case 3 -> isLower ? threeLowerModel : threeHigherModel;
			case 4 -> isLower ? fourLowerModel : fourHigherModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(YATMBlockStateProperties.AGE_FIVE));
		};
		return new ConfiguredModel[] {new ConfiguredModel(model)};
	} // end forAurumDeminutus()
	
	public static ConfiguredModel[] forCarcassRootFoliage(BlockState bs, ModelFile youngModel, ModelFile oldLowerModel, ModelFile oldHigherModel) 
	{
		boolean isLower = bs.getValue(CarcassRootFoliageBlock.HALF) == DoubleBlockHalf.LOWER;
		ModelFile model = switch(bs.getValue(CarcassRootFoliageBlock.AGE)) 
		{
			case 0 -> youngModel;
			case 1 -> isLower ? oldLowerModel : oldHigherModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(CarcassRootFoliageBlock.AGE));
		};
		return new ConfiguredModel[] {new ConfiguredModel(model)};
	} // end forCarcassRootFoliage()
	
	public static ConfiguredModel[] forCarcassRootRooted(BlockState bs, ModelFile youngModel, ModelFile oldModel) 
	{
		ModelFile model = switch(bs.getValue(CarcassRootRootBlock.AGE)) 
		{
			case 0 -> youngModel;
			case 1 -> oldModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(CarcassRootRootBlock.AGE));
		};
		return new ConfiguredModel[] {new ConfiguredModel(model)};
	} // end forCarcassRootRooted()
	
	public static ConfiguredModel[] forCuprum(BlockState bs, 
			ModelFile sproutModel,
			ModelFile youngModel,
			ModelFile adolescentModel,
			ModelFile oldLowerModel,
			ModelFile oldHigherModel)
	{
		boolean lower = bs.getValue(CuprumBlock.HALF) == DoubleBlockHalf.LOWER;
		return new ConfiguredModel[] 
				{ new ConfiguredModel(
					switch(bs.getValue(CuprumBlock.AGE)) 
					{
						case 0, 1 -> sproutModel;
						case 2, 3 -> youngModel;
						case 4, 5, 6 -> adolescentModel;
						case 7 -> lower ? oldLowerModel : oldHigherModel;
						default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(CuprumBlock.AGE));
					})
				};
	} // end forCuprum()
	
	public static ConfiguredModel[] forFireEaterLily(BlockState bs, 
			ModelFile oldLitModel, 
			ModelFile oldUnlitModel, 
			ModelFile adolescentLitModel, 
			ModelFile adolescentUnlitModel, 
			ModelFile youngLitModel, 
			ModelFile youngUnlitModel, 
			ModelFile bulbLitModel, 
			ModelFile bulbUnlitModel) 
	{
		boolean lit = bs.getValue(FireEaterLilyBlock.LIT);
		//ModelFile model = ;
		return new ConfiguredModel[] 
			{ new ConfiguredModel(
				switch(bs.getValue(FireEaterLilyBlock.AGE)) 
				{
					case 0, 1 -> lit ? bulbLitModel : bulbUnlitModel;
					case 2, 3 -> lit ? youngLitModel : youngUnlitModel;
					case 4, 5, 6 -> lit ? adolescentLitModel : adolescentUnlitModel;
					case 7 -> lit ? oldLitModel : oldUnlitModel;
					default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(FireEaterLilyBlock.AGE));
				})
			};
	} // end forFireEaterLily()
	
	public static ConfiguredModel[] forFerrum(BlockState bs, 
			ModelFile youngModel, 
			ModelFile adolescentModel, 
			ModelFile matureModel, 
			ModelFile matureFruitingModel, 
			ModelFile oldModel) 
	{
		boolean fruit = bs.getValue(FerrumBlock.HAS_FRUIT);
		//ModelFile model = ;
		return new ConfiguredModel[] 
			{ new ConfiguredModel(
				switch(bs.getValue(FerrumBlock.AGE)) 
				{
					case 0, 1 -> youngModel;
					case 2, 3 -> adolescentModel;
					case 4, 5 -> matureModel; 
					case 6 -> fruit ? matureFruitingModel : matureModel;
					case 7 -> oldModel;
					default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(FerrumBlock.AGE));
				})
			};
	} // end forFerrum()
	
	public static ConfiguredModel[] forRuberum(BlockState bs, 
			ModelFile sproutModel,
			ModelFile youngModel, 
			ModelFile adolescentModel,
			ModelFile oldModel,
			ModelFile sproutOnModel,
			ModelFile youngOnModel, 
			ModelFile adolescentOnModel,
			ModelFile oldOnModel) 
	{
		boolean on = bs.getValue(RuberumBlock.LIT);
		//ModelFile model = ;
		return new ConfiguredModel[] 
			{ new ConfiguredModel(
				switch(bs.getValue(RuberumBlock.AGE)) 
				{
					case 0, 1 -> on ? sproutOnModel : sproutModel;
					case 2, 3 -> on ? youngOnModel : youngModel;
					case 4, 5, 6 -> on ? adolescentOnModel : adolescentModel; 
					case 7 -> on ? oldOnModel : oldModel;
					default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(RuberumBlock.AGE));
				})
			};
	} // end forRuberum()
	
	private static ConfiguredModel[] forShelfFungi(BlockState bs, ModelFile smallModel, ModelFile mediumModel, ModelFile largeModel)
	{
		ModelFile model = switch(bs.getValue(CropBlock.AGE)) 
		{
			case 0, 1, 2 -> smallModel;
			case 3, 4, 5, 6 -> mediumModel;
			case 7 -> largeModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(CropBlock.AGE));
		};
		Vector2i rot = rotationForDirectionFromNorth(bs.getValue(PhantasmalShelfFungiBlock.FACING));
		return new ConfiguredModel[] {new ConfiguredModel(model, rot.x, rot.y, false)};
	} // end forShelfFungi()
	
	private static ConfiguredModel[] forSelfLayeringSapling(BlockState bs, ModelFile model) 
	{
		Direction dir = bs.getValue(SelfLayeringSaplingBlock.FACING);
		Vector2i rot = rotationForDirectionFromUp(dir);
		return new ConfiguredModel[] {new ConfiguredModel(model, rot.x, rot.y, false)};
	} // end forSelfLayeringSapling()
	
	public static ConfiguredModel[] forPillarAxis(BlockState bs, ModelFile model)
	{
		switch (bs.getValue(RotatedPillarBlock.AXIS))
		{
			case X:
			{
				return new ConfiguredModel[]
				{ new ConfiguredModel(model, 90, 90, false) };
			}
			case Y:
			{
				return new ConfiguredModel[]
				{ new ConfiguredModel(model) };
			}
			case Z:
			{
				return new ConfiguredModel[]
				{ new ConfiguredModel(model, 90, 0, false) };
			}
		}
		return null;
	} // end forPillarAxis()

	public static ConfiguredModel[] forPartiallyStrippedBlock(BlockState bs, ModelFile dryModel, ModelFile leakingModel)
	{
		Direction.Axis axis = bs.getValue(RotatedPillarBlock.AXIS);
		ModelFile f = bs.getValue(TappedLogBlock.FLOWING) ? leakingModel : dryModel;
		int xRot = axis == Direction.Axis.Y ? 0 : 90;
		int yRot = getRotForYPartiallyStrippedFromFacing(bs.getValue(TappedLogBlock.FACING)) + (axis == Direction.Axis.X ? 90 : 0);
		return new ConfiguredModel[]
		{ new ConfiguredModel(f, xRot, yRot, false) };
	} // end forPillarAxis()
	

	
	public static Vector2i rotationForDirectionFromDown(Direction dir) 
	{
		return switch(dir) 
		{
			case UP -> new Vector2i(180, 0);
			case DOWN -> new Vector2i(0, 0);
			case NORTH -> new Vector2i(270, 0);
			case EAST -> new Vector2i(270, 90);		
			case SOUTH -> new Vector2i(270, 180);
			case WEST -> new Vector2i(270, 270);		
			default -> throw new IllegalArgumentException("the provied direction: " + dir + ", was not expected.");			
		};
	} // end rotationForDirectionFromUp()
	
	public static Vector2i rotationForDirectionFromUp(Direction dir) 
	{
		switch(dir) 
		{
			case UP:
				return new Vector2i(0, 0);
			case DOWN:
				return new Vector2i(180, 0);
			case NORTH:
				return new Vector2i(90, 0);
			case EAST:
				return new Vector2i(90, 90);		
			case SOUTH:
				return new Vector2i(90, 180);
			case WEST:
				return new Vector2i(90, 270);		
			default:
				throw new IllegalArgumentException("the provied direction: " + dir + ", was not expected.");			
		}
	} // end rotationForDirectionFromUp()
	
	public static Vector2i rotationForDirectionFromNorth(Direction dir) 
	{
		switch(dir) 
		{
			case UP:
				return new Vector2i(270, 0);
			case DOWN:
				return new Vector2i(90, 0);
			case NORTH:
				return new Vector2i(0, 0);
			case EAST:
				return new Vector2i(0, 90);		
			case SOUTH:
				return new Vector2i(0, 180);
			case WEST:
				return new Vector2i(0, 270);		
			default:
				throw new IllegalArgumentException("the provied direction: " + dir + ", was not expected.");			
		}
	} // end rotationForDirectionFromNorth()
	
	
	
	private static int getRotForYPartiallyStrippedFromFacing(Direction dir)
	{
		switch (dir)
		{
			case DOWN:
			case NORTH:
				return 0;
			case EAST:
				return 90;
			case SOUTH:
				return 180;
			case WEST:
				return 270;
			case UP:
			default:
				throw new IllegalArgumentException("the provied direction: " + dir + ", it isn't supported for this particular operation");

		}
	} // end getRotForYPartiallyStrippedFromFacing()

	private static String getModelLocationNameFor(Block block) 
	{
		return YATMBlockStateProvider.BLOCK_MODEL_FOLDER + ForgeRegistries.BLOCKS.getKey(block).getPath();
	} // end getModelLocationNameFor
} // end class