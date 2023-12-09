package com.gsr.gsr_yatm.recipe;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

public abstract class FinishedRecipeBase implements FinishedRecipe
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull AdvancementHolder m_advancement;
	private final @NotNull String m_group;
	
	
	
	public FinishedRecipeBase(@NotNull ResourceLocation identifier, @NotNull AdvancementHolder advancement, @NotNull String group) 
	{
		this.m_identifier = Objects.requireNonNull(identifier);
		this.m_advancement = Objects.requireNonNull(advancement);
		this.m_group = Objects.requireNonNull(group);
	} // end consturctor
	
	
	
	@Override
	public void serializeRecipeData(@NotNull JsonObject jsonObject)
	{
		if(!this.m_group.isEmpty()) 
		{
			jsonObject.addProperty(IngredientUtil.GROUP_KEY, this.m_group);
		}
	} // end serializeRecipeData()

	@Override
	public @NotNull ResourceLocation id()
	{
		return this.m_identifier;
	} // end id()

	@Override
	public @NotNull AdvancementHolder advancement()
	{
		return this.m_advancement;
	} // end advancement

} // end class