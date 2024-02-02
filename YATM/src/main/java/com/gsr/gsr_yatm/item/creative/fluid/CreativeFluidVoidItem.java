package com.gsr.gsr_yatm.item.creative.fluid;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.UseUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
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
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand)
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
				InteractionResult r = UseUtilities.tryDrainPosition(level, hPos, player);
				if(r.consumesAction()) 
				{
					return new InteractionResultHolder<>(r, held);
				}
			}
		}		
		return super.use(level, player, hand);
	} // end use()
	
	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new CreativeFluidVoidItemStack(stack);
	} // end initCapabilities()
	
} // end class