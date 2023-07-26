package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class NBTFluidTagIngredientDeserializer implements IIngredientDeserializer<NBTFluidTagIngredient>
{

	@Override
	public @NotNull NBTFluidTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Fluid> tag = IngredientUtilities.getTagKey(jsonObject.get(IngredientUtilities.TAG_KEY).getAsString(), ForgeRegistries.FLUIDS);
		int amount = jsonObject.get(IngredientUtilities.AMOUNT_KEY).getAsInt();
		return new NBTFluidTagIngredient(tag, amount, jsonObject.has(IngredientUtilities.NBT_KEY) ? CraftingHelper.getNBT(jsonObject.get(IngredientUtilities.NBT_KEY)) : null);
	} // end deserialize()

} // end class