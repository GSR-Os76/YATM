package com.gsr.gsr_yatm.recipe.spinning;

import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SpinningRecipe implements Recipe<Container>
{
	private final ResourceLocation m_identifier;
	private final ItemStack m_result;
	private final Ingredient m_input;
	String m_group = "";



	public SpinningRecipe(ResourceLocation identifier, Ingredient input, ItemStack result)
	{
		this.m_identifier = identifier;
		this.m_input = input;
		this.m_result = result.copy();
	} // end constructor



	
	public int getInputCount(Item item) 
	{
		return RecipeUtilities.getReqiuredCountFor(item, this.m_input);
	} // end getInputCount()
	
	public boolean canBeUsedOn(ItemStack input)
	{
		return RecipeUtilities.testIngredientAgainst(input, this.m_input);
	} // end canBeUsedOn()
	


	@Override
	public boolean matches(Container container, Level level)
	{
		return RecipeUtilities.testIngredientAgainst(container.getItem(0), this.m_input);				
	} // end matches()

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end assemble()

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	} // end canCraftInDimensions()

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end getResultItem

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<SpinningRecipe> getSerializer()
	{
		return YATMRecipeSerializers.SPINNING.get();
	} // end getSerializer()

	@Override
	public RecipeType<SpinningRecipe> getType()
	{
		return YATMRecipeTypes.SPINNING.get();
	} // end getType()



	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.SPINNING_WHEEL_ITEM.get());
	} // end getToastSymbol()	
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(Container container)
	{
		return Recipe.super.getRemainingItems(container);
	} // end getRemainingItems

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(null, this.m_input);
	} // end getIngredients()

	@Override
	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()

} // end class