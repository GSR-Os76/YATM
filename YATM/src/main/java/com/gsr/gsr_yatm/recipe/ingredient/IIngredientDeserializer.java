package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;

public interface IIngredientDeserializer<T extends IIngredient<?>>
{
	public @NotNull T deserialize(@NotNull JsonObject jsonObject);
} // end interface