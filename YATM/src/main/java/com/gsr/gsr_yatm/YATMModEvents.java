package com.gsr.gsr_yatm;

import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.crafting.bioreactor.BioreactorScreen;
import com.gsr.gsr_yatm.block.device.crafting.boiler.BoilerScreen;
import com.gsr.gsr_yatm.block.device.crafting.crucible.CrucibleScreen;
import com.gsr.gsr_yatm.block.device.crafting.crystallizer.CrystallizerScreen;
import com.gsr.gsr_yatm.block.device.crafting.extractor.ExtractorScreen;
import com.gsr.gsr_yatm.block.device.crafting.grafting_table.GraftingScreen;
import com.gsr.gsr_yatm.block.device.crafting.grinder.GrinderScreen;
import com.gsr.gsr_yatm.block.device.crafting.heat_furnace.HeatFurnaceScreen;
import com.gsr.gsr_yatm.block.device.crafting.injector.InjectorScreen;
import com.gsr.gsr_yatm.block.device.crafting.still.StillScreen;
import com.gsr.gsr_yatm.block.device.creative.current_source.CreativeCurrentSourceScreen;
import com.gsr.gsr_yatm.block.device.current_storer.base.CurrentStorerScreen;
import com.gsr.gsr_yatm.block.device.solar.panel.base.SolarPanelScreen;
import com.gsr.gsr_yatm.block.device.tank.TankScreen;
import com.gsr.gsr_yatm.block.hanging_pot.HangingPotHookRenderer;
import com.gsr.gsr_yatm.data_generation.YATMBiomeModifierProvider;
import com.gsr.gsr_yatm.data_generation.YATMBiomeTags;
import com.gsr.gsr_yatm.data_generation.YATMBlockStateProvider;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.data_generation.YATMConfiguredFeatureProvider;
import com.gsr.gsr_yatm.data_generation.YATMEntityTypeTags;
import com.gsr.gsr_yatm.data_generation.YATMFluidTags;
import com.gsr.gsr_yatm.data_generation.YATMItemModelProvider;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProviderUnitedStatesEnglish;
import com.gsr.gsr_yatm.data_generation.YATMLootTableProvider;
import com.gsr.gsr_yatm.data_generation.YATMRecipeProvider;
import com.gsr.gsr_yatm.entity.boat.YATMBoatRenderer;
import com.gsr.gsr_yatm.entity.boat.YATMBoatType;
import com.gsr.gsr_yatm.item.armor.LazyArmorItem;
import com.gsr.gsr_yatm.item.tool.PoweredToolScreen;
import com.gsr.gsr_yatm.recipe.bioreaction.CompostableBioreactionRecipeProvider;
import com.gsr.gsr_yatm.recipe.smelting.WrappedSmeltingRecipeProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMCreativeModeTabs;
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
import com.gsr.gsr_yatm.utilities.capability.HeatUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtil;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class YATMModEvents
{
	public static final ResourceLocation CURRENT_STORED_ITEM_PROPERTY = new ResourceLocation(YetAnotherTechMod.MODID, "power");
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

		YATMCreativeModeTabs.register(modEventBus);
		
		modEventBus.addListener(YATMModEvents::clientSetup);
		modEventBus.addListener(YATMModEvents::commonSetup);
		modEventBus.addListener(YATMModEvents::gatherData);
		modEventBus.addListener(YATMModEvents::registerEntityRenderers);
		modEventBus.addListener(YATMModEvents::registerParticleProviders);
	} // end register()

	
	
	private static void clientSetup(FMLClientSetupEvent event)
	{
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.GRAFTING_TABLE.get(), GraftingScreen::new));

		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.BIOREACTOR.get(), BioreactorScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.BOILER.get(), BoilerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.CRUCIBLE.get(), CrucibleScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.CRYSTALLIZER.get(), CrystallizerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.EXTRACTOR.get(), ExtractorScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.HEAT_FURNACE.get(), HeatFurnaceScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.GRINDER.get(), GrinderScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.INJECTOR.get(), InjectorScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.STILL.get(), StillScreen::new));
		
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.CURRENT_STORER.get(), CurrentStorerScreen::new));
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.TANK.get(), TankScreen::new));
		
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.SOLAR_PANEL.get(), SolarPanelScreen::new));
		
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.CREATIVE_CURRENT_SOURCE.get(), CreativeCurrentSourceScreen::new));
		
		event.enqueueWork(() -> MenuScreens.register(YATMMenuTypes.POWERED_TOOL.get(), PoweredToolScreen::new));
		
		
		
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
		
		
		
		Function<ItemStack, Float> getStoredProportion = (is) -> {
			ICurrentHandler c = is.getCapability(YATMCapabilities.CURRENT).orElse(null);
			return ((float)c.stored()) / ((float)c.capacity());
		};
		event.enqueueWork(() -> ItemProperties.register(YATMItems.CURRENT_TUBER.get(), YATMModEvents.CURRENT_STORED_ITEM_PROPERTY, (is, l, e, id) -> getStoredProportion.apply(is)));
		event.enqueueWork(() -> ItemProperties.register(YATMItems.CURRENT_BATTERY.get(), YATMModEvents.CURRENT_STORED_ITEM_PROPERTY, (is, l, e, id) -> getStoredProportion.apply(is)));
		event.enqueueWork(() -> ItemProperties.register(YATMItems.ADVANCED_CURRENT_BATTERY.get(), YATMModEvents.CURRENT_STORED_ITEM_PROPERTY, (is, l, e, id) -> getStoredProportion.apply(is)));
	} // end clientSetup()

	private static void commonSetup(FMLCommonSetupEvent event)
	{
		event.enqueueWork(() -> YATMItems.addCompostables());
		event.enqueueWork(() -> RecipeUtil.addDynamicRecipeProvider(new CompostableBioreactionRecipeProvider()));
		event.enqueueWork(() -> RecipeUtil.addDynamicRecipeProvider(new WrappedSmeltingRecipeProvider()));
		event.enqueueWork(() -> YATMBlocks.addSapCollectorVariants());
		// TODO, add for all types supported by default or situation if appropriate
		event.enqueueWork(() -> {
			if(!SlotUtil.isSterileCapProviderRegistered(ForgeCapabilities.FLUID_HANDLER)) 
			{
				SlotUtil.registerSterileCapProvider(ForgeCapabilities.FLUID_HANDLER, () -> LazyOptional.of(() -> new FluidTank(0)));
			}
		});
		event.enqueueWork(() -> {
			if(!SlotUtil.isSterileCapProviderRegistered(YATMCapabilities.CURRENT)) 
			{
				SlotUtil.registerSterileCapProvider(YATMCapabilities.CURRENT, () -> LazyOptional.of(() -> new CurrentHandler(0)));
			}
		});
		
		
		// TODO, add biome to the nether, biome manager seems to only support the
		// overworld currently, and I wish to not break compatibility
		// NetherBiomes l
		// event.enqueueWork(() -> ; BiomeManager.addAdditionalOverworldBiomes(null))
		// event.enqueueWork(() -> BiomeManager.addAdditionalOverworldBiomes(ResourceKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(YetAnotherTechMod.MODID, "rubber_forest"))));

		event.enqueueWork(() -> WoodType.register(YATMBlocks.RUBBER_WOOD_TYPE));
		event.enqueueWork(() -> WoodType.register(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD_TYPE));
		
		event.enqueueWork(LazyArmorItem.loader());
		
		event.enqueueWork(HeatUtil::addDefaultsTemperatureMappings);
	} // end commonSetup()

	private static void gatherData(@NotNull GatherDataEvent event)
	{
		event.getGenerator().addProvider(event.includeClient(), (DataProvider.Factory<?>)(o) -> new YATMBlockStateProvider(o, YetAnotherTechMod.MODID, event.getExistingFileHelper()));	
		event.getGenerator().addProvider(event.includeClient(), (DataProvider.Factory<?>)(o) -> new YATMItemModelProvider(o, event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeClient(), (DataProvider.Factory<?>)(o) -> new YATMLanguageProviderUnitedStatesEnglish(o));

		event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<?>)(o) -> new YATMBiomeModifierProvider(o, event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<?>)(o) -> new YATMBiomeTags(o, event.getLookupProvider(), event.getExistingFileHelper()));
		DataProvider.Factory<YATMBlockTags> blockTagProviderFactory = new BackedFunction<PackOutput, YATMBlockTags>((o) -> new YATMBlockTags(o, event.getLookupProvider(), event.getExistingFileHelper()))::apply;
		event.getGenerator().addProvider(event.includeServer(), blockTagProviderFactory);
		event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<?>)(o) -> new YATMConfiguredFeatureProvider(o, event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<?>)(o) -> new YATMEntityTypeTags(o, event.getLookupProvider(), event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<?>)(o) -> new YATMFluidTags(o, event.getLookupProvider(), event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<?>)(o) -> new YATMItemTags(o, event.getLookupProvider(), blockTagProviderFactory.create(o).contentsGetter(), event.getExistingFileHelper()));
		event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<?>)(o) -> YATMLootTableProvider.create(o));
		event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<?>)(o) -> new YATMRecipeProvider(o));
	} // end gatherData()

	private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(YATMBlockEntityTypes.HANGING_POT_HOOK.get(), HangingPotHookRenderer::new);
//		event.registerBlockEntityRenderer(YATMBlockEntityTypes.TANK.get(), TankRenderer::new);
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