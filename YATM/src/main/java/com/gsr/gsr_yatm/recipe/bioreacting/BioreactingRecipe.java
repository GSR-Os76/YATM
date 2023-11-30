package com.gsr.gsr_yatm.recipe.bioreacting;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.block.device.bioreactor.BioreactorBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandler;

public class BioreactingRecipe implements ITimedRecipe<Container>
{
	protected final @NotNull ResourceLocation m_identifier;
	protected final @NotNull FluidStack m_result;	
	protected final @NotNull IIngredient<ItemStack> m_input;
	// TODO, update for immutibility, same with the other recipes.
	@NotNegative int m_currentPerTick = 8;
	@NotNegative int m_timeInTicks = 20;
	@NotNull String m_group = "";
	
	
	
	public BioreactingRecipe(@NotNull ResourceLocation identifier, @NotNull IIngredient<ItemStack> input, @NotNull FluidStack result) 
	{
		this.m_identifier = Objects.requireNonNull(identifier);
		this.m_input = Objects.requireNonNull(input);
		this.m_result = Objects.requireNonNull(result.copy());;
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

	
	
	
	public boolean canTick(@NotNull ICurrentHandler battery) 
	{
		return battery.extractCurrent(this.m_currentPerTick, true) == this.m_currentPerTick;
	} // end canTick()
	
	public void tick(@NotNull ICurrentHandler battery) 
	{
		battery.extractCurrent(this.m_currentPerTick, false);
	} // end canTick()
	
	public boolean matches(@NotNull IItemHandler inventory, @NotNull IFluidHandler resultTank)
	{
		return this.m_input.test(inventory.getStackInSlot(BioreactorBlockEntity.INPUT_SLOT)) && 
				resultTank.fill(this.m_result, FluidAction.SIMULATE) == this.m_result.getAmount();
	} // end canBeUsedOn()
	
	public void setResults(@NotNull IItemHandler inventory, @NotNull IFluidHandler resultTank)
	{
		inventory.extractItem(BioreactorBlockEntity.INPUT_SLOT, IngredientUtil.getReqiuredCountFor(inventory.getStackInSlot(BioreactorBlockEntity.INPUT_SLOT).getItem(), this.m_input), false);
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
	public RecipeSerializer<BioreactingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.BIOREACTING.get();
	} // end getSerializer()

	@Override
	public RecipeType<BioreactingRecipe> getType()
	{
		return YATMRecipeTypes.BIOREACTING.get();
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