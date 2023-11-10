package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.network.FriendlyByteBuf;

public class FluidStackIngredientDeserializer implements IIngredientDeserializer<FluidStackIngredient>
{

	@Override
	public @NotNull FluidStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new FluidStackIngredient(IngredientUtil.fluidStackFromJson(jsonObject));
	} // end deserialize()

	@Override
	public @NotNull FluidStackIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new FluidStackIngredient(IngredientUtil.fluidStackFromNetwork(buffer));
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FluidStackIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		IngredientUtil.fluidStackToNetwork(ingredient.getValues().get(0), buffer);
	} // end toNetwork()

} // end class