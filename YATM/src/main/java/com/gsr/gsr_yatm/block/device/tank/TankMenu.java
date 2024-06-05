package com.gsr.gsr_yatm.block.device.tank;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.gui.behavior.fluid.IFluidStackInfoProvider;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.fluid.FluidTankDataReader;

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

public class TankMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = TankBlockEntity.LAST_INVENTORY_SLOT + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull FluidTankDataReader m_tankReader;
	private final @Nullable Block m_openingBlockType;
	


	public TankMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(TankBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(TankBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	public TankMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlockType, @NotNull IItemHandler objInventory, @NotNull ContainerData data)
	{
		super(YATMMenuTypes.TANK.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_openingBlockType = openingBlockType;

		this.m_tankReader = new FluidTankDataReader(Objects.requireNonNull(data), TankBlockEntity.ACCESS_SPEC.get(TankBlockEntity.TANK_DATA_SPEC_KEY));
		
		int yShift = 9;
		this.addSlot(new SlotItemHandler(objInventory, TankBlockEntity.FILL_TANK_SLOT, 44, 41));
		this.addSlot(new SlotItemHandler(objInventory, TankBlockEntity.DRAIN_TANK_SLOT, 116, 41));
		
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
			if (quickMovedSlotIndex >= TankMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= TankMenu.PLAYER_HOTBAR_END)
			{			
				boolean moved = false;
				if(SlotUtil.isValidTankFillSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, TankBlockEntity.FILL_TANK_SLOT, TankBlockEntity.FILL_TANK_SLOT + 1, false)) 
				{					
					moved = true;				
				}
				else if(SlotUtil.isValidTankDrainSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, TankBlockEntity.DRAIN_TANK_SLOT, TankBlockEntity.DRAIN_TANK_SLOT + 1, false)) 
				{				
					moved = true;
				}
				else if((quickMovedSlotIndex >= TankMenu.PLAYER_INVENTORY_START && quickMovedSlotIndex <= TankMenu.PLAYER_INVENTORY_END) && this.moveItemStackTo(slotsStack, TankMenu.PLAYER_HOTBAR_START, TankMenu.PLAYER_HOTBAR_END + 1, false)) 
				{						
					moved = true;
				}
				else if ((quickMovedSlotIndex >= TankMenu.PLAYER_HOTBAR_START && quickMovedSlotIndex <= TankMenu.PLAYER_HOTBAR_END) && this.moveItemStackTo(slotsStack, TankMenu.PLAYER_INVENTORY_START, TankMenu.PLAYER_INVENTORY_END + 1, false))
				{
					moved = true;
				}			
				if(!moved) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(slotsStack, TankMenu.PLAYER_INVENTORY_START, TankMenu.PLAYER_HOTBAR_END + 1, false))
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

	
	
	public @NotNull IFluidStackInfoProvider getTankContentsGetter() 
	{
		return this.m_tankReader;
	} // end getTankContents()
	
} // end class