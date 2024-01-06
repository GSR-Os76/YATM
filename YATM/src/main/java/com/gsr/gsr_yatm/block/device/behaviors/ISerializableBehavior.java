package com.gsr.gsr_yatm.block.device.behaviors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISerializableBehavior extends INBTSerializable<CompoundTag>, IBehavior
{
	/** suggested key to save the object under, MUST be indempotent */
	public default @NotNull String key() 
	{
		return this.getClass().getName();
	} // end key()
	
	@Override
	@Nullable CompoundTag serializeNBT();

	@Override
	void deserializeNBT(@NotNull CompoundTag nbt);
	
} // end interface