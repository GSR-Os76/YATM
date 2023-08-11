package com.gsr.gsr_yatm.block.device.bioler;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
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

public class BiolerBlock extends DeviceBlock
{
	private final int m_currentCapacity;
	private final int m_maxCurrent;
	private final int m_fluidCapacity;
	private final int m_maxFluidTransferRate;
	
	
	
	public BiolerBlock(Properties properties, ICollisionVoxelShapeProvider shape, int currentCapacity, int maxCurrent, int fluidCapacity, int maxFluidTransferRate)
	{
		super(properties, YATMBlockEntityTypes.BIOLER::get, shape);
		this.m_currentCapacity = currentCapacity;
		this.m_maxCurrent = maxCurrent;
		this.m_fluidCapacity = fluidCapacity;
		this.m_maxFluidTransferRate = maxFluidTransferRate;
	} // end constructor

	@Override
	public DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new BiolerBlockEntity(blockPos, blockState, this.m_currentCapacity, this.m_maxCurrent, this.m_fluidCapacity, this.m_maxFluidTransferRate);
	} // end newDeviceBlockEntity()

	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		BiolerBlockEntity blockEntity = (BiolerBlockEntity)level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new BiolerMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, blockPos), 
				blockState.getBlock(),
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.getTranslatableTitleNameFor(YATMMenuTypes.BIOLER.get()));
	} // end getMenuProvider()

} // end class