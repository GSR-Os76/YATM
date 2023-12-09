package com.gsr.gsr_yatm.recipe.spinning;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.FinishedRecipeBase;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SpinningFinishedRecipe extends FinishedRecipeBase
{
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<ItemStack> m_input;
	
	
	
	public SpinningFinishedRecipe(@NotNull ResourceLocation identifier, @NotNull AdvancementHolder advancementHolder, @NotNull String group, @NotNull ItemStack result, @NotNull IIngredient<ItemStack> input) 
	{
		super(Objects.requireNonNull(identifier), Objects.requireNonNull(advancementHolder), Objects.requireNonNull(group));
		
		this.m_result = Objects.requireNonNull(result);
		this.m_input = Objects.requireNonNull(input);
	} // end consturctor
	
	
	
	@Override
	public void serializeRecipeData(@NotNull JsonObject jsonObject)
	{
		super.serializeRecipeData(jsonObject);
		
		jsonObject.add(IngredientUtil.RESULT_KEY, IngredientUtil.nbtItemStackToJson(this.m_result));
		jsonObject.add(IngredientUtil.INPUT_KEY, IngredientUtil.writeIngredient(this.m_input));
	} // end serializeRecipeData()
	
	@Override
	public @NotNull RecipeSerializer<SpinningRecipe> type()
	{
		return YATMRecipeSerializers.SPINNING.get();
	} // end type()

} // end class