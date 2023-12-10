package com.gsr.gsr_yatm.recipe.ingredient.fluid_stack;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackIngredientDeserializer implements IIngredientDeserializer<FluidStackIngredient>
{
	private static final Codec<FluidStackIngredient> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			FluidStack.CODEC.fieldOf(IngredientUtil.STACK_KEY).forGetter(FluidStackIngredient::stack))
			.apply(instance, FluidStackIngredient::new));
	
	@Override
	public @NotNull Codec<? extends FluidStackIngredient> codec()
	{
		return CODEC;
	} // end codec()
	
	@Override
	public @NotNull FluidStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new FluidStackIngredient(IngredientUtil.fluidStackFromJson(jsonObject.getAsJsonObject(IngredientUtil.STACK_KEY)));
	} // end deserialize()

	@Override
	public @NotNull FluidStackIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new FluidStackIngredient(buffer.readFluidStack());
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FluidStackIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeFluidStack(ingredient.stack());
	} // end toNetwork()

} // end class