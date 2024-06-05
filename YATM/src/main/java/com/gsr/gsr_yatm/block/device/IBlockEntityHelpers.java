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
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import oshi.util.tuples.Pair;

// why an interface?
public interface IBlockEntityHelpers
{
	default @NotNull BlockEntity self()
	{
		return (BlockEntity) this;
	} // end self

	
	
	default @NotNull FluidTank newTank(@NotNegative int capacity)
	{
		return newTank(capacity, Integer.MAX_VALUE);
	} // end newInTank()
	
	default @NotNull FluidTank newTank(@NotNegative int capacity, @NotNegative int transferRate)
	{
		return this.newTank(capacity, transferRate, transferRate);
	} // end newTank()
	
	default @NotNull FluidTank newTank(@NotNegative int capacity, @NotNegative int fillTransferRate, @NotNegative int drainTransferRate)
	{
		return new FluidTank(Contract.notNegative(capacity))
		{
			@Override
			protected void onContentsChanged()
			{
				IBlockEntityHelpers.this.self().setChanged();
			} // end onContentsChanged()

			@Override
			public int fill(FluidStack resource, FluidAction action)
			{
				// guard against modifying empty stack
				if(resource.isEmpty()) 
				{
					return 0;
				}
				
				FluidStack r = resource.copy();
				r.setAmount(Math.min(resource.getAmount(), fillTransferRate));
				return super.fill(r, action);
			} // end fill()

			@Override
			public @NotNull FluidStack drain(int maxDrain, FluidAction action)
			{
				return super.drain(Math.min(maxDrain, drainTransferRate), action);
			} // end drain()
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
		return this.newCurrentHandler(capacity, Integer.MAX_VALUE);	
	} // end newCurrentHandler()
	
	default @NotNull CurrentHandler newCurrentHandler(@NotNegative int capacity, @NotNegative int maxTransfer) 
	{
		return CurrentHandler.Builder.of(capacity).onCurrentExtracted((i) -> this.self().setChanged()).onCurrentRecieved((i) -> this.self().setChanged()).maxTransfer(maxTransfer).build();	
	} // end newCurrentHandler()
	
	
	
	default @NotNull IItemHandler slot(@NotNull IItemHandler inv, @NotNegative int index) 
	{
		return InventoryWrapper.Builder.of(inv).slotTranslationTable(new int[] {Contract.notNegative(index)}).build();
	} // end slot()

	
	
	default @NotNull ICurrentHandler noFill(ICurrentHandler c) 
	{
		return new ICurrentHandler() 
		{

			@Override
			public @NotNegative int receiveCurrent(@NotNegative int amount, boolean simulate)
			{
				return 0;
			} // end recieveCurrent()

			@Override
			public @NotNegative int extractCurrent(@NotNegative int amount, boolean simulate)
			{
				return c.extractCurrent(amount, simulate);
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
	
	default @NotNull IFluidHandler noFill(IFluidHandler f)
	{
		return new IFluidHandler() 
		{

			@Override
			public int getTanks()
			{
				return f.getTanks();
			} // end getTanks()

			@Override
			public @NotNull FluidStack getFluidInTank(int tank)
			{
				return f.getFluidInTank(tank);
			} // end getFluidInTank()

			@Override
			public int getTankCapacity(int tank)
			{
				return f.getTankCapacity(tank);
			} // end getTankCapacity()

			@Override
			public boolean isFluidValid(int tank, @NotNull FluidStack stack)
			{
				return f.isFluidValid(tank, stack);
			} // end isFluidValid()

			@Override
			public int fill(FluidStack resource, FluidAction action)
			{
				return 0;
			} // end fill()

			@Override
			public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
			{
				return f.drain(resource, action);
			} // end drain()

			@Override
			public @NotNull FluidStack drain(int maxDrain, FluidAction action)
			{
				return f.drain(maxDrain, action);
			} // end drain()
			
		};
	} // end noFill()

	default @NotNull IFluidTank noFillTank(IFluidTank f)
	{
		return new IFluidTank() 
		{

			@Override
			public @NotNull FluidStack getFluid()
			{
				return f.getFluid();
			} // getFluid()

			@Override
			public int getFluidAmount()
			{
				return f.getFluidAmount();
			} // getFluidAmount()

			@Override
			public int getCapacity()
			{
				return f.getCapacity();
			} // getCapacity()

			@Override
			public boolean isFluidValid(FluidStack stack)
			{
				return f.isFluidValid(stack);
			} // isFluidValid()

			@Override
			public int fill(FluidStack resource, FluidAction action)
			{
				return 0;
			} // fill()

			@Override
			public @NotNull FluidStack drain(int maxDrain, FluidAction action)
			{
				return f.drain(maxDrain, action);
			} // drain()

			@Override
			public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
			{
				return f.drain(resource, action);
			} // drain()

		};
	} // end noFill()

	default @NotNull ICurrentHandler noDrain(ICurrentHandler c) 
	{
		return new ICurrentHandler() 
		{

			@Override
			public @NotNegative int receiveCurrent(@NotNegative int amount, boolean simulate)
			{
				return c.receiveCurrent(amount, simulate);
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
	
	default @NotNull IFluidHandler noDrain(IFluidHandler f)
	{
		return new IFluidHandler() 
		{

			@Override
			public int getTanks()
			{
				return f.getTanks();
			} // end getTanks()

			@Override
			public @NotNull FluidStack getFluidInTank(int tank)
			{
				return f.getFluidInTank(tank);
			} // end getFluidInTank()

			@Override
			public int getTankCapacity(int tank)
			{
				return f.getTankCapacity(tank);
			} // end getTankCapacity()

			@Override
			public boolean isFluidValid(int tank, @NotNull FluidStack stack)
			{
				return f.isFluidValid(tank, stack);
			} // end isFluidValid()

			@Override
			public int fill(FluidStack resource, FluidAction action)
			{
				return f.fill(resource, action);
			} // end fill()

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
	} // end noFill()

	
} // end interface