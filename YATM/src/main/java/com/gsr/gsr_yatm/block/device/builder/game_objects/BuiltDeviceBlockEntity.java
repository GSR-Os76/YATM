package com.gsr.gsr_yatm.block.device.builder;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.behaviors.IChangedListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.IInventoryChangeListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ILoadListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BuiltDeviceBlockEntity extends BlockEntity
{
	private final @NotNull DeviceBuilder builder = this.getBuilder();
	
	private @NotNull List<SlotDefinition> m_slotDefs;
	private @NotNull List<ITickableBehavior> m_tickers;
	private @NotNull List<ISerializableBehavior> m_nbtSerializers;
	private @NotNull List<IChangedListenerBehavior> m_changedListeners;
	private @NotNull List<IInventoryChangeListenerBehavior> m_inventoryChangeListeners;
	private @NotNull List<ILoadListenerBehavior> m_loadListeners;
	
	
	public BuiltDeviceBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos position, @NotNull BlockState state)
	{
		super(type, position, state);
		this.ctor(this.getBuilder());
	} // end constructor
	
	public BuiltDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state, @NotNull DeviceBuilder builder)
	{
		super(builder.entityType, position, state);
		this.ctor(builder);
	} // end constructor
	
	private void ctor(@NotNull DeviceBuilder builder) 
	{
		this.m_slotDefs = builder.getSlots();
		this.m_tickers = builder.getBehaviors(ITickableBehavior.class);
		this.m_nbtSerializers = builder.getBehaviors(ISerializableBehavior.class);
		this.m_changedListeners = builder.getBehaviors(IChangedListenerBehavior.class);
		this.m_inventoryChangeListeners = builder.getBehaviors(IInventoryChangeListenerBehavior.class);
		this.m_loadListeners = builder.getBehaviors(ILoadListenerBehavior.class);
	} // end ctor()
	
	
	
	protected @Nullable DeviceBuilder getBuilder() 
	{
		throw new NotImplementedException();
	} // end getBuilder()
	
	

	protected boolean itemInsertionValidator(@NotNegative int slot, @NotNull ItemStack stack)
	{
		return this.m_slotDefs.get(slot).insertionValidator().test(stack);
	} // end itemInsertionValidator()
	
	protected boolean itemWithdrawalValidator(@NotNegative int slot, @NotNull ItemStack stack)
	{
		return this.m_slotDefs.get(slot).extractionValidator().test(stack);
	} // end itemWithdrawalValidator()
	
	protected void onSlotChanged(@NotNegative int slot, @NotNull ItemStack old) 
	{
		for(IInventoryChangeListenerBehavior l : this.m_inventoryChangeListeners) 
		{
			if(l.getSlotIndices().contains(slot)) 
			{
				l.onSlotChanged(slot);
			}
		}
	} // end itemChanged()

	
	
	
 	public static void tick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull DeviceBlockEntity blockEntity)
	{
		if (level.isClientSide)
		{
			blockEntity.clientTick(level, position, state);
		}
		else
		{
			blockEntity.serverTick(level, position, state);
		}
	} // end tick()

	public void clientTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		
	} // end clientTick()
	
	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		boolean changed = false;
		for(ITickableBehavior t : this.m_tickers) 
		{
			changed  |= t.tick(level, position);
		}
		if(changed) 
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
	protected void saveAdditional(@NotNull CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		for(ISerializableBehavior s : this.m_nbtSerializers) 
		{
			CompoundTag d = s.serializeNBT();
			if(d != null) 
			{
				nbt.put(s.key(), d);
			}
		}
	} // end saveAdditional()

	@Override
	public void load(@NotNull CompoundTag nbt)
	{
		super.load(nbt);
		for(ISerializableBehavior s : this.m_nbtSerializers) 
		{
			if(nbt.contains(s.key())) 
			{
				s.deserializeNBT(nbt.getCompound(s.key()));
			}
		}
		for(ILoadListenerBehavior l : this.m_loadListeners) 
		{
			l.onLoad();
		}
	} // end load()
	
} // end class