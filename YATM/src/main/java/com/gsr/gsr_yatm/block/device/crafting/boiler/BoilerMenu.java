package com.gsr.gsr_yatm.block.device.crafting.boiler;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.crafting.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
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

public class BoilerMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = BoilerBlockEntity.LAST_INVENTORY_SLOT + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull ContainerData m_data;
	private final @NotNull FluidTankDataReader m_inputTankReader;
	private final @Nullable Block m_openingBlockType;
	private final @NotNull FluidTankDataReader m_resultTankReader;
	


	// client side constructor
	public BoilerMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(BoilerBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(BoilerBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	// server side constructor
	public BoilerMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlockType, @NotNull IItemHandler objInventory, @NotNull ContainerData data)
	{
		super(YATMMenuTypes.BOILER.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_openingBlockType = openingBlockType;
		this.m_data = Objects.requireNonNull(data);

		this.m_inputTankReader = new FluidTankDataReader(this.m_data, BoilerBlockEntity.ACCESS_SPEC.get(BoilerBlockEntity.INPUT_TANK_DATA_SPEC_KEY));
		this.m_resultTankReader = new FluidTankDataReader(this.m_data, BoilerBlockEntity.ACCESS_SPEC.get(BoilerBlockEntity.RESULT_TANK_DATA_SPEC_KEY));
		
		int yShift = 36;
		int yPos = 51 + yShift;
		this.addSlot(new SlotItemHandler(objInventory, BoilerBlockEntity.FILL_INPUT_TANK_SLOT, 8, yPos));
		this.addSlot(new SlotItemHandler(objInventory, BoilerBlockEntity.DRAIN_INPUT_TANK_SLOT, 8 + 18, yPos));
		this.addSlot(new SlotItemHandler(objInventory, BoilerBlockEntity.DRAIN_RESULT_TANK_SLOT, 152, yPos));
		
		this.addSlot(new SlotItemHandler(objInventory, BoilerBlockEntity.HEAT_SLOT, 80, yPos));
		
		// add player inventory. indexing of the slots begins with hotbar, notably
		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(playerInventory, (x + (y * 9)) + 9, 8 + (x * 18), (84 + yShift) + (y * 18)));
			}
		}

		for (int x = 0; x < 9; ++x)
		{
			this.addSlot(new Slot(playerInventory, x, 8 + (x * 18), 142 + yShift));
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
			if (quickMovedSlotIndex == BoilerBlockEntity.DRAIN_RESULT_TANK_SLOT)
			{
				
				if (!this.moveItemStackTo(slotsStack, BoilerMenu.PLAYER_INVENTORY_START, BoilerMenu.PLAYER_HOTBAR_END + 1, true))
				{					
					return ItemStack.EMPTY;
				}

				quickMovedSlot.onQuickCraft(slotsStack, quickMovedStack);
			}
			else if (quickMovedSlotIndex >= BoilerMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= BoilerMenu.PLAYER_HOTBAR_END)
			{			
				boolean moved = false;
				if(SlotUtil.isValidTankFillSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, BoilerBlockEntity.FIRST_FILL_FLUID_SLOT, BoilerBlockEntity.LAST_FILL_FLUID_SLOT + 1, false)) 
				{					
					moved = true;				
				}
				else if(SlotUtil.isValidTankDrainSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, BoilerBlockEntity.FIRST_DRAIN_FLUID_SLOT, BoilerBlockEntity.LAST_DRAIN_FLUID_SLOT + 1, false)) 
				{				
					moved = true;
				}
				else if(SlotUtil.isValidHeatingSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, BoilerBlockEntity.HEAT_SLOT, BoilerBlockEntity.HEAT_SLOT + 1, false)) 
				{			
					moved = true;
				}
				else if((quickMovedSlotIndex >= BoilerMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= BoilerMenu.PLAYER_INVENTORY_END) && this.moveItemStackTo(slotsStack, BoilerMenu.PLAYER_HOTBAR_START, BoilerMenu.PLAYER_HOTBAR_END + 1, false)) 
				{						
					moved = true;
				}
				else if ((quickMovedSlotIndex >= BoilerMenu.PLAYER_HOTBAR_START && quickMovedSlotIndex <= BoilerMenu.PLAYER_HOTBAR_END) && this.moveItemStackTo(slotsStack, BoilerMenu.PLAYER_INVENTORY_START, BoilerMenu.PLAYER_INVENTORY_END + 1, false))
				{
					moved = true;
				}			
				if(!moved) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(slotsStack, BoilerMenu.PLAYER_INVENTORY_START, BoilerMenu.PLAYER_HOTBAR_END + 1, false))
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
		return NetworkUtil.getProgess(BoilerBlockEntity.ACCESS_SPEC.get(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY), this.m_data);
	} // end craftProgress()
	
	public float burnProgress() 
	{
		return NetworkUtil.getRemainingZeroIfNotRunning(BoilerBlockEntity.ACCESS_SPEC.get(BoilerBlockEntity.BURN_PROGRESS_SPEC_KEY), this.m_data);
	} // end heatProgress()
	
	public float fillInputTankTransferProgress() 
	{
		return NetworkUtil.getProgess(BoilerBlockEntity.ACCESS_SPEC.get(BoilerBlockEntity.FILL_INPUT_PROGRESS_SPEC_KEY), this.m_data);
	} // end fillInputTankTransferProgress()
	
	public float drainInputTankTransferProgress() 
	{
		return NetworkUtil.getProgess(BoilerBlockEntity.ACCESS_SPEC.get(BoilerBlockEntity.DRAIN_INPUT_PROGRESS_SPEC_KEY), this.m_data);
	} // end drainInputTankTransferProgress()
	
	public float drainResultTankTransferProgress() 
	{
		return NetworkUtil.getProgess(BoilerBlockEntity.ACCESS_SPEC.get(BoilerBlockEntity.DRAIN_RESULT_PROGRESS_SPEC_KEY), this.m_data);
	} // end drainResultTankTransferProgress()
	
	
	
	public int getTemperature() 
	{
		return NetworkUtil.getPropertyValue(BoilerBlockEntity.ACCESS_SPEC.get(BoilerBlockEntity.TEMPERATURE_SPEC_KEY), this.m_data);	
	} // end temperature()
	
	public int getMaxTemperature() 
	{
		return NetworkUtil.getPropertyValue(BoilerBlockEntity.ACCESS_SPEC.get(BoilerBlockEntity.MAX_TEMPERATURE_SPEC_KEY), this.m_data);
	} // end maxTemperature()
	
	
	
	public int getInputTankCapacity() 
	{
		return this.m_inputTankReader.getCapacity();
	} // end getInputTankCapacity() 
	
	public FluidStack getInputTankContents()
	{
		return this.m_inputTankReader.getFluidStack();
	} // end getInputTankFluid()
	


	
	public int getOutputTankCapacity() 
	{
		return this.m_resultTankReader.getCapacity();
	} // end getOutputTankCapacity() 
	
	public FluidStack getOutputTankContents()
	{
		return this.m_resultTankReader.getFluidStack();
	} // end getOutputTankFluid()

} // end class