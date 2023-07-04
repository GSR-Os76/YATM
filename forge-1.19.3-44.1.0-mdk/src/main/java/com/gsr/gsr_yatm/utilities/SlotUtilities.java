package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.IComponent;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
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
		return 1024;
	} // end getHeatingTemperature()
	
	
	
	public static boolean isValidHeatingSlotInsert(ItemStack itemStack) 
	{
		// TODO, implement actual logic, create recipe type for heating
		return getHeatingBurnTime(itemStack) > 0;
	} // end isValidHeatingSlotInsert()

	
		
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
	
	
	
	public static boolean isValidPowerSlotInsert(ItemStack itemStack) 
	{
		// TODO, implement
		return true;
	}
	
	
	
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
	
	public static int maxDrainTankTo(ItemStack to, IFluidHandler from) 
	{
		IFluidHandler c = getFluidHandlerCapability(to);
		return (c != null) ? maxDrainTankTo(c, from) : 0;
	} // end maxDrainTankTo()

	public static int maxDrainTankTo(IFluidHandler to, ItemStack from) 
	{
		IFluidHandler c = getFluidHandlerCapability(from);
		return (c != null) ? maxDrainTankTo(to, c) : 0;
	} // end maxDrainTankTo()
	
	public static int maxDrainTankTo(IFluidHandler to, IFluidHandler from) 
	{
		return to.fill(from.drain(Integer.MAX_VALUE, FluidAction.SIMULATE), FluidAction.SIMULATE);
	} // end maxDrainTankTo()
	
	public static int minDrainTankTo(ItemStack to, IFluidHandler from) 
	{
		IFluidHandler c = getFluidHandlerCapability(to);
		return (c != null) ? minDrainTankTo(c, from) : 0;
	} // end minDrainTankTo()
	
	public static int minDrainTankTo(IFluidHandler to, ItemStack from) 
	{
		IFluidHandler c = getFluidHandlerCapability(from);
		return (c != null) ? minDrainTankTo(to, c) : 0;
	} // end minDrainTankTo()
	
	public static int minDrainTankTo(IFluidHandler to, IFluidHandler from) 
	{
		// TODO, implement actual logic
		return maxDrainTankTo(to, from);
	} // end minDrainTankTo()
	
	public static int drainClosestToFavoringLow(ItemStack to, IFluidHandler from, int goal) 
	{
		IFluidHandler c = getFluidHandlerCapability(to);
		return (c != null) ? drainClosestToFavoringLow(c, from, goal) : 0;
	} // end drainClosestToFavoringLow()

	public static int drainClosestToFavoringLow(IFluidHandler to, ItemStack from, int goal) 
	{
		IFluidHandler c = getFluidHandlerCapability(from);
		return (c != null) ? drainClosestToFavoringLow(to, c, goal) : 0;
	} // end drainClosestToFavoringLow()

	public static int drainClosestToFavoringLow(IFluidHandler to, IFluidHandler from, int goal) 
	{
		return to.fill(from.drain(goal, FluidAction.SIMULATE), FluidAction.SIMULATE);
	} // end drainClosestToFavoringLow()

	
	
	
	// returns the remainder or whatever
	public static ItemStack fillTankFrom(ItemStack from, IFluidHandler to, int maxTransfer) 
	{
		if(isValidTankFillSlotInsert(from)) 
		{
			IFluidHandlerItem c = getFluidHandlerCapability(from);
			if(c == null) 
			{
				return from;
			}
			to.fill(c.drain(to.fill(c.drain(maxTransfer, FluidAction.SIMULATE), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE);
			return c.getContainer();
		}
		return from;
	} // end fillTankFrom
	
	private static IFluidHandlerItem getFluidHandlerCapability(ItemStack itemStack) 
	{
		return itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
	}

	
	
	public static ItemStack drainTankTo(ItemStack itemStack, IFluidHandler tank, int maxTransfer)
	{
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
	} // end drainTankTo()
	
	
	
	// returns the relevant capability for the slot considering the component system
	public static <T> @NotNull LazyOptional<T> getSlotsCapability(@NotNull LazyOptional<IItemHandler> slotCapability, @NotNull Capability<T> capabilityType)
	{
		IItemHandler unwrapped = slotCapability.orElse(null);
		
		if(unwrapped == null) 
		{
			return LazyOptional.empty();
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
		return LazyOptional.empty();
	} // end getSlotCapability()
	
	
	
	// returns an amount of fluid possible to be drained from the tank to the slot
	public static int queueToDrainToSlot(IItemHandler inventory, int slot, IFluidHandler toDrain, int tank, int favoredTransferSize)
	{
		ItemStack stackInSlot = inventory.getStackInSlot(slot);
		if(stackInSlot.isEmpty() || toDrain.getFluidInTank(tank).getAmount() <= 0) 
		{
			return 0;
		}
		return  Math.max(minDrainTankTo(stackInSlot, toDrain), drainClosestToFavoringLow(stackInSlot, toDrain, favoredTransferSize));
	} // end queueToDrainToSlot()
	
	public static int countDownOrDrainToSlot(Level level, BlockPos position, IItemHandler inventory, int slot, IFluidHandler toDrain, int tank, int transferSize, int countDownTime, int maxRateOfTransfer) 
	{
		countDownTime -= maxRateOfTransfer;
		if (countDownTime <= 0)
		{
			if (SlotUtilities.canDrainTankto(inventory.getStackInSlot(slot), toDrain, transferSize))
			{
					ItemStack remainder = SlotUtilities.drainTankTo(inventory.extractItem(slot, inventory.getSlotLimit(slot), false), toDrain, transferSize);
					InventoryUtilities.insertItemOrDrop(level, position, inventory, slot, remainder);
			}
		}		
		return countDownTime;
	} // end countDownOrDrainToSlot()
	
	
	
	public static int queueToFillFromSlot(Level level, BlockPos position, IItemHandler inventory, int slot, IFluidHandler toFill, int tank, IFluidHandler fillBuffer, int favoredTransferSize)
	{
		ItemStack stackInSlot = inventory.getStackInSlot(slot);
		int freeVolume = toFill.getTankCapacity(tank) - toFill.getFluidInTank(tank).getAmount();
		if(stackInSlot.isEmpty() || freeVolume <= 0) 
		{
			return 0;
		}
		
		int amountTransferableToBuffer = Math.max(minDrainTankTo(fillBuffer, stackInSlot), drainClosestToFavoringLow(fillBuffer, stackInSlot, favoredTransferSize));
		int amountTransferableToTank = Math.max(minDrainTankTo(toFill, stackInSlot), drainClosestToFavoringLow(toFill, stackInSlot, favoredTransferSize));
		int amountTransferable = Math.min(amountTransferableToBuffer, amountTransferableToTank);
		ItemStack remainder = SlotUtilities.fillTankFrom(inventory.extractItem(slot, inventory.getSlotLimit(slot), false), fillBuffer, amountTransferable);
		InventoryUtilities.insertItemOrDrop(level, position, inventory, slot, remainder);
			
		return fillBuffer.getFluidInTank(0).getAmount();		
	} // end queueToFillFromSlot()

	
	
	
	public static int tryPowerSlot(IItemHandler inventory, int slot, ICurrentHandler from, int maxTransfer)
	{
		ItemStack stackInSlot = inventory.getStackInSlot(slot);
		if(stackInSlot.isEmpty() || maxTransfer <= 0) 
		{
			return 0;
		}
		ICurrentHandler c = stackInSlot.getCapability(YATMCapabilities.CURRENT).orElse(null);
		if(c != null) 
		{
			return c.recieveCurrent(from.extractCurrent(c.recieveCurrent(from.extractCurrent(maxTransfer, true), true), false), false);
		}
		
		return 0;
	} // end tryToPower()
	
} // end class