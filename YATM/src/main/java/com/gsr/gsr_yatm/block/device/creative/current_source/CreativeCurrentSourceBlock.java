package com.gsr.gsr_yatm.block.device.creative.current_source;

import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.block.device.AttachmentState;
import com.gsr.gsr_yatm.block.device.HorizontalDeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.IDeviceBlockEntity;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
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

public class CreativeCurrentSourceBlock extends HorizontalDeviceBlock
{
	public static final Map<Direction, EnumProperty<AttachmentState>> ATTACHMENT_STATE_BY_FACE = YATMBlockStateProperties.BRANCHES_BY_DIRECTION;
	
	
	
	public CreativeCurrentSourceBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.CREATIVE_CURRENT_SOURCE::get);

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

		return super.use(state, level, position, player, hand, hitResult);
	} // end use()
	
	@Override
	public @NotNull IDeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new CreativeCurrentSourceBlockEntity(position, state, Integer.MAX_VALUE);
	} // end newDeviceBlockEntity()

	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{
		CreativeCurrentSourceBlockEntity blockEntity = (CreativeCurrentSourceBlockEntity) level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CreativeCurrentSourceMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(), 
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.translatableForMenu(YATMMenuTypes.CREATIVE_CURRENT_SOURCE.get()));
	} // end getMenuProvider()
	
} // end class