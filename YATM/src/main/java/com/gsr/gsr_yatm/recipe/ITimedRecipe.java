package com.gsr.gsr_yatm.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public interface ITimedRecipe<T extends Container> extends Recipe<T>
{
	public int getTimeInTicks();
} // end interface