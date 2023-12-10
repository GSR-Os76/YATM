package com.gsr.gsr_yatm.recipe.ingredient.fluid_tag;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidTagIngredientDeserializer implements IIngredientDeserializer<FluidTagIngredient>
{
	private static final Codec<FluidTagIngredient> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.STRING.fieldOf(IngredientUtil.TAG_KEY).forGetter((i) -> i.getTag().toString()),
			Codec.intRange(0, Integer.MAX_VALUE).fieldOf(IngredientUtil.AMOUNT_KEY).forGetter(FluidTagIngredient::getAmount)
			).apply(instance, (t, a) -> new FluidTagIngredient(IngredientUtil.getTagKey(t, ForgeRegistries.FLUIDS), a)));

	@Override
	public @NotNull Codec<? extends FluidTagIngredient> codec()
	{
		return CODEC;
	} // end codec()

	@Override
	public @NotNull FluidTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Fluid> tag = IngredientUtil.getTagKey(jsonObject.get(IngredientUtil.TAG_KEY).getAsString(), ForgeRegistries.FLUIDS);
		int amount = jsonObject.get(IngredientUtil.AMOUNT_KEY).getAsInt();
		return new FluidTagIngredient(tag, amount);
	} // end deserialize()

	@Override
	public @NotNull FluidTagIngredient fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new FluidTagIngredient(IngredientUtil.getTagKey(buffer.readUtf(), ForgeRegistries.FLUIDS), buffer.readVarInt());
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FluidTagIngredient ingredient, @NotNull FriendlyByteBuf buffer)
	{
		buffer.writeUtf(ingredient.getTag().toString());
		buffer.writeInt(ingredient.getAmount());
	} // end toNetwork()

} // end class