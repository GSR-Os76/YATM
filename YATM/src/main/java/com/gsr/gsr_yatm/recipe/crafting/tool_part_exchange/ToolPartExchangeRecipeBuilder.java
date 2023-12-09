package com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.recipe.RecipeBuilderBase;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;

public class ToolPartExchangeRecipeBuilder extends RecipeBuilderBase
{
	private ItemStack m_result;
	private IIngredient<ItemStack> m_tool;
	private IIngredient<ItemStack> m_part;
	private @NotNull CraftingBookCategory m_category = CraftingBookCategory.MISC;


	
	public @NotNull ToolPartExchangeRecipeBuilder result(@NotNull ItemStack result)
	{
		this.m_result = result.copy();
		return this;
	} // end result()
	
	public @NotNull ToolPartExchangeRecipeBuilder tool(@NotNull IIngredient<ItemStack> tool)
	{
		this.m_tool = Objects.requireNonNull(tool);
		return this;
	} // end tool()
	
	public @NotNull ToolPartExchangeRecipeBuilder part(@NotNull IIngredient<ItemStack> part)
	{
		this.m_part = Objects.requireNonNull(part);
		return this;
	} // end part()

	public @NotNull ToolPartExchangeRecipeBuilder category(@NotNull CraftingBookCategory category) 
	{
		this.m_category = Objects.requireNonNull(category);
		return this;
	} // end consumeSeed()



	@Override
	public Item getResult()
	{
		return this.m_result.getItem();
	} // end getResult()

	public @NotNull FinishedRecipe createFinishedRecipe(@NotNull ResourceLocation fileName, @NotNull AdvancementHolder advancement)
	{
		return new ToolPartExchangeFinishedRecipe(fileName, advancement, this.m_group, this.m_result, this.m_tool, this.m_part, this.m_category);
	} // end createFinishedRecipe()

} // end Builder