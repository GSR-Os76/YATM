package com.gsr.gsr_yatm.data_generation;

import java.util.List;
import java.util.Set;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class YATMLootTableProvider extends LootTableProvider
{
	public static YATMLootTableProvider create(PackOutput packOutput) 
	{
		return new YATMLootTableProvider(packOutput, 
				Set.of(), 
				List.of(new LootTableProvider.SubProviderEntry(YATMBlockLoot::new, LootContextParamSets.BLOCK),
						new LootTableProvider.SubProviderEntry(YATMHarvestLoot::new, LootContextParamSets.EMPTY)));
	} // end create()
	
	
	
	public YATMLootTableProvider(PackOutput packOutput, Set<ResourceLocation> requiredTables, List<SubProviderEntry> subProviders)
	{
		super(packOutput, requiredTables, subProviders);
	} // end constructor

} // end class
