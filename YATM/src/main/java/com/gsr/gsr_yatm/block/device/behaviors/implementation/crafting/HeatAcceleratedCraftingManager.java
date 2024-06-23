package com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.recipe.IHeatedRecipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class HeatAcceleratedCraftingManager implements ISerializableBehavior, ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ISerializableBehavior.class, ITickableBehavior.class);
	} // end behaviorTypes()
	
	private static final String TICKS_PERFORMED_TAG_NAME = "ticksPerformed";
	private static final String TICKS_SCHEDULED_TAG_NAME = "ticksScheduled";
	
	private final @NotNull BiFunction<@NotNull Level, @NotNull BlockPos, @NotNull Boolean> m_baseCrafting;
	private final @NotNull IHeatHandler m_heatHandler;
	private final @NotNull Supplier<@Nullable IHeatedRecipe<?>> m_recipeSupplier;
	
	private @NotNull int m_ticksPerformed = 0;
	private float m_ticksScheduled = 0f;	
	
	
	
	public HeatAcceleratedCraftingManager(@NotNull BiFunction<@NotNull Level, @NotNull BlockPos, @NotNull Boolean> baseCrafting, @NotNull IHeatHandler heatHandler, @NotNull Supplier<@Nullable IHeatedRecipe<?>> recipeSupplier) 
	{
		this.m_baseCrafting = Objects.requireNonNull(baseCrafting);
		this.m_heatHandler = Objects.requireNonNull(heatHandler);
		this.m_recipeSupplier = Objects.requireNonNull(recipeSupplier);
	} // end constructor
	
	
	
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		boolean changed = this.m_baseCrafting.apply(level, position);
		if (this.m_recipeSupplier.get() != null)
		{
			this.m_ticksPerformed += 1;
			this.m_ticksScheduled += ((float) this.m_heatHandler.getTemperature()) / ((float) this.m_recipeSupplier.get().getTemperature());
			int bonusTicks = ((int) this.m_ticksScheduled) - this.m_ticksPerformed;
			for (int i = 0; (this.m_recipeSupplier.get() != null) && (i < bonusTicks); i++)
			{
				changed |= this.m_baseCrafting.apply(level, position);
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
	public @Nullable CompoundTag serializeNBT(@NotNull HolderLookup.Provider registryAccess)
	{
		if (this.m_recipeSupplier.get() != null)
		{
			CompoundTag tag = new CompoundTag();
			tag.putInt(HeatAcceleratedCraftingManager.TICKS_PERFORMED_TAG_NAME, this.m_ticksPerformed);
			tag.putFloat(HeatAcceleratedCraftingManager.TICKS_SCHEDULED_TAG_NAME, this.m_ticksScheduled);
			return tag;
		}
		return null;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(@NotNull HolderLookup.Provider registryAccess, @NotNull CompoundTag tag)
	{
		if (tag.contains(HeatAcceleratedCraftingManager.TICKS_PERFORMED_TAG_NAME) && tag.contains(HeatAcceleratedCraftingManager.TICKS_SCHEDULED_TAG_NAME))
		{
			this.m_ticksPerformed = tag.getInt(HeatAcceleratedCraftingManager.TICKS_PERFORMED_TAG_NAME);
			this.m_ticksScheduled = tag.getFloat(HeatAcceleratedCraftingManager.TICKS_SCHEDULED_TAG_NAME);
		}
	} // end deserializeNBT()
	
} // end class