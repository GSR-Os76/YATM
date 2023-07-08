package com.gsr.gsr_yatm.api.implementation;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;

import net.minecraftforge.energy.IEnergyStorage;

public class CurrentUnitForgeEnergyInterchanger implements ICurrentHandler, IEnergyStorage
{
	public static final float CU_TO_FE_INTERCHANGE_RATE = 1f/5f;
	
	private int m_storageCapacityInCUs = 0;
	private int m_storedEnergyInCUs = 0;
	
	
	
	public CurrentUnitForgeEnergyInterchanger(int storageCapacityInCUs) 
	{
		this.m_storageCapacityInCUs = storageCapacityInCUs;
	} // end constructor
	
	
	
	private void addFEToStoreCapacity(int amount) 
	{
		this.m_storedEnergyInCUs += (amount * CU_TO_FE_INTERCHANGE_RATE);
	} // addFEToStoreCapacity()
	
	
	
	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
		int recieveAmount = Math.min(amount, this.m_storageCapacityInCUs - this.m_storedEnergyInCUs);
		if(!simulate) 
		{
			this.m_storedEnergyInCUs += recieveAmount;
		}
		return recieveAmount;
	} // end recieveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		int extractAmount = Math.min(amount, this.m_storedEnergyInCUs);
		if(!simulate) 
		{
			this.m_storedEnergyInCUs -= extractAmount;
		}
		return extractAmount;
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return this.m_storageCapacityInCUs;
	} // storageCapacity()

	@Override
	public int stored()
	{
		return this.m_storedEnergyInCUs;
	} // storedCapacity()


	
	

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		int recieveAmount = Math.min(maxReceive, getMaxEnergyStored() - this.getEnergyStored());
		if(!simulate) 
		{
			this.addFEToStoreCapacity(recieveAmount);
		}
		return recieveAmount;
	} // end receiveEnergy()

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		int extractAmount = Math.min(maxExtract, this.getEnergyStored());
		if(!simulate) 
		{
			this.addFEToStoreCapacity(-extractAmount);
		}
		return extractAmount;
	} // end extractEnergy()

	@Override
	public int getEnergyStored()
	{
		return (int)(m_storedEnergyInCUs * CU_TO_FE_INTERCHANGE_RATE);
	} // end getEnergyStored()

	@Override
	public int getMaxEnergyStored()
	{
		return (int)(this.m_storageCapacityInCUs * CU_TO_FE_INTERCHANGE_RATE);
	} // end getMaxEnergyStored()

	@Override
	public boolean canExtract()
	{
		return true;
	} // end canExtract()

	@Override
	public boolean canReceive()
	{
		return true;
	} // end canRecieve()

	
	
} // end class