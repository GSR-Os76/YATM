package com.gsr.gsr_yatm.utilities.network;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.inventory.ContainerData;

public class CurrentDataReader
{
	private final @NotNull ContainerData m_data;
	private final @NotNull AccessSpecification m_accessSpec;
	
	
	
	public CurrentDataReader(@NotNull ContainerData data, @NotNull AccessSpecification accessSpec) 
	{
		if(CurrentHandlerContainerData.SLOT_COUNT != accessSpec.count()) 
		{
			throw new IllegalArgumentException("accessSpec is expected to define a range compatible with reading a current handler, " + accessSpec.count() + " does not equal " + CurrentHandlerContainerData.SLOT_COUNT);
		}
		this.m_data = Objects.requireNonNull(data);
		this.m_accessSpec = Objects.requireNonNull(accessSpec);
	} // end constructor

	
	
	public int getStored() 
	{
		return this.m_data.get(this.m_accessSpec.startIndex() + CurrentHandlerContainerData.STORED_INDEX);
	} // end getStored()
	
	public int getCapacity()
	{
		return this.m_data.get(this.m_accessSpec.startIndex() + CurrentHandlerContainerData.CAPACITY_INDEX);
	} // end getCapacity()
	
} // end class