package com.gsr.gsr_yatm.block.device.grafting;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GraftingMenu extends AbstractContainerMenu
{
	public static final int RESULT_SLOT = 0;
	public static final int CRAFTING_GRID_START = 1;
	public static final int CRAFTING_GRID_END = 9;
	  
	public static final int PLAYER_INVENTORY_START = GraftingMenu.CRAFTING_GRID_END + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final ContainerLevelAccess m_access;
	private final Block m_openingBlockType;
	private final IItemHandler m_resultSlot = InventoryWrapper.Builder.of(new ItemStackHandler(1)).slotValidator((i, is, s) -> false).build();
	private final IItemHandler m_craftingGrid = new ItemStackHandler(9);
	
	
	
	// client side constructor
	public GraftingMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null);
	} // end client constructor

	// server side constructor
	public GraftingMenu(int inventoryId, Inventory playerInventory, ContainerLevelAccess access, Block openingBlockType)
	{
		super(YATMMenuTypes.GRAFTING_TABLE.get(), inventoryId);

		this.m_access = access;
		this.m_openingBlockType = openingBlockType;

		this.addSlot(new SlotItemHandler(this.m_resultSlot, 0, 124, 35));
		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 3; ++x)
			{
				this.addSlot(new SlotItemHandler(this.m_craftingGrid,
						x + (y * 3), 
						30 + (x * 18), 
						17 + (y * 18)));
			}
		}
		
		

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
	} // end server constructor

	@Override
	public void removed(Player player)
	{
		super.removed(player);
		this.m_access.execute((l, bp) ->
		{
			InventoryUtil.drop(l, bp, this.m_craftingGrid);
		});
	} // end removed()
	
	
	
	@Override
	public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex)
	{
		ItemStack quickMovedStack = ItemStack.EMPTY;
		Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);
		if (quickMovedSlot != null && quickMovedSlot.hasItem())
		{
			ItemStack slotsStack = quickMovedSlot.getItem();
			if (quickMovedSlotIndex == GraftingMenu.RESULT_SLOT)
			{				
				if (!this.moveItemStackTo(slotsStack, GraftingMenu.PLAYER_INVENTORY_START, GraftingMenu.PLAYER_HOTBAR_END + 1, true))
				{					
					return ItemStack.EMPTY;
				}
				quickMovedSlot.onQuickCraft(slotsStack, quickMovedStack);
			}
			else if (quickMovedSlotIndex >= GraftingMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= GraftingMenu.PLAYER_HOTBAR_END)
			{		
				boolean moved = false;
				if((quickMovedSlotIndex >= GraftingMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= GraftingMenu.PLAYER_INVENTORY_END) 
						&& (this.moveItemStackTo(slotsStack, GraftingMenu.CRAFTING_GRID_START, GraftingMenu.CRAFTING_GRID_END + 1, false) || this.moveItemStackTo(slotsStack, GraftingMenu.PLAYER_HOTBAR_START, GraftingMenu.PLAYER_HOTBAR_END + 1, false))) 
				{											
					moved = true;
				}
				else if ((quickMovedSlotIndex >= GraftingMenu.PLAYER_HOTBAR_START && quickMovedSlotIndex <= GraftingMenu.PLAYER_HOTBAR_END) && this.moveItemStackTo(slotsStack, GraftingMenu.CRAFTING_GRID_START, GraftingMenu.PLAYER_INVENTORY_END + 1, false))
				{
					moved = true;
				}	
				if(!moved) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(slotsStack, GraftingMenu.PLAYER_INVENTORY_START, GraftingMenu.PLAYER_HOTBAR_END + 1, false))
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
	} // end stillValid()
	
} // end class