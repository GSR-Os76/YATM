package com.gsr.gsr_yatm.recipe.cystallizing;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

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
		builder.result(CraftingHelper.getItemStack(jsonObject.getAsJsonObject(IngredientUtil.RESULT_KEY), true));
		builder.input(IngredientUtil.readIngredient(jsonObject.getAsJsonObject(IngredientUtil.INPUT_KEY).getAsJsonObject(IngredientUtil.INGREDIENT_KEY)).cast());
		
		JsonObject seedObj = jsonObject.getAsJsonObject(IngredientUtil.SEED_KEY);
		builder.seed(IngredientUtil.readIngredient(seedObj.getAsJsonObject(IngredientUtil.INGREDIENT_KEY)).cast());
		if(seedObj.has(IngredientUtil.CONSUME_SEED_KEY)) 
		{
			builder.consumeSeed(seedObj.get(IngredientUtil.CONSUME_SEED_KEY).getAsBoolean());
		}
		
		if(jsonObject.has(IngredientUtil.CURRENT_PER_TICK_KEY)) 
		{
			builder.currentPerTick(jsonObject.get(IngredientUtil.CURRENT_PER_TICK_KEY).getAsInt());
		}
		if(jsonObject.has(IngredientUtil.TIME_IN_TICKS_KEY)) 
		{
			builder.timeInTicks(jsonObject.get(IngredientUtil.TIME_IN_TICKS_KEY).getAsInt());
		}
		if(jsonObject.has(IngredientUtil.GROUP_KEY)) 
		{
			builder.group(jsonObject.get(IngredientUtil.GROUP_KEY).getAsString());
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