package com.gsr.gsr_yatm.recipe;

import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ExtrusionRecipeBuilder implements RecipeBuilder
{
	private ResourceLocation m_identifier;
	private ItemStack m_result;

	private Ingredient m_input;
	private Ingredient m_die;

	private ItemStack m_inputRemainder = ItemStack.EMPTY;
	private ItemStack m_dieRemainder = null;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;
	
	private String m_group = "";
	private Advancement.Builder m_advancement = Advancement.Builder.advancement();
	
	
	
	public ExtrusionRecipeBuilder identifier(ResourceLocation identifier) 
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public ExtrusionRecipeBuilder input(Ingredient input)
	{
		this.m_input = input;
		return this;
	} // end input()

	public ExtrusionRecipeBuilder die(Ingredient die)
	{
		this.m_die = die;
		return this;
	} // end die()
	
	public ExtrusionRecipeBuilder result(ItemStack result) 
	{
		this.m_result = result;
		return this;
	} // end result()



	public ExtrusionRecipeBuilder inputRemainder(ItemStack remainder)
	{
		this.m_inputRemainder = remainder.copy();
		return this;
	} // end inputRemainder()

	public ExtrusionRecipeBuilder dieRemainder(ItemStack remainder)
	{
		this.m_dieRemainder = remainder.copy();
		return this;
	} // end dieRemainder()

	public ExtrusionRecipeBuilder currentPerTick(int currentPerTick)
	{
		this.m_currentPerTick = currentPerTick;
		return this;
	} // end currentPerTick()

	public ExtrusionRecipeBuilder timeInTicks(int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public ExtrusionRecipe build()
	{
		ExtrusionRecipe temp = new ExtrusionRecipe(this.m_identifier, this.m_input, this.m_die, this.m_result);
		temp.m_inputRemainder = this.m_inputRemainder;
		temp.m_dieRemainder = this.m_dieRemainder;
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
		writer.accept(new ExtrusionFinishedRecipe(fileName, this.m_result, this.m_input, this.m_die, this.m_inputRemainder, this.m_dieRemainder, this.m_currentPerTick, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end Builder