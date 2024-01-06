package com.gsr.gsr_yatm.block.device.builder;

import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableSet;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.builder.behavior.BehaviorDefinition;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;

import net.minecraft.world.inventory.ContainerData;
import net.minecraftforge.items.IItemHandler;

public record DeviceDefinition(@NotNull IItemHandler inventory, Set<BehaviorDefinition<?>> behaviors, @NotNull ContainerData containerData, @NotNull IInvalidatableCapabilityProvider capabilityProvider)
{
	public DeviceDefinition(@NotNull IItemHandler inventory, Set<BehaviorDefinition<?>> behaviors, @NotNull ContainerData containerData, @NotNull IInvalidatableCapabilityProvider capabilityProvider) 
	{
		this.inventory = Objects.requireNonNull(inventory);
		this.behaviors = Objects.requireNonNull(behaviors);
		this.containerData = Objects.requireNonNull(containerData);
		this.capabilityProvider = Objects.requireNonNull(capabilityProvider);
	} // end constructor
	
	
	
	@SuppressWarnings("unchecked")
	public <X extends IBehavior> @NotNull Set<X> getBehaviors(@NotNull Class<X> type)
	{
		return this.behaviors.stream().filter((b) -> b.type() == type).map((b) -> (X)b.behavior()).collect(ImmutableSet.toImmutableSet());
	} // end getBehaviors()
} // end record