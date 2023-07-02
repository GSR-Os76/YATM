package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.block.device.bioler.BiolerScreen;
import com.gsr.gsr_yatm.block.device.boiler.BoilerScreen;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerScreen;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorScreen;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderScreen;
import com.gsr.gsr_yatm.block.device.grinder.GrinderScreen;
import com.gsr.gsr_yatm.data_generation.YATMBiomeTags;
import com.gsr.gsr_yatm.data_generation.YATMBlockStateProvider;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.data_generation.YATMEntityTypeTags;
import com.gsr.gsr_yatm.data_generation.YATMItemModelProvider;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProviderUnitedStatesEnglish;
import com.gsr.gsr_yatm.data_generation.YATMRecipeProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMFluidTypes;
import com.gsr.gsr_yatm.registry.YATMFluids;
import com.gsr.gsr_yatm.registry.YATMFoliagePlacerTypes;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.registry.YATMMobEffects;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.registry.YATMRootPlacerTypes;
import com.gsr.gsr_yatm.registry.YATMTreeDecoratorTypes;
import com.gsr.gsr_yatm.registry.YATMTrunkPlacerTypes;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class YATMModEvents
{
	public static void register(IEventBus modEventBus) 
	{
		YATMBlocks.BLOCKS.register(modEventBus);
		YATMItems.ITEMS.register(modEventBus);
		YATMMenuTypes.MENU_TYPES.register(modEventBus);
		YATMBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
		YATMRecipeTypes.RECIPE_TYPES.register(modEventBus);
		YATMRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
		YATMFluids.FLUIDS.register(modEventBus);
		YATMFluidTypes.FLUID_TYPES.register(modEventBus);
		YATMMobEffects.MOB_EFFECTS.register(modEventBus);
		YATMFoliagePlacerTypes.FOLIAGE_PLACER_TYPES.register(modEventBus);
		YATMTrunkPlacerTypes.TRUNK_PLACER_TYPES.register(modEventBus);
		YATMRootPlacerTypes.ROOT_PLACER_TYPES.register(modEventBus);
		YATMTreeDecoratorTypes.TREE_DECORATOR_TYPES.register(modEventBus);
		
		YATMBlocks.addFlowersToPots();
		
		YATMCreativeModTabs.register(modEventBus);
		modEventBus.addListener(YATMModEvents::clientSetup);
		modEventBus.addListener(YATMModEvents::gatherData);
	} // end register()
	
	
	
	private static void commonSetup(FMLCommonSetupEvent event) 
	{
		// TODO, add biome to the nether, biome manager seems to only support the overworld currently, and I wish to not break compatibility
		// NetherBiomes l
		// event.enqueueWork(() -> ; BiomeManager.addAdditionalOverworldBiomes(null))
	}
	
	private static void clientSetup(FMLClientSetupEvent event)
	{
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.BIOLER.get(), BiolerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.BOILER.get(), BoilerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.CRYSTALLIZER.get(), CrystallizerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.EXTRACTOR.get(), ExtractorScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.EXTRUDER.get(), ExtruderScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.GRINDER.get(), GrinderScreen::new));
		
	} // end clientSetup()

	private static void gatherData(GatherDataEvent event)
	{
		DataProvider.Factory<YATMItemModelProvider> itemModelProviderFactory = (o) -> new YATMItemModelProvider(o, event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeClient(), itemModelProviderFactory);

		DataProvider.Factory<YATMBlockStateProvider> blockStateProviderFactory = (o) -> new YATMBlockStateProvider(o, YetAnotherTechMod.MODID, event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeClient(), blockStateProviderFactory);

		DataProvider.Factory<YATMLanguageProviderUnitedStatesEnglish> unitedStatesEnglishLanguageProviderFactory = (o) -> new YATMLanguageProviderUnitedStatesEnglish(o);
		event.getGenerator().addProvider(event.includeClient(), unitedStatesEnglishLanguageProviderFactory);


		
		DataProvider.Factory<YATMBiomeTags> biomeTagProviderFactory = (pOP) -> new YATMBiomeTags(pOP, event.getLookupProvider(), event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeServer(), biomeTagProviderFactory);


		DataProvider.Factory<YATMBlockTags> blockTagProviderFactory = new DataProvider.Factory<YATMBlockTags>()
		{
			YATMBlockTags backing = null;

			@Override
			public YATMBlockTags create(PackOutput output)
			{
				if (backing == null)
				{
					backing = new YATMBlockTags(output, event.getLookupProvider(), event.getExistingFileHelper());
				}
				return backing;
			} // end create()

		};
		event.getGenerator().addProvider(event.includeServer(), blockTagProviderFactory);

		DataProvider.Factory<YATMItemTags> itemTagProviderFactory = (pOP) -> new YATMItemTags(pOP, event.getLookupProvider(), blockTagProviderFactory.create(pOP).contentsGetter(), event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeServer(), itemTagProviderFactory);

		DataProvider.Factory<YATMEntityTypeTags> entityTypeTagProviderFactory = (pOP) -> new YATMEntityTypeTags(pOP, event.getLookupProvider(), event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeServer(), entityTypeTagProviderFactory);

		DataProvider.Factory<YATMRecipeProvider> recipeProviderFactory = (o) -> new YATMRecipeProvider(o);
		event.getGenerator().addProvider(event.includeServer(), recipeProviderFactory);
	} // end gatherData()
	
} // end class