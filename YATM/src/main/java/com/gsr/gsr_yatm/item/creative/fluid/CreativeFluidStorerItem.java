package com.gsr.gsr_yatm.item.creative.fluid;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.UseUtilities;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class CreativeFluidStorerItem extends Item
{

	public CreativeFluidStorerItem(@NotNull Properties properties)
	{
		super(Objects.requireNonNull(properties));
	} // end constructor
	
	
	
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand)
	{
		BlockHitResult hr = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
		if (hr.getType() == HitResult.Type.BLOCK)
		{
			BlockPos hPos = hr.getBlockPos();
			Direction d = hr.getDirection();
			BlockPos rPos = hPos.relative(d);
			ItemStack held = player.getItemInHand(hand);
			if (level.mayInteract(player, hPos) && player.mayUseItemAt(rPos, d, held))
			{
				FluidState fs = level.getFluidState(hPos);
				if (!fs.isEmpty())
				{
					IFluidHandlerItem fHI = held.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
					Fluid hF = fHI.getFluidInTank(0).getFluid();
					Fluid f = fs.getType();
					if(hF == Fluids.EMPTY || f.isSame(hF)) 
					{
						InteractionResult r = UseUtilities.tryDrainPosition(level, hPos, player);
						if (r.consumesAction())
						{
							fs.getFluidType();
							fHI.fill(new FluidStack(f instanceof FlowingFluid ff ? ff.getSource() : f, FluidType.BUCKET_VOLUME), FluidAction.EXECUTE);
							return new InteractionResultHolder<>(r, held);
						}
					}
				}
				else 
				{
					IFluidHandlerItem fHI = held.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
					FluidStack c = fHI.getFluidInTank(0).copy();
					BlockState bs = level.getBlockState(hPos);
					InteractionResult r = UseUtilities.tryFillPosition(level, bs.getBlock() instanceof LiquidBlockContainer liquid && liquid.canPlaceLiquid(player, level, hPos, bs, c.getFluid()) ? hPos : rPos, player, c);
					if(r.consumesAction()) 
					{
						c = c.copy();
						c.setAmount(FluidType.BUCKET_VOLUME);
						fHI.drain(c, FluidAction.EXECUTE);
						return new InteractionResultHolder<>(r, held);
					}					
				}
			}
		}
		return super.use(level, player, hand);
	} // end use()
	
	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new FluidHandlerItemStack(stack, Integer.MAX_VALUE);
	} // end initCapabilities()
	
	
	
	@Override
	public @NotNull Component getName(@NotNull ItemStack itemStack)
	{
		FluidStack f = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null).getFluidInTank(0);
		MutableComponent name = Component.translatable(getDescriptionId(itemStack));
		if(f != FluidStack.EMPTY) 
		{
			name.append(Component.literal(" ("));
			name.append(Component.translatable(Util.makeDescriptionId("fluid", ForgeRegistries.FLUIDS.getKey(f.getFluid()))));
			name.append(Component.literal(": " + f.getAmount() +"/2³¹-1mbs)"));
		}
		return name;
	} // end getName()

} // end class