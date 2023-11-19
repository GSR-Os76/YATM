package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.device.bioler.BiolerMenu;
import com.gsr.gsr_yatm.block.device.boiler.BoilerMenu;
import com.gsr.gsr_yatm.block.device.creative.current_source.CreativeCurrentSourceMenu;
import com.gsr.gsr_yatm.block.device.crucible.CrucibleMenu;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerMenu;
import com.gsr.gsr_yatm.block.device.current_furnace.FurnacePlusMenu;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorMenu;
import com.gsr.gsr_yatm.block.device.grafting.GraftingMenu;
import com.gsr.gsr_yatm.block.device.grinder.GrinderMenu;
import com.gsr.gsr_yatm.block.device.injector.InjectorMenu;
import com.gsr.gsr_yatm.block.device.solar.BatterySolarPanelMenu;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelMenu;
import com.gsr.gsr_yatm.item.tool.PoweredToolMenu;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMMenuTypes
{
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<MenuType<GraftingMenu>> GRAFTING_TABLE = MENU_TYPES.register("grafting_menu", () -> new MenuType<>(GraftingMenu::new, FeatureFlagSet.of()));

	public static final RegistryObject<MenuType<BiolerMenu>> BIOLER = MENU_TYPES.register("bioler_menu", () -> new MenuType<>(BiolerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<BoilerMenu>> BOILER = MENU_TYPES.register("boiler_menu", () -> new MenuType<>(BoilerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<CrucibleMenu>> CRUCIBLE = MENU_TYPES.register("crucible_menu", () -> new MenuType<>(CrucibleMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<CrystallizerMenu>> CRYSTALLIZER = MENU_TYPES.register("crystallizer_menu", () -> new MenuType<>(CrystallizerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<ExtractorMenu>> EXTRACTOR = MENU_TYPES.register("extractor_menu", () -> new MenuType<>(ExtractorMenu::new, FeatureFlagSet.of()));
//	public static final RegistryObject<MenuType<ExtruderMenu>> EXTRUDER = MENU_TYPES.register("extruder_menu", () -> new MenuType<>(ExtruderMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<FurnacePlusMenu>> FURNACE_PLUS = MENU_TYPES.register("furnace_plus", () -> new MenuType<>(FurnacePlusMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<GrinderMenu>> GRINDER = MENU_TYPES.register("grinder_menu", () -> new MenuType<>(GrinderMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<InjectorMenu>> INJECTOR = MENU_TYPES.register("injector_menu", () -> new MenuType<>(InjectorMenu::new, FeatureFlagSet.of()));
	
	public static final RegistryObject<MenuType<BatterySolarPanelMenu>> BATTERY_SOLAR_PANEL = MENU_TYPES.register("battery_solar_panel", () -> new MenuType<>(BatterySolarPanelMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<SolarPanelMenu>> SOLAR_PANEL = MENU_TYPES.register("solar_panel", () -> new MenuType<>(SolarPanelMenu::new, FeatureFlagSet.of()));

	public static final RegistryObject<MenuType<CreativeCurrentSourceMenu>> CREATIVE_CURRENT_SOURCE = MENU_TYPES.register("creative_current_source", () -> new MenuType<>(CreativeCurrentSourceMenu::new, FeatureFlagSet.of()));

	public static final RegistryObject<MenuType<PoweredToolMenu>> POWERED_TOOL = MENU_TYPES.register("powered_tool", () -> new MenuType<>(PoweredToolMenu::new, FeatureFlagSet.of()));
	
} // end class