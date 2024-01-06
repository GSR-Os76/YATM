package com.gsr.gsr_yatm.block.device.builder.game_objects;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.device.IBlockEntityHelpers;
import com.gsr.gsr_yatm.block.device.IDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.behaviors.IChangedListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.IInventoryChangeListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ILoadListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;
import com.gsr.gsr_yatm.block.device.builder.inventory.InventoryDefinition;
import com.gsr.gsr_yatm.block.device.builder.inventory.slot.SlotDefinition;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.capability.CapabilityUtil;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class BuiltDeviceBlockEntity extends BlockEntity implements IDeviceBlockEntity
{
	protected final @NotNull IBlockEntityHelpers m_helpers = new IBlockEntityHelpers()
	{
		@Override
		public @NotNull BlockEntity self()
		{
			return BuiltDeviceBlockEntity.this;
		} // end self()
	};

	private @NotNull IItemHandler m_inventory;
	private @NotNull ContainerData m_containerData;
	private @NotNull IInvalidatableCapabilityProvider m_capabilityProvider;

	private @NotNull List<SlotDefinition> m_slotDefs;

	private @NotNull Set<ITickableBehavior> m_tickers;
	private @NotNull Set<ISerializableBehavior> m_nbtSerializers;
	private @NotNull Set<IChangedListenerBehavior> m_changedListeners;
	private @NotNull Set<IInventoryChangeListenerBehavior> m_inventoryChangeListeners;
	private @NotNull Set<ILoadListenerBehavior> m_loadListeners;
	
	
	
	public BuiltDeviceBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos position, @NotNull BlockState state)
	{
		super(type, position, state);
		this.define(this::ctor, CapabilityUtil.of(super::getCapability));
	} // end constructor



	private void ctor(@NotNull DeviceDefinition definition)
	{
		this.m_inventory = definition.inventory();
		this.m_containerData = definition.containerData();
		this.m_capabilityProvider = definition.capabilityProvider();
		
		this.m_tickers = definition.getBehaviors(ITickableBehavior.class);
		this.m_nbtSerializers = definition.getBehaviors(ISerializableBehavior.class);
		this.m_changedListeners = definition.getBehaviors(IChangedListenerBehavior.class);
		this.m_inventoryChangeListeners = definition.getBehaviors(IInventoryChangeListenerBehavior.class);
		this.m_loadListeners = definition.getBehaviors(ILoadListenerBehavior.class);
	} // end ctor()

	protected @NotNull ItemStackHandler createInventory(@NotNull InventoryDefinition id)
	{
		this.m_slotDefs = ImmutableList.copyOf(id.slots());
		return new ItemStackHandler(this.m_slotDefs.size())
		{
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack)
			{
				return BuiltDeviceBlockEntity.this.itemInsertionValidator(slot, stack);
			} // end isItemValid()

			@Override
			public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate)
			{
				if (!BuiltDeviceBlockEntity.this.itemWithdrawalValidator(slot, this.getStackInSlot(slot)))
				{
					return ItemStack.EMPTY;
				}
				return super.extractItem(slot, amount, simulate);
			} // end extractItem()

			@Override
			protected void onContentsChanged(int slot)
			{
				super.onContentsChanged(slot);
				BuiltDeviceBlockEntity.this.setChanged();
				BuiltDeviceBlockEntity.this.onSlotChanged(slot);
			} // end onContentsChanged()

		};
	} // end createInventory()

	protected abstract void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider);



	public @NotNull IItemHandler getInventory()
	{
		return this.m_inventory;
	} // end getInventory()

	public @NotNull ContainerData getDataAccessor()
	{
		return this.m_containerData;
	} // end getDataAccessor()
	


	protected boolean itemInsertionValidator(@NotNegative int slot, @NotNull ItemStack stack)
	{
		return this.m_slotDefs.get(slot).insertionValidator().test(stack);
	} // end itemInsertionValidator()

	protected boolean itemWithdrawalValidator(@NotNegative int slot, @NotNull ItemStack stack)
	{
		return this.m_slotDefs.get(slot).extractionValidator().test(stack);
	} // end itemWithdrawalValidator()

	protected void onSlotChanged(@NotNegative int slot)
	{
		for (IInventoryChangeListenerBehavior l : this.m_inventoryChangeListeners)
		{
			if (l.getSlotIndices().contains(slot))
			{
				l.onSlotChanged(slot);
			}
		}
	} // end itemChanged()



	@Override
	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		boolean changed = false;
		for (ITickableBehavior t : this.m_tickers)
		{
			changed |= t.tick(level, position);
		}
		if (changed)
		{
			this.setChanged();
		}
	} // end serverTick()



	@Override
	public void setChanged()
	{
		super.setChanged();
		this.m_changedListeners.forEach((l) -> l.onChanged());
	} // end setChanged()


	
	@Override
	public @NotNull NonNullList<ItemStack> getDropInventory()
	{
		return InventoryUtil.toNNList(this.m_inventory);
	} // end getDropInventory()
	
	
	



	@Override
	protected void saveAdditional(@NotNull CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		for (ISerializableBehavior s : this.m_nbtSerializers)
		{
			CompoundTag d = s.serializeNBT();
			if (d != null)
			{
				nbt.put(s.key(), d);
			}
		}
	} // end saveAdditional()

	@Override
	public void load(@NotNull CompoundTag nbt)
	{
		super.load(nbt);
		for (ISerializableBehavior s : this.m_nbtSerializers)
		{
			if (nbt.contains(s.key()))
			{
				s.deserializeNBT(nbt.getCompound(s.key()));
			}
		}
		this.m_loadListeners.forEach(ILoadListenerBehavior::onLoad);
	} // end load()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(this.m_capabilityProvider == CapabilityUtil.EMPTY_PROVIDER) 
		{
			YetAnotherTechMod.LOGGER.info("builtDeviceBlockEntity, cp wasn't the empty instance");
			return super.getCapability(cap, side);
		}
		return this.m_capabilityProvider.getCapability(cap, side);
	} // end getCapability()

	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_capabilityProvider.invalidateCaps();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_capabilityProvider.reviveCaps();
	} // end reviveCaps()
	
} // end class