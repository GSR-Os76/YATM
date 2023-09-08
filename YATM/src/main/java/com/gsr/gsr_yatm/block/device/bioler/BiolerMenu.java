package com.gsr.gsr_yatm.block.device.bioler;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.network.FluidHandlerContainerData;
import com.gsr.gsr_yatm.utilities.network.NetworkUtil;
import com.gsr.gsr_yatm.utilities.network.SubsettingContainerData;

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

public class BiolerMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = BiolerBlockEntity.POWER_SLOT + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final ContainerLevelAccess m_access;
	private final Block m_openingBlockType;
	private final ContainerData m_data;

	// private final BooleanFlagHandler m_flags;


	
	// client side constructor
	public BiolerMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(BiolerBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(BiolerBlockEntity.DATA_SLOT_COUNT));
	} // end client constructor

	// server side constructor
	public BiolerMenu(int inventoryId, Inventory playerInventory, ContainerLevelAccess access, Block openingBlockType, IItemHandler objInventory, ContainerData data)
	{
		super(YATMMenuTypes.BIOLER.get(), inventoryId);

		this.m_access = access;
		this.m_openingBlockType = openingBlockType;
		this.m_data = data;
		
		int newYDownShift = 36;
		this.addSlot(new SlotItemHandler(objInventory, BiolerBlockEntity.INPUT_SLOT, 62, 60));
		this.addSlot(new SlotItemHandler(objInventory, BiolerBlockEntity.DRAIN_RESULT_TANK_SLOT,  98, 87));
		
		this.addSlot(new SlotItemHandler(objInventory, BiolerBlockEntity.POWER_SLOT, 8, 87));

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
		// this.m_flags = new BooleanFlagHandler(new SubsettingContainerData(BiolerBlockEntity.getFlagsData(), this.m_data));
	} // end server constructor



	@Override
	public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex)
	{
		ItemStack quickMovedStack = ItemStack.EMPTY;
		Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);
		if (quickMovedSlot != null && quickMovedSlot.hasItem())
		{
			ItemStack slotsStack = quickMovedSlot.getItem();
			if (quickMovedSlotIndex == BiolerBlockEntity.DRAIN_RESULT_TANK_SLOT)
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
				if(this.moveItemStackTo(slotsStack, BiolerBlockEntity.INPUT_SLOT, BiolerBlockEntity.INPUT_SLOT + 1, false)) 
				{						
					moved = true;				
				}
				else if(SlotUtil.isValidTankDrainSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, BiolerBlockEntity.DRAIN_RESULT_TANK_SLOT, BiolerBlockEntity.DRAIN_RESULT_TANK_SLOT + 1, false)) 
				{					
					moved = true;
				}
				else if(SlotUtil.isValidPowerSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, BiolerBlockEntity.POWER_SLOT, BiolerBlockEntity.POWER_SLOT + 1, false)) 
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

	
	
	public float getCraftProgress()
	{
		return NetworkUtil.getProgess(BiolerBlockEntity.getCraftData(), this.m_data);
	} // end getCraftProgess
	
	public float getResultTankDrainProgress()
	{
		return  NetworkUtil.getProgess(BiolerBlockEntity.getDrainResultTankData(), this.m_data);
	} // end getResultTankDrainProgress()

	public int getResultTankCapacity()
	{
		return FluidHandlerContainerData.getCapacity(new SubsettingContainerData(BiolerBlockEntity.getResultTankData(), this.m_data));
	} // end getResultTankCapacity
	
	public FluidStack getResultTankContents()
	{
		return FluidHandlerContainerData.getContents(new SubsettingContainerData(BiolerBlockEntity.getResultTankData(), this.m_data));
	} // end getResultTankContents()
	
} // end class