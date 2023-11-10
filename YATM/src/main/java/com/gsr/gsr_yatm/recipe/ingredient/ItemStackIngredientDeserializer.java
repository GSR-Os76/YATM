package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ItemStackIngredientDeserializer implements IIngredientDeserializer<ItemStackIngredient>
{
	@Override
	public @NotNull ItemStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new ItemStackIngredient(CraftingHelper.getItemStack(jsonObject, false, false));
	} // end deserialize()

	@Override
	public @NotNull ItemStackIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new ItemStackIngredient(IngredientUtil.itemStackFromNetwork(buffer));
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull ItemStackIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		IngredientUtil.itemStackToNetwork(ingredient.getValues().get(0), buffer);
	} // end toNetwork()
	
} // end class