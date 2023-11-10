package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.network.FriendlyByteBuf;

public class NBTFluidStackIngredientDeserializer implements IIngredientDeserializer<NBTFluidStackIngredient>
{

	@Override
	public @NotNull NBTFluidStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new NBTFluidStackIngredient(IngredientUtil.nbtFluidStackFromJson(jsonObject));
	} // end deserialize()

	@Override
	public @NotNull NBTFluidStackIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new NBTFluidStackIngredient(IngredientUtil.nbtFluidStackFromNetwork(buffer));
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull NBTFluidStackIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		IngredientUtil.nbtFluidStackToNetwork(ingredient.getValues().get(0), buffer);
	} // end toNetwork()
} // end class