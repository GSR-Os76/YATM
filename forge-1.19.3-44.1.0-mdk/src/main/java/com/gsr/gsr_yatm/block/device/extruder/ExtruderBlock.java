package com.gsr.gsr_yatm.block.device.extruder;

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

public class ExtruderBlock extends Block implements EntityBlock
{
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);



	public ExtruderBlock(Properties properties)
	{
		super(properties);
		// this.defaultBlockState().setValue(FACING, Direction.NORTH);
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
			//ForgeRegistries.ITEMS.tags().forEach((t) -> YetAnotherTechMod.LOGGER.info("found a tag: here's its signature: " + t.toString()));
			NetworkHooks.openScreen(serverPlayer, blockState.getMenuProvider(level, blockPos));
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	} // end use()



	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		ExtruderBlockEntity blockEntity = (ExtruderBlockEntity) level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new ExtruderMenu(containerId, playerInventory, ContainerLevelAccess.create(level, blockPos), blockState.getBlock(), blockEntity.getInventory(), blockEntity.getDataAccessor()), Component.translatable("menu.title." + YetAnotherTechMod.MODID + "." + YATMMenuTypes.EXTRUDER_MENU.getId().getPath()));
		} // end getMenuProvider()



	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new ExtruderBlockEntity(blockPos, blockState);
	} // end newBlockEntity()

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
	{
		return blockEntityType == YATMBlockEntityTypes.EXTRUDER_BLOCK_ENTITY.get() ? (l, bp, bs, be) -> ExtruderBlockEntity.tick(l, bp, bs, (ExtruderBlockEntity) be) : null;
	} // end getTicker()

} // end class

//RecipeUtilities.getTag("gsr_yatm:dies/wire").forEach((i) -> YetAnotherTechMod.LOGGER.info("die tag has an item: " + i));

// level.getRecipeManager().getAllRecipesFor(YetAnotherTechMod.EXTRUSION_RECIPE_TYPE.get()).forEach((er)
// -> er.tryPrintTags());