package com.gsr.gsr_yatm.block.device.extruder;

import com.gsr.gsr_yatm.YATMMenuTypes;

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

public class ExtruderMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = ExtruderBlockEntity.LAST_INVENTORY_SLOT + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final ContainerLevelAccess m_access;
	private final Block m_openingBlockType;
	private final ContainerData m_data;
	
	
	
	// client side constructor
	public ExtruderMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(ExtruderBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(ExtruderBlockEntity.DATA_SLOT_COUNT));
	} // end client constructor

	// server side constructor
	public ExtruderMenu(int inventoryId, Inventory playerInventory, ContainerLevelAccess access, Block openingBlockType, IItemHandler objInventory, ContainerData data)
	{
		super(YATMMenuTypes.EXTRUDER_MENU.get(), inventoryId);

		this.m_access = access;
		this.m_openingBlockType = openingBlockType;
		this.m_data = data;
		this.addSlot(new SlotItemHandler(objInventory, ExtruderBlockEntity.INPUT_SLOT, 35, 29));
		this.addSlot(new SlotItemHandler(objInventory, ExtruderBlockEntity.DIE_SLOT, 53, 29));
		this.addSlot(new SlotItemHandler(objInventory, ExtruderBlockEntity.RESULT_SLOT, 107, 29));
		this.addSlot(new SlotItemHandler(objInventory, ExtruderBlockEntity.INPUT_REMAINDER_SLOT, 125, 29));
		
		this.addSlot(new SlotItemHandler(objInventory, ExtruderBlockEntity.POWER_SLOT, 8, 51));

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean stillValid(Player player)
	{
		return AbstractContainerMenu.stillValid(m_access, player, m_openingBlockType);
	} // end stillValid

	
	
	public float getExtrudeProgress()
	{
		return this.m_data.get(ExtruderBlockEntity.EXTRUDE_TIME_SLOT) == 0 
				? 0 
				: 1f - ((float)this.m_data.get(ExtruderBlockEntity.EXTRUDE_PROGRESS_SLOT) / (float)this.m_data.get(ExtruderBlockEntity.EXTRUDE_TIME_SLOT));
	} // end getExtrudeProgress()
	
	public float getPowerFillStatus()
	{
		return 1f - ((float)this.m_data.get(ExtruderBlockEntity.STORED_POWER_SLOT) / (float)this.m_data.get(ExtruderBlockEntity.POWER_CAPACITY_SLOT));
	} // end getExtrudeProgress()
	
} // end class