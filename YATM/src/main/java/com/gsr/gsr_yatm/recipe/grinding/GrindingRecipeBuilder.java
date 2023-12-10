package com.gsr.gsr_yatm.recipe.grinding;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.recipe.RecipeBuilderBase;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GrindingRecipeBuilder extends RecipeBuilderBase
{
	private ItemStack m_result;
	private IIngredient<ItemStack> m_input;
	private int m_currentPerTick = -1;
	private int m_timeInTicks = -1;
	
	

	public @NotNull GrindingRecipeBuilder input(@NotNull IIngredient<ItemStack> input)
	{
		this.m_input = Objects.requireNonNull(input);
		return this;
	} // end input()
	
	public @NotNull GrindingRecipeBuilder result(@NotNull ItemStack result) 
	{
		this.m_result = Objects.requireNonNull(result);
		return this;
	} // end result()

	public @NotNull GrindingRecipeBuilder currentPerTick(@NotNegative int currentPerTick)
	{
		this.m_currentPerTick = Contract.notNegative(currentPerTick);
		return this;
	} // end currentPerTick()

	public @NotNull GrindingRecipeBuilder timeInTicks(@NotNegative int timeInTicks)
	{
		this.m_timeInTicks = Contract.notNegative(timeInTicks);
		return this;
	} // end timeInTicks()



	@Override
	public Item getResult()
	{
		return this.m_result.getItem();
	} // end getResult()

	@Override
	public @NotNull FinishedRecipe createFinishedRecipe(@NotNull ResourceLocation fileName, @NotNull AdvancementHolder advancement)
	{
		return new GrindingFinishedRecipe(fileName, advancement, this.m_group, this.m_result, this.m_input, this.m_currentPerTick, this.m_timeInTicks);
	} // end createFinishedRecipe()

} // end Builder