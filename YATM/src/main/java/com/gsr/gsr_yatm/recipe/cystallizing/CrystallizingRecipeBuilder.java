package com.gsr.gsr_yatm.recipe.cystallizing;

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

public class CrystallizingRecipeBuilder extends RecipeBuilderBase
{
	private ItemStack m_result;
	private IIngredient<ItemStack> m_seed;	
	private IIngredient<FluidStack> m_input;
	private boolean m_consumeSeed;
	private int m_currentPerTick = -1;
	private int m_timeInTicks = -1;



	public @NotNull CrystallizingRecipeBuilder input(@NotNull IIngredient<FluidStack> input)
	{
		this.m_input = Objects.requireNonNull(input);
		return this;
	} // end input()
	
	public @NotNull CrystallizingRecipeBuilder seed(@NotNull IIngredient<ItemStack> seed)
	{
		this.m_seed = Objects.requireNonNull(seed);
		return this;
	} // end seed()

	public @NotNull CrystallizingRecipeBuilder result(@NotNull ItemStack result)
	{
		this.m_result = result.copy();
		return this;
	} // end result()

	public @NotNull CrystallizingRecipeBuilder consumeSeed(boolean consumeSeed) 
	{
		this.m_consumeSeed = consumeSeed;
		return this;
	} // end consumeSeed()
	
	public @NotNull CrystallizingRecipeBuilder currentPerTick(@NotNegative int currentPerTick)
	{
		this.m_currentPerTick = Contract.notNegative(currentPerTick);
		return this;
	} // end currentPerTick()
	
	public @NotNull CrystallizingRecipeBuilder timeInTicks(@NotNegative int timeInTicks)
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
		return new CrystallizingFinishedRecipe(fileName, advancement, this.m_group, this.m_result, this.m_input, this.m_seed, this.m_consumeSeed, this.m_currentPerTick, this.m_timeInTicks);
	}

} // end Builder