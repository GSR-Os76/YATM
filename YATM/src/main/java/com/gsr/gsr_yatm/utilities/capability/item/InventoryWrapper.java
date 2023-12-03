package com.gsr.gsr_yatm.utilities.capability.item;

import java.util.Objects;
import java.util.function.BiConsumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.capability.SlotUtil;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class InventoryWrapper implements IItemHandler, IItemHandlerModifiable
{
	private final @NotNull IItemHandler m_inventory;
	private final @NotNull int[] m_slots;
	private final @NotNull TriPredicate<Integer, ItemStack, Boolean> m_validator;
	private final @NotNull BiConsumer<Integer, ItemStack> m_onItemInsertion;
	private final @NotNull BiConsumer<Integer, ItemStack> m_onItemWithdrawal;
	
	
	
	
	public InventoryWrapper(@NotNull IItemHandler inventory, @NotNull int[] slots) 
	{
		this(inventory, slots, (s, i, sim) -> true);
	} // end constructor
	
	public InventoryWrapper(@NotNull IItemHandler inventory, @NotNull int[] slots, @NotNull TriPredicate<Integer, ItemStack, Boolean> validator) 
	{
		this(inventory, slots, validator, (i, is) -> {}, (i, a) -> {});
	} // end construct
	
	public InventoryWrapper(@NotNull IItemHandler inventory, @NotNull int[] slots, @NotNull TriPredicate<Integer, ItemStack, Boolean> validator, @NotNull BiConsumer<Integer, ItemStack> onItemInsertion, @NotNull BiConsumer<Integer, ItemStack> onItemWithdrawal) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_slots = Objects.requireNonNull(slots);
		this.m_validator = Objects.requireNonNull(validator);
		this.m_onItemInsertion = Objects.requireNonNull(onItemInsertion);
		this.m_onItemWithdrawal = Objects.requireNonNull(onItemWithdrawal);
	} // end constructor
	
	
	
	@Override
	public int getSlots()
	{
		return this.m_slots.length;
	} // end getSlots()

	@Override
	public @NotNull ItemStack getStackInSlot(int slot)
	{		
		return m_inventory.getStackInSlot(this.m_slots[slot]);
	} // end getStackInSlot()

	@Override
	public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
	{
		if(!this.m_validator.test(slot, stack, simulate)) 
		{
			return stack;
		}
		
		ItemStack result = m_inventory.insertItem(this.m_slots[slot], stack, simulate);
		if(!simulate && !(stack.equals(result))) 
		{
			this.m_onItemInsertion.accept(slot, result);
		}
		return result;
	} // end insertItem()

	@Override
	public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		ItemStack result = m_inventory.extractItem(this.m_slots[slot], amount, simulate);
		if(!simulate && !result.isEmpty()) 
		{
			this.m_onItemWithdrawal.accept(slot, result);
		}
		return result;
	} // end extractItem()

	@Override
	public int getSlotLimit(int slot)
	{
		return this.m_inventory.getSlotLimit(this.m_slots[slot]);
	} // end getSlotLimit()

	@Override
	public boolean isItemValid(int slot, @NotNull ItemStack stack)
	{
		return this.m_validator.test(slot, stack, true) 
				&& this.m_inventory.isItemValid(this.m_slots[slot], stack);
	} // end isItemValid()

	
	
	@Override
	public void setStackInSlot(int slot, @NotNull ItemStack stack)
	{
		if(m_inventory instanceof IItemHandlerModifiable iItemHandlerModifiable) 
		{
			iItemHandlerModifiable.setStackInSlot(m_slots[slot], stack);
			this.m_onItemInsertion.accept(slot, stack);		
		}		
	} // end setStackInSlot()
	
	
	
	
	
	public static class Builder
	{
		private IItemHandler m_inventory;
		private int[] m_slots;
		private TriPredicate<Integer, ItemStack, Boolean> m_validator = (sl, st, sim) -> true;;
		private BiConsumer<Integer, ItemStack> m_onItemInsertion = (sl, st) -> {};
		private BiConsumer<Integer, ItemStack> m_onItemWithdrawal = (sl, st) -> {};
		
		
		
		protected Builder() 
		{

		} // end constructor
	
		public static Builder of() 
		{
			return new Builder();
		} // end of()
		
		public static Builder of(IItemHandler inventory) 
		{
			Builder b = new Builder();
			b.m_inventory = inventory;
			b.m_slots = SlotUtil.defaultTranslationTable(inventory.getSlots());
			return b;
		} // end of()
		
		
		
		public Builder inventory(IItemHandler inventory) 
		{
			this.m_inventory = inventory;
			return this;
		} // end inventory
		
		// if array is 3 long, with values 1, 4, 2, 
		// the first slot of this will be the first of the wrapper inventory, 
		// second accesses wrapped slot four,
		// third accesses wrapped slot two
		public Builder slotTranslationTable(int[] translationTable) 
		{
			this.m_slots = translationTable;
			return this;
		} // end slotTranslationTable()
		
		public Builder slotValidator(TriPredicate<Integer, ItemStack, Boolean> validator) 
		{
			this.m_validator = validator;
			return this;
		} // end slotValidator()
		
		public Builder onInsertion(BiConsumer<Integer, ItemStack> onInsertion) 
		{
			this.m_onItemInsertion = onInsertion;
			return this;
		} // end slotValidator()
		
		public Builder onWithdrawal(BiConsumer<Integer, ItemStack> onWithdrawal) 
		{
			this.m_onItemWithdrawal = onWithdrawal;
			return this;
		} // end slotValidator()
		
		public InventoryWrapper build() 
		{
			return new InventoryWrapper(this.m_inventory, this.m_slots, this.m_validator, this.m_onItemInsertion, this.m_onItemWithdrawal);
		} // end build()
		
	} // end inner class
} // end class