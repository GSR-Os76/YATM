package com.gsr.gsr_yatm.block.device.grinder;

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

public class GrinderBlock extends DeviceBlock
{
	private final int m_currentCapacity;
	private final int m_maxCurrent;
	
	
	
	public GrinderBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, int currentCapacity, int maxCurrent)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.GRINDER::get);
		this.m_currentCapacity = currentCapacity;
		this.m_maxCurrent = maxCurrent;
	} // end constructor
	

	
	@Override
	public @NotNull DeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new GrinderBlockEntity(position, state, this.m_currentCapacity, this.m_maxCurrent);
	} // end newDeviceBlockEntity()
	
	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{
		GrinderBlockEntity ebe = (GrinderBlockEntity)level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInv, player) -> new GrinderMenu(
				containerId,
				playerInv, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(), 
				ebe.getInventory(), 
				ebe.getDataAccessor()),
		YATMLanguageProvider.translatableFor(YATMMenuTypes.GRINDER.get()));
	} // end getMenuProvider()
	
} // end class