package com.gsr.gsr_yatm.block.device.extractor;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.IDeviceBlockEntity;
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

public class ExtractorBlock extends DeviceBlock
{
	public ExtractorBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.EXTRACTOR::get);
	} // end constructor
	
	

	@Override
	public @NotNull IDeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new ExtractorBlockEntity(position, state);
	} // end newDeviceBlockEntity()

	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{
		ExtractorBlockEntity ebe = (ExtractorBlockEntity)level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInv, player) -> new ExtractorMenu(
				containerId, 
				playerInv, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(), 
				ebe.getInventory(), 
				ebe.getDataAccessor()),
		YATMLanguageProvider.translatableFor(YATMMenuTypes.EXTRACTOR.get()));
	} // end getMenuProvider()
	
} // end class