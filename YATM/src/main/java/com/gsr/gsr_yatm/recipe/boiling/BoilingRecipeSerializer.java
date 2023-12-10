package com.gsr.gsr_yatm.recipe.boiling;

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

public class BoilingRecipeSerializer implements RecipeSerializer<BoilingRecipe>
{
	private static final @NotNull Codec<BoilingRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(BoilingRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.INPUT_KEY).forGetter(BoilingRecipe::input),
			FluidStack.CODEC.fieldOf(IngredientUtil.RESULT_KEY).forGetter(BoilingRecipe::result),
			Codec.INT.fieldOf(IngredientUtil.TEMPERATURE_KEY).forGetter(BoilingRecipe::getTemperature),
			Codec.INT.fieldOf(IngredientUtil.TIME_IN_TICKS_KEY).forGetter(BoilingRecipe::getTimeInTicks)
			).apply(instance, (g, i, r, temp, time) -> new BoilingRecipe(g, i.cast(), r, temp, time)));
	
	@Override
	public Codec<BoilingRecipe> codec()
	{
		return BoilingRecipeSerializer.CODEC;
	} // end codec()

	@Override
	public @Nullable BoilingRecipe fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		String group = buffer.readUtf();
		IIngredient<FluidStack> input = IngredientUtil.fromNetwork(buffer);
		FluidStack result = buffer.readFluidStack();
		int temp = buffer.readVarInt();
		int time = buffer.readVarInt();
		
		return new BoilingRecipe(group, input, result, temp, time);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull BoilingRecipe recipe)
	{
		buffer.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.input(), buffer);
		buffer.writeFluidStack(recipe.result());
		buffer.writeVarInt(recipe.getTemperature());
		buffer.writeVarInt(recipe.getTimeInTicks());
	} // end toNetwork()

} // end class