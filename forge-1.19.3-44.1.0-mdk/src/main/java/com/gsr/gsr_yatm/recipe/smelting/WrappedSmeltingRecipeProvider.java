package com.gsr.gsr_yatm.recipe.smelting;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.gsr.gsr_yatm.recipe.dynamic.IDynamicRecipeProvider;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;

public class WrappedSmeltingRecipeProvider implements IDynamicRecipeProvider<WrappedSmeltingRecipe>
{
	private Map<String, WrappedSmeltingRecipe> m_cache = new HashMap<>();
	
	
	
	public WrappedSmeltingRecipeProvider() 
	{
		RecipeUtilities.addPersistentRecipeLoadListener(() -> WrappedSmeltingRecipeProvider.this.m_cache = new HashMap<>());
	} // end constructor
	
	
	
	@Override
	public Enumeration<WrappedSmeltingRecipe> getEnumerator(Level level)
	{
		return new Enumeration<>() 
		{
			private Iterator<SmeltingRecipe> m_smeltingRecipes = level.getRecipeManager().getAllRecipesFor(RecipeType.SMELTING).iterator();
			
			@Override
			public boolean hasMoreElements()
			{
				return this.m_smeltingRecipes.hasNext();
			} // end hasMoreElements()

			@Override
			public WrappedSmeltingRecipe nextElement()
			{
				SmeltingRecipe r = this.m_smeltingRecipes.next();
				String key = r.getId().toString(); 
				if(!WrappedSmeltingRecipeProvider.this.m_cache.containsKey(key)) 
				{
					WrappedSmeltingRecipeProvider.this.m_cache.put(key, new WrappedSmeltingRecipe(r));
				}
				return WrappedSmeltingRecipeProvider.this.m_cache.get(key);
			} // end nextElement()
		};
	} // end getEnumerator()

	@Override
	public RecipeType<WrappedSmeltingRecipe> recipeType()
	{
		return YATMRecipeTypes.SMELTING_PLUS.get();
	} // end recipeType()

} // end class