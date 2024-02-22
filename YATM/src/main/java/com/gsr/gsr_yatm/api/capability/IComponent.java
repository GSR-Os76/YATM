package com.gsr.gsr_yatm.api.capability;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

@AutoRegisterCapability
public interface IComponent
{
	// the capability that receives forwarded whatever from the component, example: the device that's getting power from a slotted fuse
	// should throw error if invalid's attempt to be attached
	public <T> void attachReceivingCapability(@NotNull Capability<T> capType, @NotNull LazyOptional<T> cap);

	public void removeReceivingCapability(@NotNull LazyOptional<?> cap);

	
	
	public @NotNull List<Capability<?>> getValidCapabilities();
	
} // end interface