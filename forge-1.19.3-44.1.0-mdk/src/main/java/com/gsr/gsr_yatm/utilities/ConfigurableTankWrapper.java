package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ConfigurableTankWrapper implements IFluidHandler, IFluidTank
{
	private final IFluidHandler m_tank;
	private final FluidContentsChangedEventHandler m_onChanged;
	//private final int m_capacity;
	
	

	public ConfigurableTankWrapper(IFluidHandler tank, FluidContentsChangedEventHandler fluidContentsChangedEventHandler)//, int capacity) 
	{
		this.m_tank = tank;
		this.m_onChanged = fluidContentsChangedEventHandler;
		//this.m_capacity = capacity;
	} // end constructor
	
	
	
	@Override
	public int getTanks()
	{		
		return this.m_tank.getTanks();
	} // end getTanks

	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		//FluidStack contained = this.m_tank.getFluidInTank(tank);
		//return new FluidStack(contained.getFluid(), Math.min(contained.getAmount(), this.m_capacity));
		return this.m_tank.getFluidInTank(tank);
	} // end getFluidInTank

	@Override
	public int getTankCapacity(int tank)
	{
		return this.m_tank.getTankCapacity(tank);
	} // end getTankCapacity()

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		return this.m_tank.isFluidValid(tank, stack);
	} // end isFluidValid()

	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		//FluidStack wrappedResource = new FluidStack(resource.getFluid(), Math.min(resource.getAmount(), this.m_capacity));
		
		int result = this.m_tank.fill(resource, action);
		if(action.execute() && result > 0) 
		{
			this.m_onChanged.whatever();
		}
		return result; // this.m_tank.fill(wrappedResource, action);
	} // end fill()

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		//FluidStack wrappedResource = new FluidStack(resource.getFluid(), Math.min(resource.getAmount(), this.m_capacity));
		FluidStack result = this.m_tank.drain(resource, action);
		if(action.execute() && !result.isEmpty()) 
		{
			this.m_onChanged.whatever();
		}
		return result;//this.m_tank.drain(wrappedResource, action);
	} // end drain()

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		FluidStack result = this.m_tank.drain(maxDrain, action);
		if(action.execute() && !result.isEmpty()) 
		{
			this.m_onChanged.whatever();
		}
		return result;//this.m_tank.drain(Math.max(maxDrain, this.m_capacity), action);
	} // end drain()



	
	
	
	private IFluidTank m_t;
	
	@Override
	public @NotNull FluidStack getFluid()
	{
		this.pupulateT();
//		FluidStack g = this.m_t.getFluid();
//		FluidStack safetyWrap = new FluidStack(g.getFluid(), Math.min(g.getAmount(), m_capacity));
//		return safetyWrap;
		return this.m_t.getFluid();
	} // end getFluid()
	
	@Override
	public int getFluidAmount()
	{
		this.pupulateT();
		return this.m_t.getFluidAmount();//Math.min(this.m_t.getFluidAmount(), m_capacity);
	} // end getFluidAmount()
	
	@Override
	public int getCapacity()
	{
		this.pupulateT();
		return this.m_t.getCapacity();//Math.min(this.m_t.getCapacity(), m_capacity);
	} // end getCapacity
	
	@Override
	public boolean isFluidValid(FluidStack stack)
	{
		this.pupulateT();
		return this.m_t.isFluidValid(stack);
	} // end drain()
	
	private void pupulateT() 
	{
		if(this.m_t == null) 
		{
			this.m_t = (IFluidTank)this.m_tank;
		}
	} // end populateT()
	

} // end class