package com.gsr.gsr_yatm.block.device.grinder;

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

public class GrinderBlock extends DeviceBlock
{
	// public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	
	private final int m_currentCapacity;
	private final int m_maxCurrent;
	
	
	
	public GrinderBlock(Properties properties, VoxelShapeGetter shape, int currentCapacity, int maxCurrent)
	{
		super(properties, YATMBlockEntityTypes.GRINDER::get, shape);
		// this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
		this.m_currentCapacity = currentCapacity;
		this.m_maxCurrent = maxCurrent;
	} // end constructor
	

	
	@Override
	public DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new GrinderBlockEntity(blockPos, blockState, this.m_currentCapacity, this.m_maxCurrent);
	} // end newDeviceBlockEntity
	
	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		GrinderBlockEntity ebe = (GrinderBlockEntity)level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInv, player) -> new GrinderMenu(containerId, playerInv, ContainerLevelAccess.create(level, blockPos), blockState.getBlock(), ebe.getInventory(), ebe.getDataAccessor())
				,
				Component.translatable(YATMLanguageProviderUnitedStatesEnglish.getTitleNameFor(YATMMenuTypes.GRINDER.get()))
				// Component.translatable("menu.title." + YetAnotherTechMod.MODID + "." + YATMMenuTypes.EXTRACTOR_MENU.getId().getPath())
				);
	} // end getMenuProvider()
	
} // end class