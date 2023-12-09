package com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipeCodecs;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ToolPartExchangeRecipeSerializer implements RecipeSerializer<ToolPartExchangeRecipe>
{
	private static final @NotNull Codec<ToolPartExchangeRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(ToolPartExchangeRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.TOOL_KEY).forGetter(ToolPartExchangeRecipe::tool),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.PART_KEY).forGetter(ToolPartExchangeRecipe::part),
			CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf(IngredientUtil.RESULT_KEY).forGetter(ToolPartExchangeRecipe::result),
			ExtraCodecs.strictOptionalField(CraftingBookCategory.CODEC, IngredientUtil.CATEGORY_KEY, CraftingBookCategory.MISC).forGetter(ToolPartExchangeRecipe::category)
			).apply(instance, (g, t, p, r, c) -> new ToolPartExchangeRecipe(g, t.cast(), p.cast(), r, c)));

	@Override
	public Codec<ToolPartExchangeRecipe> codec()
	{
		return ToolPartExchangeRecipeSerializer.CODEC;
	} // end codec()

	public ToolPartExchangeRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf)
	{
		String group = friendlyByteBuf.readUtf();
		IIngredient<ItemStack> tool = IngredientUtil.fromNetwork(friendlyByteBuf);
		IIngredient<ItemStack> part = IngredientUtil.fromNetwork(friendlyByteBuf);
		ItemStack result = friendlyByteBuf.readItem();
		CraftingBookCategory category = friendlyByteBuf.readEnum(CraftingBookCategory.class);
		
		return new ToolPartExchangeRecipe(group, tool, part, result, category);
	} // end fromNetwork()

	public void toNetwork(FriendlyByteBuf friendlyByteBuf, ToolPartExchangeRecipe recipe)
	{
		friendlyByteBuf.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.tool(), friendlyByteBuf);
		IngredientUtil.toNetwork(recipe.part(), friendlyByteBuf);
		friendlyByteBuf.writeItem(recipe.result());
		friendlyByteBuf.writeEnum(recipe.category());		
	} // end toNetwork()

} // end class