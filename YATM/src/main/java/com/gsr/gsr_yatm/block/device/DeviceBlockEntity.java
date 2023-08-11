package com.gsr.gsr_yatm.block.device;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.implementation.CurrentUnitHandler;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;
import com.gsr.gsr_yatm.utilities.capability.item.ConfigurableInventoryWrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

// TODO, subclass BasicBlockEntity probably, also probably rename that class
public abstract class DeviceBlockEntity extends BlockEntity 
{	
	public static final String SETUP_TAG_NAME = "setup";
	public static final String INVENTORY_TAG_NAME = "inventory";
	public static final String CURRENT_HANDLER_TAG_NAME = "current";
	
	protected ItemStackHandler m_rawInventory;
	protected ConfigurableInventoryWrapper m_uncheckedInventory;
	protected ConfigurableInventoryWrapper m_inventory;
	
	protected CurrentUnitHandler m_internalCurrentStorer;	
	
	protected int m_maxSafeCurrentTransfer;
	protected int m_currentTransferedThisTick = 0;
	
	
	
	public DeviceBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState, int inventorySlotCount)
	{
		super(type, blockPos, blockState);
		this.m_rawInventory = new ItemStackHandler(inventorySlotCount);
		this.m_uncheckedInventory = ConfigurableInventoryWrapper.Builder.of(this.m_rawInventory).onInsertion(this::onItemInsertion).onWithdrawal(this::onItemWithdrawal).build();
		this.m_inventory = ConfigurableInventoryWrapper.Builder.of(this.m_uncheckedInventory).slotValidator(this::itemInsertionValidator).build();
	} // end constructor
	
	protected abstract @NotNull CompoundTag setupToNBT();
	protected abstract void setupFromNBT(@NotNull CompoundTag tag);
	
	
	
	public IItemHandler getInventory()
	{
		return m_inventory;
	} // end getInventory()

	public abstract ContainerData getDataAccessor();
	
	protected abstract boolean itemInsertionValidator(int slot, ItemStack itemStack, boolean simulate);

	protected void onItemInsertion(int slot, ItemStack stack)
	{
		this.setChanged();
	} // end onItemInsertion()

	protected void onItemWithdrawal(int slot, ItemStack stack)
	{
		this.setChanged();
	} // onItemWithdrawal
	
	protected void onCurrentExchanged(int amount) 
	{
		this.m_currentTransferedThisTick += amount;
		this.setChanged();
		if(this.m_currentTransferedThisTick > this.m_maxSafeCurrentTransfer) 
		{
			// TODO, detonate. or rather call a method that can be overriden that by default will detonates this
		}
	} // end onCurrentExchanged()
	
	protected void onFluidContentsChanged(FluidStack stack) 
	{		
		this.setChanged();
	} // end onFluidContentsChanged()
	
	
	public static void tick(Level level, BlockPos pos, BlockState blockState, DeviceBlockEntity blockEntity)
	{
		if (level.isClientSide)
		{
			blockEntity.clientTick(level, pos, blockState);
		}
		else
		{
			blockEntity.serverTick(level, pos, blockState);
		}
	} // end tick()

	public void clientTick(Level level, BlockPos pos, BlockState blockState)
	{
		
	} // end clientTick()
	
	public void serverTick(Level level, BlockPos pos, BlockState blockState)
	{
		this.m_currentTransferedThisTick = 0;
	} // end serverTick()
	
	
	
	
	protected IItemHandler getDropInventory() 
	{
		return this.m_rawInventory;
	} // end getDropInventory()
	
	public void blockBroken() 
	{
		InventoryUtilities.drop(this.level, this.worldPosition, this.getDropInventory());
	} // end blockBroken()

	
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		tag.put(DeviceBlockEntity.SETUP_TAG_NAME, this.setupToNBT());
		tag.put(DeviceBlockEntity.INVENTORY_TAG_NAME, this.m_rawInventory.serializeNBT());
		if(this.m_internalCurrentStorer != null && this.m_internalCurrentStorer.stored() > 0) 
		{
			tag.put(DeviceBlockEntity.CURRENT_HANDLER_TAG_NAME, this.m_internalCurrentStorer.serializeNBT());
		}
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if(tag.contains(DeviceBlockEntity.SETUP_TAG_NAME)) 
		{
			this.setupFromNBT(tag.getCompound(DeviceBlockEntity.SETUP_TAG_NAME));
		}
		if(tag.contains(DeviceBlockEntity.INVENTORY_TAG_NAME)) 
		{
			this.m_rawInventory.deserializeNBT(tag.getCompound(DeviceBlockEntity.INVENTORY_TAG_NAME));
		}
		
		if(tag.contains(DeviceBlockEntity.CURRENT_HANDLER_TAG_NAME)) 
		{
			this.m_internalCurrentStorer.deserializeNBT(tag.getCompound(DeviceBlockEntity.CURRENT_HANDLER_TAG_NAME));
		}
	} // end load()
	
} // end class