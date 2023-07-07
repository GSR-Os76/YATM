package com.gsr.gsr_yatm.utilities.network;

import net.minecraft.world.inventory.ContainerData;

//TODO, candidate for removal
public record ContainerDataPackage(ContainerData containerData, CompositeAccessSpecification accessSpec)
{

} // end record