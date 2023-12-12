package com.gsr.gsr_yatm.item.component.current_storer;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CurrentStorerItem extends Item
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

} // end class