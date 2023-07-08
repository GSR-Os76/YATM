package com.gsr.gsr_yatm.recipe;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FluidInjectionRecipeSerializer implements RecipeSerializer<FluidInjectionRecipe>
{

	@Override
	public FluidInjectionRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject)
	{
		// should take in as ingredients a fluid and an item always, returning an item
		// a crafting time
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable FluidInjectionRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toNetwork(FriendlyByteBuf resourceLocation, FluidInjectionRecipe recipe)
	{
		// TODO Auto-generated method stub
		
	}

} // end class