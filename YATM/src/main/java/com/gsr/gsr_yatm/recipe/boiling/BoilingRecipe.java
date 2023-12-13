package com.gsr.gsr_yatm.recipe.boiling;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.recipe.IHeatedRecipe;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.recipe.RecipeBase;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.Tuple3;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class BoilingRecipe extends RecipeBase<Container> implements ITimedRecipe<Container, Tuple3<IFluidHandler, IFluidHandler, IHeatHandler>>, IHeatedRecipe<Container>
{
	private final @NotNull FluidStack m_result;
	private final @NotNull IIngredient<FluidStack> m_input;
	private final @NotNegative int m_temperature;
	private final @NotNegative int m_timeInTicks;	
	
	
	
	public BoilingRecipe(@NotNull String group, @NotNull IIngredient<FluidStack> input, @NotNull FluidStack result, @NotNegative int temperature, @NotNegative int timeInTicks) 
	{
		super(Objects.requireNonNull(group));
		
		this.m_input = Objects.requireNonNull(input);
		this.m_result = result.copy();
		this.m_temperature = Contract.notNegative(temperature);
		this.m_timeInTicks = Contract.notNegative(timeInTicks);
	} // end constructor
	
	
	
	@Override
	public @NotNegative int getTemperature()
	{
		return this.m_temperature;
	} // end getTemperature()
	
	@Override
	public @NotNegative int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()
	
	public @NotNull FluidStack result()
	{
		return this.m_result.copy();
	} // end result()
	
	public @NotNull IIngredient<FluidStack> input()
	{
		return this.m_input;
	} // end input()
	
	
	
	@Override
	public boolean matches(@NotNull Tuple3<IFluidHandler, IFluidHandler, IHeatHandler> context)
	{
		Fluid f = context.a().getFluidInTank(0).getFluid();
		int am = IngredientUtil.getRequiredAmountFor(f, this.m_input);
		if (am == -1)
		{
			return false;
		}
		FluidStack inputDrainSimulated = context.a().drain(new FluidStack(f, am), FluidAction.SIMULATE);
		return inputDrainSimulated.getAmount() == am 
				&& context.b().fill(this.m_result, FluidAction.SIMULATE) == this.m_result.getAmount(); 
	} // end matches()

	@Override
	public boolean canTick(@NotNull Tuple3<IFluidHandler, IFluidHandler, IHeatHandler> context)
	{
		return this.m_temperature <= context.c().getTemperature();
	} // end canTick()

	@Override
	public void tick(@NotNull Tuple3<IFluidHandler, IFluidHandler, IHeatHandler> context)
	{
		// TODO, make consume some heat to perform recipe
		ITimedRecipe.super.tick(context);
	} // end tick()
	
	@Override
	public void end(@NotNull Tuple3<IFluidHandler, IFluidHandler, IHeatHandler> context)
	{
		Fluid f = context.a().getFluidInTank(0).getFluid();
		context.a().drain(new FluidStack(f, IngredientUtil.getRequiredAmountFor(f, this.m_input)), FluidAction.EXECUTE);
		context.b().fill(this.m_result.copy(), FluidAction.EXECUTE);
	} // end end()
		
	
		
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
		return new ItemStack(YATMItems.STEEL_BOILER.get());
	} // end getToastSymbol()
	
} // end class