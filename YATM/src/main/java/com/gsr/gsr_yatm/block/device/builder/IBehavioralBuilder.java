package com.gsr.gsr_yatm.block.device.builder;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;

public interface IBehavioralBuilder
{
	<X extends IBehavior> List<X> getBehaviors(@NotNull Class<X> type);
	
	<X extends IBehavior> void addBehavior(@NotNull X behavior);

} // end class