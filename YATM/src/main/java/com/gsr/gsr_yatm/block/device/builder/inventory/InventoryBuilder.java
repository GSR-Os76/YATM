package com.gsr.gsr_yatm.block.device.builder.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.block.device.builder.inventory.slot.SlotBuilder;
import com.gsr.gsr_yatm.block.device.builder.inventory.slot.SlotDefinition;

public class InventoryBuilder<T> implements IInventoryBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Consumer<@NotNull InventoryDefinition> m_buildReceiver;
	
	private final @NotNull List<SlotDefinition> m_slots = new ArrayList<>();
	// add on change
	
	
	public InventoryBuilder(@Nullable T parent, @NotNull Consumer<@NotNull InventoryDefinition> buildReceiver) 
	{
		this.m_parent = parent;
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
	} // end constructor()
	
	
	
	@Override
	public @NotNull SlotBuilder<InventoryBuilder<T>> slot() 
	{
		return new SlotBuilder<>(this, this.m_slots.size(), this.m_slots::add);
	} // end slot
	
	@Override
	public @Nullable T end() 
	{
		this.m_buildReceiver.accept(new InventoryDefinition(ImmutableList.copyOf(this.m_slots)));
		return this.m_parent;
	} // end()
	
} // end class