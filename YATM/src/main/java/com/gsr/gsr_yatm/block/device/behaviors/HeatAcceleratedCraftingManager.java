package com.gsr.gsr_yatm.block.device.behaviors;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.recipe.IHeatedRecipe;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public class HeatAcceleratedCraftingManager implements INBTSerializable<CompoundTag>
{
	private static final String TICKS_PERFORMED_TAG_NAME = "ticksPerformed";
	private static final String TICKS_SCHEDULED_TAG_NAME = "ticksScheduled";
	
	private final @NotNull Supplier<@NotNull Boolean> m_baseCrafting;
	private final @NotNull IHeatHandler m_heatHandler;
	private final @NotNull Supplier<@Nullable IHeatedRecipe<?>> m_recipeSupplier;
	
	private @NotNull int m_ticksPerformed = 0;
	private float m_ticksScheduled = 0f;	
	
	
	
	public HeatAcceleratedCraftingManager(@NotNull Supplier<@NotNull Boolean> baseCrafting, @NotNull IHeatHandler heatHandler, @NotNull Supplier<@Nullable IHeatedRecipe<?>> recipeSupplier) 
	{
		this.m_baseCrafting = Objects.requireNonNull(baseCrafting);
		this.m_heatHandler = Objects.requireNonNull(heatHandler);
		this.m_recipeSupplier = Objects.requireNonNull(recipeSupplier);
	} // end constructor
	
	
	
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		boolean changed = this.m_baseCrafting.get();
		if (this.m_recipeSupplier.get() != null)
		{
			this.m_ticksPerformed += 1;
			this.m_ticksScheduled += ((float) this.m_heatHandler.getTemperature()) / ((float) this.m_recipeSupplier.get().getTemperature());
			int bonusTicks = ((int) this.m_ticksScheduled) - this.m_ticksPerformed;
			for (int i = 0; (this.m_recipeSupplier.get() != null) && (i < bonusTicks); i++)
			{
				changed |= this.m_baseCrafting.get();
				this.m_ticksPerformed += 1;
			}
		}
		return changed;
	} // end tick()
	
	public void clearCounters() 
	{
		this.m_ticksPerformed = 0;
		this.m_ticksScheduled = 0f;
	} // end clearCounter()



	@Override
	public @NotNull CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
		if(this.m_recipeSupplier.get() != null) 
		{
			tag.putInt(HeatAcceleratedCraftingManager.TICKS_PERFORMED_TAG_NAME, this.m_ticksPerformed);
			tag.putFloat(HeatAcceleratedCraftingManager.TICKS_SCHEDULED_TAG_NAME, this.m_ticksScheduled);
		}
		return tag;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(@NotNull CompoundTag tag)
	{
		if (tag.contains(HeatAcceleratedCraftingManager.TICKS_PERFORMED_TAG_NAME) && tag.contains(HeatAcceleratedCraftingManager.TICKS_SCHEDULED_TAG_NAME))
		{
			this.m_ticksPerformed = tag.getInt(HeatAcceleratedCraftingManager.TICKS_PERFORMED_TAG_NAME);
			this.m_ticksScheduled = tag.getFloat(HeatAcceleratedCraftingManager.TICKS_SCHEDULED_TAG_NAME);
		}
	} // end deserializeNBT()
	
} // end class