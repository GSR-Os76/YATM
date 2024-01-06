package com.gsr.gsr_yatm.block.device.builder.container_data;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.generic.Property;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.AggregatedContainerData;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.PropertyContainerData;

import net.minecraft.world.inventory.ContainerData;

// TODO, merge with one in util?
public class ContainerDataBuilder<T> implements IContainerDataBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNull Consumer<ContainerData> m_buildReceiver;
	
	private final @NotNull List<ContainerData> m_members = Lists.newArrayList();
	private final @NotNull List<Property<Integer>> m_propertyHolder = Lists.newArrayList();

	
	
	public ContainerDataBuilder(@Nullable T parent, @NotNull Consumer<ContainerData> buildReceiver) 
	{
		this.m_parent = parent;
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
	} // end constructor
	

	
	@Override
	public @NotNull IContainerDataBuilder<T> addContainerData(@NotNull ContainerData data)
	{
		Objects.requireNonNull(data);
		this.pushProperties();
		this.m_members.add(data);
		return this;
	} // end addContainerData()

	@Override
	public @NotNull IContainerDataBuilder<T> addProperty(@NotNull Property<Integer> property)
	{
		;
		this.m_propertyHolder.add(Objects.requireNonNull(property));
		return this;
	} // end addProperty()
	
	public @NotNull IContainerDataBuilder<T> pushProperties() 
	{
		if(this.m_propertyHolder.size() > 0) 
		{
			this.m_members.add(new PropertyContainerData(this.m_propertyHolder));
			this.m_propertyHolder.clear();
		}
		return this;
	} // end pushProperties()
	
	
	
	@Override
	public @Nullable T end()
	{
		this.pushProperties();
		this.m_buildReceiver.accept(new AggregatedContainerData(this.m_members));
		return this.m_parent;
	} // end end()
	
} // end class