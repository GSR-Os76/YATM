package com.gsr.gsr_yatm.block.device.crystallizer;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.VoxelShapeGetter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CrystallizerBlock extends DeviceBlock
{
	private final int m_tankCapacities;
	private final int m_maximumFluidTransferRate;
	
	
	
	public CrystallizerBlock(Properties properties, VoxelShapeGetter shape, int tankCapacities, int maximumFluidTransferRate)
	{
		super(properties, YATMBlockEntityTypes.CRYSTALLIZER::get, shape);
		this.m_tankCapacities = tankCapacities;
		this.m_maximumFluidTransferRate = maximumFluidTransferRate;
	} // end constructor



	@Override
	public DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new CrystallizerBlockEntity(blockPos, blockState, this.m_tankCapacities, this.m_maximumFluidTransferRate);
	} // end newBlockEntity()
	
	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		CrystallizerBlockEntity blockEntity = (CrystallizerBlockEntity) level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CrystallizerMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, blockPos), 
				blockState.getBlock(), 
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.getTranslatableTitleNameFor(YATMMenuTypes.CRYSTALLIZER.get()));
	} // end getMenuProvider()
	
} // end class