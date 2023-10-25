package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class UseUtilities
{

	public static InteractionResult tryFillOrDrainFromHeld(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult) 
	{
		return UseUtilities.tryFillOrDrainFromHeld(state, level, position, player, hand, hitResult, Integer.MAX_VALUE);
	} // end tryFillOrDrainFromHeld()
	
	public static InteractionResult tryFillOrDrainFromHeld(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult, int maxTransfer) 
	{
		ItemStack held = player.getItemInHand(hand);
		if(held.isEmpty()) 
		{
			return InteractionResult.PASS;
		}
		@NotNull LazyOptional<IFluidHandlerItem> heldCap = held.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
		IFluidHandlerItem heldHandler = heldCap.orElse((IFluidHandlerItem)null);
		if(heldHandler == null) 
		{
			return InteractionResult.PASS;
		}
		
		BlockEntity be = level.getBlockEntity(position);
		if(be == null) 
		{
			return InteractionResult.PASS;
		}
		LazyOptional<IFluidHandler> beCap = be.getCapability(ForgeCapabilities.FLUID_HANDLER);
		IFluidHandler beHandler = beCap.orElse((IFluidHandler)null);
		if(beHandler == null) 
		{
			return InteractionResult.PASS;
		}
		
		
		if(!level.isClientSide) 
		{
			int fillFromHeldSim = beHandler.fill(heldHandler.drain(maxTransfer, FluidAction.SIMULATE), FluidAction.SIMULATE);
			if(fillFromHeldSim > 0) 
			{
				beHandler.fill(heldHandler.drain(fillFromHeldSim, FluidAction.EXECUTE), FluidAction.EXECUTE);
				player.setItemInHand(hand, heldHandler.getContainer());
				// return InteractionResult.sidedSuccess(level.isClientSide);
			}
			else 
			{
				int drainToHeldSim = heldHandler.fill(beHandler.drain(maxTransfer, FluidAction.SIMULATE), FluidAction.SIMULATE);
				if(drainToHeldSim > 0) 
				{
					heldHandler.fill(beHandler.drain(drainToHeldSim, FluidAction.EXECUTE), FluidAction.EXECUTE);
					player.setItemInHand(hand, heldHandler.getContainer());
					// return InteractionResult.sidedSuccess(level.isClientSide);
				}
			}
		}
				
		return InteractionResult.sidedSuccess(level.isClientSide);
	} // end tryFillOrDrainFromHeld()
	
} // end class