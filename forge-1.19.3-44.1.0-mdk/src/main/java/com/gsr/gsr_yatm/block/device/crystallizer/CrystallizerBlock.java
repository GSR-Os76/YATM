package com.gsr.gsr_yatm.block.device.crystallizer;

import com.gsr.gsr_yatm.YATMBlockStateProperties;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProviderUnitedStatesEnglish;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.VoxelShapeGetter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
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

public class CrystallizerBlock extends Block implements EntityBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING;
	
	private final VoxelShapeGetter m_shape;
	private final int m_tankCapacities;
	private final int m_maximumFluidTransferRate;
	
	
	
	public CrystallizerBlock(Properties properties, VoxelShapeGetter shape, int tankCapacities, int maximumFluidTransferRate)
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
		this.m_shape = shape;
		this.m_tankCapacities = tankCapacities;
		this.m_maximumFluidTransferRate = maximumFluidTransferRate;
	} // end constructor



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	} // createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext)
	{
		return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
	} // end getStateForPlacement()



	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
	{
		if (!level.isClientSide && player instanceof ServerPlayer serverPlayer)
		{
			NetworkHooks.openScreen(serverPlayer, blockState.getMenuProvider(level, blockPos));
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	} // end use()

	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		CrystallizerBlockEntity blockEntity = (CrystallizerBlockEntity) level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> 
		new CrystallizerMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, blockPos), 
				blockState.getBlock(), 
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		Component.translatable(YATMLanguageProviderUnitedStatesEnglish.getTitleNameFor(YATMMenuTypes.CRYSTALLIZER_MENU.get())));
	} // end getMenuProvider()



	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new CrystallizerBlockEntity(blockPos, blockState, this.m_tankCapacities, this.m_maximumFluidTransferRate);
	} // end newBlockEntity()

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
	{
		return blockEntityType == YATMBlockEntityTypes.CRYSTALLIZER.get() ? (l, bp, bs, be) -> CrystallizerBlockEntity.tick(l, bp, bs, (CrystallizerBlockEntity) be) : null;
	} // end getTicker()



	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{
		// TODO Auto-generated method stub
		return super.getShape(blockState, blockGetter, blockPos, context);
	}

	
	
	
	
	
} // end class