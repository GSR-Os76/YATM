package com.gsr.gsr_yatm.block.device;

import java.util.function.Supplier;

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
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public abstract class DeviceBlock extends Block implements EntityBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	
	protected final Supplier<BlockEntityType<? extends DeviceBlockEntity>> m_type;
	protected final ICollisionVoxelShapeProvider m_shape;
	
	
	
	public DeviceBlock(Properties properties, Supplier<BlockEntityType<? extends DeviceBlockEntity>> type, ICollisionVoxelShapeProvider shape)
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
		this.m_type = type;
		this.m_shape = shape;
	} // end constructor

	
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FACING));
	} // createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext)
	{
		return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
	} // end getStateForPlacement()
	
	
	
	public abstract DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState);
	
	@Override
	public final BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return this.newDeviceBlockEntity(blockPos, blockState);
	} // end newBlockEntity()

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
	{
		return blockEntityType == this.m_type.get() ? (l, bp, bs, be) -> DeviceBlockEntity.tick(l, bp, bs, (DeviceBlockEntity)be) : null;
	} // end getTicker()

	
	
	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
	{
		if(!level.isClientSide && player instanceof ServerPlayer serverPlayer) 
		{
			NetworkHooks.openScreen(serverPlayer, blockState.getMenuProvider(level, blockPos));
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	} // end use()

	@Override
	public abstract MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos);
	
	
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState fromState, Level level, BlockPos blockPos, BlockState toState, boolean dunno)
	{
		if(!fromState.is(toState.getBlock())) 
		{
			BlockEntity be = level.getBlockEntity(blockPos);
			if(be instanceof DeviceBlockEntity dbe && level instanceof ServerLevel) 
			{
				dbe.blockBroken();
			}
		}
		super.onRemove(fromState, level, blockPos, toState, dunno);;
	} // end onRemove()

} // end class