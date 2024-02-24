package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.device.conduit.channel_vine.ChannelVinesBlockEntity;
import com.gsr.gsr_yatm.block.device.conduit.conduit_vine_bundle.ConduitVineBundleBlockEntity;
import com.gsr.gsr_yatm.block.device.crafting.bioreactor.BioreactorBlockEntity;
import com.gsr.gsr_yatm.block.device.crafting.boiler.BoilerBlockEntity;
import com.gsr.gsr_yatm.block.device.crafting.crucible.CrucibleBlockEntity;
import com.gsr.gsr_yatm.block.device.crafting.crystallizer.CrystallizerBlockEntity;
import com.gsr.gsr_yatm.block.device.crafting.extractor.ExtractorBlockEntity;
import com.gsr.gsr_yatm.block.device.crafting.grinder.GrinderBlockEntity;
import com.gsr.gsr_yatm.block.device.crafting.heat_furnace.HeatFurnaceBlockEntity;
import com.gsr.gsr_yatm.block.device.crafting.injector.InjectorBlockEntity;
import com.gsr.gsr_yatm.block.device.crafting.still.StillBlockEntity;
import com.gsr.gsr_yatm.block.device.creative.current_source.CreativeCurrentSourceBlockEntity;
import com.gsr.gsr_yatm.block.device.current_storer.AdvancedCurrentBatteryBlockBlockEntity;
import com.gsr.gsr_yatm.block.device.current_storer.CurrentBatteryBlockBlockEntity;
import com.gsr.gsr_yatm.block.device.current_storer.CurrentTuberBlockBlockEntity;
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
	
	public static final RegistryObject<BlockEntityType<BioreactorBlockEntity>> BIOLER = BLOCK_ENTITY_TYPES.register("bioreactor", () -> BlockEntityType.Builder.of(BioreactorBlockEntity::new, YATMBlocks.BIOREACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<BoilerBlockEntity>> BOILER = BLOCK_ENTITY_TYPES.register("boiler", () -> BlockEntityType.Builder.of(BoilerBlockEntity::new, YATMBlocks.BOILER.get()).build(null));
	public static final RegistryObject<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE = BLOCK_ENTITY_TYPES.register("crucible", () -> BlockEntityType.Builder.of(CrucibleBlockEntity::new, YATMBlocks.CRUCIBLE.get()).build(null));
	public static final RegistryObject<BlockEntityType<CrystallizerBlockEntity>> CRYSTALLIZER = BLOCK_ENTITY_TYPES.register("crystallizer", () -> BlockEntityType.Builder.of(CrystallizerBlockEntity::new, YATMBlocks.CRYSTALLIZER.get()).build(null));
//	public static final RegistryObject<BlockEntityType<ExtruderBlockEntity>> EXTRUDER = BLOCK_ENTITY_TYPES.register("extruder_block_entity", () -> BlockEntityType.Builder.of(ExtruderBlockEntity::new, YATMBlocks.STEEL_EXTRUDER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR = BLOCK_ENTITY_TYPES.register("extractor", () -> BlockEntityType.Builder.of(ExtractorBlockEntity::new, YATMBlocks.EXTRACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<HeatFurnaceBlockEntity>> HEAT_FURNACE = BLOCK_ENTITY_TYPES.register("heat_furnace", () -> BlockEntityType.Builder.of(HeatFurnaceBlockEntity::new, YATMBlocks.HEAT_FURNACE.get()).build(null));
	public static final RegistryObject<BlockEntityType<GrinderBlockEntity>> GRINDER = BLOCK_ENTITY_TYPES.register("grinder", () -> BlockEntityType.Builder.of(GrinderBlockEntity::new, YATMBlocks.GRINDER.get()).build(null));
	public static final RegistryObject<BlockEntityType<InjectorBlockEntity>> INJECTOR = BLOCK_ENTITY_TYPES.register("injector", () -> BlockEntityType.Builder.of(InjectorBlockEntity::new, YATMBlocks.INJECTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<StillBlockEntity>> STILL = BLOCK_ENTITY_TYPES.register("still", () -> BlockEntityType.Builder.of(StillBlockEntity::new, YATMBlocks.STILL.get()).build(null));
	
	
	
	
	public static final RegistryObject<BlockEntityType<CurrentTuberBlockBlockEntity>> CURRENT_TUBER_BLOCK = BLOCK_ENTITY_TYPES.register("current_tuber_block", () -> BlockEntityType.Builder.of(CurrentTuberBlockBlockEntity::new, YATMBlocks.CURRENT_TUBER_BLOCK.get()).build(null));
	public static final RegistryObject<BlockEntityType<CurrentBatteryBlockBlockEntity>> CURRENT_BATTERY_BLOCK = BLOCK_ENTITY_TYPES.register("current_battery_block", () -> BlockEntityType.Builder.of(CurrentBatteryBlockBlockEntity::new, YATMBlocks.CURRENT_BATTERY_BLOCK.get()).build(null));
	public static final RegistryObject<BlockEntityType<AdvancedCurrentBatteryBlockBlockEntity>> ADVANCED_CURRENT_BATTERY_BLOCK = BLOCK_ENTITY_TYPES.register("advanced_current_battery_block", () -> BlockEntityType.Builder.of(AdvancedCurrentBatteryBlockBlockEntity::new, YATMBlocks.ADVANCED_CURRENT_BATTERY_BLOCK.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<TankBlockEntity>> TANK = BLOCK_ENTITY_TYPES.register("tank", () -> BlockEntityType.Builder.of(TankBlockEntity::new, YATMBlocks.STEEL_TANK.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<BatterySolarPanelBlockEntity>> BATTERY_SOLAR_PANEL = BLOCK_ENTITY_TYPES.register("battery_solar_panel", () -> BlockEntityType.Builder.of(BatterySolarPanelBlockEntity::new, YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get(), YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get(), YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get()).build(null));
	public static final RegistryObject<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL = BLOCK_ENTITY_TYPES.register("solar_panel", () -> BlockEntityType.Builder.of(SolarPanelBlockEntity::new, YATMBlocks.CRUDE_SOLAR_PANEL.get(), YATMBlocks.ADVANCED_SOLAR_PANEL.get(), YATMBlocks.SUNS_COMPLEMENT_SOLAR_PANEL.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<ChannelVinesBlockEntity>> CHANNEL_VINES = BLOCK_ENTITY_TYPES.register("channel_vines", () -> BlockEntityType.Builder.of(ChannelVinesBlockEntity::new, YATMBlocks.CHANNEL_VINES.get()).build(null));
	public static final RegistryObject<BlockEntityType<ConduitVineBundleBlockEntity>> CONDUIT_VINE_BUNDLE = BLOCK_ENTITY_TYPES.register("conduit_vine_bundle", () -> BlockEntityType.Builder.of(ConduitVineBundleBlockEntity::new, YATMBlocks.CONDUIT_VINE_BUNDLE.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<CreativeCurrentSourceBlockEntity>> CREATIVE_CURRENT_SOURCE = BLOCK_ENTITY_TYPES.register("current_source", () -> BlockEntityType.Builder.of(CreativeCurrentSourceBlockEntity::new, YATMBlocks.CREATIVE_CURRENT_SOURCE.get()).build(null));



	
} // end class