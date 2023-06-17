package com.gsr.gsr_yatm.recipe;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ExtractionRecipeSerializer implements RecipeSerializer<ExtractionRecipe>
{
	@Override
	public ExtractionRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		YetAnotherTechMod.LOGGER.info("made it at least into the serializer");
		ExtractionRecipeBuilder builder = new ExtractionRecipeBuilder();
		
		builder.identifier(resourceLocation);
		
		JsonObject inputObj = jsonObject.getAsJsonObject(RecipeUtilities.INPUT_OBJECT_KEY);
		builder.input(Ingredient.fromJson(inputObj.get(RecipeUtilities.INGREDIENT_KEY))); // vanilla serializer accepts both "item" and "tag", expectably this'll work similarly so
		if(inputObj.has(RecipeUtilities.REMAINDER_STACK_KEY)) 
		{
			builder.inputRemainder(CraftingHelper.getItemStack(inputObj.getAsJsonObject(RecipeUtilities.REMAINDER_STACK_KEY), false));
		}
		
		builder.result(RecipeUtilities.fluidStackFromJson(jsonObject.getAsJsonObject(RecipeUtilities.RESULT_OBJECT_KEY)));
		
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
		
		
		return builder.build();
	} // end fromJson()

	@Override
	public @Nullable ExtractionRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf friendlyByteBuf, ExtractionRecipe recipe)
	{
		// TODO Auto-generated method stub
		
	}

} // end class