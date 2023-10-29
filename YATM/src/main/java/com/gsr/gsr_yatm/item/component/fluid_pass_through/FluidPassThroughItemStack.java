package com.gsr.gsr_yatm.item.component.fluid_pass_through;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class FluidPassThroughItemStack implements ICapabilityProvider, IFluidHandlerItem
{
	private final @NotNull LazyOptional<IFluidHandlerItem> m_thisCap = LazyOptional.of(() -> this);
	private final @NotNull LazyOptional<IFluidHandlerItem> m_thisCapI = LazyOptional.of(() -> this);

	private final @NotNull ItemStack m_itemStack;
	
	private LazyOptional<IFluidHandler> m_attachedCap;
	private IFluidHandler m_attachment;



	public FluidPassThroughItemStack(@NotNull ItemStack itemStack)
	{
		this.m_itemStack = Objects.requireNonNull(itemStack);
	} // end constructor

	
	
	@Override
	public @NotNull ItemStack getContainer()
	{
		return this.m_itemStack;
	} // end getContainer()
	
	@Override
	public int getTanks()
	{
		return this.m_attachment == null ? 0 : this.m_attachment.getTanks();
	} // end getTanks()

	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		return this.m_attachment == null ? FluidStack.EMPTY : this.m_attachment.getFluidInTank(tank);
	} // end getFluidInTank()

	@Override
	public int getTankCapacity(int tank)
	{
		return this.m_attachment == null ? 0 : this.m_attachment.getTankCapacity(tank);
	} // end getTankCapacity()

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		return this.m_attachment == null ? false : this.m_attachment.isFluidValid(tank, stack);
	} // end isFluidValid()

	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		return this.m_attachment == null ? 0 : this.m_attachment.fill(resource, action);
	} // end fill()

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		return this.m_attachment == null ? FluidStack.EMPTY : this.m_attachment.drain(resource, action);
	} // end drain()

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		return this.m_attachment == null ? FluidStack.EMPTY : this.m_attachment.drain(maxDrain, action);
	} // end drain()
	
	

	public <T> void attach(@NotNull LazyOptional<IFluidHandler> cap)
	{
		if (cap.isPresent())
		{
			cap.addListener((la) -> FluidPassThroughItemStack.this.tryRemove(la));
			this.m_attachedCap = cap;
			this.m_attachment = cap.orElse(null);
		}
	} // end attach()

	public void tryRemove(@NotNull LazyOptional<?> cap)
	{
		if (cap == this.m_attachedCap)
		{
			this.m_attachedCap = null;
			this.m_attachment = null;
		}
	} // end tryRemove()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		// TODO, may be simplified, test how generic variance and casting works in java.
		if (cap == ForgeCapabilities.FLUID_HANDLER)
		{
			return this.m_thisCap.cast();
		}
		else if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM)
		{
			return this.m_thisCapI.cast();
		}
		return LazyOptional.empty();
	} // end getCapabilitiy()

} // end class