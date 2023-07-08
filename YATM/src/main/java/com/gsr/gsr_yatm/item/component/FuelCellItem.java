package com.gsr.gsr_yatm.item.component;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.IOxidizable;
import com.gsr.gsr_yatm.api.IOxidizer;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;

import net.minecraft.world.item.Item;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

// TODO, make this work
public class FuelCellItem extends Item implements ICurrentHandler, IFluidHandler
{
	private float m_efficiency;
	
	private int m_fuelTankVolume;	
	private FluidStack m_fuelTankContents = FluidStack.EMPTY;
	
	private int m_oxidizerTankVolume;	
	private FluidStack m_oxidizerTankContents = FluidStack.EMPTY;
	
	
	
	public FuelCellItem(Properties p_41383_, float efficiencyPercentage, int fuelTankVolume, int oxidizerTankVolume)
	{
		super(p_41383_);

		m_efficiency = efficiencyPercentage;
		
		m_fuelTankVolume = fuelTankVolume;
		m_oxidizerTankVolume = oxidizerTankVolume;
	} // end constructor
	
	
	
	// make separate the tank object
	private int getOxidizerMultiplier() 
	{
		if(m_oxidizerTankContents.isEmpty())
			return 0;
		
		return m_oxidizerTankContents.getFluid() instanceof IOxidizer ox 
				? ox.currentUnitPerUnitMultiplier() 
				: 1;
	} // end getOxidizerMultiplier()
	
	private int getFuelPotential() 
	{
		if(m_fuelTankContents.isEmpty())
			return 0;
		
		return m_fuelTankContents.getFluid() instanceof IOxidizable fuel
				? fuel.currentUnitsPerUnit() 
				: 0;
	}
	
	

	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
		return 0;
	} // end recieveCurrent()
	
	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		// take in liquid oxidizable fuel, oxidize, collect power in amount derived from the potential times the cells efficiency rating
		
		int currentPotential = getFuelPotential() * getOxidizerMultiplier();
		return (int)(((float)currentPotential) * m_efficiency);
	}

	@Override
	public int capacity()
	{
		return 0;
	} // end storageCapacity()

	@Override
	public int stored()
	{
		return 0;
	} // end storedCapacity()

	
	
	
	@Override
	public int getTanks()
	{
		return 2;
	} // end getTanks()

	// am I indexing the tanks as's appropriate? how could I possibly know, the documentation's unclear
	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		if(tank == 0)
			return m_fuelTankContents;
		else if(tank == 1)
			return m_oxidizerTankContents;
		else
			return FluidStack.EMPTY;
	} // end getFluidInTank()

	@Override
	public int getTankCapacity(int tank)
	{
		if(tank == 0)
			return m_fuelTankVolume;
		else if(tank == 1)
			return m_oxidizerTankVolume;
		else
			return 0;
	} // end getTankCapacity()

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		
		if(tank == 0)
			return stack.getFluid() instanceof IOxidizable;
		else if(tank == 1)
			return stack.getFluid() instanceof IOxidizer;
		else
			return false;
	} // end isFluidValid()

	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		FluidStack toDrainFrom;		
		if(!m_fuelTankContents.isEmpty() && m_fuelTankContents.getFluid() == resource.getFluid())
			toDrainFrom = m_fuelTankContents;
		else if(!m_oxidizerTankContents.isEmpty() && m_oxidizerTankContents.getFluid() == resource.getFluid())
			toDrainFrom = m_oxidizerTankContents;
		else		
			return FluidStack.EMPTY;		

		int amountToDrain = resource.getAmount() >= toDrainFrom.getAmount() 
				? toDrainFrom.getAmount() 
				: resource.getAmount();
		
		if(action == FluidAction.EXECUTE)
			toDrainFrom.shrink(amountToDrain);
		
		return new FluidStack(toDrainFrom.getFluid(), amountToDrain);
	} // end drain()

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		FluidStack toDrainFrom;
		if(!m_fuelTankContents.isEmpty())
			toDrainFrom = m_fuelTankContents;
		else if(!m_oxidizerTankContents.isEmpty()) 
			toDrainFrom = m_oxidizerTankContents;
		else		
			return FluidStack.EMPTY;
		
		int amountToDrain = maxDrain >= toDrainFrom.getAmount() 
				? toDrainFrom.getAmount() 
				: maxDrain;
		
		if(action == FluidAction.EXECUTE)
			toDrainFrom.shrink(amountToDrain);
		
		return new FluidStack(toDrainFrom.getFluid(), amountToDrain);		
	} // end drain()
	
} // end class