package com.gsr.gsr_yatm.recipe.spinning;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SpinningFinishedRecipe implements FinishedRecipe
{
	private ResourceLocation m_identifier;
	private ItemStack m_result;
	
	private Ingredient m_input;
	
	private String m_group;
	private ResourceLocation m_advancementIdentifier;	
	private Advancement.Builder m_advancement;
	
	
	
	public SpinningFinishedRecipe(ResourceLocation identifier, ItemStack result, Ingredient input, String group, ResourceLocation advancementIdentifier, Advancement.Builder advancement) 
	{
		this.m_identifier = identifier;
		this.m_result = result;
		
		this.m_input = input;		
		
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
		jsonObject.add(RecipeUtilities.INPUT_OBJECT_KEY, inputObject);
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<SpinningRecipe> getType()
	{
		return YATMRecipeSerializers.SPINNING.get();
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