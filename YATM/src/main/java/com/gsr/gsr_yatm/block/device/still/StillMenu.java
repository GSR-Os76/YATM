package com.gsr.gsr_yatm.block.device.still;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.NetworkUtil;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.fluid.FluidTankDataReader;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class StillMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = StillBlockEntity.LAST_INVENTORY_SLOT + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull ContainerData m_data;
	private final @NotNull FluidTankDataReader m_distillateTankReader;
	private final @NotNull FluidTankDataReader m_inputTankReader;
	private final @Nullable Block m_openingBlockType;
	private final @NotNull FluidTankDataReader m_remainderTankReader;
	


	public StillMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(StillBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(StillBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	public StillMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlockType, @NotNull IItemHandler objInventory, @NotNull ContainerData data)
	{
		super(YATMMenuTypes.STILL.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_openingBlockType = openingBlockType;
		this.m_data = Objects.requireNonNull(data);

		this.m_inputTankReader = new FluidTankDataReader(this.m_data, StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.INPUT_TANK_SPEC_KEY));
		this.m_remainderTankReader = new FluidTankDataReader(this.m_data, StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.REMAINDER_TANK_SPEC_KEY));
		this.m_distillateTankReader = new FluidTankDataReader(this.m_data, StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.DISTILLATE_TANK_SPEC_KEY));
		
		int newYDownShift = 36;
		this.addSlot(new SlotItemHandler(objInventory, StillBlockEntity.FILL_INPUT_TANK_SLOT, 22, 87));
		this.addSlot(new SlotItemHandler(objInventory, StillBlockEntity.DRAIN_INPUT_TANK_SLOT, 40, 87));
		this.addSlot(new SlotItemHandler(objInventory, StillBlockEntity.DRAIN_DISTILLATE_TANK_SLOT, 152, 21));
		this.addSlot(new SlotItemHandler(objInventory, StillBlockEntity.DRAIN_REMAINDER_TANK_SLOT, 152, 87));
		this.addSlot(new SlotItemHandler(objInventory, StillBlockEntity.HEAT_SLOT, 63, 87));
		
		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(playerInventory, (x + (y * 9)) + 9, 8 + (x * 18), (84 + newYDownShift) + (y * 18)));
			}
		}

		for (int x = 0; x < 9; ++x)
		{
			this.addSlot(new Slot(playerInventory, x, 8 + (x * 18), 142 + newYDownShift));
		}
		
		this.addDataSlots(data);
	} // end server constructor



	@Override
	public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex)
	{
		ItemStack quickMovedStack = ItemStack.EMPTY;
		Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);
		if (quickMovedSlot != null && quickMovedSlot.hasItem())
		{
			ItemStack slotsStack = quickMovedSlot.getItem();
			if (quickMovedSlotIndex == StillBlockEntity.DRAIN_DISTILLATE_TANK_SLOT)
			{
				
				if (!this.moveItemStackTo(slotsStack, StillMenu.PLAYER_INVENTORY_START, StillMenu.PLAYER_HOTBAR_END + 1, true))
				{					
					return ItemStack.EMPTY;
				}

				quickMovedSlot.onQuickCraft(slotsStack, quickMovedStack);
			}
			else if (quickMovedSlotIndex >= StillMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= StillMenu.PLAYER_HOTBAR_END)
			{			
				boolean moved = false;
				if(SlotUtil.isValidTankFillSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, StillBlockEntity.FILL_INPUT_TANK_SLOT, StillBlockEntity.FILL_INPUT_TANK_SLOT + 1, false)) 
				{					
					moved = true;				
				}
				else if(SlotUtil.isValidTankDrainSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, StillBlockEntity.FIRST_DRAIN_FLUID_SLOT, StillBlockEntity.LAST_DRAIN_FLUID_SLOT + 1, false)) 
				{				
					moved = true;
				}
				else if(SlotUtil.isValidHeatingSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, StillBlockEntity.HEAT_SLOT, StillBlockEntity.HEAT_SLOT + 1, false)) 
				{			
					moved = true;
				}
				else if((quickMovedSlotIndex >= StillMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= StillMenu.PLAYER_INVENTORY_END) && this.moveItemStackTo(slotsStack, StillMenu.PLAYER_HOTBAR_START, StillMenu.PLAYER_HOTBAR_END + 1, false)) 
				{						
					moved = true;
				}
				else if ((quickMovedSlotIndex >= StillMenu.PLAYER_HOTBAR_START && quickMovedSlotIndex <= StillMenu.PLAYER_HOTBAR_END) && this.moveItemStackTo(slotsStack, StillMenu.PLAYER_INVENTORY_START, StillMenu.PLAYER_INVENTORY_END + 1, false))
				{
					moved = true;
				}			
				if(!moved) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(slotsStack, StillMenu.PLAYER_INVENTORY_START, StillMenu.PLAYER_HOTBAR_END + 1, false))
			{
				return ItemStack.EMPTY;
			}
			
			
			
			if(slotsStack.isEmpty())
			{
				quickMovedSlot.set(ItemStack.EMPTY);
			}
			else 
			{
				quickMovedSlot.setChanged();
			}
			
			if(slotsStack == quickMovedStack)
			{
				return ItemStack.EMPTY;
			}
		
			quickMovedSlot.onTake(player, quickMovedStack);
		} // end slot usability check

		return quickMovedStack;
	} // end quickMoveStack()

	@Override
	public boolean stillValid(Player player)
	{
		return AbstractContainerMenu.stillValid(this.m_access, player, this.m_openingBlockType);
	} // end stillValid();

	

	public float craftProgress() 
	{
		return NetworkUtil.getProgess(StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.CRAFT_PROGRESS_SPEC_KEY), this.m_data);
	} // end craftProgress()
	
	public float burnProgress() 
	{
		return NetworkUtil.getRemainingZeroIfNotRunning(StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.BURN_PROGRESS_SPEC_KEY), this.m_data);
	} // end heatProgress()
	
	public float fillInputTankTransferProgress() 
	{
		return NetworkUtil.getProgess(StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.FILL_INPUT_PROGRESS_SPEC_KEY), this.m_data);
	} // end fillInputTankTransferProgress()
	
	public float drainInputTankTransferProgress() 
	{
		return NetworkUtil.getProgess(StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.DRAIN_INPUT_PROGRESS_SPEC_KEY), this.m_data);
	} // end drainInputTankTransferProgress()
	
	public float drainDistillateTankTransferProgress() 
	{
		return NetworkUtil.getProgess(StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.DRAIN_DISTILLATE_PROGRESS_SPEC_KEY), this.m_data);
	} // end drainDistillateTankTransferProgress()
	
	public float drainRemainderTankTransferProgress() 
	{
		return NetworkUtil.getProgess(StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.DRAIN_REMAINDER_PROGRESS_SPEC_KEY), this.m_data);
	} // end drainRemainderTankTransferProgress()
	
	
	
	public int getTemperature() 
	{
		return NetworkUtil.getPropertyValue(StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.TEMPERATURE_SPEC_KEY), this.m_data);	
	} // end temperature()
	
	public int getMaxTemperature() 
	{
		return NetworkUtil.getPropertyValue(StillBlockEntity.ACCESS_SPEC.get(StillBlockEntity.MAX_TEMPERATURE_SPEC_KEY), this.m_data);
	} // end maxTemperature()
	
	public @NotNegative int getInputTankCapacity() 
	{
		return this.m_inputTankReader.getCapacity();
	} // end getInputTankCapacity() 
	
	public @NotNull FluidStack getInputTankContents()
	{
		return this.m_inputTankReader.getFluidStack();
	} // end getInputTankFluid()
	
	public @NotNegative int getRemainderTankCapacity() 
	{
		return this.m_remainderTankReader.getCapacity();
	} // end getRemainderTankCapacity() 
	
	public @NotNull FluidStack getRemainderTankContents()
	{
		return this.m_remainderTankReader.getFluidStack();
	} // end getRemainderTankContents()

	public @NotNegative int getDistillateTankCapacity() 
	{
		return this.m_distillateTankReader.getCapacity();
	} // end getRemainderTankCapacity() 
	
	public @NotNull FluidStack getDistillateTankContents()
	{
		return this.m_distillateTankReader.getFluidStack();
	} // end getRemainderTankContents()
	
} // end class