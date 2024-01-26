package com.gsr.gsr_yatm.utilities.network.container_data.implementation.bool;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.network.container_data.AccessSpecification;

import net.minecraft.world.inventory.ContainerData;

public class BooleanFlagReader
{
	private final @NotNull ContainerData m_data;
	private final @NotNull AccessSpecification m_accessSpec;
	
	
	
	public BooleanFlagReader(@NotNull ContainerData data, @NotNull AccessSpecification accessSpec) 
	{
		if(BooleanFlagHandler.SLOT_COUNT != accessSpec.count()) 
		{
			throw new IllegalArgumentException("accessSpec is expected to define a range currently compatible with reading boolean flag, " + accessSpec.count() + " does not equal " + BooleanFlagHandler.SLOT_COUNT);
		}
		this.m_data = Objects.requireNonNull(data);
		this.m_accessSpec = Objects.requireNonNull(accessSpec);
	} // end constructor
	
	
	
	public boolean getValue(int index) 
	{
		int mask = BooleanFlagHandler.getIndexMask(index);
		return (this.m_data.get(this.m_accessSpec.startIndex()) & mask) == mask;
	} // end getValue()
} // end class