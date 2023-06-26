package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.block.conduit.CurrentConduitBlockEntity;
import com.gsr.gsr_yatm.block.conduit.FluidConduitBlockEntity;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlockEntity;
import com.gsr.gsr_yatm.block.device.boiler.BoilerTankBlockEntity;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerBlockEntity;
import com.gsr.gsr_yatm.block.device.energy_converter.CurrentUnitForgeEnergyInterchangerBlockEntity;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorBlockEntity;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMBlockEntityTypes
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<BlockEntityType<BoilerTankBlockEntity>> BOILER_TANK = BLOCK_ENTITY_TYPES.register("boiler_tank_block_entity", () -> BlockEntityType.Builder.of(BoilerTankBlockEntity::new, YATMBlocks.STEEL_BOILER_TANK.get()).build(null));
	public static final RegistryObject<BlockEntityType<BoilerBlockEntity>> BOILER = BLOCK_ENTITY_TYPES.register("boiler_block_entity", () -> BlockEntityType.Builder.of(BoilerBlockEntity::new, YATMBlocks.STEEL_BOILER.get()).build(null));
	public static final RegistryObject<BlockEntityType<CrystallizerBlockEntity>> CRYSTALLIZER = BLOCK_ENTITY_TYPES.register("crystallizer_block_entity", () -> BlockEntityType.Builder.of(CrystallizerBlockEntity::new, YATMBlocks.STEEL_CRYSTALLIZER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ExtruderBlockEntity>> EXTRUDER = BLOCK_ENTITY_TYPES.register("extruder_block_entity", () -> BlockEntityType.Builder.of(ExtruderBlockEntity::new, YATMBlocks.STEEL_EXTRUDER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR = BLOCK_ENTITY_TYPES.register("extractor_block_entity", () -> BlockEntityType.Builder.of(ExtractorBlockEntity::new, YATMBlocks.STEEL_EXTRACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<CurrentUnitForgeEnergyInterchangerBlockEntity>> C_U_F_E_I = BLOCK_ENTITY_TYPES.register("current_unit_forge_energy_interchanger_block_entity", () -> BlockEntityType.Builder.of(CurrentUnitForgeEnergyInterchangerBlockEntity::new, YATMBlocks.C_U_F_E_I.get()).build(null));
	public static final RegistryObject<BlockEntityType<CurrentConduitBlockEntity>> CURRENT_CONDUIT = BLOCK_ENTITY_TYPES.register("current_conduit_block_entity", () -> BlockEntityType.Builder.of(CurrentConduitBlockEntity::new, 
			YATMBlocks.ONE_CU_WIRE.get(), YATMBlocks.EIGHT_CU_WIRE.get(), YATMBlocks.SIXTYFOUR_CU_WIRE.get(), YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(),
			YATMBlocks.ENAMELED_ONE_CU_WIRE.get(), YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get(), YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get(), YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(),
			YATMBlocks.INSULATED_ONE_CU_WIRE.get(), YATMBlocks.INSULATED_EIGHT_CU_WIRE.get(), YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get(), YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get()
			).build(null));
	public static final RegistryObject<BlockEntityType<FluidConduitBlockEntity>> FLUID_CONDUIT = BLOCK_ENTITY_TYPES.register("fluid_conduit_block_entity", () -> BlockEntityType.Builder.of(FluidConduitBlockEntity::new, YATMBlocks.STEEL_FLUID_CONDUIT.get()).build(null));


	
} // end class
