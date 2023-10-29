package com.gsr.gsr_yatm.block.device.crucible;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CrucibleMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = (CrucibleBlockEntity.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull ContainerData m_data;
	private final @Nullable Block m_openingBlock;
	private final @NotNull FluidTankDataReader m_tankReader;
	
	
	
	// client side constructor
	public CrucibleMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(CrucibleBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(CrucibleBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	// server side constructor
	public CrucibleMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlock, @NotNull IItemHandler objInventory, @NotNull /* IEvent */ContainerData data)
	{
		super(YATMMenuTypes.CRUCIBLE.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_data = Objects.requireNonNull(data);
		this.m_openingBlock = openingBlock;
		
		this.m_tankReader = new FluidTankDataReader(this.m_data, CrucibleBlockEntity.ACCESS_SPEC.get(CrucibleBlockEntity.TANK_DATA_SPEC_KEY));
		
		this.addSlot(new SlotItemHandler(objInventory, CrucibleBlockEntity.INPUT_SLOT, 44, 51));
		this.addSlot(new SlotItemHandler(objInventory, CrucibleBlockEntity.HEAT_SLOT, 44, 87));
		this.addSlot(new SlotItemHandler(objInventory, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT, 116, 87));
		

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
			if (quickMovedSlotIndex == CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT)
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
				if(this.moveItemStackTo(slotsStack, CrucibleBlockEntity.INPUT_SLOT, CrucibleBlockEntity.INPUT_SLOT + 1, false)) 
				{						
					moved = true;					
				}
				else if(SlotUtil.isValidHeatingSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CrucibleBlockEntity.HEAT_SLOT, CrucibleBlockEntity.HEAT_SLOT + 1, false)) 
				{					
					moved = true;
				}
				else if(SlotUtil.isValidTankDrainSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT + 1, false)) 
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
		} // end slot check

		return quickMovedStack;
	} // end quickMoveStack()

	@Override
	public boolean stillValid(Player player)
	{
		return AbstractContainerMenu.stillValid(this.m_access, player, this.m_openingBlock);	
	} // end stillValid()

	
	
	
	public float craftProgress() 
	{
		return NetworkUtil.getProgess(CrucibleBlockEntity.ACCESS_SPEC.get(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY), this.m_data);
	} // end craftProgress()
	
	public float burnProgress() 
	{
		return NetworkUtil.getRemainingZeroIfNotRunning(CrucibleBlockEntity.ACCESS_SPEC.get(CrucibleBlockEntity.BURN_PROGRESS_SPEC_KEY), this.m_data);
	} // end heatProgress()
	
	public float resultTankDrainProgress()
	{
		return NetworkUtil.getProgess(CrucibleBlockEntity.ACCESS_SPEC.get(CrucibleBlockEntity.DRAIN_PROGRESS_SPEC_KEY), this.m_data);
	} // end resultTankDrainProgress()
	
	
	
	public int getTemperature() 
	{
		return NetworkUtil.getPropertyValue(CrucibleBlockEntity.ACCESS_SPEC.get(CrucibleBlockEntity.TEMPERATURE_SPEC_KEY), this.m_data);	
	} // end temperature()
	
	public int getMaxTemperature() 
	{
		return NetworkUtil.getPropertyValue(CrucibleBlockEntity.ACCESS_SPEC.get(CrucibleBlockEntity.MAX_TEMPERATURE_SPEC_KEY), this.m_data);
	} // end maxTemperature()
	
	
	
	public int getFluidAmount()
	{
		return this.m_tankReader.getAmount();
	} // end getFluidCapacity()
	
	public int getFluidCapacity()
	{
		return this.m_tankReader.getCapacity();
	} // end getFluidCapacity()

	public @NotNull Fluid getFluid() 
	{
		return this.m_tankReader.getFluid();
	} // end getFluid()	
	
} // end class