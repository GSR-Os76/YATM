package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.CraftingHelper;

public class NBTItemStackIngredientDeserializer implements IIngredientDeserializer<NBTItemStackIngredient>
{
	@Override
	public @NotNull NBTItemStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new NBTItemStackIngredient(CraftingHelper.getItemStack(jsonObject, true, false));
	} // end deserialize()
	
} // end class