package com.gsr.gsr_yatm.block.device.builder.container_data;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.generic.Property;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.AggregatedContainerData;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.PropertyContainerData;

import net.minecraft.world.inventory.ContainerData;
import net.minecraftforge.items.IItemHandler;

public class InvLazyContainerDataBuilder<T> implements IInvContainerDataBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Supplier<? extends IItemHandler> m_inventory;
	private final @NotNull Consumer<ContainerData> m_buildReceiver;
	private final @NotNull Consumer<Runnable> m_runReceiver;
	
	private final @NotNull List<Supplier<ContainerData>> m_members = Lists.newArrayList();
	private final @NotNull List<Supplier<Property<Integer>>> m_propertyHolder = Lists.newArrayList();

	
	
	public InvLazyContainerDataBuilder(@Nullable T parent, @NotNull Supplier<? extends IItemHandler> inventory, @NotNull Consumer<ContainerData> buildReceiver, @NotNull Consumer<Runnable> runReceiver) 
	{
		this.m_parent = parent;
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
		this.m_runReceiver = Objects.requireNonNull(runReceiver);
	} // end constructor
	

	@Override
	public @NotNull IInvContainerDataBuilder<T> addContainerData(@NotNull Function<IItemHandler, ContainerData> dataProvider)
	{
		Objects.requireNonNull(dataProvider);
		if(this.m_propertyHolder.size() > 0) 
		{
			this.m_members.add(() -> new PropertyContainerData(this.m_propertyHolder.stream().map(Supplier::get).toList()));
			this.m_members.clear();
		}
		this.m_members.add(() -> dataProvider.apply(this.m_inventory.get()));
		return this;
	} // end addContainerData()

	@Override
	public @NotNull IInvContainerDataBuilder<T> addProperty(@NotNull Function<IItemHandler, Property<Integer>> propertyProvider)
	{
		Objects.requireNonNull(propertyProvider);
		this.m_propertyHolder.add(() -> propertyProvider.apply(this.m_inventory.get()));
		return this;
	} // end addProperty()
	
	@Override
	public @Nullable T end()
	{
		this.m_runReceiver.accept(() -> this.m_buildReceiver.accept(new AggregatedContainerData(this.m_members.stream().map(Supplier::get).toList())));
		return this.m_parent;
	} // end end()
	
} // end class