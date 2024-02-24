package com.gsr.gsr_yatm.recipe.injecting;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.block.device.crafting.injector.InjectorBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.recipe.RecipeBase;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple3;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandler;

public class InjectingRecipe extends RecipeBase<Container> implements ITimedRecipe<Container, Tuple3<IFluidHandler, IItemHandler, ICurrentHandler>>
{
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<FluidStack> m_input;
	private final @NotNull IIngredient<ItemStack> m_substrate;
	private final @NotNegative int m_currentPerTick;
	private final @NotNegative int m_timeInTicks;



	public InjectingRecipe(@NotNull String group, @NotNull IIngredient<FluidStack> input, @NotNull IIngredient<ItemStack> substrate, @NotNull ItemStack result, @NotNegative int currentPerTick, @NotNegative int timeInTicks)
	{
		super(Objects.requireNonNull(group));
		this.m_input = Objects.requireNonNull(input);
		this.m_substrate = Objects.requireNonNull(substrate);
		this.m_result = Objects.requireNonNull(result.copy());
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
	
	
	public @NotNull ItemStack result()
	{
		return this.m_result;
	} // end result()

	public @NotNull IIngredient<FluidStack> input()
	{
		return this.m_input;
	} // end input()
	
	public @NotNull IIngredient<ItemStack> substrate()
	{
		return this.m_substrate;
	} // end substrate()
	


	@Override
	public boolean matches(@NotNull Tuple3<IFluidHandler, IItemHandler, ICurrentHandler> context)
	{
		return this.m_input.test(context.a().getFluidInTank(0)) 
				&& this.m_substrate.test(context.b().getStackInSlot(InjectorBlockEntity.SUBSTRATE_SLOT)) 
				&& context.b().insertItem(InjectorBlockEntity.RESULT_SLOT, this.m_result.copy(), true).isEmpty();
	} // end matches()

	@Override
	public boolean canTick(@NotNull Tuple3<IFluidHandler, IItemHandler, ICurrentHandler> context) 
	{
		return context.c().extractCurrent(this.m_currentPerTick, true) == this.m_currentPerTick;
	} // end canTick()
	
	@Override
	public void tick(@NotNull Tuple3<IFluidHandler, IItemHandler, ICurrentHandler> context) 
	{
		context.c().extractCurrent(this.m_currentPerTick, false);
	} // end tick()
	
	@Override
	public void end(@NotNull Tuple3<IFluidHandler, IItemHandler, ICurrentHandler> context)
	{
		Fluid f = context.a().getFluidInTank(0).getFluid();
		context.a().drain(new FluidStack(f, IngredientUtil.getRequiredAmountFor(f, this.m_input)), FluidAction.EXECUTE);
		context.b().extractItem(InjectorBlockEntity.SUBSTRATE_SLOT, IngredientUtil.getReqiuredCountFor(context.b().getStackInSlot(InjectorBlockEntity.SUBSTRATE_SLOT).getItem(), this.m_substrate), false);
		context.b().insertItem(InjectorBlockEntity.RESULT_SLOT, this.m_result.copy(), false);
	} // end end()



	@Override
	public boolean matches(Container container, Level level)
	{
		return false;
	} // end matches()

	@Override
	public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end assemble()

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	} // end canCraftInDimensions()

	@Override
	public @NotNull ItemStack getResultItem(RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end getResultItem

	@Override
	public @NotNull RecipeSerializer<InjectingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.INJECTING.get();
	} // end getSerializer()

	@Override
	public @NotNull RecipeType<InjectingRecipe> getType()
	{
		return YATMRecipeTypes.INJECTING.get();
	} // end getType()



	@Override
	public @NotNull ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.INJECTOR.get());
	} // end getToastSymbol()

	@Override
	public @NotNull NonNullList<ItemStack> getRemainingItems(Container container)
	{
		return ITimedRecipe.super.getRemainingItems(container);
	} // end getRemainingItems

	@Override
	public @NotNull NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(Ingredient.EMPTY, IngredientUtil.toMinecraftIngredient(this.m_substrate)); // TODO, possibly add something like the following in, except this wouldn't be generic, unless ingredients were made to define their conversion to minecraft ingredients. IngredientUtilities.toMinecraftIngredients(this.m_input, this.m_substrate);
	} // end getIngredients()

} // end class