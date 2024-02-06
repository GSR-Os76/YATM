package com.gsr.gsr_yatm.block.device.behaviors.implementation.heat;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.capability.HeatUtil;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.Property;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.PropertyContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class HeatingManager implements ISerializableBehavior, ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ISerializableBehavior.class, ITickableBehavior.class);
	} // end behaviorTypes()
	
	public static final int SLOT_COUNT = PropertyContainerData.LENGTH_PER_PROPERTY * 2;
	private static final String BURN_TIME_ELAPSED_TAG_NAME = "burnTimeElapsed";
	private static final String BURN_TIME_INITIAL_TAG_NAME = "burnTimeInitial";
	private static final String BURN_TEMP_TAG_NAME = "burnTemperature";
	
	private final @NotNull IItemHandler m_inventory;
	private final @NotNegative int m_slot;
	private final @NotNull IHeatHandler m_heatHandler;
	
	private @NotNegative int m_burnProgress = 0;
	private @NotNegative int m_burnTime = 0;
	private @NotNegative int m_burnTemperature;
	
	protected final @NotNull ContainerData m_data = new PropertyContainerData(List.of(new Property<>(this::burnProgress, (i) -> {}), new Property<>(this::burnTime, (i) -> {})));
	
	
	
	public HeatingManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull IHeatHandler heatHandler) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_slot = Contract.notNegative(slot);
		this.m_heatHandler = Objects.requireNonNull(heatHandler);
	} // end constructor


	
	public @NotNull ContainerData getData()
	{
		return this.m_data;
	} // end getData()
	
	public @NotNegative int burnProgress()
	{
		return this.m_burnProgress;
	} // end burnProgress()
	
	public @NotNegative int burnTime()
	{
		return this.m_burnTime;
	} // end burnTime()
	
	public @NotNegative int burnTemperature()
	{
		return this.m_burnTemperature;
	} // end burnTemperature()

	
	
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		boolean changed = false;
		
		this.m_heatHandler.heat(IHeatHandler.getAmbientTemp(level, position));
		
		if (this.m_burnProgress != 0)
		{
			if (--this.m_burnProgress <= 0)
			{
				this.m_burnTemperature = 0;
				this.m_burnProgress = 0;
				this.m_burnTime = 0;
			}
			else 
			{
				this.m_heatHandler.heat(this.m_burnTemperature);
			}
			changed = true;
		}
		else if (HeatUtil.getHeatingBurnTime(this.m_inventory.getStackInSlot(this.m_slot)) > 0)
		{
			ItemStack i = this.m_inventory.extractItem(this.m_slot, 1, false);
			if (i.hasCraftingRemainingItem())
			{
				InventoryUtil.insertItemOrDrop(level, position, this.m_inventory, this.m_slot, i.getCraftingRemainingItem());
			}

			this.m_burnTime = HeatUtil.getHeatingBurnTime(i);
			this.m_burnProgress = this.m_burnTime;
			this.m_burnTemperature = HeatUtil.getHeatingTemperature(i);
			changed = true;
		}
		return changed;
	} // end tick()
	
	

	@Override
	public @Nullable CompoundTag serializeNBT()
	{
		if (this.m_burnProgress > 0 && this.m_burnTime > 0)
		{
			CompoundTag tag = new CompoundTag();
			tag.putInt(HeatingManager.BURN_TIME_ELAPSED_TAG_NAME, this.m_burnProgress);
			tag.putInt(HeatingManager.BURN_TIME_INITIAL_TAG_NAME, this.m_burnTime);
			tag.putInt(HeatingManager.BURN_TEMP_TAG_NAME, this.m_burnTemperature);
			return tag;
		}
		return null;
	} // serializeNBT() 

	@Override
	public void deserializeNBT(@NotNull CompoundTag tag)
	{
		if (tag.contains(HeatingManager.BURN_TIME_ELAPSED_TAG_NAME) && tag.contains(HeatingManager.BURN_TIME_INITIAL_TAG_NAME)&& tag.contains(HeatingManager.BURN_TEMP_TAG_NAME))
		{
			this.m_burnProgress = tag.getInt(HeatingManager.BURN_TIME_ELAPSED_TAG_NAME);
			this.m_burnTime = tag.getInt(HeatingManager.BURN_TIME_INITIAL_TAG_NAME);
			this.m_burnTemperature = tag.getInt(HeatingManager.BURN_TEMP_TAG_NAME);
		}
	} // end deserializeNBT()
	
} // end class