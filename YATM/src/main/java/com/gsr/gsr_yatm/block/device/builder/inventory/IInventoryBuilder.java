package com.gsr.gsr_yatm.block.device.builder.inventory;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;
import com.gsr.gsr_yatm.block.device.builder.inventory.slot.ISlotBuilder;

public interface IInventoryBuilder<T> extends IBuilder<T>
{
	
	@NotNull ISlotBuilder<? extends IInventoryBuilder<T>> slot();
		
} // end interface