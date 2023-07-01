package com.gsr.gsr_yatm.recipe;

import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class BoilingRecipe implements Recipe<Container>
{
	private ResourceLocation m_identifier;
	private FluidStack m_result;
	private FluidStack m_input;
	int m_temperature = 373;
	int m_timeInTicks = 20;

	String m_group = "";
	
	
	public BoilingRecipe(ResourceLocation identifier, FluidStack input, FluidStack result) 
	{
		this.m_identifier = identifier;
		this.m_input = input;
		this.m_result = result;
	} // end constructor
	
	
	
	public int getTemperature()
	{
		return this.m_temperature;
	} // end getCurrentPerTick()
	
	public int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()

	
	
	
	public boolean canBeUsedOn(IFluidHandler inputTank, IFluidHandler resultTank, int temperature)
	{
		// fix, input should be if it has m_input not can accept m_input
		FluidStack inputDrainSimulated = inputTank.drain(this.m_input, FluidAction.SIMULATE);
		return inputDrainSimulated.getFluid() == this.m_input.getFluid() && 
				inputDrainSimulated.getAmount() == this.m_input.getAmount() &&
				resultTank.fill(this.m_result, FluidAction.SIMULATE) == this.m_result.getAmount() &&
				temperature >= this.m_temperature;
	} // end canBeUsedOn()
	
	public void startRecipe(IFluidHandler inputTank)
	{
		inputTank.drain(this.m_input, FluidAction.EXECUTE);
	} // end startRecipe()
	
	public void setResults(IFluidHandler resultTank)
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