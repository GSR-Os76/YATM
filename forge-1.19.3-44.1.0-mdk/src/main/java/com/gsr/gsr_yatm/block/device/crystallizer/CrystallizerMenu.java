package com.gsr.gsr_yatm.block.device.crystallizer;

import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.NetworkUtilities;

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
		super(YATMMenuTypes.CRYSTALLIZER_MENU.get(), inventoryId);

		this.m_access = access;
		this.m_openingBlockType = openingBlockType;
		this.m_data = data;
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT, 116, 87));
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, 134, 87));
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.SEED_SLOT, 80, 51));
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.RESULT_SLOT, 80, 87));
		
		this.addSlot(new SlotItemHandler(objInventory, CrystallizerBlockEntity.POWER_SLOT, 8, 51));

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
		// TODO Auto-generated method stub
		return null;
	}

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
		return NetworkUtilities.getFluid(NetworkUtilities.composeInt(this.m_data.get(CrystallizerBlockEntity.FLUID_INDEX_LOW_SLOT), this.m_data.get(CrystallizerBlockEntity.FLUID_INDEX_HIGH_SLOT)));
	} // end getFluid()	



} // end class