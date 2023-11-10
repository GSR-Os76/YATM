package com.gsr.gsr_yatm.recipe.ingredient;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.registry.custom.YATMIngredientDeserializers;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public class NBTItemTagIngredient implements IIngredient<ItemStack>
{
	private final @NotNull TagKey<Item> m_tagKey;
	private final @NotNull ITag<Item> m_tag;
	private final int m_count;
	private final @Nullable CompoundTag m_nbt;

	
	
	public NBTItemTagIngredient(@NotNull TagKey<Item> tag) 
	{
		this(Objects.requireNonNull(tag), 1, null);
	} // end constructor
	
	public NBTItemTagIngredient(@NotNull TagKey<Item> tag, @NotNegative int count, @Nullable CompoundTag nbt) 
	{
		this.m_tagKey = Objects.requireNonNull(tag);
		this.m_tag = ForgeRegistries.ITEMS.tags().getTag(this.m_tagKey);
		this.m_count = Contract.notNegative(count);
		this.m_nbt = nbt;
	} // end constructor
	
	public @NotNull TagKey<Item> getTag()
	{
		return this.m_tagKey;
	} // end getTag()
	
	public @NotNegative int getCount()
	{
		return this.m_count;
	} // end getCount()
	
	public @Nullable CompoundTag getNBT()
	{
		return this.m_nbt;
	} // end getNBT()
	
	
	
	@Override
	public boolean test(@Nullable ItemStack input)
	{
		return this.m_tag.contains(input.getItem()) 
				&& this.m_count <= input.getCount()
				&& ((this.m_nbt != null && input.hasTag()) || this.m_nbt.equals(input.getTag()));
	} // end apply()

	@Override
	public @NotNull JsonObject serialize()
	{
		JsonObject jsObj = new JsonObject();
		jsObj.addProperty(IngredientUtil.TAG_KEY, this.m_tagKey.location().toString());
		jsObj.addProperty(IngredientUtil.COUNT_KEY, this.m_count);
		if(this.m_nbt != null) 
		{
			jsObj.addProperty(IngredientUtil.NBT_KEY, this.m_nbt.toString());
		}
		return jsObj;
	} // end serialize()

	@Override
	public @NotNull List<ItemStack> getValues()
	{
		return this.m_tag.stream().map((i) -> new ItemStack(i, this.m_count, this.m_nbt)).toList();
	} // end getValues()
	
	

	@Override
	public @NotNull NBTItemTagIngredientDeserializer deserializer()
	{
		return YATMIngredientDeserializers.NBT_ITEM_TAG_INGREDIENT.get();
	} // end deserializer()

} // end class