package com.gsr.gsr_yatm.recipe.bioling;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BiolingRecipeSerializer implements RecipeSerializer<BiolingRecipe>
{
	@Override
	public BiolingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		BiolingRecipeBuilder builder = new BiolingRecipeBuilder();
		
		builder.identifier(resourceLocation);
		builder.result(RecipeUtilities.fluidStackFromJson(jsonObject.getAsJsonObject(RecipeUtilities.RESULT_OBJECT_KEY)));
		
		JsonObject inputObj = jsonObject.getAsJsonObject(RecipeUtilities.INPUT_OBJECT_KEY);
		builder.input(Ingredient.fromJson(inputObj.get(RecipeUtilities.INGREDIENT_KEY)));
	
		// current
		if(jsonObject.has(RecipeUtilities.CURRENT_PER_TICK_KEY)) 
		{
			builder.currentPerTick(jsonObject.get(RecipeUtilities.CURRENT_PER_TICK_KEY).getAsInt());
		}
		// time
		if(jsonObject.has(RecipeUtilities.TIME_IN_TICKS_KEY)) 
		{
			builder.timeInTicks(jsonObject.get(RecipeUtilities.TIME_IN_TICKS_KEY).getAsInt());
		}
		if(jsonObject.has(RecipeUtilities.GROUP_KEY)) 
		{
			builder.group(jsonObject.get(RecipeUtilities.GROUP_KEY).getAsString());
		}
		return builder.build();
	} // end fromJson()

	@Override
	public @Nullable BiolingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf friendlyByteBuf, BiolingRecipe recipe)
	{
		// TODO Auto-generated method stub
		
	}

} // end class