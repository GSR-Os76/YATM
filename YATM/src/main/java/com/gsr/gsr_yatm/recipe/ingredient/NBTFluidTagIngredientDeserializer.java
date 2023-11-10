package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class NBTFluidTagIngredientDeserializer implements IIngredientDeserializer<NBTFluidTagIngredient>
{

	@Override
	public @NotNull NBTFluidTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Fluid> tag = IngredientUtil.getTagKey(jsonObject.get(IngredientUtil.TAG_KEY).getAsString(), ForgeRegistries.FLUIDS);
		int amount = jsonObject.get(IngredientUtil.AMOUNT_KEY).getAsInt();
		return new NBTFluidTagIngredient(tag, amount, jsonObject.has(IngredientUtil.NBT_KEY) ? CraftingHelper.getNBT(jsonObject.get(IngredientUtil.NBT_KEY)) : null);
	} // end deserialize()
	
	@Override
	public @NotNull NBTFluidTagIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new NBTFluidTagIngredient(IngredientUtil.getTagKey(buffer.readUtf(), ForgeRegistries.FLUIDS), buffer.readVarInt(), buffer.readBoolean() ? buffer.readNbt() : null);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull NBTFluidTagIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeUtf(ingredient.getTag().toString());
		buffer.writeInt(ingredient.getAmount());
		CompoundTag tag = ingredient.getNBT();
		buffer.writeBoolean(tag != null);
		if(tag != null) 
		{
			buffer.writeNbt(tag);
		}
	} // end toNetwork()
} // end class