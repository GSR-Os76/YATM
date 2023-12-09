package com.gsr.gsr_yatm.recipe.cystallizing;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.FinishedRecipeBase;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class CrystallizingFinishedRecipe extends FinishedRecipeBase
{
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<ItemStack> m_seed;	
	private final @NotNull IIngredient<FluidStack> m_input;
	private final boolean m_consumeSeed;
	private final @NotNegative int m_currentPerTick;
	private final @NotNegative int m_timeInTicks;
	
	
	
	public CrystallizingFinishedRecipe(@NotNull ResourceLocation identifier, @NotNull AdvancementHolder advancement, @NotNull String group, @NotNull ItemStack result, @NotNull IIngredient<FluidStack> input, @NotNull IIngredient<ItemStack> seed, boolean consumeSeed, @NotNegative int currentPerTick, @NotNegative int timeInTicks) 
	{
		super(Objects.requireNonNull(identifier), Objects.requireNonNull(advancement), Objects.requireNonNull(group));	
		
		this.m_result = Objects.requireNonNull(result);
		this.m_input = Objects.requireNonNull(input);
		this.m_seed = Objects.requireNonNull(seed);
		this.m_consumeSeed = consumeSeed;
		this.m_currentPerTick = Contract.notNegative(currentPerTick);
		this.m_timeInTicks = Contract.notNegative(timeInTicks);
	} // end constructor
	
	
	@Override
	public void serializeRecipeData(@NotNull JsonObject jsonObject)
	{
		super.serializeRecipeData(jsonObject);
		
		jsonObject.add(IngredientUtil.RESULT_KEY, IngredientUtil.nbtItemStackToJson(this.m_result));
		jsonObject.add(IngredientUtil.INPUT_KEY, IngredientUtil.writeIngredient(this.m_input));
		jsonObject.add(IngredientUtil.SEED_KEY, IngredientUtil.writeIngredient(this.m_seed));
		jsonObject.addProperty(IngredientUtil.CONSUME_SEED_KEY, this.m_consumeSeed);		
		jsonObject.addProperty(IngredientUtil.CURRENT_PER_TICK_KEY, this.m_currentPerTick);
		jsonObject.addProperty(IngredientUtil.TIME_IN_TICKS_KEY, this.m_timeInTicks);
	} // end serializeRecipeData()

	@Override
	public @NotNull RecipeSerializer<CrystallizingRecipe> type()
	{
		return YATMRecipeSerializers.CRYSTALLIZING.get();
	} // end type()

} // end class