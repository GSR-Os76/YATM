package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class MergingTankWrapper implements IFluidHandler
{
	//private final IFluidHandler m_a;
	//private final IFluidHandler m_b;
	
	
	public MergingTankWrapper(IFluidHandler a, IFluidHandler b) 
	{
		//this.m_a = a;
		//this.m_b = b;
		
		if(canMerge(a, b)) 
		{
			throw new UnsupportedOperationException("Attempted to merge two different fluids together.");
		}
	} // end constructor

	
	
	public static boolean canMerge(IFluidHandler a, IFluidHandler b) 
	{
		return a.getFluidInTank(0).isEmpty() || b.getFluidInTank(0).isEmpty() || (a.getFluidInTank(0) == b.getFluidInTank(0));
	} // end canMerge()
	
	

	@Override
	public int getTanks()
	{
		return 1;
	}


	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getTankCapacity(int tank)
	{
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		// TODO Auto-generated method stub
		return null;
	}
} // end class