package com.gsr.gsr_yatm.block.device;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface IInventoryDroppingBlockEntity extends IBlockEntity
{
	@NotNull NonNullList<ItemStack> getDropInventory();

} // end interface