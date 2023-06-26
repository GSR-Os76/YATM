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
import net.minecraftforge.fluids.FluidStack;

public class CrystallizationFinishedRecipe implements FinishedRecipe
{
	private final ResourceLocation m_identifier;
	private final ItemStack m_result;
	private final Ingredient m_seed;	
	private final FluidStack m_input;
	private final boolean m_consumeSeed;
	private final int m_currentPerTick;
	private final int m_timeInTicks;
	private final String m_group;
	
	private final ResourceLocation m_advancementIdentifier;	
	private final Advancement.Builder m_advancement;
	
	
	
	public CrystallizationFinishedRecipe(ResourceLocation identifier, ItemStack result, FluidStack input, Ingredient seed, boolean consumeSeed, int currentPerTick, int timeInTicks, String group, ResourceLocation advancementIdentifier, Advancement.Builder advancement) 
	{
		this.m_identifier = identifier;
		this.m_result = result;
		this.m_input = input;
		this.m_seed = seed;
		this.m_consumeSeed = consumeSeed;
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
		JsonObject seedObject = new JsonObject();
		seedObject.add(RecipeUtilities.INGREDIENT_KEY, this.m_seed.toJson());
		seedObject.addProperty(RecipeUtilities.CONSUME_SEED_KEY, this.m_consumeSeed);		
		jsonObject.add(RecipeUtilities.SEED_KEY, seedObject);		
		jsonObject.add(RecipeUtilities.INPUT_OBJECT_KEY, RecipeUtilities.fluidStackToJson(this.m_input));
		jsonObject.addProperty(RecipeUtilities.CURRENT_PER_TICK_KEY, this.m_currentPerTick);
		jsonObject.addProperty(RecipeUtilities.TIME_IN_TICKS_KEY, this.m_timeInTicks);
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<CrystallizationRecipe> getType()
	{
		return YATMRecipeSerializers.CRYSTALLIZATION_SERIALIZER.get();
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