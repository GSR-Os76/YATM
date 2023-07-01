package com.gsr.gsr_yatm.block.device;

import com.gsr.gsr_yatm.utilities.ConfigurableInventoryWrapper;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class DeviceBlockEntity extends BlockEntity 
{	
	public static final String INVENTORY_TAG_NAME = "inventory";

	protected ItemStackHandler m_rawInventory;
	protected ConfigurableInventoryWrapper m_uncheckedInventory;
	protected ConfigurableInventoryWrapper m_inventory;
	
	protected int m_maxCurrentTransfer;
	protected int m_currentTransferedThisTick = 0;
	
	
	
	public DeviceBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState, int inventorySlotCount)
	{
		super(type, blockPos, blockState);
		this.m_rawInventory = new ItemStackHandler(inventorySlotCount);
		this.m_uncheckedInventory = new ConfigurableInventoryWrapper.Builder(this.m_rawInventory).onInsertion(this::onItemInsertion).onWithdrawal(this::onItemWithdrawal).build();
		this.m_inventory = new ConfigurableInventoryWrapper.Builder(this.m_uncheckedInventory).slotValidator(this::itemInsertionValidator).build();
	} // end constructor
	
	
	
	public IItemHandler getInventory()
	{
		return m_inventory;
	} // end getInventory()

	public abstract ContainerData getDataAccessor();
	
	protected abstract boolean itemInsertionValidator(int slot, ItemStack itemStack, boolean simulate);

	protected void onItemInsertion(int slot, ItemStack itemStack)
	{
		this.setChanged();
	} // end onItemInsertion()

	protected void onItemWithdrawal(int slot, int amount)
	{
		this.setChanged();
	} // onItemWithdrawal
	
	protected void onCurrentExchanged(int amount) 
	{
		this.m_currentTransferedThisTick += amount;
		this.setChanged();
		if(this.m_currentTransferedThisTick > this.m_maxCurrentTransfer) 
		{
			// TODO, detonate
		}
	} // end onCurrentExchanged()
	
	
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
		
		tag.put(DeviceBlockEntity.INVENTORY_TAG_NAME, this.m_rawInventory.serializeNBT());
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if(tag.contains(DeviceBlockEntity.INVENTORY_TAG_NAME)) 
		{
			this.m_rawInventory.deserializeNBT(tag.getCompound(DeviceBlockEntity.INVENTORY_TAG_NAME));
		}
	} // end load()
	
} // end class