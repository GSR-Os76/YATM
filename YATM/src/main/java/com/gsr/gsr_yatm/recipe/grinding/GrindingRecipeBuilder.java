package com.gsr.gsr_yatm.recipe.grinding;

import java.util.function.Consumer;

import com.gsr.gsr_yatm.recipe.ingredient.IYATMIngredient;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GrindingRecipeBuilder implements RecipeBuilder
{
	private ResourceLocation m_identifier;
	private ItemStack m_result;
	private IYATMIngredient<ItemStack> m_input;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;
	
	String m_group = "";
	private Advancement.Builder m_advancement = Advancement.Builder.advancement();
	
	
	
	public GrindingRecipeBuilder identifier(ResourceLocation identifier) 
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public GrindingRecipeBuilder input(IYATMIngredient<ItemStack> input)
	{
		this.m_input = input;
		return this;
	} // end input()
	
	public GrindingRecipeBuilder result(ItemStack result) 
	{
		this.m_result = result;
		return this;
	} // end result()



	public GrindingRecipeBuilder currentPerTick(int currentPerTick)
	{
		this.m_currentPerTick = currentPerTick;
		return this;
	} // end currentPerTick()

	public GrindingRecipeBuilder timeInTicks(int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public GrindingRecipe build()
	{
		GrindingRecipe temp = new GrindingRecipe(this.m_identifier, this.m_input, this.m_result);
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
		this.m_group = group == null ? "" : group;
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
		writer.accept(new GrindingFinishedRecipe(fileName, this.m_result, this.m_input, this.m_currentPerTick, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end Builder