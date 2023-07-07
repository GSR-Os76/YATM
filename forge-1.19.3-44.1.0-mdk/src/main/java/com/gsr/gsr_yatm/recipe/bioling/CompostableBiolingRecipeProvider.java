package com.gsr.gsr_yatm.recipe.bioling;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.dynamic.IDynamicRecipeProvider;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;

import it.unimi.dsi.fastutil.objects.Object2FloatMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.registries.ForgeRegistries;

public class CompostableBiolingRecipeProvider implements IDynamicRecipeProvider<BiolingRecipe>
{
	private Map<Map.Entry<ItemLike, Float>, CompostableBiolingRecipe> m_cache = new HashMap<>();
	

	
	public CompostableBiolingRecipeProvider() 
	{
		RecipeUtilities.addPersistentRecipeLoadListener(() -> CompostableBiolingRecipeProvider.this.m_cache = new HashMap<>());
	}
	
	
	
	@Override
	public Enumeration<BiolingRecipe> getEnumerator(Level level)
	{
		return new Enumeration<BiolingRecipe>() 
		{
			private final ObjectIterator<Entry<ItemLike>> m_compostablesList = ComposterBlock.COMPOSTABLES.object2FloatEntrySet().iterator();
			
			
			
			@Override
			public boolean hasMoreElements()
			{
				return this.m_compostablesList.hasNext();
			} // end hasMoreElements()

			@Override
			public CompostableBiolingRecipe nextElement()
			{
				Entry<ItemLike> t = this.m_compostablesList.next();
				if(!CompostableBiolingRecipeProvider.this.m_cache.containsKey(t)) 
				{
					CompostableBiolingRecipeProvider.this.m_cache.put(t, 
							new CompostableBiolingRecipe(this.identifierFor(t),	t));
				}
				return CompostableBiolingRecipeProvider.this.m_cache.get(t);
			} // end nextElement()
			
			
			
			private ResourceLocation identifierFor(Entry<ItemLike> e) 
			{
				return new ResourceLocation(YetAnotherTechMod.MODID, "biofluid_from_" + ForgeRegistries.ITEMS.getKey(e.getKey().asItem()).getNamespace() + "_" + ForgeRegistries.ITEMS.getKey(e.getKey().asItem()).getPath() + "_bioling");
			} // end identifierFor()
			
		};		
	} // end getEnumerator
	
	@Override
	public RecipeType<BiolingRecipe> recipeType()
	{
		return YATMRecipeTypes.BIOLING.get();
	} // end recipeType()
	
} // end class