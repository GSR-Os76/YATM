package com.gsr.gsr_yatm.recipe.distilling;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class DistillingRecipeSerializer implements RecipeSerializer<DistillingRecipe>
{
	private static final @NotNull Codec<DistillingRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(DistillingRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.INPUT_KEY).forGetter(DistillingRecipe::input),
			FluidStack.CODEC.fieldOf(IngredientUtil.REMAINDER_KEY).forGetter(DistillingRecipe::remainder),
			FluidStack.CODEC.fieldOf(IngredientUtil.DISTILLATE_KEY).forGetter(DistillingRecipe::distillate),
			Codec.INT.fieldOf(IngredientUtil.TEMPERATURE_KEY).forGetter(DistillingRecipe::getTemperature),
			Codec.INT.fieldOf(IngredientUtil.TIME_IN_TICKS_KEY).forGetter(DistillingRecipe::getTimeInTicks)
			).apply(instance, (g, i, r, d, temp, time) -> new DistillingRecipe(g, i.cast(), r, d, temp, time)));
	
	@Override
	public Codec<DistillingRecipe> codec()
	{
		return DistillingRecipeSerializer.CODEC;
	} // end codec()

	@Override
	public @Nullable DistillingRecipe fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		String group = buffer.readUtf();
		IIngredient<FluidStack> input = IngredientUtil.fromNetwork(buffer);
		FluidStack remainder = buffer.readFluidStack();
		FluidStack distillate = buffer.readFluidStack();
		int temp = buffer.readVarInt();
		int time = buffer.readVarInt();
		
		return new DistillingRecipe(group, input, remainder, distillate, temp, time);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull DistillingRecipe recipe)
	{
		buffer.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.input(), buffer);
		buffer.writeFluidStack(recipe.remainder());
		buffer.writeFluidStack(recipe.distillate());
		buffer.writeVarInt(recipe.getTemperature());
		buffer.writeVarInt(recipe.getTimeInTicks());
	} // end toNetwork()

} // end class