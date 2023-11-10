package com.gsr.gsr_yatm.recipe.melting;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.JsonUtil;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MeltingRecipeSerializer implements RecipeSerializer<MeltingRecipe>
{
	@Override
	public @NotNull MeltingRecipe fromJson(@NotNull ResourceLocation resourceLocation, @NotNull JsonObject jsonObject)
	{
		MeltingRecipeBuilder builder = new MeltingRecipeBuilder();
		
		builder.identifier(resourceLocation);
		builder.result(IngredientUtil.fluidStackFromJson(jsonObject.getAsJsonObject(IngredientUtil.RESULT_KEY)));
		builder.input(IngredientUtil.readIngredient(jsonObject.getAsJsonObject(IngredientUtil.INPUT_KEY).getAsJsonObject(IngredientUtil.INGREDIENT_KEY)).cast());
		
		JsonUtil.ifPresent(jsonObject, IngredientUtil.TEMPERATURE_KEY, (e) -> builder.temperature(e.getAsInt()));
		JsonUtil.ifPresent(jsonObject, IngredientUtil.TIME_IN_TICKS_KEY, (e) -> builder.timeInTicks(e.getAsInt()));
		JsonUtil.ifPresent(jsonObject, IngredientUtil.GROUP_KEY, (e) -> builder.group(e.getAsString()));
		
		return builder.build();
	} // end fromJson()

	@Override
	public @Nullable MeltingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf friendlyByteBuf, MeltingRecipe recipe)
	{
		// TODO Auto-generated method stub		
	}

} // end class