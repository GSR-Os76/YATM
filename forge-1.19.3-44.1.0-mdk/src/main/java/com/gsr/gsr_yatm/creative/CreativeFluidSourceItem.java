package com.gsr.gsr_yatm.creative;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;

public class CreativeFluidSourceItem extends Item
{

	public CreativeFluidSourceItem(Properties properties)
	{
		super(properties);
	} // end constructor

	
	// TODO, make these work
	@Override
	public InteractionResult useOn(UseOnContext context)
	{
		YetAnotherTechMod.LOGGER.info("useOn's being called");

		Level l = context.getLevel();
		if(!l.isClientSide) 
		{
			// ItemStack i = ;
			LazyOptional<IFluidHandlerItem>  lo = context.getItemInHand().getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
			IFluidHandlerItem fh = lo.orElse(null);
			if(fh != null) 
			{				
				BlockState bs = context.getLevel().getBlockState(context.getClickedPos());
				if(bs.getBlock() instanceof BucketPickup bp) 
				{
					ItemStack bucketPickedUp = bp.pickupBlock(l, context.getClickedPos(), bs);
					if(bucketPickedUp != null) 
					{
						LazyOptional<IFluidHandlerItem>  bf = bucketPickedUp.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
						IFluidHandlerItem bfh = bf.orElse(null);
						if(bfh != null) 
						{
							fh.fill(bfh.drain(Integer.MAX_VALUE, FluidAction.EXECUTE), FluidAction.EXECUTE);

						}						
					}
				}
			fh.fill(FluidStack.EMPTY, FluidAction.EXECUTE);
				
			}
		}
		return InteractionResult.sidedSuccess(l.isClientSide);
	} // end useOn()

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		YetAnotherTechMod.LOGGER.info("use's being called");
		// TODO Auto-generated method stub
		// set the fluid to empty
		ItemStack i = player.getItemInHand(hand);
		if(!level.isClientSide()) 
		{
			LazyOptional<IFluidHandlerItem>  lo = i.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
			IFluidHandlerItem fh = lo.orElse(null);
			if(fh != null) 
			{
				fh.fill(FluidStack.EMPTY, FluidAction.EXECUTE);
				return InteractionResultHolder.consume(fh.getContainer());
			}
			// how, how're you here
		}
			return InteractionResultHolder.success(i);
	} // end use()
	
	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		if(this.getClass() == CreativeFluidSourceItem.class) 
		{
			return new CreativeFluidSourceItemStack(stack);
		}
		return super.initCapabilities(stack, nbt);
	} // end initCapabilities()


	
	
	
//	@Override
//	public Component getDescription()
//	{
//		// TODO Auto-generated method stub
//		return super.getDescription();
//	}
//
//
//	
//	@Override
//	public String getDescriptionId(ItemStack p_41455_)
//	{
//		// TODO Auto-generated method stub
//		return super.getDescriptionId(p_41455_);
//	}
//
//
//	
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
	
	
	
	// glGetTexLevelParameteri
	
} // end class