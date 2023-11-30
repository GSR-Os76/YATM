package com.gsr.gsr_yatm.block.device.behaviors;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.block.device.crucible.CrucibleBlockEntity;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

public class HeatingManager implements INBTSerializable<CompoundTag>
{
	private static final String BURN_TIME_ELAPSED_TAG_NAME = "burnTimeElapsed";
	private static final String BURN_TIME_INITIAL_TAG_NAME = "burnTimeInitial";
	private static final String BURN_TEMP_TAG_NAME = "burnTemperature";
	
	private final @NotNull IItemHandler m_inventory;
	private final @NotNegative int m_slot;
	private final @NotNull IHeatHandler m_heatHandler;
	
	private @NotNegative int m_burnProgress = 0;
	private @NotNegative int m_burnTime = 0;
	private @NotNegative int m_burnTemperature;
	
	
	
	public HeatingManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull IHeatHandler heatHandler) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_slot = Contract.notNegative(slot);
		this.m_heatHandler = Objects.requireNonNull(heatHandler);
	} // end constructor


	
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
		
		this.m_heatHandler.heat(IHeatHandler.getAmbientTemp());
		
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
		else if (SlotUtil.getHeatingBurnTime(this.m_inventory.getStackInSlot(CrucibleBlockEntity.HEAT_SLOT)) > 0)
		{
			ItemStack i = this.m_inventory.extractItem(CrucibleBlockEntity.HEAT_SLOT, 1, false);
			if (i.hasCraftingRemainingItem())
			{
				InventoryUtil.insertItemOrDrop(level, position, this.m_inventory, this.m_slot, i.getCraftingRemainingItem());
			}

			this.m_burnTime = SlotUtil.getHeatingBurnTime(i);
			this.m_burnProgress = this.m_burnTime;
			this.m_burnTemperature = SlotUtil.getHeatingTemperature(i);
			changed = true;
		}
		return changed;
	} // end tick()
	
	

	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
				if(this.m_burnProgress > 0 && this.m_burnTime > 0) 
				{
					tag.putInt(HeatingManager.BURN_TIME_ELAPSED_TAG_NAME, this.m_burnProgress);
					tag.putInt(HeatingManager.BURN_TIME_INITIAL_TAG_NAME, this.m_burnTime);
					tag.putInt(HeatingManager.BURN_TEMP_TAG_NAME, this.m_burnTemperature);
					
				}
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag)
	{
		if (tag.contains(HeatingManager.BURN_TIME_ELAPSED_TAG_NAME) && tag.contains(HeatingManager.BURN_TIME_INITIAL_TAG_NAME))
		{
			this.m_burnProgress = tag.getInt(HeatingManager.BURN_TIME_ELAPSED_TAG_NAME);
			this.m_burnTime = tag.getInt(HeatingManager.BURN_TIME_INITIAL_TAG_NAME);
			this.m_burnTemperature = tag.getInt(HeatingManager.BURN_TEMP_TAG_NAME);
		}
	} // end constructor
	
} // end class