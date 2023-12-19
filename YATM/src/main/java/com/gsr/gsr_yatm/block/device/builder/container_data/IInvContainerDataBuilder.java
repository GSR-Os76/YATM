package com.gsr.gsr_yatm.block.device.builder.container_data;

import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.IBuilder;
import com.gsr.gsr_yatm.utilities.generic.Property;

import net.minecraft.world.inventory.ContainerData;
import net.minecraftforge.items.IItemHandler;

public interface IInvContainerDataBuilder<T> extends IBuilder<T>
{
	@NotNull IInvContainerDataBuilder<T> addContainerData(@NotNull Function<IItemHandler, ContainerData> dataProvider);

	@NotNull IInvContainerDataBuilder<T> addProperty(@NotNull Function<IItemHandler, Property<Integer>> propertyProvider);

} // end interface