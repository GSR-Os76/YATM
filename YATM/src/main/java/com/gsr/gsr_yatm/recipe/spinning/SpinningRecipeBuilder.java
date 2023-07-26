package com.gsr.gsr_yatm.recipe.spinning;

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

public class SpinningRecipeBuilder implements RecipeBuilder
{
	private @Nullable ResourceLocation m_identifier;
	private @Nullable ItemStack m_result;

	private @Nullable IIngredient<ItemStack> m_input;
	
	private @NotNull String m_group = "";
	private @NotNull Advancement.Builder m_advancement = Advancement.Builder.advancement();
	
	
	
	public @NotNull SpinningRecipeBuilder identifier(@Nullable ResourceLocation identifier) 
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public @NotNull SpinningRecipeBuilder input(@Nullable IIngredient<ItemStack> input)
	{
		this.m_input = input;
		return this;
	} // end input()

	public @NotNull SpinningRecipeBuilder result(@Nullable ItemStack result) 
	{
		this.m_result = result;
		return this;
	} // end result()

	

	public @NotNull SpinningRecipe build()
	{
		SpinningRecipe temp = new SpinningRecipe(this.m_identifier, this.m_input, this.m_result);
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
		writer.accept(new SpinningFinishedRecipe(fileName, this.m_result, this.m_input, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(@NotNull ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end class