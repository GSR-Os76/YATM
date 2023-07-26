package com.gsr.gsr_yatm.recipe.cystallizing;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class CrystallizingRecipeSerializer implements RecipeSerializer<CrystallizingRecipe>
{
	@Override
	public CrystallizingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		CrystallizingRecipeBuilder builder = new CrystallizingRecipeBuilder();
		
		builder.identifier(resourceLocation);
		builder.result(CraftingHelper.getItemStack(jsonObject.getAsJsonObject(IngredientUtilities.RESULT_OBJECT_KEY), true));
		builder.input(IngredientUtilities.readIngredient(jsonObject.getAsJsonObject(IngredientUtilities.INPUT_OBJECT_KEY).getAsJsonObject(IngredientUtilities.INGREDIENT_KEY)).cast());
		
		JsonObject seedObj = jsonObject.getAsJsonObject(IngredientUtilities.SEED_KEY);
		builder.seed(IngredientUtilities.readIngredient(seedObj.getAsJsonObject(IngredientUtilities.INGREDIENT_KEY)).cast());
		if(seedObj.has(IngredientUtilities.CONSUME_SEED_KEY)) 
		{
			builder.consumeSeed(seedObj.get(IngredientUtilities.CONSUME_SEED_KEY).getAsBoolean());
		}
		
		if(jsonObject.has(IngredientUtilities.CURRENT_PER_TICK_KEY)) 
		{
			builder.currentPerTick(jsonObject.get(IngredientUtilities.CURRENT_PER_TICK_KEY).getAsInt());
		}
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
	public @Nullable CrystallizingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf friendlyByteBuf, CrystallizingRecipe recipe)
	{
		// TODO Auto-generated method stub	
	}

} // end class