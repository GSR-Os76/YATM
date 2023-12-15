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
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

public class YATMHarvestLoot implements LootTableSubProvider
{
	public static final ResourceLocation DWARF_PERSIMMON = locationForBlock(YATMBlocks.DWARF_PERSIMMON.get());

	
	
	@Override
	public void generate(@NotNull BiConsumer<ResourceLocation, Builder> consumer)
	{
		consumer.accept(DWARF_PERSIMMON, rangeTable(YATMItems.PERSIMMON.get(), 1, 3));
	} // end generate()
	
	
	
	protected static @NotNull Builder rangeTable(@NotNull Item item, @NotNegative int min, @NotNegative int max) 
	{
		LootTable.Builder table = LootTable.lootTable();
		if(min > 0) 
		{
			table.withPool(
						LootPool.lootPool().add(LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)))
								));
		}
		if(max > min) 
		{
			table.withPool(
						LootPool.lootPool().add(LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0, max - min)))
								));
		}
		return table;
	} // end rangeTable()
	
	protected static @NotNull ResourceLocation locationForBlock(@NotNull Block block) 
	{
		ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(block);
        return new ResourceLocation(registryName.getNamespace(), "harvest_results/" + registryName.getPath());
	} // end locationForBlock()

} // end class