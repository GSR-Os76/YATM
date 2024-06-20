package com.gsr.gsr_yatm.block;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.InventoryUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

// TODO, make usage registry and event based for better cooperablility
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
	

	
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position);
	
	public @NotNull BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action);	
	
	public @NotNull NonNullList<ItemStack> getResults(@NotNull ServerLevel level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action);	
	
	
	
	public static ItemInteractionResult useItemOn(@NotNull IHarvestableBlock harvestable, @NotNull ItemStack held, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
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
				return ItemInteractionResult.sidedSuccess(level.isClientSide);
			}			
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	} // end use()

} // end interface