package com.gsr.gsr_yatm.recipe.bioreacting;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class BioreactingRecipeSerializer implements RecipeSerializer<BioreactingRecipe>
{
	private static final @NotNull Codec<BioreactingRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(BioreactingRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.INPUT_KEY).forGetter(BioreactingRecipe::input),
			FluidStack.CODEC.fieldOf(IngredientUtil.RESULT_KEY).forGetter(BioreactingRecipe::result),
			Codec.INT.fieldOf(IngredientUtil.CURRENT_PER_TICK_KEY).forGetter(BioreactingRecipe::getCurrentPerTick),
			Codec.INT.fieldOf(IngredientUtil.TIME_IN_TICKS_KEY).forGetter(BioreactingRecipe::getTimeInTicks)
			).apply(instance, (g, i, r, c, t) -> new BioreactingRecipe(g, i.cast(), r, c, t)));
	
	@Override
	public Codec<BioreactingRecipe> codec()
	{
		return BioreactingRecipeSerializer.CODEC;
	} // end codec()

	@Override
	public @Nullable BioreactingRecipe fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		String group = buffer.readUtf();
		IIngredient<ItemStack> input = IngredientUtil.fromNetwork(buffer);
		FluidStack result = buffer.readFluidStack();
		int current = buffer.readVarInt();
		int time = buffer.readVarInt();

		return new BioreactingRecipe(group, input, result, current, time);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull BioreactingRecipe recipe)
	{
		buffer.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.input(), buffer);
		buffer.writeFluidStack(recipe.result());
		buffer.writeVarInt(recipe.getCurrentPerTick());
		buffer.writeVarInt(recipe.getTimeInTicks());
	} // end toNetwork()

} // end class