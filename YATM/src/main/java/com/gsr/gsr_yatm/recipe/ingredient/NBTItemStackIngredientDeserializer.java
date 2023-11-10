package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.crafting.CraftingHelper;

public class NBTItemStackIngredientDeserializer implements IIngredientDeserializer<NBTItemStackIngredient>
{
	@Override
	public @NotNull NBTItemStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new NBTItemStackIngredient(CraftingHelper.getItemStack(jsonObject, true, false));
	} // end deserialize()

	@Override
	public @NotNull NBTItemStackIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new NBTItemStackIngredient(buffer.readItem());
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull NBTItemStackIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeItem(ingredient.getValues().get(0));
	} // end toNetwork()
	
} // end class