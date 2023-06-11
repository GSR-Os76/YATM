package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class ConfigurableInventoryWrapper implements IItemHandler, IItemHandlerModifiable
{
	private final IItemHandler m_inventory;
	private final int[] m_slots;
	private final SlotInsertionValidator m_validator;
	private final ItemInsertionEventHandler m_onItemInsertion;
	private final ItemWithdrawalEventHandler m_onItemWithdrawal;
	
	
	
	
	public ConfigurableInventoryWrapper(IItemHandler inventory, int[] slots) 
	{
		this(inventory, slots, (s, i, sim) -> true);
	} // end constructor
	
	public ConfigurableInventoryWrapper(IItemHandler inventory, int[] slots, SlotInsertionValidator validator) 
	{
		this(inventory, slots, validator, (i, is) -> {}, (i, a) -> {});
	} // end construct
	
	public ConfigurableInventoryWrapper(IItemHandler inventory, int[] slots, SlotInsertionValidator validator, ItemInsertionEventHandler onItemInsertion, ItemWithdrawalEventHandler onItemWithdrawal) 
	{
		m_inventory = inventory;
		// maybe validate against duplicated indices, or maybe not, could be a cool feature. don't hand hold.
		m_slots = slots;
		m_validator = validator;
		m_onItemInsertion = onItemInsertion;
		m_onItemWithdrawal = onItemWithdrawal;
	} // end constructor
	
	
	
	@Override
	public int getSlots()
	{
		return m_slots.length;
	} // end getSlots()

	@Override
	public @NotNull ItemStack getStackInSlot(int slot)
	{		
		return m_inventory.getStackInSlot(m_slots[slot]);
	} // end getStackInSlot()

	@Override
	public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
	{
		if(!m_validator.validate(slot, stack, simulate)) 
		{
			return stack;
		}
		
		ItemStack result = m_inventory.insertItem(m_slots[slot], stack, simulate);
		if(!simulate && !(stack.equals(result))) 
		{
			this.m_onItemInsertion.onItemInsertion(slot, result);
		}
		return result;
	} // end insertItem()

	@Override
	public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		ItemStack result = m_inventory.extractItem(m_slots[slot], amount, simulate);
		if(!simulate && !result.isEmpty()) 
		{
			this.m_onItemWithdrawal.onItemWithdrawal(slot, result.getCount());
		}
		return result;
	} // end extractItem()

	@Override
	public int getSlotLimit(int slot)
	{
		return m_inventory.getSlotLimit(m_slots[slot]);
	} // end getSlotLimit()

	@Override
	public boolean isItemValid(int slot, @NotNull ItemStack stack)
	{
		return m_inventory.isItemValid(m_slots[slot], stack);
	} // end isItemValid()

	
	
	@Override
	public void setStackInSlot(int slot, @NotNull ItemStack stack)
	{
		if(!m_validator.validate(slot, stack, false)) 
		{
			return;
		}

		this.m_onItemInsertion.onItemInsertion(slot, stack);
		
		if(m_inventory instanceof IItemHandlerModifiable iItemHandlerModifiable) 
		{
			iItemHandlerModifiable.setStackInSlot(m_slots[slot], stack);
		}		
	} // end setStackInSlot()
	
} // end class