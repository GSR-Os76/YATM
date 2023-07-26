package com.gsr.gsr_yatm.recipe.ingredient;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.registry.custom.YATMIngredientDeserializers;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public class ItemTagIngredient implements IIngredient<ItemStack>
{
	private final @NotNull TagKey<Item> m_tagKey;
	private final @NotNull ITag<Item> m_tag;
	private final int m_count;
	

	
	
	public ItemTagIngredient(@NotNull TagKey<Item> tag) 
	{
		this(tag, 1);
	} // end constructor
	
	public ItemTagIngredient(@NotNull TagKey<Item> tag, int count) 
	{
		Objects.nonNull(tag);
		
		this.m_tagKey = tag;
		this.m_tag = ForgeRegistries.ITEMS.tags().getTag(this.m_tagKey);
		this.m_count = count;
	} // end constructor
	
	
	
	@Override
	public boolean test(@Nullable ItemStack input)
	{
		return input != null 
				&& this.m_tag.contains(input.getItem()) 
				&& this.m_count <= input.getCount();
	} // end apply()

	@Override
	public @NotNull JsonObject serialize()
	{
		JsonObject jsObj = new JsonObject();
		jsObj.addProperty(IngredientUtilities.TAG_KEY, this.m_tagKey.location().toString());
		jsObj.addProperty(IngredientUtilities.COUNT_KEY, this.m_count);
		return jsObj;
	} // end serialize()

	@Override
	public @NotNull List<ItemStack> getValues()
	{
		return this.m_tag.stream().map((i) -> new ItemStack(i, this.m_count)).toList();
	} // end getValues()
	
	

	@Override
	public @NotNull ItemTagIngredientDeserializer deserializer()
	{
		return YATMIngredientDeserializers.ITEM_TAG_INGREDIENT.get();
	} // end deserializer()

} // end class