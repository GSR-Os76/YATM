package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

public class NBTFluidStackIngredientDeserializer implements IIngredientDeserializer<NBTFluidStackIngredient>
{

	@Override
	public @NotNull NBTFluidStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new NBTFluidStackIngredient(IngredientUtil.nbtFluidStackFromJson(jsonObject));
	} // end deserialize()

} // end class