package com.gsr.gsr_yatm.recipe.injecting;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class InjectingFinishedRecipe implements FinishedRecipe
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<FluidStack> m_input;
	private final @NotNull IIngredient<ItemStack> m_substrate;
	private final @NotNegative int m_currentPerTick;
	private final @NotNegative int m_timeInTicks;
	private final @NotNull String m_group;
	
	private final @NotNull ResourceLocation m_advancementIdentifier;	
	private final @NotNull Advancement.Builder m_advancement;
	
	
	
	public InjectingFinishedRecipe(@NotNull ResourceLocation identifier, @NotNull ItemStack result, @NotNull IIngredient<FluidStack> input, @NotNull IIngredient<ItemStack> substrate, @NotNegative int currentPerTick, @NotNegative int timeInTicks, @NotNull String group, @NotNull ResourceLocation advancementIdentifier, @NotNull Advancement.Builder advancement) 
	{
		this.m_identifier = Objects.requireNonNull(identifier);
		this.m_result = Objects.requireNonNull(result);
		this.m_input = Objects.requireNonNull(input);
		this.m_substrate = Objects.requireNonNull(substrate);
		this.m_currentPerTick = Contract.NotNegative(currentPerTick);
		this.m_timeInTicks = Contract.NotNegative(timeInTicks);
		this.m_group = Objects.requireNonNull(group);
		this.m_advancementIdentifier = Objects.requireNonNull(advancementIdentifier);
		this.m_advancement = Objects.requireNonNull(advancement);
	} // end constructor
	
	
	
	@Override
	public void serializeRecipeData(JsonObject jsonObject)
	{
		if(!this.m_group.isEmpty()) 
		{
			jsonObject.addProperty(IngredientUtil.GROUP_KEY, this.m_group);
		}
		
		jsonObject.add(IngredientUtil.RESULT_KEY, IngredientUtil.nbtItemStackToJson(this.m_result));
		JsonObject inputObject = new JsonObject();
		inputObject.add(IngredientUtil.INGREDIENT_KEY, IngredientUtil.writeIngredient(this.m_input));
		jsonObject.add(IngredientUtil.INPUT_KEY, inputObject);
		JsonObject substrateObject = new JsonObject();
		substrateObject.add(IngredientUtil.INGREDIENT_KEY, IngredientUtil.writeIngredient(this.m_substrate));
		jsonObject.add(IngredientUtil.SUBSTRATE_KEY, substrateObject);	
		jsonObject.addProperty(IngredientUtil.CURRENT_PER_TICK_KEY, this.m_currentPerTick);
		jsonObject.addProperty(IngredientUtil.TIME_IN_TICKS_KEY, this.m_timeInTicks);
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<InjectingRecipe> getType()
	{
		return YATMRecipeSerializers.INJECTING.get();
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