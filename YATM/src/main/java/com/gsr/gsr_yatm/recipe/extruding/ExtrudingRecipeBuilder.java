package com.gsr.gsr_yatm.recipe.extruding;

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

public class ExtrudingRecipeBuilder implements RecipeBuilder
{
	private @Nullable ResourceLocation m_identifier;
	private @Nullable ItemStack m_result;

	private @Nullable IIngredient<ItemStack> m_input;
	private @Nullable IIngredient<ItemStack> m_die;

	private @NotNull ItemStack m_inputRemainder = ItemStack.EMPTY;
	private @Nullable ItemStack m_dieRemainder;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;
	
	private @NotNull String m_group = "";
	private final @NotNull Advancement.Builder m_advancement = Advancement.Builder.advancement();
	
	
	
	public @NotNull ExtrudingRecipeBuilder identifier(@Nullable ResourceLocation identifier) 
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public @NotNull ExtrudingRecipeBuilder input(@Nullable IIngredient<ItemStack> input)
	{
		this.m_input = input;
		return this;
	} // end input()

	public @NotNull ExtrudingRecipeBuilder die(@Nullable IIngredient<ItemStack> die)
	{
		this.m_die = die;
		return this;
	} // end die()
	
	public @NotNull ExtrudingRecipeBuilder result(@Nullable ItemStack result) 
	{
		this.m_result = result == null ? null : result.copy();
		return this;
	} // end result()



	public @NotNull ExtrudingRecipeBuilder inputRemainder(@NotNull ItemStack remainder)
	{
		Objects.requireNonNull(remainder);
		this.m_inputRemainder = remainder.copy();
		return this;
	} // end inputRemainder()

	public @NotNull ExtrudingRecipeBuilder dieRemainder(@Nullable ItemStack remainder)
	{
		this.m_dieRemainder = remainder == null ? null :remainder.copy();
		return this;
	} // end dieRemainder()

	public @NotNull ExtrudingRecipeBuilder currentPerTick(int currentPerTick)
	{
		this.m_currentPerTick = currentPerTick;
		return this;
	} // end currentPerTick()

	public @NotNull ExtrudingRecipeBuilder timeInTicks(int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public @NotNull ExtrudingRecipe build()
	{
		ExtrudingRecipe temp = new ExtrudingRecipe(this.m_identifier, this.m_input, this.m_die, this.m_result);
		temp.m_inputRemainder = this.m_inputRemainder.copy();
		temp.m_dieRemainder =  this.m_dieRemainder == null ? null : this.m_dieRemainder.copy();
		temp.m_currentPerTick = this.m_currentPerTick;
		temp.m_timeInTicks = this.m_timeInTicks;
		temp.m_group = this.m_group;
		return temp;
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
		return this.m_result.getItem();
	} // end getResult()

	@Override
	public void save(Consumer<FinishedRecipe> writer, ResourceLocation fileName)
	{
		this.validate(fileName);
		writer.accept(new ExtrudingFinishedRecipe(fileName, this.m_result, this.m_input, this.m_die, this.m_inputRemainder, this.m_dieRemainder, this.m_currentPerTick, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(@NotNull ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end Builder