package com.gsr.gsr_yatm.block.device.current_furnace;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class CurrentFurnaceBlock extends DeviceBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;

	private final DeviceTierConstants m_constants;
	

	
	public CurrentFurnaceBlock(Properties properties, ICollisionVoxelShapeProvider shape, DeviceTierConstants constants)
	{
		super(properties, YATMBlockEntityTypes.FURNACE_PLUS::get, shape);
		
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
		
		this.m_constants = constants;
		
	} // end constructor



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(LIT));
	} // createBlockStateDefinition()


	
	@Override
	public DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new CurrentFurnaceBlockEntity(blockPos, blockState, this.m_constants.maxTemperature());
	} // end newDeviceBlockEntity()

	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{		
		CurrentFurnaceBlockEntity blockEntity = (CurrentFurnaceBlockEntity)level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CurrentFurnaceMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, blockPos), 
				blockState.getBlock(),
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()),
		YATMLanguageProvider.getTranslatableTitleNameFor(YATMMenuTypes.CURRENT_FURNACE.get())
				);
	} // end getMenuProvider()

} // end class