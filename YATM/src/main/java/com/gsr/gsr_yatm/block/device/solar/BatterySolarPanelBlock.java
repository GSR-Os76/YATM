package com.gsr.gsr_yatm.block.device.solar;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.HorizontalDeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BatterySolarPanelBlock extends HorizontalDeviceBlock
{
	private final int m_currentCapacity;
	private final int m_maxSafeCurrent;
	private final int m_maxCurrent;
	private final SolarPanelSettings m_settings;
	
	
	public BatterySolarPanelBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, int currentCapacity, int maxSafeCurrent, int maxCurrent, SolarPanelSettings settings)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.BATTERY_SOLAR_PANEL::get);
		this.m_currentCapacity = currentCapacity;
		this.m_maxSafeCurrent = maxSafeCurrent;
		this.m_maxCurrent = maxCurrent;
		this.m_settings = settings;
	} // end constructor

	
	
	@Override
	public @NotNull DeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new BatterySolarPanelBlockEntity(position, state, this.m_currentCapacity, this.m_maxSafeCurrent, this.m_maxCurrent, this.m_settings);
	} // end newDeviceBlockEntity()

	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{
		BatterySolarPanelBlockEntity blockEntity = (BatterySolarPanelBlockEntity)level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new BatterySolarPanelMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(),
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.translatableForMenu(YATMMenuTypes.BATTERY_SOLAR_PANEL.get()));
	} // end getMenuProvider()
	
} // end class