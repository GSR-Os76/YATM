package com.gsr.gsr_yatm.recipe.ingredient.fluid_stack.nbt;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;

public class NBTFluidStackIngredientDeserializer implements IIngredientDeserializer<NBTFluidStackIngredient>
{
	private static final Codec<NBTFluidStackIngredient> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			FluidStack.CODEC.fieldOf(IngredientUtil.STACK_KEY).forGetter(NBTFluidStackIngredient::stack))
			.apply(instance, NBTFluidStackIngredient::new));
	
	@Override
	public @NotNull Codec<? extends NBTFluidStackIngredient> codec()
	{
		return CODEC;
	} // end codec()

	@Override
	public @NotNull NBTFluidStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new NBTFluidStackIngredient(IngredientUtil.nbtFluidStackFromJson(jsonObject.getAsJsonObject(IngredientUtil.STACK_KEY)));
	} // end deserialize()

	@Override
	public @NotNull NBTFluidStackIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new NBTFluidStackIngredient(buffer.readFluidStack());
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull NBTFluidStackIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeFluidStack(ingredient.stack());
	} // end toNetwork()
} // end class