package com.gsr.gsr_yatm.block.plant.carcass_root;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.IBlockForPlacementByBlockSupplier;
import com.gsr.gsr_yatm.block.IOccasionallySpreadableBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CarcassRootRootSupplier implements IBlockForPlacementByBlockSupplier
{

	@Override
	public @Nullable BlockState get(@NotNull Level placerLevel, @NotNull BlockState placerState, @NotNull BlockPos placerPosition, @NotNull Level placingLevel, @NotNull BlockPos placingPosition)
	{
		boolean canSpread = placerState.getBlock() instanceof IOccasionallySpreadableBlock os && os.canSpread(placerLevel, placerState, placerPosition);
		BlockState p = placingLevel.getBlockState(placingPosition);

		if(p.is(YATMBlockTags.CARCASS_ROOT_ROOTED_DIRT_ROOTS_FROM_KEY)) 
		{
			return YATMBlocks.CARCASS_ROOT_ROOTED_DIRT.get().defaultBlockState().setValue(CarcassRootRootBlock.CAN_SPREAD, canSpread);
		}
		if(p.is(YATMBlockTags.CARCASS_ROOT_ROOTED_NETHERRACK_ROOTS_FROM_KEY)) 
		{
			return YATMBlocks.CARCASS_ROOT_ROOTED_NETHERRACK.get().defaultBlockState().setValue(CarcassRootRootBlock.CAN_SPREAD, canSpread);
		}
		return null;
	} // end get()

} // end class