package com.gsr.gsr_yatm.recipe;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

public abstract class RecipeBuilderBase implements RecipeBuilder
{
	protected @NotNull ResourceLocation m_identifier;
	protected @NotNull String m_group = "";
	protected final @NotNull Map<String, Criterion<?>> m_criteria = new LinkedHashMap<>();
	
	
	
	public @NotNull RecipeBuilderBase identifier(@NotNull ResourceLocation identifier) 
	{
		Objects.requireNonNull(identifier);
		this.m_identifier = identifier;
		return this;
	} // end identifier()


	
	@Override
	public @NotNull RecipeBuilderBase unlockedBy(@NotNull String criteriaKey, @NotNull Criterion<?> criteria)
	{
		this.m_criteria.put(criteriaKey, criteria);
		return this;
	}

	@Override
	public @NotNull RecipeBuilderBase group(@Nullable String group)
	{
		this.m_group = group == null ? "" : group;
		return this;
	} // end group()

	@Override
	public void save(@NotNull RecipeOutput writer, @NotNull ResourceLocation fileName)
	{
		this.validate(fileName);
		Advancement.Builder advancement = writer.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(fileName)).rewards(AdvancementRewards.Builder.recipe(fileName)).requirements(AdvancementRequirements.Strategy.OR);
	    this.m_criteria.forEach(advancement::addCriterion);
		writer.accept(this.createFinishedRecipe(fileName, advancement.build(fileName.withPrefix("recipes/"))));
	} // end save()
	
	
	
	public abstract @NotNull FinishedRecipe createFinishedRecipe(@NotNull ResourceLocation fileName, @NotNull AdvancementHolder advancement);
	
	
	
	protected void validate(@NotNull ResourceLocation filename)
	{
		if (this.m_criteria.isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + filename);
		}
	} // end validate()

} // end class