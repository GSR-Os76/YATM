package com.gsr.gsr_yatm.block;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.InventoryUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

public interface IHarvestableBlock
{
	public default boolean allowEventHandling() 
	{
		return false;
	} // end allowEventHandling()
	
	// should be called by harvesters after all other logic is completed.
	public default void onHarvest(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action) 
	{
		
	} // end onHarvest()	
	
	// could these first two not be consolidated?
	// should return a list of all the ToolActions that're able to be used on the provided state and situation
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position);
	
	@Deprecated(forRemoval=true)
	public default boolean isHarvestable(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return this.validActions(level, state, position).contains(action);
	} // end isHarvestable()
	
	// should cause not state changes
	// null result means action failed, or wasn't performable. should never return null immediately after isHarvestable returned true.
	public @Nullable BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action);	
	
	// should cause not state changes
	// null result means action failed, or wasn't performable
	public @Nullable NonNullList<ItemStack> getResults(@NotNull ServerLevel level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action);	
	
	public static InteractionResult use(@NotNull IHarvestableBlock harvestable, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull Player player, @NotNull InteractionHand hand)
	{
		ItemStack held = player.getItemInHand(hand);
		for(ToolAction usable : harvestable.validActions(level, state, position)) 
		{
			if((usable == null && held.isEmpty()) || held.canPerformAction(usable)) 
			{
				if(level instanceof ServerLevel sl) 
				{				
					InventoryUtil.drop(level, position, harvestable.getResults(sl, state, position, usable));
					level.setBlock(position, harvestable.getResultingState(level, state, position, usable), 3);
				}
				harvestable.onHarvest(level, state, position, usable);
				return InteractionResult.sidedSuccess(level.isClientSide);
			}			
		}
		return InteractionResult.PASS;
	} // end use()

} // end interface