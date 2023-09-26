package com.gsr.gsr_yatm.block;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.InventoryUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;

public interface IHarvestableBlock
{
	public default boolean allowEventHandling() 
	{
		return true;
	} // end expectsEventHandling()
	
	// should be called by harvesters after all other logic is completed.
	public default void onHarvest(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action) 
	{
		
	} // end onHarvest()	
	
	// should return a list of all the ToolActions that're able to be used on the provided state and situation
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position);
	
	// should cause not state changes
	public boolean isHarvestable(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action);
	
	// should cause not state changes
	// null result means action failed, or wasn't performable. should never return null immediately after isHarvestable returned true.
	public @Nullable BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action);	
	
	// should cause not state changes
	// null result means action failed, or wasn't performable
	public @Nullable NonNullList<ItemStack> getResults(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action);	
	
	
	
	// since BlockToolModificationEvent isn't raised consistently for all the ToolActions, e.g. the ToolActions.SHEARS_HARVEST, this default use implementation can be utilized instead when a block is interacted with by a player
	public static InteractionResult use(IHarvestableBlock harvestable, Level level, BlockState state, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		ItemStack held = player.getItemInHand(hand);
		for(ToolAction usable : harvestable.validActions(level, state, position)) 
		{
			if(((usable == null && held.isEmpty()) || (held.canPerformAction(usable))) && harvestable.isHarvestable(level, state, position, usable)) 
			{
				if(!level.isClientSide) 
				{				
					InventoryUtilities.drop(level, position, harvestable.getResults(level, state, position, usable));
					level.setBlock(position, harvestable.getResultingState(level, state, position, usable), 3);
				}
				harvestable.onHarvest(level, state, position, usable);
				return InteractionResult.sidedSuccess(level.isClientSide);
			}			
		}
		return InteractionResult.PASS;
	} // end use()

	
} // end interface