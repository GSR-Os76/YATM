package com.gsr.gsr_yatm.recipe.bioling;

import java.util.Map;

import com.gsr.gsr_yatm.registry.YATMFluids;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;

public class CompostableBiolingRecipe extends BiolingRecipe
{
	public static final float CHANCE_TO_QUANTITY_VALUE = 100f;
	
	
	
	public CompostableBiolingRecipe(ResourceLocation identifier, Map.Entry<ItemLike, Float> entry) 
	{
		this(identifier, entry.getKey(), entry.getValue());
	} // end constructor
	
	public CompostableBiolingRecipe(ResourceLocation identifier, ItemLike input, float resultChance)
	{
		super(identifier, Ingredient.of(input.asItem()), CompostableBiolingRecipe.getFluidStackFor(resultChance));
	} // end constructor
	
	
	
	private static FluidStack getFluidStackFor(float chance) 
	{
		return new FluidStack(YATMFluids.BIO.get(), (int)(chance * CHANCE_TO_QUANTITY_VALUE));
	} // end getFluidStackFor()
	
} // end class