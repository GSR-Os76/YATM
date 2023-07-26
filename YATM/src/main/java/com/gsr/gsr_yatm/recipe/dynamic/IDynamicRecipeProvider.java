package com.gsr.gsr_yatm.recipe.dynamic;

import java.util.Enumeration;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public interface IDynamicRecipeProvider<T extends Recipe<? extends Container>>
{
	public @NotNull Enumeration<T> getEnumerator(@NotNull Level level);
	
	public @NotNull RecipeType<?> recipeType();
} // end class