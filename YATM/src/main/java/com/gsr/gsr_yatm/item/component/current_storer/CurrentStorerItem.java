package com.gsr.gsr_yatm.item.component.current_storer;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.item.component.IComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CurrentStorerItem extends Item implements IComponent
{
	private final @NotNull Supplier<Integer> m_capacity;
	
	
	
	public CurrentStorerItem(@NotNull Properties properties, @NotNull Supplier<Integer> capacity)
	{
		super(Objects.requireNonNull(properties));
		this.m_capacity = capacity;
	} // end constructor
	
	
	
	@Override
	public int getMaxDamage(@NotNull ItemStack stack)
	{
		return this.m_capacity.get();
	} // end getMaxDamage()


	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new CurrentStorerItemStack(stack, this.m_capacity.get());
	} // end initCapabilities()

	
	
	@Override
	public <T> void attachRecievingCapability(@NotNull ItemStack itemStack, @NotNull Capability<T> capType, @NotNull LazyOptional<T> cap)
	{
		if(!this.getValidCapabilities().contains(capType)) 
		{
			throw new IllegalArgumentException("The capability type " + capType + " is not valid for this.");
		}
		
		LazyOptional<ICurrentHandler> t = itemStack.getCapability(YATMCapabilities.CURRENT);
		if(t.isPresent()) 
		{
			((CurrentStorerItemStack)t.orElse(null)).attach(cap.cast());
		}
	} // end attachRecievingCapability()

	@Override
	public <T> void removeRecievingCapability(@NotNull ItemStack itemStack, @NotNull LazyOptional<T> cap)
	{
		LazyOptional<ICurrentHandler> t = itemStack.getCapability(YATMCapabilities.CURRENT);
		if(t.isPresent()) 
		{
			((CurrentStorerItemStack)t.orElse(null)).tryRemove(cap);
		}
	} // end removeRecievingCapability()

	@Override
	public @NotNull List<Capability<?>> getValidCapabilities()
	{
		return List.of(YATMCapabilities.CURRENT);
	} // end getValidCapabilities()

} // end class