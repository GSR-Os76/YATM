package com.gsr.gsr_yatm.data_generation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class YATMLootTableProvider extends LootTableProvider
{
	public static YATMLootTableProvider create(PackOutput packOutput) 
	{
		return new YATMLootTableProvider(packOutput, 
				Set.of(), 
				List.of(new LootTableProvider.SubProviderEntry(YATMBlockLoot::new, LootContextParamSets.BLOCK)));
	} // end create()
	
	
	
	public YATMLootTableProvider(PackOutput packOutput, Set<ResourceLocation> requiredTables, List<SubProviderEntry> subProviders)
	{
		super(packOutput, requiredTables, subProviders);
	} // end constructor

	
	
//	@Override
//	public List<SubProviderEntry> getTables()
//	{
//		return List.of(new SubProviderEntry(() -> new YATMBlockLoot()::generate, LootContextParamSets.BLOCK));
//	} // end getTables()

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext)
	{
		// super.validate(map, validationcontext);
	} // end validate()

} // end class
