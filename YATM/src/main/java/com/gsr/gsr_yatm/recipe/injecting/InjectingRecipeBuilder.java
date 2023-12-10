package com.gsr.gsr_yatm.recipe.injecting;

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
import net.minecraftforge.fluids.FluidStack;

public class InjectingRecipeBuilder extends RecipeBuilderBase
{
	private ItemStack m_result;
	private IIngredient<FluidStack> m_input;
	private IIngredient<ItemStack> m_substrate;
	private int m_currentPerTick = -1;
	private int m_timeInTicks = -1;


	
	public @NotNull InjectingRecipeBuilder input(@NotNull IIngredient<FluidStack> input)
	{
		this.m_input = Objects.requireNonNull(input);
		return this;
	} // end input()
	
	public @NotNull InjectingRecipeBuilder substrate(@NotNull IIngredient<ItemStack> substrate)
	{
		this.m_substrate = Objects.requireNonNull(substrate);
		return this;
	} // end seed()

	public @NotNull InjectingRecipeBuilder result(@NotNull ItemStack result)
	{
		this.m_result = result.copy();
		return this;
	} // end result()
	
	public @NotNull InjectingRecipeBuilder currentPerTick(@NotNegative int currentPerTick)
	{
		this.m_currentPerTick = Contract.notNegative(currentPerTick);
		return this;
	} // end currentPerTick()
	
	public @NotNull InjectingRecipeBuilder timeInTicks(@NotNegative int timeInTicks)
	{
		this.m_timeInTicks = Contract.notNegative(timeInTicks);
		return this;
	} // end timeInTicks()



	@Override
	public @NotNull Item getResult()
	{
		return this.m_result.getItem();
	} // end getResult()

	@Override
	public @NotNull FinishedRecipe createFinishedRecipe(@NotNull ResourceLocation fileName, @NotNull AdvancementHolder advancement)
	{
		return new InjectingFinishedRecipe(fileName, advancement, this.m_group, this.m_result, this.m_input, this.m_substrate, this.m_currentPerTick, this.m_timeInTicks);
	} // end createFinishedRecipe

} // end class