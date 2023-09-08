package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

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

} // end class