package com.gsr.gsr_yatm.block.device.builder.container_data;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;
import com.gsr.gsr_yatm.utilities.generic.Property;

import net.minecraft.world.inventory.ContainerData;

public interface IContainerDataBuilder<T> extends IBuilder<T>
{
	@NotNull IContainerDataBuilder<T> addContainerData(@NotNull ContainerData data);

	@NotNull IContainerDataBuilder<T> addProperty(@NotNull Property<Integer> property);

} // end interface