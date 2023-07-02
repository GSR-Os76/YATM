package com.gsr.gsr_yatm.recipe;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;

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
		builder.result(RecipeUtilities.fluidStackFromJson(jsonObject.getAsJsonObject(RecipeUtilities.RESULT_OBJECT_KEY)));
		builder.input(RecipeUtilities.fluidStackFromJson(jsonObject.getAsJsonObject(RecipeUtilities.INPUT_OBJECT_KEY)));
		builder.temperature(jsonObject.get(RecipeUtilities.TEMPERATURE_KEY).getAsInt());
		
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