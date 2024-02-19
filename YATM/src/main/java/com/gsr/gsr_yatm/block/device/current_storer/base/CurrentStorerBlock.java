package com.gsr.gsr_yatm.block.device.current_storer.base;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.IDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.OmniDirectionalDeviceBlock;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CurrentStorerBlock extends OmniDirectionalDeviceBlock
{

	public CurrentStorerBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, @NotNull Supplier<BlockEntityType<? extends IDeviceBlockEntity>> type)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), Objects.requireNonNull(type));
	} // end constructor
	
	

	@Override
	public @NotNull IDeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return this.m_type.get().create(position, state);
	} // end newDeviceBlockEntity()

	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{
		CurrentStorerBlockEntity blockEntity = (CurrentStorerBlockEntity) level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CurrentStorerMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(), 
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.translatableForMenu(this));
	} // end getMenuProvider()

} // end class