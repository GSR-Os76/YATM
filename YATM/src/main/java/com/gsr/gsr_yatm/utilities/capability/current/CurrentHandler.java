package com.gsr.gsr_yatm.utilities.capability.current;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class CurrentHandler implements ICurrentHandler, INBTSerializable<CompoundTag>
{
	private static final String STORED_AMOUNT_TAG_NAME = "stored";
	private static final String CAPACITY_TAG_NAME = "capacity";
	
	private int m_storedUnits = 0;
	private boolean m_canExtract = true;
	private boolean m_canRecieve = true;
	private @NotNegative int m_capacity = 0;
	private @NotNull Consumer<Integer> m_onCurrentExtracted = (i) -> {};
	private @NotNull Consumer<Integer> m_onCurrentRecieved = (i) -> {};
	private @NotNegative int m_maxTransfer = 0;
	
	
	
	
	public CurrentHandler(@NotNegative int capacity) 
	{
		this.m_capacity = Contract.notNegative(capacity);
	} // end constructor
	
	
	
	@Override
	public @NotNegative int receiveCurrent(@NotNegative int amount, boolean simulate)
	{
		if(!this.m_canRecieve) 
		{
			return 0;
		}
		
		int recieveable = Math.min(this.m_maxTransfer, Math.min(amount, this.m_capacity - this.m_storedUnits));
		if(!simulate) 
		{
			this.m_storedUnits += recieveable;
			this.m_onCurrentRecieved.accept(recieveable);
		}
		return recieveable;
	} // end recieveCurrent()

	@Override
	public @NotNegative int extractCurrent(@NotNegative int amount, boolean simulate)
	{
		if(!this.m_canExtract) 
		{
			return 0;
		}
		
		int extractable = Math.min(this.m_maxTransfer, Math.min(amount, this.m_storedUnits));
		if(!simulate) 
		{
			this.m_storedUnits -= extractable;
			this.m_onCurrentExtracted.accept(extractable);
		}
		return extractable;
	} // end extractCurrent()

	@Override
	public @NotNegative int capacity()
	{
		return this.m_capacity;
	} // end storageCapacity()

	@Override
	public @NotNegative int stored()
	{
		return this.m_storedUnits;
	} // end storedCapcity()

	
	
	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(CurrentHandler.STORED_AMOUNT_TAG_NAME, this.m_storedUnits);
		tag.putInt(CurrentHandler.CAPACITY_TAG_NAME, this.m_capacity);
		return tag;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(@NotNull CompoundTag nbt)
	{
		this.m_storedUnits = nbt.getInt(CurrentHandler.STORED_AMOUNT_TAG_NAME);
		this.m_capacity = nbt.getInt(CurrentHandler.CAPACITY_TAG_NAME);
	} // end deserializeNBT()
	
	
	
	
	public static class Builder 
	{
		private boolean m_canExtract = true;
		private boolean m_canRecieve = true;
		private @NotNegative int m_capacity = 0;
		private @NotNull Consumer<Integer> m_onCurrentExtracted = (i) -> {};
		private @NotNull Consumer<Integer> m_onCurrentRecieved = (i) -> {};
		private @NotNegative int m_maxTransfer = Integer.MAX_VALUE;
		
		
		
		public static @NotNull Builder of() 
		{
			return new Builder();
		} // end of()
		
		public static @NotNull Builder of(@NotNegative int capacity) 
		{
			return new Builder().capacity(capacity);
		} // end of()
		
		
		
		public @NotNull Builder canExtract(boolean can) 
		{
			this.m_canExtract = can;
			return this;
		} // end canExtract()
		
		public @NotNull Builder canRecieve(boolean can) 
		{
			this.m_canRecieve = can;
			return this;
		} // end canRecieve()
		
		public @NotNull Builder capacity(@NotNegative int capacity) 
		{
			this.m_capacity = Contract.notNegative(capacity);
			return this;
		} // end capacity()
		
		public @NotNull Builder onCurrentExtracted(@NotNull Consumer<Integer> onCurrentExtracted) 
		{
			this.m_onCurrentExtracted = Objects.requireNonNull(onCurrentExtracted);
			return this;
		} // end onCurrentExtracted()
		
		public @NotNull Builder onCurrentRecieved(@NotNull Consumer<Integer> onCurrentRecieved) 
		{
			this.m_onCurrentRecieved = Objects.requireNonNull(onCurrentRecieved);
			return this;
		} // end onCurrentRecieved()
		
		public @NotNull Builder maxTransfer(@NotNegative int maxTransfer) 
		{
			this.m_maxTransfer = Contract.notNegative(maxTransfer);
			return this;
		} // end maxTransfer()
		
		
		
		public @NotNull CurrentHandler build() 
		{
			CurrentHandler temp = new CurrentHandler(this.m_capacity);
			temp.m_onCurrentRecieved = this.m_onCurrentRecieved;
			temp.m_onCurrentExtracted = this.m_onCurrentExtracted;
			temp.m_canRecieve = this.m_canRecieve;
			temp.m_canExtract = this.m_canExtract;
			temp.m_maxTransfer = this.m_maxTransfer;
			return temp;
		} // end build()
		
	} // end inner class
	
} // end class