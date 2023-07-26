package com.gsr.gsr_yatm.recipe.boiling;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BoilingRecipeSerializer implements RecipeSerializer<BoilingRecipe>
{
	@Override
	public BoilingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		BoilingRecipeBuilder builder = new BoilingRecipeBuilder();
		
		builder.identifier(resourceLocation);
		builder.result(IngredientUtilities.nbtFluidStackFromJson(jsonObject.getAsJsonObject(IngredientUtilities.RESULT_OBJECT_KEY)));
		builder.input(IngredientUtilities.readIngredient(jsonObject.getAsJsonObject(IngredientUtilities.INPUT_OBJECT_KEY).getAsJsonObject(IngredientUtilities.INGREDIENT_KEY)).cast());
		builder.temperature(jsonObject.get(IngredientUtilities.TEMPERATURE_KEY).getAsInt());
		
		if(jsonObject.has(IngredientUtilities.TIME_IN_TICKS_KEY)) 
		{
			builder.timeInTicks(jsonObject.get(IngredientUtilities.TIME_IN_TICKS_KEY).getAsInt());
		}
		if(jsonObject.has(IngredientUtilities.GROUP_KEY)) 
		{
			builder.group(jsonObject.get(IngredientUtilities.GROUP_KEY).getAsString());
		}
		
		return builder.build();
	} // end fromJson()

	@Override
	public @Nullable BoilingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf friendlyByteBuf, BoilingRecipe recipe)
	{
		// TODO Auto-generated method stub
		
	}

} // end class