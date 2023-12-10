package com.gsr.gsr_yatm.recipe.bioreacting;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.recipe.ingredient.item_stack.ItemStackIngredient;
import com.gsr.gsr_yatm.registry.YATMFluids;

import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;

public class CompostableBioreactingRecipe extends BioreactingRecipe
{
	public static final float CHANCE_TO_QUANTITY_VALUE = 100f;
	
	
	
	public CompostableBioreactingRecipe(@NotNull Map.Entry<ItemLike, Float> entry) 
	{
		this(entry.getKey(), entry.getValue());
	} // end constructor
	
	public CompostableBioreactingRecipe(@NotNull ItemLike input, float resultChance)
	{
		super("", 
				new ItemStackIngredient(input.asItem()), 
				new FluidStack(YATMFluids.BIO.get(), (int)(resultChance * YATMConfigs.COMPOSTABLE_BIOREACTING_CHANCE_TO_QUANTITY.get())), 
				YATMConfigs.COMPOSTABLE_BIOREACTING_CURRENT_COST.get(), 
				(int)(resultChance * YATMConfigs.COMPOSTABLE_BIOREACTING_CHANCE_TO_TICKS.get()));
	} // end constructor
	
} // end class