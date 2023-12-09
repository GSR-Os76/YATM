package com.gsr.gsr_yatm.recipe.ingredient.item_tag;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemTagIngredientDeserializer implements IIngredientDeserializer<ItemTagIngredient>
{
	private static final Codec<ItemTagIngredient> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.STRING.fieldOf(IngredientUtil.TAG_KEY).forGetter((i) -> i.getTag().toString()),
			Codec.intRange(0, Integer.MAX_VALUE).fieldOf(IngredientUtil.AMOUNT_KEY).orElse(1).forGetter(ItemTagIngredient::getCount)
			).apply(instance, (t, a) -> new ItemTagIngredient(IngredientUtil.getTagKey(t, ForgeRegistries.ITEMS), a)));

	@Override
	public @NotNull Codec<? extends ItemTagIngredient> codec()
	{
		return CODEC;
	} // end codec()
	
	@Override
	public @NotNull ItemTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		// return this.codec().decode(JsonOps.INSTANCE, jsonObject).?result();
		TagKey<Item> tag = IngredientUtil.getTagKey(jsonObject.get(IngredientUtil.TAG_KEY).getAsString(), ForgeRegistries.ITEMS);
		int count = jsonObject.has(IngredientUtil.AMOUNT_KEY) ? jsonObject.get(IngredientUtil.AMOUNT_KEY).getAsInt() : 1;
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