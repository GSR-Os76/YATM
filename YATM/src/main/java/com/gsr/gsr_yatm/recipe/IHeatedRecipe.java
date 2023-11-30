package com.gsr.gsr_yatm.recipe;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public interface IHeatedRecipe<T extends Container> extends Recipe<T>
{
	public @NotNegative int getTemperature();
} // end interface