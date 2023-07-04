package com.gsr.gsr_yatm.block.device.solar;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProviderUnitedStatesEnglish;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.VoxelShapeGetter;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BatterySolarPanelBlock extends DeviceBlock
{
	private final int m_currentCapacity;
	private final int m_maxSafeCurrent;
	private final int m_maxCurrent;
	private final SolarPanelSettings m_settings;
	
	
	public BatterySolarPanelBlock(Properties properties, VoxelShapeGetter shape, int currentCapacity, int maxSafeCurrent, int maxCurrent, SolarPanelSettings settings)
	{
		super(properties, YATMBlockEntityTypes.BATTERY_SOLAR_PANEL::get, shape);
		this.m_currentCapacity = currentCapacity;
		this.m_maxSafeCurrent = maxSafeCurrent;
		this.m_maxCurrent = maxCurrent;
		this.m_settings = settings;
	} // end constructor

	
	
	@Override
	public DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new BatterySolarPanelBlockEntity(blockPos, blockState, this.m_currentCapacity, this.m_maxSafeCurrent, this.m_maxCurrent, this.m_settings);
	} // end newDeviceBlockEntity()

	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		BatterySolarPanelBlockEntity blockEntity = (BatterySolarPanelBlockEntity)level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new BatterySolarPanelMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, blockPos), 
				blockState.getBlock(),
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor())
				, 
				Component.translatable(YATMLanguageProviderUnitedStatesEnglish.getTitleNameFor(YATMMenuTypes.BATTERY_SOLAR_PANEL.get()))
				);
	} // end getMenuProvider()
	
} // end class