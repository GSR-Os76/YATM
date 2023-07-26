package com.gsr.gsr_yatm.recipe.ingredient;

import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;

public interface IIngredient<T> extends Predicate<T>
{
	@Override
	public boolean test(@Nullable T t);
	

	public @NotNull IIngredientDeserializer<?> deserializer();
	
	public @NotNull JsonObject serialize();

	public @NotNull List<T> getValues();
	
} // end interface