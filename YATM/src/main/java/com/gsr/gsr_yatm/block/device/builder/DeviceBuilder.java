package com.gsr.gsr_yatm.block.device.builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.builder.behavior.BehaviorBuilder;
import com.gsr.gsr_yatm.block.device.builder.behavior.BehaviorDefinition;
import com.gsr.gsr_yatm.block.device.builder.behavior.IBehaviorBuilder;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.CapabilityProviderBuilder;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.ICapabilityProviderBuilder;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;
import com.gsr.gsr_yatm.block.device.builder.container_data.ContainerDataBuilder;
import com.gsr.gsr_yatm.block.device.builder.container_data.IContainerDataBuilder;
import com.gsr.gsr_yatm.block.device.builder.inventory.IInventoryBuilder;
import com.gsr.gsr_yatm.block.device.builder.inventory.InventoryBuilder;
import com.gsr.gsr_yatm.block.device.builder.inventory.InventoryDefinition;
import com.gsr.gsr_yatm.utilities.capability.CapabilityUtil;
import com.gsr.gsr_yatm.utilities.network.NetworkUtil;

import net.minecraft.world.inventory.ContainerData;
import net.minecraftforge.items.IItemHandler;

public class DeviceBuilder<T, I extends IItemHandler> implements IDeviceBuilder<T, I>
{
	private final @Nullable T m_parent;
	private final @NotNull Function<InventoryDefinition, I> m_inventoryAssembler;
	private final @NotNull Consumer<DeviceDefinition> m_buildReceiver;
	
	private I m_inventory;
	private final @NotNull Set<BehaviorDefinition<?>> m_behaviors = new HashSet<>();
	private ContainerData m_containerData;
	private IInvalidatableCapabilityProvider m_capabilityProvider;
	

	
	public DeviceBuilder(@Nullable T parent, @NotNull Function<InventoryDefinition, I> inventoryAssembler, @NotNull Consumer<DeviceDefinition> buildReceiver) 
	{
		this.m_parent = parent;
		this.m_inventoryAssembler = Objects.requireNonNull(inventoryAssembler);
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
	} // end constructor
	
	
	
	@Override
	public @NotNull IInventoryBuilder<IDeviceBuilder<T, I>> inventory()
	{
		return new InventoryBuilder<>(this, (i) -> this.m_inventory = this.m_inventoryAssembler.apply(i));
	} // end inventory()
	
	@Override
	public @NotNull IBehaviorBuilder<IDeviceBuilder<T, I>> behavior(@NotNull Function<I, IBehavior> behavior) 
	{
		return new BehaviorBuilder<>(this, behavior.apply(this.m_inventory), (b) -> b.forEach(this.m_behaviors::add));
	} // end behavior()
	
	@Override
	public @NotNull IContainerDataBuilder<IDeviceBuilder<T, I>> containerData()
	{
		return new ContainerDataBuilder<>(this, (c) -> this.m_containerData = c);
	} // end containerData()
	
	@Override
	public @NotNull ICapabilityProviderBuilder<IDeviceBuilder<T, I>> capabilityProvider()
	{
		return new CapabilityProviderBuilder<>(this, (c) -> this.m_capabilityProvider = c);
	} // end capabilityProvider()
	
	
	
	@Override
	public @Nullable T end() 
	{
		this.m_buildReceiver.accept(new DeviceDefinition(this.m_inventory, this.m_behaviors, this.m_containerData != null ? this.m_containerData : NetworkUtil.EMPTY_CD, this.m_capabilityProvider != null ? this.m_capabilityProvider : CapabilityUtil.EMPTY_PROVIDER));
		return this.m_parent;
	} // end()
	
} // end class