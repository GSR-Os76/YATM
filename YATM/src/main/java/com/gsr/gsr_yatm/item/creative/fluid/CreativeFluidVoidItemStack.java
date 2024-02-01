package com.gsr.gsr_yatm.item.creative.fluid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class CreativeFluidVoidItemStack implements IFluidHandlerItem, ICapabilityProvider
{
		private ItemStack m_container;
		private LazyOptional<IFluidHandlerItem> m_fluidHandlerHolder = LazyOptional.of(() -> this);



		public CreativeFluidVoidItemStack(ItemStack container)
		{
			this.m_container = container;
		} // end constructor

		

		@Override
		public int getTanks()
		{
			return 1;
		} // end getTanks()

		@Override
		public @NotNull FluidStack getFluidInTank(int tank)
		{
			return FluidStack.EMPTY;
		} // end getFluidInTank()

		@Override
		public int getTankCapacity(int tank)
		{
			return Integer.MAX_VALUE;
		} // end getTankCapacity()

		@Override
		public boolean isFluidValid(int tank, @NotNull FluidStack stack)
		{
			return true;
		} // end isFluidValid()

		@Override
		public int fill(FluidStack resource, FluidAction action)
		{
			return resource.getAmount();
		} // end fill

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
		public @NotNull ItemStack getContainer()
		{
			return this.m_container;
		} // end getContainer
		
		

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
		{
			if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM) 
			{
				return this.m_fluidHandlerHolder.cast();
			}
			return LazyOptional.empty();
		} // end getCapability()

} // end class