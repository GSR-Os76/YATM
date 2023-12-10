package com.gsr.gsr_yatm.utilities.recipe;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

@SuppressWarnings("unused")
public class RecipeUtil
{	private static final List<Runnable> PERSISTENT_RELOAD_LISTENERS = new ArrayList<>();
	private static final List<Runnable> RELOAD_LISTENERS = new ArrayList<>();
	private static final Map<RecipeType<?>, List<IDynamicRecipeProvider<?>>> DYNAMIC_RECIPE_PROVIDERS = new HashMap<>();
	
	
	
	public static void recipesUpdated() 
	{
		for(int i = 0; i < RecipeUtil.RELOAD_LISTENERS.size(); i++) 
		{
			RecipeUtil.RELOAD_LISTENERS.remove(0).run();
		}
		RecipeUtil.PERSISTENT_RELOAD_LISTENERS.forEach((r) -> r.run());
	} // end recipesUpdated()
	
	public static void addPersistentRecipeLoadListener(@NotNull Runnable listener) 
	{
		RecipeUtil.PERSISTENT_RELOAD_LISTENERS.add(Objects.requireNonNull(listener));
	} // end addRecipeLoadListener()
	
	public static void addRecipeLoadListener(@NotNull Runnable listener) 
	{
		RecipeUtil.RELOAD_LISTENERS.add(Objects.requireNonNull(listener));
	} // end addRecipeLoadListener()
	
	@SuppressWarnings("unchecked")
	public static <C extends Container, T extends Recipe<C>> @Nullable RecipeHolder<T> loadRecipe(@NotNull String recipeId, @NotNull Level level, @NotNull RecipeType<T> type)
	{
		// TODO, make more like, or possible just call to, firstRecipeMatching. TODO, determine what I was talking abour here
		for (RecipeHolder<T> r : level.getRecipeManager().getAllRecipesFor(type))
		{
			if (r.id().toString().equals(recipeId))
			{
				return r;
			}
		}
		if(RecipeUtil.DYNAMIC_RECIPE_PROVIDERS.containsKey(type)) 
		{
			for(IDynamicRecipeProvider<?> dpl : RecipeUtil.DYNAMIC_RECIPE_PROVIDERS.get(type)) 
			{
				Enumeration<? extends RecipeHolder<? extends Recipe<? extends Container>>> e = dpl.getEnumerator(level);
				while(e.hasMoreElements()) 
				{
					var ei = e.nextElement();
					if (ei.id().toString().equals(recipeId))
					{
						return (RecipeHolder<T>) ei;
					}
				}
			}
		}
		return null;
	} // end loadRecipe()

	public static <C extends Container, T extends Recipe<C>> RecipeHolder<T> firstRecipeMatching(Level level, RecipeType<T> type, Predicate<T> predicate)
	{
		Enumeration<RecipeHolder<T>> recipes = getRecipes(level, type);
		while(recipes.hasMoreElements()) 
		{
			RecipeHolder<T> r = recipes.nextElement();
			if(predicate.test(r.value())) 
			{
				return r;
			}
		}
		
		return null;
	} // end firstRecipeMatching()
	
	
	
	public static <C extends Container, T extends Recipe<C>> Enumeration<RecipeHolder<T>> getRecipes(Level level, RecipeType<T> type)
	{
		return new Enumeration<RecipeHolder<T>>() 
		{
			Iterator<RecipeHolder<T>> levelRManager = level.getRecipeManager().getAllRecipesFor(type).iterator();
			Iterator<IDynamicRecipeProvider<?>> drp = RecipeUtil.DYNAMIC_RECIPE_PROVIDERS.containsKey(type) 
					? RecipeUtil.DYNAMIC_RECIPE_PROVIDERS.get(type).iterator() 
							: null;
			Enumeration<? extends RecipeHolder<? extends Recipe<? extends Container>>> drprs = null;
			
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
			public RecipeHolder<T> nextElement()
			{
				if(levelRManager.hasNext()) 
				{
					return levelRManager.next();
				}
				else 
				{
					if(drprs != null && drprs.hasMoreElements()) 
					{
						return (RecipeHolder<T>) drprs.nextElement();
					}
					while((drprs == null || !drprs.hasMoreElements()) && drp != null && drp.hasNext()) 
					{
						drprs = drp.next().getEnumerator(level);
						if(drprs.hasMoreElements()) 
						{
							return (RecipeHolder<T>) drprs.nextElement();
						}
					}
				}
				return null;
			} // end nextElement()
		};
	} // end getRecipes()
	
	public static void addDynamicRecipeProvider(IDynamicRecipeProvider<?> provider)
	{
		if(!RecipeUtil.DYNAMIC_RECIPE_PROVIDERS.containsKey(provider.recipeType())) 
		{
			RecipeUtil.DYNAMIC_RECIPE_PROVIDERS.put(provider.recipeType(), new ArrayList<IDynamicRecipeProvider<?>>());
		}
		RecipeUtil.DYNAMIC_RECIPE_PROVIDERS.get(provider.recipeType()).add(provider);
	} // end addDynamicRecipeProvider()
	
} // end class