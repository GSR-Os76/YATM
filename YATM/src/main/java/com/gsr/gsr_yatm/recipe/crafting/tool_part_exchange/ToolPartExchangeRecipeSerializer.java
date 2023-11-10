package com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ToolPartExchangeRecipeSerializer implements RecipeSerializer<ToolPartExchangeRecipe>
{
	public ToolPartExchangeRecipe fromJson(ResourceLocation identifier, JsonObject jsonObject)
	{
		ToolPartExchangeRecipeBuilder builder = new ToolPartExchangeRecipeBuilder();
		builder.identifier(identifier);
		builder.result(CraftingHelper.getItemStack(jsonObject.getAsJsonObject(IngredientUtil.RESULT_KEY), true));
		JsonObject inputObj = jsonObject.getAsJsonObject(IngredientUtil.INPUT_KEY);
		builder.tool(IngredientUtil.readIngredient(inputObj.getAsJsonObject(IngredientUtil.TOOL_KEY)).cast());
		builder.part(IngredientUtil.readIngredient(inputObj.getAsJsonObject(IngredientUtil.PART_KEY)).cast());
		
		if(jsonObject.has(IngredientUtil.GROUP_KEY)) 
		{
			builder.group(jsonObject.get(IngredientUtil.GROUP_KEY).getAsString());
		}
		if(jsonObject.has(IngredientUtil.CATEGORY_KEY)) 
		{
			String categoryName = jsonObject.get(IngredientUtil.CATEGORY_KEY).getAsString();
			for(CraftingBookCategory cbc : CraftingBookCategory.values()) 
			{
				if(cbc.getSerializedName() == categoryName) 
				{
					builder.category(cbc);
					break;
				}
			}
		}
		return builder.build();
	} // end fromJson()
	
	

	public ToolPartExchangeRecipe fromNetwork(ResourceLocation identifier, FriendlyByteBuf friendlyByteBuf)
	{
		IIngredient<ItemStack> tool = IngredientUtil.fromNetwork(friendlyByteBuf);
		IIngredient<ItemStack> part = IngredientUtil.fromNetwork(friendlyByteBuf);
		ItemStack result = friendlyByteBuf.readItem();
		String group = friendlyByteBuf.readUtf();
		CraftingBookCategory category = friendlyByteBuf.readEnum(CraftingBookCategory.class);
		
		return new ToolPartExchangeRecipe(identifier, tool, part, result, group, category);
	} // end fromNetwork()

	public void toNetwork(FriendlyByteBuf friendlyByteBuf, ToolPartExchangeRecipe recipe)
	{
		IngredientUtil.toNetwork(recipe.getTool(), friendlyByteBuf);
		IngredientUtil.toNetwork(recipe.getPart(), friendlyByteBuf);
		friendlyByteBuf.writeItem(recipe.getResultItem(null));
		friendlyByteBuf.writeUtf(recipe.getGroup());
		friendlyByteBuf.writeEnum(recipe.category());		
	} // end toNetwork()

} // end class