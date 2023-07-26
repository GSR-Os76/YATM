package com.gsr.gsr_yatm.recipe.extruding;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ExtrudingFinishedRecipe implements FinishedRecipe
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull ItemStack m_result;

	private final @NotNull IIngredient<ItemStack> m_input;
	private final @NotNull IIngredient<ItemStack> m_die;

	private final @NotNull ItemStack m_inputRemainder;
	private final @Nullable ItemStack m_dieRemainder;
	private final int m_currentPerTick;
	private final int m_timeInTicks;
	
	private final @NotNull String m_group;
	private final @NotNull ResourceLocation m_advancementIdentifier;	
	private final @NotNull Advancement.Builder m_advancement;
	
	
	
	public ExtrudingFinishedRecipe(@NotNull ResourceLocation identifier, @NotNull ItemStack result, @NotNull IIngredient<ItemStack> input, @NotNull IIngredient<ItemStack> die, @NotNull ItemStack inputRemainder, @Nullable ItemStack dieRemainder, int currentPerTick, int timeInTicks, @NotNull String group, @NotNull ResourceLocation advancementIdentifier, @NotNull Advancement.Builder advancement) 
	{
		Objects.requireNonNull(identifier);
		Objects.requireNonNull(result);
		Objects.requireNonNull(input);
		Objects.requireNonNull(die);
		Objects.requireNonNull(inputRemainder);
		Objects.requireNonNull(group);
		Objects.requireNonNull(advancementIdentifier);
		Objects.requireNonNull(advancement);



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
			jsonObject.addProperty(IngredientUtilities.GROUP_KEY, this.m_group);
		}
		
		jsonObject.add(IngredientUtilities.RESULT_OBJECT_KEY, IngredientUtilities.nbtItemStackToJson(this.m_result));
		JsonObject inputObject = new JsonObject();
		inputObject.add(IngredientUtilities.INGREDIENT_KEY, IngredientUtilities.writeIngredient(this.m_input));
		if(this.m_inputRemainder != null && !this.m_inputRemainder.isEmpty()) 
		{
			inputObject.add(IngredientUtilities.REMAINDER_STACK_KEY, IngredientUtilities.nbtItemStackToJson(this.m_inputRemainder));
		}
		JsonObject dieObject = new JsonObject();
		dieObject.add(IngredientUtilities.INGREDIENT_KEY, IngredientUtilities.writeIngredient(this.m_die));
		if(this.m_dieRemainder != null && !this.m_dieRemainder.isEmpty()) 
		{
			dieObject.add(IngredientUtilities.REMAINDER_STACK_KEY, IngredientUtilities.nbtItemStackToJson(this.m_dieRemainder));
		}
		jsonObject.add(IngredientUtilities.INPUT_OBJECT_KEY, inputObject);
		jsonObject.add(IngredientUtilities.DIE_OBJECT_KEY, dieObject);
		jsonObject.addProperty(IngredientUtilities.CURRENT_PER_TICK_KEY, this.m_currentPerTick);
		jsonObject.addProperty(IngredientUtilities.TIME_IN_TICKS_KEY, this.m_timeInTicks);
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<ExtrudingRecipe> getType()
	{
		return YATMRecipeSerializers.EXTRUSION.get();
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