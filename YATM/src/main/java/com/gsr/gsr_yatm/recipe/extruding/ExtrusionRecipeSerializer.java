package com.gsr.gsr_yatm.recipe.extruding;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ExtrusionRecipeSerializer implements RecipeSerializer<ExtrusionRecipe>
{	
	
	@Override
	public ExtrusionRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		ExtrusionRecipeBuilder builder = new ExtrusionRecipeBuilder();
		
		builder.identifier(resourceLocation);
		builder.result(CraftingHelper.getItemStack(jsonObject.getAsJsonObject(RecipeUtilities.RESULT_OBJECT_KEY), false));
		
		JsonObject inputObj = jsonObject.getAsJsonObject(RecipeUtilities.INPUT_OBJECT_KEY);
		builder.input(Ingredient.fromJson(inputObj.get(RecipeUtilities.INGREDIENT_KEY)));
		if(inputObj.has(RecipeUtilities.REMAINDER_STACK_KEY)) 
		{
			builder.inputRemainder(CraftingHelper.getItemStack(inputObj.getAsJsonObject(RecipeUtilities.REMAINDER_STACK_KEY), false));
		}
		
		JsonObject dieObj = jsonObject.getAsJsonObject(RecipeUtilities.DIE_OBJECT_KEY);
		builder.die(Ingredient.fromJson(dieObj.get(RecipeUtilities.INGREDIENT_KEY)));
		if(dieObj.has(RecipeUtilities.REMAINDER_STACK_KEY)) 
		{
			builder.dieRemainder(CraftingHelper.getItemStack(dieObj.getAsJsonObject(RecipeUtilities.REMAINDER_STACK_KEY), false));
		}
		
		if(jsonObject.has(RecipeUtilities.CURRENT_PER_TICK_KEY)) 
		{
			builder.currentPerTick(jsonObject.get(RecipeUtilities.CURRENT_PER_TICK_KEY).getAsInt());
		}
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
	public @Nullable ExtrusionRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf friendlyByteBuf, ExtrusionRecipe recipe)
	{
		// TODO Auto-generated method stub
	}
	
} // end class
/* //SimpleCraftingRecipeSerializer s;
		ResourceLocation identifier = resourceLocation;
		ItemStack result = CraftingHelper.getItemStack(jsonObject.getAsJsonObject(RESULT_OBJECT_KEY), false);
		ExtrusionRecipe.Builder builder = new ExtrusionRecipe.Builder(identifier, result);
		
		JsonObject inputObj = jsonObject.getAsJsonObject(INPUT_OBJECT_KEY);
		JsonObject dieObj = jsonObject.getAsJsonObject(DIE_OBJECT_KEY);
		
		
		
		ItemStack inputStack = null;
		ITag<Item> inputTag = null;
		int inputCount = 1;
		if(inputObj.has(ITEM_KEY)) 
		{			
			inputStack = CraftingHelper.getItemStack(inputObj, false);
		}
		if(inputObj.has(TAG_KEY)) 
		{			
			inputTag = RecipeUtilities.getTag(inputObj.get(TAG_KEY).getAsString());
		}
		if(inputObj.has(COUNT_KEY)) 
		{			
			inputCount = inputObj.get(COUNT_KEY).getAsInt();
		}
		builder.input(inputTag, inputStack, Math.max(inputCount, inputStack != null ? inputStack.getCount() : 0));
		
		
		
		ItemStack dieStack = null;
		ITag<Item> dieTag = null;
		if(dieObj.has(ITEM_KEY)) 
		{			
			dieStack = new ItemStack(CraftingHelper.getItem(dieObj.get(ITEM_KEY).getAsString(), true), 1);
		}
		if(dieObj.has(TAG_KEY)) 
		{
			dieTag = RecipeUtilities.getTag(dieObj.get(TAG_KEY).getAsString()); 
		}
		builder.die(dieTag, dieStack);
		
		
		
		if(inputObj.has(INPUT_REMAINDER_STACK_KEY)) 
		{
			builder.inputRemainder(CraftingHelper.getItemStack(inputObj.getAsJsonObject(INPUT_REMAINDER_STACK_KEY), false));
		}
		if(dieObj.has(DIE_REMAINDER_KEY)) 
		{
			builder.dieRemainder(CraftingHelper.getItemStack(dieObj.getAsJsonObject(DIE_REMAINDER_KEY), false));
		}
		
		if(jsonObject.has(CURRENT_PER_TICK_KEY)) 
		{
			builder.currentPerTick(jsonObject.get(CURRENT_PER_TICK_KEY).getAsInt());
		}
		if(jsonObject.has(TIME_IN_TICKS_KEY)) 
		{
			builder.timeInTicks(jsonObject.get(TIME_IN_TICKS_KEY).getAsInt());
		}
		
		return builder.build();
  
  
 
 * @Override
	public ExtrusionRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		//SimpleCraftingRecipeSerializer s;
		ResourceLocation identifier = resourceLocation;
		ItemStack result = CraftingHelper.getItemStack(jsonObject.getAsJsonObject(RESULT_OBJECT_KEY), false);
		
		JsonObject inputObj = jsonObject.getAsJsonObject(INPUT_OBJECT_KEY);
		JsonObject dieObj = jsonObject.getAsJsonObject(DIE_OBJECT_KEY);
		
		ItemStack input = CraftingHelper.getItemStack(inputObj, false);
		ItemStack die = new ItemStack(CraftingHelper.getItem(dieObj.get(ITEM_KEY).getAsString(), true), 1);
		
		ExtrusionRecipe.Builder builder = new ExtrusionRecipe.Builder(identifier, input, die, result);
		
		if(inputObj.has(INPUT_REMAINDER_STACK_KEY)) 
		{
			builder.inputRemainder(CraftingHelper.getItemStack(inputObj.getAsJsonObject(INPUT_REMAINDER_STACK_KEY), false));
		}
		if(dieObj.has(DIE_REMAINDER_KEY)) 
		{
			builder.dieRemainder(CraftingHelper.getItemStack(dieObj.getAsJsonObject(DIE_REMAINDER_KEY), false));
		}
		
		if(jsonObject.has(CURRENT_PER_TICK_KEY)) 
		{
			builder.currentPerTick(jsonObject.get(CURRENT_PER_TICK_KEY).getAsInt());
		}
		if(jsonObject.has(TIME_IN_TICKS_KEY)) 
		{
			builder.timeInTicks(jsonObject.get(TIME_IN_TICKS_KEY).getAsInt());
		}
		
		return builder.build();
	} // end fromJson()
 * */
 