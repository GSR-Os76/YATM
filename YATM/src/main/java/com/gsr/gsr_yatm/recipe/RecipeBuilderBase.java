package com.gsr.gsr_yatm.recipe;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;


// possibly do this and base class builders
public abstract class RecipeBuilderBase implements RecipeBuilder
{
	protected @NotNull ResourceLocation m_identifier;
	
	protected @NotNull String m_group = "";
	protected @NotNull Advancement.Builder m_advancement = Advancement.Builder.advancement();
	
	
	
	public @NotNull RecipeBuilderBase identifier(@NotNull ResourceLocation identifier) 
	{
		Objects.requireNonNull(identifier);
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	

	public abstract @NotNull SpinningRecipe build();



	@Override
	public RecipeBuilder unlockedBy(String triggerName, CriterionTriggerInstance trigger)
	{
		this.m_advancement.addCriterion(triggerName, trigger);
		return this;
	} // end unlockedBy()

	@Override
	public RecipeBuilder group(String group)
	{
		this.m_group = group == null ? "" : group;
		return this;
	} // end group()

	@Override
	public abstract Item getResult();

	@Override
	public void save(Consumer<FinishedRecipe> writer, ResourceLocation fileName)
	{
		this.validate(fileName);
		// writer.accept(new SpinningFinishedRecipe(fileName, this.m_result, this.m_input, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(@NotNull ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

}
