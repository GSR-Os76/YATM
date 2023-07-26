package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

public class NBTFluidStackIngredientDeserializer implements IIngredientDeserializer<NBTFluidStackIngredient>
{

	@Override
	public @NotNull NBTFluidStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new NBTFluidStackIngredient(IngredientUtilities.nbtFluidStackFromJson(jsonObject));
	} // end deserialize()

} // end class