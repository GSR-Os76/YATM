package com.gsr.gsr_yatm.recipe;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public interface ITimedRecipe<T extends Container> extends Recipe<T>
{
	public @NotNegative int getTimeInTicks();
} // end interface