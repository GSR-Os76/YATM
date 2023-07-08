package com.gsr.gsr_yatm.recipe.boiling;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class BoilingFinishedRecipe implements FinishedRecipe
{
	private ResourceLocation m_identifier;
	private FluidStack m_result;
	private FluidStack m_input;
	private int m_temperature = 373;
	private int m_timeInTicks = 20;
	
	private String m_group;
	private ResourceLocation m_advancementIdentifier;	
	private Advancement.Builder m_advancement;
	
	
	
	public BoilingFinishedRecipe(ResourceLocation identifier, FluidStack result, FluidStack input, int temperature, int timeInTicks, String group, ResourceLocation advancementIdentifier, Advancement.Builder advancement) 
	{
		this.m_identifier = identifier;
		this.m_result = result;
		this.m_input = input;
		this.m_temperature = temperature;
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
		jsonObject.add(RecipeUtilities.INPUT_OBJECT_KEY, RecipeUtilities.fluidStackToJson(this.m_input));
		
		jsonObject.addProperty(RecipeUtilities.TEMPERATURE_KEY, this.m_temperature);
		jsonObject.addProperty(RecipeUtilities.TIME_IN_TICKS_KEY, this.m_timeInTicks);
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<BoilingRecipe> getType()
	{
		return YATMRecipeSerializers.BOILING.get();
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