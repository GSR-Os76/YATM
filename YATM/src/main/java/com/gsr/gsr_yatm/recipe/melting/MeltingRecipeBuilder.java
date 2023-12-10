package com.gsr.gsr_yatm.recipe.melting;

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
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class MeltingRecipeBuilder extends RecipeBuilderBase
{
	private FluidStack m_result;
	private IIngredient<ItemStack> m_input;
	private int m_temperature = -1;
	private int m_timeInTicks = -1;


	
	public @NotNull MeltingRecipeBuilder input(@NotNull IIngredient<ItemStack> input)
	{
		this.m_input = Objects.requireNonNull(input);
		return this;
	} // end input()

	public @NotNull MeltingRecipeBuilder result(@NotNull FluidStack result)
	{
		this.m_result = result.copy();
		return this;
	} // end result()
	
	public @NotNull MeltingRecipeBuilder temperature(@NotNegative int temperature)
	{
		this.m_temperature = Contract.notNegative(temperature);
		return this;
	} // end currentPerTick()
	
	public @NotNull MeltingRecipeBuilder timeInTicks(@NotNegative int timeInTicks)
	{
		this.m_timeInTicks = Contract.notNegative(timeInTicks);
		return this;
	} // end timeInTicks()



	@Override
	public @NotNull Item getResult()
	{
		return Items.AIR;
	} // end getResult()

	@Override
	public @NotNull FinishedRecipe createFinishedRecipe(@NotNull ResourceLocation fileName, @NotNull AdvancementHolder advancement)
	{
		return new MeltingFinishedRecipe(fileName, advancement, this.m_group, this.m_result, this.m_input, this.m_temperature, this.m_timeInTicks);
	} // end createFinishedRecipe()

} // end class