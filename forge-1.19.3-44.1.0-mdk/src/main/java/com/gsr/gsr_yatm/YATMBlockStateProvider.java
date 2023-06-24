package com.gsr.gsr_yatm;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.block.conduit.IConduit;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerTankBlock;
import com.gsr.gsr_yatm.block.device.heat_sink.HeatSinkBlock;
import com.gsr.gsr_yatm.block.plant.tree.SelfLayeringSaplingBlock;
import com.gsr.gsr_yatm.block.plant.tree.StrippedSapLogBlock;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
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
	
	public static final ModelFile DEFAULT_ITEM_MODEL_PARENT = new ModelFile.UncheckedModelFile("item/generated");

	public static final ModelFile LARGE_HEAT_SINK_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/large_heat_sink"));
	
	public static final ModelFile BOILER_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/boiler"));
	public static final ModelFile BOILER_WHEN_HAS_TANK_PART_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/boiler_has_tank_multipart"));
	public static final ModelFile BOILER_TANK_MODEL = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/boiler_tank"));
	
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
		
		this.createAllBlock(YATMBlocks.RUBBER_BLOCK.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/rubber_block"));
		
		this.addHeatSinks();
		this.addBoilers();
		this.addBoilerTanks();
		this.createAllBlock(YATMBlocks.C_U_F_E_I.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/device/energy_converter/energy_converter"));
		
		this.addConduits();
		
		//YetAnotherTechMod.LOGGER.info(getModelLocationNameFor(YATMBlocks.C_U_F_E_I.get()));
	} // end registerStatesAndModels

	private void addRubberSet() 
	{
		this.createSelfLayeringSapling(YATMBlocks.RUBBER_MERISTEM.get(), YATMItems.RUBBER_MERISTEM_ITEM.get(), "block/rubber_meristem", new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_meristem"));
		this.createAllBlock(YATMBlocks.RUBBER_LEAVES_YOUNG.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_leaves_young"));
		this.createAllBlock(YATMBlocks.RUBBER_LEAVES_FLOWERING.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_leaves_flowering"));
		this.createAllBlock(YATMBlocks.RUBBER_LEAVES_OLD.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_leaves_old"));
		
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
		// sign
		// the newer and fancier sign
	} // end addRubberSet()
	
	private void addSoulAfflictedRubberSet() 
	{
		this.createSelfLayeringSapling(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get(), "block/soul_afflicted_rubber_meristem", new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_meristem"));
		this.createAllBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_young"));
		this.createAllBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_flowering"));
		this.createAllBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_old"));
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
	} // end addSoulAfflictedRubberSet()

	private void addHeatSinks() 
	{
		this.createLargeHeatSink(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), YATMItems.LARGE_COPPER_HEAT_SINK_ITEM.get(), 
				new ResourceLocation(YetAnotherTechMod.MODID, "block/large_copper_heat_sink_one"),
				new ResourceLocation(YetAnotherTechMod.MODID, "block/large_copper_heat_sink_two"));

	} // end addHeatSinks()
	
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
	
	private void createSelfLayeringSapling(SelfLayeringSaplingBlock block, Item item, String nameForModel, ResourceLocation texture) 
	{
		this.models().cross(nameForModel, texture).renderType(CUTOUT_RENDER_TYPE);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameForModel));
		this.getVariantBuilder(block).forAllStates((bs) -> forSelfLayeringSapling(bs, model));
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString())
        .parent(DEFAULT_ITEM_MODEL_PARENT)
        .texture("layer0", texture);
	} // end createSelfLayerinSapling
	
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
	
	
	
	private void createLargeHeatSink(HeatSinkBlock block, Item item, ResourceLocation oneTexture, ResourceLocation twoTexture) 
	{
		String modelName = getModelLocationNameFor(block);
		this.models().getBuilder(modelName).parent(LARGE_HEAT_SINK_MODEL).texture("1", oneTexture).texture("2", twoTexture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, modelName));
		this.getVariantBuilder(block).forAllStates((bs) -> new ConfiguredModel[] {new ConfiguredModel(model, rotationForDirectionFromUp(bs.getValue(HeatSinkBlock.FACING)).x, rotationForDirectionFromUp(bs.getValue(HeatSinkBlock.FACING)).y, false)});
		
		this.itemModels().getBuilder(ForgeRegistries.ITEMS.getKey(item).toString()).parent(model);
	} // end createHeatSink()
	
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
	} // end rotationForDirectionFromNorth()
	
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