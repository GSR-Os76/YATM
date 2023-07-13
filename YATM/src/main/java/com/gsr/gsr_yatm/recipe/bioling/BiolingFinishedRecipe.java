package com.gsr.gsr_yatm.recipe.bioling;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class BiolingFinishedRecipe implements FinishedRecipe
{
	private ResourceLocation m_identifier;
	private FluidStack m_result;	
	private Ingredient m_input;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;
	private String m_group;
	
	private ResourceLocation m_advancementIdentifier;	
	private Advancement.Builder m_advancement;
	
	
	
	public BiolingFinishedRecipe(ResourceLocation identifier, FluidStack result, Ingredient input, int currentPerTick, int timeInTicks, String group, ResourceLocation advancementIdentifier, Advancement.Builder advancement) 
	{
		this.m_identifier = identifier;
		this.m_result = result;
		this.m_input = input;
		this.m_currentPerTick = currentPerTick;
		this.m_timeInTicks = timeInTicks;
		this.m_group = group;
		this.m_advancementIdentifier = advancementIdentifier;
		this.m_advancement = advancement;
	} // end consturctor
	
	
	@Override
	public void serializeRecipeData(JsonObject jsonObject)
	{
		if(!this.m_group.isEmpty()) 
		{
			jsonObject.addProperty(RecipeUtilities.GROUP_KEY, this.m_group);
		}
		
		jsonObject.add(RecipeUtilities.RESULT_OBJECT_KEY, RecipeUtilities.fluidStackToJson(this.m_result));
		JsonObject inputObject = new JsonObject();
		inputObject.add(RecipeUtilities.INGREDIENT_KEY, this.m_input.toJson());
		jsonObject.add(RecipeUtilities.INPUT_OBJECT_KEY, inputObject);
		jsonObject.addProperty(RecipeUtilities.CURRENT_PER_TICK_KEY, this.m_currentPerTick);
		jsonObject.addProperty(RecipeUtilities.TIME_IN_TICKS_KEY, this.m_timeInTicks);
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<BiolingRecipe> getType()
	{
		return YATMRecipeSerializers.BIOLING.get();
	} // end getType()

	@Override
	public JsonObject serializeAdvancement()
	{
		return this.m_advancement.serializeToJson();
	} // end serializeAdvancement()

	@Override
	public ResourceLocation getAdvancementId()
	{
		return this.m_advancementIdentifier;
	} // end getAdvancementId()

} // end class