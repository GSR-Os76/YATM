package com.gsr.gsr_yatm.block.device.crucible;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
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

public class CrucibleBlock extends DeviceBlock
{
	private final @NotNull DeviceTierConstants m_constants;
	
	
	
	public CrucibleBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, @NotNull DeviceTierConstants constants)
	{
		super(Objects.requireNonNull(properties), YATMBlockEntityTypes.CRUCIBLE::get, Objects.requireNonNull(shape));
		this.m_constants = Objects.requireNonNull(constants);
	} // end constructor



	@Override
	public DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new CrucibleBlockEntity(blockPos, blockState, this.m_constants);
	} // end newBlockEntity()
	
	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		CrucibleBlockEntity blockEntity = (CrucibleBlockEntity) level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CrucibleMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, blockPos), 
				blockState.getBlock(), 
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.getTranslatableTitleNameFor(YATMMenuTypes.CRUCIBLE.get()));
	} // end getMenuProvider()
	
} // end class