package com.gsr.gsr_yatm.block.device.grinder;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;

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

public class GrinderMenu extends AbstractContainerMenu
{
	private final ContainerLevelAccess m_access;
	private final Block m_openingBlockType;
	private final ContainerData m_data;
	
	// client side constructor
		public GrinderMenu(int inventoryId, Inventory playerInventory)
		{
			this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(GrinderBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(GrinderBlockEntity.DATA_SLOT_COUNT));
		} // end client constructor

		// server side constructor
		public GrinderMenu(int inventoryId, Inventory playerInventory, ContainerLevelAccess access, Block openingBlockType, IItemHandler objInventory, ContainerData data)
		{
			super(YATMMenuTypes.GRINDER_MENU.get(), inventoryId);

			this.m_access = access;
			this.m_openingBlockType = openingBlockType;
			this.m_data = data;
			this.addSlot(new SlotItemHandler(objInventory, GrinderBlockEntity.INPUT_SLOT, 35, 29));
			//this.addSlot(new SlotItemHandler(objInventory, GrinderBlockEntity.DIE_SLOT, 53, 29));
			this.addSlot(new SlotItemHandler(objInventory, GrinderBlockEntity.RESULT_SLOT, 107, 29));
			//this.addSlot(new SlotItemHandler(objInventory, GrinderBlockEntity.INPUT_REMAINDER_SLOT, 125, 29));
			
			this.addSlot(new SlotItemHandler(objInventory, GrinderBlockEntity.POWER_SLOT, 8, 51));

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
		public ItemStack quickMoveStack(Player p_38941_, int p_38942_)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean stillValid(Player p_38874_)
		{
			// TODO Auto-generated method stub
			return false;
		}

}
