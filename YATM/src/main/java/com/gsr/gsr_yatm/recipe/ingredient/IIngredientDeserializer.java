package com.gsr.gsr_yatm.recipe.ingredient;

import com.google.gson.JsonObject;

public interface IIngredientDeserializer<T extends IIngredient<?>>
{
	public IIngredient<?> deserialize(JsonObject jsonObject);
} // end interface