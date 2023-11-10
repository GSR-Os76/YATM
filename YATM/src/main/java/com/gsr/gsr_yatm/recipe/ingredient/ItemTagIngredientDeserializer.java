package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemTagIngredientDeserializer implements IIngredientDeserializer<ItemTagIngredient>
{

	@Override
	public @NotNull ItemTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Item> tag = IngredientUtil.getItemTagKey(jsonObject.get(IngredientUtil.TAG_KEY).getAsString());
		int count = jsonObject.get(IngredientUtil.COUNT_KEY).getAsInt();
		return new ItemTagIngredient(tag, count);
	} // end deserialize()

	@Override
	public @NotNull ItemTagIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new ItemTagIngredient(IngredientUtil.getTagKey(buffer.readUtf(), ForgeRegistries.ITEMS), buffer.readVarInt());
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull ItemTagIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeUtf(ingredient.getTag().toString());
		buffer.writeInt(ingredient.getCount());
	} // end toNetwork()
	
} // end class