package com.gsr.gsr_yatm.block.device.crystallizer;

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
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CrystallizerMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = (CrystallizerBlockEntity.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	
	private final ContainerLevelAccess m_access;
	private final Block m_openingBlockType;
	private final ContainerData m_data;
	
	
	
	// client side constructor
	public CrystallizerMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(CrystallizerBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(CrystallizerBlockEntity.DATA_SLOT_COUNT));
	} // end client constructor

	// server side constructor
	public CrystallizerMenu(int inventoryId, Inventory playerInventory, ContainerLevelAccess access, Block openingBlockType, IItemHandler objInventory, ContainerData data)
	{
		super(YATMMenuTypes.CRYSTALLIZER.get(), inventoryId);

		this.m_access = access;
		this.m_openingBlockType = openingBlockType;
		this.m_data = data;
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT, 116, 87));
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, 134, 87));
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.SEED_SLOT, 80, 51));
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.RESULT_SLOT, 80, 87));
		
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.POWER_SLOT, 8, 87));

		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(playerInventory, (x + (y * 9)) + 9, 8 + (x * 18), (120) + (y * 18)));
			}
		}

		for (int x = 0; x < 9; ++x)
		{
			this.addSlot(new Slot(playerInventory, x, 8 + (x * 18), 178));
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
			if (quickMovedSlotIndex == CrystallizerBlockEntity.RESULT_SLOT)
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
				if(SlotUtil.isValidTankFillSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT + 1, false)) 
				{						
					moved = true; //return ItemStack.EMPTY;					
				}
				else if(SlotUtil.isValidTankDrainSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT + 1, false)) 
				{											
					moved = true; //return ItemStack.EMPTY;					
				}
				else if(SlotUtil.isValidPowerSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CrystallizerBlockEntity.POWER_SLOT, CrystallizerBlockEntity.POWER_SLOT + 1, false)) 
				{					
					moved = true; //return ItemStack.EMPTY;
				}
				// seed slot consider
				else if(this.moveItemStackTo(slotsStack, CrystallizerBlockEntity.SEED_SLOT, CrystallizerBlockEntity.SEED_SLOT + 1, false)) 
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

	
	
	public float getCrystallizationProgress()
	{
		return 1f - (((float) this.m_data.get(CrystallizerBlockEntity.CRYSTALLIZE_PROGRESS_SLOT)) / ((float) this.m_data.get(CrystallizerBlockEntity.CRYSTALLIZE_TIME_SLOT)));
	} // end getCrystallizationProgress()
	
	public float inputTankFillProgress()
	{
		return 1f - (((float) this.m_data.get(CrystallizerBlockEntity.FILL_INPUT_TANK_TRANSFER_PROGRESS)) / ((float) this.m_data.get(CrystallizerBlockEntity.FILL_INPUT_TANK_TRANSFER_INITIAL)));
	} // end inputTankFillProgress()
	
	public float inputTankDrainProgress()
	{
		return 1f - (((float) this.m_data.get(CrystallizerBlockEntity.DRAIN_INPUT_TANK_TRANSFER_PROGRESS)) / ((float) this.m_data.get(CrystallizerBlockEntity.DRAIN_INPUT_TANK_TRANSFER_INITIAL)));
	} // end inputTankDrainProgress()
	
	
	
	public int getFluidAmount()
	{
		return this.m_data.get(CrystallizerBlockEntity.FLUID_AMOUNT_SLOT);
	} // end getFluidCapacity()
	
	public int getFluidCapacity()
	{
		return this.m_data.get(CrystallizerBlockEntity.FLUID_CAPACITY_SLOT);
	} // end getFluidCapacity()

	public Fluid getFluid() 
	{
		return NetworkUtil.getFluid(NetworkUtil.composeInt(this.m_data.get(CrystallizerBlockEntity.FLUID_INDEX_LOW_SLOT), this.m_data.get(CrystallizerBlockEntity.FLUID_INDEX_HIGH_SLOT)));
	} // end getFluid()	



} // end class