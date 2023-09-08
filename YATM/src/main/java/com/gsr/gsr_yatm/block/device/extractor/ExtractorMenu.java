package com.gsr.gsr_yatm.block.device.extractor;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.network.BooleanFlagHandler;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ExtractorMenu extends AbstractContainerMenu
{

	public static final int PLAYER_INVENTORY_START = (ExtractorBlockEntity.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final ContainerLevelAccess m_access;
	private final Block m_openingBlockType;
	private final ContainerData m_data;
	
	
	
	// client side constructor
	public ExtractorMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(ExtractorBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(ExtractorBlockEntity.DATA_SLOT_COUNT));
	} // end client constructor

	// server side constructor
	public ExtractorMenu(int inventoryId, Inventory playerInventory, ContainerLevelAccess access, Block openingBlockType, IItemHandler objInventory, ContainerData data)
	{
		super(YATMMenuTypes.EXTRACTOR.get(), inventoryId);

		this.m_access = access;
		this.m_openingBlockType = openingBlockType;
		this.m_data = data;
		this.addSlot(new SlotItemHandler(objInventory, ExtractorBlockEntity.INPUT_SLOT, 62, 60));
		this.addSlot(new SlotItemHandler(objInventory, ExtractorBlockEntity.INPUT_REMAINDER_SLOT, 80, 87));
		this.addSlot(new SlotItemHandler(objInventory, ExtractorBlockEntity.DRAIN_RESULT_TANK_SLOT,  98, 87));
		
		this.addSlot(new SlotItemHandler(objInventory, ExtractorBlockEntity.POWER_SLOT, 8, 87));

		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(playerInventory, (x + (y * 9)) + 9, 8 + (x * 18), (84 + 36) + (y * 18)));
			}
		}

		for (int x = 0; x < 9; ++x)
		{
			this.addSlot(new Slot(playerInventory, x, 8 + (x * 18), 142 + 36));
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
			if (quickMovedSlotIndex == ExtractorBlockEntity.DRAIN_RESULT_TANK_SLOT)
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
				if(this.moveItemStackTo(slotsStack, ExtractorBlockEntity.INPUT_SLOT, ExtractorBlockEntity.INPUT_SLOT + 1, false)) 
				{						
					moved = true; //return ItemStack.EMPTY;					
				}
				else if(SlotUtil.isValidPowerSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, ExtractorBlockEntity.POWER_SLOT, ExtractorBlockEntity.POWER_SLOT + 1, false)) 
				{					
					moved = true; //return ItemStack.EMPTY;
				}
				else if((quickMovedSlotIndex >= PLAYER_INVENTORY_START && quickMovedSlotIndex <= PLAYER_INVENTORY_END) && this.moveItemStackTo(slotsStack, PLAYER_HOTBAR_START, PLAYER_HOTBAR_END + 1, false)) 
				{											
					moved = true; //return ItemStack.EMPTY;
				}
				else if ((quickMovedSlotIndex >= PLAYER_HOTBAR_START && quickMovedSlotIndex <= PLAYER_HOTBAR_END) && this.moveItemStackTo(slotsStack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END + 1, false))
				{
					moved = true; //return ItemStack.EMPTY;
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
	} // end stillValid()
	
	
	
	
	public boolean recipeHasRemainder()
	{
		return new BooleanFlagHandler(this.m_data.get(ExtractorBlockEntity.DATA_FLAGS_SLOT)).getValue(ExtractorBlockEntity.HAS_REMAINDER_FLAG_INDEX);
	} // end recipeHasRemainder()
	
	public float getExtractProgress() 
	{
		return 1f - ((float)(this.m_data.get(ExtractorBlockEntity.EXTRACT_PROGRESS_SLOT)) / ((float)(this.m_data.get(ExtractorBlockEntity.EXTRACT_TIME_SLOT))));
	} // end getExtractProgress()
	
	public float getResultTankDrainProgress() 
	{
		return 1f - ((float)(this.m_data.get(ExtractorBlockEntity.FLUID_TRANSFER_COUNT_DOWN_SLOT)) / ((float)(this.m_data.get(ExtractorBlockEntity.FLUID_TRANSFER_SIZE_SLOT))));
	} // end getExtractProgress()

	public int getFluidAmount()
	{
		return this.m_data.get(ExtractorBlockEntity.STORED_FlUID_AMOUNT_SLOT);
	} // end getFluidAmount()
	
	public int getFluidCapacity()
	{
		return this.m_data.get(ExtractorBlockEntity.FLUID_CAPACITY_SLOT);
	} // end getFluidCapacity()

	public Fluid getFluid()
	{
		int index = NetworkUtil.composeInt(this.m_data.get(ExtractorBlockEntity.FLUID_INDEX_LOW_SLOT), this.m_data.get(ExtractorBlockEntity.FLUID_INDEX_HIGH_SLOT));
		return NetworkUtil.getFluid(index);//Fluids.EMPTY;
	} // end getFluid()
	
	// current held things	

} // end class