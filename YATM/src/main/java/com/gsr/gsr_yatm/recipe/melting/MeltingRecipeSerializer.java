package com.gsr.gsr_yatm.recipe.melting;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class MeltingRecipeSerializer implements RecipeSerializer<MeltingRecipe>
{
	private static final @NotNull Codec<MeltingRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(MeltingRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.INPUT_KEY).forGetter(MeltingRecipe::input),
			FluidStack.CODEC.fieldOf(IngredientUtil.RESULT_KEY).forGetter(MeltingRecipe::result),
			Codec.INT.fieldOf(IngredientUtil.TEMPERATURE_KEY).forGetter(MeltingRecipe::getTemperature),
			Codec.INT.fieldOf(IngredientUtil.TIME_IN_TICKS_KEY).forGetter(MeltingRecipe::getTimeInTicks)
			).apply(instance, (g, i, r, temp, time) -> new MeltingRecipe(g, i.cast(), r, temp, time)));
	
	@Override
	public Codec<MeltingRecipe> codec()
	{
		return MeltingRecipeSerializer.CODEC;
	} // end codec()

	@Override
	public @NotNull MeltingRecipe fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		String group = buffer.readUtf();
		IIngredient<ItemStack> input = IngredientUtil.fromNetwork(buffer);
		FluidStack result = buffer.readFluidStack();
		int temp = buffer.readVarInt();
		int time = buffer.readVarInt();
		
		return new MeltingRecipe(group, input, result, temp, time);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull MeltingRecipe recipe)
	{
		buffer.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.input(), buffer);
		buffer.writeFluidStack(recipe.result());
		buffer.writeVarInt(recipe.getTemperature());
		buffer.writeVarInt(recipe.getTimeInTicks());
	} // end toNetwork()

} // end class