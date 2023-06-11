package com.gsr.gsr_yatm.utilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

//import net.minecraftforge.registries.ForgeRegistries;

public class RecipeUtilities
{

	public static ITag<Item> getTag(String location)
	{
		ITagManager<Item> i = ForgeRegistries.ITEMS.tags();
		TagKey<Item> tk = i.createTagKey(new ResourceLocation(location));//YetAnotherTechMod.ITEMS.createTagKey("test");//.tag
		return i.getTag(tk);
	} // end getTag()

	//ForgeRegistries.ITEMS.tags().fo
} // end class