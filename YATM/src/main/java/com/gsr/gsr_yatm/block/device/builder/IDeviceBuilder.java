package com.gsr.gsr_yatm.block.device.builder;

import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.builder.behavior.IBehaviorBuilder;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.ICapabilityProviderBuilder;
import com.gsr.gsr_yatm.block.device.builder.container_data.IContainerDataBuilder;
import com.gsr.gsr_yatm.block.device.builder.inventory.IInventoryBuilder;

import net.minecraftforge.items.IItemHandler;

public interface IDeviceBuilder<T, I extends IItemHandler> extends IBuilder<T>
{
	@NotNull IInventoryBuilder<? extends IDeviceBuilder<T, I>> inventory();

	default @NotNull IBehaviorBuilder<? extends IDeviceBuilder<T, I>> behavior(@NotNull IBehavior behavior)
	{
		return this.behavior((i) -> behavior);
	} // end behavior

	@NotNull IBehaviorBuilder<? extends IDeviceBuilder<T, I>> behavior(@NotNull Function<I, IBehavior> behavior);

	@NotNull IContainerDataBuilder<? extends IDeviceBuilder<T, I>> containerData();

	@NotNull ICapabilityProviderBuilder<? extends IDeviceBuilder<T, I>> capabilityProvider();
	
} // end interface