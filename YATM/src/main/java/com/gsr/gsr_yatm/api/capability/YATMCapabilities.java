package com.gsr.gsr_yatm.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class YATMCapabilities
{
	public static final Capability<ICurrentHandler> CURRENT = CapabilityManager.get(new CapabilityToken<>(){});
	public static final Capability<IDamageableRepairableItemStack> DAMAGEABLE_REPAIRABLE = CapabilityManager.get(new CapabilityToken<>(){});
	public static final Capability<IDataHandler> DATA_HANDLER = CapabilityManager.get(new CapabilityToken<>(){});
	
} // end class