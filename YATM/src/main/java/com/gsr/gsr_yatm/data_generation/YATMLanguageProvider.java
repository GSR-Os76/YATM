package com.gsr.gsr_yatm.data_generation;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class YATMLanguageProvider extends LanguageProvider
{

	public YATMLanguageProvider(PackOutput output, String locale)
	{
		super(output, YetAnotherTechMod.MODID, locale);
	} // end constructor

	
	
	public void add(Fluid fluid, String name)
	{
		this.add(Util.makeDescriptionId("fluid", ForgeRegistries.FLUIDS.getKey(fluid)), name);
	} // end add()
	
	public <T extends AbstractContainerMenu> void add(MenuType<T> menu, String name) 
	{
		this.add(getTitleNameFor(menu), name);
	} // end add()
	
	public void add(RegistryObject<CreativeModeTab> tab, String name) 
	{
		this.add(getTitleNameFor(tab), name);
	} // end add()
	
	
	
	public static <T extends AbstractContainerMenu> String getTitleNameFor(MenuType<T> menu) 
	{
		return Util.makeDescriptionId("menu.title", ForgeRegistries.MENU_TYPES.getKey(menu));
	} // end getTitleNameFor()
	
	public static String getTitleNameFor(RegistryObject<CreativeModeTab> tab) 
	{
		ResourceLocation tabLocation = tab.getId();
		return getTitleNameFor(tabLocation);
	} // end getTitleNameFor()
	
	public static String getTitleNameFor(ResourceLocation tabLocation) 
	{
		return "item_group." + tabLocation.getNamespace() + "." + tabLocation.getPath();
	} // end getTitleNameFor()
	
	
	
	public static <T extends AbstractContainerMenu> Component translatableFor(MenuType<T> menu) 
	{
		return Component.translatable(getTitleNameFor(menu));
	} // end getTranslatableTitleNameFor()
	
	public static Component getTranslatableTitleNameFor(ResourceLocation tabLocation) 
	{
		return Component.translatable(getTitleNameFor(tabLocation));
	} // end getTranslatableTitleNameFor()
	
} // end class