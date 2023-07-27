package com.gsr.gsr_yatm.data_generation;

import java.util.function.BiFunction;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.plant.OnceFruitingPlantStages;
import com.gsr.gsr_yatm.block.plant.fern.AurumDeminutusBlock;
import com.gsr.gsr_yatm.block.plant.moss.PrismarineCrystalMossBlock;
import com.gsr.gsr_yatm.block.plant.parasite.ShulkwartBlock;
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
		this.dropSelf(YATMBlocks.SOUL_AFFLICTED_LEAF_MULCH.get());
		
		this.add(YATMBlocks.AURUM_DEMINUTUS.get(), (b) -> this.createAurumDeminutusTable());
		
		this.dropOther(YATMBlocks.CARCASS_ROOT_FOLIAGE.get(), YATMItems.CARCASS_ROOT_CUTTING.get());
		this.dropSelf(YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get());
		this.dropSelf(YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get());
		
	    this.add(YATMBlocks.COTTON.get(), this.createCropDrops(YATMBlocks.COTTON.get(), YATMItems.COTTON_BOLLS.get(), YATMItems.COTTON_SEEDS.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(YATMBlocks.COTTON.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7))));

		this.dropSelf(YATMBlocks.PHANTASMAL_SHELF_FUNGUS.get());
		
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

		
		
		this.dropSelf(YATMBlocks.RUBBER_BLOCK.get());
		this.dropSelf(YATMBlocks.ROOTED_SOUL_SOIL.get());
			
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
		this.dropSelf(YATMBlocks.STEEL_EXTRUDER.get());
		this.dropSelf(YATMBlocks.STEEL_GRINDER.get());
			
		this.dropSelf(YATMBlocks.C_U_F_E_I.get());
		
		this.dropSelf(YATMBlocks.CRUDE_BATTERY_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.ADVANCED_BATTERY_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.SUNS_COMPLEMENT_BATTERY_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.CRUDE_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.ADVANCED_SOLAR_PANEL.get());
		this.dropSelf(YATMBlocks.SUNS_COMPLEMENT_SOLAR_PANEL.get());
		
		
		
		this.dropSelf(YATMBlocks.ONE_CU_WIRE.get());
		this.dropSelf(YATMBlocks.EIGHT_CU_WIRE.get());
		this.dropSelf(YATMBlocks.SIXTYFOUR_CU_WIRE.get());
		this.dropSelf(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get());
		this.dropSelf(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get());
			
		this.dropSelf(YATMBlocks.ENAMELED_ONE_CU_WIRE.get());
		this.dropSelf(YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get());
		this.dropSelf(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get());
		this.dropSelf(YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get());
		this.dropSelf(YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get());
		
		this.dropSelf(YATMBlocks.INSULATED_ONE_CU_WIRE.get());
		this.dropSelf(YATMBlocks.INSULATED_EIGHT_CU_WIRE.get());
		this.dropSelf(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get());
		this.dropSelf(YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get());
		this.dropSelf(YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get());
		
		this.dropSelf(YATMBlocks.STEEL_FLUID_CONDUIT.get());
	} // end generate()

	@Override
	protected Iterable<Block> getKnownBlocks()
	{
		return YATMBlocks.BLOCKS.getEntries().stream().map((ro) -> ro.get()).toList();
	} // end getKnownBlocks()

	
	
	protected void addShulkwart(@NotNull Block shulkwart, @NotNull Item horn) 
	{
	      this.add(shulkwart, this.createShulkwartTable(shulkwart, horn));
	} // end add Shulkwart
	
	protected @NotNull LootTable.Builder createShulkwartTable(@NotNull Block shulkwart, @NotNull Item horn)
	{
		LootItemCondition.Builder dropConditions = LootItemBlockStatePropertyCondition.hasBlockStateProperties(shulkwart).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ShulkwartBlock.AGE, 7));
		return this.applyExplosionDecay(shulkwart, LootTable.lootTable().withPool(LootPool.lootPool().when(dropConditions).add(LootItem.lootTableItem(horn).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0f))).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2)))));
	} // end CreateShulkwartTable()
	
	protected @NotNull LootTable.Builder createAurumDeminutusTable() 
	{
		LootItemCondition.Builder fiddleHeadConditions 
		= LootItemBlockStatePropertyCondition
		.hasBlockStateProperties(YATMBlocks.AURUM_DEMINUTUS.get())
		.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AurumDeminutusBlock.HALF, DoubleBlockHalf.LOWER));

		BiFunction<DoubleBlockHalf, Integer, LootItemCondition.Builder> forHalfAge = (hf, i) -> LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(YATMBlocks.AURUM_DEMINUTUS.get())
				.setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(AurumDeminutusBlock.HALF, hf))
				.and(LootItemBlockStatePropertyCondition
						.hasBlockStateProperties(YATMBlocks.AURUM_DEMINUTUS.get())
						.setProperties(StatePropertiesPredicate.Builder.properties()
								.hasProperty(AurumDeminutusBlock.AGE, i)));

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
	
} // end class