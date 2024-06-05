package com.gsr.gsr_yatm.block.device.tank;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.IDeviceBlockEntity;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.UseUtilities;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class TankBlock extends DeviceBlock
{
	public static final BooleanProperty DRAINING = YATMBlockStateProperties.DRAINING;

	
	
	public TankBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.TANK::get);
		
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
		InteractionResult fluidExchangeResult = UseUtilities.tryFillOrDrainFromHeld(state, level, position, player, hand, hitResult);
		if(fluidExchangeResult.consumesAction()) 
		{
			return fluidExchangeResult;
		}
		return super.use(state, level, position, player, hand, hitResult);
	} // end use()

	 @Override
	 public @NotNull IDeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	 {
	 	return new TankBlockEntity(position, state);
	 } // end newDeviceBlockEntity()
	 	



	 @Override
	 public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	 {
		TankBlockEntity ebe = (TankBlockEntity)level.getBlockEntity(position);
	 	return new SimpleMenuProvider((containerId, playerInv, player) -> new TankMenu(
	 			containerId,
	 			playerInv, 
	 			ContainerLevelAccess.create(level, position), 
	 			state.getBlock(), 
	 			ebe.getInventory(), 
	 			ebe.getDataAccessor()),
	 	YATMLanguageProvider.translatableForMenu(YATMMenuTypes.TANK.get()));
	} // end getMenuProvider()
} // end class