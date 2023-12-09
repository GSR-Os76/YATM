package com.gsr.gsr_yatm.recipe;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public abstract class RecipeBase<C extends Container> implements Recipe<C>
{
	private final @NotNull String m_group;

	
	
	public RecipeBase(@NotNull String group)
	{
		this.m_group = Objects.requireNonNull(group);
	} // end constructor

	
	
	@Override
	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()
	
} // end class