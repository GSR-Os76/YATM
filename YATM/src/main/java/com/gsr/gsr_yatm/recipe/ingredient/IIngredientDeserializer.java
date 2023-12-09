package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;

import net.minecraft.network.FriendlyByteBuf;

public interface IIngredientDeserializer<T extends IIngredient<?>>
{
	public @NotNull Codec<? extends T> codec();
	
	public @NotNull T deserialize(@NotNull JsonObject jsonObject);
	
	public @NotNull T fromNetwork(@NotNull FriendlyByteBuf buffer);
	
	public void toNetwork(@NotNull T ingredient, @NotNull FriendlyByteBuf buffer);
	
} // end interface