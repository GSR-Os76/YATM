package com.gsr.gsr_yatm.block.device.extruder;

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

public class ExtruderBlock extends DeviceBlock
{
	private final int m_currentCapacity;
	private final int m_maxCurrent;



	public ExtruderBlock(Properties properties, VoxelShapeGetter shape, int currentCapacity, int maxCurrent)
	{
		super(properties, YATMBlockEntityTypes.EXTRUDER::get, shape);
		this.m_currentCapacity = currentCapacity;
		this.m_maxCurrent = maxCurrent;
	} // end constructor



	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		ExtruderBlockEntity blockEntity = (ExtruderBlockEntity) level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new ExtruderMenu(containerId, playerInventory, ContainerLevelAccess.create(level, blockPos), blockState.getBlock(), blockEntity.getInventory(), blockEntity.getDataAccessor()), Component.translatable(YATMLanguageProviderUnitedStatesEnglish.getTitleNameFor(YATMMenuTypes.EXTRUDER.get())));
	} // end getMenuProvider()

	@Override
	public DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new ExtruderBlockEntity(blockPos, blockState, this.m_currentCapacity, this.m_maxCurrent);
	} // end newDeviceBlockEntity()

} // end class