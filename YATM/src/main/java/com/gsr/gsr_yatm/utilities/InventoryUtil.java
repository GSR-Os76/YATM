package com.gsr.gsr_yatm.utilities;

import java.util.stream.IntStream;

import org.jetbrains.annotations.NotNull;

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
		InventoryUtil.drop(level, position, InventoryUtil.toNNList(inventory));
		
	} // end drop()

	public static void insertItemOrDrop(Level level, BlockPos position, IItemHandler inventory, int slot, ItemStack stack)
	{
		ItemStack r = inventory.insertItem(slot, stack, false);
		if(!r.isEmpty()) 
		{
			InventoryUtil.drop(level, position, r);
		}
	} // end insertItemOrDrop()

	
	
	public static @NotNull NonNullList<ItemStack> toNNList(@NotNull IItemHandler inv)
	{
		NonNullList<ItemStack> nl = NonNullList.createWithCapacity(inv.getSlots());
		IntStream.rangeClosed(0, inv.getSlots()).boxed().map((i) -> inv.getStackInSlot(i).copy()).forEach(nl::add);
		return nl;
	} // end toNNList()
	
} // end class