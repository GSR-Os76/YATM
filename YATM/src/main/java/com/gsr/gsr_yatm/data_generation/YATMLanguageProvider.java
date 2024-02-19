package com.gsr.gsr_yatm.data_generation;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class YATMLanguageProvider extends LanguageProvider
{

	public YATMLanguageProvider(@NotNull PackOutput output, @NotNull String locale)
	{
		super(Objects.requireNonNull(output), YetAnotherTechMod.MODID, Objects.requireNonNull(locale));
	} // end constructor

	
	
	public void add(@NotNull Fluid fluid, @NotNull String name)
	{
		this.add(Util.makeDescriptionId("fluid", ForgeRegistries.FLUIDS.getKey(fluid)), name);
	} // end add()
	
	public void add(@NotNull MenuType<? extends AbstractContainerMenu> menu, @NotNull String name) 
	{
		this.add(getMenuTitleNameFor(menu), name);
	} // end add()
	
	public void add(@NotNull RegistryObject<CreativeModeTab> tab, @NotNull String name) 
	{
		this.add(getCreativeTabTitleNameFor(tab), name);
	} // end add()
	
	public void addMenu(@NotNull Block block, @NotNull String name) 
	{
		this.add(getMenuTitleNameFor(ForgeRegistries.BLOCKS.getKey(block)), name);
	} // end addMenu()
	
	
	public static @NotNull String getMenuTitleNameFor(@NotNull MenuType<? extends AbstractContainerMenu> menu) 
	{
		return getMenuTitleNameFor(ForgeRegistries.MENU_TYPES.getKey(menu));
	} // end getMenuTitleNameFor()
	
	public static @NotNull String getMenuTitleNameFor(@NotNull ResourceLocation location) 
	{
		return Util.makeDescriptionId("menu.title", location);
	} // end getMenuTitleNameFor()
	
	
	
	public static @NotNull String getCreativeTabTitleNameFor(@NotNull RegistryObject<CreativeModeTab> tab) 
	{
		return getCreativeTabTitleNameFor(tab.getId());
	} // end getCreativeTabTitleNameFor()
	
	public static @NotNull String getCreativeTabTitleNameFor(@NotNull ResourceLocation location) 
	{
		return Util.makeDescriptionId("item_group", location);
	} // end getCreativeTabTitleNameFor()
	
	
	
	public static @NotNull Component translatableForMenu(@NotNull MenuType<? extends AbstractContainerMenu> menu) 
	{
		return Component.translatable(getMenuTitleNameFor(menu));
	} // end getTranslatableTitleNameFor()
	
	public static @NotNull Component translatableForCreativeTab(@NotNull ResourceLocation location) 
	{
		return Component.translatable(getCreativeTabTitleNameFor(location));
	} // end translatableForMenu()
	
	public static @NotNull Component translatableForMenu(@NotNull Block block) 
	{
		return Component.translatable(getMenuTitleNameFor(ForgeRegistries.BLOCKS.getKey(block)));
	} // end translatableForMenu()
	
} // end class