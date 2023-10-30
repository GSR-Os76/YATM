package com.gsr.gsr_yatm.item.component.fluid_pass_through;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.item.component.IComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidPassThroughBlockItem extends BlockItem implements IComponent
{

	public FluidPassThroughBlockItem(@NotNull Block block, @NotNull Properties properties)
	{
		super(Objects.requireNonNull(block), Objects.requireNonNull(properties));
	} // end constructor

	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new FluidPassThroughItemStack(Objects.requireNonNull(stack));
	} // end initCapabilities()
	
	
	
	@Override
	public <T> void attachRecievingCapability(@NotNull ItemStack itemStack, @NotNull Capability<T> capType, @NotNull LazyOptional<T> cap)
	{
		if(!this.getValidCapabilities().contains(capType)) 
		{
			throw new IllegalArgumentException("The capability type " + capType + " is not valid for this.");
		}
		
		LazyOptional<IFluidHandler> t = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER);
		if(t.isPresent()) 
		{
			((FluidPassThroughItemStack)t.orElse(null)).attach(cap.cast());
		}
	} // end attachRecievingCapability()

	@Override
	public <T> void removeRecievingCapability(@NotNull ItemStack itemStack, @NotNull LazyOptional<T> cap)
	{
		LazyOptional<IFluidHandler> t = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER);
		if(t.isPresent()) 
		{
			((FluidPassThroughItemStack)t.orElse(null)).tryRemove(cap);
		}
	} // end removeRecievingCapability()

	@Override
	public @NotNull List<Capability<?>> getValidCapabilities()
	{
		return List.of(ForgeCapabilities.FLUID_HANDLER);
	} // end getValidCapabilities()
} // end class