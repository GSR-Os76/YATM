package com.gsr.gsr_yatm.recipe.smelting;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.dynamic.IDynamicRecipeProvider;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtil;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;

public class WrappedSmeltingRecipeProvider implements IDynamicRecipeProvider<WrappedSmeltingRecipe>
{
	private Map<String, RecipeHolder<WrappedSmeltingRecipe>> m_cache = new HashMap<>();
	
	
	
	public WrappedSmeltingRecipeProvider() 
	{
		RecipeUtil.addPersistentRecipeLoadListener(() -> WrappedSmeltingRecipeProvider.this.m_cache = new HashMap<>());
	} // end constructor
	
	
	
	@Override
	public @NotNull Enumeration<RecipeHolder<WrappedSmeltingRecipe>> getEnumerator(@NotNull Level level)
	{
		return new Enumeration<>() 
		{
			private Iterator<RecipeHolder<SmeltingRecipe>> m_smeltingRecipes = level.getRecipeManager().getAllRecipesFor(RecipeType.SMELTING).iterator();
			
			@Override
			public boolean hasMoreElements()
			{
				return this.m_smeltingRecipes.hasNext();
			} // end hasMoreElements()

			@Override
			public RecipeHolder<WrappedSmeltingRecipe> nextElement()
			{
				RecipeHolder<SmeltingRecipe> r = this.m_smeltingRecipes.next();
				String key = r.id().toString(); 
				if(!WrappedSmeltingRecipeProvider.this.m_cache.containsKey(key)) 
				{
					WrappedSmeltingRecipeProvider.this.m_cache.put(key, new RecipeHolder<>(r.id(), new WrappedSmeltingRecipe(r.value())));
				}
				return WrappedSmeltingRecipeProvider.this.m_cache.get(key);
			} // end nextElement()
		};
	} // end getEnumerator()

	@Override
	public @NotNull RecipeType<WrappedSmeltingRecipe> recipeType()
	{
		return YATMRecipeTypes.SMELTING_PLUS.get();
	} // end recipeType()

} // end class