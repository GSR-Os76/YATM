package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidTagIngredientDeserializer implements IIngredientDeserializer<FluidTagIngredient>
{

	@Override
	public @NotNull FluidTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Fluid> tag = IngredientUtil.getTagKey(jsonObject.get(IngredientUtil.TAG_KEY).getAsString(), ForgeRegistries.FLUIDS);
		int amount = jsonObject.get(IngredientUtil.AMOUNT_KEY).getAsInt();
		return new FluidTagIngredient(tag, amount);
	} // end deserialize()

	@Override
	public @NotNull FluidTagIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new FluidTagIngredient(IngredientUtil.getTagKey(buffer.readUtf(), ForgeRegistries.FLUIDS), buffer.readVarInt());
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FluidTagIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeUtf(ingredient.getTag().toString());
		buffer.writeInt(ingredient.getAmount());
	} // end toNetwork()

} // end class