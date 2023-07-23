package com.gsr.gsr_yatm.recipe.ingredient;

import java.util.List;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.registry.custom.YATMIngredientDeserializers;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStackIngredient implements IIngredient<ItemStack>
{
	private final ItemStack m_itemStack;

	
	
	public ItemStackIngredient(Item item) 
	{
		this(new ItemStack(item));
	} // end constructor
	
	public ItemStackIngredient(ItemStack itemStack) 
	{
		this.m_itemStack = itemStack.copy();
	} // end constructor
	
	
	
	@Override
	public boolean apply(ItemStack input)
	{
		return input.getItem() == this.m_itemStack.getItem() && input.getCount() >= this.m_itemStack.getCount();
	} // end apply()

	@Override
	public JsonObject serialize()
	{
		return RecipeUtilities.itemStackToJson(this.m_itemStack);
	} // end serialize()

	@Override
	public List<ItemStack> getValues()
	{
		return List.of(this.m_itemStack.copy());
	} // end getValues()



	@Override
	public IIngredientDeserializer<?> deserializer()
	{
		return YATMIngredientDeserializers.ITEM_STACK_INGREDIENT.get();
	} // end deserializer()
	
} // end class