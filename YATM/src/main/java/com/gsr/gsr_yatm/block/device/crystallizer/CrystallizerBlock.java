package com.gsr.gsr_yatm.block.device.crystallizer;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

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

public class CrystallizerBlock extends DeviceBlock
{
	private final int m_tankCapacities;
	private final int m_maximumFluidTransferRate;
	
	
	
	public CrystallizerBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, int tankCapacities, int maximumFluidTransferRate)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.CRYSTALLIZER::get);
		this.m_tankCapacities = tankCapacities;
		this.m_maximumFluidTransferRate = maximumFluidTransferRate;
	} // end constructor



	@Override
	public @NotNull DeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new CrystallizerBlockEntity(position, state, this.m_tankCapacities, this.m_maximumFluidTransferRate);
	} // end newBlockEntity()
	
	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{
		CrystallizerBlockEntity blockEntity = (CrystallizerBlockEntity) level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CrystallizerMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(), 
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.translatableFor(YATMMenuTypes.CRYSTALLIZER.get()));
	} // end getMenuProvider()
	
} // end class