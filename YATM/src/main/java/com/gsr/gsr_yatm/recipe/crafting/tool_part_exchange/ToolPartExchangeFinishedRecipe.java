package com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ToolPartExchangeFinishedRecipe implements FinishedRecipe
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<ItemStack> m_tool;
	private final @NotNull IIngredient<ItemStack> m_part;
	private final @NotNull String m_group;
	private final @NotNull CraftingBookCategory m_category;
	
	private final @NotNull ResourceLocation m_advancementIdentifier;	
	private final @NotNull Advancement.Builder m_advancement;
	
	
	
	public ToolPartExchangeFinishedRecipe(@NotNull ResourceLocation identifier, @NotNull ItemStack result, @NotNull IIngredient<ItemStack> tool, @NotNull IIngredient<ItemStack> part, @NotNull String group, @NotNull CraftingBookCategory category, @NotNull ResourceLocation advancementIdentifier, @NotNull Advancement.Builder advancement) 
	{
		this.m_identifier = Objects.requireNonNull(identifier);
		this.m_result = Objects.requireNonNull(result);
		this.m_tool = Objects.requireNonNull(tool);
		this.m_part = Objects.requireNonNull(part);
		this.m_group = Objects.requireNonNull(group);
		this.m_category = Objects.requireNonNull(category);
		this.m_advancementIdentifier = Objects.requireNonNull(advancementIdentifier);
		this.m_advancement = Objects.requireNonNull(advancement);
	} // end constructor
	
	
	@Override
	public void serializeRecipeData(@NotNull JsonObject jsonObject)
	{
		if(!this.m_group.isEmpty()) 
		{
			jsonObject.addProperty(IngredientUtil.GROUP_KEY, this.m_group);
		}
		
		jsonObject.add(IngredientUtil.RESULT_KEY, IngredientUtil.nbtItemStackToJson(this.m_result));
		JsonObject inputObject = new JsonObject();
		inputObject.add(IngredientUtil.TOOL_KEY, IngredientUtil.writeIngredient(this.m_tool));
		inputObject.add(IngredientUtil.PART_KEY, IngredientUtil.writeIngredient(this.m_part));
		jsonObject.add(IngredientUtil.INPUT_KEY, inputObject);
		jsonObject.addProperty(IngredientUtil.CATEGORY_KEY, this.m_category.getSerializedName());
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<ToolPartExchangeRecipe> getType()
	{
		return YATMRecipeSerializers.TOOL_PART_EXCHANGE.get();
	} // end getType()

	@Override
	public JsonObject serializeAdvancement()
	{
		return this.m_advancement.serializeToJson();
	} // end serializeAdvancement()

	@Override
	public ResourceLocation getAdvancementId()
	{
		return this.m_advancementIdentifier;
	} // end getAdvancementId()

} // end class