package com.gsr.gsr_yatm.utilities.recipe;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.dynamic.IDynamicRecipeProvider;

import net.minecraft.nbt.CompoundTag;
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
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

@SuppressWarnings("unused")
public class RecipeUtilities
{	
	public static final int RECHECK_CRAFTING_PERIOD = 20;
	
	
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String GROUP_KEY = "group";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String INPUT_OBJECT_KEY = "input";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String RESULT_OBJECT_KEY = "result";
	
	// unique secondary keys
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String DIE_OBJECT_KEY = "die";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String SEED_KEY = "seed";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String CONSUME_SEED_KEY = "consume";
	
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String CURRENT_PER_TICK_KEY = "cost";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String TIME_IN_TICKS_KEY = "time";
			
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String COUNT_KEY = "count";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String FLUID_KEY = "fluid";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String INGREDIENT_KEY = "ingedient";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String ITEM_KEY = "item";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String REMAINDER_STACK_KEY = "remainder";
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String TAG_KEY = "tag";	
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String NBT_KEY = "nbt";
	// NOTE: it's in kelvin
	/**use IngredientUtilities instead*/
	@Deprecated
	public static final String TEMPERATURE_KEY = "temperature";
	
	
	private static final List<Runnable> PERSISTENT_RELOAD_LISTENERS = new ArrayList<>();
	private static final List<Runnable> RELOAD_LISTENERS = new ArrayList<>();
	private static final Map<RecipeType<?>, List<IDynamicRecipeProvider<?>>> DYNAMIC_RECIPE_PROVIDERS = new HashMap<>();
	
	
	
	/**use IngredientUtilities instead*/
	@Deprecated
	public static ITag<Item> getTag(String location)
	{
		ITagManager<Item> i = ForgeRegistries.ITEMS.tags();
		TagKey<Item> tk = i.createTagKey(new ResourceLocation(location));
		return i.getTag(tk);
	} // end getTag()

	/**use IngredientUtilities instead*/
	@Deprecated
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
        if(jsonObject.has(NBT_KEY)) 
        {
        	return new FluidStack(fluid, amount, CraftingHelper.getNBT(jsonObject.get(NBT_KEY)));
        }
		return new FluidStack(fluid, amount);
	} // end fluidStackFromJson()
	
	/**use IngredientUtilities instead*/
	@Deprecated
	public static JsonObject fluidStackToJson(FluidStack fluidStack) 
	{
		JsonObject result = new JsonObject();
		result.addProperty(FLUID_KEY, ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
		result.addProperty(COUNT_KEY, fluidStack.getAmount());
		CompoundTag t = fluidStack.getTag();
		if(t != null) 
		{
			result.addProperty(NBT_KEY, t.toString());
		}
		return result;
	} // end fluidStackToJson()

	/**use IngredientUtilities instead*/
	@Deprecated
	public static JsonObject itemStackToJson(ItemStack itemStack)
	{
		JsonObject result = new JsonObject();
		result.addProperty(ITEM_KEY, ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
		result.addProperty(COUNT_KEY, itemStack.getCount());
		CompoundTag t = itemStack.getTag();
		if(t != null) 
		{
			result.addProperty(NBT_KEY, t.toString());
		}
		return result;
	} // end itemStackToJson()

	
	
	/**use IngredientUtilities instead*/
	@Deprecated
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
	/**use IngredientUtilities instead*/
	@Deprecated
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
		RecipeUtilities.PERSISTENT_RELOAD_LISTENERS.forEach((r) -> r.run());
	} // end recipesUpdated()
	
	public static void addPersistentRecipeLoadListener(Runnable handler) 
	{
		RecipeUtilities.PERSISTENT_RELOAD_LISTENERS.add(handler);
	} // end addRecipeLoadListener()
	
	public static void addRecipeLoadListener(Runnable handler) 
	{
		RecipeUtilities.RELOAD_LISTENERS.add(handler);
	} // end addRecipeLoadListener()
	
	@SuppressWarnings("unchecked")
	public static <C extends Container, T extends Recipe<C>> T loadRecipe(String recipeIdentifierToMatch, Level level, RecipeType<T> type)
	{
		// TODO, make more like, or possible just call to, firstRecipeMatching
		for (T r : level.getRecipeManager().getAllRecipesFor(type))
		{
			if (r.getId().toString().equals(recipeIdentifierToMatch))
			{
				return r;
			}
		}
		if(RecipeUtilities.DYNAMIC_RECIPE_PROVIDERS.containsKey(type)) 
		{
			for(IDynamicRecipeProvider<?> dpl : RecipeUtilities.DYNAMIC_RECIPE_PROVIDERS.get(type)) 
			{
				Enumeration<? extends Recipe<? extends Container>> e = dpl.getEnumerator(level);
				while(e.hasMoreElements()) 
				{
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

	public static <C extends Container, T extends Recipe<C>> T firstRecipeMatching(Level level, RecipeType<T> type, Function<T, Boolean> predicate)
	{
		Enumeration<T> recipes = getRecipes(level, type);
		while(recipes.hasMoreElements()) 
		{
			T r = recipes.nextElement();
			if(predicate.apply(r)) 
			{
				return r;
			}
		}
		
		return null;
	} // end firstRecipeMatching()
	
	
	
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
						drprs = drp.next().getEnumerator(level);
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
						drprs = drp.next().getEnumerator(level);
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