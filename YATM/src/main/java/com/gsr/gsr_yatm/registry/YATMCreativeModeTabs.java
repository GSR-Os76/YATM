package com.gsr.gsr_yatm.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YATMCreativeModeTabs
{
	private static final HashMap<ResourceKey<CreativeModeTab>, List<Supplier<? extends Item>>> TAB_ADDITIONS_QUEUE = new HashMap<>();
	
	
	
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, YetAnotherTechMod.MODID);
	
	
	
	private static final String YATM_GENERAL_ID_PATH = "yatm_general";
	private static final ResourceLocation YATM_GENERAL_LOCATION = new ResourceLocation(YetAnotherTechMod.MODID, YATMCreativeModeTabs.YATM_GENERAL_ID_PATH);
	public static final RegistryObject<CreativeModeTab> YATM_GENERAL = CREATIVE_MODE_TABS.register(YATMCreativeModeTabs.YATM_GENERAL_ID_PATH, () -> CreativeModeTab.builder().title(YATMLanguageProvider.translatableForCreativeTab(YATMCreativeModeTabs.YATM_GENERAL_LOCATION)).icon(() -> new ItemStack(YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM.get())).build());
	
	
	
	public static void register(@NotNull IEventBus modEventBus) 
	{
		YATMCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
		
		modEventBus.addListener(YATMCreativeModeTabs::buildCreativeModeTabContents);
	} // end register()
	
	
	
	private static void buildCreativeModeTabContents(@NotNull BuildCreativeModeTabContentsEvent event)
	{
		ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
		if(YATMCreativeModeTabs.TAB_ADDITIONS_QUEUE.containsKey(tabKey)) 
		{
			
			YATMCreativeModeTabs.TAB_ADDITIONS_QUEUE.get(tabKey).forEach((i) -> event.accept(i.get()));	
		}
	} // end buildCreativeModeTabContents()
	
	
	
	public static <T extends Item> RegistryObject<T> tabEnqueue(@NotNull ResourceKey<CreativeModeTab> tabKey, @NotNull RegistryObject<T> item)
	{
		YATMCreativeModeTabs.TAB_ADDITIONS_QUEUE.computeIfAbsent(tabKey, (k) -> new ArrayList<>()).add(item);
		
		return item;		
	} // end tabEnqueue()
	
	public static <T extends Item> RegistryObject<T> yatmGeneralTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(YATMCreativeModeTabs.YATM_GENERAL.getKey(), item);		
	} // end yatmGeneralTabEnqueue()
	
	
	
	public static <T extends Item> RegistryObject<T> buildingBlocksTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(CreativeModeTabs.BUILDING_BLOCKS, item);		
	} // end buildingTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> coloredBlocksTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(CreativeModeTabs.COLORED_BLOCKS, item);		
	} // end coloredBlocksTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> naturalBlocksTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(CreativeModeTabs.NATURAL_BLOCKS, item);		
	} // end naturalBlocksTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> functionalBlocksTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(CreativeModeTabs.FUNCTIONAL_BLOCKS, item);		
	} // end functionalBlocksTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> redstoneBlocksTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(CreativeModeTabs.REDSTONE_BLOCKS, item);		
	} // end redstoneBlocksTabEnqueue()
	
	
	
	public static <T extends Item> RegistryObject<T> toolAndUtilitiesTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(CreativeModeTabs.TOOLS_AND_UTILITIES, item);		
	} // end toolAndUtilitiesTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> combatTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(CreativeModeTabs.COMBAT, item);		
	} // end combatTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> foodAndDrinksTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(CreativeModeTabs.FOOD_AND_DRINKS, item);		
	} // end foodAndDrinksTabEnqueue()
	
	public static <T extends Item> RegistryObject<T> ingredientsTabEnqueue(@NotNull RegistryObject<T> item)
	{
		return YATMCreativeModeTabs.tabEnqueue(CreativeModeTabs.INGREDIENTS, item);		
	} // end ingredientsTabEnqueue()
	
} // end class