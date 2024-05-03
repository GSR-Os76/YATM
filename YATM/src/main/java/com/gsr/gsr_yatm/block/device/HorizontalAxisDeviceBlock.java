package com.gsr.gsr_yatm.block.device;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;

	
public abstract class HorizontalAxisDeviceBlock extends DeviceBlock
{
	public static final EnumProperty<Axis> AXIS = YATMBlockStateProperties.AXIS_HORIZONTAL;
		
	
	
	public HorizontalAxisDeviceBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, @NotNull Supplier<BlockEntityType<? extends IDeviceBlockEntity>> type)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), Objects.requireNonNull(type));
		this.registerDefaultState(this.defaultBlockState().setValue(HorizontalAxisDeviceBlock.AXIS, Direction.Axis.Z));
	} // end constructor

	
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(HorizontalAxisDeviceBlock.AXIS));
	} // createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(HorizontalAxisDeviceBlock.AXIS, context.getHorizontalDirection().getAxis());
	} // end getStateForPlacement()
	
} // end class
