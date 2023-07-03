package com.gsr.gsr_yatm.utilities;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.dynamic.IDynamicRecipeProvider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

@SuppressWarnings("unused")
public class RecipeUtilities
{	
	public static final int RECHECK_CRAFTING_PERIOD = 20;
	
	
	
	public static final String GROUP_KEY = "group";

	public static final String INPUT_OBJECT_KEY = "input";
	public static final String RESULT_OBJECT_KEY = "result";
	
	// unique secondary keys
	public static final String DIE_OBJECT_KEY = "die";
	public static final String SEED_KEY = "seed";
	public static final String CONSUME_SEED_KEY = "consume";
	
	public static final String CURRENT_PER_TICK_KEY = "cost";
	public static final String TIME_IN_TICKS_KEY = "time";
			
	public static final String COUNT_KEY = "count";
	public static final String FLUID_KEY = "fluid";
	public static final String INGREDIENT_KEY = "ingedient";
	public static final String ITEM_KEY = "item";
	public static final String REMAINDER_STACK_KEY = "remainder";
	public static final String TAG_KEY = "tag";
	// NOTE: it's in kelvin
	public static final String TEMPERATURE_KEY = "temperature";
	
	private static final List<Runnable> RELOAD_LISTENERS = new ArrayList<>();
	private static final Map<RecipeType<?>, List<IDynamicRecipeProvider<?>>> DYNAMIC_RECIPE_PROVIDERS = new HashMap<>();
	
	
	
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

	
	
	public static int getReqiuredCountFor(@NotNull Item countFor, @NotNull Ingredient in)
	{
		for(ItemStack i : in.getItems()) 
		{
			if(i.getItem() == countFor) 
			{
				return i.getCount();
			}
		}
		return -1;
	} // end getReqiuredCountFor()

	// respects item and count but not nbt
	public static boolean testIngredientAgainst(ItemStack against, Ingredient ingredient)
	{
		for(ItemStack i : ingredient.getItems()) 
		{
			if(i.getItem() == against.getItem() && i.getCount() <= against.getCount()) 
			{
				return true;
			}
		}
		return false;
	} // end testIngredientAgainst()

	
	
	public static void recipesUpdated() 
	{
		for(int i = 0; i < RecipeUtilities.RELOAD_LISTENERS.size(); i++) 
		{
			RecipeUtilities.RELOAD_LISTENERS.remove(0).run();
		}
	} // end recipesUpdated()
	
	public static void addRecipeLoadListener(Runnable handler) 
	{
		RecipeUtilities.RELOAD_LISTENERS.add(handler);
	} // end addRecipeLoadListener()
	
	@SuppressWarnings("unchecked")
	public static <C extends Container, T extends Recipe<C>> T loadRecipe(String recipeIdentifierToMatch, Level level, RecipeType<T> type)
	{
		for (T r : level.getRecipeManager().getAllRecipesFor(type))
		{
			if (r.getId().toString().equals(recipeIdentifierToMatch))
			{
				return r;
			}
		}
		YetAnotherTechMod.LOGGER.info("about to try dynamic loop");
		if(RecipeUtilities.DYNAMIC_RECIPE_PROVIDERS.containsKey(type)) 
		{
			YetAnotherTechMod.LOGGER.info("found key of recipe type");
			for(IDynamicRecipeProvider<?> dpl : RecipeUtilities.DYNAMIC_RECIPE_PROVIDERS.get(type)) 
			{
				YetAnotherTechMod.LOGGER.info("looking at dynamic recipe providers for type");
				Enumeration<? extends Recipe<? extends Container>> e = dpl.getEnumerator();
				while(e.hasMoreElements()) 
				{
					YetAnotherTechMod.LOGGER.info("has more elements");
					var ei = e.nextElement();
					if (ei.getId().toString().equals(recipeIdentifierToMatch))
					{
						return (T) ei;
					}
				}
			}
		}
		return null;
	} // end loadRecipe()

	
	
	public static <C extends Container, T extends Recipe<C>> Enumeration<T> getRecipes(Level level, RecipeType<T> type)
	{
		return new Enumeration<T>() 
		{
			Iterator<T> levelRManager = level.getRecipeManager().getAllRecipesFor(type).iterator();
			Iterator<IDynamicRecipeProvider<?>> drp = RecipeUtilities.DYNAMIC_RECIPE_PROVIDERS.containsKey(type) 
					? RecipeUtilities.DYNAMIC_RECIPE_PROVIDERS.get(type).iterator() 
							: null;
			Enumeration<? extends Recipe<? extends Container>> drprs = null;
			
			@Override
			public boolean hasMoreElements()
			{
				if(levelRManager.hasNext()) 
				{
					return true;
				}
				else 
				{
					if(drprs != null && drprs.hasMoreElements()) 
					{
						return true;
					}
					while((drprs == null || !drprs.hasMoreElements()) && drp != null && drp.hasNext()) 
					{
						drprs = drp.next().getEnumerator();
						if(drprs.hasMoreElements()) 
						{
							return true;
						}
					}
				}
				return false;
			} // end hasMoreElements()

			@SuppressWarnings("unchecked")
			@Override
			public T nextElement()
			{
				if(levelRManager.hasNext()) 
				{
					return levelRManager.next();
				}
				else 
				{
					if(drprs != null && drprs.hasMoreElements()) 
					{
						return (T) drprs.nextElement();
					}
					while((drprs == null || !drprs.hasMoreElements()) && drp != null && drp.hasNext()) 
					{
						drprs = drp.next().getEnumerator();
						if(drprs.hasMoreElements()) 
						{
							return (T) drprs.nextElement();
						}
					}
				}
				return null;
			} // end nextElement()
		};
	} // end getRecipes()
	
	public static void addDynamicRecipeProvider(IDynamicRecipeProvider<?> provider)
	{
		if(!RecipeUtilities.DYNAMIC_RECIPE_PROVIDERS.containsKey(provider.recipeType())) 
		{
			RecipeUtilities.DYNAMIC_RECIPE_PROVIDERS.put(provider.recipeType(), new ArrayList<IDynamicRecipeProvider<?>>());
		}
		RecipeUtilities.DYNAMIC_RECIPE_PROVIDERS.get(provider.recipeType()).add(provider);
	} // end addDynamicRecipeProvider()

} // end class