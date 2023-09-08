package com.gsr.gsr_yatm.utilities.network;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankDataReader
{
	private final @NotNull ContainerData m_data;
	private final @NotNull AccessSpecification m_accessSpec;
	
	
	
	public FluidTankDataReader(@NotNull ContainerData data, @NotNull AccessSpecification accessSpec) 
	{
		if(FluidHandlerContainerData.SLOT_COUNT != accessSpec.count()) 
		{
			throw new IllegalArgumentException("accessSpec is expected to define a range compatible with reading a fluid tank, " + accessSpec.count() + " does not equal " + FluidHandlerContainerData.SLOT_COUNT);
		}
		this.m_data = Objects.requireNonNull(data);
		this.m_accessSpec = Objects.requireNonNull(accessSpec);
	} // end constructor

	
	
	public int getAmount() 
	{
		return this.m_data.get(this.m_accessSpec.startIndex() + FluidHandlerContainerData.AMOUNT_INDEX);
	} // end getQuantity()
	
	public int getCapacity()
	{
		return this.m_data.get(this.m_accessSpec.startIndex() + FluidHandlerContainerData.CAPACITY_INDEX);
	} // end getCapacity() 
	
	public @NotNull Fluid getFluid()
	{
		return NetworkUtil.getFluid(this.m_data.get(this.m_accessSpec.startIndex() + FluidHandlerContainerData.AMOUNT_INDEX));
	} // end getFluid() 
	
	public @NotNull FluidStack getFluidStack() 
	{
		return new FluidStack(this.getFluid(), this.getAmount());
	} // end get()
	
} // end class