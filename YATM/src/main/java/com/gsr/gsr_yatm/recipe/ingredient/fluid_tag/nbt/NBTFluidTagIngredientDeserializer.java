package com.gsr.gsr_yatm.recipe.ingredient.fluid_tag.nbt;

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
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class NBTFluidTagIngredientDeserializer implements IIngredientDeserializer<NBTFluidTagIngredient>
{
	private static final Codec<NBTFluidTagIngredient> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.STRING.fieldOf(IngredientUtil.TAG_KEY).forGetter((i) -> i.getTag().toString()),
			Codec.intRange(0, Integer.MAX_VALUE).fieldOf(IngredientUtil.AMOUNT_KEY).forGetter(NBTFluidTagIngredient::getAmount),
			ExtraCodecs.strictOptionalField(CompoundTag.CODEC, IngredientUtil.NBT_KEY, null).forGetter(NBTFluidTagIngredient::getNBT)
			).apply(instance, (t, a, n) -> new NBTFluidTagIngredient(IngredientUtil.getTagKey(t, ForgeRegistries.FLUIDS), a, n)));

	@Override
	public @NotNull Codec<? extends NBTFluidTagIngredient> codec()
	{
		return CODEC;
	} // end codec()
	
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