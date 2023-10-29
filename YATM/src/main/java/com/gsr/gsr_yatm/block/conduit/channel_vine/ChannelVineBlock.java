package com.gsr.gsr_yatm.block.conduit.channel_vine;

import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ChannelVineBlock extends ShapeBlock implements EntityBlock
{
	public static final Map<Direction, EnumProperty<AttachmentState>> BRANCHES_BY_DIRECTION = YATMBlockStateProperties.BRANCHES_BY_DIRECTION;

	
	
	public ChannelVineBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		BlockState d = this.defaultBlockState();
		for(EnumProperty<AttachmentState> b : ChannelVineBlock.BRANCHES_BY_DIRECTION.values()) 
		{
			d.setValue(b, AttachmentState.NONE);
		}
		this.registerDefaultState(d);
		
	} // end constructor



	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		for(EnumProperty<AttachmentState> b : ChannelVineBlock.BRANCHES_BY_DIRECTION.values()) 
		{
			builder.add(b);
		}
		super.createBlockStateDefinition(builder);
	} // end createBlockStateDefinition()

	
	@Override	 
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult)
	{
//		if(player.getItemInHand(hand).is(YATMItemTags.DEVICE_ADJUSTERS_KEY)) 
//		{
//			AABB bb = new AABB(x, y, z, maxX, maxY, maxZ);
//			
//			AABB.ofSize(position.getCenter(), jumpFactor, friction, explosionResistance)
//			detected collision with specifc branchs, and toggle state as allowed
//		
//			level.setBlock(position, state.cycle(TankBlock.DRAINING), Block.UPDATE_CLIENTS);
//			return InteractionResult.sidedSuccess(level.isClientSide);
//		}
//		
		return InteractionResult.PASS;
	} // end use()

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos position, Block changeFrom, BlockPos changePosition, boolean p_60514_)
	{		
		((ChannelVinesBlockEntity)level.getBlockEntity(position)).recheckNeighborAttachments(level, position, state);
	} // end neighborChanged()



	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new ChannelVinesBlockEntity(position, state);//, this.m_constants);
	} // end newBlockEntity

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
	{
		return type == YATMBlockEntityTypes.CHANNEL_VINES.get() ? (l, bp, bs, be) -> ChannelVinesBlockEntity.tick(l, bp, bs, (ChannelVinesBlockEntity)be) : null;
	} // end getTicker()

} // end class