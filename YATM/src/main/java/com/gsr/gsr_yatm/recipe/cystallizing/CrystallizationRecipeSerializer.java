package com.gsr.gsr_yatm.recipe.cystallizing;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class CrystallizationRecipeSerializer implements RecipeSerializer<CrystallizationRecipe>
{
	@Override
	public CrystallizationRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		CrystallizationRecipeBuilder builder = new CrystallizationRecipeBuilder();
		
		builder.identifier(resourceLocation);
		builder.result(CraftingHelper.getItemStack(jsonObject.getAsJsonObject(RecipeUtilities.RESULT_OBJECT_KEY), false));
		JsonObject seedObj = jsonObject.getAsJsonObject(RecipeUtilities.SEED_KEY);
		builder.seed(CraftingHelper.getIngredient(seedObj.getAsJsonObject(RecipeUtilities.INGREDIENT_KEY), false));
		if(seedObj.has(RecipeUtilities.CONSUME_SEED_KEY)) 
		{
			builder.consumeSeed(seedObj.get(RecipeUtilities.CONSUME_SEED_KEY).getAsBoolean());
		}
		builder.input(RecipeUtilities.fluidStackFromJson(jsonObject.getAsJsonObject(RecipeUtilities.INPUT_OBJECT_KEY)));
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
	public @Nullable CrystallizationRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf friendlyByteBuf, CrystallizationRecipe recipe)
	{
		// TODO Auto-generated method stub
		
	}

} // end class