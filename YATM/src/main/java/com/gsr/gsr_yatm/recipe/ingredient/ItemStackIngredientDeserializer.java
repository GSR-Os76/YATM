package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.CraftingHelper;

public class ItemStackIngredientDeserializer implements IIngredientDeserializer<ItemStackIngredient>
{
	@Override
	public @NotNull ItemStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new ItemStackIngredient(CraftingHelper.getItemStack(jsonObject, false, false));
	} // end deserialize()
	
} // end class