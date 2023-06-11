package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.block.conduit.ConductorProperties;
import com.gsr.gsr_yatm.block.conduit.CurrentConduitBlock;
import com.gsr.gsr_yatm.block.conduit.FluidConduitBlock;
import com.gsr.gsr_yatm.block.conduit.InsulatedCurrentConduitBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
import com.gsr.gsr_yatm.block.device.boiler.BoilerTankBlock;
import com.gsr.gsr_yatm.block.device.energy_converter.CurrentUnitForgeEnergyInterchangerBlock;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderBlock;
import com.gsr.gsr_yatm.block.device.heat_sink.HeatSinkBlock;
import com.gsr.gsr_yatm.block.plant.tree.StrippedSapLogBlock;
import com.gsr.gsr_yatm.block.plant.tree.rubber_bush.RubberBushSaplingBlock;
import com.gsr.gsr_yatm.block.plant.tree.rubber_bush.SoulAfflictedRubberBushSaplingBlock;
import com.gsr.gsr_yatm.utilities.BlockShapes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, YetAnotherTechMod.MODID);
	//this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
	
	
	
	public static final RegistryObject<Block> RUBBER_MERISTEM = BLOCKS.register("rubber_meristem", () -> new RubberBushSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));	
	public static final RegistryObject<Block> RUBBER_LEAVES_YOUNG = BLOCKS.register("rubber_leaves_young", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> RUBBER_LEAVES_FLOWERING = BLOCKS.register("rubber_leaves_flowering", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	// maybe make persistent?
	public static final RegistryObject<Block> RUBBER_LEAVES_OLD = BLOCKS.register("rubber_leaves_old", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.3F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> RUBBER_LOG = BLOCKS.register("rubber_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_WOOD = BLOCKS.register("rubber_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> STRIPPED_RUBBER_LOG = BLOCKS.register("stripped_rubber_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> PARTIALLY_STRIPPED_RUBBER_LOG = BLOCKS.register("partially_stripped_rubber_log", () -> new StrippedSapLogBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD).air().randomTicks()));
	public static final RegistryObject<Block> STRIPPED_RUBBER_WOOD = BLOCKS.register("stripped_rubber_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_PLANKS = BLOCKS.register("rubber_planks", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_STAIRS = BLOCKS.register("rubber_stairs", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_SLAB = BLOCKS.register("rubber_slab", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_FENCE = BLOCKS.register("rubber_fence", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_FENCE_GATE = BLOCKS.register("rubber_fence_gate", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_DOOR = BLOCKS.register("rubber_door", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_TRAPDOOR = BLOCKS.register("rubber_trapdoor", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_PRESSURE_PLATE = BLOCKS.register("rubber_pressure_plate", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> RUBBER_BUTTON = BLOCKS.register("rubber_button", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));
	// sign
	// boat
	// boat with chest
	

	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_MERISTEM = BLOCKS.register("soul_afflicted_rubber_meristem", () -> new SoulAfflictedRubberBushSaplingBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG = BLOCKS.register("soul_afflicted_rubber_leaves_young", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING = BLOCKS.register("soul_afflicted_rubber_leaves_flowering", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_LEAVES_OLD = BLOCKS.register("soul_afflicted_rubber_leaves_old", () ->  new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.3F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((bs, bg, bp) -> false).isViewBlocking((bs,bg,bp) -> false)));
	public static final RegistryObject<Block> SOUL_AFFLICTED_RUBBER_LOG = BLOCKS.register("soul_afflicted_rubber_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD)));

	
	
	
	public static final RegistryObject<Block> LARGE_COPPER_HEAT_SINK = BLOCKS.register("large_copper_heat_sink", () -> new HeatSinkBlock(BlockBehaviour.Properties.of(Material.STONE)));
	public static final RegistryObject<Block> STEEL_BOILER_TANK = BLOCKS.register("steel_boiler_tank", () -> new BoilerTankBlock(BlockBehaviour.Properties.of(Material.STONE), 256, 32000));
	public static final RegistryObject<Block> STEEL_BOILER = BLOCKS.register("steel_boiler", () -> new BoilerBlock(BlockBehaviour.Properties.of(Material.STONE)));
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
	
	public static final RegistryObject<Block> INSULATED_ONE_CU_WIRE = BLOCKS.register("insulated_one_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.ONE_CU_WIRE_CONDUCTOR_PROPERTIES, () -> ONE_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP_ITEM.get()));
	public static final RegistryObject<Block> INSULATED_EIGHT_CU_WIRE = BLOCKS.register("insulated_eight_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.EIGHT_CU_WIRE_CONDUCTOR_PROPERTIES, () -> EIGHT_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP_ITEM.get()));
	public static final RegistryObject<Block> INSULATED_SIXTYFOUR_CU_WIRE = BLOCKS.register("insulated_sixtyfour_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.SIXTYFOUR_CU_WIRE_CONDUCTOR_PROPERTIES, () -> SIXTYFOUR_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP_ITEM.get()));
	public static final RegistryObject<Block> INSULATED_FIVEHUNDREDTWELVE_CU_WIRE = BLOCKS.register("insulated_fivehundredtwelve_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.FIVEHUNDREDTWELVE_CU_WIRE_CONDUCTOR_PROPERTIES, () -> FIVEHUNDREDTWELVE_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP_ITEM.get()));
	public static final RegistryObject<Block> INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE = BLOCKS.register("insulated_fourthousandnintysix_cu_wire", () -> new InsulatedCurrentConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.INSULATED_WIRE_SHAPE, ConductorProperties.FOURTHOUSANDNINTYSIX_CU_WIRE_CONDUCTOR_PROPERTIES, () -> FOURTHOUSANDNINTYSIX_CU_WIRE.get(), () -> YATMItems.RUBBER_SCRAP_ITEM.get()));

	public static final RegistryObject<Block> STEEL_FLUID_CONDUIT = BLOCKS.register("steel_fluid_conduit", () -> new FluidConduitBlock(BlockBehaviour.Properties.of(Material.STONE), BlockShapes.STEEL_FLUID_CONDUIT_SHAPE));
	
} // end class
