package com.gsr.gsr_yatm.recipe.bioling;

import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class BiolingRecipeBuilder implements RecipeBuilder
{
	private ResourceLocation m_identifier;
	private FluidStack m_result;
	private Ingredient m_input;
	private ItemStack m_inputRemainder = ItemStack.EMPTY;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;

	private String m_group = "";
	private Advancement.Builder m_advancement = Advancement.Builder.advancement();


	public BiolingRecipeBuilder identifier(ResourceLocation identifier)
	{
		this.m_identifier = identifier;
		return this;
	} // end identifier()

	public BiolingRecipeBuilder input(Ingredient input)
	{
		this.m_input = input;
		return this;
	} // end input()

	public BiolingRecipeBuilder result(FluidStack result)
	{
		this.m_result = result;
		return this;
	} // end result()

	public BiolingRecipeBuilder inputRemainder(ItemStack inputRemainder)
	{
		this.m_inputRemainder = inputRemainder;
		return this;
	} // end inputRemainder()

	public BiolingRecipeBuilder currentPerTick(int currentPerTick)
	{
		this.m_currentPerTick = currentPerTick;
		return this;
	} // end currentPerTick()

	public BiolingRecipeBuilder timeInTicks(int timeInTicks)
	{
		this.m_timeInTicks = timeInTicks;
		return this;
	} // end timeInTicks()



	public BiolingRecipe build()
	{
		BiolingRecipe r = new BiolingRecipe(this.m_identifier, this.m_input, this.m_result);
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
		writer.accept(new BiolingFinishedRecipe(fileName, this.m_result, this.m_input, this.m_inputRemainder, this.m_currentPerTick, this.m_timeInTicks, this.m_group, fileName.withPrefix("recipes/"), this.m_advancement));
	} // end save()
	
	
	
	private void validate(ResourceLocation wouldBeFileName)
	{
		if (this.m_advancement.getCriteria().isEmpty())
		{
			throw new IllegalStateException("No way of obtaining recipe: " + wouldBeFileName);
		}
	} // end validate()

} // end Builder