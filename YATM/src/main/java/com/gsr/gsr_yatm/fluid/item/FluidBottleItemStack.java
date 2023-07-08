package com.gsr.gsr_yatm.fluid.item;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class FluidBottleItemStack implements IFluidHandlerItem, ICapabilityProvider
{
	private final FluidStack m_held;
	private final ItemStack m_container;
	private final ItemStack m_emptyContainer;
	
	private final LazyOptional<IFluidHandlerItem> m_capability = LazyOptional.of(() -> this);
	
	
	

	@Override
	public @NotNull ItemStack getContainer()
	{
		return this.m_held.isEmpty() ? this.m_emptyContainer : this.m_container;
	} // end getContainer()


	
	public FluidBottleItemStack(Fluid fluid, ItemStack container)
	{
		this(fluid, container, new ItemStack(Items.GLASS_BOTTLE));
	} // end constructor


	public FluidBottleItemStack(Fluid fluid, ItemStack container, ItemStack emptyContainer)
	{
		this.m_held = new FluidStack(fluid, FluidBottleItem.BOTTLE_CAPACITY);
		this.m_container = container;
		this.m_emptyContainer = emptyContainer;
	} // end constructor



	
	
	
	@Override
	public int getTanks()
	{
		return 1;
	} // end getTanks()

	
	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		return this.m_held;
	} // end getFluidInTank()

	
	@Override
	public int getTankCapacity(int tank)
	{
		return FluidBottleItem.BOTTLE_CAPACITY;
	} // end getTankCapacity()

	
	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		return stack.getFluid() == this.m_held.getFluid();
	} // end isFluidValid

	
	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		return 0;
	} // end fill()

	
	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		if (resource.getFluid() == this.m_held.getFluid())
		{
			return this.drain(resource.getAmount(), action);
		}
		return FluidStack.EMPTY;
	} // end drain()

	
	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		if (maxDrain < FluidBottleItem.BOTTLE_CAPACITY || this.m_held.isEmpty())
		{
			return FluidStack.EMPTY;
		}
		FluidStack resultHolder = this.m_held.copy();
		if (action.execute())
		{
			this.m_held.shrink(FluidBottleItem.BOTTLE_CAPACITY);
		}
		return resultHolder;
	} // end drain()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == ForgeCapabilities.FLUID_HANDLER_ITEM) 
		{
			return m_capability.cast();
		}
		return LazyOptional.empty();
	} // end getCapability()

} // end class