package com.gsr.gsr_yatm.block.device.builder.inventory.slot;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;

import net.minecraft.world.item.ItemStack;

public interface ISlotBuilder<T> extends IBuilder<T>
{

	@NotNull ISlotBuilder<T> insertionValidator(Predicate<ItemStack> validator);

	@NotNull ISlotBuilder<T> extractionValidator(Predicate<ItemStack> validator);

} // end interface