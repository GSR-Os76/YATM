package com.gsr.gsr_yatm.block.device;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IBlockEntityHelpers
{
	default @NotNull BlockEntity self()
	{
		return (BlockEntity) this;
	} // end self

	default @NotNull FluidTank newInTank(@NotNegative int capacity)
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

	default @NotNull OnChangedHeatHandler newHeatHandler(@NotNegative int maxTemp)
	{
		return new OnChangedHeatHandler(maxTemp, (t) -> this.self().setChanged());
	} // end newHeatHandler()

} // end interface