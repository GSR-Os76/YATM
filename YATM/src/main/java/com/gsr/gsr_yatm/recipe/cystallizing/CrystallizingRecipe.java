package com.gsr.gsr_yatm.recipe.cystallizing;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerBlockEntity;
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
import net.minecraftforge.items.IItemHandler;

public class CrystallizingRecipe implements ITimedRecipe<Container>
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<ItemStack> m_seed;	
	private final @NotNull IIngredient<FluidStack> m_input;
	boolean m_consumeSeed = false;
	int m_currentPerTick = 8;
	int m_timeInTicks = 20;
	@NotNull String m_group = "";
	
	
	
	public CrystallizingRecipe(@NotNull ResourceLocation identifier, @NotNull IIngredient<FluidStack> input, @NotNull IIngredient<ItemStack> seed, @NotNull ItemStack result) 
	{
		Objects.requireNonNull(identifier);
		Objects.requireNonNull(input);
		Objects.requireNonNull(seed);
		Objects.requireNonNull(result);

		this.m_identifier = identifier;
		this.m_input = input;
		this.m_seed = seed;
		this.m_result = result.copy();
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

	
	
	
	public boolean canBeUsedOn(@NotNull IItemHandler inventory, @NotNull IFluidHandler inputTank)
	{
		Fluid f = inputTank.getFluidInTank(0).getFluid();
		int am = IngredientUtilities.getRequiredAmountFor(f, this.m_input);
		FluidStack inputDrainSimulated = inputTank.drain(new FluidStack(f, am), FluidAction.SIMULATE);
		
		return am != -1 
				&& inputDrainSimulated.getAmount() == am 
				&& this.m_seed.test(inventory.getStackInSlot(CrystallizerBlockEntity.SEED_SLOT)) 
				&& inventory.insertItem(CrystallizerBlockEntity.RESULT_SLOT, this.m_result, true).isEmpty();
	} // end canBeUsedOn()
	
	public void startRecipe(@NotNull IItemHandler inventory, @NotNull IFluidHandler inputTank)
	{
		Fluid f = inputTank.getFluidInTank(0).getFluid();
		inputTank.drain(new FluidStack(f, IngredientUtilities.getRequiredAmountFor(f, this.m_input)), FluidAction.EXECUTE);
		if(this.m_consumeSeed) 
		{
			inventory.extractItem(CrystallizerBlockEntity.SEED_SLOT, 1, false);
		}		
	} // end startRecipe()
	
	public void setResults(@NotNull IItemHandler inventory)
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
	public RecipeSerializer<CrystallizingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.CRYSTALLIZING.get();
	} // end getSerializer()

	@Override
	public RecipeType<CrystallizingRecipe> getType()
	{
		return YATMRecipeTypes.CRYSTALLIZING.get();
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