package com.gsr.gsr_yatm.recipe.cystallizing;

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
import net.minecraftforge.fluids.FluidStack;

public class CrystallizingRecipeBuilder implements RecipeBuilder
{
	private @Nullable ResourceLocation m_identifier;
	private @Nullable ItemStack m_result;
	private @Nullable IIngredient<ItemStack> m_seed;	
	private @Nullable IIngredient<FluidStack> m_input;
	private boolean m_consumeSeed = false;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;
	private @NotNull String m_group = "";
	private final @NotNull Advancement.Builder m_advancement = Advancement.Builder.advancement();


	
	public @NotNull CrystallizingRecipeBuilder identifier(@Nullable ResourceLocation identifier)
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public @NotNull CrystallizingRecipeBuilder input(@Nullable IIngredient<FluidStack> input)
	{
		this.m_input = input;
		return this;
	} // end input()
	
	public @NotNull CrystallizingRecipeBuilder seed(@Nullable IIngredient<ItemStack> seed)
	{
		this.m_seed = seed;
		return this;
	} // end seed()

	public @NotNull CrystallizingRecipeBuilder result(@Nullable ItemStack result)
	{
		this.m_result = result == null ? null : result.copy();
		return this;
	} // end result()

	public @NotNull CrystallizingRecipeBuilder consumeSeed(boolean consumeSeed) 
	{
		this.m_consumeSeed = consumeSeed;
		return this;
	} // end consumeSeed()
	
	public @NotNull CrystallizingRecipeBuilder currentPerTick(int currentPerTick)
	{
		this.m_currentPerTick = currentPerTick;
		return this;
	} // end currentPerTick()
	
	public @NotNull CrystallizingRecipeBuilder timeInTicks(int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public @NotNull CrystallizingRecipe build()
	{
		CrystallizingRecipe r = new CrystallizingRecipe(this.m_identifier, this.m_input, this.m_seed, this.m_result);
		r.m_consumeSeed = this.m_consumeSeed;
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
		return this.m_result.getItem();
	} // end getResult()

	@Override
	public void save(Consumer<FinishedRecipe> writer, ResourceLocation fileName)
	{
		this.validate(fileName);
		writer.accept(new CrystallizingFinishedRecipe(fileName, this.m_result, this.m_input, this.m_seed, this.m_consumeSeed, this.m_currentPerTick, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(@NotNull ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end Builder