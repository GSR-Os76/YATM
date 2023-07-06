package com.gsr.gsr_yatm.item.component;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.IComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CurrentFuseItem extends Item implements IComponent
{
	private final int m_overloadThreshold;
	
	
		
	public CurrentFuseItem(Properties properties, int overloadThreshold)
	{
		super(properties);		
		this.m_overloadThreshold = overloadThreshold;
	} // end constructor

	

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new FuseItemStack(stack, this.m_overloadThreshold);
	} // end initCapabilities()
	
} // end class