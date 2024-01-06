package com.gsr.gsr_yatm.recipe.distilling;

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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class DistillingFinishedRecipe extends FinishedRecipeBase
{
	private final @NotNull FluidStack m_distillate;
	private final @NotNull FluidStack m_remainder;
	private final @NotNull IIngredient<FluidStack> m_input;
	private final @NotNegative int m_temperature;
	private final @NotNegative int m_timeInTicks;
	
	
	
	public DistillingFinishedRecipe(@NotNull ResourceLocation identifier, @NotNull AdvancementHolder advancement, @NotNull String group, @NotNull FluidStack distillate, @NotNull FluidStack remainder, @NotNull IIngredient<FluidStack> input, @NotNegative int temperature, @NotNegative int timeInTicks) 
	{
		super(Objects.requireNonNull(identifier), Objects.requireNonNull(advancement), Objects.requireNonNull(group));	
		
		this.m_distillate = distillate.copy();
		this.m_remainder = remainder.copy();
		this.m_input = Objects.requireNonNull(input);
		this.m_temperature = Contract.notNegative(temperature);
		this.m_timeInTicks = Contract.notNegative(timeInTicks);
	} // end consturctor
	
	
	@Override
	public void serializeRecipeData(@NotNull JsonObject jsonObject)
	{
		super.serializeRecipeData(jsonObject);
		
		jsonObject.add(IngredientUtil.DISTILLATE_KEY, IngredientUtil.nbtFluidStackToJson(this.m_distillate));
		jsonObject.add(IngredientUtil.REMAINDER_KEY, IngredientUtil.nbtFluidStackToJson(this.m_remainder));
		jsonObject.add(IngredientUtil.INPUT_KEY, IngredientUtil.writeIngredient(this.m_input));
		jsonObject.addProperty(IngredientUtil.TEMPERATURE_KEY, this.m_temperature);
		jsonObject.addProperty(IngredientUtil.TIME_IN_TICKS_KEY, this.m_timeInTicks);
	} // end serializeRecipeData()

	@Override
	public @NotNull RecipeSerializer<DistillingRecipe> type()
	{
		return YATMRecipeSerializers.DISTILLING.get();
	} // end type()

} // end class