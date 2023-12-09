package com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange;

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
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ToolPartExchangeFinishedRecipe extends FinishedRecipeBase
{
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<ItemStack> m_tool;
	private final @NotNull IIngredient<ItemStack> m_part;
	private final @NotNull CraftingBookCategory m_category;
	
	
	
	public ToolPartExchangeFinishedRecipe(@NotNull ResourceLocation identifier, @NotNull AdvancementHolder advancement, @NotNull String group, @NotNull ItemStack result, @NotNull IIngredient<ItemStack> tool, @NotNull IIngredient<ItemStack> part, @NotNull CraftingBookCategory category) 
	{
		super(Objects.requireNonNull(identifier), Objects.requireNonNull(advancement), Objects.requireNonNull(group));	
		
		this.m_result = Objects.requireNonNull(result);
		this.m_tool = Objects.requireNonNull(tool);
		this.m_part = Objects.requireNonNull(part);
		this.m_category = Objects.requireNonNull(category);
	} // end constructor
	
	
	@Override
	public void serializeRecipeData(@NotNull JsonObject jsonObject)
	{
		super.serializeRecipeData(jsonObject);
		
		jsonObject.add(IngredientUtil.RESULT_KEY, IngredientUtil.nbtItemStackToJson(this.m_result));
		jsonObject.add(IngredientUtil.TOOL_KEY, IngredientUtil.writeIngredient(this.m_tool));
		jsonObject.add(IngredientUtil.PART_KEY, IngredientUtil.writeIngredient(this.m_part));
		jsonObject.addProperty(IngredientUtil.CATEGORY_KEY, this.m_category.getSerializedName());
	} // end serializeRecipeData()

	@Override
	public @NotNull RecipeSerializer<ToolPartExchangeRecipe> type()
	{
		return YATMRecipeSerializers.TOOL_PART_EXCHANGE.get();
	} // end type()

} // end class