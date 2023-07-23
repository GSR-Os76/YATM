package com.gsr.gsr_yatm.armor;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IArmorSet
{
	public boolean isMember(@NotNull Item item);
	
	public void subscribeEffects(IEventBus eventBus);
	
} // end interface