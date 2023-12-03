package com.gsr.gsr_yatm.block.device.crystallizer;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.CurrentDataReader;
import com.gsr.gsr_yatm.utilities.network.FluidTankDataReader;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CrystallizerMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = (CrystallizerBlockEntity.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull ContainerData m_data;
	private final @Nullable Block m_openingBlockType;
	private final @NotNull CurrentDataReader m_currentReader;
	private final @NotNull FluidTankDataReader m_tankReader;
	
	
	
	// client side constructor
	public CrystallizerMenu(int inventoryId, @NotNull Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(CrystallizerBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(CrystallizerBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	// server side constructor
	public CrystallizerMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlockType, @NotNull IItemHandler objInventory, @NotNull ContainerData data)
	{
		super(YATMMenuTypes.CRYSTALLIZER.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_data = Objects.requireNonNull(data);
		this.m_openingBlockType = openingBlockType;
		
		this.m_currentReader = new CurrentDataReader(this.m_data, CrystallizerBlockEntity.ACCESS_SPEC.get(CrystallizerBlockEntity.CURRENT_DATA_SPEC_KEY));
		this.m_tankReader = new FluidTankDataReader(this.m_data, CrystallizerBlockEntity.ACCESS_SPEC.get(CrystallizerBlockEntity.TANK_DATA_SPEC_KEY));
		
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
				if (!this.moveItemStackTo(slotsStack, CrystallizerMenu.PLAYER_INVENTORY_START, CrystallizerMenu.PLAYER_HOTBAR_END + 1, true))
				{					
					return ItemStack.EMPTY;
				}
				quickMovedSlot.onQuickCraft(slotsStack, quickMovedStack);
			}
			else if (quickMovedSlotIndex >= CrystallizerMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= CrystallizerMenu.PLAYER_HOTBAR_END)
			{	
				boolean moved = false;
				if(SlotUtil.isValidTankFillSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT + 1, false)) 
				{						
					moved = true;
				}
				else if(SlotUtil.isValidTankDrainSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT + 1, false)) 
				{											
					moved = true;
				}
				else if(SlotUtil.isValidPowerSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CrystallizerBlockEntity.POWER_SLOT, CrystallizerBlockEntity.POWER_SLOT + 1, false)) 
				{					
					moved = true;
				}
				else if(this.moveItemStackTo(slotsStack, CrystallizerBlockEntity.SEED_SLOT, CrystallizerBlockEntity.SEED_SLOT + 1, false)) 
				{					
					moved = true;
				}
				else if((quickMovedSlotIndex >= CrystallizerMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= CrystallizerMenu.PLAYER_INVENTORY_END) && this.moveItemStackTo(slotsStack, CrystallizerMenu.PLAYER_HOTBAR_START, CrystallizerMenu.PLAYER_HOTBAR_END + 1, false)) 
				{											
					moved = true;
				}
				else if ((quickMovedSlotIndex >= CrystallizerMenu.PLAYER_HOTBAR_START && quickMovedSlotIndex <= CrystallizerMenu.PLAYER_HOTBAR_END) && this.moveItemStackTo(slotsStack, CrystallizerMenu.PLAYER_INVENTORY_START, CrystallizerMenu.PLAYER_INVENTORY_END + 1, false))
				{
					moved = true;
				}
				if(!moved) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(slotsStack, CrystallizerMenu.PLAYER_INVENTORY_START, CrystallizerMenu.PLAYER_HOTBAR_END + 1, false))
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

	
	
	public float getCraftProgress()
	{
		return NetworkUtil.getProgess(CrystallizerBlockEntity.ACCESS_SPEC.get(CrystallizerBlockEntity.CRAFT_PROGESS_TAG_NAME), this.m_data);
	} // end getCraftProgess()
	
	public float getInputTankDrainProgress()
	{
		return NetworkUtil.getProgess(CrystallizerBlockEntity.ACCESS_SPEC.get(CrystallizerBlockEntity.DRAIN_PROGRESS_SPEC_KEY), this.m_data);
	} // end getResultTankDrainProgress()

	public float getInputTankFillProgress()
	{
		return NetworkUtil.getProgess(CrystallizerBlockEntity.ACCESS_SPEC.get(CrystallizerBlockEntity.FILL_PROGRESS_SPEC_KEY), this.m_data);
	} // end getResultTankDrainProgress()
	
	
	
	public @NotNegative int getCurrentCapacity()
	{
		return this.m_currentReader.getCapacity();
	} // end getResultTankCapacity
	
	public @NotNegative int getCurrentStored()
	{
		return this.m_currentReader.getStored();
	} // end getResultTankCapacity
	
	public @NotNegative int getInputTankCapacity()
	{
		return this.m_tankReader.getCapacity();
	} // end getResultTankCapacity
	
	public @NotNull FluidStack getInputTankContents()
	{
		return this.m_tankReader.getFluidStack();
	} // end getResultTankContents()
} // end class