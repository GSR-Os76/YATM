package com.gsr.gsr_yatm.recipe.ingredient;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTagIngredientDeserializer implements IIngredientDeserializer<ItemTagIngredient>
{

	@Override
	public @NotNull ItemTagIngredient deserialize(@NotNull JsonObject jsonObject)
	{
		TagKey<Item> tag = IngredientUtil.getItemTagKey(jsonObject.get(IngredientUtil.TAG_KEY).getAsString());
		int count = jsonObject.get(IngredientUtil.COUNT_KEY).getAsInt();
		return new ItemTagIngredient(tag, count);
	} // end deserialize()
	
} // end class