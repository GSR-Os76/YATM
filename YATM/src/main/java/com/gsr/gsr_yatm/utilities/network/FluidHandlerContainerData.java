package com.gsr.gsr_yatm.utilities.network;

import net.minecraft.world.inventory.ContainerData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidHandlerContainerData implements ContainerData
{
	public static final int AMOUNT_INDEX = 0;
	public static final int CAPACITY_INDEX = 1;
	public static final int FLUID_INDEX = 2;
	
	private final IFluidHandler m_fluidHandler;
	private final int m_tank;
	private final boolean m_allowSetting;
	
	
	
	public FluidHandlerContainerData(IFluidHandler fluidHandler, int tank) 
	{
		this(fluidHandler, tank, false);
	} // end constructor
	
	public FluidHandlerContainerData(IFluidHandler fluidHandler, int tank, boolean allowSetting) 
	{
		this.m_fluidHandler = fluidHandler;
		this.m_tank = tank;
		this.m_allowSetting = allowSetting;
	} // end constructor
	
	
	
	@Override
	public int get(int index)
	{
		return switch(index) 
		{
			case AMOUNT_INDEX -> this.m_fluidHandler.getFluidInTank(this.m_tank).getAmount();
			case CAPACITY_INDEX -> this.m_fluidHandler.getTankCapacity(this.m_tank);
			case FLUID_INDEX -> NetworkUtilities.getFluidIndex(this.m_fluidHandler.getFluidInTank(this.m_tank).getFluid());
			default -> throw new IllegalArgumentException("Unexpected value of: " + index);
		};
	} // end get()

	@Override
	public void set(int index, int value)
	{
		if(this.m_allowSetting) 
		{
			throw new IllegalArgumentException("not implemented yet");
			/*
			 * switch(index) { case AMOUNT_INDEX ->
			 * this.m_fluidHandler.getFluidInTank(this.m_tank).getAmount(); case
			 * CAPACITY_INDEX -> this.m_fluidHandler.getTankCapacity(this.m_tank); case
			 * FLUID_INDEX -> this.m_fluidHandler.getFluidInTank(this.m_tank).getFluid();
			 * default -> throw new IllegalArgumentException("Unexpected value of: " +
			 * index); }
			 */
		}
	} // end set()

	@Override
	public int getCount()
	{
		return 3;
	} // end getCount()
	
	
	
	public static int getCapacity(ContainerData containerData) 
	{
		return containerData.get(CAPACITY_INDEX);
	} // end getCapacity()
	
	public static FluidStack getContents(ContainerData containerData) 
	{
		return new FluidStack(NetworkUtilities.getFluid(containerData.get(FLUID_INDEX)), containerData.get(AMOUNT_INDEX));
	} // end getCapacity()
	
} // end class