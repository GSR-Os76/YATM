package com.gsr.gsr_yatm.block.device.tank;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.UseUtilities;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class TankBlock extends ShapeBlock implements EntityBlock
{
	public static final BooleanProperty DRAINING = YATMBlockStateProperties.DRAINING;

	
	
	public TankBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(TankBlock.DRAINING, false));
	} // end constructor()



	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(TankBlock.DRAINING));
	} // end createBlockStateDefinition()



	@Override
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult)
	{
		if(player.getItemInHand(hand).is(YATMItemTags.DEVICE_ADJUSTERS_KEY)) 
		{
			level.setBlock(position, state.cycle(TankBlock.DRAINING), Block.UPDATE_CLIENTS);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		
		return UseUtilities.tryFillOrDrainFromHeld(state, level, position, player, hand, hitResult);
	} // end use()

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos position, Block changeFrom, BlockPos changePosition, boolean p_60514_)
	{
		if(position.below().equals(changePosition)) 
		{
			((TankBlockEntity)level.getBlockEntity(position)).recheckDrainAttachment(level, position, state);;
		}
	} // end neighborChanged()



	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new TankBlockEntity(position, state);
	} // end newBlockEntity

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
	{
		return type == YATMBlockEntityTypes.TANK.get() ? (l, bp, bs, be) -> TankBlockEntity.tick(l, bp, bs, (TankBlockEntity)be) : null;
	} // end getTicker()
	
} // end class