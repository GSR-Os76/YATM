package com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandler;

public class DrainTankManager implements ISerializableBehavior, ITickableBehavior
{
	private static final String COUNT_DOWN_TAG_NAME = "drainResultCount";
	private static final String TRANSFER_INITIAL_TAG_NAME = "drainResultInitial";
	private static final String STACK_TAG_NAME = "stack";
	
	private final @NotNull IItemHandler m_inventory;
	private final @NotNegative int m_slot;
	private final @NotNull IFluidHandler m_fluidHandler;
	private final @NotNegative int m_tank;
	private final @NotNegative int m_transferRate;
	
	private @NotNegative int m_countDown = 0;
	private @NotNegative int m_initial = 0;	
	private @Nullable ItemStack m_initialItemStack = null;


	
	public DrainTankManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull IFluidHandler fluidHandler, @NotNegative int transferRate) 
	{
		this(Objects.requireNonNull(inventory), Contract.notNegative(slot), Objects.requireNonNull(fluidHandler), Contract.notNegative(transferRate), 0);
	} // end constructor()
	
	public DrainTankManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull IFluidHandler fluidHandler, @NotNegative int transferRate, @NotNegative int tank) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_slot = Contract.notNegative(slot);
		
		this.m_fluidHandler = Objects.requireNonNull(fluidHandler);
		this.m_tank = Contract.notNegative(tank);
		this.m_transferRate = Contract.notNegative(transferRate);
	} // end constructor
	
	
	
	public @NotNegative int countDown() 
	{
		return this.m_countDown;
	} // end countDown()
	
	public @NotNegative int initial() 
	{
		return this.m_initial;
	} // end initial()
	
	
	
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		ItemStack holding = this.m_inventory.getStackInSlot(this.m_slot);
		if(this.m_initialItemStack != null && !ItemStack.matches(this.m_initialItemStack, holding)) 
		{
			this.m_countDown = 0;
			this.m_initial = 0;
		}
		
		IFluidHandlerItem heldHandler = holding.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
		
		
		
		boolean changed = false;
		
		if (holding.isEmpty() || heldHandler == null || this.m_fluidHandler.getFluidInTank(this.m_tank).getAmount() <= 0) 
		{
			return changed;
		}
		
		

		if (this.m_countDown > 0)
		{
			this.m_countDown -= this.m_transferRate;
			if (this.m_countDown <= 0)
			{
				ItemStack remainder = this.m_inventory.extractItem(this.m_slot, this.m_inventory.getSlotLimit(this.m_slot), false);
				// note, we assume heldHandler still represents the stack in the slot after the extraction
				if (heldHandler.fill(this.m_fluidHandler.drain(this.m_initial, FluidAction.SIMULATE), FluidAction.SIMULATE) > 0)
				{
					heldHandler.fill(this.m_fluidHandler.drain(heldHandler.fill(this.m_fluidHandler.drain(this.m_initial, FluidAction.SIMULATE), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE);
					remainder = heldHandler.getContainer();					
				}
				InventoryUtil.insertItemOrDrop(level, position, this.m_inventory, this.m_slot, remainder);
				this.m_initial = 0;
			}
			changed = true;
		}
		if (this.m_initial == 0) 
		{
			int max = heldHandler.fill(this.m_fluidHandler.drain(Integer.MAX_VALUE, FluidAction.SIMULATE), FluidAction.SIMULATE);
			int goal = heldHandler.fill(this.m_fluidHandler.drain(this.m_transferRate, FluidAction.SIMULATE), FluidAction.SIMULATE);
			this.m_initial = goal == 0 ? max : goal;
			this.m_countDown = this.m_initial;
			this.m_initialItemStack = this.m_inventory.getStackInSlot(this.m_slot);
			changed = true;
		}
		return changed;
	} // end tick()
	
	

	@Override
	public @Nullable CompoundTag serializeNBT()
	{
		if (this.m_countDown > 0 && this.m_initial > 0 && this.m_initialItemStack != null)
		{
			CompoundTag tag = new CompoundTag();
			tag.putInt(DrainTankManager.COUNT_DOWN_TAG_NAME, this.m_countDown);
			tag.putInt(DrainTankManager.TRANSFER_INITIAL_TAG_NAME, this.m_initial);
			tag.put(DrainTankManager.STACK_TAG_NAME, this.m_initialItemStack.save(new CompoundTag()));
			return tag;
		}
		return null;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(@NotNull CompoundTag tag)
	{
		if (tag.contains(DrainTankManager.COUNT_DOWN_TAG_NAME) && tag.contains(DrainTankManager.TRANSFER_INITIAL_TAG_NAME) && tag.contains(DrainTankManager.STACK_TAG_NAME))
		{
			this.m_countDown = tag.getInt(DrainTankManager.COUNT_DOWN_TAG_NAME);
			this.m_initial = tag.getInt(DrainTankManager.TRANSFER_INITIAL_TAG_NAME);
			this.m_initialItemStack = ItemStack.of(tag.getCompound(DrainTankManager.STACK_TAG_NAME));
			
		}
	} // end deserializeNBT()
	
} // end class