package com.gsr.gsr_yatm.block.device.heat_furnace;

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

public class HeatFurnaceMenu extends AbstractContainerMenu
{

	public static final int PLAYER_INVENTORY_START = (HeatFurnaceBlockEntity.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull ContainerData m_data;
	private final @Nullable Block m_openingBlockType;
	


	// client side constructor
	public HeatFurnaceMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(HeatFurnaceBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(HeatFurnaceBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	// server side constructor
	public HeatFurnaceMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlockType, @NotNull IItemHandler objInventory, @NotNull ContainerData data)
	{
		super(YATMMenuTypes.HEAT_FURNACE.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_openingBlockType = openingBlockType;
		this.m_data = Objects.requireNonNull(data);

		int yShift = 0;
		this.addSlot(new SlotItemHandler(objInventory, HeatFurnaceBlockEntity.INPUT_SLOT, 58, 47));
		this.addSlot(new SlotItemHandler(objInventory, HeatFurnaceBlockEntity.RESULT_SLOT, 118, 47));		
		this.addSlot(new SlotItemHandler(objInventory, HeatFurnaceBlockEntity.HEAT_SLOT, 38, 47));
		
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
			if (quickMovedSlotIndex == HeatFurnaceBlockEntity.RESULT_SLOT)
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
				if(this.moveItemStackTo(slotsStack, HeatFurnaceBlockEntity.INPUT_SLOT, HeatFurnaceBlockEntity.INPUT_SLOT + 1, false)) 
				{					
					moved = true;				
				}
				else if(SlotUtil.isValidHeatingSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, HeatFurnaceBlockEntity.HEAT_SLOT, HeatFurnaceBlockEntity.HEAT_SLOT + 1, false)) 
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
		return NetworkUtil.getProgess(HeatFurnaceBlockEntity.ACCESS_SPEC.get(HeatFurnaceBlockEntity.CRAFT_PROGRESS_SPEC_KEY), this.m_data);
	} // end craftProgress()
	
	public float burnProgress() 
	{
		return NetworkUtil.getRemainingZeroIfNotRunning(HeatFurnaceBlockEntity.ACCESS_SPEC.get(HeatFurnaceBlockEntity.BURN_PROGRESS_SPEC_KEY), this.m_data);
	} // end heatProgress()
	
	
	
	public int getTemperature() 
	{
		return NetworkUtil.getPropertyValue(HeatFurnaceBlockEntity.ACCESS_SPEC.get(HeatFurnaceBlockEntity.TEMPERATURE_SPEC_KEY), this.m_data);	
	} // end temperature()
	
	public int getMaxTemperature() 
	{
		return NetworkUtil.getPropertyValue(HeatFurnaceBlockEntity.ACCESS_SPEC.get(HeatFurnaceBlockEntity.MAX_TEMPERATURE_SPEC_KEY), this.m_data);
	} // end maxTemperature()

} // end class