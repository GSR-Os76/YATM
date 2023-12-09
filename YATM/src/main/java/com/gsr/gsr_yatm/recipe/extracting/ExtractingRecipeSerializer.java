package com.gsr.gsr_yatm.recipe.extracting;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipeCodecs;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class ExtractingRecipeSerializer implements RecipeSerializer<ExtractingRecipe>
{
	private static final @NotNull Codec<ExtractingRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(ExtractingRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.INPUT_KEY).forGetter(ExtractingRecipe::input),
			ExtraCodecs.strictOptionalField(CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC, IngredientUtil.INPUT_REMAINDER_STACK_KEY, ItemStack.EMPTY).forGetter(ExtractingRecipe::inputRemainder),
			FluidStack.CODEC.fieldOf(IngredientUtil.RESULT_KEY).forGetter(ExtractingRecipe::result),
			Codec.INT.fieldOf(IngredientUtil.CURRENT_PER_TICK_KEY).forGetter(ExtractingRecipe::getCurrentPerTick),
			Codec.INT.fieldOf(IngredientUtil.TIME_IN_TICKS_KEY).forGetter(ExtractingRecipe::getTimeInTicks)
			).apply(instance, (g, i, ir, r, c, t) -> new ExtractingRecipe(g, i.cast(), ir, r, c, t)));

	@Override
	public @NotNull Codec<ExtractingRecipe> codec()
	{
		return ExtractingRecipeSerializer.CODEC;
	} // end codec()

	@Override
	public @NotNull ExtractingRecipe fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		String group = buffer.readUtf();
		IIngredient<ItemStack> input = IngredientUtil.fromNetwork(buffer);
		ItemStack inputRemainder = buffer.readItem();
		FluidStack result = buffer.readFluidStack();
		int current = buffer.readVarInt();
		int time = buffer.readVarInt();
		
		return new ExtractingRecipe(group, input, inputRemainder, result, current, time);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull ExtractingRecipe recipe)
	{
		buffer.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.input(), buffer);
		buffer.writeItem(recipe.inputRemainder());
		buffer.writeFluidStack(recipe.result());
		buffer.writeVarInt(recipe.getCurrentPerTick());
		buffer.writeVarInt(recipe.getTimeInTicks());
	} // end toNetwork()

} // end class