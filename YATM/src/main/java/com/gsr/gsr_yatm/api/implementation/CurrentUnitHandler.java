package com.gsr.gsr_yatm.api.implementation;

import java.util.function.Consumer;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class CurrentUnitHandler implements ICurrentHandler, INBTSerializable<CompoundTag>
{
	private static final String STORED_AMOUNT_TAG_NAME = "stored";
	private static final String CAPACITY_TAG_NAME = "capacity";
	
	private int m_storedUnits = 0;
	private int m_capacity;
	private Consumer<Integer> m_onCurrentRecieved;
	private Consumer<Integer> m_onCurrentExtracted;
	private boolean m_canRecieve = true;
	private boolean m_canExtract = true;
	
	
	
	public CurrentUnitHandler(int capacity) 
	{
		this.m_capacity = capacity;
	} // end constructor
	
	
	
	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
		if(!this.m_canRecieve) 
		{
			return 0;
		}
		
		int recieveable = Math.min(amount, this.m_capacity - this.m_storedUnits);
		if(!simulate) 
		{
			this.m_storedUnits += recieveable;
			if(this.m_onCurrentRecieved != null) 
			{
				this.m_onCurrentRecieved.accept(recieveable);
			}
		}
		return recieveable;
	} // end recieveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		if(!this.m_canExtract) 
		{
			return 0;
		}
		
		int extractable = Math.min(amount, this.m_storedUnits);
		if(!simulate) 
		{
			this.m_storedUnits -= extractable;
			if(this.m_onCurrentExtracted != null) 
			{
				this.m_onCurrentExtracted.accept(extractable);
			}
		}
		return extractable;
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return this.m_capacity;
	} // end storageCapacity()

	@Override
	public int stored()
	{
		return this.m_storedUnits;
	} // end storedCapcity()

	
	
	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(CurrentUnitHandler.STORED_AMOUNT_TAG_NAME, this.m_storedUnits);
		tag.putInt(CurrentUnitHandler.CAPACITY_TAG_NAME, this.m_capacity);
		return tag;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.m_storedUnits = nbt.getInt(CurrentUnitHandler.STORED_AMOUNT_TAG_NAME);
		this.m_capacity = nbt.getInt(CurrentUnitHandler.CAPACITY_TAG_NAME);
	} // end deserializeNBT()
	
	
	
	
	public static class Builder 
	{
		private int m_capacity = 0;
		private Consumer<Integer> m_onCurrentRecieved;
		private Consumer<Integer> m_onCurrentExtracted;
		private boolean m_canRecieve = true;
		private boolean m_canExtract = true;
		
		
		
		public Builder capacity(int capacity) 
		{
			this.m_capacity = capacity;
			return this;
		} // end capacity()
		
		public Builder onCurrentRecieved(Consumer<Integer> onCurrentRecieved) 
		{
			this.m_onCurrentRecieved = onCurrentRecieved;
			return this;
		} // end onCurrentRecieved()
		
		public Builder onCurrentExtracted(Consumer<Integer> onCurrentExtracted) 
		{
			this.m_onCurrentExtracted = onCurrentExtracted;
			return this;
		} // end onCurrentExtracted()
		
		public Builder canRecieve(boolean can) 
		{
			this.m_canRecieve = can;
			return this;
		} // end canRecieve()
		
		public Builder canExtract(boolean can) 
		{
			this.m_canExtract = can;
			return this;
		} // end canExtract()
		
		
		
		public CurrentUnitHandler build() 
		{
			CurrentUnitHandler temp = new CurrentUnitHandler(this.m_capacity);
			temp.m_onCurrentRecieved = this.m_onCurrentRecieved;
			temp.m_onCurrentExtracted = this.m_onCurrentExtracted;
			temp.m_canRecieve = this.m_canRecieve;
			temp.m_canExtract = this.m_canExtract;
			return temp;
		} // end build()
		
	} // end inner class
	
} // end class