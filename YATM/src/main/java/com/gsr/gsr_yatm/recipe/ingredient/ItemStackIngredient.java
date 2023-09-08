package com.gsr.gsr_yatm.recipe.ingredient;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.registry.custom.YATMIngredientDeserializers;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStackIngredient implements IIngredient<ItemStack>
{
	private final @NotNull ItemStack m_ingredient;

	
	
	public ItemStackIngredient(@NotNull Item item) 
	{
		this(new ItemStack(item));
	} // end constructor
	
	public ItemStackIngredient(@NotNull ItemStack itemStack) 
	{
		Objects.requireNonNull(itemStack);
		this.m_ingredient = itemStack.copy();
	} // end constructor
	
	
	
	@Override
	public boolean test(@Nullable ItemStack input)
	{
		return input != null && this.m_ingredient.getItem() == input.getItem() && this.m_ingredient.getCount() <= input.getCount();
	} // end test()

	@Override
	public @NotNull JsonObject serialize()
	{
		return IngredientUtil.itemStackToJson(this.m_ingredient);
	} // end serialize()

	@Override
	public @NotNull List<ItemStack> getValues()
	{
		return List.of(this.m_ingredient.copy());
	} // end getValues()



	@Override
	public @NotNull ItemStackIngredientDeserializer deserializer()
	{
		return YATMIngredientDeserializers.ITEM_STACK_INGREDIENT.get();
	} // end deserializer()
	
} // end class