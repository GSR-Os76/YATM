package com.gsr.gsr_yatm.recipe.ingredient;

import com.google.gson.JsonObject;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ItemStackIngredientDeserializer implements YATMIngredientDeserializer<ItemStackIngredient>
{
	@Override
	public IYATMIngredient<ItemStack> deserialize(JsonObject jsonObject)
	{
		// TODO Auto-generated method stub
		return new ItemStackIngredient(CraftingHelper.getItemStack(jsonObject, false, false));
	} // end deserialize()
	
} // end class