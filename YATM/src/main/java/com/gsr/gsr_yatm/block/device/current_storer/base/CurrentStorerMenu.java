package com.gsr.gsr_yatm.block.device.current_storer.base;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.current.CurrentDataReader;

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

public class CurrentStorerMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = (CurrentStorerBlockEntity.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull ContainerData m_data;
	private final @Nullable Block m_openingBlockType;
	private final @NotNull CurrentDataReader m_currentReader;
	
	
	
	// client side constructor
	public CurrentStorerMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(CurrentStorerBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(CurrentStorerBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	// server side constructor
	public CurrentStorerMenu(int inventoryId, Inventory playerInventory, ContainerLevelAccess access, Block openingBlockType, IItemHandler objInventory, ContainerData data)
	{
		super(YATMMenuTypes.CURRENT_STORER.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_data = Objects.requireNonNull(data);
		this.m_openingBlockType = openingBlockType;
		
		this.m_currentReader = new CurrentDataReader(this.m_data, CurrentStorerBlockEntity.ACCESS_SPEC.get(CurrentStorerBlockEntity.CURRENT_DATA_SPEC_KEY));
		
		this.addSlot(new SlotItemHandler(objInventory, CurrentStorerBlockEntity.FILL_POWER_SLOT, 44, 41));
		this.addSlot(new SlotItemHandler(objInventory, CurrentStorerBlockEntity.DRAIN_POWER_SLOT, 116, 41));

		int yShift = 9;
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
			if (quickMovedSlotIndex >= PLAYER_INVENTORY_START && quickMovedSlotIndex <= PLAYER_HOTBAR_END)
			{	
				boolean moved = false;
				if(SlotUtil.isValidPowerSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CurrentStorerBlockEntity.FILL_POWER_SLOT, CurrentStorerBlockEntity.DRAIN_POWER_SLOT + 1, false)) 
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

	
	
	public @NotNegative int getCurrentCapacity()
	{
		return this.m_currentReader.getCapacity();
	} // end getCurrentCapacity()
	
	public @NotNegative int getCurrentStored()
	{
		return this.m_currentReader.getStored();
	} // end getCurrentStored()
	
} // end class