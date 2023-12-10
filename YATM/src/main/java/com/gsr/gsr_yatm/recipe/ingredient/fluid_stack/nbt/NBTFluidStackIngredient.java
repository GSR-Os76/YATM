package com.gsr.gsr_yatm.recipe.ingredient.fluid_stack.nbt;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.custom.YATMIngredientDeserializers;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraftforge.fluids.FluidStack;

public class NBTFluidStackIngredient implements IIngredient<FluidStack>
{
	private final @NotNull FluidStack m_ingredient;
	
	
	
	public NBTFluidStackIngredient(@NotNull FluidStack ingredient) 
	{
		Objects.requireNonNull(ingredient);
		this.m_ingredient = ingredient;
	} // end constructor

	public @NotNull FluidStack stack() 
	{
		return this.m_ingredient.copy();
	} // end stack()
	
	
	
	@Override
	public boolean test(@Nullable FluidStack input)
	{
		return input != null 
				&& this.m_ingredient.getFluid() == input.getFluid() 
				&& this.m_ingredient.getAmount() <= input.getAmount()
				&& ((!this.m_ingredient.hasTag() && !input.hasTag()) || this.m_ingredient.getTag().equals(input.getTag()));
	} // end test()

	@Override
	public @NotNull JsonObject serialize()
	{
		JsonObject obj = new JsonObject();
		obj.add(IngredientUtil.STACK_KEY, IngredientUtil.nbtFluidStackToJson(this.m_ingredient));
		return obj;
	} // end serialize()

	@Override
	public @NotNull List<FluidStack> getValues()
	{
		return List.of(this.m_ingredient.copy());
	} // end getValues()
	
	
	
	@Override
	public @NotNull NBTFluidStackIngredientDeserializer deserializer()
	{
		return YATMIngredientDeserializers.NBT_FLUID_STACK_INGREDIENT.get();
	} // end deserializer()

} // end class