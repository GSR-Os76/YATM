package com.gsr.gsr_yatm.recipe.boiling;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class BoilingRecipe implements ITimedRecipe<Container>
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull FluidStack m_result;
	private final @NotNull IIngredient<FluidStack> m_input;
	int m_temperature = 373;
	int m_timeInTicks = 20;

	@NotNull String m_group = "";
	
	
	public BoilingRecipe(@NotNull ResourceLocation identifier, @NotNull IIngredient<FluidStack> input, @NotNull FluidStack result) 
	{
		Objects.requireNonNull(identifier);
		Objects.requireNonNull(input);
		Objects.requireNonNull(result);
		
		this.m_identifier = identifier;
		this.m_input = input;
		this.m_result = result.copy();
	} // end constructor
	
	
	
	public int getTemperature()
	{
		return this.m_temperature;
	} // end getCurrentPerTick()
	
	@Override
	public int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()

	
	
	
	public boolean canBeUsedOn(@NotNull IFluidHandler inputTank, @NotNull IFluidHandler resultTank, int temperature)
	{
		Fluid f = inputTank.getFluidInTank(0).getFluid();
		int am = IngredientUtilities.getRequiredAmountFor(f, this.m_input);
		FluidStack inputDrainSimulated = inputTank.drain(new FluidStack(f, am), FluidAction.EXECUTE);
		
		return am != -1 
				&& inputDrainSimulated.getAmount() == am 
				&& resultTank.fill(this.m_result, FluidAction.SIMULATE) == this.m_result.getAmount() 
				&& temperature >= this.m_temperature;
	} // end canBeUsedOn()
	
	public void startRecipe(@NotNull IFluidHandler inputTank)
	{
		Fluid f = inputTank.getFluidInTank(0).getFluid();
		inputTank.drain(new FluidStack(f, IngredientUtilities.getRequiredAmountFor(f, this.m_input)), FluidAction.EXECUTE);
	} // end startRecipe()
	
	public void setResults(@NotNull IFluidHandler resultTank)
	{
		resultTank.fill(this.m_result.copy(), FluidAction.EXECUTE);
	} // end setResults()
		
	
		
	// use canBeUsedOn() instead
	@Override
	public boolean matches(Container container, Level level)
	{
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
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<BoilingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.BOILING.get();
	} // end getSerializer()

	@Override
	public RecipeType<BoilingRecipe> getType()
	{
		return YATMRecipeTypes.BOILING.get();
	} // end getType()

	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_BOILER_ITEM.get());
	} // end getToastSymbol()

	@Override
	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()

	

	
} // end class