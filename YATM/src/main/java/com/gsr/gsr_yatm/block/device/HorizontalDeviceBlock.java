package com.gsr.gsr_yatm.block.device;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public abstract class HorizontalDeviceBlock extends DeviceBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
		
	
	
	public HorizontalDeviceBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, @NotNull Supplier<BlockEntityType<? extends IDeviceBlockEntity>> type)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), Objects.requireNonNull(type));
		this.registerDefaultState(this.defaultBlockState().setValue(HorizontalDeviceBlock.FACING, Direction.NORTH));
	} // end constructor

	
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(HorizontalDeviceBlock.FACING));
	} // createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(HorizontalDeviceBlock.FACING, context.getHorizontalDirection().getOpposite());
	} // end getStateForPlacement()
	
} // end class