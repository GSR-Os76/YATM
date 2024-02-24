package com.gsr.gsr_yatm.block.device.crafting.extractor;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.crafting.injector.InjectorBlockEntity;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.NetworkUtil;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.bool.BooleanFlagReader;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.current.CurrentDataReader;
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
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull ContainerData m_data;
	private final @Nullable Block m_openingBlock;
	
	private final @NotNull CurrentDataReader m_currentReader;
	private final @NotNull BooleanFlagReader m_flagReader;
	private final @NotNull FluidTankDataReader m_tankReader;
	
	
	
	// client side constructor
	public ExtractorMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(ExtractorBlockEntity.INVENTORY_SLOT_COUNT), new SimpleContainerData(ExtractorBlockEntity.ACCESS_SPEC.getCount()));
	} // end client constructor

	// server side constructor
	public ExtractorMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlock, @NotNull IItemHandler objInventory, @NotNull ContainerData data)
	{
		super(YATMMenuTypes.EXTRACTOR.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_data = Objects.requireNonNull(data);
		this.m_openingBlock = openingBlock;
		
		this.m_tankReader = new FluidTankDataReader(this.m_data, ExtractorBlockEntity.ACCESS_SPEC.get(ExtractorBlockEntity.RESULT_TANK_SPEC_KEY));
		this.m_currentReader = new CurrentDataReader(this.m_data, ExtractorBlockEntity.ACCESS_SPEC.get(ExtractorBlockEntity.CURRENT_DATA_SPEC_KEY));
		this.m_flagReader = new BooleanFlagReader(this.m_data, ExtractorBlockEntity.ACCESS_SPEC.get(ExtractorBlockEntity.FLAGS_SPEC_KEY));
		
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
					moved = true;				
				}
				else if(SlotUtil.isValidTankDrainSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, ExtractorBlockEntity.DRAIN_RESULT_TANK_SLOT, ExtractorBlockEntity.DRAIN_RESULT_TANK_SLOT + 1, false)) 
				{					
					moved = true;
				}
				else if(SlotUtil.isValidPowerSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, ExtractorBlockEntity.POWER_SLOT, ExtractorBlockEntity.POWER_SLOT + 1, false)) 
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
		return AbstractContainerMenu.stillValid(this.m_access, player, this.m_openingBlock);	
	} // end stillValid()
	
	
	
	
	public boolean recipeHasRemainder()
	{
		return this.m_flagReader.getValue(ExtractorBlockEntity.HAS_REMAINDER_FLAG_INDEX);
	} // end recipeHasRemainder()
	
	public float craftProgress() 
	{
		return NetworkUtil.getProgess(InjectorBlockEntity.ACCESS_SPEC.get(InjectorBlockEntity.CRAFT_PROGRESS_SPEC_KEY), this.m_data);
	} // end craftProgress()
	
	public float resultTankDrainProgress()
	{
		return NetworkUtil.getProgess(InjectorBlockEntity.ACCESS_SPEC.get(InjectorBlockEntity.DRAIN_INPUT_PROGRESS_SPEC_KEY), this.m_data);
	} // end inputTankDrainProgress()
	
	
	
	public int getFluidAmount()
	{
		return this.m_tankReader.getAmount();
	} // end getFluidCapacity()
	
	public int getFluidCapacity()
	{
		return this.m_tankReader.getCapacity();
	} // end getFluidCapacity()

	public Fluid getFluid() 
	{
		return this.m_tankReader.getFluid();
	} // end getFluid()	
	
	
	
	public @NotNegative int getCurrentCapacity()
	{
		return this.m_currentReader.getCapacity();
	} // end getResultTankCapacity()
	
	public @NotNegative int getCurrentStored()
	{
		return this.m_currentReader.getStored();
	} // end getResultTankCapacity()
	
	// current held things	

} // end class