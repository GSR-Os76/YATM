package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.device.bioler.BiolerMenu;
import com.gsr.gsr_yatm.block.device.boiler.BoilerMenu;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerMenu;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorMenu;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderMenu;
import com.gsr.gsr_yatm.block.device.grinder.GrinderMenu;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMMenuTypes
{
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<MenuType<BiolerMenu>> BIOLER = MENU_TYPES.register("bioler_menu", () -> new MenuType<>(BiolerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<BoilerMenu>> BOILER = MENU_TYPES.register("boiler_menu", () -> new MenuType<>(BoilerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<CrystallizerMenu>> CRYSTALLIZER = MENU_TYPES.register("crystallizer_menu", () -> new MenuType<>(CrystallizerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<GrinderMenu>> GRINDER = MENU_TYPES.register("grinder_menu", () -> new MenuType<>(GrinderMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<ExtractorMenu>> EXTRACTOR = MENU_TYPES.register("extractor_menu", () -> new MenuType<>(ExtractorMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<ExtruderMenu>> EXTRUDER = MENU_TYPES.register("extruder_menu", () -> new MenuType<>(ExtruderMenu::new, FeatureFlagSet.of()));
	
} // end class