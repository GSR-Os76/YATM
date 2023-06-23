package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.block.StrippableRotatedPillarBlock;
import com.gsr.gsr_yatm.block.conduit.ConductorProperties;
import com.gsr.gsr_yatm.block.conduit.CurrentConduitBlock;
import com.gsr.gsr_yatm.block.conduit.FluidConduitBlock;
import com.gsr.gsr_yatm.block.conduit.InsulatedCurrentConduitBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerTankBlock;
import com.gsr.gsr_yatm.block.device.energy_converter.CurrentUnitForgeEnergyInterchangerBlock;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorBlock;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderBlock;
import com.gsr.gsr_yatm.block.device.heat_sink.HeatSinkBlock;
import com.gsr.gsr_yatm.block.plant.tree.StrippedSapLogBlock;
import com.gsr.gsr_yatm.block.plant.tree.rubber_bush.RubberBushSaplingBlock;
import com.gsr.gsr_yatm.block.plant.tree.rubber_bush.SoulAfflictedRubberBushSaplingBlock;
import com.gsr.gsr_yatm.utilities.BlockShapes;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, YetAnotherTechMod.MODID);
	//this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
	
	
	private static final BlockSetType RUBBER_BLOCK_SET_TYPE = new BlockSetType("rubber");
	public static final RegistryObject<Block> RUBBER_MERISTEM = BLOCKS.register("rubber_meristem", () -> new RubberBushSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));	
	public static final RegistryObject<Block> RUBBER_LEAVES_YOUNG = BLOCKS.register("rubber_leaves_young", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> RUBBER_LEAVES_FLOWERING = BLOCKS.register("rubber_leaves_flowering", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	// maybe make persistent?
	public static final RegistryObject<Block> RUBBER_LEAVES_OLD = BLOCKS.register("rubber_leaves_old", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.3F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> RUBBER_LOG = BLOCKS.register("rubber_log", () -> new StrippableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), YATMBlocks::getPartiallyStrippedRubberLog));
	public static final RegistryObject<Block> RUBBER_WOOD = BLOCKS.register("rubber_wood", () -> new StrippableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), YATMBlocks::getStrippedRubberWood));
	public static final RegistryObject<Block> PARTIALLY_STRIPPED_RUBBER_LOG = BLOCKS.register("partially_stripped_rubber_log", () -> new StrippedSapLogBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD).randomTicks(), YATMBlocks::getStrippedRubberLog));
	public static final RegistryObject<Block> STRIPPED_RUBBER_LOG = BLOCKS.register("stripped_rubber_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> STRIPPED_RUBBER_WOOD = BLOCKS.register("stripped_rubber_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_PLANKS = BLOCKS.register("rubber_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> FANCY_RUBBER_PLANKS = BLOCKS.register("fancy_rubber_planks", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_STAIRS = BLOCKS.register("rubber_stairs", () -> new StairBlock(() -> RUBBER_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_SLAB = BLOCKS.register("rubber_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_FENCE = BLOCKS.register("rubber_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_FENCE_GATE = BLOCKS.register("rubber_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
	public static final RegistryObject<Block> RUBBER_DOOR = BLOCKS.register("rubber_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<Block> RUBBER_TRAPDOOR = BLOCKS.register("rubber_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<Block> RUBBER_PRESSURE_PLATE = BLOCKS.register("rubber_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD).noCollission().strength(0.5F), RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<Block> RUBBER_BUTTON = BLOCKS.register("rubber_button", () -> new ButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F), RUBBER_BLOCK_SET_TYPE, 30, true));
	// sign StandingSignBlock WallSignBlock CeilingHangingSignBlock WallHangingSignBlock
	// boat
	// boat with chest
	

	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_MERISTEM = BLOCKS.register("soul_afflicted_rubber_meristem", () -> new SoulAfflictedRubberBushSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG = BLOCKS.register("soul_afflicted_rubber_leaves_young", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING = BLOCKS.register("soul_afflicted_rubber_leaves_flowering", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_LEAVES_OLD = BLOCKS.register("soul_afflicted_rubber_leaves_old", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.3F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_LOG = BLOCKS.register("soul_afflicted_rubber_log", () -> new StrippableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), YATMBlocks::getSoulAfflictedPartiallyStrippedRubberLog));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_WOOD = BLOCKS.register("soul_afflicted_rubber_wood", () -> new StrippableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), YATMBlocks::getSoulAfflictedStrippedRubberWood));
	public static final RegistryObject<Block> SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG = BLOCKS.register("soul_afflicted_partially_stripped_rubber_log", () -> new StrippedSapLogBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD).randomTicks(), YATMBlocks::getSoulAfflictedStrippedRubberLog));
	public static final RegistryObject<Block> SOUL_AFFLICTED_STRIPPED_RUBBER_LOG = BLOCKS.register("soul_afflicted_stripped_rubber_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD = BLOCKS.register("soul_afflicted_stripped_rubber_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_PLANKS = BLOCKS.register("soul_afflicted_rubber_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS = BLOCKS.register("soul_afflicted_fancy_rubber_planks", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED = BLOCKS.register("soul_afflicted_fancy_rubber_planks_tiled", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_STAIRS = BLOCKS.register("soul_afflicted_rubber_stairs", () -> new StairBlock(() -> SOUL_AFFLICTED_RUBBER_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_SLAB = BLOCKS.register("soul_afflicted_rubber_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_FENCE = BLOCKS.register("soul_afflicted_rubber_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_FENCE_GATE = BLOCKS.register("soul_afflicted_rubber_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_DOOR = BLOCKS.register("soul_afflicted_rubber_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_TRAPDOOR = BLOCKS.register("soul_afflicted_rubber_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD), RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE = BLOCKS.register("soul_afflicted_rubber_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD).noCollission().strength(0.5F), RUBBER_BLOCK_SET_TYPE));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_BUTTON = BLOCKS.register("soul_afflicted_rubber_button", () -> new ButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F), RUBBER_BLOCK_SET_TYPE, 30, true));
	
	
	
	//TODO, maybe add some sort of soul stone 
	
	// TODO, properties
	public static final RegistryObject<Block> RUBBER_BLOCK = BLOCKS.register("rubber_block", () -> new Block(BlockBehaviour.Properties.of(Material.FROGLIGHT).strength(2f).sound(SoundType.CANDLE)));
	
	public static final int STEEL_MAXIMUM_TEMPERATURE = 4000;
	public static final int STEEL_DEVICE_TANK_CAPACITY = 4000;
	public static final int STEEL_MAXIMUM_FLUID_TRANSFER_RATE = 16;
	
	// TODO, add in inventory content dropping on break
	public static final RegistryObject<Block> LARGE_COPPER_HEAT_SINK = BLOCKS.register("large_copper_heat_sink", () -> new HeatSinkBlock(BlockBehaviour.Properties.of(Material.STONE)));
	public static final RegistryObject<Block> STEEL_BOILER_TANK = BLOCKS.register("steel_boiler_tank", () -> new BoilerTankBlock(BlockBehaviour.Properties.of(Material.STONE), 256, 32000));
	public static final RegistryObject<Block> STEEL_BOILER = BLOCKS.register("steel_boiler", () -> new BoilerBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.BOILER_SHAPE, STEEL_MAXIMUM_TEMPERATURE, STEEL_DEVICE_TANK_CAPACITY, STEEL_MAXIMUM_FLUID_TRANSFER_RATE));
	public static final RegistryObject<Block> STEEL_EXTRACTOR = BLOCKS.register("steel_extractor", () -> new ExtractorBlock(BlockBehaviour.Properties.of(Material.STONE)));
	public static final RegistryObject<Block> STEEL_EXTRUDER = BLOCKS.register("steel_extruder", () -> new ExtruderBlock(BlockBehaviour.Properties.of(Material.STONE)));
	public static final RegistryObject<Block> C_U_F_E_I = BLOCKS.register("current_unit_forge_energy_interchanger", () -> new CurrentUnitForgeEnergyInterchangerBlock(BlockBehaviour.Properties.of(Material.STONE)));
	
	
	
	public static final BlockBehaviour.Properties WIRE_PROPERTIES = BlockBehaviour.Properties.of(Material.HEAVY_METAL);

	public static final RegistryObject<Block> ONE_CU_WIRE = BLOCKS.register("one_cu_wire", () -> new CurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.ONE_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<Block> EIGHT_CU_WIRE = BLOCKS.register("eight_cu_wire", () -> new CurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.EIGHT_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<Block> SIXTYFOUR_CU_WIRE = BLOCKS.register("sixtyfour_cu_wire", () -> new CurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.SIXTYFOUR_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<Block> FIVEHUNDREDTWELVE_CU_WIRE = BLOCKS.register("fivehundredtwelve_cu_wire", () -> new CurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.FIVEHUNDREDTWELVE_CU_WIRE_CONDUCTOR_PROPERTIES));
	public static final RegistryObject<Block> FOURTHOUSANDNINTYSIX_CU_WIRE = BLOCKS.register("fourthousandnintysix_cu_wire", () -> new CurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.FOURTHOUSANDNINTYSIX_CU_WIRE_CONDUCTOR_PROPERTIES));
	
	public static final RegistryObject<Block> ENAMELED_ONE_CU_WIRE = BLOCKS.register("enameled_one_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.ONE_CU_WIRE_CONDUCTOR_PROPERTIES, () -> ONE_CU_WIRE.get(), () -> YATMItems.WAX_BIT_ITEM.get()));
	public static final RegistryObject<Block> ENAMELED_EIGHT_CU_WIRE = BLOCKS.register("enameled_eight_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.EIGHT_CU_WIRE_CONDUCTOR_PROPERTIES, () -> EIGHT_CU_WIRE.get(), () -> YATMItems.WAX_BIT_ITEM.get()));
	public static final RegistryObject<Block> ENAMELED_SIXTYFOUR_CU_WIRE = BLOCKS.register("enameled_sixtyfour_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.SIXTYFOUR_CU_WIRE_CONDUCTOR_PROPERTIES, () -> SIXTYFOUR_CU_WIRE.get(), () -> YATMItems.WAX_BIT_ITEM.get()));
	public static final RegistryObject<Block> ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE = BLOCKS.register("enameled_fivehundredtwelve_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.FIVEHUNDREDTWELVE_CU_WIRE_CONDUCTOR_PROPERTIES, () -> FIVEHUNDREDTWELVE_CU_WIRE.get(), () -> YATMItems.WAX_BIT_ITEM.get()));
	public static final RegistryObject<Block> ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE = BLOCKS.register("enameled_fourthousandnintysix_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.WIRE_SHAPE, ConductorProperties.FOURTHOUSANDNINTYSIX_CU_WIRE_CONDUCTOR_PROPERTIES, () -> FOURTHOUSANDNINTYSIX_CU_WIRE.get(), () -> YATMItems.WAX_BIT_ITEM.get()));
	
	public static final RegistryObject<Block> INSULATED_ONE_CU_WIRE = BLOCKS.register("insulated_one_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.ONE_CU_WIRE_CONDUCTOR_PROPERTIES, () -> ONE_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP.get()));
	public static final RegistryObject<Block> INSULATED_EIGHT_CU_WIRE = BLOCKS.register("insulated_eight_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.EIGHT_CU_WIRE_CONDUCTOR_PROPERTIES, () -> EIGHT_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP.get()));
	public static final RegistryObject<Block> INSULATED_SIXTYFOUR_CU_WIRE = BLOCKS.register("insulated_sixtyfour_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.SIXTYFOUR_CU_WIRE_CONDUCTOR_PROPERTIES, () -> SIXTYFOUR_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP.get()));
	public static final RegistryObject<Block> INSULATED_FIVEHUNDREDTWELVE_CU_WIRE = BLOCKS.register("insulated_fivehundredtwelve_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.FIVEHUNDREDTWELVE_CU_WIRE_CONDUCTOR_PROPERTIES, () -> FIVEHUNDREDTWELVE_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP.get()));
	public static final RegistryObject<Block> INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE = BLOCKS.register("insulated_fourthousandnintysix_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.FOURTHOUSANDNINTYSIX_CU_WIRE_CONDUCTOR_PROPERTIES, () -> FOURTHOUSANDNINTYSIX_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP.get()));

	public static final RegistryObject<Block> STEEL_FLUID_CONDUIT = BLOCKS.register("steel_fluid_conduit", () -> new FluidConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.STEEL_FLUID_CONDUIT_SHAPE));
	
	
	
	public static final RegistryObject<Block> BIO_LIQUID_BLOCK = BLOCKS.register("bio", () -> new LiquidBlock(YATMFluids.BIO, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> CHORUS_LIQUID_BLOCK = BLOCKS.register("chorus", () -> new LiquidBlock(YATMFluids.CHORUS, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> CHORUS_BIO_LIQUID_BLOCK = BLOCKS.register("chorus_bio", () -> new LiquidBlock(YATMFluids.CHORUS_BIO, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> ENDER_LIQUID_BLOCK = BLOCKS.register("ender", () -> new LiquidBlock(YATMFluids.ENDER, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> ESSENCE_OF_DECAY_LIQUID_BLOCK = BLOCKS.register("essence_of_decay", () -> new LiquidBlock(YATMFluids.ESSENCE_OF_DECAY, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> ESSENCE_OF_SOULS_LIQUID_BLOCK = BLOCKS.register("essence_of_souls", () -> new LiquidBlock(YATMFluids.ESSENCE_OF_SOULS, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> LATEX_LIQUID_BLOCK = BLOCKS.register("latex", () -> new LiquidBlock(YATMFluids.LATEX, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> SOUL_SAP_LIQUID_BLOCK = BLOCKS.register("soul_sap", () -> new LiquidBlock(YATMFluids.SOUL_SAP, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> SOUL_SYRUP_LIQUID_BLOCK = BLOCKS.register("soul_syrup", () -> new LiquidBlock(YATMFluids.SOUL_SYRUP, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));

	public static final RegistryObject<Block> CELESTIAL_LIGHT_LIQUID_BLOCK = BLOCKS.register("celestial_light", () -> new LiquidBlock(YATMFluids.CELESTIAL_LIGHT, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> LUNAR_LIGHT_LIQUID_BLOCK = BLOCKS.register("lunar_light", () -> new LiquidBlock(YATMFluids.LUNAR_LIGHT, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> SOLAR_LIGHT_LIQUID_BLOCK = BLOCKS.register("solar_light", () -> new LiquidBlock(YATMFluids.SOLAR_LIGHT, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));

	// TODO, investigate, at net.minecraftforge.client.ForgeHooksClient.getFluidSprites(ForgeHooksClient.java:505) ~[forge-1.19.4-45.1.0_mapped_official_1.19.4-recomp.jar%23188%23195!/:?] {re:classloading}
	//LiquidBlockRenderer l;
	
	private static Block getPartiallyStrippedRubberLog() 
	{
		return PARTIALLY_STRIPPED_RUBBER_LOG.get();
	} // end getPartiallyStrippedRubberLog()
	
	private static Block getStrippedRubberLog() 
	{
		return STRIPPED_RUBBER_LOG.get();
	} // end getStrippedRubberLog()
	
	private static Block getStrippedRubberWood() 
	{
		return STRIPPED_RUBBER_WOOD.get();
	} // end getStrippedRubberWood()

	
	
	private static Block getSoulAfflictedPartiallyStrippedRubberLog() 
	{
		return SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get();
	} // end getPartiallyStrippedRubberLog()
	
	private static Block getSoulAfflictedStrippedRubberLog() 
	{
		return SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get();
	} // end getStrippedRubberLog()
	
	private static Block getSoulAfflictedStrippedRubberWood() 
	{
		return SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get();
	} // end getStrippedRubberWood()

} // end class
