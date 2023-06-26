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
import net.minecraftforge.fluids.FluidStack;

public class CrystallizationRecipeBuilder implements RecipeBuilder
{
	private ResourceLocation m_identifier;
	private ItemStack m_result;
	private Ingredient m_seed;	
	private FluidStack m_input;
	boolean m_consumeSeed = false;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;
	private String m_group = "";
	private Advancement.Builder m_advancement = Advancement.Builder.advancement();


	
	public CrystallizationRecipeBuilder identifier(ResourceLocation identifier)
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public CrystallizationRecipeBuilder input(FluidStack input)
	{
		this.m_input = input;
		return this;
	} // end input()
	
	public CrystallizationRecipeBuilder seed(Ingredient seed)
	{
		this.m_seed = seed;
		return this;
	} // end seed()

	public CrystallizationRecipeBuilder result(ItemStack result)
	{
		this.m_result = result;
		return this;
	} // end result()

	public CrystallizationRecipeBuilder consumeSeed(boolean consumeSeed) 
	{
		this.m_consumeSeed = consumeSeed;
		return this;
	} // end consumeSeed()
	
	public CrystallizationRecipeBuilder currentPerTick(int currentPerTick)
	{
		this.m_currentPerTick = currentPerTick;
		return this;
	} // end currentPerTick()
	
	public CrystallizationRecipeBuilder timeInTicks(int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public CrystallizationRecipe build()
	{
		CrystallizationRecipe r = new CrystallizationRecipe(this.m_identifier, this.m_input, this.m_seed, this.m_result);
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
		writer.accept(new CrystallizationFinishedRecipe(fileName, this.m_result, this.m_input, this.m_seed, this.m_consumeSeed, this.m_currentPerTick, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end Builder