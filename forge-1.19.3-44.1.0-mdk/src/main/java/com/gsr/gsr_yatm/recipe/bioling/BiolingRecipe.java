package com.gsr.gsr_yatm.recipe.bioling;

import com.gsr.gsr_yatm.block.device.bioler.BiolerBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandler;

public class BiolingRecipe implements ITimedRecipe<Container>
{
	protected final ResourceLocation m_identifier;
	protected final FluidStack m_result;	
	protected final Ingredient m_input;
	int m_currentPerTick = 8;
	int m_timeInTicks = 20;
	String m_group = "";
	
	
	
	public BiolingRecipe(ResourceLocation identifier, Ingredient input, FluidStack result) 
	{
		this.m_identifier = identifier;
		this.m_input = input;
		this.m_result = result;
	} // end constructor
	
	
	
	public int getCurrentPerTick()
	{
		return this.m_currentPerTick;
	} // end getCurrentPerTick()
	
	@Override
	public int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()

	
	
	
	public boolean canBeUsedOn(IItemHandler inventory, IFluidHandler resultTank)
	{
		return this.m_input.test(inventory.getStackInSlot(BiolerBlockEntity.INPUT_SLOT)) && 
				resultTank.fill(this.m_result, FluidAction.SIMULATE) == this.m_result.getAmount();
	} // end canBeUsedOn()
	
	public void startRecipe(IItemHandler inventory)
	{
		inventory.extractItem(BiolerBlockEntity.INPUT_SLOT, 1, false);
	} // end startRecipe()
	
	public void setResults(IItemHandler inventory, IFluidHandler resultTank)
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
	public RecipeSerializer<BiolingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.BIOLING.get();
	} // end getSerializer()

	@Override
	public RecipeType<BiolingRecipe> getType()
	{
		return YATMRecipeTypes.BIOLING.get();
	} // end getType()

	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_BIOLER_ITEM.get());
	} // end getToastSymbol()
	
	@Override
	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()
	
} // end class