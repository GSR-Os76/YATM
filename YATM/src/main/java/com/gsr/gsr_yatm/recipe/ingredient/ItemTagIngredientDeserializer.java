package com.gsr.gsr_yatm.recipe.ingredient;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTagIngredientDeserializer implements YATMIngredientDeserializer<ItemTagIngredient>
{

	@Override
	public IYATMIngredient<?> deserialize(JsonObject jsonObject)
	{
		TagKey<Item> tag = RecipeUtilities.getTag(jsonObject.get(RecipeUtilities.TAG_KEY).getAsString()).getKey();
		int count = jsonObject.get(RecipeUtilities.COUNT_KEY).getAsInt();
		return new ItemTagIngredient(tag, count);
	} // end deserialize()
	
} // end class