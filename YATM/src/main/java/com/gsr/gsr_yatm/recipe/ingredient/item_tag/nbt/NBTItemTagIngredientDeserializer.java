package com.gsr.gsr_yatm.recipe.ingredient.item_tag.nbt;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class NBTItemTagIngredientDeserializer implements IIngredientDeserializer<NBTItemTagIngredient>
{
	private static final Codec<NBTItemTagIngredient> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.STRING.fieldOf(IngredientUtil.TAG_KEY).forGetter((i) -> i.getTag().toString()),
			Codec.intRange(0, Integer.MAX_VALUE).fieldOf(IngredientUtil.AMOUNT_KEY).orElse(1).forGetter(NBTItemTagIngredient::getCount),
			ExtraCodecs.strictOptionalField(CompoundTag.CODEC, IngredientUtil.NBT_KEY, null).forGetter(NBTItemTagIngredient::getNBT)
			).apply(instance, (t, a, n) -> new NBTItemTagIngredient(IngredientUtil.getTagKey(t, ForgeRegistries.ITEMS), a, n)));

	@Override
	public @NotNull Codec<? extends NBTItemTagIngredient> codec()
	{
		return CODEC;
	} // end codec()
	
	

	@Override
	public @NotNull NBTItemTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Item> tag = IngredientUtil.getTagKey(jsonObject.get(IngredientUtil.TAG_KEY).getAsString(), ForgeRegistries.ITEMS);
		int count = jsonObject.get(IngredientUtil.AMOUNT_KEY).getAsInt();
		return new NBTItemTagIngredient(tag, count,  jsonObject.has(IngredientUtil.NBT_KEY) ? CraftingHelper.getNBT(jsonObject.get(IngredientUtil.NBT_KEY)) : null);
	} // end deserialize()
	
	@Override
	public @NotNull NBTItemTagIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new NBTItemTagIngredient(IngredientUtil.getTagKey(buffer.readUtf(), ForgeRegistries.ITEMS), buffer.readVarInt(), buffer.readBoolean() ? buffer.readNbt() : null);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull NBTItemTagIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeUtf(ingredient.getTag().toString());
		buffer.writeInt(ingredient.getCount());
		CompoundTag tag = ingredient.getNBT();
		buffer.writeBoolean(tag != null);
		if(tag != null) 
		{
			buffer.writeNbt(tag);
		}
	} // end toNetwork()
} // end class