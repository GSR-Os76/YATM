package com.gsr.gsr_yatm.data_generation;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class YATMLootTableProvider extends LootTableProvider
{
	public static YATMLootTableProvider create(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) 
	{
		return new YATMLootTableProvider(packOutput, 
				Set.of(), 
				List.of(new LootTableProvider.SubProviderEntry(YATMBlockLoot::new, LootContextParamSets.BLOCK),
						new LootTableProvider.SubProviderEntry(YATMHarvestLoot::new, LootContextParamSets.EMPTY)),
				registries);
	} // end create()
	
	
	
	public YATMLootTableProvider(PackOutput packOutput, Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders, CompletableFuture<HolderLookup.Provider> registries)
	{
		super(packOutput, requiredTables, subProviders, registries);
	} // end constructor

} // end class
