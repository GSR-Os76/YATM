package com.gsr.gsr_yatm;

import java.util.ArrayList;
import org.slf4j.Logger;

import com.gsr.gsr_yatm.block.device.boiler.BoilerScreen;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderScreen;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

@Mod(YetAnotherTechMod.MODID)
public class YetAnotherTechMod
{
	public static final String MODID = "gsr_yatm";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	private static final ArrayList<RegistryObject<Item>> YATM_GENERAL_CREATIVE_TAB_QUEUE = new ArrayList<RegistryObject<Item>>();

	public static CreativeModeTab YATM_GENERAL;
	
	

	public YetAnotherTechMod()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		YATMBlocks.BLOCKS.register(modEventBus);
		YATMItems.ITEMS.register(modEventBus);
		YATMMenuTypes.MENU_TYPES.register(modEventBus);
		YATMBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
		YATMRecipeTypes.RECIPE_TYPES.register(modEventBus);
		YATMRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
		
		modEventBus.addListener(this::creativeModeTabsBuildContent);
		modEventBus.addListener(this::creativeModeTabsRegister);
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::gatherData);
	} // end constructor
	
	
	

	private void creativeModeTabsRegister(CreativeModeTabEvent.Register event)
	{
		YATM_GENERAL = event.registerCreativeModeTab(new ResourceLocation(MODID, "yatm_general"), 
				builder -> builder
				.title(Component.translatable("item_group." + MODID + ".yatm_general"))
				.icon(() -> new ItemStack(YATMItems.SILVER_INGOT.get())));
	} // end creativeModeTabsRegister()
	
	private void creativeModeTabsBuildContent(CreativeModeTabEvent.BuildContents event)
	{
		if (event.getTab() == YATM_GENERAL)	
		{
			YATM_GENERAL_CREATIVE_TAB_QUEUE.forEach((ri) -> event.accept(ri.get()));				
		}
	} // end creativeModeTabsBuildContent()
	

	private void clientSetup(FMLClientSetupEvent event)
	{
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.BOILER_MENU.get(), BoilerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.EXTRUDER_MENU.get(), ExtruderScreen::new));
		
		//net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(Blocks.RUBBER_MERISTEM, RenderType.yt);
	} // end clientSetup()

	private void gatherData(GatherDataEvent event)
	{
			DataProvider.Factory<YATMBlockStateProvider> blockStateProviderFactory = (o) -> new YATMBlockStateProvider(o, YetAnotherTechMod.MODID, event.getExistingFileHelper());
			event.getGenerator().addProvider(event.includeClient(), blockStateProviderFactory);
		
			
			
			DataProvider.Factory<YATMBlockTags> blockTagProviderFactory 
			= new DataProvider.Factory<YATMBlockTags>() {
				YATMBlockTags backing = null;
			
				@Override
				public YATMBlockTags create(PackOutput output)
				{
					if(backing == null) 
						{
							backing = new YATMBlockTags(output, event.getLookupProvider(), MODID, event.getExistingFileHelper());
						}
						return backing;
				} // end create()
			};		
			event.getGenerator().addProvider(event.includeServer(), blockTagProviderFactory);
			
			DataProvider.Factory<YATMItemTags> itemTagProviderFactory 
			= (pOP) -> new YATMItemTags(pOP, event.getLookupProvider(), blockTagProviderFactory.create(pOP).contentsGetter(), MODID, event.getExistingFileHelper());
			event.getGenerator().addProvider(event.includeServer(), itemTagProviderFactory);
			
			DataProvider.Factory<YATMRecipeProvider> recipeProviderFactory = (o) -> new YATMRecipeProvider(o);
			event.getGenerator().addProvider(event.includeServer(), recipeProviderFactory);
	} // end gatherData()

	
	
	public static RegistryObject<Item> queueForGeneralCreativeTab(RegistryObject<Item> item)
	{
		YATM_GENERAL_CREATIVE_TAB_QUEUE.add(item);
		return item;		
	} // end queueForGeneralCreativeTab()

	
	
	
	
	
} // end outer class

//MinecraftForge.EVENT_BUS.register(this);

//modEventBus.addListener(this::commonSetup);

//private void commonSetup(final FMLCommonSetupEvent event)

// You can use SubscribeEvent and let the Event Bus discover methods to call
// @SubscribeEvent
// public void onServerStarting(ServerStartingEvent event)


// You can use EventBusSubscriber to automatically register all static methods
// in the class annotated with @SubscribeEvent
//@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//public static class ClientModEvents
//{
//	@SubscribeEvent
//	public static void onClientSetup(FMLClientSetupEvent event)
//	{
//		// Some client setup code
//		LOGGER.info("HELLO FROM CLIENT SETUP");
//		LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
//	}
//
//}