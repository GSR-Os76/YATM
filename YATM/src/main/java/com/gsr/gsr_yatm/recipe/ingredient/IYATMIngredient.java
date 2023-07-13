package com.gsr.gsr_yatm.recipe.ingredient;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.gson.JsonObject;

public interface IYATMIngredient<T> extends Predicate<T>
{
	public YATMIngredientDeserializer<?> deserializer();
	
	public JsonObject serialize();

	public List<T> getValues();
	
} // end outer class