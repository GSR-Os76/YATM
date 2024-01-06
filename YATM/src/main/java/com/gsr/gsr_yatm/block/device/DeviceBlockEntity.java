package com.gsr.gsr_yatm.block.device;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

// TODO, subclass BasicBlockEntity probably, also probably rename that class
public abstract class DeviceBlockEntity extends BlockEntity implements IDeviceBlockEntity
{	
	public static final String SETUP_TAG_NAME = "setup";
	public static final String INVENTORY_TAG_NAME = "inventory";
	
	static final float AMBIENT_COOLING_FACTOR = .013f;	
	static final int MINIMUM_CHANGE_PER_AMBIENT_COOLING = 6;	
	
	
	
	protected final ItemStackHandler m_rawInventory;
	protected final InventoryWrapper m_uncheckedInventory;
	protected final InventoryWrapper m_inventory;
	
	
	
	
	public DeviceBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos position, @NotNull BlockState state, @NotNegative int inventorySlotCount)
	{
		super(Objects.requireNonNull(type), Objects.requireNonNull(position), Objects.requireNonNull(state));
		this.m_rawInventory = new ItemStackHandler(Contract.notNegative(inventorySlotCount));
		this.m_uncheckedInventory = InventoryWrapper.Builder.of(this.m_rawInventory).onInsertion(this::onItemInsertion).onWithdrawal(this::onItemWithdrawal).build();
		this.m_inventory = InventoryWrapper.Builder.of(this.m_uncheckedInventory).slotValidator(this::itemInsertionValidator).build();
	} // end constructor
	
	@Deprecated(forRemoval = true)
	protected @Nullable CompoundTag setupToNBT() {return null;}
	@Deprecated(forRemoval = true)
	protected void setupFromNBT(@NotNull CompoundTag tag) {}
	
	
	
	public @NotNull IItemHandler getInventory()
	{
		return this.m_inventory;
	} // end getInventory()

	public abstract @NotNull ContainerData getDataAccessor();
	
	protected abstract boolean itemInsertionValidator(@NotNegative int slot, @NotNull ItemStack stack, boolean simulate);

	protected void onItemInsertion(@NotNegative int slot, @NotNull ItemStack stack)
	{
		this.onItemChange(slot, stack);
	} // end onItemInsertion()

	protected void onItemWithdrawal(@NotNegative int slot, @NotNull ItemStack stack)
	{
		this.onItemChange(slot, stack);
	} // end onItemWithdrawal()
	
	protected void onItemChange(@NotNegative int slot, @NotNull ItemStack stack)
	{
		this.setChanged();
	} // end onItemChange()
	
	protected void onCurrentExchanged(@NotNegative int amount) 
	{
		this.setChanged();
	} // end onCurrentExchanged()
	
	protected void onFluidContentsChanged(@NotNull FluidStack stack) 
	{		
		this.setChanged();
	} // end onFluidContentsChanged()
	
	
	
	@Override
	public @NotNull NonNullList<ItemStack> getDropInventory() 
	{
		return InventoryUtil.toNNList(this.m_rawInventory);
	} // end getDropInventory()

	
	
	@Override
	protected void saveAdditional(@NotNull CompoundTag tag)
	{
		super.saveAdditional(tag);
		// TODO, removing setup system
		CompoundTag setup = this.setupToNBT();
		if(setup != null) 
		{
			tag.put(DeviceBlockEntity.SETUP_TAG_NAME, setup);
		}
		tag.put(DeviceBlockEntity.INVENTORY_TAG_NAME, this.m_rawInventory.serializeNBT());
		
	} // end saveAdditional()
	
	@Override
	public void load(@NotNull CompoundTag tag)
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
		
	} // end load()
} // end class