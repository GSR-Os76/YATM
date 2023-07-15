package com.gsr.gsr_yatm.api.capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IDataHandler
{
	public @Nullable String getAddress();
	
	// public @Nullable DataPacket source(@Nullable DataRequest request);
	
	public void receive(@NotNull DataPacket packet);
	
	// public boolean isSource();
	
	public boolean isReciever();
	
} // end interface