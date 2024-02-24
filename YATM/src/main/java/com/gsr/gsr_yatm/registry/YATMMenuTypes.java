package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.device.crafting.bioreactor.BioreactorMenu;
import com.gsr.gsr_yatm.block.device.crafting.boiler.BoilerMenu;
import com.gsr.gsr_yatm.block.device.crafting.crucible.CrucibleMenu;
import com.gsr.gsr_yatm.block.device.crafting.crystallizer.CrystallizerMenu;
import com.gsr.gsr_yatm.block.device.crafting.extractor.ExtractorMenu;
import com.gsr.gsr_yatm.block.device.crafting.grafting_table.GraftingMenu;
import com.gsr.gsr_yatm.block.device.crafting.grinder.GrinderMenu;
import com.gsr.gsr_yatm.block.device.crafting.heat_furnace.HeatFurnaceMenu;
import com.gsr.gsr_yatm.block.device.crafting.injector.InjectorMenu;
import com.gsr.gsr_yatm.block.device.crafting.still.StillMenu;
import com.gsr.gsr_yatm.block.device.creative.current_source.CreativeCurrentSourceMenu;
import com.gsr.gsr_yatm.block.device.current_storer.base.CurrentStorerMenu;
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
	
	
	
	public static final RegistryObject<MenuType<GraftingMenu>> GRAFTING_TABLE = MENU_TYPES.register("grafting", () -> new MenuType<>(GraftingMenu::new, FeatureFlagSet.of()));

	public static final RegistryObject<MenuType<BioreactorMenu>> BIOREACTOR = MENU_TYPES.register("bioreactor", () -> new MenuType<>(BioreactorMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<BoilerMenu>> BOILER = MENU_TYPES.register("boiler", () -> new MenuType<>(BoilerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<CrucibleMenu>> CRUCIBLE = MENU_TYPES.register("crucible", () -> new MenuType<>(CrucibleMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<CrystallizerMenu>> CRYSTALLIZER = MENU_TYPES.register("crystallizer", () -> new MenuType<>(CrystallizerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<ExtractorMenu>> EXTRACTOR = MENU_TYPES.register("extractor", () -> new MenuType<>(ExtractorMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<HeatFurnaceMenu>> HEAT_FURNACE = MENU_TYPES.register("heat_furnace", () -> new MenuType<>(HeatFurnaceMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<GrinderMenu>> GRINDER = MENU_TYPES.register("grinder", () -> new MenuType<>(GrinderMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<InjectorMenu>> INJECTOR = MENU_TYPES.register("injector", () -> new MenuType<>(InjectorMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<StillMenu>> STILL = MENU_TYPES.register("still_menu", () -> new MenuType<>(StillMenu::new, FeatureFlagSet.of()));
      
	public static final RegistryObject<MenuType<CurrentStorerMenu>> CURRENT_STORER = MENU_TYPES.register("current_storer", () -> new MenuType<>(CurrentStorerMenu::new, FeatureFlagSet.of())); 
	
	public static final RegistryObject<MenuType<BatterySolarPanelMenu>> BATTERY_SOLAR_PANEL = MENU_TYPES.register("battery_solar_panel", () -> new MenuType<>(BatterySolarPanelMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<SolarPanelMenu>> SOLAR_PANEL = MENU_TYPES.register("solar_panel", () -> new MenuType<>(SolarPanelMenu::new, FeatureFlagSet.of()));

	public static final RegistryObject<MenuType<CreativeCurrentSourceMenu>> CREATIVE_CURRENT_SOURCE = MENU_TYPES.register("creative_current_source", () -> new MenuType<>(CreativeCurrentSourceMenu::new, FeatureFlagSet.of()));

	public static final RegistryObject<MenuType<PoweredToolMenu>> POWERED_TOOL = MENU_TYPES.register("powered_tool", () -> new MenuType<>(PoweredToolMenu::new, FeatureFlagSet.of()));



	
} // end class