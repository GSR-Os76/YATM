package com.gsr.gsr_yatm;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;

import com.gsr.gsr_yatm.block.device.boiler.BoilerScreen;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorScreen;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderScreen;
import com.gsr.gsr_yatm.fluid.item.GlassBottleItemStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
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
	
	private static final ArrayList<RegistryObject<? extends Item>> YATM_GENERAL_CREATIVE_TAB_QUEUE = new ArrayList<>();

	public static CreativeModeTab YATM_GENERAL;
	
	

	public YetAnotherTechMod()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus eventBus = MinecraftForge.EVENT_BUS;
		
		YATMBlocks.BLOCKS.register(modEventBus);
		YATMItems.ITEMS.register(modEventBus);
		YATMMenuTypes.MENU_TYPES.register(modEventBus);
		YATMBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
		YATMRecipeTypes.RECIPE_TYPES.register(modEventBus);
		YATMRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
		YATMFluids.FLUIDS.register(modEventBus);
		YATMFluidTypes.FLUID_TYPES.register(modEventBus);
		YATMMobEffects.MOB_EFFECTS.register(modEventBus);
		
		modEventBus.addListener(this::creativeModeTabsBuildContent);
		modEventBus.addListener(this::creativeModeTabsRegister);
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::gatherData);
		
		eventBus.addListener(this::onEntityDamaged);
		eventBus.addGenericListener(ItemStack.class, this::attachItemStackCapabilities);
	} // end constructor
	
	
	
	// should probably move these events into a separate class

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
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.EXTRACTOR_MENU.get(), ExtractorScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.EXTRUDER_MENU.get(), ExtruderScreen::new));
		
	} // end clientSetup()

	private void gatherData(GatherDataEvent event)
	{
		DataProvider.Factory<YATMItemModelProvider> itemModelProviderFactory = (o) -> new YATMItemModelProvider(o, event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeClient(), itemModelProviderFactory);

		DataProvider.Factory<YATMBlockStateProvider> blockStateProviderFactory = (o) -> new YATMBlockStateProvider(o, YetAnotherTechMod.MODID, event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeClient(), blockStateProviderFactory);

		DataProvider.Factory<YATMLanguageProviderUnitedStatesEnglish> unitedStatesEnglishLanguageProviderFactory = (o) -> new YATMLanguageProviderUnitedStatesEnglish(o);
		event.getGenerator().addProvider(event.includeClient(), unitedStatesEnglishLanguageProviderFactory);



		DataProvider.Factory<YATMBlockTags> blockTagProviderFactory = new DataProvider.Factory<YATMBlockTags>()
		{
			YATMBlockTags backing = null;

			@Override
			public YATMBlockTags create(PackOutput output)
			{
				if (backing == null)
				{
					backing = new YATMBlockTags(output, event.getLookupProvider(), MODID, event.getExistingFileHelper());
				}
				return backing;
			} // end create()

		};
		event.getGenerator().addProvider(event.includeServer(), blockTagProviderFactory);

		DataProvider.Factory<YATMItemTags> itemTagProviderFactory = (pOP) -> new YATMItemTags(pOP, event.getLookupProvider(), blockTagProviderFactory.create(pOP).contentsGetter(), MODID, event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeServer(), itemTagProviderFactory);

		DataProvider.Factory<YATMEntityTypeTags> entityTypeTagProviderFactory = (pOP) -> new YATMEntityTypeTags(pOP, event.getLookupProvider(), event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeServer(), entityTypeTagProviderFactory);

		DataProvider.Factory<YATMRecipeProvider> recipeProviderFactory = (o) -> new YATMRecipeProvider(o);
		event.getGenerator().addProvider(event.includeServer(), recipeProviderFactory);
	} // end gatherData()

	
	
	private void onEntityDamaged(LivingDamageEvent event) 
	{
		if(event.getSource().is(DamageTypes.WITHER)) 
		{
			LivingEntity entity = event.getEntity();
			Collection<MobEffectInstance> effects = entity.getActiveEffects();
			for(MobEffectInstance effect : effects) 
			{
				if(effect.getEffect() == YATMMobEffects.SOUL_AFFLICTION.get()) 
				{
					entity.removeEffect(YATMMobEffects.SOUL_AFFLICTION.get());
					entity.addEffect(new MobEffectInstance(
							MobEffects.WITHER, 
							effect.getDuration(), 
							effect.getAmplifier(), 
							effect.isAmbient(), 
							effect.isVisible(),
							effect.showIcon()));
					break;
				}
			}
		}
	} // end onEntityDamaged()
	
	private void attachItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) 
	{
		if(event.getObject().getItem() == Items.GLASS_BOTTLE) 
		{
			event.addCapability(new ResourceLocation(MODID, "glass_bottle_fluid_handler"), new GlassBottleItemStack(event.getObject()));
		}
	} // end attachItemStackCapabilities()
	
	
	
	
	public static <T extends Item> RegistryObject<T> queueForGeneralCreativeTab(RegistryObject<T> item)
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