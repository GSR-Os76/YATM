package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class ConfigurableInventoryWrapper implements IItemHandler, IItemHandlerModifiable
{
	private final IItemHandler m_inventory;
	private int[] m_slots;
	private SlotInsertionValidator m_validator;
	private ItemInsertionEventHandler m_onItemInsertion;
	private ItemWithdrawalEventHandler m_onItemWithdrawal;
	
	
	
	
	private ConfigurableInventoryWrapper(IItemHandler inventory) 
	{
		this(inventory, createDefaultTranslationTable(inventory.getSlots()));
	} // end constructor
	
	
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
	
	
	
	private static int[] createDefaultTranslationTable(int slotCount) 
	{
		int[] t = new int[slotCount];
		for(int i = 0; i < slotCount; i++) 
		{
			t[i] = i;
		}
		return t;
	} // end createDefaultTranslationTable()
	
	
	
	public static class Builder
	{
		private ConfigurableInventoryWrapper m_building;
		public Builder(IItemHandler inventory) 
		{
			this.m_building = new ConfigurableInventoryWrapper(inventory);
		} // end constructor
	
		
		
		// if array is 3 long, with values 1, 4, 2, 
		// the first slot of this will be the first of the wrapper inventory, 
		// second accesses wrapped slot four,
		// third accesses wrapped slot two
		public Builder slotTranslationTable(int[] transTable) 
		{
			this.m_building.m_slots = transTable;
			return this;
		} // end slotTranslationTable()
		
		
		public Builder slotValidator(SlotInsertionValidator validator) 
		{
			this.m_building.m_validator = validator;
			return this;
		} // end slotValidator()
		
		public Builder onInsertion(ItemInsertionEventHandler onInsertion) 
		{
			this.m_building.m_onItemInsertion = onInsertion;
			return this;
		} // end slotValidator()
		
		public Builder onWithdrawal(ItemWithdrawalEventHandler onWithdrawal) 
		{
			this.m_building.m_onItemWithdrawal = onWithdrawal;
			return this;
		} // end slotValidator()
		
		public ConfigurableInventoryWrapper build() 
		{
			ConfigurableInventoryWrapper temp = this.m_building;
			this.internalCopy();
			return temp;
		} // end build()
		
		private void internalCopy() 
		{
			this.m_building = new ConfigurableInventoryWrapper(this.m_building.m_inventory, this.m_building.m_slots, this.m_building.m_validator, this.m_building.m_onItemInsertion, this.m_building.m_onItemWithdrawal);
		} // end internalCopy()
		
	} // end inner class
} // end class