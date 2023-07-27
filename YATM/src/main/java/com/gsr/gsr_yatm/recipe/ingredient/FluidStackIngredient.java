package com.gsr.gsr_yatm.recipe.ingredient;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.registry.custom.YATMIngredientDeserializers;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

import net.minecraftforge.fluids.FluidStack;

public class FluidStackIngredient implements IIngredient<FluidStack>
{
	private final @NotNull FluidStack m_ingredient;
	
	
	
	public FluidStackIngredient(@NotNull FluidStack ingredient) 
	{
		Objects.requireNonNull(ingredient);
		this.m_ingredient = ingredient;
	} // end constructor
	
	
	
	@Override
	public boolean test(@Nullable FluidStack input)
	{
		return input != null && this.m_ingredient.getFluid() == input.getFluid() && this.m_ingredient.getAmount() <= input.getAmount();
	} // end test()

	@Override
	public @NotNull JsonObject serialize()
	{
		return IngredientUtilities.fluidStackToJson(this.m_ingredient);
	} // end serialize()

	@Override
	public @NotNull List<FluidStack> getValues()
	{
		return List.of(this.m_ingredient.copy());
	} // end getValues()
	
	
	
	@Override
	public @NotNull FluidStackIngredientDeserializer deserializer()
	{
		return YATMIngredientDeserializers.FLUID_STACK_INGREDIENT.get();
	} // end deserializer()

} // end class