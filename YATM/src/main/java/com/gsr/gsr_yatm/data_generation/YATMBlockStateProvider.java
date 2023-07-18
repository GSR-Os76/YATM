package com.gsr.gsr_yatm.data_generation;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.YATMBlockStateProperties;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.conduit.IConduit;
import com.gsr.gsr_yatm.block.device.bioler.BiolerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerTankBlock;
import com.gsr.gsr_yatm.block.device.heat_sink.HeatSinkBlock;
import com.gsr.gsr_yatm.block.device.solar.BatterySolarPanelBlock;
import com.gsr.gsr_yatm.block.device.spinning_wheel.SpinningWheelBlock;
import com.gsr.gsr_yatm.block.plant.fungi.PhantasmalShelfFungiBlock;
import com.gsr.gsr_yatm.block.plant.moss.PrismarineCrystalMossBlock;
import com.gsr.gsr_yatm.block.plant.tree.SelfLayeringSaplingBlock;
import com.gsr.gsr_yatm.block.plant.tree.StrippedSapLogBlock;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMItems;
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
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class YATMBlockStateProvider extends BlockStateProvider
{
	public static final String BLOCK_MODEL_FOLDER = "block/";
	
	public static final String CUTOUT_RENDER_TYPE = "cutout";
	
	public static final ModelFile DEFAULT_ITEM_MODEL_PARENT = new ModelFile.UncheckedModelFile("minecraft:item/generated");
	
	public static final ModelFile CARPET = new ModelFile.UncheckedModelFile("minecraft:block/carpet");
	public static final ModelFile CROP_MODEL = new ModelFile.UncheckedModelFile("minecraft:block/crop");
	public static final ModelFile FLOWER_POT_CROSS = new ModelFile.UncheckedModelFile("minecraft:block/flower_pot_cross");
	public static final ModelFile GLOW_LICHEN = new ModelFile.UncheckedModelFile("minecraft:block/glow_lichen");
	public static final ModelFile MANGROVE_ROOTS = new ModelFile.UncheckedModelFile("minecraft:block/mangrove_roots");
	
	
	
	public static final ModelFile SMALL_SHELF_FUNGUS = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/shelf_fungi_small"));
	public static final ModelFile MEDIUM_SHELF_FUNGUS = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/shelf_fungi_medium"));
	public static final ModelFile LARGE_SHELF_FUNGUS = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/shelf_fungi_large"));
	
	public static final ModelFile SHULKWART_SPORE_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/shulkwart_spore"));
	public static final ModelFile SHULKWARK_CRYPTIC_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/shulkwart_cryptic"));
	private ModelFile m_shulkwartFruitOneModel;
	private ModelFile m_shulkwartFruitTwoModel;
	private ModelFile m_shulkwartFruitThreeModel;
	
	public static final ModelFile SPINNING_WHEEL_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/spinning_wheel"));
	
	public static final ModelFile LARGE_HEAT_SINK_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/large_heat_sink"));
	
	public static final ModelFile BIOLER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/bioler"));
	
	public static final ModelFile BOILER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/boiler"));
	public static final ModelFile BOILER_WHEN_HAS_TANK_PART_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/boiler_has_tank_multipart"));
	public static final ModelFile BOILER_TANK_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/boiler_tank"));
	
	public static final ModelFile SOLAR_PANEL_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/solar_panel"));
	
	private static final List<Direction> HIGH_DIRECTIONS = ImmutableList.of(Direction.UP, Direction.NORTH, Direction.EAST);
	public static final ModelFile WIRE_BRANCH_HIGH_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/wire_branch_high"));
	public static final ModelFile WIRE_BRANCH_LOW_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/wire_branch_low"));
	public static final ModelFile WIRE_CENTER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/wire_center"));
	public static final ModelFile WIRE_STRAIGHT_VERTICAL_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/wire_straight_vertical"));

	public static final ModelFile INSULATED_WIRE_BRANCH_HIGH_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_wire_branch_high"));
	public static final ModelFile INSULATED_WIRE_BRANCH_LOW_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_wire_branch_low"));
	public static final ModelFile INSULATED_WIRE_CENTER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_wire_center"));
	public static final ModelFile INSULATED_WIRE_STRAIGHT_VERTICAL_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_wire_straight_vertical"));
	
	
	
	public YATMBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper)
	{
		super(output, modid, exFileHelper);
	} // end constructor



	@Override
	protected void registerStatesAndModels()
	{
		this.addRubberSet();
		this.addSoulAfflictedRubberSet();
		this.createShelfFungus(YATMBlocks.PHANTASMAL_SHELF_FUNGUS.get(), YATMItems.PHANTASMAL_SHELF_FUNGUS_ITEM.get());
		this.createFourStageCrop(YATMBlocks.COTTON.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cotton/cotton_germinating"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cotton/cotton_flowering"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cotton/cotton_maturing"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/cotton/cotton_mature"));
		this.createPrismarineCrystalMossLike(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get(), 
				//new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/moss/prismarine/prismarine_crystal_moss_germinating"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/moss/prismarine/prismarine_crystal_moss_young"), 
				//new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/moss/prismarine/prismarine_crystal_moss_maturing"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/moss/prismarine/prismarine_crystal_moss_mature"));
		this.addShulkwarts();
		this.addSpiderVine();
		
		this.createAllBlock(YATMBlocks.RUBBER_BLOCK.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/rubber_block"));
		this.createAllBlock(YATMBlocks.ROOTED_SOUL_SOIL.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/rooted_soul_soil"));
		
		this.createSpinningWheel(YATMBlocks.SPINNING_WHEEL.get(), YATMItems.SPINNING_WHEEL_ITEM.get());
		this.addHeatSinks();
		this.addComputeBlocks();
		this.addBiolers();
		this.addBoilers();
		this.addBoilerTanks();
		this.createAllBlock(YATMBlocks.C_U_F_E_I.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/energy_converter/energy_converter"));
		this.addSolarPanels();
		
		this.addConduits();
	} // end registerStatesAndModels

	
	
	private void addRubberSet() 
	{
		ResourceLocation meristemTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_meristem");
		this.createSelfLayeringSapling(YATMBlocks.RUBBER_MERISTEM.get(), YATMItems.RUBBER_MERISTEM_ITEM.get(), "block/rubber_meristem", meristemTexture);
		this.addPottedPlant(YATMBlocks.POTTED_RUBBER_MERISTEM.get(), meristemTexture);
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
		this.createCarpet(YATMBlocks.LEAF_MULCH.get(), YATMItems.LEAF_MULCH_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/leaf_mulch"));
		// sign
		// the newer and fancier sign
	} // end addRubberSet()
	
	private void addSoulAfflictedRubberSet() 
	{
		ResourceLocation meristemTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_meristem");
		this.createSelfLayeringSapling(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get(), "block/soul_afflicted_rubber_meristem", meristemTexture);
		this.addPottedPlant(YATMBlocks.POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM.get(), meristemTexture);
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
		this.createCarpet(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get(), YATMItems.SOUL_AFFLICTED_LEAF_MULCH_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_leaf_mulch"));
	} // end addSoulAfflictedRubberSet()

	private void addShulkwarts()
	{
		this.initializeShulkwartCommonModels();
		this.createShulkwart(YATMBlocks.SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.WHITE_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/white_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.ORANGE_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/orange_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.MAGENTA_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/magenta_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.LIGHT_BLUE_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/light_blue_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.YELLOW_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/yellow_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.LIME_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/lime_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.PINK_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/pink_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.GRAY_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/gray_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.LIGHT_GRAY_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/light_gray_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.CYAN_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/cyan_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.PURPLE_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/purple_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.BLUE_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/blue_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.BROWN_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/brown_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.GREEN_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/green_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.RED_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/red_shulkwart_fruit"));
		this.createShulkwart(YATMBlocks.BLACK_SHULKWART.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/shulkwart/black_shulkwart_fruit"));
	} // end addShulkWarts()
	
	private void addSpiderVine() 
	{
		this.createOnceFruitingCross(YATMBlocks.SPIDER_VINE.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/spider_vine/spider_vine"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/spider_vine/spider_vine_fruiting"), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/spider_vine/spider_vine_harvested"));
		this.createCross(YATMBlocks.SPIDER_VINE_MERISTEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/spider_vine/spider_vine_meristem"));
	} // end addSpiderVine()
	
	
	
	private void addHeatSinks() 
	{
		this.createLargeHeatSink(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), YATMItems.LARGE_COPPER_HEAT_SINK_ITEM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/large_copper_heat_sink_one"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/large_copper_heat_sink_two"));

	} // end addHeatSinks()
	
	private void addComputeBlocks() 
	{
		this.createFaceFacingBlock(YATMBlocks.DATA_SCAN_COLLECTOR.get(), YATMItems.DATA_SCAN_COLLECTOR_ITEM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/compute/data_scan_collector/data_scan_collector_face"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/compute/data_scan_collector/data_scan_collector_side"));
	} // end addComputeBlocks()
	
	private void addBiolers() 
	{
		this.createBioler(YATMBlocks.STEEL_BIOLER.get(), YATMItems.STEEL_BIOLER_ITEM.get(),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/steel_bioler_side_ports"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/bioler_port_sides"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/bioler_bottom"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/bioler_top"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/bioler/bioler_inside"));
		
	} // end addBioler()
	
	private void addBoilers() 
	{
		this.createBoiler(YATMBlocks.STEEL_BOILER.get(), YATMItems.STEEL_BOILER_ITEM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_face_plate_and_ports"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_top"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_port_sides"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_face_side_off"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/device/boiler/steel_boiler_face_side_lit"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_tank_top_and_bottom"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_has_tank_multipart_side"));

	} // end addBoilers()
	
	private void addBoilerTanks() 
	{
		this.createBoilerTank(YATMBlocks.STEEL_BOILER_TANK.get(), YATMItems.STEEL_BOILER_TANK_ITEM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_tank_side"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_tank_side_has_boiler"), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/steel_boiler_tank_top_and_bottom"));
	} // end addBoilers()
	
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
		this.createWire(YATMBlocks.ONE_CU_WIRE.get(), YATMItems.ONE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/one_cu_wire"));
		this.createWire(YATMBlocks.EIGHT_CU_WIRE.get(), YATMItems.EIGHT_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/eight_cu_wire"));
		this.createWire(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/sixtyfour_cu_wire"));
		this.createWire(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/fivehundredtwelve_cu_wire"));
		this.createWire(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/fourthousandnintysix_cu_wire"));
		
		this.createWire(YATMBlocks.ENAMELED_ONE_CU_WIRE.get(), YATMItems.ENAMELED_ONE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/one_cu_wire"));
		this.createWire(YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get(), YATMItems.ENAMELED_EIGHT_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/eight_cu_wire"));
		this.createWire(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get(), YATMItems.ENAMELED_SIXTYFOUR_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/sixtyfour_cu_wire"));
		this.createWire(YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMItems.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/fivehundredtwelve_cu_wire"));
		this.createWire(YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), YATMItems.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/fourthousandnintysix_cu_wire"));
		
		this.createInsulatedWire(YATMBlocks.INSULATED_ONE_CU_WIRE.get(), YATMItems.INSULATED_ONE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_one_cu_wire"));
		this.createInsulatedWire(YATMBlocks.INSULATED_EIGHT_CU_WIRE.get(), YATMItems.INSULATED_EIGHT_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_eight_cu_wire"));
		this.createInsulatedWire(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get(), YATMItems.INSULATED_SIXTYFOUR_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_sixtyfour_cu_wire"));
		this.createInsulatedWire(YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMItems.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_fivehundredtwelve_cu_wire"));
		this.createInsulatedWire(YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), YATMItems.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/conduit/current/insulated_fourthousandnintysix_cu_wire"));
		
	} // end addConduits()
	
	
	

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
		this.models().getBuilder(name).parent(SOLAR_PANEL_MODEL).texture("top", topTexture).texture("side", sideTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
		this.getVariantBuilder(block).forAllStates((bs) -> 
		{
			return new ConfiguredModel[] { new ConfiguredModel(model, rotationForDirectionFromNorth(bs.getValue(BatterySolarPanelBlock.FACING)).x, rotationForDirectionFromNorth(bs.getValue(BatterySolarPanelBlock.FACING)).y, false) };
		});
	} // end createSolarPanel()
	
	private void createFourStageCrop(CropBlock block, ResourceLocation textureOne, ResourceLocation textureTwo, ResourceLocation textureThree, ResourceLocation textureFour) 
	{
		String name = getModelLocationNameFor(block);
		String nameOne = name + "_one";
		String nameTwo = name + "_two";
		String nameThree = name + "_three";
		String nameFour = name + "_four";
		this.models().getBuilder(nameOne).parent(CROP_MODEL).texture("crop", textureOne).renderType(CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameTwo).parent(CROP_MODEL).texture("crop", textureTwo).renderType(CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameThree).parent(CROP_MODEL).texture("crop", textureThree).renderType(CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameFour).parent(CROP_MODEL).texture("crop", textureFour).renderType(CUTOUT_RENDER_TYPE);
		ModelFile modelOne = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOne));
		ModelFile modelTwo = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameTwo));
		ModelFile modelThree = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameThree));
		ModelFile modelFour = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameFour));
		
		this.getVariantBuilder(block).forAllStates((bs) -> forCrop(bs, modelOne, modelOne, modelTwo, modelTwo, modelThree, modelThree, modelFour, modelFour));
	} // end createCrop()
	
	private void createPrismarineCrystalMossLike(PrismarineCrystalMossBlock block, ResourceLocation textureOne, ResourceLocation textureTwo)//, ResourceLocation textureThree, ResourceLocation textureFour) 
	{
		String name = getModelLocationNameFor(block);
		String nameOne = name + "_one";
		String nameTwo = name + "_two";
		//String nameThree = name + "_three";
		//String nameFour = name + "_four";
		this.models().getBuilder(nameOne).parent(GLOW_LICHEN).texture("glow_lichen", textureOne).renderType(CUTOUT_RENDER_TYPE);
		this.models().getBuilder(nameTwo).parent(GLOW_LICHEN).texture("glow_lichen", textureTwo).renderType(CUTOUT_RENDER_TYPE);
		//this.models().getBuilder(nameThree).parent(GLOW_LICHEN).texture("glow_lichen", textureThree).renderType(CUTOUT_RENDER_TYPE);
		//this.models().getBuilder(nameFour).parent(GLOW_LICHEN).texture("glow_lichen", textureFour).renderType(CUTOUT_RENDER_TYPE);
		ModelFile modelOne = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOne));
		ModelFile modelTwo = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameTwo));
		//ModelFile modelThree = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameThree));
		//ModelFile modelFour = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameFour));
		
		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		PrismarineCrystalMossBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.forEach((d, p) -> forPrismarineCrystalMossLikeFace(d, builder, modelOne, modelTwo));//, modelThree, modelFour));
	} // end createCrop()
	
	private void initializeShulkwartCommonModels() 
	{
		String name = "block/plant/shulkwart/shulkwart_fruit";
		String nameOne = name + "_one";
		String nameTwo = name + "_two";
		String nameThree = name + "_three";
		ResourceLocation locationOne = new ResourceLocation(YetAnotherTechMod.MODID, nameOne);
		ResourceLocation locationTwo = new ResourceLocation(YetAnotherTechMod.MODID, nameTwo);
		ResourceLocation locationThree = new ResourceLocation(YetAnotherTechMod.MODID, nameThree);
		this.models().cross(nameOne, locationOne).renderType(CUTOUT_RENDER_TYPE);
		this.models().cross(nameTwo, locationTwo).renderType(CUTOUT_RENDER_TYPE);
		this.models().cross(nameThree, locationThree).renderType(CUTOUT_RENDER_TYPE);
		this.m_shulkwartFruitOneModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameOne));
		this.m_shulkwartFruitTwoModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameTwo));
		this.m_shulkwartFruitThreeModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameThree));
	} // end initializeShulkwartCommonModels()
	
	private void createShulkwart(Block block, ResourceLocation finalTexture) 
	{
		String name = getModelLocationNameFor(block) + "_grown";
		this.models().cross(name, finalTexture).renderType(CUTOUT_RENDER_TYPE);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((bs) -> forCrop(bs, SHULKWART_SPORE_MODEL, SHULKWARK_CRYPTIC_MODEL, SHULKWARK_CRYPTIC_MODEL, SHULKWARK_CRYPTIC_MODEL, this.m_shulkwartFruitOneModel, this.m_shulkwartFruitTwoModel, this.m_shulkwartFruitThreeModel, model));
		
	} // end createShulkwart()
	
	private void createOnceFruitingCross(Block block, ResourceLocation textureImmature, ResourceLocation textureFruiting, ResourceLocation textureHarvested) 
	{
		String name = getModelLocationNameFor(block);
		String nameIm = name + "_immature";
		String nameFr = name + "_fruiting";
		String nameHa = name + "_harvested";
		this.models().cross(nameIm, textureImmature).renderType(CUTOUT_RENDER_TYPE);
		this.models().cross(nameFr, textureFruiting).renderType(CUTOUT_RENDER_TYPE);
		this.models().cross(nameHa, textureHarvested).renderType(CUTOUT_RENDER_TYPE);
		ModelFile modelIm = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameIm));
		ModelFile modelFr = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameFr));
		ModelFile modelHa = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameHa));
		this.getVariantBuilder(block).forAllStates((bs) -> forOnceFruiting(bs, modelIm, modelFr, modelHa));
		//YATMBlockStateProperties.ONCE_FRUITING_STAGE
	}
	
	private void createCross(Block block, ResourceLocation texture) 
	{
		String name = getModelLocationNameFor(block);
		this.models().cross(name, texture).renderType(CUTOUT_RENDER_TYPE);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(model)});
	} // end createCross
	
	private void createSpinningWheel(SpinningWheelBlock block, Item item) 
	{
		// String name = getModelLocationNameFor(block);
		//this.models().getBuilder(name).parent(SPINNING_WHEEL_MODEL);//.texture("top", topTexture).texture("side", sideTexture);
		//ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(SPINNING_WHEEL_MODEL);
		this.getVariantBuilder(block).forAllStates((bs) -> 
		{
			return new ConfiguredModel[] { new ConfiguredModel(SPINNING_WHEEL_MODEL, rotationForDirectionFromNorth(bs.getValue(SpinningWheelBlock.FACING)).x, rotationForDirectionFromNorth(bs.getValue(SpinningWheelBlock.FACING)).y, false) };
		});
	} // end createSolarPanel()
	
	@SuppressWarnings("unused")
	private void createEndsBlock(Block block, Item item, ResourceLocation sideTexture, ResourceLocation endTexture, boolean cutOut)
	{
		String name = getModelLocationNameFor(block);
		if(cutOut) 
		{
			this.models().cubeBottomTop(name, sideTexture, endTexture, endTexture).renderType(CUTOUT_RENDER_TYPE);
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
			this.models().getBuilder(name).parent(MANGROVE_ROOTS).texture("side", sideTexture).texture("top", endTexture)
			.renderType(CUTOUT_RENDER_TYPE);
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
	
	private void addPottedPlant(Block block, ResourceLocation texture) 
	{
		String name = getModelLocationNameFor(block);			
		this.models().getBuilder(name).parent(FLOWER_POT_CROSS).texture("plant", texture).renderType(CUTOUT_RENDER_TYPE);		
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end addPottedPlant()
	
	private void createShelfFungus(PhantasmalShelfFungiBlock block, Item item) 
	{
		// TODO, likely change model reducing vertex density significantly
		// TODO, textures
		String name = getModelLocationNameFor(block);	
		String smallName = name + "_small";
		String mediumName = name + "_medium";
		String largeName = name + "_large";
		this.models().getBuilder(smallName).parent(SMALL_SHELF_FUNGUS);
		this.models().getBuilder(mediumName).parent(MEDIUM_SHELF_FUNGUS);	
		this.models().getBuilder(largeName).parent(LARGE_SHELF_FUNGUS);	
		ModelFile smallModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, smallName));
		ModelFile mediumModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, mediumName));
		ModelFile largeModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, largeName));
		this.getVariantBuilder(block).forAllStates((bs) -> forShelfFungi(bs, smallModel, mediumModel, largeModel));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(largeModel);
	}
	
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
	
	private void createPartiallyStrippedLog(@NotNull Block block, ResourceLocation strippedSide, ResourceLocation strippedSideLeaking, ResourceLocation plainSide, ResourceLocation topBottom, String name, boolean leakingForItem)
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
	
	private void createCarpet(Block block, Item item, ResourceLocation texture) 
	{
		String name = getModelLocationNameFor(block);			
		this.models().getBuilder(name).parent(CARPET).texture("wool", texture);		
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		if (item != null)
		{
			this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
		}
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end createCarpet()
	
	
	
	private void createLargeHeatSink(HeatSinkBlock block, Item item, ResourceLocation oneTexture, ResourceLocation twoTexture) 
	{
		String modelName = getModelLocationNameFor(block);
		this.models().getBuilder(modelName).parent(LARGE_HEAT_SINK_MODEL).texture("1", oneTexture).texture("2", twoTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, modelName));
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(model, rotationForDirectionFromUp(bs.getValue(HeatSinkBlock.FACING)).x, rotationForDirectionFromUp(bs.getValue(HeatSinkBlock.FACING)).y, false)});
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
	} // end createHeatSink()
	
	
	
	private void createFaceFacingBlock(Block block, Item item, ResourceLocation faceTexture, ResourceLocation sideTexture)
	{
		String name = getModelLocationNameFor(block);
		this.models().cube(name, sideTexture, sideTexture, faceTexture, sideTexture, sideTexture, sideTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		
		this.getVariantBuilder(block).forAllStates((bs) -> 
		{
			return new ConfiguredModel[] { new ConfiguredModel(model, rotationForDirectionFromNorth(bs.getValue(BiolerBlock.FACING)).x, rotationForDirectionFromNorth(bs.getValue(BiolerBlock.FACING)).y, false) };
		});
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
	} // end createFaceFacingBlock()
	
	
	private void createBioler(BiolerBlock block, Item item, ResourceLocation portTexture, ResourceLocation sideTexture, ResourceLocation bottomTexture, ResourceLocation topTexture, ResourceLocation insideTexture) 
	{
		String name = getModelLocationNameFor(block);
		this.models().getBuilder(name).parent(BIOLER_MODEL)
		.texture("0", portTexture)
		.texture("1", sideTexture)
		.texture("2", bottomTexture)
		.texture("3", topTexture)
		.texture("4", insideTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		
		this.getVariantBuilder(block).forAllStates((bs) -> 
		{
			return new ConfiguredModel[] { new ConfiguredModel(model, rotationForDirectionFromNorth(bs.getValue(BiolerBlock.FACING)).x, rotationForDirectionFromNorth(bs.getValue(BiolerBlock.FACING)).y, false) };
		});
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
	} // end createBioler()
	
	private void createBoiler(BoilerBlock block, Item item, ResourceLocation facePlateAndPortsTexture, ResourceLocation topTexture, ResourceLocation sidesTexture, ResourceLocation faceTexture, ResourceLocation litFaceTexture, ResourceLocation hasTankTopTexture, ResourceLocation hasTankPartSideTexture)
	{
		String baseName = getModelLocationNameFor(block);
		String litName = baseName + "_lit";
		String hasTankPartName = baseName + "_has_tank_part";
		this.models().getBuilder(baseName).parent(BOILER_MODEL).texture("0", facePlateAndPortsTexture).texture("1", topTexture).texture("2", sidesTexture).texture("3", faceTexture);
		this.models().getBuilder(litName).parent(BOILER_MODEL).texture("0", facePlateAndPortsTexture).texture("1", topTexture).texture("2", sidesTexture).texture("3", litFaceTexture);
		this.models().getBuilder(hasTankPartName).parent(BOILER_WHEN_HAS_TANK_PART_MODEL).texture("0", hasTankTopTexture).texture("1", hasTankPartSideTexture);
		ModelFile unlitModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, baseName));
		ModelFile litModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, litName));
		ModelFile hasTankPartModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, hasTankPartName));
		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		Direction.Plane.HORIZONTAL.forEach(new Consumer<>() 
		{
			@Override
			public void accept(Direction dir)
			{
				builder.part()
				.modelFile(unlitModel)
				.rotationY(YATMBlockStateProvider.rotationForDirectionFromNorth(dir).y)
				.addModel()
				.condition(BoilerBlock.FACING, dir)
				.condition(BoilerBlock.LIT, false);
			} // end accept()
		} // end anonymous type
		);
		Direction.Plane.HORIZONTAL.forEach(new Consumer<>() 
		{
			@Override
			public void accept(Direction dir)
			{
				builder.part()
				.modelFile(litModel)
				.rotationY(YATMBlockStateProvider.rotationForDirectionFromNorth(dir).y)
				.addModel()
				.condition(BoilerBlock.FACING, dir)
				.condition(BoilerBlock.LIT, true);
			} // end accept()
		} // end anonymous type
		);
		builder.part().modelFile(hasTankPartModel).addModel().condition(BoilerBlock.HAS_TANK, true);
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(unlitModel);
	} // end createBoiler()
	
	private void createBoilerTank(BoilerTankBlock block, Item item, ResourceLocation sideTexture, ResourceLocation hasBoilerSideTexture, ResourceLocation endsTexture)
	{
		String baseName = getModelLocationNameFor(block);
		String hasBoilerName = baseName + "_has_boiler";
		this.models().getBuilder(baseName).parent(BOILER_TANK_MODEL).texture("0", endsTexture).texture("1", sideTexture);
		this.models().getBuilder(hasBoilerName).parent(BOILER_TANK_MODEL).texture("0", endsTexture).texture("1", hasBoilerSideTexture);
		ModelFile unpairedModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, baseName));
		ModelFile pairedModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, hasBoilerName));
		this.getVariantBuilder(block)
		.partialState().with(BoilerTankBlock.HAS_BOILER, false).addModels(new ConfiguredModel(unpairedModel))
		.partialState().with(BoilerTankBlock.HAS_BOILER, true).addModels(new ConfiguredModel(pairedModel));

		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(unpairedModel);
	} // end createBoilerTank()
	
	
	private void createWire(Block block, Item item, ResourceLocation texture) 
	{
		this.createConduit(block, item, texture, WIRE_BRANCH_HIGH_MODEL, WIRE_BRANCH_LOW_MODEL, WIRE_CENTER_MODEL, WIRE_STRAIGHT_VERTICAL_MODEL);
	} // end addWire()
	
	private void createInsulatedWire(Block block, Item item, ResourceLocation texture) 
	{
		this.createConduit(block, item, texture, INSULATED_WIRE_BRANCH_HIGH_MODEL, INSULATED_WIRE_BRANCH_LOW_MODEL, INSULATED_WIRE_CENTER_MODEL, INSULATED_WIRE_STRAIGHT_VERTICAL_MODEL);
	} // end addInsulatedWire()
	
	private void createConduit(Block block, Item item, ResourceLocation texture, ModelFile highBranch, ModelFile lowBranch, ModelFile center, ModelFile straight) 
	{
		String baseName = getModelLocationNameFor(block);
		String highModelName = baseName + "_branch_high";
		String lowModelName = baseName + "_branch_low";
		String centerModelName = baseName + "_center";
		String straightModelName = baseName + "_straight_vertical";
		this.models().getBuilder(highModelName).parent(highBranch).texture("0", texture);
		this.models().getBuilder(lowModelName).parent(lowBranch).texture("0", texture);
		this.models().getBuilder(centerModelName).parent(center).texture("0", texture);
		this.models().getBuilder(straightModelName).parent(straight).texture("0", texture);
		ModelFile highModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, highModelName));
		ModelFile lowModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, lowModelName));
		ModelFile centerModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, centerModelName));
		ModelFile straightModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, straightModelName));

		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		builder.part().modelFile(centerModel).addModel().end();
		IConduit.DIRECTION_PROPERTIES_BY_DIRECTION.forEach(new BiConsumer<>() 
		{
			@Override
			public void accept(Direction dir, BooleanProperty val)
			{
				boolean high = HIGH_DIRECTIONS.contains(dir);
				Vector2i rot = YATMBlockStateProvider.rotationForDirectionFromNorth(dir);
				builder.part()
				.modelFile(high ? highModel : lowModel)
				.rotationX(rot.x)
				.rotationY(rot.y)
				.uvLock(false)
				.addModel()
				.condition(val, true);
				
			} // end accept()			
		} // end anonymous type
		);
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(straightModel);//.texture("0", texture);
	} // end addConduit()

	
	
	
	
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
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(PhantasmalShelfFungiBlock.GROWTH_STAGE));
		};
		return new ConfiguredModel[] {new ConfiguredModel(model)};
	} // end forCrop()
	
	private static void forPrismarineCrystalMossLikeFace(Direction face, MultiPartBlockStateBuilder builder, ModelFile ageZeroModel, ModelFile ageOneModel)//, ModelFile ageTwoModel)//, ModelFile ageThreeModel)
	{
		IntegerProperty faceAge = PrismarineCrystalMossBlock.AGE_PROPERTIES_BY_DIRECTION.get(face);
		for(int i = 0; i <= PrismarineCrystalMossBlock.MAX_AGE; i++) 
		{
			YetAnotherTechMod.LOGGER.info("building prism sta, age: " + i + ", face: " + face);
			Vector2i rot = YATMBlockStateProvider.rotationForDirectionFromNorth(face);
			builder.part()
			.modelFile(getModelForPrismarineCrystalMossLikeAge(i, ageZeroModel, ageOneModel))//, ageTwoModel, ageThreeModel))
			.rotationX(rot.x)
			.rotationY(rot.y)
			.uvLock(false)
			.addModel()
			.condition(faceAge, i)
			.condition(PrismarineCrystalMossBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(face), true);
		
		}
		
	} // end forPrismarineCrystalMossLike()
	
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
	
	private static ModelFile getModelForPrismarineCrystalMossLikeAge(int age, ModelFile ageZeroModel, ModelFile ageOneModel)//, ModelFile ageTwoModel, ModelFile ageThreeModel)
	{
		return switch(age) 
		{
			case 0 -> ageZeroModel;
			case 1 -> ageOneModel;//ageZeroModel;
			//case 2 -> ageOneModel;
			//case 3 -> ageOneModel;
			//case 4 -> ageTwoModel;
			//case 5 -> ageTwoModel;
			//case 6 -> ageThreeModel;
			//case 7 -> ageThreeModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + age);
		};
		
		
	} // end forPrismarineCrystalMossLike()
	
	private static ConfiguredModel[] forShelfFungi(BlockState bs, ModelFile smallModel, ModelFile mediumModel, ModelFile largeModel)
	{
		ModelFile model = switch(bs.getValue(PhantasmalShelfFungiBlock.GROWTH_STAGE)) 
		{
			case 0 -> smallModel;
			case 1 -> mediumModel;
			case 2 -> largeModel;
			default -> throw new IllegalArgumentException("Unexpected value of: " + bs.getValue(PhantasmalShelfFungiBlock.GROWTH_STAGE));
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
		ModelFile f = bs.getValue(StrippedSapLogBlock.FLOWING) ? leakingModel : dryModel;
		int xRot = axis == Direction.Axis.Y ? 0 : 90;
		int yRot = getRotForYPartiallyStrippedFromFacing(bs.getValue(StrippedSapLogBlock.FACING)) + (axis == Direction.Axis.X ? 90 : 0);
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
		return BLOCK_MODEL_FOLDER + ForgeRegistries.BLOCKS.getKey(block).getPath();
	}
} // end class