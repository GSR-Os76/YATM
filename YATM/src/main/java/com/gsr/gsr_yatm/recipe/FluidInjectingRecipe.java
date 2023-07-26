package com.gsr.gsr_yatm.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class FluidInjectingRecipe implements Recipe<Container>
{

	@Override
	public boolean matches(Container container, Level level)
	{
		// can container be used to craft this
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		// do the recipe
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		// something to do with the recipe book
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess)
	{
		// what's the result? need not copy
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(Container p_44004_)
	{
		// TODO Auto-generated method stub
		return Recipe.super.getRemainingItems(p_44004_);
	}

	@Override
	public ResourceLocation getId()
	{
		// id of the recipe in data pack
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public RecipeSerializer<FluidInjectingRecipe> getSerializer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecipeType<FluidInjectingRecipe> getType()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ItemStack getToastSymbol()
	{
		// TODO Auto-generated method stub
		return Recipe.super.getToastSymbol();
	}
} // end class