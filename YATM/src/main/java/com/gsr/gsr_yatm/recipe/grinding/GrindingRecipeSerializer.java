package com.gsr.gsr_yatm.recipe.grinding;

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

public class GrindingRecipeSerializer implements RecipeSerializer<GrindingRecipe>
{	
	private static final @NotNull Codec<GrindingRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(GrindingRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.INPUT_KEY).forGetter(GrindingRecipe::input),
			CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf(IngredientUtil.RESULT_KEY).forGetter(GrindingRecipe::result),
			Codec.INT.fieldOf(IngredientUtil.CURRENT_PER_TICK_KEY).forGetter(GrindingRecipe::getCurrentPerTick),
			Codec.INT.fieldOf(IngredientUtil.TIME_IN_TICKS_KEY).forGetter(GrindingRecipe::getTimeInTicks)
			).apply(instance, (g, i, r, c, t) -> new GrindingRecipe(g, i.cast(), r, c, t)));
	
	@Override
	public Codec<GrindingRecipe> codec()
	{
		return GrindingRecipeSerializer.CODEC;
	} // end codec()
	
	@Override
	public @Nullable GrindingRecipe fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		String group = buffer.readUtf();
		IIngredient<ItemStack> input = IngredientUtil.fromNetwork(buffer);
		ItemStack result = buffer.readItem();
		int current = buffer.readVarInt();
		int time = buffer.readVarInt();
		
		return new GrindingRecipe(group, input, result, current, time);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull GrindingRecipe recipe)
	{
		buffer.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.input(), buffer);
		buffer.writeItem(recipe.result());
		buffer.writeVarInt(recipe.getCurrentPerTick());
		buffer.writeVarInt(recipe.getTimeInTicks());
	} // end toNetwork()
	
} // end class