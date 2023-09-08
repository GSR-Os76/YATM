package com.gsr.gsr_yatm.utilities.network;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.inventory.ContainerData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidHandlerContainerData implements ContainerData
{
	public static final int SLOT_COUNT = 3;
	
	public static final int AMOUNT_INDEX = 0;
	public static final int CAPACITY_INDEX = 1;
	public static final int FLUID_INDEX = 2;
	
	private final IFluidHandler m_fluidHandler;
	private final int m_tank;
	private final boolean m_allowSetting;
	
	
	
	public FluidHandlerContainerData(@NotNull IFluidHandler fluidHandler, int tank) 
	{
		this(Objects.requireNonNull(fluidHandler), tank, false);
	} // end constructor
	
	public FluidHandlerContainerData(@NotNull IFluidHandler fluidHandler, int tank, boolean allowSetting) 
	{
		this.m_fluidHandler = Objects.requireNonNull(fluidHandler);
		this.m_tank = tank;
		this.m_allowSetting = allowSetting;
	} // end constructor
	
	
	
	@Override
	public int get(@NotNegative int index)
	{
		return switch(index) 
		{
			case FluidHandlerContainerData.AMOUNT_INDEX -> this.m_fluidHandler.getFluidInTank(this.m_tank).getAmount();
			case FluidHandlerContainerData.CAPACITY_INDEX -> this.m_fluidHandler.getTankCapacity(this.m_tank);
			case FluidHandlerContainerData.FLUID_INDEX -> NetworkUtil.getFluidIndex(this.m_fluidHandler.getFluidInTank(this.m_tank).getFluid());
			default -> throw new IllegalArgumentException("Unexpected value of: " + index);
		};
	} // end get()

	@Override
	public void set(@NotNegative int index, int value)
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
	public @NotNegative int getCount()
	{
		return FluidHandlerContainerData.SLOT_COUNT;
	} // end getCount()
	
	
	
	public static int getCapacity(@NotNull ContainerData containerData) 
	{
		return containerData.get(FluidHandlerContainerData.CAPACITY_INDEX);
	} // end getCapacity()
	
	public static FluidStack getContents(@NotNull ContainerData containerData) 
	{
		return new FluidStack(NetworkUtil.getFluid(containerData.get(FluidHandlerContainerData.FLUID_INDEX)), containerData.get(FluidHandlerContainerData.AMOUNT_INDEX));
	} // end getContents()
	
} // end class