package com.gsr.gsr_yatm.recipe.ingredient;

import com.google.gson.JsonObject;

public interface YATMIngredientDeserializer<T extends IYATMIngredient<?>>
{
	public IYATMIngredient<?> deserialize(JsonObject jsonObject);
} // end interface