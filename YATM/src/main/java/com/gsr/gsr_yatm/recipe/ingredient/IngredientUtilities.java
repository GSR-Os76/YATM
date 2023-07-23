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
	public static <T> IIngredient<T> readIngredient(JsonObject object)
	{
		IIngredientDeserializer<?> deserializer = YATMRegistries.INGREDIENT_DESERIALIZERS.get().getValue(new ResourceLocation(object.get(TYPE_KEY).getAsString()));
		return (IIngredient<T>) deserializer.deserialize(object);
	} // end readIngredient()
	
	public static JsonObject writeIngredient(IIngredient<?> ingredient)
	{
		JsonObject jsObj = ingredient.serialize();
		jsObj.addProperty(TYPE_KEY, YATMRegistries.INGREDIENT_DESERIALIZERS.get().getKey(ingredient.deserializer()).toString());
		return jsObj;
	} // end writeIngredient()

	
	
	public static int getReqiuredCountFor(Item forI, IIngredient<ItemStack> in)
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