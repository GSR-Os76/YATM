package com.gsr.gsr_yatm.recipe.ingredient;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.registry.custom.YATMRegistries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IngredientUtilities
{
	public static final String TYPE_KEY = "type";
	
	
	
	@SuppressWarnings("unchecked")
	public static <T> IYATMIngredient<T> readIngredient(JsonObject object)
	{
		YATMIngredientDeserializer<?> deserializer = YATMRegistries.INGREDIENT_DESERIALIZERS.get().getValue(new ResourceLocation(object.get(TYPE_KEY).getAsString()));
		return (IYATMIngredient<T>) deserializer.deserialize(object);
	} // end readIngredient()
	
	public static JsonObject writeIngredient(IYATMIngredient<?> ingredient)
	{
		JsonObject jsObj = ingredient.serialize();
		jsObj.addProperty(TYPE_KEY, YATMRegistries.INGREDIENT_DESERIALIZERS.get().getKey(ingredient.deserializer()).toString());
		return jsObj;
	} // end writeIngredient()

	
	
	public static int getReqiuredCountFor(Item forI, IYATMIngredient<ItemStack> in)
	{
		for(ItemStack i : in.getValues()) 
		{
			if(i.getItem() == forI) 
			{
				return i.getCount();
				
			}
		}
		return -1;
	} // end getReqiuredCountFor()
	
} // end class