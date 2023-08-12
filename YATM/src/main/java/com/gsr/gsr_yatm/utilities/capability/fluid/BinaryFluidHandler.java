package com.gsr.gsr_yatm.utilities.capability.fluid;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class BinaryFluidHandler implements IFluidHandler, IFluidTank, INBTSerializable<CompoundTag>
{
	private static final int MAX_CAPACITY = Integer.MAX_VALUE;
	private static final int MIN_CAPACITY = 1;
	
	private static final String STORED_TAG_NAME = "stored";
	
	
	
	private final int m_capacity;
	private @NotNull Fluid m_stored;
	
	
	
	public BinaryFluidHandler(int capacity) 
	{
		this(capacity, Fluids.EMPTY);
	} // end constructor
	
	public BinaryFluidHandler(@Range(from = BinaryFluidHandler.MIN_CAPACITY, to = BinaryFluidHandler.MAX_CAPACITY) int capacity, @NotNull Fluid stored) 
	{
		Validate.inclusiveBetween(BinaryFluidHandler.MIN_CAPACITY, BinaryFluidHandler.MAX_CAPACITY, capacity);
		this.m_capacity = capacity;
		this.m_stored = stored;
	} // end constructor
	
	public boolean isEmpty() 
	{
		return this.m_stored == Fluids.EMPTY;
	} // end isFilled()
	
	
	
	@Override
	public int getTanks()
	{
		return 1;
	} // end getTanks()
	
	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		return this.getFluid();
	} // end getFluidInTank()

	@Override
	public int getTankCapacity(int tank)
	{
		return this.getCapacity();
	} // end getTankCapacity()

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		return true;
	} // end isFluidValid()

	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		if(resource.isEmpty() || !this.isEmpty() || resource.getAmount() < this.m_capacity ) 
		{
			return 0;	
		}
		
		if(action.execute()) 
		{
			this.m_stored = resource.getFluid();
		}
		return this.m_capacity;
	} // end fill()

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		if(resource.getFluid() != this.m_stored) 
		{
			return FluidStack.EMPTY;
		}
		return this.drain(resource.getAmount(), action);
	} // end drain()

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		if(this.isEmpty() || maxDrain < this.m_capacity) 
		{
			return FluidStack.EMPTY;
		}
		
		FluidStack result = this.getFluid();
		if(action.execute()) 
		{
			this.m_stored = Fluids.EMPTY;
		}
		return result;
	} // end drain()

	
	
	@Override
	public @NotNull FluidStack getFluid()
	{
		return this.isEmpty() ? FluidStack.EMPTY : new FluidStack(this.m_stored, this.m_capacity);
	} // end getFluid()

	@Override
	public int getFluidAmount()
	{
		return this.isEmpty() ? 0 : this.m_capacity;
	} // end getFluidAmount()

	@Override
	public int getCapacity()
	{
		return this.m_capacity;
	} // end getCapacity()

	@Override
	public boolean isFluidValid(FluidStack stack)
	{
		return true;
	} // end isFluidValid()

	
	
	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
		if(!this.isEmpty()) 
		{
			tag.putString(BinaryFluidHandler.STORED_TAG_NAME, ForgeRegistries.FLUIDS.getKey(this.m_stored).toString());
		}
		return tag;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(CompoundTag tag)
	{
		if(tag.contains(BinaryFluidHandler.STORED_TAG_NAME)) 
		{
			this.m_stored = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(tag.getString(BinaryFluidHandler.STORED_TAG_NAME)));
		}
	} // end deserializeNBT()

} // end class