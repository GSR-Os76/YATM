package com.gsr.gsr_yatm.recipe.extracting;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.extractor.ExtractorBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.recipe.RecipeBase;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandler;

public class ExtractingRecipe extends RecipeBase<Container> implements ITimedRecipe<Container>
{
	private final @NotNull FluidStack m_result;	
	private final @NotNull IIngredient<ItemStack> m_input;
	private final @NotNull ItemStack m_inputRemainder;
	private final @NotNegative int m_currentPerTick;
	private final @NotNegative int m_timeInTicks;
	
	
	
	public ExtractingRecipe(@NotNull String group, @NotNull IIngredient<ItemStack> input, @NotNull ItemStack inputRemainder, @NotNull FluidStack result, @NotNegative int currentPerTick, @NotNegative int timeInTicks) 
	{
		super(Objects.requireNonNull(group));
		
		this.m_input = Objects.requireNonNull(input);
		this.m_inputRemainder = Objects.requireNonNull(inputRemainder);
		this.m_result = result.copy();
		this.m_currentPerTick = Contract.notNegative(currentPerTick);
		this.m_timeInTicks = Contract.notNegative(timeInTicks);
	} // end constructor
	
	
	
	public @NotNegative int getCurrentPerTick()
	{
		return this.m_currentPerTick;
	} // end getCurrentPerTick()
	
	@Override
	public @NotNegative int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()

	public boolean hasRemainder()
	{
		return !this.m_inputRemainder.isEmpty();
	} // end hasRemainder()

	public @NotNull FluidStack result()
	{
		return this.m_result;
	} // end result()
	
	public @NotNull IIngredient<ItemStack> input()
	{
		return this.m_input;
	} // end input()
	
	public @NotNull ItemStack inputRemainder()
	{
		return this.m_inputRemainder;
	} // end inputRemainder()
	
	
	
	public boolean canBeUsedOn(@NotNull IItemHandler inventory, @NotNull IFluidHandler resultTank)
	{
		return this.m_input.test(inventory.getStackInSlot(ExtractorBlockEntity.INPUT_SLOT)) && 
				inventory.insertItem(ExtractorBlockEntity.INPUT_REMAINDER_SLOT, this.m_inputRemainder, true).isEmpty() &&
				resultTank.fill(this.m_result, FluidAction.SIMULATE) == this.m_result.getAmount();
	} // end canBeUsedOn()
	
	public void startRecipe(@NotNull IItemHandler inventory)
	{
		inventory.extractItem(ExtractorBlockEntity.INPUT_SLOT, 1, false);
	} // end startRecipe()
	
	public void setResults(@NotNull IItemHandler inventory, @NotNull IFluidHandler resultTank)
	{
		inventory.insertItem(ExtractorBlockEntity.INPUT_REMAINDER_SLOT, this.m_inputRemainder.copy(), false);
		resultTank.fill(this.m_result.copy(), FluidAction.EXECUTE);
	} // end setResults()
		
	
		
	// use canBeUsedOn() instead
	@Override
	public boolean matches(Container container, Level level)
	{
		// TODO, maybe implement this to some limited extent.
		return false;
	} // end matches()

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		return ItemStack.EMPTY;
	} // end assemble()

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	} // end canCraftInDimensions()

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess)
	{
		return ItemStack.EMPTY;
	} // end getResultItem()

	@Override
	public RecipeSerializer<ExtractingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.EXTRACTING.get();
	} // end getSerializer()

	@Override
	public RecipeType<ExtractingRecipe> getType()
	{
		return YATMRecipeTypes.EXTRACTING.get();
	} // end getType()

	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_EXTRACTOR.get());
	} // end getToastSymbol()
	
} // end class