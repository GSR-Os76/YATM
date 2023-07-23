package com.gsr.gsr_yatm.recipe.ingredient;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.gson.JsonObject;

public interface IIngredient<T> extends Predicate<T>
{
	public IIngredientDeserializer<?> deserializer();
	
	public JsonObject serialize();

	public List<T> getValues();
	
} // end outer class