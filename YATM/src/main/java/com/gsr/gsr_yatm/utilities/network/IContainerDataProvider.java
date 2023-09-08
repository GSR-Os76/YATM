package com.gsr.gsr_yatm.utilities.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.inventory.ContainerData;

public interface IContainerDataProvider<T>
{
	// but then what of the access spec?
	// maybe add a method to return an object containing the access specs and string reference to them
	// the same constant strings can access and recover them.
	// and the correspondence of the too methods's just a weak contract.
	
	
	public @NotNull ContainerData createFor(@NotNull T t);
	
	/**
	 * Result must always correspond with all possible return values for "createFor(T t)".
	 */
	public @NotNull ICompositeAccessSpecification createSpec();
} // end class
