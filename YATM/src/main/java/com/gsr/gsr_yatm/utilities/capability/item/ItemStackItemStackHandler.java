package com.gsr.gsr_yatm.utilities.capability.item;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

// very partially inspired by net.minecraftforge.items.ItemStackHandler
public class ItemStackItemStackHandler implements IItemHandler, IItemHandlerModifiable
{
	protected static final String INVENTORY_TAG_NAME = "inventory";
	
	protected final @NotNull ItemStack m_self;
    protected @NotNegative int m_size;

    
    
    public ItemStackItemStackHandler(@NotNull ItemStack self)
    {
        this(1, Objects.requireNonNull(self));
    } // end constructor

    public ItemStackItemStackHandler(@NotNegative int size, @NotNull ItemStack self)
    {
        this.m_size = Contract.notNegative(size);
        this.m_self = Objects.requireNonNull(self);
    } // end constructor



    @Override
    public @NotNegative int getSlots()
    {
        return this.m_size;
    } // end getSlots()
    
    @Override
    public @NotNull ItemStack getStackInSlot(int slot)
    {
    	Contract.inInclusiveRange(0, this.m_size - 1, slot, "The slot index: " + slot + ", is not valid.");
        CompoundTag tag = this.m_self.getOrCreateTag();
        if(!tag.contains(ItemStackItemStackHandler.INVENTORY_TAG_NAME) 
        		|| !tag.getCompound(ItemStackItemStackHandler.INVENTORY_TAG_NAME)
        		.contains(((Integer)slot).toString())) 
        {
        	this.populateTag(tag);
        }
        return ItemStack.of(tag.getCompound(ItemStackItemStackHandler.INVENTORY_TAG_NAME).getCompound(((Integer)slot).toString()));
    } // end getStackInSlot()
    
    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack)
    {
    	Contract.inInclusiveRange(0, this.m_size - 1, slot, "The slot index: " + slot + ", is not valid.");
    	CompoundTag tag = this.m_self.getOrCreateTag();
    	if(!tag.contains(ItemStackItemStackHandler.INVENTORY_TAG_NAME)) 
        {
        	this.populateTag(tag);
        }
    	tag.getCompound(ItemStackItemStackHandler.INVENTORY_TAG_NAME).put(((Integer)slot).toString(), stack.save(new CompoundTag()));
    } // end setStackInSlot()

    protected void populateTag(@NotNull CompoundTag tag) 
    {
    	CompoundTag inv;
    	if(tag.contains(ItemStackItemStackHandler.INVENTORY_TAG_NAME)) 
    	{ 
    		inv = tag.getCompound(ItemStackItemStackHandler.INVENTORY_TAG_NAME);
    	} 
    	else
    	{
    		inv = new CompoundTag();
    		tag.put(ItemStackItemStackHandler.INVENTORY_TAG_NAME, inv);
    	}
    	for(Integer i = 0; i < this.m_size; i++) 
    	{
    		if(!inv.contains(i.toString())) 
    		{
    			inv.put(i.toString(), ItemStack.EMPTY.save(new CompoundTag()));
    		}
    	}
    } // end populateTag()
    
    
    
    @Override    
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
    {
    	Contract.inInclusiveRange(0, this.m_size - 1, slot, "The slot index: " + slot + ", is not valid.");
        if (stack.isEmpty()) 
        {
            return ItemStack.EMPTY;
        }
        if (!this.isItemValid(slot, stack))
        {
        	return stack;
        }
        
        

        // stack merge considerations
        ItemStack existing = this.getStackInSlot(slot);
        int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());
        if (!existing.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) 
            {
                return stack;
            }
            limit -= existing.getCount();
            if (limit <= 0) 
	        {
	            return stack;
	        }
        }        

        boolean reachedLimit = stack.getCount() > limit;
        if (!simulate)
        {
            if (existing.isEmpty())
            {
            	this.setStackInSlot(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else
            {
                existing.grow(reachedLimit ? limit : stack.getCount());
                this.setStackInSlot(slot, existing);
            }
        }

        return reachedLimit
        		? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) 
        		: ItemStack.EMPTY;
    } // end insertItem()

    @Override    
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate)
    {
    	Contract.inInclusiveRange(0, this.m_size - 1, slot, "The slot index: " + slot + ", is not valid.");
        if (amount == 0) 
        {
            return ItemStack.EMPTY;
        }

        ItemStack existing = this.getStackInSlot(slot);
        if (existing.isEmpty()) 
        {
            return ItemStack.EMPTY;
        }

        int toExtract = Math.min(amount, existing.getMaxStackSize());
        if (existing.getCount() <= toExtract)
        {
            if (!simulate)
            {
                this.setStackInSlot(slot, ItemStack.EMPTY);
                return existing;
            }
            else
            {
                return existing.copy();
            }
        }
        else
        {
            if (!simulate)
            {
                this.setStackInSlot(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
            }
            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    } // end extractItem()

    @Override
    public int getSlotLimit(int slot)
    {
        return 64;
    } // end getSlotLimit()

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack)
    {
        return true;
    } // end isItemValid()

} // end class