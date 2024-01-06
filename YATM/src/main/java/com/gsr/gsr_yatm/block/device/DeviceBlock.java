package com.gsr.gsr_yatm.block.device;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public abstract class DeviceBlock extends ShapeBlock implements EntityBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	
	protected final Supplier<BlockEntityType<? extends IDeviceBlockEntity>> m_type;
	
	
	
	public DeviceBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, @NotNull Supplier<BlockEntityType<? extends IDeviceBlockEntity>> type)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		this.registerDefaultState(this.defaultBlockState().setValue(DeviceBlock.FACING, Direction.NORTH));
		this.m_type = Objects.requireNonNull(type);
	} // end constructor

	
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(DeviceBlock.FACING));
	} // createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(DeviceBlock.FACING, context.getHorizontalDirection().getOpposite());
	} // end getStateForPlacement()
	
	
	
	public abstract @NotNull IDeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state);
	
	@Override
	public final @NotNull BlockEntity newBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return this.newDeviceBlockEntity(position, state).self();
	} // end newBlockEntity()

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType)
	{
		return blockEntityType == this.m_type.get() ? (l, bp, bs, be) -> IDeviceBlockEntity.tick(l, bp, bs, (IDeviceBlockEntity)be) : null;
	} // end getTicker()

	
	
	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		if(!level.isClientSide && player instanceof ServerPlayer serverPlayer) 
		{
			serverPlayer.openMenu(state.getMenuProvider(level, position));
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	} // end use()

	@Override
	public abstract @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position);
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull BlockState toState, boolean dunno)
	{
		if(!state.is(toState.getBlock())) 
		{
			if(level.getBlockEntity(position) instanceof IDeviceBlockEntity dbe && level instanceof ServerLevel) 
			{
				InventoryUtil.drop(level, position, dbe.getDropInventory());
			}
		}
		super.onRemove(state, level, position, toState, dunno);
	} // end onRemove()

} // end class