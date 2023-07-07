package com.gsr.gsr_yatm.utilities.network;

import net.minecraft.world.inventory.ContainerData;

public interface IContainerDataProvider
{
	// but then what of the access spec?
	// maybe add a method to return an object containing the access specs and string reference to them
	// the same constant strings can access and recover them.
	// and the correspondence of the too methods's just a weak contract.
	
	
	public <T> ContainerData createFor(T t);
	
	/**
	 * Result must always correspond with all possible return values for "createFor(T t)".
	 */
	public ICompositeAccessSpecification createSpec();
} // end class
