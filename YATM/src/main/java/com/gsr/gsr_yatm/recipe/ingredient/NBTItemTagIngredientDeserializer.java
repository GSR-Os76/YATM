package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class NBTItemTagIngredientDeserializer implements IIngredientDeserializer<NBTItemTagIngredient>
{

	@Override
	public @NotNull NBTItemTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Item> tag = IngredientUtil.getItemTagKey(jsonObject.get(IngredientUtil.TAG_KEY).getAsString());
		int count = jsonObject.get(IngredientUtil.COUNT_KEY).getAsInt();
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