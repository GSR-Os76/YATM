package com.gsr.gsr_yatm.recipe.grinding;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IYATMIngredient;
import com.gsr.gsr_yatm.recipe.ingredient.IngredientUtilities;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class GrindingFinishedRecipe implements FinishedRecipe
{
	private final ResourceLocation m_identifier;
	private final ItemStack m_result;
	private final IYATMIngredient<ItemStack> m_input;
	int m_currentPerTick = 8;
	int m_timeInTicks = 20;
	
	String m_group = "";
	private ResourceLocation m_advancementIdentifier;	
	private Advancement.Builder m_advancement;
	
	
	
	public GrindingFinishedRecipe(ResourceLocation identifier, ItemStack result, IYATMIngredient<ItemStack> input, int currentPerTick, int timeInTicks, String group, ResourceLocation advancementIdentifier, Advancement.Builder advancement) 
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
		
		jsonObject.add(RecipeUtilities.RESULT_OBJECT_KEY, RecipeUtilities.itemStackToJson(this.m_result));
		JsonObject inputObject = new JsonObject();
		inputObject.add(RecipeUtilities.INGREDIENT_KEY, IngredientUtilities.writeIngredient(this.m_input));
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
	public RecipeSerializer<GrindingRecipe> getType()
	{
		return YATMRecipeSerializers.GRINDING.get();
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