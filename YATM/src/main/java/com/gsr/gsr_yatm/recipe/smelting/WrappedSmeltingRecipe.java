package com.gsr.gsr_yatm.recipe.smelting;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.block.device.heat_furnace.HeatFurnaceBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.items.IItemHandler;

public class WrappedSmeltingRecipe extends SmeltingRecipe implements ITimedRecipe<Container, Tuple2<IItemHandler, IHeatHandler>>
{
	
	public WrappedSmeltingRecipe(@NotNull SmeltingRecipe r)
	{
		super(r.getGroup(), r.category(), r.getIngredients().get(0), r.getResultItem(null), r.getExperience(), r.getCookingTime());
	} // end constructor

	public WrappedSmeltingRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime)
	{
		super(group, category, ingredient, result, experience, cookingTime);
	} // end constructor
	


	@Override
	public int getTimeInTicks()
	{
		return this.cookingTime;
	} // end getTimeInTicks
	
	
	@Override
	public boolean matches(@NotNull Tuple2<IItemHandler, IHeatHandler> context)
	{
		return this.ingredient.test(context.a().getStackInSlot(HeatFurnaceBlockEntity.INPUT_SLOT)) && 
				context.a().insertItem(HeatFurnaceBlockEntity.RESULT_SLOT, this.result, true).isEmpty();
	} // end canBeUsedOn()
	
	@Override
	public boolean canTick(@NotNull Tuple2<IItemHandler, IHeatHandler> context)
	{
		return /*TODO this.m_temperature TODO*/0 <= context.b().getTemperature();
	} // end canTick()

	@Override
	public void tick(@NotNull Tuple2<IItemHandler, IHeatHandler> context)
	{
		// TODO, make consume some heat to perform recipe
		ITimedRecipe.super.tick(context);
	} // end tick()
	
	@Override
	public void end(@NotNull Tuple2<IItemHandler, IHeatHandler> context)
	{
		context.a().extractItem(HeatFurnaceBlockEntity.INPUT_SLOT, 1, false);
		context.a().insertItem(HeatFurnaceBlockEntity.RESULT_SLOT, this.result.copy(), false);
	} // end end()

	
	
	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.HEAT_FURNACE.get());
	} // end getToastSymbol()
	
} // end class