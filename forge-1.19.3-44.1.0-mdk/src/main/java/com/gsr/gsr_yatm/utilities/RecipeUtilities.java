package com.gsr.gsr_yatm.utilities;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

public class RecipeUtilities
{	
	public static final String GROUP_KEY = "group";

	public static final String INPUT_OBJECT_KEY = "input";
	public static final String RESULT_OBJECT_KEY = "result";
	
	public static final String CURRENT_PER_TICK_KEY = "cost";
	public static final String TIME_IN_TICKS_KEY = "time";
			
	public static final String INGREDIENT_KEY = "ingedient";
	public static final String REMAINDER_STACK_KEY = "remainder";
	public static final String FLUID_KEY = "fluid";
	public static final String ITEM_KEY = "item";
	public static final String TAG_KEY = "tag";
	public static final String COUNT_KEY = "count";

	
	
	
	public static ITag<Item> getTag(String location)
	{
		ITagManager<Item> i = ForgeRegistries.ITEMS.tags();
		TagKey<Item> tk = i.createTagKey(new ResourceLocation(location));
		return i.getTag(tk);
	} // end getTag()

	public static FluidStack fluidStackFromJson(JsonObject jsonObject)
	{
		ResourceLocation fluidKey = new ResourceLocation(jsonObject.get(FLUID_KEY).getAsString());
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidKey);
        if (fluid == null)
        {
            return FluidStack.EMPTY;
        }
        int amount = 1;
        if(jsonObject.has(COUNT_KEY)) 
        {
        	amount = jsonObject.get(COUNT_KEY).getAsInt();
        }
		return new FluidStack(fluid, amount);
	} // end fluidStackFromJson()
	
	public static JsonObject fluidStackToJson(FluidStack fluidStack) 
	{
		JsonObject result = new JsonObject();
		result.addProperty(FLUID_KEY, ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
		result.addProperty(COUNT_KEY, fluidStack.getAmount());
		return result;
	} // end fluidStackToJson()

	public static JsonObject itemStackToJson(ItemStack itemStack)
	{
		JsonObject result = new JsonObject();
		result.addProperty(ITEM_KEY, ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
		result.addProperty(COUNT_KEY, itemStack.getCount());
		return result;
	} // end itemStackToJson()

} // end class