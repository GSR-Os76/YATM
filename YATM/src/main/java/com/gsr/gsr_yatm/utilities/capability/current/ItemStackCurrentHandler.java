package com.gsr.gsr_yatm.utilities.capability.current;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ItemStackCurrentHandler implements ICurrentHandler
{
	// TODO, make this and ItemStackItemStackHandler correspondingly instance customizable
	protected static final String CURRENT_HANDLER_TAG_NAME = "currentHandler";
	protected static final String STORED_TAG_NAME = "stored";
	
	private final @NotNull ItemStack m_self;
	private final @NotNegative int m_capacity;
	
	
	
	public ItemStackCurrentHandler(@NotNull ItemStack self, @NotNegative int capacity)
	{
		this.m_self = Objects.requireNonNull(self);		
		this.m_capacity = Contract.notNegative(capacity);
	} // end constructor()
	
	
	
	@Override
	public int recieveCurrent(@NotNegative int amount, boolean simulate)
	{
		Contract.notNegative(amount);
		
		int c = this.capacity();
		int s = this.stored();
		int r = Math.min(amount, c - s);
		if(!simulate && r != 0) 
		{
			this.setStored(s + r);
		}	
		return r;
	} // end recieveCurrent()

	@Override
	public int extractCurrent(@NotNegative int amount, boolean simulate)
	{
		Contract.notNegative(amount);
		int s = this.stored();
		int e = Math.min(amount, s);
		
		if(!simulate && e != 0) 
		{			
			this.setStored(s - e);
		}
		return e;
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return this.m_capacity;
	} // end capacity()

	@Override
	public int stored()
	{
		CompoundTag tag = this.m_self.getOrCreateTag();
		CompoundTag ch;
		if(tag.contains(ItemStackCurrentHandler.CURRENT_HANDLER_TAG_NAME)) 
		{
			ch = tag.getCompound(ItemStackCurrentHandler.CURRENT_HANDLER_TAG_NAME);
		}
		else 
		{			
			ch = new CompoundTag();
			tag.put(ItemStackCurrentHandler.CURRENT_HANDLER_TAG_NAME, ch);
		}
		if(!ch.contains(ItemStackCurrentHandler.STORED_TAG_NAME)) 
		{
			ch.putInt(ItemStackCurrentHandler.STORED_TAG_NAME, 0);
		}
		return ch.getInt(ItemStackCurrentHandler.STORED_TAG_NAME);
	} // end stored()

	protected void setStored(@NotNegative int stored) 
	{
		CompoundTag tag = this.m_self.getOrCreateTag();
		CompoundTag ch;
		if(tag.contains(ItemStackCurrentHandler.CURRENT_HANDLER_TAG_NAME)) 
		{
			ch = tag.getCompound(ItemStackCurrentHandler.CURRENT_HANDLER_TAG_NAME);
		}
		else 
		{			
			ch = new CompoundTag();
			tag.put(ItemStackCurrentHandler.CURRENT_HANDLER_TAG_NAME, ch);
		}
		ch.putInt(ItemStackCurrentHandler.STORED_TAG_NAME, Contract.notNegative(stored));
	} // end setStored()
	
} // end class