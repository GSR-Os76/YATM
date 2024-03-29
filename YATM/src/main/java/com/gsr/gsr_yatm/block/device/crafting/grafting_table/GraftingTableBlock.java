package com.gsr.gsr_yatm.block.device.crafting.grafting_table;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class GraftingTableBlock extends ShapeBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	
	
	
	public GraftingTableBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		this.registerDefaultState(this.defaultBlockState().setValue(GraftingTableBlock.FACING, Direction.NORTH));
	} // end constructor

	
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(GraftingTableBlock.FACING));
	} // createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(GraftingTableBlock.FACING, context.getHorizontalDirection().getOpposite());
	} // end getStateForPlacement()

	
	
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
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{
		return new SimpleMenuProvider((containerId, playerInv, player) -> new GraftingMenu(
				containerId, 
				playerInv, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock()),
		YATMLanguageProvider.translatableForMenu(YATMMenuTypes.GRAFTING_TABLE.get())
				);
	} // end getMenuProvider
	
} // end class