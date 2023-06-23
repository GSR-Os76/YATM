package com.gsr.gsr_yatm.utilities;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.api.IComponent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandler;

public class SlotUtilities
{
	
	public static int getHeatingBurnTime(ItemStack itemStack) 
	{
		return ForgeHooks.getBurnTime(itemStack, RecipeType.SMELTING);
	} // getHeatingBurnTime()
	
	public static int getHeatingTemperature(ItemStack itemStack) 
	{
		// TODO, implement or remove
		return 512;
	} // end getHeatingTemperature()
	
	
	
	public static boolean isValidHeatingSlotInsert(ItemStack itemStack) 
	{
		// TODO, implement actual logic, create recipe type for heating
		return getHeatingBurnTime(itemStack) > 0;
	}

	
	
	//add component considerations
	
	public static boolean isValidTankFillSlotInsert(ItemStack itemStack)
	{
		return 
//				(itemStack.getItem() instanceof BucketItem || 
//				itemStack.getItem() instanceof MilkBucketItem ||
				itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()
//				) 
//				&& 
//				itemStack.getItem() != Items.BUCKET &&
//				// maybe eventually handler mob buckets, take the fluid, and spawn the creature nearby
//				!(itemStack.getItem() instanceof MobBucketItem)
				;
	} // end isValidTankFillSlotInsert()
	
	public static boolean isValidTankDrainSlotInsert(ItemStack itemStack)
	{
		return 
//				itemStack.getItem() instanceof BucketItem 
//				? itemStack.getItem() == Items.BUCKET 
//				: 
					itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
	} // end isValidTankDrainSlotInsert()
	
	
	
	public static boolean canFillTankFrom(ItemStack itemStack, IFluidHandler tank, int maxTransfer) 
	{
		IFluidHandlerItem c = getFluidHandlerCapability(itemStack);		
		return (c != null) && (tank.fill(c.drain(maxTransfer, FluidAction.SIMULATE), FluidAction.SIMULATE) > 0);
	} // end canFillTankFrom
	
	public static boolean canDrainTankto(ItemStack itemStack, IFluidHandler tank, int maxTransfer) 
	{
		IFluidHandlerItem c = getFluidHandlerCapability(itemStack);
		return (c != null) && (c.fill(tank.drain(maxTransfer, FluidAction.SIMULATE), FluidAction.SIMULATE) > 0);
	} // end canDrainTankTo()
	
	
	
	// returns the remainder or whatever
	public static ItemStack fillTankFrom(ItemStack itemStack, IFluidHandler tank, int maxTransfer) 
	{
		if(isValidTankFillSlotInsert(itemStack)) 
		{
			IFluidHandlerItem c = getFluidHandlerCapability(itemStack);
			if(c == null) 
			{
				return itemStack;
			}
			tank.fill(c.drain(tank.fill(c.drain(maxTransfer, FluidAction.SIMULATE), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE);
			return c.getContainer();
		}
		return itemStack;
	} // end fillTankFrom
	
	private static IFluidHandlerItem getFluidHandlerCapability(ItemStack itemStack) 
	{
		return itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).orElse(null);
	}

	
	
	public static ItemStack drainTankTo(ItemStack itemStack, IFluidHandler tank, int maxTransfer)
	{
		YetAnotherTechMod.LOGGER.info("entered drain to");
		if(isValidTankDrainSlotInsert(itemStack)) 
		{
			IFluidHandlerItem c = getFluidHandlerCapability(itemStack);
			if(c == null) 
			{
				return itemStack;
			}
			c.fill(tank.drain(c.fill(tank.drain(maxTransfer, FluidAction.SIMULATE), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE);
			return c.getContainer();
		}
		return itemStack;
	}
	
	
	
	// returns the relevant capability for the slot considering the component system
	public static <T> LazyOptional<T> getSlotsCapability(LazyOptional<IItemHandler> slotCapability, Capability<T> capabilityType)
	{
		if(slotCapability == null || capabilityType == null) 
		{
			return null;
		}
		IItemHandler unwrapped = slotCapability.orElse(null);
		
		if(unwrapped == null) 
		{
			return null;
		}
		ItemStack i = unwrapped.getStackInSlot(0);
		if(i.getItem() instanceof IComponent) 
		{
			LazyOptional<T> v = i.getCapability(capabilityType);
			return v;
		}
		else if (capabilityType == ForgeCapabilities.ITEM_HANDLER) 
		{					
			return slotCapability.cast();
		}
		return null;
	} // end getSlotCapability()
	
} // end class