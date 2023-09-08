package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.CraftingHelper;

public class NBTItemTagIngredientDeserializer implements IIngredientDeserializer<NBTItemTagIngredient>
{

	@Override
	public @NotNull NBTItemTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Item> tag = IngredientUtil.getItemTagKey(jsonObject.get(IngredientUtil.TAG_KEY).getAsString());
		int count = jsonObject.get(IngredientUtil.COUNT_KEY).getAsInt();
		return new NBTItemTagIngredient(tag, count,  jsonObject.has(IngredientUtil.NBT_KEY) ? CraftingHelper.getNBT(jsonObject.get(IngredientUtil.NBT_KEY)) : null);
	} // end deserialize()
	
} // end class