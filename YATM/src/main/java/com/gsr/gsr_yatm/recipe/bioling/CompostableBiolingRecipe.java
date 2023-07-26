package com.gsr.gsr_yatm.recipe.bioling;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.ingredient.ItemStackIngredient;
import com.gsr.gsr_yatm.registry.YATMFluids;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;

public class CompostableBiolingRecipe extends BiolingRecipe
{
	public static final float CHANCE_TO_QUANTITY_VALUE = 100f;
	
	
	
	public CompostableBiolingRecipe(@NotNull ResourceLocation identifier, @NotNull Map.Entry<ItemLike, Float> entry) 
	{
		this(identifier, entry.getKey(), entry.getValue());
	} // end constructor
	
	public CompostableBiolingRecipe(@NotNull ResourceLocation identifier, @NotNull  ItemLike input, float resultChance)
	{
		super(identifier, new ItemStackIngredient(input.asItem()), CompostableBiolingRecipe.getFluidStackFor(resultChance));
	} // end constructor
	
	
	
	private static @NotNull FluidStack getFluidStackFor(float chance) 
	{
		return new FluidStack(YATMFluids.BIO.get(), (int)(chance * CHANCE_TO_QUANTITY_VALUE));
	} // end getFluidStackFor()
	
} // end class