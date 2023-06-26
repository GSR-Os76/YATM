package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.block.device.boiler.BoilerMenu;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerMenu;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorMenu;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderMenu;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMMenuTypes
{
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<MenuType<BoilerMenu>> BOILER_MENU = MENU_TYPES.register("boiler_menu", () -> new MenuType<>(BoilerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<CrystallizerMenu>> CRYSTALLIZER_MENU = MENU_TYPES.register("crystallizer_menu", () -> new MenuType<>(CrystallizerMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<ExtractorMenu>> EXTRACTOR_MENU = MENU_TYPES.register("extractor_menu", () -> new MenuType<>(ExtractorMenu::new, FeatureFlagSet.of()));
	public static final RegistryObject<MenuType<ExtruderMenu>> EXTRUDER_MENU = MENU_TYPES.register("extruder_menu", () -> new MenuType<>(ExtruderMenu::new, FeatureFlagSet.of()));
	
} // end class
