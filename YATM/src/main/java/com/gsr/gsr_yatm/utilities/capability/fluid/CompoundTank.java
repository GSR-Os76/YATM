package com.gsr.gsr_yatm.utilities.capability.fluid;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class CompoundTank implements IFluidHandler
{
	private final @NotNull List<@NotNull IFluidTank> m_tanks;
	
	
	
	public CompoundTank(@NotNull IFluidTank tank, @NotNull IFluidTank... tanks) 
	{
		Builder<IFluidTank> b = ImmutableList.builder();
		b.add(Objects.requireNonNull(tank));
		Stream.of(tanks).forEach((t) -> b.add(Objects.requireNonNull(t)));
		this.m_tanks = b.build();
	} // end constructor
	
	
	
	@Override
	public int getTanks()
	{
		return this.m_tanks.size();
	} // end getTanks()

	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		return this.m_tanks.get(tank).getFluid();
	} // end getFluidInTank()

	@Override
	public int getTankCapacity(int tank)
	{
		return this.m_tanks.get(tank).getCapacity();
	} // end getTankCapacity()

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		return this.m_tanks.get(tank).isFluidValid(stack);
	} // end isFluidValid();

	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		for(IFluidTank t : this.m_tanks) 
		{
			if(t.isFluidValid(resource)) 
			{
				return t.fill(resource, action);
			}
		}
		return 0;
	} // end fill

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		for(IFluidTank t : this.m_tanks) 
		{
			if(t.getFluid().equals(resource)) 
			{
				return t.drain(resource, action);
			}
		}
		return FluidStack.EMPTY;
	} // end drain()

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		for(IFluidTank t : this.m_tanks) 
		{
			if(t.getFluid().getAmount() > 0) 
			{
				return t.drain(maxDrain, action);
			}
		}
		return FluidStack.EMPTY;
	} // end drain()

} // end class