package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidTagIngredientDeserializer implements IIngredientDeserializer<FluidTagIngredient>
{

	@Override
	public @NotNull FluidTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Fluid> tag = IngredientUtilities.getTagKey(jsonObject.get(IngredientUtilities.TAG_KEY).getAsString(), ForgeRegistries.FLUIDS);
		int amount = jsonObject.get(IngredientUtilities.AMOUNT_KEY).getAsInt();
		return new FluidTagIngredient(tag, amount);
	} // end deserialize()

} // end class