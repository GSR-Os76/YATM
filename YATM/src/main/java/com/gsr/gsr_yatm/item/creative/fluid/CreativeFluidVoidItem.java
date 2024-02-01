package com.gsr.gsr_yatm.item.creative.fluid;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CreativeFluidVoidItem extends Item
{

	public CreativeFluidVoidItem(@NotNull Properties properties)
	{
		super(Objects.requireNonNull(properties));
	} // end constructor


	
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, InteractionHand hand)
	{
		BlockHitResult hr = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
		if (hr.getType() == HitResult.Type.BLOCK)
		{
			BlockPos hPos = hr.getBlockPos();
			Direction d = hr.getDirection();
			BlockPos rPos = hPos.relative(d);
			ItemStack held = player.getItemInHand(hand);
			if (level.mayInteract(player, hPos) && player.mayUseItemAt(rPos, d, held))
			{
				BlockState bs = level.getBlockState(hPos);
				Block b = bs.getBlock();
				if (b instanceof LiquidBlock)
				{
					level.setBlock(hPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
					return InteractionResultHolder.sidedSuccess(held, level.isClientSide);
				}
				else if (b instanceof BucketPickup bp)
				{
					bp.pickupBlock(player, level, hPos, bs);
					return InteractionResultHolder.sidedSuccess(held, level.isClientSide);
				}
			}
		}		
		return super.use(level, player, hand);
	} // end use()
	
	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		if(this.getClass() == CreativeFluidVoidItem.class) 
		{
			return new CreativeFluidVoidItemStack(stack);
		}
	
		return super.initCapabilities(stack, nbt);
	} // end initCapabilities()
} // end class
