package com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

public class ToolPartExchangeRecipe implements CraftingRecipe
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<ItemStack> m_tool;
	private final @NotNull IIngredient<ItemStack> m_part;
	private final @NotNull String m_group;
	private final @NotNull CraftingBookCategory m_category;
	
	
	
	public ToolPartExchangeRecipe(@NotNull ResourceLocation identifier,
			@NotNull IIngredient<ItemStack> tool,
			@NotNull IIngredient<ItemStack> part,
			@NotNull ItemStack result,
			@NotNull String group, 
			@NotNull CraftingBookCategory category)
	{
		this.m_identifier = Objects.requireNonNull(identifier);
		this.m_tool = Objects.requireNonNull(tool);
		this.m_part = Objects.requireNonNull(part);
		this.m_result = Objects.requireNonNull(result);
		this.m_group = Objects.requireNonNull(group);
		this.m_category = Objects.requireNonNull(category);
	} // end constructor
	
	
	
	public IIngredient<ItemStack> getTool()
	{
		return this.m_tool;
	} // end getTool()
	
	public IIngredient<ItemStack> getPart()
	{
		return this.m_part;
	} // end getPart()
	
	

	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	public RecipeSerializer<ToolPartExchangeRecipe> getSerializer()
	{
		return YATMRecipeSerializers.TOOL_PART_EXCHANGE.get();
	} // end getSerializer()

	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()

	public CraftingBookCategory category()
	{
		return this.m_category;
	} // end category()

	public ItemStack getResultItem(RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end getResultItem()

	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(null, IngredientUtil.toMinecraftIngredient(this.m_tool), IngredientUtil.toMinecraftIngredient(this.m_part));
	} // end getIngredients()

	public boolean matches(CraftingContainer container, Level level)
	{
		List<ItemStack> inputs = new java.util.ArrayList<>();
		int stackHeld = 0;
		for (int j = 0; j < container.getContainerSize(); ++j)
		{
			ItemStack stack = container.getItem(j);
			if (!stack.isEmpty())
			{
				++stackHeld;
				inputs.add(stack);
			}
		}

		return stackHeld == this.getIngredients().size() 
				&& (RecipeMatcher.findMatches(inputs, List.of(this.m_tool, this.m_part)) != null);
	} // end matches()

	public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess)
	{
		CompoundTag tag = null;
		for (int j = 0; j < container.getContainerSize(); ++j)
		{
			ItemStack stack = container.getItem(j);
			if(this.m_tool.test(stack)) 
			{
				tag = stack.getTag();
				break;
			}
		}
		
		ItemStack result = this.getResultItem(registryAccess);
		result.setTag(tag);
		return result;
	} // end assembled()

	public boolean canCraftInDimensions(int width, int height)
	{
		return width * height >= this.getIngredients().size();
	} // end canCraftInDimensions()

} // end class