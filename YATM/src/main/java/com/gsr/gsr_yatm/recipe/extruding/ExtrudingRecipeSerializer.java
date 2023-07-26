package com.gsr.gsr_yatm.recipe.extruding;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;
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
		builder.result(CraftingHelper.getItemStack(jsonObject.getAsJsonObject(IngredientUtilities.RESULT_OBJECT_KEY), true));
		
		JsonObject inputObj = jsonObject.getAsJsonObject(IngredientUtilities.INPUT_OBJECT_KEY);
		builder.input(IngredientUtilities.readIngredient((inputObj.getAsJsonObject(IngredientUtilities.INGREDIENT_KEY))).cast());
		if(inputObj.has(IngredientUtilities.REMAINDER_STACK_KEY)) 
		{
			builder.inputRemainder(CraftingHelper.getItemStack(inputObj.getAsJsonObject(IngredientUtilities.REMAINDER_STACK_KEY), true));
		}
		
		JsonObject dieObj = jsonObject.getAsJsonObject(IngredientUtilities.DIE_OBJECT_KEY);
		builder.die(IngredientUtilities.readIngredient((dieObj.getAsJsonObject(IngredientUtilities.INGREDIENT_KEY))).cast());
		if(dieObj.has(IngredientUtilities.REMAINDER_STACK_KEY)) 
		{
			builder.dieRemainder(CraftingHelper.getItemStack(dieObj.getAsJsonObject(IngredientUtilities.REMAINDER_STACK_KEY), true));
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