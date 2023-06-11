package com.gsr.gsr_yatm.block.device.boiler;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.YATMMenuTypes;
import com.gsr.gsr_yatm.YetAnotherTechMod;

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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class BoilerBlock extends Block implements EntityBlock //IForgeBlockState
{
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
	public static final BooleanProperty HAS_TANK = BooleanProperty.create("has_tank");
	public static final BooleanProperty LIT = BooleanProperty.create("lit");

	private static final VoxelShape BASE_SHAPE = Block.box(0, 0, 0, 16, 14, 16);



	public BoilerBlock(Properties p_49795_)
	{
		super(p_49795_);
		this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HAS_TANK, Boolean.FALSE).setValue(LIT, Boolean.FALSE);
	} // end constructor



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(FACING).add(HAS_TANK).add(LIT);
		super.createBlockStateDefinition(builder);
	} // createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext)
	{
		Block above = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().above()).getBlock();// .offset(Direction.UP.getNormal())).getBlock();
		Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();
		return this.defaultBlockState().setValue(FACING, direction).setValue(HAS_TANK, above instanceof BoilerTankBlock).setValue(LIT, false);
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
	public VoxelShape getShape(BlockState blockState, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
	{		// blockState.getValue(HAS_TANK) ? Shapes.join(BASE_SHAPE, HAS_TANK_MULTIPART_SHAPE, BooleanOp.AND) : 
		return BASE_SHAPE;
	} // end getShape()



	
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
				, Component.translatable("menu.title." + YetAnotherTechMod.MODID + "." + YATMMenuTypes.BOILER_MENU.getId().getPath()));//, null, null, null, null));
	} // end getMenuProvider()



	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new BoilerBlockEntity(blockPos, blockState);
	} // end newBlockEntity()

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
	{
		return blockEntityType == YATMBlockEntityTypes.BOILER_BLOCK_ENTITY.get() ? (l, bp, bs, be) -> BoilerBlockEntity.tick(l, bp, bs, (BoilerBlockEntity)be) : null;
	} // end getTicker()



	
	

} // end class