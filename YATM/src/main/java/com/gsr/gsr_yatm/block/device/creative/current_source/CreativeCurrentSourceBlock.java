package com.gsr.gsr_yatm.block.device.creative.current_source;

import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.block.device.AttachmentState;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
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
import net.minecraftforge.network.NetworkHooks;

public class CreativeCurrentSourceBlock extends ShapeBlock implements EntityBlock
{
	public static final Map<Direction, EnumProperty<AttachmentState>> ATTACHMENT_STATE_BY_FACE = YATMBlockStateProperties.BRANCHES_BY_DIRECTION;
	
	
	
	public CreativeCurrentSourceBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));

		BlockState d = this.defaultBlockState();	
		for(EnumProperty<AttachmentState> p : CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.values()) 
		{
			d = d.setValue(p, AttachmentState.PUSH);
		}
		this.registerDefaultState(d);
	} // end constructor
	
	
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		for(EnumProperty<AttachmentState> p : CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.values()) 
		{
			builder.add(p);
		}
		super.createBlockStateDefinition(builder);
	} // end createBlockStateDefinition()

	@Override
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult)
	{
		// TODO, Create UseUtil utility method for attachment adjustment
//		if(player.getItemInHand(hand).is(YATMItemTags.DEVICE_ADJUSTERS_KEY)) 
//		{
//			return InteractionResult.sidedSuccess(level.isClientSide);
//		}

		if(!level.isClientSide && player instanceof ServerPlayer serverPlayer) 
		{
			NetworkHooks.openScreen(serverPlayer, state.getMenuProvider(level, position));
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	} // end use()
	
	
	
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new CreativeCurrentSourceBlockEntity(position, state, Integer.MAX_VALUE);
	} // end newBlockEntity()

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
	{
		return type == YATMBlockEntityTypes.CREATIVE_CURRENT_SOURCE.get() ? (l, bp, bs, be) -> CreativeCurrentSourceBlockEntity.tick(l, bp, bs, (CreativeCurrentSourceBlockEntity)be) : null;
	} // end getTicker()
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(@NotNull BlockState fromState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState toState, boolean dunno)
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
	
	@Override
	public MenuProvider getMenuProvider(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos)
	{
		CreativeCurrentSourceBlockEntity blockEntity = (CreativeCurrentSourceBlockEntity) level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CreativeCurrentSourceMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, blockPos), 
				blockState.getBlock(), 
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.translatableFor(YATMMenuTypes.CREATIVE_CURRENT_SOURCE.get()));
	} // end getMenuProvider()
} // end class