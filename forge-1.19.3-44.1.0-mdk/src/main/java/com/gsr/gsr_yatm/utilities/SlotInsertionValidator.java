package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface SlotInsertionValidator
{
	public boolean validate(int slot, @NotNull ItemStack stack, boolean simulate);
} // end interface