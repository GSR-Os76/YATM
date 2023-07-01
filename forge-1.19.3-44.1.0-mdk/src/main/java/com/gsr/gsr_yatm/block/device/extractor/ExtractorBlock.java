package com.gsr.gsr_yatm.block.device.extractor;

import com.gsr.gsr_yatm.YATMBlockStateProperties;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProviderUnitedStatesEnglish;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class ExtractorBlock extends Block implements EntityBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	
	
	
	public ExtractorBlock(Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	} // end constructor
	
	

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FACING));
	} // end createBlockStateDefinition()
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	} // end getStateForPlacement()

	

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new ExtractorBlockEntity(blockPos, blockState, 8192, 4000, 128, 32);
	} // end newBlockEntity()

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level levl, BlockState blockState, BlockEntityType<T> blockEntityType)
	{
		return blockEntityType == YATMBlockEntityTypes.EXTRACTOR.get() ? (l, bp, bs, be) -> ExtractorBlockEntity.tick(l, bp, bs, (ExtractorBlockEntity) be) : null;
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
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		ExtractorBlockEntity ebe = (ExtractorBlockEntity)level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInv, player) -> new ExtractorMenu(containerId, playerInv, ContainerLevelAccess.create(level, blockPos), blockState.getBlock(), ebe.getInventory(), ebe.getDataAccessor())
				,
				Component.translatable(YATMLanguageProviderUnitedStatesEnglish.getTitleNameFor(YATMMenuTypes.EXTRACTOR_MENU.get()))
				// Component.translatable("menu.title." + YetAnotherTechMod.MODID + "." + YATMMenuTypes.EXTRACTOR_MENU.getId().getPath())
				);
	} // end getMenuProvider()
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState fromBlockState, Level level, BlockPos blockPos, BlockState toBlockstate, boolean dunno)
	{
		if(!fromBlockState.is(toBlockstate.getBlock())) 
		{
			BlockEntity be = level.getBlockEntity(blockPos);
			if(be instanceof ExtractorBlockEntity bbe && level instanceof ServerLevel) 
			{
				bbe.blockBroken();
			}
		}
		super.onRemove(fromBlockState, level, blockPos, toBlockstate, dunno);;
	} // end onRemove()
	
} // end class