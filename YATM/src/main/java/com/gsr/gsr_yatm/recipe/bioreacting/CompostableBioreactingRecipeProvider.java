package com.gsr.gsr_yatm.recipe.bioreacting;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.dynamic.IDynamicRecipeProvider;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtil;

import it.unimi.dsi.fastutil.objects.Object2FloatMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.registries.ForgeRegistries;

public class CompostableBioreactingRecipeProvider implements IDynamicRecipeProvider<BioreactingRecipe>
{
	private Map<Map.Entry<ItemLike, Float>, RecipeHolder<BioreactingRecipe>> m_cache = new HashMap<>();
	

	
	public CompostableBioreactingRecipeProvider() 
	{
		RecipeUtil.addPersistentRecipeLoadListener(() -> CompostableBioreactingRecipeProvider.this.m_cache = new HashMap<>());
	} // end constructor
	
	
	
	@Override
	public @NotNull Enumeration<RecipeHolder<BioreactingRecipe>> getEnumerator(@NotNull Level level)
	{
		return new Enumeration<RecipeHolder<BioreactingRecipe>>() 
		{
			private final ObjectIterator<Entry<ItemLike>> m_compostablesList = ComposterBlock.COMPOSTABLES.object2FloatEntrySet().iterator();
			
			
			
			@Override
			public boolean hasMoreElements()
			{
				return this.m_compostablesList.hasNext();
			} // end hasMoreElements()

			@Override
			public RecipeHolder<BioreactingRecipe> nextElement()
			{
				Entry<ItemLike> t = this.m_compostablesList.next();
				if(!CompostableBioreactingRecipeProvider.this.m_cache.containsKey(t)) 
				{
					CompostableBioreactingRecipeProvider.this.m_cache.put(t, 
							new RecipeHolder<>(this.identifierFor(t), new CompostableBioreactingRecipe(t)));
				}
				return CompostableBioreactingRecipeProvider.this.m_cache.get(t);
			} // end nextElement()
			
			
			
			private ResourceLocation identifierFor(Entry<ItemLike> e) 
			{
				return new ResourceLocation(YetAnotherTechMod.MODID, "biofluid_from_" + ForgeRegistries.ITEMS.getKey(e.getKey().asItem()).getNamespace() + "_" + ForgeRegistries.ITEMS.getKey(e.getKey().asItem()).getPath() + "_bioling");
			} // end identifierFor()
			
		};		
	} // end getEnumerator
	
	@Override
	public @NotNull RecipeType<BioreactingRecipe> recipeType()
	{
		return YATMRecipeTypes.BIOREACTING.get();
	} // end recipeType()
	
} // end class