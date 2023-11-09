package com.gsr.gsr_yatm.item.tool;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.network.NetworkUtil;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PoweredToolMenu extends AbstractContainerMenu
{

	public static final int PLAYER_INVENTORY_START = (PoweredToolItemStack.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final ContainerData m_data;
	
	
	
	// client side constructor
	public PoweredToolMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, new ItemStackHandler(PoweredToolItemStack.INVENTORY_SLOT_COUNT), new SimpleContainerData(PoweredToolItemStack.ACCESS_SPEC.getCount()));
	} // end client constructor

	// server side constructor
	public PoweredToolMenu(int inventoryId, Inventory playerInventory, IItemHandler objInventory, ContainerData data)
	{
		super(YATMMenuTypes.POWERED_TOOL.get(), inventoryId);

		this.m_data = data;
		this.addSlot(new SlotItemHandler(objInventory, PoweredToolItemStack.POWER_SLOT, 62, 60));
		this.addSlot(new SlotItemHandler(objInventory, PoweredToolItemStack.UPGRADE_SLOT_ONE, 151, 21));
		this.addSlot(new SlotItemHandler(objInventory, PoweredToolItemStack.UPGRADE_SLOT_TWO, 151, 40));
		
		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(playerInventory, (x + (y * 9)) + 9, 8 + (x * 18), (84) + (y * 18)));
			}
		}

		for (int x = 0; x < 9; ++x)
		{
			this.addSlot(new Slot(playerInventory, x, 8 + (x * 18), 142));
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
			if (quickMovedSlotIndex >= PLAYER_INVENTORY_START && quickMovedSlotIndex <= PLAYER_HOTBAR_END)
			{		
				boolean moved = false;
				if(SlotUtil.isValidPowerSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, PoweredToolItemStack.POWER_SLOT, PoweredToolItemStack.POWER_SLOT + 1, false)) 
				{						
					moved = true;
				}
				else if(SlotUtil.isValidUpgradeSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, PoweredToolItemStack.UPGRADE_SLOT_ONE, PoweredToolItemStack.UPGRADE_SLOT_TWO + 1, false)) 
				{					
					moved = true;
				}
				else if((quickMovedSlotIndex >= PoweredToolMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= PoweredToolMenu.PLAYER_INVENTORY_END) && this.moveItemStackTo(slotsStack, PoweredToolMenu.PLAYER_HOTBAR_START, PoweredToolMenu.PLAYER_HOTBAR_END + 1, false)) 
				{											
					moved = true;
				}
				else if ((quickMovedSlotIndex >= PoweredToolMenu.PLAYER_HOTBAR_START && quickMovedSlotIndex <= PoweredToolMenu.PLAYER_HOTBAR_END) && this.moveItemStackTo(slotsStack, PoweredToolMenu.PLAYER_INVENTORY_START, PoweredToolMenu.PLAYER_INVENTORY_END + 1, false))
				{
					moved = true;
				}	
				if(!moved) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(slotsStack, PoweredToolMenu.PLAYER_INVENTORY_START, PoweredToolMenu.PLAYER_HOTBAR_END + 1, false))
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
		return true;	
	} // end stillValid()
	
	
	
	public int getCurrentStored()
	{
		return NetworkUtil.getPropertyValue(PoweredToolItemStack.ACCESS_SPEC.get(PoweredToolItemStack.CURRENT_STORED_SPEC_KEY), this.m_data);
	} // end PoweredToolItemStack()
	
	public int getCurrentCapacity()
	{
		return NetworkUtil.getPropertyValue(PoweredToolItemStack.ACCESS_SPEC.get(PoweredToolItemStack.CURRENT_CAPACITY_SPEC_KEY), this.m_data);
	} // end getCurrentCapacity()

} // end class