package com.gsr.gsr_yatm.block.device.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.items.IItemHandler;

public class DeviceBuilder implements IBehavioralBuilder, IInventoryBuilder
{
	public final @NotNull BlockEntityType<?> entityType;
	
	private final @NotNull List<SlotDefinition> m_slots = new ArrayList<>();
	private final @NotNull Map<Class<? extends IBehavior>, List<IBehavior>> m_behaviors = new HashMap<>();
	


	public DeviceBuilder(@NotNull BlockEntityType<?> entityType) 
	{
		this.entityType = Objects.requireNonNull(entityType);
	} // end constructor
	
	public static DeviceBuilder of(@NotNull BlockEntityType<?> entityType) 
	{
		return new DeviceBuilder(entityType);
	} // end of()
	
	
	
	@SuppressWarnings("unchecked")
	public <X extends IBehavior> List<X> getBehaviors(@NotNull Class<X> type)
	{
		return !this.m_behaviors.containsKey(type) ? List.of() : this.m_behaviors.get(type).stream().map((b) -> (X)b).collect(ImmutableList.toImmutableList());
	} // end getBehaviors()
	
	@Override
	public <X extends IBehavior> void addBehavior(@NotNull X behavior)
	{
		if(!this.m_behaviors.containsKey(behavior.getClass())) 
		{
			this.m_behaviors.put(behavior.getClass(), new ArrayList<IBehavior>());
		}
		this.m_behaviors.get(behavior.getClass()).add(behavior);
	} // end addBehavior()
	
	public @NotNull BehaviorBuilder<DeviceBuilder> behavior(@NotNull IBehavior behavior) 
	{
		return new BehaviorBuilder<>(this, Objects.requireNonNull(behavior));
	} // end behavior()
	
	public @NotNull BehaviorBuilder<DeviceBuilder> behavior(@NotNull Function<IItemHandler, IBehavior> behavior) 
	{
		return new BehaviorBuilder<>(this, Objects.requireNonNull(behavior.apply(null)));//TODO, inventory builder proper, this.m_inventory)));
	} // end behavior()
	
	
	
	@Override
	public @NotNull List<@NotNull SlotDefinition> getSlots()
	{
		return List.copyOf(this.m_slots);
	} // end getSlots()

	@Override
	public void addSlot(@NotNull SlotDefinition slot)
	{
		this.m_slots.add(slot);
	} // end addSlot()
	
	public @NotNull SlotBuilder<DeviceBuilder> slot() 
	{
		return new SlotBuilder<>(this);
	} // end slot
	
} // end class