package com.gsr.gsr_yatm.recipe.extracting;

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
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class ExtractingRecipeBuilder implements RecipeBuilder
{
	// TODO, TODONE, maybe these should more all be nullable, considering they can validly be null sometimes, just shouldn't be when
	private @Nullable ResourceLocation m_identifier;
	private @Nullable FluidStack m_result;
	private @Nullable IIngredient<ItemStack> m_input;
	private @NotNull ItemStack m_inputRemainder = ItemStack.EMPTY;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;

	private @NotNull String m_group = "";
	private final @NotNull Advancement.Builder m_advancement = Advancement.Builder.advancement();


	public @NotNull ExtractingRecipeBuilder identifier(@Nullable ResourceLocation identifier)
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public @NotNull ExtractingRecipeBuilder input(@Nullable IIngredient<ItemStack> input)
	{
		this.m_input = input;
		return this;
	} // end input()

	public @NotNull ExtractingRecipeBuilder result(@Nullable FluidStack result)
	{
		this.m_result = result.copy();
		return this;
	} // end result()

	public @NotNull ExtractingRecipeBuilder inputRemainder(@NotNull ItemStack inputRemainder)
	{
		Objects.requireNonNull(inputRemainder);
		this.m_inputRemainder = inputRemainder.copy();
		return this;
	} // end inputRemainder()

	public @NotNull ExtractingRecipeBuilder currentPerTick(int currentPerTick)
	{
		this.m_currentPerTick = currentPerTick;
		return this;
	} // end currentPerTick()

	public @NotNull ExtractingRecipeBuilder timeInTicks(int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public @NotNull ExtractingRecipe build()
	{
		ExtractingRecipe r = new ExtractingRecipe(this.m_identifier, this.m_input, this.m_result);
		r.m_inputRemainder = this.m_inputRemainder;
		r.m_currentPerTick = this.m_currentPerTick;
		r.m_timeInTicks = this.m_timeInTicks;
		r.m_group = this.m_group;
		return r;
	} // end build()



	@Override
	public RecipeBuilder unlockedBy(String triggerName, CriterionTriggerInstance trigger)
	{
		this.m_advancement.addCriterion(triggerName, trigger);
		return this;
	} // end unlockedBy()

	@Override
	public RecipeBuilder group(String group)
	{
		Objects.requireNonNull(group);
		this.m_group = group;// == null ? "" : group;
		return this;
	} // end group()

	@Override
	public Item getResult()
	{
		return Items.AIR;
	} // end getResult()

	@Override
	public void save(Consumer<FinishedRecipe> writer, ResourceLocation fileName)
	{
		this.validate(fileName);
		writer.accept(new ExtractingFinishedRecipe(fileName, this.m_result, this.m_input, this.m_inputRemainder, this.m_currentPerTick, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(@NotNull ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end Builder