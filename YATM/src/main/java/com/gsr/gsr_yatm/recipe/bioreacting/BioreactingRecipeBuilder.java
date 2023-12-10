package com.gsr.gsr_yatm.recipe.bioreacting;

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

public class BioreactingRecipeBuilder extends RecipeBuilderBase
{
	private FluidStack m_result;
	private IIngredient<ItemStack> m_input;
	private int m_currentPerTick = -1;
	private int m_timeInTicks = -1;
	


	public @NotNull BioreactingRecipeBuilder input(@NotNull IIngredient<ItemStack> input)
	{
		this.m_input = Objects.requireNonNull(input);
		return this;
	} // end input()

	public @NotNull BioreactingRecipeBuilder result(@NotNull FluidStack result)
	{
		this.m_result = result.copy();
		return this;
	} // end result()

	public @NotNull BioreactingRecipeBuilder currentPerTick(@NotNegative int currentPerTick)
	{
		this.m_currentPerTick = Contract.notNegative(currentPerTick);
		return this;
	} // end currentPerTick()

	public @NotNull BioreactingRecipeBuilder timeInTicks(@NotNegative int timeInTicks)
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
		return new BioreactingFinishedRecipe(fileName, advancement, this.m_group, this.m_result, this.m_input, this.m_currentPerTick, this.m_timeInTicks);
	} // end createFinishedRecipe()

} // end Builder