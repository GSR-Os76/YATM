package com.gsr.gsr_yatm.recipe.grinding;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class GrindingRecipeSerializer implements RecipeSerializer<GrindingRecipe>
{	
	
	@Override
	public GrindingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		GrindingRecipeBuilder builder = new GrindingRecipeBuilder();
		
		builder.identifier(resourceLocation);
		builder.result(CraftingHelper.getItemStack(jsonObject.getAsJsonObject(IngredientUtil.RESULT_KEY), true));
		builder.input(IngredientUtil.readIngredient(jsonObject.getAsJsonObject(IngredientUtil.INPUT_KEY).getAsJsonObject(IngredientUtil.INGREDIENT_KEY)).cast());
		
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
	public @Nullable GrindingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf friendlyByteBuf, GrindingRecipe recipe)
	{
		// TODO Auto-generated method stub
	}
	
} // end class