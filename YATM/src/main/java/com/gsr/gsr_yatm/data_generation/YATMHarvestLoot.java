package com.gsr.gsr_yatm.data_generation;

import java.util.function.BiConsumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

public class YATMHarvestLoot implements LootTableSubProvider
{
	public static final ResourceLocation DWARF_PERSIMMON = locationForBlock(YATMBlocks.DWARF_PERSIMMON.get());
	public static final ResourceLocation SPIDER_PLANT_MANUAL = locationForBlock(YATMBlocks.SPIDER_PLANT.get()).withSuffix("_manual");
	public static final ResourceLocation SPIDER_PLANT_SHEARED = locationForBlock(YATMBlocks.SPIDER_PLANT.get()).withSuffix("_sheared");

	
	
	@Override
	public void generate(@NotNull BiConsumer<ResourceLocation, Builder> consumer)
	{
		consumer.accept(DWARF_PERSIMMON, rangeTable(YATMItems.PERSIMMON.get(), 1, 3));
		consumer.accept(SPIDER_PLANT_MANUAL, rangeTable(YATMItems.GLARING_PLANTLET.get(), 1, 1));
		consumer.accept(SPIDER_PLANT_SHEARED, rangeTable(YATMItems.GLARING_PLANTLET.get(), 1, 3));
	} // end generate()


	
	protected static @NotNull Builder rangeTable(@NotNull Item item, @NotNegative int min, @NotNegative int max) 
	{
		return LootTable.lootTable().withPool(
						LootPool.lootPool().add(LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between((float)min, (float)max)))
								));
	} // end rangeTable()
	
	protected static @NotNull ResourceLocation locationForBlock(@NotNull Block block) 
	{
		ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(block);
        return new ResourceLocation(registryName.getNamespace(), "harvest_results/" + registryName.getPath());
	} // end locationForBlock()

} // end class