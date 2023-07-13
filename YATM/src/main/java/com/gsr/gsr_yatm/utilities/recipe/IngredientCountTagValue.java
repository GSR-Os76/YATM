package com.gsr.gsr_yatm.utilities.recipe;

import java.util.Collection;

import com.google.gson.JsonObject;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientCountTagValue extends Ingredient.TagValue
{
	private final int m_count;
	
	
	
	public IngredientCountTagValue(TagKey<Item> tag, int count)
	{
		super(tag);
		this.m_count = count;
	} // end constructor

	
	
	@Override
	public Collection<ItemStack> getItems()
	{
		return super.getItems().stream().map((i) -> i.copyWithCount(this.m_count)).toList();
	} // end getItems()

	@Override
	public JsonObject serialize()
	{
		JsonObject jsObj = super.serialize();
		jsObj.addProperty(RecipeUtilities.COUNT_KEY, this.m_count);
		return jsObj;
	} // end serialize()
	
} // end class