package com.gsr.gsr_yatm.recipe.spinning;

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
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SpinningFinishedRecipe implements FinishedRecipe
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull ItemStack m_result;
	
	private final @NotNull IIngredient<ItemStack> m_input;
	
	private final @NotNull String m_group;
	private final @NotNull ResourceLocation m_advancementIdentifier;	
	private final @NotNull Advancement.Builder m_advancement;
	
	
	
	public SpinningFinishedRecipe(@NotNull ResourceLocation identifier, @NotNull ItemStack result, @NotNull IIngredient<ItemStack> input, @NotNull String group, @NotNull ResourceLocation advancementIdentifier, @NotNull Advancement.Builder advancement) 
	{
		Objects.requireNonNull(identifier);
		Objects.requireNonNull(result);
		Objects.requireNonNull(input);
		Objects.requireNonNull(group);
		Objects.requireNonNull(advancementIdentifier);
		Objects.requireNonNull(advancement);

		this.m_identifier = identifier;
		this.m_result = result;
		
		this.m_input = input;		
		
		this.m_group = group;
		this.m_advancementIdentifier = advancementIdentifier;
		this.m_advancement = advancement;
	} // end consturctor
	
	
	
	@Override
	public void serializeRecipeData(JsonObject jsonObject)
	{
		if(!this.m_group.isEmpty()) 
		{
			jsonObject.addProperty(IngredientUtil.GROUP_KEY, this.m_group);
		}
		
		jsonObject.add(IngredientUtil.RESULT_KEY, IngredientUtil.nbtItemStackToJson(this.m_result));
		JsonObject inputObject = new JsonObject();
		inputObject.add(IngredientUtil.INGREDIENT_KEY, IngredientUtil.writeIngredient(this.m_input));
		jsonObject.add(IngredientUtil.INPUT_KEY, inputObject);
	} // end serializeRecipeData()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<SpinningRecipe> getType()
	{
		return YATMRecipeSerializers.SPINNING.get();
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