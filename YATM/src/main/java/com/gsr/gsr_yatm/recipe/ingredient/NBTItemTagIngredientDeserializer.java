package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.CraftingHelper;

public class NBTItemTagIngredientDeserializer implements IIngredientDeserializer<NBTItemTagIngredient>
{

	@Override
	public @NotNull NBTItemTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Item> tag = IngredientUtilities.getItemTagKey(jsonObject.get(IngredientUtilities.TAG_KEY).getAsString());
		int count = jsonObject.get(IngredientUtilities.COUNT_KEY).getAsInt();
		return new NBTItemTagIngredient(tag, count,  jsonObject.has(IngredientUtilities.NBT_KEY) ? CraftingHelper.getNBT(jsonObject.get(IngredientUtilities.NBT_KEY)) : null);
	} // end deserialize()
	
} // end class