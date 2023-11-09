package com.gsr.gsr_yatm.utilities.network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.inventory.ContainerData;

public class ContainerDataBuilder
{
	private int m_count = 0;
	private final List<ContainerData> m_containers = new ArrayList<>();
	private List<Property<Integer>> m_properties = new ArrayList<>();
	
	
	
	public @NotNull AccessSpecification addContainerData(@NotNull ContainerData adding) 
	{
		this.tryPushProperties();
		this.m_containers.add(adding);
		
		int oldCount = m_count;
		m_count += adding.getCount();
		return new AccessSpecification(oldCount, m_count - 1);		
	} // end addContainerData()

	public @NotNull AccessSpecification addProperty(@NotNull Supplier<Integer> getter, @NotNull Consumer<Integer> setter) 
	{
		this.m_properties.add(new Property<>(getter, setter));
		
		int oldCount = m_count;
		m_count += 1;
		return new AccessSpecification(oldCount, m_count - 1);
	} // and addProperty()
	
	
	
	public int getCount()
	{
		return this.m_count;
	} // end getCount()
	
	
	
	public ContainerData build() 
	{
		this.tryPushProperties();
		return new AggregatedContainerData(this.m_containers);
	} // end build()

	
	
	

	private void tryPushProperties()
	{
		if(this.m_properties.size() > 0) 
		{
			this.m_containers.add(new PropertyContainerData(this.m_properties));
			this.m_properties = new ArrayList<>();
		}
	} // end tryPushProperties()

} // end class