package com.gsr.gsr_yatm.recipe.boiling;

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
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class BoilingRecipeBuilder extends RecipeBuilderBase
{
	private FluidStack m_result;
	private IIngredient<FluidStack> m_input;
	private int m_temperature = -1;
	private int m_timeInTicks = -1;


	
	public @NotNull BoilingRecipeBuilder input(@NotNull IIngredient<FluidStack> input)
	{
		this.m_input = Objects.requireNonNull(input);
		return this;
	} // end input()

	public @NotNull BoilingRecipeBuilder result(@NotNull FluidStack result)
	{
		this.m_result = result.copy();
		return this;
	} // end result()

	public @NotNull BoilingRecipeBuilder temperature(@NotNegative int temperature) 
	{
		this.m_temperature = Contract.notNegative(temperature);
		return this;
	} // end temperature()
	
	public @NotNull BoilingRecipeBuilder timeInTicks(@NotNegative int timeInTicks)
	{
		this.m_timeInTicks = Contract.notNegative(timeInTicks);
		return this;
	} // end timeInTicks()

	

	@Override
	public Item getResult()
	{
		return Items.AIR;
	} // end getResult()

	@Override
	public @NotNull FinishedRecipe createFinishedRecipe(@NotNull ResourceLocation fileName, @NotNull AdvancementHolder advancement)
	{
		return new BoilingFinishedRecipe(fileName, advancement, this.m_group, this.m_result, this.m_input, this.m_temperature, this.m_timeInTicks);
	} // end createFinishedRecipe()

} // end Builder