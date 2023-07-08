package com.gsr.gsr_yatm.utilities;

import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface ItemInsertionEventHandler
{
	public void onItemInsertion(int slot, ItemStack itemStack);
} // end interface