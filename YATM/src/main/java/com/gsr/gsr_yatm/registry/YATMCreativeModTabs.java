package com.gsr.gsr_yatm.registry;

import java.util.ArrayList;
import java.util.function.Supplier;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
	
	
	
	private static final String YATM_GENERAL_ID_PATH = "yatm_general";
	private static final ResourceLocation YATM_GENERAL_LOCATION = new ResourceLocation(YetAnotherTechMod.MODID, YATM_GENERAL_ID_PATH);
	public static final RegistryObject<CreativeModeTab> YATM_GENERAL = CREATIVE_MOD_TABS.register(YATM_GENERAL_ID_PATH, () -> CreativeModeTab.builder().title(YATMLanguageProvider.getTranslatableTitleNameFor(YATM_GENERAL_LOCATION)).icon(() -> new ItemStack(YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get())).build());
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