package com.gsr.gsr_yatm.api;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface IComponent
{

	// the capability that recieves forwarded whatever from the component, example: the device that's getting power from a slotted fuse
	// should throw error if invalid's attempt to be attached
	public <T> void attachRecievingCapability(@NotNull ItemStack itemStack, @NotNull Capability<T> capType, @NotNull LazyOptional<T> cap);

	public @NotNull List<Capability<?>> getValidCapabilities();
	
} // end interface