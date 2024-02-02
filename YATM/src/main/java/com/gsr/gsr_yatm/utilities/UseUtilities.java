package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
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
		
		
		
		int fillFromHeldSim = beHandler.fill(heldHandler.drain(maxTransfer, FluidAction.SIMULATE), FluidAction.SIMULATE);
		if(fillFromHeldSim > 0) 
		{
			beHandler.fill(heldHandler.drain(fillFromHeldSim, FluidAction.EXECUTE), FluidAction.EXECUTE);
			player.setItemInHand(hand, heldHandler.getContainer());
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		else 
		{
			int drainToHeldSim = heldHandler.fill(beHandler.drain(maxTransfer, FluidAction.SIMULATE), FluidAction.SIMULATE);
			if(drainToHeldSim > 0) 
			{
				heldHandler.fill(beHandler.drain(drainToHeldSim, FluidAction.EXECUTE), FluidAction.EXECUTE);
				player.setItemInHand(hand, heldHandler.getContainer());
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
						
		return InteractionResult.PASS;
	} // end tryFillOrDrainFromHeld()
	
	
	
	public static InteractionResult tryFillOrDrainFromHeldDesynchronized(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult) 
	{
		return UseUtilities.tryFillOrDrainFromHeldDesynchronized(state, level, position, player, hand, hitResult, Integer.MAX_VALUE);
	} // end tryFillOrDrainFromHeld()
	
	public static InteractionResult tryFillOrDrainFromHeldDesynchronized(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult, int maxTransfer) 
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
	
	
	
	public static InteractionResult tryDrainPosition(@NotNull Level level, @NotNull BlockPos position, @Nullable Player player) 
	{
		BlockState bs = level.getBlockState(position);
		Block b = bs.getBlock();
		if (b instanceof LiquidBlock)
		{
			level.setBlock(position, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		else if (b instanceof BucketPickup bp)
		{
			bp.pickupBlock(player, level, position, bs);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	} // end tryDrainPosition()
	
	
	
	public static InteractionResult tryDrainPositionSource(@NotNull Level level, @NotNull BlockPos position, @Nullable Player player) 
	{
		BlockState bs = level.getBlockState(position);
		Block b = bs.getBlock();
		if (b instanceof LiquidBlock l)
		{
			if (l.getFluidState(bs).isSource())
			{
				level.setBlock(position, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		else if (b instanceof BucketPickup bp)
		{
			bp.pickupBlock(player, level, position, bs);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	} // end tryDrainPositionSource()

	
	
	public static InteractionResult tryFillPosition(@NotNull Level level, @NotNull BlockPos position, @Nullable Player player, @NotNull FluidStack fluid)
	{
		if(fluid.getFluid() instanceof FlowingFluid ff && fluid.getAmount() >= FluidType.BUCKET_VOLUME) 
		{
			BlockState bs = level.getBlockState(position);
			FluidType ft = fluid.getFluid().getFluidType();
			// if not air or replacable, or a LiquidBlockContainer, creep towards hiresult, the bucket behavior, only occurs once by nulling the hitresult
			if(ft.isVaporizedOnPlacement(level, position, fluid)) 
			{
				ft.onVaporize(player, level, position, fluid);
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
			else if(bs.getBlock() instanceof LiquidBlockContainer l && l.canPlaceLiquid(player, level, position, bs, ff)) 
			{
				l.placeLiquid(level, position, bs, ff.getSource(false));
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
			else if(bs.canBeReplaced(ff)) 
			{
				level.setBlock(position, ff.defaultFluidState().createLegacyBlock(), Block.UPDATE_ALL_IMMEDIATE);
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		return InteractionResult.PASS;
	} // end tryFillPosition()
	
} // end class