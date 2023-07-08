package com.gsr.gsr_yatm.recipe.boiling;

import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class BoilingRecipeBuilder implements RecipeBuilder
{
	private ResourceLocation m_identifier;
	private FluidStack m_result;
	private FluidStack m_input;
	private int m_temperature = 373;
	private int m_timeInTicks = 20;

	private String m_group = "";
	private Advancement.Builder m_advancement = Advancement.Builder.advancement();


	
	public BoilingRecipeBuilder identifier(ResourceLocation identifier)
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public BoilingRecipeBuilder input(FluidStack input)
	{
		this.m_input = input;
		return this;
	} // end input()

	public BoilingRecipeBuilder result(FluidStack result)
	{
		this.m_result = result;
		return this;
	} // end result()

	public BoilingRecipeBuilder temperature(int temperature) 
	{
		this.m_temperature = temperature;
		return this;
	} // end temperature()
	
	public BoilingRecipeBuilder timeInTicks(int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public BoilingRecipe build()
	{
		BoilingRecipe r = new BoilingRecipe(this.m_identifier, this.m_input, this.m_result);
		r.m_temperature = this.m_temperature;
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
		this.m_group = group == null ? "" : group;
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
		writer.accept(new BoilingFinishedRecipe(fileName, this.m_result, this.m_input, this.m_temperature, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end Builder