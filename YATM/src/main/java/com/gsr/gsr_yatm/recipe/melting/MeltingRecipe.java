package com.gsr.gsr_yatm.recipe.melting;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.block.device.crucible.CrucibleBlockEntity;
import com.gsr.gsr_yatm.recipe.IHeatedRecipe;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandler;

public class MeltingRecipe extends RecipeBase<Container> implements ITimedRecipe<Container, Tuple3<IItemHandler, IFluidHandler, IHeatHandler>>, IHeatedRecipe<Container>
{
	private final @NotNull FluidStack m_result;
	private final @NotNull IIngredient<ItemStack> m_input;
	private final @NotNegative int m_temperature;
	private final @NotNegative int m_timeInTicks;



	public MeltingRecipe(@NotNull String group, @NotNull IIngredient<ItemStack> input, @NotNull FluidStack result, @NotNegative int temperature, @NotNegative int timeInTicks)
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
	} // end getCurrentPerTick()

	@Override
	public @NotNegative int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()
	
	
	public @NotNull FluidStack result()
	{
		return this.m_result;
	} // end result()
	
	
	public @NotNull IIngredient<ItemStack> input()
	{
		return this.m_input;
	} // end input()


	
	

	@Override
	public boolean matches(@NotNull Tuple3<IItemHandler, IFluidHandler, IHeatHandler> context)
	{
		return this.m_input.test(context.a().getStackInSlot(CrucibleBlockEntity.INPUT_SLOT)) 
				&& context.b().fill(this.m_result.copy(), FluidAction.SIMULATE) == this.m_result.getAmount();
	} // end matches()
	
	@Override
	public boolean canTick(@NotNull Tuple3<IItemHandler, IFluidHandler, IHeatHandler> context)
	{
		return this.m_temperature <= context.c().getTemperature();
	} // end canTick()
	
	@Override
	public void tick(@NotNull Tuple3<IItemHandler, IFluidHandler, IHeatHandler> context)
	{
		// TODO, make consume some heat to perform recipe
		ITimedRecipe.super.tick(context);
	} // end tick()

	@Override
	public void end(@NotNull Tuple3<IItemHandler, IFluidHandler, IHeatHandler> context)
	{
		context.a().extractItem(CrucibleBlockEntity.INPUT_SLOT, IngredientUtil.getReqiuredCountFor(context.a().getStackInSlot(CrucibleBlockEntity.INPUT_SLOT).getItem(), this.m_input), false);
		context.b().fill(this.m_result.copy(), FluidAction.EXECUTE);
	} // end end()



	@Override
	public boolean matches(Container container, Level level)
	{
		return false;
	} // end matches()

	@Override
	public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		return ItemStack.EMPTY;
	} // end assemble()

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	} // end canCraftInDimensions()

	@Override
	public @NotNull ItemStack getResultItem(RegistryAccess registryAccess)
	{
		return ItemStack.EMPTY;
	} // end getResultItem

	@Override
	public @NotNull RecipeSerializer<MeltingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.MELTING.get();
	} // end getSerializer()

	@Override
	public @NotNull RecipeType<MeltingRecipe> getType()
	{
		return YATMRecipeTypes.MELTING.get();
	} // end getType()



	@Override
	public @NotNull ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.CRUCIBLE.get());
	} // end getToastSymbol()

	@Override
	public @NotNull NonNullList<ItemStack> getRemainingItems(Container container)
	{
		return ITimedRecipe.super.getRemainingItems(container);
	} // end getRemainingItems

	@Override
	public @NotNull NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(Ingredient.EMPTY, IngredientUtil.toMinecraftIngredient(this.m_input));
	} // end getIngredients()

} // end class