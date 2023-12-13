package com.gsr.gsr_yatm.recipe;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public interface ITimedRecipe<T extends Container, A> extends Recipe<T>
{
	public @NotNegative int getTimeInTicks();
	
	

	
	public boolean matches(@NotNull A context);
	
	public default void start(@NotNull A context) { }
	
	public boolean canTick(@NotNull A context);

	public default void tick(@NotNull A context) { }

	public void end(@NotNull A context);

	
} // end interface