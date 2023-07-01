package com.gsr.gsr_yatm.recipe;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerBlockEntity;
import com.gsr.gsr_yatm.block.device.extractor.ExtractorBlockEntity;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandler;

public class CrystallizationRecipe implements Recipe<Container>
{
	private final ResourceLocation m_identifier;
	private final ItemStack m_result;
	private final Ingredient m_seed;	
	private final FluidStack m_input;
	boolean m_consumeSeed = false;
	int m_currentPerTick = 8;
	int m_timeInTicks = 20;
	String m_group = "";
	
	
	
	public CrystallizationRecipe(ResourceLocation identifier, FluidStack input, Ingredient seed, ItemStack result) 
	{
		this.m_identifier = identifier;
		this.m_input = input;
		this.m_seed = seed;
		this.m_result = result;
	} // end constructor
	
	
	
	public int getCurrentPerTick()
	{
		return this.m_currentPerTick;
	} // end getCurrentPerTick()
	
	public int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()

	
	
	
	public boolean canBeUsedOn(IItemHandler inventory, IFluidHandler inputTank)
	{
		FluidStack inputDrainSimulated = inputTank.drain(this.m_input, FluidAction.SIMULATE);
		return inputDrainSimulated.getFluid() == this.m_input.getFluid() && 
				inputDrainSimulated.getAmount() == this.m_input.getAmount() && 
				this.m_seed.test(inventory.getStackInSlot(CrystallizerBlockEntity.SEED_SLOT)) && 
				inventory.insertItem(CrystallizerBlockEntity.RESULT_SLOT, this.m_result, true).isEmpty();
	} // end canBeUsedOn()
	
	public void startRecipe(IItemHandler inventory, IFluidHandler inputTank)
	{
		inputTank.drain(this.m_input, FluidAction.EXECUTE);
		if(this.m_consumeSeed) 
		{
			inventory.extractItem(CrystallizerBlockEntity.SEED_SLOT, 1, false);
		}		
	} // end startRecipe()
	
	public void setResults(IItemHandler inventory)
	{
		inventory.insertItem(CrystallizerBlockEntity.RESULT_SLOT, this.m_result.copy(), false);
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
	} // end getResultItem()

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<CrystallizationRecipe> getSerializer()
	{
		return YATMRecipeSerializers.CRYSTALLIZATION.get();
	} // end getSerializer()

	@Override
	public RecipeType<CrystallizationRecipe> getType()
	{
		return YATMRecipeTypes.CRYSTALLIZATION.get();
	} // end getType()

	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_CRYSTALLIZER_ITEM.get());
	} // end getToastSymbol()
	
	@Override
	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()
	
} // end class