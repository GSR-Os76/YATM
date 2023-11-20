package com.gsr.gsr_yatm.recipe.smelting;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.current_furnace.CurrentFurnaceBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.items.IItemHandler;

public class WrappedSmeltingRecipe extends SmeltingRecipe implements ITimedRecipe<Container>
{
	
	public WrappedSmeltingRecipe(@NotNull SmeltingRecipe r)
	{
		super(r.getId(), r.getGroup(), r.category(), r.getIngredients().get(0), r.getResultItem(null), r.getExperience(), r.getCookingTime());
	} // end constructor

	public WrappedSmeltingRecipe(ResourceLocation id, String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime)
	{
		super(id, group, category, ingredient, result, experience, cookingTime);
	} // end constructor
	


	@Override
	public int getTimeInTicks()
	{
		return this.cookingTime;
	} // end getTimeInTicks
	
	
	
	public boolean canBeUsedOn(@NotNull IItemHandler inventory)
	{
		return this.ingredient.test(inventory.getStackInSlot(CurrentFurnaceBlockEntity.INPUT_SLOT)) && 
				inventory.insertItem(CurrentFurnaceBlockEntity.RESULT_SLOT, this.result, true).isEmpty();
	} // end canBeUsedOn()
	
	public void startRecipe(@NotNull IItemHandler inventory)
	{
		inventory.extractItem(CurrentFurnaceBlockEntity.INPUT_SLOT, 1, false);
	} // end startRecipe()
	
	public void setResults(@NotNull IItemHandler inventory)
	{
		inventory.insertItem(CurrentFurnaceBlockEntity.RESULT_SLOT, this.result.copy(), false);
	} // end setResults()

	
	
	@Override
	public ItemStack getToastSymbol()
	{
		// TODO Auto-generated method stub
		return new ItemStack(YATMItems.STEEL_CURRENT_FURNACE_ITEM.get());
	} // end getToastSymbol()
	
} // end class