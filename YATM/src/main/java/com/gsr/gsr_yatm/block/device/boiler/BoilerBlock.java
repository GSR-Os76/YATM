package com.gsr.gsr_yatm.block.device.boiler;

import com.gsr.gsr_yatm.YATMBlockStateProperties;
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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class BoilerBlock extends DeviceBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	public static final BooleanProperty HAS_TANK = BooleanProperty.create("has_tank");
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;

	private final int m_maximumTemperature;
	private final int m_tankCapacities;
	private final int m_maximumFluidTransferRate;
	

	
	public BoilerBlock(Properties properties, VoxelShapeGetter shape, int maximumTemperature, int tankCapacities, int maximumFluidTransferRate)
	{
		super(properties, YATMBlockEntityTypes.BOILER::get, shape);
		
		this.registerDefaultState(this.defaultBlockState().setValue(HAS_TANK, false).setValue(LIT, false));
		
		this.m_maximumTemperature = maximumTemperature;
		this.m_tankCapacities = tankCapacities;
		this.m_maximumFluidTransferRate = maximumFluidTransferRate;
		
	} // end constructor



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(HAS_TANK, LIT));
	} // createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext)
	{
		Block above = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().above()).getBlock();
		return super.getStateForPlacement(blockPlaceContext).setValue(HAS_TANK, above instanceof BoilerTankBlock);
	} // end getStateForPlacement()

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState blockState, Level level, BlockPos thissPos, Block p_60512_, BlockPos p_60513_, boolean p_60514_)
	{
		boolean hasTank = level.getBlockState(thissPos.above()).getBlock() instanceof BoilerTankBlock;
		if (hasTank != blockState.getValue(HAS_TANK))
		{
			level.setBlockAndUpdate(thissPos, blockState.setValue(HAS_TANK, hasTank));
		}
		
		super.neighborChanged(blockState, level, thissPos, p_60512_, p_60513_, p_60514_);
	} // end onNeighborChange()

	
	
	@Override
	public DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new BoilerBlockEntity(blockPos, blockState, this.m_maximumTemperature, this.m_tankCapacities, this.m_maximumFluidTransferRate);
	} // end newBlockEntity()

	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{		
		BoilerBlockEntity blockEntity = (BoilerBlockEntity)level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new BoilerMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, blockPos), 
				blockState.getBlock(),
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor())
				, 
				Component.translatable(YATMLanguageProviderUnitedStatesEnglish.getTitleNameFor(YATMMenuTypes.BOILER.get()))
				);
	} // end getMenuProvider()

} // end class