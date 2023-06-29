// fungi aren't plant's in a taxonomical sense, i just sort them here for convenience and organization, a raname would be apt
package com.gsr.gsr_yatm.block.plant.fungi;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlockStateProperties;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class PhantasmalShelfFungiBlock extends Block
{
	// so 3 stages
	public static final int MAX_GROWTH_STAGE = 2;
	
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	public static final IntegerProperty GROWTH_STAGE = IntegerProperty.create("growth_stage", 0, MAX_GROWTH_STAGE);
	
	
	
	public PhantasmalShelfFungiBlock(Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(GROWTH_STAGE, 0));
	} // end constructor



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FACING, GROWTH_STAGE));
	} // end createBlockStateDefinition()
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	} // end getStateForPlacement()



	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random)
	{
		if(random.nextInt(20) == 0) 
		{
			this.grow(serverLevel, blockState, blockPos);
		}
	} // end randomTick()



	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		ItemStack usdItm = player.getItemInHand(hand);
		if(YATMItemTags.SPREADS_PHANTASMAL_SHELF_FUNGI.contains(usdItm.getItem())) 
		{
			if(!level.isClientSide && level instanceof ServerLevel sLevel) 
			{
				if(this.spread(sLevel, blockState, blockPos, RandomSource.create())) 
				{
					usdItm.shrink(1);
				}
			}
		}
		if(YATMItemTags.GROWS_PHANTASMAL_SHELF_FUNGI.contains(usdItm.getItem())) 
		{
			if(!level.isClientSide && level instanceof ServerLevel sLevel) 
			{
				if(this.grow(sLevel, blockState, blockPos)) 
				{
					usdItm.shrink(1);
				}
			}
		}
		// TODO, change this around to be better conditional perhaps so
		return InteractionResult.sidedSuccess(level.isClientSide);
	} // end use()

	
	
	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
	{
		if (toolAction == ToolActions.SHEARS_HARVEST && state.getValue(GROWTH_STAGE) == MAX_GROWTH_STAGE)
		{
			if(!simulate) 
			{
				InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(Items.PHANTOM_MEMBRANE));
			}
			return state.setValue(GROWTH_STAGE, 0);
		}
		return super.getToolModifiedState(state, context, toolAction, simulate);
	} // end getToolModifierState()



	public boolean grow(ServerLevel level, BlockState blockState, BlockPos blockPos) 
	{
		if(blockState.getValue(GROWTH_STAGE) != MAX_GROWTH_STAGE) 
		{
			level.setBlock(blockPos, blockState.cycle(GROWTH_STAGE), UPDATE_ALL);
			return true;
		}
		// TODO, maybe add a rule to allow for naturally spreading, and use it in an else here
		return false;
	} // end grow()
	
	public boolean spread(ServerLevel level, BlockState blockState, BlockPos blockPos, RandomSource random) 
	{
		if(blockState.getValue(GROWTH_STAGE) == MAX_GROWTH_STAGE) 
		{
			YetAnotherTechMod.LOGGER.info("trying to spread");
			// TODO, find surrounding surfaces to use randomly so, spread to those surfaces a yound shelf
			/*if(blockState.getValue(GROWTH_STAGE) != MAX_GROWTH_STAGE) 
			{
				level.setBlock(blockPos, blockState.cycle(GROWTH_STAGE), UPDATE_ALL);
			}*/
			// maybe add a rule to allow naturally spreading, and use it in an else here
			return true;
		}
		return false;
	} // end spread()
	
} // end class