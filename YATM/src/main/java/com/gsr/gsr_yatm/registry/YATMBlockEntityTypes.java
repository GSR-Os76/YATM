package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.conduit.channel_vine.ChannelVinesBlockEntity;
import com.gsr.gsr_yatm.block.device.bioreactor.BioreactorBlockEntity;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlockEntity;
import com.gsr.gsr_yatm.block.device.compute.scan_collector.DataScanCollectorBlockEntity;
import com.gsr.gsr_yatm.block.device.compute.storage.DataStorageBlockEntity;
import com.gsr.gsr_yatm.block.device.creative.current_source.CreativeCurrentSourceBlockEntity;
import com.gsr.gsr_yatm.block.device.crucible.CrucibleBlockEntity;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerBlockEntity;
import com.gsr.gsr_yatm.block.device.current_furnace.CurrentFurnaceBlockEntity;
import com.gsr.gsr_yatm.block.device.energy_converter.CurrentUnitForgeEnergyInterchangerBlockEntity;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorBlockEntity;
import com.gsr.gsr_yatm.block.device.grinder.GrinderBlockEntity;
import com.gsr.gsr_yatm.block.device.injector.InjectorBlockEntity;
import com.gsr.gsr_yatm.block.device.sap_collector.SapCollectorBlockEntity;
import com.gsr.gsr_yatm.block.device.solar.BatterySolarPanelBlockEntity;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelBlockEntity;
import com.gsr.gsr_yatm.block.device.tank.TankBlockEntity;
import com.gsr.gsr_yatm.block.hanging_pot.HangingPotHookBlockEntity;
import com.gsr.gsr_yatm.block.sign.YATMHangingSignBlockEntity;
import com.gsr.gsr_yatm.block.sign.YATMSignBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMBlockEntityTypes
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<BlockEntityType<HangingPotHookBlockEntity>> HANGING_POT_HOOK = BLOCK_ENTITY_TYPES.register("hanging_pot_hook", () -> BlockEntityType.Builder.of(HangingPotHookBlockEntity::new, YATMBlocks.HANGING_POT_HOOK.get()).build(null));
	public static final RegistryObject<BlockEntityType<YATMSignBlockEntity>> YATM_SIGN = BLOCK_ENTITY_TYPES.register("yatm_sign", () -> BlockEntityType.Builder.of(YATMSignBlockEntity::new, YATMBlocks.RUBBER_SIGN.get(), YATMBlocks.RUBBER_WALL_SIGN.get(), YATMBlocks.SOUL_AFFLICTED_RUBBER_SIGN.get(), YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_SIGN.get()).build(null));
	public static final RegistryObject<BlockEntityType<YATMHangingSignBlockEntity>> YATM_HANGING_SIGN = BLOCK_ENTITY_TYPES.register("yatm_hanging_sign", () -> BlockEntityType.Builder.of(YATMHangingSignBlockEntity::new, YATMBlocks.RUBBER_HANGING_SIGN.get(), YATMBlocks.RUBBER_WALL_HANGING_SIGN.get(), YATMBlocks.SOUL_AFFLICTED_RUBBER_HANGING_SIGN.get(), YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_HANGING_SIGN.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<SapCollectorBlockEntity>> SAP_COLLECTOR = BLOCK_ENTITY_TYPES.register("sap_collector", () -> BlockEntityType.Builder.of(SapCollectorBlockEntity::new, YATMBlocks.SAP_COLLECTOR.get()).build(null));	
	
	public static final RegistryObject<BlockEntityType<BioreactorBlockEntity>> BIOLER = BLOCK_ENTITY_TYPES.register("bioreactor", () -> BlockEntityType.Builder.of(BioreactorBlockEntity::new, YATMBlocks.STEEL_BIOREACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<BoilerBlockEntity>> BOILER = BLOCK_ENTITY_TYPES.register("boiler", () -> BlockEntityType.Builder.of(BoilerBlockEntity::new, YATMBlocks.STEEL_BOILER.get()).build(null));
	public static final RegistryObject<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE = BLOCK_ENTITY_TYPES.register("crucible", () -> BlockEntityType.Builder.of(CrucibleBlockEntity::new, YATMBlocks.STEEL_CRUCIBLE.get()).build(null));
	public static final RegistryObject<BlockEntityType<CrystallizerBlockEntity>> CRYSTALLIZER = BLOCK_ENTITY_TYPES.register("crystallizer", () -> BlockEntityType.Builder.of(CrystallizerBlockEntity::new, YATMBlocks.STEEL_CRYSTALLIZER.get()).build(null));
//	public static final RegistryObject<BlockEntityType<ExtruderBlockEntity>> EXTRUDER = BLOCK_ENTITY_TYPES.register("extruder_block_entity", () -> BlockEntityType.Builder.of(ExtruderBlockEntity::new, YATMBlocks.STEEL_EXTRUDER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR = BLOCK_ENTITY_TYPES.register("extractor", () -> BlockEntityType.Builder.of(ExtractorBlockEntity::new, YATMBlocks.STEEL_EXTRACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<CurrentFurnaceBlockEntity>> FURNACE_PLUS = BLOCK_ENTITY_TYPES.register("current_furnace", () -> BlockEntityType.Builder.of(CurrentFurnaceBlockEntity::new, YATMBlocks.STEEL_CRYSTALLIZER.get()).build(null));
	public static final RegistryObject<BlockEntityType<GrinderBlockEntity>> GRINDER = BLOCK_ENTITY_TYPES.register("grinder", () -> BlockEntityType.Builder.of(GrinderBlockEntity::new, YATMBlocks.STEEL_GRINDER.get()).build(null));
	public static final RegistryObject<BlockEntityType<InjectorBlockEntity>> INJECTOR = BLOCK_ENTITY_TYPES.register("injector", () -> BlockEntityType.Builder.of(InjectorBlockEntity::new, YATMBlocks.STEEL_INJECTOR.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<DataScanCollectorBlockEntity>> DATA_SCAN_COLLECTOR = BLOCK_ENTITY_TYPES.register("data_scan_collector", () -> BlockEntityType.Builder.of(DataScanCollectorBlockEntity::new, YATMBlocks.DATA_SCAN_COLLECTOR.get()).build(null));
	// public static final RegistryObject<BlockEntityType<DestructiveScannerBlockEntity>> DESTRUCTIVE_SCANNER = BLOCK_ENTITY_TYPES.register("destructive_scanner", () -> BlockEntityType.Builder.of(DestructiveScannerBlockEntity::new, YATMBlocks..get()).build(null));
	public static final RegistryObject<BlockEntityType<DataStorageBlockEntity>> DATA_STORAGE = BLOCK_ENTITY_TYPES.register("data_storage", () -> BlockEntityType.Builder.of(DataStorageBlockEntity::new, YATMBlocks.DATA_STORAGE_BLOCK.get()).build(null));
	// public static final RegistryObject<BlockEntityType<DataProcessorBlockEntity>> DATA_PROCESSOR = BLOCK_ENTITY_TYPES.register("data_processor", () -> BlockEntityType.Builder.of(DataProcessorBlockEntity::new, YATMBlocks..get()).build(null));
	
	
	
	public static final RegistryObject<BlockEntityType<BatterySolarPanelBlockEntity>> BATTERY_SOLAR_PANEL = BLOCK_ENTITY_TYPES.register("battery_solar_panel", () -> BlockEntityType.Builder.of(BatterySolarPanelBlockEntity::new, YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get(), YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get(), YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get()).build(null));
	public static final RegistryObject<BlockEntityType<CurrentUnitForgeEnergyInterchangerBlockEntity>> C_U_F_E_I = BLOCK_ENTITY_TYPES.register("current_unit_forge_energy_interchanger_block_entity", () -> BlockEntityType.Builder.of(CurrentUnitForgeEnergyInterchangerBlockEntity::new, YATMBlocks.C_U_F_E_I.get()).build(null));
	public static final RegistryObject<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL = BLOCK_ENTITY_TYPES.register("solar_panel", () -> BlockEntityType.Builder.of(SolarPanelBlockEntity::new, YATMBlocks.CRUDE_SOLAR_PANEL.get(), YATMBlocks.ADVANCED_SOLAR_PANEL.get(), YATMBlocks.SUNS_COMPLEMENT_SOLAR_PANEL.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<TankBlockEntity>> TANK = BLOCK_ENTITY_TYPES.register("tank", () -> BlockEntityType.Builder.of(TankBlockEntity::new, YATMBlocks.STEEL_TANK.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<ChannelVinesBlockEntity>> CHANNEL_VINES = BLOCK_ENTITY_TYPES.register("channel_vines", () -> BlockEntityType.Builder.of(ChannelVinesBlockEntity::new, YATMBlocks.CHANNEL_VINES.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<CreativeCurrentSourceBlockEntity>> CREATIVE_CURRENT_SOURCE = BLOCK_ENTITY_TYPES.register("current_source", () -> BlockEntityType.Builder.of(CreativeCurrentSourceBlockEntity::new, YATMBlocks.CREATIVE_CURRENT_SOURCE.get()).build(null));
	
	
} // end class
