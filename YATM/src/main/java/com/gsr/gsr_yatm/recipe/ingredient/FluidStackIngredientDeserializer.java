package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

public class FluidStackIngredientDeserializer implements IIngredientDeserializer<FluidStackIngredient>
{

	@Override
	public @NotNull FluidStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new FluidStackIngredient(IngredientUtil.fluidStackFromJson(jsonObject));
	} // end deserialize()

} // end class