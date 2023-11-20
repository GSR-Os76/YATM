package com.gsr.gsr_yatm.block.device.current_furnace;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.network.NetworkUtil;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CurrentFurnaceMenu extends AbstractContainerMenu
{

	public static final int PLAYER_INVENTORY_START = (CurrentFurnaceBlockEntity.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final ContainerLevelAccess m_access;
	private final Block m_openingBlockType;
	private final ContainerData m_data;



	// client side constructor
	public CurrentFurnaceMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(CurrentFurnaceBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(CurrentFurnaceBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	// server side constructor
	public CurrentFurnaceMenu(int inventoryId, Inventory playerInventory, ContainerLevelAccess access, Block openingBlockType, IItemHandler objInventory, ContainerData data)
	{
		super(YATMMenuTypes.CURRENT_FURNACE.get(), inventoryId);

		this.m_access = access;
		this.m_openingBlockType = openingBlockType;
		this.m_data = data;

		int newYDownShift = 36;
		int yPos = 51 + newYDownShift;
		this.addSlot(new SlotItemHandler(objInventory, CurrentFurnaceBlockEntity.INPUT_SLOT, 8, yPos));
		this.addSlot(new SlotItemHandler(objInventory, CurrentFurnaceBlockEntity.RESULT_SLOT, 8 + 18, yPos));		
		this.addSlot(new SlotItemHandler(objInventory, CurrentFurnaceBlockEntity.HEAT_SLOT, 80, yPos));
		
		// add player inventory. indexing of the slots begins with hotbar, notably
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
			if (quickMovedSlotIndex == CurrentFurnaceBlockEntity.RESULT_SLOT)
			{
				
				if (!this.moveItemStackTo(slotsStack, PLAYER_INVENTORY_START, PLAYER_HOTBAR_END + 1, true))
				{					
					return ItemStack.EMPTY;
				}

				quickMovedSlot.onQuickCraft(slotsStack, quickMovedStack);
			}
			else if (quickMovedSlotIndex >= PLAYER_INVENTORY_START && quickMovedSlotIndex <= PLAYER_HOTBAR_END)
			{			
				boolean moved = false;
				if(this.moveItemStackTo(slotsStack, CurrentFurnaceBlockEntity.INPUT_SLOT, CurrentFurnaceBlockEntity.INPUT_SLOT + 1, false)) 
				{					
					moved = true;				
				}
				else if(SlotUtil.isValidHeatingSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CurrentFurnaceBlockEntity.HEAT_SLOT, CurrentFurnaceBlockEntity.HEAT_SLOT + 1, false)) 
				{			
					moved = true;
				}
				else if((quickMovedSlotIndex >= PLAYER_INVENTORY_START && quickMovedSlotIndex <= PLAYER_INVENTORY_END) && this.moveItemStackTo(slotsStack, PLAYER_HOTBAR_START, PLAYER_HOTBAR_END + 1, false)) 
				{						
					moved = true;
				}
				else if ((quickMovedSlotIndex >= PLAYER_HOTBAR_START && quickMovedSlotIndex <= PLAYER_HOTBAR_END) && this.moveItemStackTo(slotsStack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END + 1, false))
				{
					moved = true;
				}			
				if(!moved) 
				{
					return ItemStack.EMPTY;
				}
			}
			// if not result or inventory of player, move from other device slots to player
			// inventory if is possible
			else if (!this.moveItemStackTo(slotsStack, PLAYER_INVENTORY_START, PLAYER_HOTBAR_END + 1, false))
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
		return NetworkUtil.getProgess(CurrentFurnaceBlockEntity.ACCESS_SPEC.get(CurrentFurnaceBlockEntity.CRAFT_PROGRESS_SPEC_KEY), this.m_data);
	} // end craftProgress()
	
	public float burnRemaining()
	{
		return NetworkUtil.getRemaining(CurrentFurnaceBlockEntity.ACCESS_SPEC.get(CurrentFurnaceBlockEntity.BURN_PROGRESS_SPEC_KEY), this.m_data);
	} // end burnPercentageRemaining()
	
	
	
	public int getTemperature()
	{
		return this.m_data.get(CurrentFurnaceBlockEntity.ACCESS_SPEC.get(CurrentFurnaceBlockEntity.TEMPERATURE_DATA_SPEC_KEY).startIndex());
	} // end getTemperature() 
	
	public int getMaxTemperature()
	{
		return this.m_data.get(CurrentFurnaceBlockEntity.ACCESS_SPEC.get(CurrentFurnaceBlockEntity.TEMPERATURE_DATA_SPEC_KEY).endIndex());
	} // end getMaxTemperature()
} // end class