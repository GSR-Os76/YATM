package com.gsr.gsr_yatm.recipe.bioreaction;

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

public class CompostableBioreactionRecipeProvider implements IDynamicRecipeProvider<BioreactionRecipe>
{
	private Map<Map.Entry<ItemLike, Float>, RecipeHolder<BioreactionRecipe>> m_cache = new HashMap<>();
	

	
	public CompostableBioreactionRecipeProvider() 
	{
		RecipeUtil.addPersistentRecipeLoadListener(() -> CompostableBioreactionRecipeProvider.this.m_cache = new HashMap<>());
	} // end constructor
	
	
	
	@Override
	public @NotNull Enumeration<RecipeHolder<BioreactionRecipe>> getEnumerator(@NotNull Level level)
	{
		return new Enumeration<RecipeHolder<BioreactionRecipe>>() 
		{
			private final ObjectIterator<Entry<ItemLike>> m_compostablesList = ComposterBlock.COMPOSTABLES.object2FloatEntrySet().iterator();
			
			
			
			@Override
			public boolean hasMoreElements()
			{
				return this.m_compostablesList.hasNext();
			} // end hasMoreElements()

			@Override
			public RecipeHolder<BioreactionRecipe> nextElement()
			{
				Entry<ItemLike> t = this.m_compostablesList.next();
				if(!CompostableBioreactionRecipeProvider.this.m_cache.containsKey(t)) 
				{
					CompostableBioreactionRecipeProvider.this.m_cache.put(t, 
							new RecipeHolder<>(this.identifierFor(t), new CompostableBioreactionRecipe(t)));
				}
				return CompostableBioreactionRecipeProvider.this.m_cache.get(t);
			} // end nextElement()
			
			
			
			private ResourceLocation identifierFor(Entry<ItemLike> e) 
			{
				return new ResourceLocation(YetAnotherTechMod.MODID, "biofluid_from_" + ForgeRegistries.ITEMS.getKey(e.getKey().asItem()).getNamespace() + "_" + ForgeRegistries.ITEMS.getKey(e.getKey().asItem()).getPath() + "_bioling");
			} // end identifierFor()
			
		};		
	} // end getEnumerator
	
	@Override
	public @NotNull RecipeType<BioreactionRecipe> recipeType()
	{
		return YATMRecipeTypes.BIOREACTION.get();
	} // end recipeType()
	
} // end class