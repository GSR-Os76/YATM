package com.gsr.gsr_yatm.block.device.injector;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.network.NetworkUtil;
import com.gsr.gsr_yatm.utilities.network.container_data.AccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
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

public class InjectorMenu extends AbstractContainerMenu
{
	public static final int PLAYER_INVENTORY_START = (InjectorBlockEntity.INVENTORY_SLOT_COUNT - 1) + 1;
	public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 26;
	public static final int PLAYER_HOTBAR_START = PLAYER_INVENTORY_END + 1;
	public static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 8;
	
	private static ICompositeAccessSpecification s_spec = new CompositeAccessSpecification(List.of(Map.entry(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY, new AccessSpecification(0,1))));
	
	private final @NotNull ContainerLevelAccess m_access;
	private final @NotNull /* IEvent */ContainerData m_data;
	private final @Nullable Block m_openingBlock;
	private final @NotNull FluidTankDataReader m_tankReader;
	
	
	// client side constructor
	public InjectorMenu(int inventoryId, Inventory playerInventory)
	{
		this(inventoryId, playerInventory, ContainerLevelAccess.NULL, null, new ItemStackHandler(InjectorBlockEntity.INVENTORY_SLOT_COUNT), new /*SimpleEventContainerData*/SimpleContainerData(2));
	} // end client constructor

//	// server side constructor
//	public InjectorMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlock, @NotNull IItemHandler objInventory, @NotNull ContainerData data)
//	{
//		this(inventoryId, Objects.requireNonNull(playerInventory), Objects.requireNonNull(access), openingBlock, Objects.requireNonNull(objInventory), Objects.requireNonNull(wrapper.of(data)));
//	}
	
	// server side constructor
	public InjectorMenu(int inventoryId, @NotNull Inventory playerInventory, @NotNull ContainerLevelAccess access, @Nullable Block openingBlock, @NotNull IItemHandler objInventory, @NotNull /* IEvent */ContainerData data)
	{
		super(YATMMenuTypes.INJECTOR.get(), inventoryId);

		this.m_access = Objects.requireNonNull(access);
		this.m_data = Objects.requireNonNull(data);
		this.m_openingBlock = openingBlock;
		

		this.m_tankReader = new FluidTankDataReader(this.m_data, InjectorMenu.s_spec.get(InjectorBlockEntity.TANK_DATA_SPEC_KEY));
		
		this.addSlot(new SlotItemHandler(objInventory, InjectorBlockEntity.FILL_INPUT_TANK_SLOT, 116, 87));
		this.addSlot(new SlotItemHandler(objInventory, InjectorBlockEntity.DRAIN_INPUT_TANK_SLOT, 134, 87));
		this.addSlot(new SlotItemHandler(objInventory, InjectorBlockEntity.SUBSTRATE_SLOT, 80, 51));
		this.addSlot(new SlotItemHandler(objInventory, InjectorBlockEntity.RESULT_SLOT, 80, 87));
		
		this.addSlot(new SlotItemHandler(objInventory, InjectorBlockEntity.POWER_SLOT, 8, 87));

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
			if (quickMovedSlotIndex == InjectorBlockEntity.RESULT_SLOT)
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
				if(SlotUtil.isValidTankFillSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, InjectorBlockEntity.FILL_INPUT_TANK_SLOT, InjectorBlockEntity.FILL_INPUT_TANK_SLOT + 1, false)) 
				{						
					moved = true; //return ItemStack.EMPTY;					
				}
				else if(SlotUtil.isValidTankDrainSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, InjectorBlockEntity.DRAIN_INPUT_TANK_SLOT, InjectorBlockEntity.DRAIN_INPUT_TANK_SLOT + 1, false)) 
				{											
					moved = true; //return ItemStack.EMPTY;					
				}
				else if(SlotUtil.isValidPowerSlotInsert(slotsStack) && this.moveItemStackTo(slotsStack, InjectorBlockEntity.POWER_SLOT, InjectorBlockEntity.POWER_SLOT + 1, false)) 
				{					
					moved = true; //return ItemStack.EMPTY;
				}
				// seed slot consider
				else if(this.moveItemStackTo(slotsStack, InjectorBlockEntity.SUBSTRATE_SLOT, InjectorBlockEntity.SUBSTRATE_SLOT + 1, false)) 
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
		return AbstractContainerMenu.stillValid(this.m_access, player, this.m_openingBlock);	
	} // end stillValid()

	
	
	
	public float craftProgress() 
	{
		return NetworkUtil.getProgess(InjectorMenu.s_spec.get(InjectorBlockEntity.CRAFT_PROGRESS_SPEC_KEY), this.m_data);
	} // end craftProgress()
	
	public float inputTankFillProgress()
	{
		return NetworkUtil.getProgess(InjectorMenu.s_spec.get(InjectorBlockEntity.FILL_PROGRESS_SPEC_KEY), this.m_data);
	} // end inputTankFillProgress()
	
	public float inputTankDrainProgress()
	{
		return NetworkUtil.getProgess(InjectorMenu.s_spec.get(InjectorBlockEntity.DRAIN_PROGRESS_SPEC_KEY), this.m_data);
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
	
	
	
//	create object reference to this: (i) -> Objects.requireNonNull(subscriber).accept(InjectorMenu.this.getFluid())
//	/** Note: Event is only raised when this menu receives an updated value through it's data slot.*/
//	public void subscribe(@NotNull Consumer<Fluid> subscriber) 
//	{
//		this.m_data.subscribe(this.getFluidDataSlotIndex(), Objects.requireNonNull(subscriber));
//	} // end subscribe()
//	
//	public void unsubscribe(@NotNull Consumer<Fluid> subscriber) 
//	{
//		this.m_data.unsubscribe(this.getFluidDataSlotIndex(), 
//				);
//	} // end unsubcribe()
//
//	private int getFluidDataSlotIndex() 
//	{
//		return InjectorMenu.s_spec.get(InjectorBlockEntity.TANK_DATA_SPEC_KEY).startIndex() 
//				+ FluidHandlerContainerData.FLUID_INDEX;
//	} // end getFluidDataSlotIndex()
	
} // end class