package com.gsr.gsr_yatm.block.device.solar.panel;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelSettings;
import com.gsr.gsr_yatm.block.device.solar.panel.base.SolarPanelBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedSolarPanelBlockEntity extends SolarPanelBlockEntity
{

	public AdvancedSolarPanelBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.ADVANCED_SOLAR_PANEL.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	
	
	@Override
	public @NotNull SolarPanelSettings getSettings()
	{
		return YATMConfigs.ADVANCED_SOLAR_PANEL_SETTINGS.get();
	} // end getSettings()

} // end class