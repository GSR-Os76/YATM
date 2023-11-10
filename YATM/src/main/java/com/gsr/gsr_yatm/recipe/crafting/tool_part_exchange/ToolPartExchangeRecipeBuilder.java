package com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;

public class ToolPartExchangeRecipeBuilder implements RecipeBuilder
{
	private ResourceLocation m_identifier;
	private ItemStack m_result;
	private IIngredient<ItemStack> m_tool;
	private IIngredient<ItemStack> m_part;
	private @NotNull CraftingBookCategory m_category = CraftingBookCategory.MISC;
	private String m_group = "";
	private final Advancement.Builder m_advancement = Advancement.Builder.advancement();


	
	public @NotNull ToolPartExchangeRecipeBuilder identifier(@NotNull ResourceLocation identifier)
	{
		this.m_identifier = Objects.requireNonNull(identifier);
		return this;
	} // end identifier()

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



	public @NotNull ToolPartExchangeRecipe build()
	{
		ToolPartExchangeRecipe r = new ToolPartExchangeRecipe(this.m_identifier, this.m_tool, this.m_part, this.m_result, this.m_group, this.m_category);
		return r;
	} // end build()



	@Override
	public RecipeBuilder unlockedBy(@NotNull String triggerName, @NotNull CriterionTriggerInstance trigger)
	{
		this.m_advancement.addCriterion(triggerName, trigger);
		return this;
	} // end unlockedBy()

	@Override
	public RecipeBuilder group(@Nullable String group)
	{
		this.m_group = group == null ? "" : group;
		return this;
	} // end group()

	@Override
	public Item getResult()
	{
		return this.m_result.getItem();
	} // end getResult()

	@Override
	public void save(@NotNull Consumer<FinishedRecipe> writer, @NotNull ResourceLocation fileName)
	{
		this.validate(fileName);
		writer.accept(new ToolPartExchangeFinishedRecipe(fileName, this.m_result, this.m_tool, this.m_part, this.m_group, this.m_category, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(@NotNull ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end Builder