package com.gsr.gsr_yatm.utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtil
{
	public static void drop(Level level, BlockPos position, NonNullList<ItemStack> drops)
	{
		Containers.dropContents(level, position, drops);
	} // end drop()
	
	public static void drop(Level level, BlockPos position, ItemStack drop)
	{
		Containers.dropContents(level, position, NonNullList.of(ItemStack.EMPTY, drop));
	} // end drop()
	
	public static void drop(Level level, BlockPos position, IItemHandler inventory)
	{
		for(int i = 0; i < inventory.getSlots(); i++) 
		{
			drop(level, position, inventory.extractItem(i, inventory.getSlotLimit(i), false));
		}
	} // end drop()

	public static void insertItemOrDrop(Level level, BlockPos position, IItemHandler inventory, int slot, ItemStack stack)
	{
		ItemStack r = inventory.insertItem(slot, stack, false);
		if(!r.isEmpty()) 
		{
			drop(level, position, r);
		}
	} // end insertItemOrDrop()
	
} // end class