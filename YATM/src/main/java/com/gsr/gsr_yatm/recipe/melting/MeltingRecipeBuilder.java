package com.gsr.gsr_yatm.recipe.melting;

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
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class MeltingRecipeBuilder implements RecipeBuilder
{
	private @NotNull ResourceLocation m_identifier;
	private @NotNull FluidStack m_result;
	private @NotNull IIngredient<ItemStack> m_input;
	private @NotNegative int m_temperature = 8;
	private @NotNegative int m_timeInTicks = 20;
	private @NotNull String m_group = "";
	
	private @NotNull Advancement.Builder m_advancement;


	
	public @NotNull MeltingRecipeBuilder identifier(@NotNull ResourceLocation identifier)
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public @NotNull MeltingRecipeBuilder input(@NotNull IIngredient<ItemStack> input)
	{
		this.m_input = input;
		return this;
	} // end input()

	public @NotNull MeltingRecipeBuilder result(@NotNull FluidStack result)
	{
		this.m_result = result == null ? null : result.copy();
		return this;
	} // end result()
	
	public @NotNull MeltingRecipeBuilder temperature(@NotNegative int temperature)
	{
		this.m_temperature = temperature;
		return this;
	} // end currentPerTick()
	
	public @NotNull MeltingRecipeBuilder timeInTicks(@NotNegative int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public @NotNull MeltingRecipe build()
	{
		return new MeltingRecipe(
				this.m_identifier, 
				this.m_input,  
				this.m_result,
				this.m_temperature,
				this.m_timeInTicks,
				this.m_group);
	} // end build()



	@Override
	public @NotNull MeltingRecipeBuilder unlockedBy(String triggerName, CriterionTriggerInstance trigger)
	{
		this.m_advancement.addCriterion(triggerName, trigger);
		return this;
	} // end unlockedBy()

	@Override
	public @NotNull MeltingRecipeBuilder group(@NotNull String group)
	{
		this.m_group = group;
		return this;
	} // end group()

	@Override
	public @NotNull Item getResult()
	{
		return Items.AIR;
	} // end getResult()

	@Override
	public void save(@NotNull Consumer<FinishedRecipe> writer, @NotNull ResourceLocation fileName)
	{
		this.validate(fileName);
		writer.accept(new MeltingFinishedRecipe(fileName, this.m_result, this.m_input, this.m_temperature, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(@NotNull ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end class