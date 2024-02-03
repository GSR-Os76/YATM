package com.gsr.gsr_yatm.block.device.creative.current_source;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

public class CreativeCurrentSourceMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = (CreativeCurrentSourceBlockEntity.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull ContainerData m_data;
	private final @Nullable Block m_openingBlock;
	
	
	
	// client side constructor
	public CreativeCurrentSourceMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(CreativeCurrentSourceBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(CreativeCurrentSourceBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	// server side constructor
	public CreativeCurrentSourceMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlock, @NotNull IItemHandler objInventory, @NotNull ContainerData data)
	{
		super(YATMMenuTypes.CREATIVE_CURRENT_SOURCE.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_data = Objects.requireNonNull(data);
		this.m_openingBlock = openingBlock;
		
		this.addSlot(new SlotItemHandler(objInventory, CreativeCurrentSourceBlockEntity.CHARGE_SLOT, 80, 51));
		

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
			
			if (quickMovedSlotIndex >= CreativeCurrentSourceMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= CreativeCurrentSourceMenu.PLAYER_HOTBAR_END)
			{	
				boolean moved = false;
				if(SlotUtil.isValidPowerSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CreativeCurrentSourceBlockEntity.CHARGE_SLOT, CreativeCurrentSourceBlockEntity.CHARGE_SLOT + 1, false)) 
				{					
					moved = true;
				}
				else if((quickMovedSlotIndex >= CreativeCurrentSourceMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= CreativeCurrentSourceMenu.PLAYER_INVENTORY_END) && this.moveItemStackTo(slotsStack, CreativeCurrentSourceMenu.PLAYER_HOTBAR_START, CreativeCurrentSourceMenu.PLAYER_HOTBAR_END + 1, false)) 
				{											
					moved = true;
				}
				else if ((quickMovedSlotIndex >= CreativeCurrentSourceMenu.PLAYER_HOTBAR_START && quickMovedSlotIndex <= CreativeCurrentSourceMenu.PLAYER_HOTBAR_END) && this.moveItemStackTo(slotsStack, CreativeCurrentSourceMenu.PLAYER_INVENTORY_START, CreativeCurrentSourceMenu.PLAYER_INVENTORY_END + 1, false))
				{
					moved = true;
				}
				if(!moved) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(slotsStack, CreativeCurrentSourceMenu.PLAYER_INVENTORY_START, CreativeCurrentSourceMenu.PLAYER_HOTBAR_END + 1, false))
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
		} // end slot check

		return quickMovedStack;
	} // end quickMoveStack()

	@Override
	public boolean stillValid(Player player)
	{
		return AbstractContainerMenu.stillValid(this.m_access, player, this.m_openingBlock);	
	} // end stillValid()

	
	
	
	public int getOutput() 
	{
		return NetworkUtil.getPropertyValue(CreativeCurrentSourceBlockEntity.ACCESS_SPEC.get(CreativeCurrentSourceBlockEntity.OUTPUT_SPEC_KEY), this.m_data);
	} // end craftProgress()
	
	public void setOutput(int output) 
	{
		NetworkUtil.setPropertyValue(CreativeCurrentSourceBlockEntity.ACCESS_SPEC.get(CreativeCurrentSourceBlockEntity.OUTPUT_SPEC_KEY), this.m_data, output);
		// this.sendAllDataToRemote();
		// TODO, maybe something with the added slots instead?
	} // end heatProgress()
	
} // end class