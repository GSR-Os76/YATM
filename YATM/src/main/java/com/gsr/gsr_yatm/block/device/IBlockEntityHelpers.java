package com.gsr.gsr_yatm.block.device;

import java.util.function.BiFunction;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import oshi.util.tuples.Pair;

public interface IBlockEntityHelpers
{
	default @NotNull BlockEntity self()
	{
		return (BlockEntity) this;
	} // end self

	
	
	default @NotNull FluidTank newTank(@NotNegative int capacity)
	{
		return new FluidTank(Contract.notNegative(capacity))
		{
			@Override
			protected void onContentsChanged()
			{
				IBlockEntityHelpers.this.self().setChanged();
			} // end onContentsChanged()

		};
	} // end newInTank()

	default @NotNull FluidTank newOutTank(@NotNegative int capacity)
	{
		return new FluidTank(Contract.notNegative(capacity))
		{
			@Override
			protected void onContentsChanged()
			{
				IBlockEntityHelpers.this.self().setChanged();
			} // end onContentsChanged()

			@Override
			public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
			{

				return FluidStack.EMPTY;
			} // end drain()

			@Override
			public @NotNull FluidStack drain(int maxDrain, FluidAction action)
			{
				return FluidStack.EMPTY;
			} // end drain()
		};
	} // end newOutTank()

	
	
	default @NotNull OnChangedHeatHandler newHeatHandler(@NotNegative int temp, @NotNegative int maxTemp)
	{
		return new OnChangedHeatHandler(temp, (t) -> this.self().setChanged(), IDeviceBlockEntity::deviceHeatEquation, maxTemp);
	} // end newHeatHandler()
	
	default @NotNull OnChangedHeatHandler newHeatHandler(@NotNegative int temp, @NotNull BiFunction<Integer, Integer, Pair<Integer, Integer>> heatEquation, @NotNegative int maxTemp)
	{
		return new OnChangedHeatHandler(temp, (t) -> this.self().setChanged(), heatEquation, maxTemp);
	} // end newHeatHandler()

	
	
	default @NotNull CurrentHandler newCurrentHandler(@NotNegative int capacity) 
	{
		return CurrentHandler.Builder.of(capacity).onCurrentExtracted((i) -> this.self().setChanged()).onCurrentRecieved((i) -> this.self().setChanged()).build();	
	} // end newCurrentHandler()
	
	
	
	default @NotNull IItemHandler slot(@NotNull IItemHandler inv, @NotNegative int index) 
	{
		return InventoryWrapper.Builder.of(inv).slotTranslationTable(new int[] {Contract.notNegative(index)}).build();
	} // end slot()



	default @NotNull ICurrentHandler noDrain(ICurrentHandler c) 
	{
		return new ICurrentHandler() 
		{

			@Override
			public @NotNegative int recieveCurrent(@NotNegative int amount, boolean simulate)
			{
				return c.recieveCurrent(amount, simulate);
			} // end recieveCurrent()

			@Override
			public @NotNegative int extractCurrent(@NotNegative int amount, boolean simulate)
			{
				return 0;
			} // end extractCurrent()

			@Override
			public @NotNegative int capacity()
			{
				return c.capacity();
			} // end capacity()

			@Override
			public @NotNegative int stored()
			{
				return c.stored();
			} // end stored()
			
		};
	} // end noDrain()
	
} // end interface