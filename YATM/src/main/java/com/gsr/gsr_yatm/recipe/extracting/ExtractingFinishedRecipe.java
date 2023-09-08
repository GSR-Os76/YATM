package com.gsr.gsr_yatm.recipe.extracting;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class ExtractingFinishedRecipe implements FinishedRecipe
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull FluidStack m_result;	
	private final @NotNull IIngredient<ItemStack> m_input;
	private final @NotNull ItemStack m_inputRemainder;
	private final int m_currentPerTick;
	private final int m_timeInTicks;
	private final @NotNull String m_group;
	
	private final @NotNull ResourceLocation m_advancementIdentifier;	
	private final @NotNull Advancement.Builder m_advancement;
	
	
	
	public ExtractingFinishedRecipe(@NotNull ResourceLocation identifier, @NotNull FluidStack result, @NotNull IIngredient<ItemStack> input, @NotNull ItemStack inputRemainder, int currentPerTick, int timeInTicks, @NotNull String group, @NotNull ResourceLocation advancementIdentifier, @NotNull Advancement.Builder advancement) 
	{
		Objects.requireNonNull(identifier);
		Objects.requireNonNull(result);
		Objects.requireNonNull(input);
		Objects.requireNonNull(inputRemainder);
		Objects.requireNonNull(group);
		Objects.requireNonNull(advancementIdentifier);
		Objects.requireNonNull(advancement);
		
		this.m_identifier = identifier;
		this.m_result = result;
		this.m_input = input;
		this.m_inputRemainder = inputRemainder;
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
			jsonObject.addProperty(IngredientUtil.GROUP_KEY, this.m_group);
		}
		
		jsonObject.add(IngredientUtil.RESULT_KEY, IngredientUtil.nbtFluidStackToJson(this.m_result));
		JsonObject inputObject = new JsonObject();
		inputObject.add(IngredientUtil.INGREDIENT_KEY, IngredientUtil.writeIngredient(this.m_input));
		if(this.m_inputRemainder != null && !this.m_inputRemainder.isEmpty()) 
		{
			inputObject.add(IngredientUtil.REMAINDER_STACK_KEY, IngredientUtil.nbtItemStackToJson(this.m_inputRemainder));
		}
		jsonObject.add(IngredientUtil.INPUT_KEY, inputObject);
		jsonObject.addProperty(IngredientUtil.CURRENT_PER_TICK_KEY, this.m_currentPerTick);
		jsonObject.addProperty(IngredientUtil.TIME_IN_TICKS_KEY, this.m_timeInTicks);
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<ExtractingRecipe> getType()
	{
		return YATMRecipeSerializers.EXTRACTING.get();
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