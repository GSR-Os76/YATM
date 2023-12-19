package com.gsr.gsr_yatm.block.device.builder.inventory.slot;

import java.util.Objects;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.item.ItemStack;

public record SlotDefinition(@NotNegative int index, @NotNull Predicate<@NotNull ItemStack> insertionValidator, @NotNull Predicate<@NotNull ItemStack> extractionValidator)
{
	public SlotDefinition(@NotNegative int index, @NotNull Predicate<@NotNull ItemStack> insertionValidator, @NotNull Predicate<@NotNull ItemStack> extractionValidator) 
	{
		this.index = Contract.notNegative(index);
		this.insertionValidator = Objects.requireNonNull(insertionValidator);
		this.extractionValidator = Objects.requireNonNull(extractionValidator);
	} // end constructor
	
} // end record