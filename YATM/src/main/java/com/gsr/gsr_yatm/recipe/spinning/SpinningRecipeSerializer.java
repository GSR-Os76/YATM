package com.gsr.gsr_yatm.recipe.spinning;

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

public class SpinningRecipeSerializer implements RecipeSerializer<SpinningRecipe>
{	
	private static final @NotNull Codec<SpinningRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(SpinningRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.INPUT_KEY).forGetter(SpinningRecipe::input),
			CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf(IngredientUtil.RESULT_KEY).forGetter(SpinningRecipe::result)
			).apply(instance, (g, i, r) -> new SpinningRecipe(g, i.cast(), r)));
	
	@Override
	public Codec<SpinningRecipe> codec()
	{
		return SpinningRecipeSerializer.CODEC;
	} // end codec()

	@Override
	public @NotNull SpinningRecipe fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		String group = buffer.readUtf();
		IIngredient<ItemStack> input = IngredientUtil.fromNetwork(buffer);
		ItemStack result = buffer.readItem();
		
		return new SpinningRecipe(group, input, result);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull SpinningRecipe recipe)
	{
		buffer.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.input(), buffer);
		buffer.writeItem(recipe.result());		
	} // end toNetwork()
	
} // end class