package com.gsr.gsr_yatm.block.device.solar;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

public record SolarPanelSettings(@NotNegative int outputRecheckPeriod, @NotNegative int maxCurrentTransfer, @NotNegative int currentCapacity, int currentPerTick, float percentWithoutSky, float percentDuringDay, float percentDuringNight, float percentDuringPrecipitation)
{

} // end record