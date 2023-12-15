package com.gsr.gsr_yatm.block.device.builder;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

public interface IInventoryBuilder
{
	public @NotNull List<@NotNull SlotDefinition> getSlots();
	
	public void addSlot(@NotNull SlotDefinition slot);
	
	
	
	public default @NotNegative int nextIndex() 
	{
		return this.getSlots().size();
	} // end nextIndex()

	
} // end interface