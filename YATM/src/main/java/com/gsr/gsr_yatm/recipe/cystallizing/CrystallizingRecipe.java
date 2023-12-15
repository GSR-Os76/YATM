package com.gsr.gsr_yatm.recipe.cystallizing;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.block.device.crystallizer.CrystallizerBlockEntity;
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
import net.minecraftforge.items.IItemHandler;

public class CrystallizingRecipe extends RecipeBase<Container> implements ITimedRecipe<Container, Tuple3<IFluidHandler, IItemHandler, ICurrentHandler>>
{
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<FluidStack> m_input;
	private final @NotNull IIngredient<ItemStack> m_seed;	
	private final boolean m_consumeSeed;
	private final @NotNegative int m_currentPerTick;
	private final @NotNegative int m_timeInTicks;
	
	
	
	public CrystallizingRecipe(@NotNull String group, @NotNull IIngredient<FluidStack> input, @NotNull IIngredient<ItemStack> seed, boolean consumeSeed, @NotNull ItemStack result, @NotNegative int currentPerTick, @NotNegative int timeInTicks) 
	{
		super(Objects.requireNonNull(group));

		this.m_input = Objects.requireNonNull(input);
		this.m_seed = Objects.requireNonNull(seed);
		this.m_result = Objects.requireNonNull(result);
		this.m_consumeSeed = consumeSeed;
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

	public boolean consumeSeed()
	{
		return this.m_consumeSeed;
	} // end consumeSeed()
	
	public @NotNull IIngredient<ItemStack> seed()
	{
		return this.m_seed;
	} // end seed()
	
	public @NotNull IIngredient<FluidStack> input()
	{
		return this.m_input;
	} // end input()	
	
	public @NotNull ItemStack result()
	{
		return this.m_result.copy();
	} // end result()
	
	
	
	@Override
	public boolean matches(@NotNull Tuple3<IFluidHandler, IItemHandler, ICurrentHandler> context)
	{
		Fluid f = context.a().getFluidInTank(0).getFluid();
		int am = IngredientUtil.getRequiredAmountFor(f, this.m_input);
		FluidStack inputDrainSimulated = context.a().drain(new FluidStack(f, am), FluidAction.SIMULATE);
		
		return am != -1 
				&& inputDrainSimulated.getAmount() == am 
				&& this.m_seed.test(context.b().getStackInSlot(CrystallizerBlockEntity.SEED_SLOT)) 
				&& context.b().insertItem(CrystallizerBlockEntity.RESULT_SLOT, this.m_result, true).isEmpty();
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
		if(this.m_consumeSeed) 
		{
			context.b().extractItem(CrystallizerBlockEntity.SEED_SLOT, 1, false);
		}
		context.b().insertItem(CrystallizerBlockEntity.RESULT_SLOT, this.m_result.copy(), false);
	} // end end()
		
	
		
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
		return new ItemStack(YATMItems.STEEL_CRYSTALLIZER.get());
	} // end getToastSymbol()
	
} // end class