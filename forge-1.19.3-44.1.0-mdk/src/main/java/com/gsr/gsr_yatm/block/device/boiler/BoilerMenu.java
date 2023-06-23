package com.gsr.gsr_yatm.block.device.boiler;

import com.gsr.gsr_yatm.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.NetworkUtilities;
import com.gsr.gsr_yatm.utilities.SlotUtilities;

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

public class BoilerMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = BoilerBlockEntity.LAST_INVENTORY_SLOT + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final ContainerLevelAccess m_access;
	private final Block m_openingBlockType;
	private final ContainerData m_data;



	// client side constructor
	public BoilerMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(BoilerBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(BoilerBlockEntity.DATA_SLOT_COUNT));//, new SimpleContainerData(4));
	} // end client constructor

	// server side constructor
	public BoilerMenu(int inventoryId, Inventory playerInventory, ContainerLevelAccess access, Block openingBlockType, IItemHandler objInventory, ContainerData data)//, ContainerData container)
	{
		super(YATMMenuTypes.BOILER_MENU.get(), inventoryId);

		this.m_access = access;
		this.m_openingBlockType = openingBlockType;
		this.m_data = data;
		//this.m_inputTankCapacity = data.get(BoilerBlockEntity.INPUT_CAPACITY_INDEX);
		//this.m_outputTankCapacity = data.get(BoilerBlockEntity.OUTPUT_CAPACITY_INDEX);
		// checkObjInventorySize();
		int newYDownShift = 36;
		int yPos = 51 + newYDownShift;
		this.addSlot(new SlotItemHandler(objInventory, BoilerBlockEntity.FILL_INPUT_TANK_SLOT, 8, yPos));
		this.addSlot(new SlotItemHandler(objInventory, BoilerBlockEntity.DRAIN_INPUT_TANK_SLOT, 8 + 18, yPos));
		this.addSlot(new SlotItemHandler(objInventory, BoilerBlockEntity.DRAIN_RESULT_TANK_SLOT, 152, yPos));
		
		this.addSlot(new SlotItemHandler(objInventory, BoilerBlockEntity.FUEL_SLOT, 80, yPos));
		
		// add player inventory. indexing of the slots begins with hotbar, notably
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
	} // end server constructor



	@Override
	public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex)
	{
		// get moved stack
		// if from player, and is a fluidHandler or a fuel, move as appropriate
		ItemStack quickMovedStack = ItemStack.EMPTY;
		Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);
		if (quickMovedSlot != null && quickMovedSlot.hasItem())
		{
			ItemStack slotsStack = quickMovedSlot.getItem();
			if (quickMovedSlotIndex == BoilerBlockEntity.DRAIN_RESULT_TANK_SLOT)
			{
				
				if (!this.moveItemStackTo(slotsStack, PLAYER_INVENTORY_START, PLAYER_HOTBAR_END + 1, true))
				{					
					return ItemStack.EMPTY;
				}

				quickMovedSlot.onQuickCraft(slotsStack, quickMovedStack);
			}
			else if (quickMovedSlotIndex >= PLAYER_INVENTORY_START && quickMovedSlotIndex <= PLAYER_HOTBAR_END)
			{				
				// if fluid handling
				//...
				// elif valid fuel
				// ...
				// elif from inventory
					// go to hotbar
				// elif from hotbar
					// go to inventory
				if(SlotUtilities.isValidTankFillSlotInsert(slotsStack)) 
				{
					if(!this.moveItemStackTo(slotsStack, BoilerBlockEntity.FIRST_FILL_FLUID_SLOT, BoilerBlockEntity.LAST_FILL_FLUID_SLOT + 1, false)) 
					{						
						return ItemStack.EMPTY;
					}
				}
				else if(SlotUtilities.isValidTankDrainSlotInsert(slotsStack)) 
				{
					if(!this.moveItemStackTo(slotsStack, BoilerBlockEntity.FIRST_DRAIN_FLUID_SLOT, BoilerBlockEntity.LAST_DRAIN_FLUID_SLOT + 1, false)) 
					{						
						return ItemStack.EMPTY;
					}
				}
				else if(SlotUtilities.isValidHeatingSlotInsert(slotsStack)) 
				{
					if(!this.moveItemStackTo(slotsStack, BoilerBlockEntity.FUEL_SLOT, BoilerBlockEntity.FUEL_SLOT + 1, false)) 
					{						
						return ItemStack.EMPTY;
					}
				}
				else if(quickMovedSlotIndex >= PLAYER_INVENTORY_START && quickMovedSlotIndex <= PLAYER_INVENTORY_END) 
				{
					if(!this.moveItemStackTo(slotsStack, PLAYER_HOTBAR_START, PLAYER_HOTBAR_END + 1, false)) 
					{						
						return ItemStack.EMPTY;
					}
				}
				else if ((quickMovedSlotIndex >= PLAYER_HOTBAR_START && quickMovedSlotIndex <= PLAYER_HOTBAR_END) && !this.moveItemStackTo(slotsStack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END + 1, false))
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
		return AbstractContainerMenu.stillValid(m_access, player, m_openingBlockType);
	} // end stillValid();

	
	
	public float burnFractionRemaining()
	{
		int elapsed = this.m_data.get(BoilerBlockEntity.BURN_TIME_ELAPSED_INDEX);
		int time =  this.m_data.get(BoilerBlockEntity.BURN_TIME_INDEX);
		return time == 0 ? 0f : ((float)elapsed / (float)time);
	} // end burnPercentageRemaining()
	
	public float fillInputTankTransferProgress() 
	{
		return 1f - ((float)this.m_data.get(BoilerBlockEntity.FILL_INPUT_TANK_TRANSFER_PROGRESS) / (float)this.m_data.get(BoilerBlockEntity.FILL_INPUT_TANK_TRANSFER_INITIAL));
	} // end fillInputTankTransferProgress()
	
	public float drainInputTankTransferProgress() 
	{
		return 1f - ((float)this.m_data.get(BoilerBlockEntity.DRAIN_INPUT_TANK_TRANSFER_PROGRESS) / (float)this.m_data.get(BoilerBlockEntity.DRAIN_INPUT_TANK_TRANSFER_INITIAL));
	} // end drainInputTankTransferProgress()
	
	public float drainResultTankTransferProgress() 
	{
		return 1f - ((float)this.m_data.get(BoilerBlockEntity.DRAIN_RESULT_TANK_TRANSFER_PROGRESS) / (float)this.m_data.get(BoilerBlockEntity.DRAIN_RESULT_TANK_TRANSFER_INITIAL));
	} // end drainResultTankTransferProgress()

	
	
	public int getInputTankCapacity() 
	{
		return this.m_data.get(BoilerBlockEntity.INPUT_CAPACITY_INDEX);
	} // end getInputTankCapacity() 
	
	public FluidStack getInputTankContents()
	{
		int index = NetworkUtilities.composeInt(this.m_data.get(BoilerBlockEntity.INPUT_TANK_FLUID_INDEX_LOW), this.m_data.get(BoilerBlockEntity.INPUT_TANK_FLUID_INDEX_HIGH));
		return new FluidStack(NetworkUtilities.getFluid(index), this.m_data.get(BoilerBlockEntity.INPUT_HOLDING_INDEX));
	} // end getInputTankFluid()
	


	
	public int getOutputTankCapacity() 
	{
		return this.m_data.get(BoilerBlockEntity.RESULT_CAPACITY_INDEX);
	} // end getOutputTankCapacity() 
	
	public FluidStack getOutputTankContents()
	{
		int index = NetworkUtilities.composeInt(this.m_data.get(BoilerBlockEntity.RESULT_TANK_FLUID_INDEX_LOW), this.m_data.get(BoilerBlockEntity.RESULT_TANK_FLUID_INDEX_HIGH));
		return new FluidStack(NetworkUtilities.getFluid(index), this.m_data.get(BoilerBlockEntity.RESULT_HOLDING_INDEX));
	} // end getOutputTankFluid()
	
	

	// ForgeHoooks.getBurnTime(x, RecipeType.Smelting);

} // end class