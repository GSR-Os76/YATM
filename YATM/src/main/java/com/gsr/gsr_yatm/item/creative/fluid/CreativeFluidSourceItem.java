package com.gsr.gsr_yatm.item.creative.fluid;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;

public class CreativeFluidSourceItem extends Item
{

	public CreativeFluidSourceItem(@NotNull Properties properties)
	{
		super(Objects.requireNonNull(properties));
	} // end constructor

	

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, InteractionHand hand)
	{
		ItemStack held = player.getItemInHand(hand);
		if(player.isCrouching()) 
		{
			if(!level.isClientSide()) 
			{
				LazyOptional<IFluidHandlerItem>  lo = held.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
				IFluidHandlerItem fh = lo.orElse(null);
				if(fh != null) 
				{
					fh.fill(FluidStack.EMPTY, FluidAction.EXECUTE);
					return InteractionResultHolder.consume(fh.getContainer());
				}
			}
			return InteractionResultHolder.success(held);
		}
		else 
		{
			BlockHitResult hr = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
		    if(hr.getType() == HitResult.Type.BLOCK) 
		    {
				BlockPos hPos = hr.getBlockPos();
				Direction d = hr.getDirection();
				BlockPos rPos = hPos.relative(d);
				if (level.mayInteract(player, hPos) && player.mayUseItemAt(rPos, d, held))
				{
					FluidState fs = level.getFluidState(hPos);
					if(!fs.isEmpty()) 
					{
						Fluid f = fs.getType();
						f = f instanceof FlowingFluid ff ? ff.getSource() : f;
						held.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null).fill(new FluidStack(f, 1), FluidAction.EXECUTE);
						return InteractionResultHolder.sidedSuccess(held, level.isClientSide);
					}
				}
		    }
		}
		return super.use(level, player, hand);
	} // end use()
	
	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new CreativeFluidSourceItemStack(stack);
	} // end initCapabilities()


	
	@Override
	public Component getName(ItemStack itemStack)
	{
		LazyOptional<IFluidHandlerItem> fh = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
		IFluidHandlerItem c = fh.orElse(null);
		Fluid toNameTake = c == null ? Fluids.EMPTY : c.getFluidInTank(0).getFluid();
		
		MutableComponent name = Component.translatable(getDescriptionId(itemStack));
		if(toNameTake != null && toNameTake != Fluids.EMPTY) 
		{
			name.append(Component.literal(" ("));
			name.append(Component.translatable(Util.makeDescriptionId("fluid", ForgeRegistries.FLUIDS.getKey(toNameTake))));//ForgeRegistries.FLUIDS.getKey(toNameTake).toString()));
			name.append(Component.literal(")"));
		}
		
		return name;
	} // end getName()
	
} // end class