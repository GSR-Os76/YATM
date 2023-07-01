package com.gsr.gsr_yatm.recipe;

import com.gsr.gsr_yatm.YetAnotherTechMod;
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

public class ExtractionRecipe implements Recipe<Container>
{
	private final ResourceLocation m_identifier;
	private final FluidStack m_result;	
	private final Ingredient m_input;
	ItemStack m_inputRemainder = ItemStack.EMPTY;
	int m_currentPerTick = 8;
	int m_timeInTicks = 20;
	String m_group = "";
	
	
	
	public ExtractionRecipe(ResourceLocation identifier, Ingredient input, FluidStack result) 
	{
		this.m_identifier = identifier;
		this.m_input = input;
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

	public boolean hasRemainder()
	{
		return !this.m_inputRemainder.isEmpty();
	} // end hasRemainder()

	
	
	
	public boolean canBeUsedOn(IItemHandler inventory, IFluidHandler resultTank)
	{
		return this.m_input.test(inventory.getStackInSlot(ExtractorBlockEntity.INPUT_SLOT)) && 
				inventory.insertItem(ExtractorBlockEntity.INPUT_REMAINDER_SLOT, this.m_inputRemainder, true).isEmpty() &&
				resultTank.fill(this.m_result, FluidAction.SIMULATE) == this.m_result.getAmount();
	} // end canBeUsedOn()
	
	public void startRecipe(IItemHandler inventory)
	{
		inventory.extractItem(ExtractorBlockEntity.INPUT_SLOT, 1, false);
	} // end startRecipe()
	
	public void setResults(IItemHandler inventory, IFluidHandler resultTank)
	{
		inventory.insertItem(ExtractorBlockEntity.INPUT_REMAINDER_SLOT, this.m_inputRemainder.copy(), false);
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
	public RecipeSerializer<ExtractionRecipe> getSerializer()
	{
		return YATMRecipeSerializers.EXTRACTION.get();
	} // end getSerializer()

	@Override
	public RecipeType<ExtractionRecipe> getType()
	{
		return YATMRecipeTypes.EXTRACTION.get();
	} // end getType()

	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_EXTRACTOR_ITEM.get());
	} // end getToastSymbol()
	
	@Override
	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()



	@Deprecated
	public String writeSomeInfo()
	{
		YetAnotherTechMod.LOGGER.info("id: " + this.m_identifier);
		YetAnotherTechMod.LOGGER.info("input: " + this.m_input);
		YetAnotherTechMod.LOGGER.info("remainder: " + this.m_inputRemainder);
		YetAnotherTechMod.LOGGER.info("result: " + this.m_result);
		YetAnotherTechMod.LOGGER.info("cost: " + this.m_currentPerTick);
		YetAnotherTechMod.LOGGER.info("time: " + this.m_timeInTicks);
		return "";
	} // end writeSomeInfo()
	
} // end class