package com.gsr.gsr_yatm.item.fluid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.fluid.IBottleableFluid;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class GlassBottleItemStack implements IFluidHandlerItem, ICapabilityProvider
{
	private ItemStack m_container;

	private final LazyOptional<IFluidHandlerItem> m_capability = LazyOptional.of(() -> this);
	

	
	public GlassBottleItemStack(ItemStack container)
	{
		this.m_container = container;
	} // end constructor
	
	
	
	@Override
	public @NotNull ItemStack getContainer()
	{
		return this.m_container;
	} // end getContainer()
	
	private boolean canAcceptCurrently() 
	{
		return this.m_container.getItem() == Items.GLASS_BOTTLE && this.m_container.getCount() == 1;
	} // end canAcceptCurrently()
	
	
	
	@Override
	public int getTanks()
	{
		return 1;
	} // end getTanks()

	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		return FluidStack.EMPTY;
	}

	@Override
	public int getTankCapacity(int tank)
	{
		return FluidBottleItem.BOTTLE_CAPACITY;
	} // end getTankCapacity()

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		return stack.getFluid() instanceof IBottleableFluid;// || stack.getFluid() == Fluids.WATER;
	} // end isFluidValid()

	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		if(!(this.isFluidValid(0, resource) && 
				resource.getAmount() >= FluidBottleItem.BOTTLE_CAPACITY && 
				this.canAcceptCurrently())) 
		{
			return 0;
		}
		
		if(action.execute()) 
		{
			this.m_container = 
					//resource.getFluid() == Fluids.WATER ? Items.POTION :
						new ItemStack(((IBottleableFluid)resource.getFluid()).getBottle());
		}
		
		return FluidBottleItem.BOTTLE_CAPACITY;
	} // end fill()

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		return FluidStack.EMPTY;
	} // end drain()

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		return FluidStack.EMPTY;
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