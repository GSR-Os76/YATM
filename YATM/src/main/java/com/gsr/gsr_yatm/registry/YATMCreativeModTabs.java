package com.gsr.gsr_yatm.registry;

import java.util.ArrayList;
import java.util.function.Supplier;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YATMCreativeModTabs
{
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, YetAnotherTechMod.MODID);
	
	
	
	// TODO, get name component dynamically and from a dedicated area
	private static final RegistryObject<CreativeModeTab> YATM_GENERAL = CREATIVE_MOD_TABS.register("yatm_general", () -> CreativeModeTab.builder().title(Component.translatable("item_group." + YetAnotherTechMod.MODID + ".yatm_general")).icon(() -> new ItemStack(YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get())).build());
	private static final ArrayList<Supplier<Item>> YATM_GENERAL_CREATIVE_TAB_QUEUE = new ArrayList<>();
	
	
	
	public static void register(IEventBus modEventBus) 
	{
		YATMCreativeModTabs.CREATIVE_MOD_TABS.register(modEventBus);
		
		modEventBus.addListener(YATMCreativeModTabs::buildCreativeModeTabContents);
	} // end register()
	
	
	
	private static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event)
	{
		if (event.getTab() == YATMCreativeModTabs.YATM_GENERAL.get())	
		{
			YATM_GENERAL_CREATIVE_TAB_QUEUE.forEach((i) -> event.accept(i.get()));				
		}
	} // end creativeModeTabsBuildContent()
	
	
	
	public static <T extends Item> RegistryObject<T> generalTabEnqueue(RegistryObject<T> item)
	{
		YATMCreativeModTabs.YATM_GENERAL_CREATIVE_TAB_QUEUE.add(() -> item.get());
		return item;		
	} // end queueForGeneralCreativeTab()
} // end class