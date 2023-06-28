package com.gsr.gsr_yatm;

import java.util.ArrayList;

import com.gsr.gsr_yatm.registry.YATMItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class YATMCreativeModTabs
{
	private static final ArrayList<RegistryObject<? extends Item>> YATM_GENERAL_CREATIVE_TAB_QUEUE = new ArrayList<>();
	private static CreativeModeTab YATM_GENERAL;

	
	
	public static void register(IEventBus modEventBus) 
	{
		modEventBus.addListener(YATMCreativeModTabs::registerCreativeModeTabs);
		modEventBus.addListener(YATMCreativeModTabs::buildCreativeModeTabContents);
	} // end register()
	
	
	
	private static void registerCreativeModeTabs(CreativeModeTabEvent.Register event)
	{
		YATM_GENERAL = event.registerCreativeModeTab(new ResourceLocation(YetAnotherTechMod.MODID, "yatm_general"), 
				builder -> builder
				.title(Component.translatable("item_group." + YetAnotherTechMod.MODID + ".yatm_general"))
				.icon(() -> new ItemStack(YATMItems.SILVER_INGOT.get())));
	} // end creativeModeTabsRegister()
	
	private static void buildCreativeModeTabContents(CreativeModeTabEvent.BuildContents event)
	{
		if (event.getTab() == YATM_GENERAL)	
		{
			YATM_GENERAL_CREATIVE_TAB_QUEUE.forEach((ri) -> event.accept(ri.get()));				
		}
	} // end creativeModeTabsBuildContent()
	
	
	
	public static <T extends Item> RegistryObject<T> queueForGeneralCreativeTab(RegistryObject<T> item)
	{
		YATMCreativeModTabs.YATM_GENERAL_CREATIVE_TAB_QUEUE.add(item);
		return item;		
	} // end queueForGeneralCreativeTab()
} // end class