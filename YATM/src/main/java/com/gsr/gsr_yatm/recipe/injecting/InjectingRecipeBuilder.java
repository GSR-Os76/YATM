package com.gsr.gsr_yatm.recipe.injecting;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

// relatively failure tolerant, if input doesn't match contract it's tolerated as it can be changed again before use
public class InjectingRecipeBuilder implements RecipeBuilder
{
	private @NotNull ResourceLocation m_identifier;
	private @NotNull ItemStack m_result;
	private @NotNull IIngredient<FluidStack> m_input;
	private @NotNull IIngredient<ItemStack> m_substrate;
	private @NotNegative int m_currentPerTick = 8;
	private @NotNegative int m_timeInTicks = 20;
	private @NotNull String m_group = "";
	
	private @NotNull Advancement.Builder m_advancement;


	
	public @NotNull InjectingRecipeBuilder identifier(@NotNull ResourceLocation identifier)
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public @NotNull InjectingRecipeBuilder input(@NotNull IIngredient<FluidStack> input)
	{
		this.m_input = input;
		return this;
	} // end input()
	
	public @NotNull InjectingRecipeBuilder substrate(@NotNull IIngredient<ItemStack> substrate)
	{
		this.m_substrate = substrate;
		return this;
	} // end seed()

	public @NotNull InjectingRecipeBuilder result(@NotNull ItemStack result)
	{
		this.m_result = result == null ? null : result.copy();
		return this;
	} // end result()
	
	public @NotNull InjectingRecipeBuilder currentPerTick(@NotNegative int currentPerTick)
	{
		this.m_currentPerTick = currentPerTick;
		return this;
	} // end currentPerTick()
	
	public @NotNull InjectingRecipeBuilder timeInTicks(@NotNegative int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public @NotNull InjectingRecipe build()
	{
		return new InjectingRecipe(
				this.m_identifier, 
				this.m_input, 
				this.m_substrate, 
				this.m_result,
				this.m_currentPerTick,
				this.m_timeInTicks,
				this.m_group);
	} // end build()



	@Override
	public @NotNull InjectingRecipeBuilder unlockedBy(String triggerName, CriterionTriggerInstance trigger)
	{
		this.m_advancement.addCriterion(triggerName, trigger);
		return this;
	} // end unlockedBy()

	@Override
	public @NotNull InjectingRecipeBuilder group(@NotNull String group)
	{
		this.m_group = group;
		return this;
	} // end group()

	@Override
	public @NotNull Item getResult()
	{
		return this.m_result.getItem();
	} // end getResult()

	@Override
	public void save(@NotNull Consumer<FinishedRecipe> writer, @NotNull ResourceLocation fileName)
	{
		this.validate(fileName);
		writer.accept(new InjectingFinishedRecipe(fileName, this.m_result, this.m_input, this.m_substrate, this.m_currentPerTick, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(@NotNull ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end class