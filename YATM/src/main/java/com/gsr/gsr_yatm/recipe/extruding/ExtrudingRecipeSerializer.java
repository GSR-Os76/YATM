package com.gsr.gsr_yatm.recipe.extruding;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ExtrudingRecipeSerializer implements RecipeSerializer<ExtrudingRecipe>
{	
	
	@Override
	public ExtrudingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		ExtrudingRecipeBuilder builder = new ExtrudingRecipeBuilder();
		
		builder.identifier(resourceLocation);
		builder.result(CraftingHelper.getItemStack(jsonObject.getAsJsonObject(IngredientUtil.RESULT_KEY), true));
		
		JsonObject inputObj = jsonObject.getAsJsonObject(IngredientUtil.INPUT_KEY);
		builder.input(IngredientUtil.readIngredient((inputObj.getAsJsonObject(IngredientUtil.INGREDIENT_KEY))).cast());
		if(inputObj.has(IngredientUtil.REMAINDER_STACK_KEY)) 
		{
			builder.inputRemainder(CraftingHelper.getItemStack(inputObj.getAsJsonObject(IngredientUtil.REMAINDER_STACK_KEY), true));
		}
		
		JsonObject dieObj = jsonObject.getAsJsonObject(IngredientUtil.DIE_KEY);
		builder.die(IngredientUtil.readIngredient((dieObj.getAsJsonObject(IngredientUtil.INGREDIENT_KEY))).cast());
		if(dieObj.has(IngredientUtil.REMAINDER_STACK_KEY)) 
		{
			builder.dieRemainder(CraftingHelper.getItemStack(dieObj.getAsJsonObject(IngredientUtil.REMAINDER_STACK_KEY), true));
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
	public @Nullable ExtrudingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf friendlyByteBuf, ExtrudingRecipe recipe)
	{
		// TODO Auto-generated method stub
	}
	
} // end class