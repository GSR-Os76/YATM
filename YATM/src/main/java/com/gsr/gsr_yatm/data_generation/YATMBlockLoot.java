package com.gsr.gsr_yatm.data_generation;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.FaceBlock;
import com.gsr.gsr_yatm.block.plant.OnceFruitingPlantStages;
import com.gsr.gsr_yatm.block.plant.aurum.AurumBlock;
import com.gsr.gsr_yatm.block.plant.basin_of_tears.BasinOfTearsFloralBlock;
import com.gsr.gsr_yatm.block.plant.carbum.CarbumBlock;
import com.gsr.gsr_yatm.block.plant.ferrum.FerrumBlock;
import com.gsr.gsr_yatm.block.plant.fire_eater_lily.FireEaterLilyBlock;
import com.gsr.gsr_yatm.block.plant.folium.FoliumBlock;
import com.gsr.gsr_yatm.block.plant.ice_coral.IceCoralBlock;
import com.gsr.gsr_yatm.block.plant.parasite.ShulkwartBlock;
import com.gsr.gsr_yatm.block.plant.prismarine_crystal_moss.PrismarineCrystalMossBlock;
import com.gsr.gsr_yatm.block.plant.vine.OnceFruitVineBodyBlock;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class YATMBlockLoot extends VanillaBlockLoot
{

	@Override
	protected void generate()
	{
		this.dropSelf(YATMBlocks.RUBBER_MERISTEM.get());
		this.dropPottedContents(YATMBlocks.POTTED_RUBBER_MERISTEM.get());
		this.add(YATMBlocks.RUBBER_LEAVES_YOUNG.get(), (b) -> this.createLeavesDrops(b, YATMBlocks.RUBBER_MERISTEM.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get(), (b) -> this.createLeavesDrops(b, YATMBlocks.RUBBER_MERISTEM.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(YATMBlocks.RUBBER_LEAVES_OLD.get(), (b) -> this.createLeavesDrops(b, YATMBlocks.RUBBER_MERISTEM.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.dropSelf(YATMBlocks.RUBBER_ROOTS.get());
		this.dropSelf(YATMBlocks.RUBBER_LOG.get());
		this.dropSelf(YATMBlocks.RUBBER_WOOD.get());
		this.dropSelf(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get());
		this.dropSelf(YATMBlocks.STRIPPED_RUBBER_LOG.get());
		this.dropSelf(YATMBlocks.STRIPPED_RUBBER_WOOD.get());
		this.dropSelf(YATMBlocks.RUBBER_PLANKS.get());
		this.dropSelf(YATMBlocks.FANCY_RUBBER_PLANKS.get());
		this.dropSelf(YATMBlocks.RUBBER_STAIRS.get());
		this.add(YATMBlocks.RUBBER_SLAB.get(), (b) -> this.createSlabItemTable(b));
		this.dropSelf(YATMBlocks.RUBBER_FENCE.get());
		this.dropSelf(YATMBlocks.RUBBER_FENCE_GATE.get());
		this.add(YATMBlocks.RUBBER_DOOR.get(), (b) ->  this.createDoorTable(b));
		this.dropSelf(YATMBlocks.RUBBER_TRAPDOOR.get());
		this.dropSelf(YATMBlocks.RUBBER_PRESSURE_PLATE.get());
		this.dropSelf(YATMBlocks.RUBBER_BUTTON.get());
		this.dropSelf(YATMBlocks.RUBBER_SIGN.get());
		this.dropSelf(YATMBlocks.RUBBER_WALL_SIGN.get());
		this.dropSelf(YATMBlocks.RUBBER_HANGING_SIGN.get());
		this.dropSelf(YATMBlocks.RUBBER_WALL_HANGING_SIGN.get());
		this.dropSelf(YATMBlocks.LEAF_MULCH.get());

		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get());
		this.dropPottedContents(YATMBlocks.POTTED_SOUL_AFFLICTED_RUBBER_MERISTEM.get());
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), (b) -> this.createLeavesDrops(b, YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), (b) -> this.createLeavesDrops(b, YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), (b) -> this.createLeavesDrops(b, YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_ROOTS.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_PLANKS.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get());
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get(), (b) -> this.createSlabItemTable(b));
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE_GATE.get());
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get(), (b) ->  this.createDoorTable(b));
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_BUTTON.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_SIGN.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_SIGN.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_HANGING_SIGN.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_RUBBER_WALL_HANGING_SIGN.get());
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get());
		
		this.add(YATMBlocks.AURUM.get(), (b) -> this.createAurumTable());
		this.dropPottedContents(YATMBlocks.POTTED_AURUM_DEMINUTUS.get());
				
		//this.add(YATMBlocks.BASIN_OF_TEARS_VEGETATION.get(), this.createUniformTable(YATMItems.TEAR_LEAF.get(), 0f, 3f, LootItemBlockStatePropertyCondition.hasBlockStateProperties(YATMBlocks.BASIN_OF_TEARS_VEGETATION.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BasinOfTearsVegetationBlock.AGE, YATMBlocks.BASIN_OF_TEARS_VEGETATION.get().getMaxAge()))));
		this.add(YATMBlocks.BASIN_OF_TEARS_FLORAL.get(), this.createBasinOfTearsFloralTable());
		
		this.dropSelf(YATMBlocks.CANDLELILY.get());		
		this.dropPottedContents(YATMBlocks.POTTED_CANDLELILY.get());
		
		this.add(YATMBlocks.CARBUM.get(), this.createCarbumTable());		
		this.dropPottedContents(YATMBlocks.POTTED_CARBUM.get());
		
		this.dropOther(YATMBlocks.CARCASS_ROOT_FOLIAGE.get(), YATMItems.CARCASS_ROOT_CUTTING.get());
		this.dropSelf(YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get());
		this.dropSelf(YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get());
		this.dropPottedContents(YATMBlocks.POTTED_CARCASS_ROOT_FOLIAGE.get());
		
		this.add(YATMBlocks.COTTON.get(), this.createCropDrops(YATMBlocks.COTTON.get(), YATMItems.COTTON_BOLLS.get(), YATMItems.COTTON_SEEDS.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(YATMBlocks.COTTON.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7))));
		
		this.add(YATMBlocks.FERRUM.get(), this.createFerrumTable());		
		this.dropPottedContents(YATMBlocks.POTTED_FERRUM.get());
		
		this.add(YATMBlocks.FIRE_EATER_LILY.get(), this.createFireEaterLilyTable());
		this.dropPottedContents(YATMBlocks.POTTED_FIRE_EATER_LILY.get());
		
		this.add(YATMBlocks.FOLIUM.get(), this.createFoliumTable());		
		this.dropPottedContents(YATMBlocks.POTTED_FOLIUM.get());
		
		this.add(YATMBlocks.ICE_CORAL.get(), (b) -> this.createIceCoralTable());
		this.dropSelf(YATMBlocks.BLEACHED_ICE_CORAL_OLD.get());
		this.dropSelf(YATMBlocks.BLEACHED_ICE_CORAL_ADOLESCENT.get());
		this.dropSelf(YATMBlocks.BLEACHED_ICE_CORAL_YOUNG.get());
		this.dropSelf(YATMBlocks.BLEACHED_ICE_CORAL_POLYP.get());
		this.dropPottedContents(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_OLD.get());
		this.dropPottedContents(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_ADOLESCENT.get());
		this.dropPottedContents(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_YOUNG.get());
		this.dropPottedContents(YATMBlocks.POTTED_BLEACHED_ICE_CORAL_POLYP.get());
		
		this.dropSelf(YATMBlocks.PHANTASMAL_SHELF_FUNGUS.get());
		this.dropSelf(YATMBlocks.PITCHER_CLUSTER.get());
		this.add(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get(), (b) -> this.createPrismarineCrystalMossTable());
		this.dropOther(YATMBlocks.FALLEN_SHULKWART_SPORES.get(), YATMItems.SHULKWART_SPORES.get());
		this.addShulkwart(YATMBlocks.SHULKWART.get(), YATMItems.SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.WHITE_SHULKWART.get(), YATMItems.WHITE_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.ORANGE_SHULKWART.get(), YATMItems.ORANGE_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.MAGENTA_SHULKWART.get(), YATMItems.MAGENTA_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.LIGHT_BLUE_SHULKWART.get(), YATMItems.LIGHT_BLUE_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.YELLOW_SHULKWART.get(), YATMItems.YELLOW_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.LIME_SHULKWART.get(), YATMItems.LIME_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.PINK_SHULKWART.get(), YATMItems.PINK_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.GRAY_SHULKWART.get(), YATMItems.GRAY_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.LIGHT_GRAY_SHULKWART.get(), YATMItems.LIGHT_GRAY_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.CYAN_SHULKWART.get(), YATMItems.CYAN_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.PURPLE_SHULKWART.get(), YATMItems.PURPLE_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.BLUE_SHULKWART.get(), YATMItems.BLUE_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.BROWN_SHULKWART.get(), YATMItems.BROWN_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.GREEN_SHULKWART.get(), YATMItems.GREEN_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.RED_SHULKWART.get(), YATMItems.RED_SHULKWART_HORN.get());
		this.addShulkwart(YATMBlocks.BLACK_SHULKWART.get(), YATMItems.BLACK_SHULKWART_HORN.get());
		
		this.add(YATMBlocks.SPIDER_VINE.get(), (b) -> LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(b).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OnceFruitVineBodyBlock.FRUITING_STAGE, OnceFruitingPlantStages.FRUITING))).add(LootItem.lootTableItem(YATMItems.SPIDER_VINE_FRUITS.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))));
		this.add(YATMBlocks.SPIDER_VINE_MERISTEM.get(), BlockLootSubProvider.noDrop());

		this.dropSelf(YATMBlocks.VARIEGATED_CACTUS.get());
		this.dropPottedContents(YATMBlocks.POTTED_VARIEGATED_CACTUS.get());
		
		
		
		this.dropOther(YATMBlocks.HANGING_POT_HOOK.get(), Items.CHAIN);
		
		this.add(YATMBlocks.FOLIAR_STEEL_ORE.get(), this.createOreDrop(YATMBlocks.FOLIAR_STEEL_ORE.get(), YATMItems.FOLIAR_STEEL.get()));
		this.add(YATMBlocks.DEEPSLATE_FOLIAR_STEEL_ORE.get(), this.createOreDrop(YATMBlocks.DEEPSLATE_FOLIAR_STEEL_ORE.get(), YATMItems.FOLIAR_STEEL.get()));
		this.dropSelf(YATMBlocks.FOLIAR_STEEL_BLOCK.get());
		this.dropSelf(YATMBlocks.RUBBER_BLOCK.get());
		this.dropSelf(YATMBlocks.ROOTED_SOUL_SOIL.get());
		
		
		
		this.dropSelf(YATMBlocks.SAP_COLLECTOR.get());
		this.dropOther(YATMBlocks.SAP_COLLECTOR_LATEX.get(), YATMItems.SAP_COLLECTOR_ITEM.get());
		this.dropOther(YATMBlocks.SAP_COLLECTOR_SOUL_SAP.get(), YATMItems.SAP_COLLECTOR_ITEM.get());
		
		
		this.dropSelf(YATMBlocks.SPINNING_WHEEL.get());
		
		this.dropSelf(YATMBlocks.LARGE_COPPER_HEAT_SINK.get());
		
		
		this.dropSelf(YATMBlocks.DATA_STORAGE_BLOCK.get());
		this.dropSelf(YATMBlocks.DATA_SCAN_COLLECTOR.get());
		this.dropSelf(YATMBlocks.DESTRUCTIVE_DATA_SCANNER.get());
		this.dropSelf(YATMBlocks.DATA_PROCESSOR.get());
		
		this.dropSelf(YATMBlocks.STEEL_BIOLER.get());
		this.dropSelf(YATMBlocks.STEEL_BOILER.get());
		this.dropSelf(YATMBlocks.STEEL_BOILER_TANK.get());
		this.dropSelf(YATMBlocks.STEEL_CRYSTALLIZER.get());
		this.dropSelf(YATMBlocks.STEEL_FURNACE_PLUS.get());
		this.dropSelf(YATMBlocks.STEEL_EXTRACTOR.get());
//		this.dropSelf(YATMBlocks.STEEL_EXTRUDER.get());
		this.dropSelf(YATMBlocks.STEEL_GRINDER.get());
		this.dropSelf(YATMBlocks.STEEL_INJECTOR.get());
			
		this.dropSelf(YATMBlocks.C_U_F_E_I.get());
		
		this.dropSelf(YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.CRUDE_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.ADVANCED_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.SUNS_COMPLEMENT_SOLAR_PANEL.get());
		
		
		
		this.faceDropSelf(YATMBlocks.CONDUIT_VINES.get());
		
//		this.dropSelf(YATMBlocks.ONE_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.EIGHT_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.SIXTYFOUR_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get());
//			
//		this.dropSelf(YATMBlocks.ENAMELED_ONE_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get());
//		
//		this.dropSelf(YATMBlocks.INSULATED_ONE_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.INSULATED_EIGHT_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get());
//		this.dropSelf(YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get());
//		
//		this.dropSelf(YATMBlocks.STEEL_FLUID_CONDUIT.get());
	} // end generate()

	@Override
	protected Iterable<Block> getKnownBlocks()
	{
		return YATMBlocks.BLOCKS.getEntries().stream().map((ro) -> ro.get()).toList();
	} // end getKnownBlocks()

	
	
	protected @NotNull LootTable.Builder createUniformTable(@NotNull Item item, float min, float max)
	{
		return createUniformTable(item, min, max, null); 					
	} // end createUniformTable()
	
	protected @NotNull LootTable.Builder createUniformTable(@NotNull Item item, float min, float max, @Nullable LootItemCondition.Builder condition)
	{
		 LootPoolEntryContainer.Builder<?> l = condition == null 
				 ?
				 LootItem.lootTableItem(item)
				 .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
				 :
				 LootItem.lootTableItem(item)
				 .when(condition)
				 .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
		 
		return LootTable.lootTable().withPool(LootPool.lootPool().add(l));
	} // end createUniformTable()
	
	protected void faceDropSelf(@NotNull FaceBlock block) 
	{
		this.faceDropOther(block, block.asItem());
	} // end faceDropSelf()
	
	protected void faceDropOther(@NotNull FaceBlock block, @NotNull Item item) 
	{
		Function<Direction, LootPool.Builder> forFace = (d) -> 
		LootPool.lootPool()
		.when(
				LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(block)
				.setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(d), true)
						)
				)
		.add(LootItem
				.lootTableItem(item)
				.apply(
						SetItemCountFunction
						.setCount(ConstantValue.exactly(1.0f)
								)
						)
				)
		;

		BiFunction<Direction, LootTable.Builder, LootTable.Builder> addFace = (d, t) -> t.withPool(forFace.apply(d));
		
		LootTable.Builder table = addFace.apply(Direction.WEST, addFace.apply(Direction.EAST, addFace.apply(Direction.SOUTH, addFace.apply(Direction.NORTH, addFace.apply(Direction.DOWN, addFace.apply(Direction.UP, LootTable.lootTable()))))));
		this.add(block, (b) -> table);
	} // end faceDropSelf()
	
	protected @NotNull LootTable.Builder createAurumTable() 
	{
		LootItemCondition.Builder fiddleHeadConditions 
		= LootItemBlockStatePropertyCondition
		.hasBlockStateProperties(YATMBlocks.AURUM.get())
		.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AurumBlock.HALF, DoubleBlockHalf.LOWER));

		BiFunction<DoubleBlockHalf, Integer, LootItemCondition.Builder> forHalfAge = (hf, i) -> LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(YATMBlocks.AURUM.get())
				.setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(AurumBlock.HALF, hf))
				.and(LootItemBlockStatePropertyCondition
						.hasBlockStateProperties(YATMBlocks.AURUM.get())
						.setProperties(StatePropertiesPredicate.Builder.properties()
								.hasProperty(AurumBlock.AGE, i)));

		return LootTable.lootTable()
				.withPool(
						LootPool.lootPool()
						.when(fiddleHeadConditions)
						.add(LootItem.lootTableItem(YATMItems.AURUM_DEMINUTUS_FIDDLE_HEAD.get())
								.apply(
										SetItemCountFunction
										.setCount(
												ConstantValue
												.exactly(1.0f)
												)
										)
								)
						)
				.withPool(
						LootPool.lootPool()
						.when(forHalfAge.apply(DoubleBlockHalf.LOWER, 1))
						.add(LootItem.lootTableItem(YATMItems.AURUM_DEMINUTUS_FROND.get())
								.apply(
										SetItemCountFunction
										.setCount(
												ConstantValue
												.exactly(1.0f)
												)
										)
								)
						)
				.withPool(
						LootPool.lootPool()
						.when(forHalfAge.apply(DoubleBlockHalf.LOWER, 2).or(forHalfAge.apply(DoubleBlockHalf.LOWER, 3).or(forHalfAge.apply(DoubleBlockHalf.LOWER, 4))))
						.add(LootItem.lootTableItem(YATMItems.AURUM_DEMINUTUS_FROND.get())
								.apply(
										SetItemCountFunction
										.setCount(
												ConstantValue
												.exactly(2.0f)
												)
										)
								)
						)
				.withPool(
						LootPool.lootPool()
						.when(forHalfAge.apply(DoubleBlockHalf.UPPER, 3))
						.add(LootItem.lootTableItem(YATMItems.AURUM_DEMINUTUS_FROND.get())
								.apply(
										SetItemCountFunction
										.setCount(
												ConstantValue
												.exactly(2.0f)
												)
										)
								)
						)
				.withPool(
						LootPool.lootPool()
						.when(forHalfAge.apply(DoubleBlockHalf.UPPER, 4))
						.add(LootItem.lootTableItem(YATMItems.AURUM_DEMINUTUS_FROND.get())
								.apply(
										SetItemCountFunction
										.setCount(
												ConstantValue
												.exactly(4.0f)
												)
										)
								)
						);
	} // end createAurumDeminutusTable()
	
	protected @NotNull LootTable.Builder createBasinOfTearsFloralTable()
	{
		
		Function<Integer, LootPool.Builder> forFlowerCount = (count) -> 
		LootPool.lootPool()
				.when(LootItemBlockStatePropertyCondition
						.hasBlockStateProperties(YATMBlocks.BASIN_OF_TEARS_FLORAL.get())
						.setProperties(StatePropertiesPredicate.Builder.properties()
								.hasProperty(BasinOfTearsFloralBlock.AGE, YATMBlocks.BASIN_OF_TEARS_FLORAL.get().getMaxAge())
								.hasProperty(BasinOfTearsFloralBlock.FLOWER_COUNT, count))
						)
				.add(LootItem.lootTableItem(YATMItems.BASIN_OF_TEARS_SEED.get())
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 1f * count)))
						);
		
		return LootTable.lootTable()
				.withPool(forFlowerCount.apply(1))
				.withPool(forFlowerCount.apply(2))
				.withPool(forFlowerCount.apply(3))
				.withPool(forFlowerCount.apply(4));
		//young enough drop flower count
	} // end createBasinOfTearsFloralTable()
	
	protected @NotNull LootTable.Builder createCarbumTable() 
	{
		Function<Integer, LootPool.Builder> forAge = (age) -> LootPool.lootPool()
				.add(LootItem.lootTableItem(YATMItems.CARBUM_LEAF.get())
					.apply(SetItemCountFunction.setCount(
							ConstantValue.exactly(age == 7 ? 4 : (age / 2)))
						  )
					)
				.when(LootItemBlockStatePropertyCondition
						.hasBlockStateProperties(YATMBlocks.CARBUM.get())
						.setProperties(StatePropertiesPredicate.Builder.properties()
								.hasProperty(FerrumBlock.AGE, age)));
		
		
		
		LootTable.Builder table = LootTable.lootTable()
				.withPool(
						LootPool.lootPool()
						.add(LootItem.lootTableItem(YATMItems.CARBUM_MERISTEM.get())
								.apply(
										SetItemCountFunction
										.setCount(ConstantValue.exactly(1.0f))
										)
								)
						);
		for(Integer i : CarbumBlock.AGE.getAllValues().map((i) -> i.value()).toList()) 
		{
			table = table.withPool(forAge.apply(i));
		}		
		return table;				
	} // end createCarbumTable()
	
	protected @NotNull LootTable.Builder createFerrumTable() 
	{
		Function<Integer, LootPool.Builder> forAge = (age) -> LootPool.lootPool()
				.add(LootItem.lootTableItem(YATMItems.FERRUM_BRANCH.get())
					.apply(SetItemCountFunction.setCount(
							ConstantValue.exactly(age == 7 ? 4 : (age / 2)))
						  )
					)
				.when(LootItemBlockStatePropertyCondition
						.hasBlockStateProperties(YATMBlocks.FERRUM.get())
						.setProperties(StatePropertiesPredicate.Builder.properties()
								.hasProperty(FerrumBlock.AGE, age)));
		
		
		
		LootTable.Builder table = LootTable.lootTable()
				.withPool(
						LootPool.lootPool()
						.add(LootItem.lootTableItem(YATMItems.FERRUM_ROOTSTOCK.get())
								.apply(
										SetItemCountFunction
										.setCount(ConstantValue.exactly(1.0f))
										)
								)
						)
				.withPool(LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.IRON_NUGGET)
								.when(LootItemBlockStatePropertyCondition
										.hasBlockStateProperties(YATMBlocks.FERRUM.get())
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(FerrumBlock.HAS_FRUIT, true))
									 )
								.apply(
										SetItemCountFunction
										.setCount(UniformGenerator.between(1.0f, (float)FerrumBlock.MAX_FRUIT_COUNT))
										)
								)
						);
		for(Integer i : FerrumBlock.AGE.getAllValues().map((i) -> i.value()).toList()) 
		{
			table = table.withPool(forAge.apply(i));
		}		
		return table;				
	} // end createFerrumTable()
	
	protected @NotNull LootTable.Builder createFireEaterLilyTable() 
	{
		LootItemCondition.Builder fullGrown = LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(YATMBlocks.FIRE_EATER_LILY.get())
				.setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(FireEaterLilyBlock.AGE, YATMBlocks.FIRE_EATER_LILY.get().getMaxAge()));

		return LootTable.lootTable()
				.withPool(
						LootPool.lootPool()
						.add(LootItem.lootTableItem(YATMItems.FIRE_EATER_LILY_BULB.get())
								.apply(
										SetItemCountFunction
										.setCount(
												ConstantValue
												.exactly(1.0f)
												)
										)
								)
						)
				.withPool(
						LootPool.lootPool()
						.when(fullGrown)
						.add(LootItem.lootTableItem(YATMItems.FIRE_EATER_LILY_FOLIAGE.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 3f)))
								)
						);
	} // end createFireEaterLilyTable()
	
	
	protected @NotNull LootTable.Builder createFoliumTable() 
	{
		BiFunction<DoubleBlockHalf, Integer, LootItemCondition.Builder> forHalfAge = (hf, i) -> LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(YATMBlocks.FOLIUM.get())
				.setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(FoliumBlock.HALF, hf).hasProperty(FoliumBlock.AGE, i));
				/*.and(LootItemBlockStatePropertyCondition
						.hasBlockStateProperties(YATMBlocks.FOLIUM.get())
						.setProperties(StatePropertiesPredicate.Builder.properties()
								.hasProperty(FoliumBlock.AGE, i)))*/

		return LootTable.lootTable()
				.withPool(
						LootPool.lootPool()
						.when(LootItemBlockStatePropertyCondition
								.hasBlockStateProperties(YATMBlocks.FOLIUM.get())
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(FoliumBlock.HALF, DoubleBlockHalf.LOWER)))
						.add(LootItem.lootTableItem(YATMItems.FOLIUM_RHIZOME.get())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)))
								)
						)
				.withPool(
						LootPool.lootPool()
						.when(forHalfAge.apply(DoubleBlockHalf.LOWER, 1))
						.add(LootItem.lootTableItem(YATMItems.FOLIAR_STEEL.get())
								.apply(
										SetItemCountFunction
										.setCount(
												UniformGenerator
												.between(0.0f, 1.0f)
												)
										)
								)
						)
				.withPool(
						// TODO, verify that this still works identical as the before
						LootPool.lootPool()
						.when(forHalfAge.apply(DoubleBlockHalf.LOWER, 2)
								.or(forHalfAge.apply(DoubleBlockHalf.UPPER, 4))
								.or(forHalfAge.apply(DoubleBlockHalf.UPPER, 3))
							 )
						.add(LootItem.lootTableItem(YATMItems.FOLIAR_STEEL.get())
								.apply(
										SetItemCountFunction
										.setCount(
												ConstantValue
												.exactly(1.0f)
												)
										)
								)
						)
				.withPool(
						LootPool.lootPool()
						.when(forHalfAge.apply(DoubleBlockHalf.LOWER, 4)
								.or(forHalfAge.apply(DoubleBlockHalf.LOWER, 3)))
						.add(LootItem.lootTableItem(YATMItems.FOLIAR_STEEL.get())
								.apply(
										SetItemCountFunction
										.setCount(
												ConstantValue
												.exactly(2.0f)
												)
										)
								)
						);
	} // end createFoliumTable()
	
	protected @NotNull LootTable.Builder createIceCoralTable() 
	{
		LootItemCondition.Builder fullGrown = LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(YATMBlocks.ICE_CORAL.get())
				.setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(IceCoralBlock.AGE, YATMBlocks.ICE_CORAL.get().getMaxAge()));

		return LootTable.lootTable()
				.withPool(
						LootPool.lootPool()
						.when(fullGrown)
						.add(LootItem.lootTableItem(YATMItems.ICE_CORAL_POLYP.get())
								.apply(
										SetItemCountFunction
										.setCount(
												ConstantValue
												.exactly(3.0f)
												)
										)
								)
						);
	} // end createAurumDeminutusTable()
	
	protected @NotNull LootTable.Builder createPrismarineCrystalMossTable()
	{
			LootItemCondition.Builder tideTemplateConditions 
			= LootItemRandomChanceCondition
			.randomChance(1.0f / 576.0f)
			.or(WeatherCheck
					.weather()
					.setRaining(true)
					.setThundering(true)
					.and(LootItemRandomChanceCondition
							.randomChance(1.0f / 96.0f)));
			
			BiFunction<Direction, Integer, LootItemCondition.Builder> forFaceAge = (d, i) -> LootItemBlockStatePropertyCondition
					.hasBlockStateProperties(YATMBlocks.PRISMARINE_CRYSTAL_MOSS.get())
					.setProperties(StatePropertiesPredicate.Builder.properties()
							.hasProperty(PrismarineCrystalMossBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(d), true)
							.hasProperty(PrismarineCrystalMossBlock.AGE_PROPERTIES_BY_DIRECTION.get(d), i));

			BiFunction<Direction, LootTable.Builder, LootTable.Builder> addFace = (d, t) -> 
			t.withPool(
					LootPool.lootPool()
					.when(forFaceAge.apply(d, 0))
					.add(LootItem
							.lootTableItem(YATMItems.PRISMARINE_CRYSTAL_MOSS_SPORES.get())
							.apply(
									SetItemCountFunction
									.setCount(ConstantValue.exactly(1.0f)
											)
									)
							)
					)
			.withPool(
					LootPool.lootPool()
					.when(forFaceAge.apply(d, 1))
					.add(
							LootItem
							.lootTableItem(Items.PRISMARINE_SHARD)
							.apply(
									SetItemCountFunction
									.setCount( UniformGenerator.between(0.0f, 3.0f)
											)
									)
							)
					.add(LootItem
							.lootTableItem(Items.PRISMARINE_CRYSTALS)
							.apply(
									SetItemCountFunction
									.setCount( UniformGenerator.between(0.0f, 1.0f)
											)
									)
							)
					.add(LootItem
							.lootTableItem(YATMItems.PRISMARINE_CRYSTAL_MOSS_SPORES.get())
							.apply(
									SetItemCountFunction
									.setCount( UniformGenerator.between(1.0f, 2.0f)
											)
									)
							)			
					)
			.withPool(
					LootPool.lootPool()
					.when(forFaceAge.apply(d, 1).and(tideTemplateConditions))
					.add(
							LootItem
							.lootTableItem(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE)
							.apply(
									SetItemCountFunction
									.setCount( ConstantValue.exactly(1.0f)
											)
									)
							)
					);
			
			return addFace.apply(Direction.WEST, addFace.apply(Direction.EAST, addFace.apply(Direction.SOUTH, addFace.apply(Direction.NORTH, addFace.apply(Direction.DOWN, addFace.apply(Direction.UP, LootTable.lootTable()))))));
	} // end createPrismarineCrystalMossTabl()
	
	protected void addShulkwart(@NotNull Block shulkwart, @NotNull Item horn) 
	{
	      this.add(shulkwart, this.createShulkwartTable(shulkwart, horn));
	} // end add Shulkwart
	
	protected @NotNull LootTable.Builder createShulkwartTable(@NotNull Block shulkwart, @NotNull Item horn)
	{
		LootItemCondition.Builder dropConditions = LootItemBlockStatePropertyCondition.hasBlockStateProperties(shulkwart).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ShulkwartBlock.AGE, 7));
		return this.applyExplosionDecay(shulkwart, LootTable.lootTable().withPool(LootPool.lootPool().when(dropConditions).add(LootItem.lootTableItem(horn).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0f))).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2)))));
	} // end CreateShulkwartTable()
	
} // end class