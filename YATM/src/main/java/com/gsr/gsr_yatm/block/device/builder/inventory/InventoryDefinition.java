package com.gsr.gsr_yatm.block.device.builder.inventory;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.inventory.slot.SlotDefinition;

public record InventoryDefinition(@NotNull List<SlotDefinition> slots)
{
	public InventoryDefinition(@NotNull List<SlotDefinition> slots) 
	{
		this.slots = Objects.requireNonNull(slots);
	} // end constructor
	
} // end record