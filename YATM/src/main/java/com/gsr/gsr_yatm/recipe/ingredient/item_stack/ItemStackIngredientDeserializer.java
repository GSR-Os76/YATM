package com.gsr.gsr_yatm.recipe.ingredient.item_stack;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.CraftingRecipeCodecs;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ItemStackIngredientDeserializer implements IIngredientDeserializer<ItemStackIngredient>
{
	private static final Codec<ItemStackIngredient> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf(IngredientUtil.STACK_KEY).forGetter(ItemStackIngredient::stack))
			.apply(instance, ItemStackIngredient::new));
	
	@Override
	public @NotNull Codec<? extends ItemStackIngredient> codec()
	{
		return CODEC;
	} // end codec()
	
	@Override
	public @NotNull ItemStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new ItemStackIngredient(CraftingHelper.getItemStack(jsonObject, false, false));
	} // end deserialize()

	@Override
	public @NotNull ItemStackIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new ItemStackIngredient(buffer.readItem());
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull ItemStackIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeItem(ingredient.getValues().get(0));
	} // end toNetwork()
	
} // end class