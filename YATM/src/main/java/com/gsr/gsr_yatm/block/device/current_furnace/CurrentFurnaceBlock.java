package com.gsr.gsr_yatm.block.device.current_furnace;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class CurrentFurnaceBlock extends DeviceBlock
{
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;


	
	public CurrentFurnaceBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.FURNACE_PLUS::get);
		this.registerDefaultState(this.defaultBlockState().setValue(CurrentFurnaceBlock.LIT, false));		
	} // end constructor



	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(LIT));
	} // createBlockStateDefinition()


	
	@Override
	public @NotNull DeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new CurrentFurnaceBlockEntity(position, state);
	} // end newDeviceBlockEntity()

	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{		
		CurrentFurnaceBlockEntity blockEntity = (CurrentFurnaceBlockEntity)level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CurrentFurnaceMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(),
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()),
		YATMLanguageProvider.translatableFor(YATMMenuTypes.CURRENT_FURNACE.get()));
	} // end getMenuProvider()

} // end class