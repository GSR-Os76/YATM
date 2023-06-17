package com.gsr.gsr_yatm.api.implementation;

import java.util.function.Consumer;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class CurrentUnitHandler implements ICurrentHandler, INBTSerializable<CompoundTag>
{
	private int m_storedUnits = 0;
	private int m_capacity;
	private Consumer<Integer> m_onCurrentRecieved;
	private Consumer<Integer> m_onCurrentExtracted;
	
	
	
	public CurrentUnitHandler(int capacity) 
	{
		this.m_capacity = capacity;
	} // end constructor
	
	
	
	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
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
	public int storageCapacity()
	{
		return this.m_capacity;
	} // end storageCapacity()

	@Override
	public int storedCapacity()
	{
		return this.m_storedUnits;
	} // end storedCapcity()

	
	
	public static class Builder 
	{
		private int m_capacity = 0;
		private Consumer<Integer> m_onCurrentRecieved;
		private Consumer<Integer> m_onCurrentExtracted;
		
		
		
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
		
		
		
		public CurrentUnitHandler build() 
		{
			CurrentUnitHandler temp = new CurrentUnitHandler(this.m_capacity);
			temp.m_onCurrentRecieved = this.m_onCurrentRecieved;
			temp.m_onCurrentExtracted = m_onCurrentExtracted;
			return temp;
		} // end build()
		
	} // end inner class



	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt("stored", this.m_storedUnits);
		tag.putInt("capacity", this.m_capacity);
		return tag;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.m_storedUnits = nbt.getInt("stored");
		this.m_capacity = nbt.getInt("capacity");
	} // end deserializeNBT()
	
} // end class