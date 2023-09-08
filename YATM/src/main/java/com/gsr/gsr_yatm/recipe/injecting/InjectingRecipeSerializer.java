package com.gsr.gsr_yatm.recipe.injecting;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.JsonUtil;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class InjectingRecipeSerializer implements RecipeSerializer<InjectingRecipe>
{
	@Override
	public @NotNull InjectingRecipe fromJson(@NotNull ResourceLocation resourceLocation, @NotNull JsonObject jsonObject)
	{
		InjectingRecipeBuilder builder = new InjectingRecipeBuilder();
		
		builder.identifier(resourceLocation);
		builder.result(CraftingHelper.getItemStack(jsonObject.getAsJsonObject(IngredientUtil.RESULT_KEY), true));
		builder.input(IngredientUtil.readIngredient(jsonObject.getAsJsonObject(IngredientUtil.INPUT_KEY).getAsJsonObject(IngredientUtil.INGREDIENT_KEY)).cast());
		builder.substrate(IngredientUtil.readIngredient(jsonObject.getAsJsonObject(IngredientUtil.SUBSTRATE_KEY).getAsJsonObject(IngredientUtil.INGREDIENT_KEY)).cast());
		
		JsonUtil.ifPresent(jsonObject, IngredientUtil.CURRENT_PER_TICK_KEY, (e) -> builder.currentPerTick(e.getAsInt()));
		JsonUtil.ifPresent(jsonObject, IngredientUtil.TIME_IN_TICKS_KEY, (e) -> builder.timeInTicks(e.getAsInt()));
		JsonUtil.ifPresent(jsonObject, IngredientUtil.GROUP_KEY, (e) -> builder.group(e.getAsString()));
		
		return builder.build();
	} // end fromJson()

	@Override
	public @Nullable InjectingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf resourceLocation, InjectingRecipe recipe)
	{
		// TODO Auto-generated method stub		
	}

} // end class