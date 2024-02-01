package com.gsr.gsr_yatm.item.creative.fluid;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;

public class CreativeFluidSourceItemStack implements IFluidHandlerItem, ICapabilityProvider
{
	private static final String FLUID_TAG_KEY = "fluid";
	
	private final @NotNull ItemStack m_container;
	private final @NotNull LazyOptional<IFluidHandlerItem> m_fluidHandlerHolder = LazyOptional.of(() -> this);


	public CreativeFluidSourceItemStack(@NotNull ItemStack container)
	{
		this.m_container = Objects.requireNonNull(container);
	} // end constructor

	@Override
	public @NotNull ItemStack getContainer()
	{
		return this.m_container;
	} // end getContainer
	
	

	@NotNull
	private Fluid getFluid()
	{
		CompoundTag tagCompound = m_container.getTag();
		if (tagCompound == null || !tagCompound.contains(FLUID_TAG_KEY))
		{
			return Fluids.EMPTY;
		}
		Fluid fluid = ForgeRegistries.FLUIDS.getValue((new ResourceLocation(tagCompound.getString(FLUID_TAG_KEY))));
		return fluid == null ? Fluids.EMPTY : fluid;
	} // end getFluid()

	private void setFluid(Fluid fluid)
	{
		if (!m_container.hasTag())
		{
			m_container.setTag(new CompoundTag());
		}
		m_container.getTag().putString(FLUID_TAG_KEY, ForgeRegistries.FLUIDS.getKey(fluid).toString());
	} // end setFluid()



	@Override
	public int getTanks()
	{
		return 1;
	} // end getTanks()

	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		return new FluidStack(this.getFluid(), Integer.MAX_VALUE);
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
		this.setFluid(resource.getFluid());
		return 0;
	} // end fill

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		Fluid f = this.getFluid();
		return resource.getFluid() == f ? resource.copy() : FluidStack.EMPTY;
	} // end drain()

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		return new FluidStack(this.getFluid(), maxDrain);
	} // end drain()



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