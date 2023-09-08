package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.block.device.bioler.BiolerScreen;
import com.gsr.gsr_yatm.block.device.boiler.BoilerScreen;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerScreen;
import com.gsr.gsr_yatm.block.device.current_furnace.FurnacePlusScreen;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorScreen;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderScreen;
import com.gsr.gsr_yatm.block.device.grinder.GrinderScreen;
import com.gsr.gsr_yatm.block.device.injector.InjectorScreen;
import com.gsr.gsr_yatm.block.device.solar.BatterySolarPanelScreen;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelScreen;
import com.gsr.gsr_yatm.block.hanging_pot.HangingPotHookRenderer;
import com.gsr.gsr_yatm.data_generation.YATMBiomeTags;
import com.gsr.gsr_yatm.data_generation.YATMBlockStateProvider;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.data_generation.YATMEntityTypeTags;
import com.gsr.gsr_yatm.data_generation.YATMItemModelProvider;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProviderUnitedStatesEnglish;
import com.gsr.gsr_yatm.data_generation.YATMLootTableProvider;
import com.gsr.gsr_yatm.data_generation.YATMRecipeProvider;
import com.gsr.gsr_yatm.entity.boat.YATMBoatRenderer;
import com.gsr.gsr_yatm.entity.boat.YATMBoatType;
import com.gsr.gsr_yatm.recipe.bioling.CompostableBiolingRecipeProvider;
import com.gsr.gsr_yatm.recipe.smelting.WrappedSmeltingRecipeProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMCreativeModTabs;
import com.gsr.gsr_yatm.registry.YATMEntityTypes;
import com.gsr.gsr_yatm.registry.YATMFluidTypes;
import com.gsr.gsr_yatm.registry.YATMFluids;
import com.gsr.gsr_yatm.registry.YATMFoliagePlacerTypes;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.registry.YATMMobEffects;
import com.gsr.gsr_yatm.registry.YATMParticleTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.registry.YATMRootPlacerTypes;
import com.gsr.gsr_yatm.registry.YATMTreeDecoratorTypes;
import com.gsr.gsr_yatm.registry.YATMTrunkPlacerTypes;
import com.gsr.gsr_yatm.registry.custom.YATMArmorSets;
import com.gsr.gsr_yatm.registry.custom.YATMIngredientDeserializers;
import com.gsr.gsr_yatm.registry.custom.YATMRegistries;
import com.gsr.gsr_yatm.utilities.YATMModelLayers;
import com.gsr.gsr_yatm.utilities.YATMParticleProviders;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtil;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class YATMModEvents
{
	public static void register(IEventBus modEventBus)
	{
		YATMBlocks.BLOCKS.register(modEventBus);
		YATMBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);		
		YATMEntityTypes.ENTITY_TYPES.register(modEventBus);
		YATMFluids.FLUIDS.register(modEventBus);
		YATMFluidTypes.FLUID_TYPES.register(modEventBus);
		YATMFoliagePlacerTypes.FOLIAGE_PLACER_TYPES.register(modEventBus);
		YATMItems.ITEMS.register(modEventBus);
		YATMMenuTypes.MENU_TYPES.register(modEventBus);
		YATMMobEffects.MOB_EFFECTS.register(modEventBus);
		YATMParticleTypes.PARTICLE_TYPES.register(modEventBus);
		YATMRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
		YATMRecipeTypes.RECIPE_TYPES.register(modEventBus);
		YATMRootPlacerTypes.ROOT_PLACER_TYPES.register(modEventBus);
		YATMTreeDecoratorTypes.TREE_DECORATOR_TYPES.register(modEventBus);
		YATMTrunkPlacerTypes.TRUNK_PLACER_TYPES.register(modEventBus);
		
		YATMRegistries.classInitializer();
		YATMArmorSets.ARMOR_SETS.register(modEventBus);
		YATMIngredientDeserializers.INGREDIENT_DESERIALIZERS.register(modEventBus);

		YATMBlocks.addFlowersToPots();

		YATMCreativeModTabs.register(modEventBus);
		modEventBus.addListener(YATMModEvents::clientSetup);
		modEventBus.addListener(YATMModEvents::commonSetup);
		modEventBus.addListener(YATMModEvents::gatherData);
		modEventBus.addListener(YATMModEvents::registerEntityRenderers);
		modEventBus.addListener(YATMModEvents::registerParticleProviders);
	} // end register()

	
	
	private static void clientSetup(FMLClientSetupEvent event)
	{
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.BIOLER.get(), BiolerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.BOILER.get(), BoilerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.CRYSTALLIZER.get(), CrystallizerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.EXTRACTOR.get(), ExtractorScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.EXTRUDER.get(), ExtruderScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.FURNACE_PLUS.get(), FurnacePlusScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.GRINDER.get(), GrinderScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.INJECTOR.get(), InjectorScreen::new));
		
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.BATTERY_SOLAR_PANEL.get(), BatterySolarPanelScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.SOLAR_PANEL.get(), SolarPanelScreen::new));
		
		
		
		event.enqueueWork(() -> Sheets.addWoodType(YATMBlocks.RUBBER_WOOD_TYPE));
		event.enqueueWork(() -> Sheets.addWoodType(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD_TYPE));
		// event.enqueueWork(() -> YATMSheets.addBlock(HangingPotHookRenderer.DEFAULT_HANGING_POT_SUPPORT_CHAINS_LOCATION));
		
		
		
		LayerDefinition boat = BoatModel.createBodyModel();
		LayerDefinition chestBoat = ChestBoatModel.createBodyModel();
		// LayerDefinition defaulfHangingPotSupportChain = DefaultHangingPotSupportChainModel.createModel();
		for (YATMBoatType type : YATMBoatType.values())
		{
			ForgeHooksClient.registerLayerDefinition(YATMModelLayers.createYATMBoatModelName(type), () -> boat);
			ForgeHooksClient.registerLayerDefinition(YATMModelLayers.createYATMChestBoatModelName(type), () -> chestBoat);
		}
		// ForgeHooksClient.registerLayerDefinition(YATMModelLayers.DEAFULT_HANGING_POT_SUPPORT_CHAIN_MODEL_NAME, () -> defaulfHangingPotSupportChain);
	} // end clientSetup()

	private static void commonSetup(FMLCommonSetupEvent event)
	{
		event.enqueueWork(() -> YATMItems.addCompostables());
		event.enqueueWork(() -> RecipeUtil.addDynamicRecipeProvider(new CompostableBiolingRecipeProvider()));
		event.enqueueWork(() -> RecipeUtil.addDynamicRecipeProvider(new WrappedSmeltingRecipeProvider()));
		event.enqueueWork(() -> YATMBlocks.addSapCollectorVariants());
		
		// TODO, add biome to the nether, biome manager seems to only support the
		// overworld currently, and I wish to not break compatibility
		// NetherBiomes l
		// event.enqueueWork(() -> ; BiomeManager.addAdditionalOverworldBiomes(null))
		event.enqueueWork(() -> BiomeManager.addAdditionalOverworldBiomes(ResourceKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(YetAnotherTechMod.MODID, "rubber_forest"))));

		event.enqueueWork(() -> WoodType.register(YATMBlocks.RUBBER_WOOD_TYPE));
		event.enqueueWork(() -> WoodType.register(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD_TYPE));
	} // end commonSetup()

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



		DataProvider.Factory<YATMLootTableProvider> lootTableProviderFactory = (o) -> YATMLootTableProvider.create(o);
		event.getGenerator().addProvider(event.includeServer(), lootTableProviderFactory);

	} // end gatherData()

	private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(YATMBlockEntityTypes.HANGING_POT_HOOK.get(), HangingPotHookRenderer::new);
		event.registerBlockEntityRenderer(YATMBlockEntityTypes.YATM_HANGING_SIGN.get(), HangingSignRenderer::new);
		event.registerBlockEntityRenderer(YATMBlockEntityTypes.YATM_SIGN.get(), SignRenderer::new);
		
		event.<Boat>registerEntityRenderer(YATMEntityTypes.YATM_BOAT.get(), (c) -> new YATMBoatRenderer(c, false));
		event.<Boat>registerEntityRenderer(YATMEntityTypes.YATM_CHEST_BOAT.get(), (c) -> new YATMBoatRenderer(c, true));
	} // end clientSetup()

	private static void registerParticleProviders(RegisterParticleProvidersEvent event) 
	{
		event.registerSprite(YATMParticleTypes.DRIPPING_TAPPED_LOG_LATEX.get(), YATMParticleProviders.DRIPPING_TAPPED_LOG_LATEX);
		event.registerSprite(YATMParticleTypes.DRIPPING_TAPPED_LOG_SOUL_SAP.get(), YATMParticleProviders.DRIPPING_TAPPED_LOG_SOUL_SAP);
		event.registerSprite(YATMParticleTypes.FALLING_LATEX.get(), YATMParticleProviders.FALLING_TAPPED_LOG_LATEX);
		event.registerSprite(YATMParticleTypes.FALLING_SOUL_SAP.get(), YATMParticleProviders.FALLING_TAPPED_LOG_SOUL_SAP);
		event.registerSprite(YATMParticleTypes.LANDING_LATEX.get(), YATMParticleProviders.LANDING_LATEX);
		event.registerSprite(YATMParticleTypes.LANDING_SOUL_SAP.get(), YATMParticleProviders.LANDING_SOUL_SAP);
	} // end registerParticleProviders()
	
} // end class