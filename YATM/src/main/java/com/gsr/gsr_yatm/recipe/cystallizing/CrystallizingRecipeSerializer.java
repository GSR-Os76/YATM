package com.gsr.gsr_yatm.recipe.cystallizing;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

public class CrystallizingRecipeSerializer implements RecipeSerializer<CrystallizingRecipe>
{
	private static final @NotNull Codec<CrystallizingRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(CrystallizingRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.INPUT_KEY).forGetter(CrystallizingRecipe::input),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.SEED_KEY).forGetter(CrystallizingRecipe::seed),
			Codec.BOOL.fieldOf(IngredientUtil.CONSUME_SEED_KEY).forGetter(CrystallizingRecipe::consumeSeed),
			CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf(IngredientUtil.RESULT_KEY).forGetter(CrystallizingRecipe::result),
			Codec.INT.fieldOf(IngredientUtil.CURRENT_PER_TICK_KEY).forGetter(CrystallizingRecipe::getCurrentPerTick),
			Codec.INT.fieldOf(IngredientUtil.TIME_IN_TICKS_KEY).forGetter(CrystallizingRecipe::getTimeInTicks)
			).apply(instance, (g, i, s, cs, r, c, t) -> new CrystallizingRecipe(g, i.cast(), s.cast(), cs, r, c, t)));

	@Override
	public @NotNull Codec<CrystallizingRecipe> codec()
	{
		return CrystallizingRecipeSerializer.CODEC;
	} // end codec()

	@Override
	public @Nullable CrystallizingRecipe fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		String group = buffer.readUtf();
		IIngredient<FluidStack> input = IngredientUtil.fromNetwork(buffer);
		IIngredient<ItemStack> seed = IngredientUtil.fromNetwork(buffer);
		boolean consumeSeed = buffer.readBoolean();
		ItemStack result = buffer.readItem();
		int current = buffer.readVarInt();
		int time = buffer.readVarInt();
		
		return new CrystallizingRecipe(group, input, seed, consumeSeed, result, current, time);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull CrystallizingRecipe recipe)
	{
		buffer.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.input(), buffer);
		IngredientUtil.toNetwork(recipe.seed(), buffer);
		buffer.writeBoolean(recipe.consumeSeed());
		buffer.writeItem(recipe.result());
		buffer.writeVarInt(recipe.getCurrentPerTick());
		buffer.writeVarInt(recipe.getTimeInTicks());
	} // end toNetwork()

} // end class