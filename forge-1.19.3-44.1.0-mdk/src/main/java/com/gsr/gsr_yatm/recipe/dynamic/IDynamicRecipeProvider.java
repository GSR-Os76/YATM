package com.gsr.gsr_yatm.recipe.dynamic;

import java.util.Enumeration;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface IDynamicRecipeProvider<T extends Recipe<? extends Container>>
{
	public Enumeration<T> getEnumerator();
	
	public RecipeType<?> recipeType();
} // end class