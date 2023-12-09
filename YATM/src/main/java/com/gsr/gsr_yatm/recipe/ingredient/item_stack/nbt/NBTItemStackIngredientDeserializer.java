package com.gsr.gsr_yatm.recipe.ingredient.item_stack.nbt;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.CraftingRecipeCodecs;
import net.minecraftforge.common.crafting.CraftingHelper;

public class NBTItemStackIngredientDeserializer implements IIngredientDeserializer<NBTItemStackIngredient>
{
	private static final Codec<NBTItemStackIngredient> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf(IngredientUtil.STACK_KEY).forGetter(NBTItemStackIngredient::stack))
			.apply(instance, NBTItemStackIngredient::new));
	
	@Override
	public @NotNull Codec<? extends NBTItemStackIngredient> codec()
	{
		return CODEC;
	} // end codec()
	
	@Override
	public @NotNull NBTItemStackIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		return new NBTItemStackIngredient(CraftingHelper.getItemStack(jsonObject, true, false));
	} // end deserialize()

	@Override
	public @NotNull NBTItemStackIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new NBTItemStackIngredient(buffer.readItem());
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull NBTItemStackIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeItem(ingredient.getValues().get(0));
	} // end toNetwork()
	
} // end class