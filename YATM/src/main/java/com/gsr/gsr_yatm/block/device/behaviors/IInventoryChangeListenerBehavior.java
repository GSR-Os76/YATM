package com.gsr.gsr_yatm.block.device.behaviors;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

public interface IInventoryChangeListenerBehavior extends IBehavior
{
	@NotNull List<@NotNegative Integer> getSlotIndices();
	
	void onSlotChanged(@NotNegative int slot);
} // end 