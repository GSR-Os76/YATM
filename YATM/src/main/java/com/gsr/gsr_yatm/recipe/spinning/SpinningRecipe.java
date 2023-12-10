package com.gsr.gsr_yatm.recipe.spinning;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.RecipeBase;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SpinningRecipe extends RecipeBase<Container>
{
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<ItemStack> m_input;



	public SpinningRecipe(@NotNull String group, @NotNull IIngredient<ItemStack> input, @NotNull ItemStack result)
	{
		super(Objects.requireNonNull(group));
		this.m_input = Objects.requireNonNull(input);
		this.m_result = result.copy();
	} // end constructor

	public @NotNull IIngredient<ItemStack> input()
	{
		return this.m_input;
	} // end input()

	public @NotNull ItemStack result() 
	{
		return this.m_result.copy();
	} // end result()
	

	
	public int getInputCount(@NotNull Item item) 
	{
		return IngredientUtil.getReqiuredCountFor(item, this.m_input);
	} // end getInputCount()
	
	public boolean matches(@NotNull ItemStack input)
	{
		return this.m_input.test(input);
	} // end canBeUsedOn()
	


	@Override
	public boolean matches(Container container, Level level)
	{
		return this.m_input.test(container.getItem(0));				
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
		return new ItemStack(YATMItems.SPINNING_WHEEL.get());
	} // end getToastSymbol()	
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(Container container)
	{
		return super.getRemainingItems(container);
	} // end getRemainingItems

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(Ingredient.EMPTY, IngredientUtil.toMinecraftIngredient(this.m_input));
	} // end getIngredients()

} // end class