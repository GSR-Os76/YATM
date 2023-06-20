package com.gsr.gsr_yatm.recipe;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ExtrusionFinishedRecipe implements FinishedRecipe
{
	private ResourceLocation m_identifier;
	private ItemStack m_result;

	private Ingredient m_input;
	private Ingredient m_die;

	private ItemStack m_inputRemainder = ItemStack.EMPTY;
	private ItemStack m_dieRemainder = null;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;
	
	private String m_group;
	private ResourceLocation m_advancementIdentifier;	
	private Advancement.Builder m_advancement;
	
	
	
	public ExtrusionFinishedRecipe(ResourceLocation identifier, ItemStack result, Ingredient input, Ingredient die, ItemStack inputRemainder, ItemStack dieRemainder, int currentPerTick, int timeInTicks, String group, ResourceLocation advancementIdentifier, Advancement.Builder advancement) 
	{
		this.m_identifier = identifier;
		this.m_result = result;
		
		this.m_input = input;		
		this.m_die = die;
		
		this.m_inputRemainder = inputRemainder;
		this.m_dieRemainder = dieRemainder;
		
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
		
		jsonObject.add(RecipeUtilities.RESULT_OBJECT_KEY, RecipeUtilities.itemStackToJson(this.m_result));
		JsonObject inputObject = new JsonObject();
		inputObject.add(RecipeUtilities.INGREDIENT_KEY, this.m_input.toJson());
		if(this.m_inputRemainder != null && !this.m_inputRemainder.isEmpty()) 
		{
			inputObject.add(RecipeUtilities.REMAINDER_STACK_KEY, RecipeUtilities.itemStackToJson(this.m_inputRemainder));
		}
		JsonObject dieObject = new JsonObject();
		dieObject.add(RecipeUtilities.INGREDIENT_KEY, this.m_die.toJson());
		if(this.m_dieRemainder != null && !this.m_dieRemainder.isEmpty()) 
		{
			dieObject.add(RecipeUtilities.REMAINDER_STACK_KEY, RecipeUtilities.itemStackToJson(this.m_dieRemainder));
		}
		jsonObject.add(RecipeUtilities.INPUT_OBJECT_KEY, inputObject);
		jsonObject.add(RecipeUtilities.DIE_OBJECT_KEY, dieObject);
		jsonObject.addProperty(RecipeUtilities.CURRENT_PER_TICK_KEY, this.m_currentPerTick);
		jsonObject.addProperty(RecipeUtilities.TIME_IN_TICKS_KEY, this.m_timeInTicks);
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<ExtrusionRecipe> getType()
	{
		return YATMRecipeSerializers.EXTRUSION_SERIALIZER.get();
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