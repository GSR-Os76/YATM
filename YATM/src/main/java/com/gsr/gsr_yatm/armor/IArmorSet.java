package com.gsr.gsr_yatm.armor;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IArmorSet
{
	public boolean isMember(@NotNull ItemStack item);
	
	public void subscribeEffects(@NotNull IEventBus eventBus);
	
} // end interface