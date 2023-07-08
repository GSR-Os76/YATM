package com.gsr.gsr_yatm.item.component;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.IComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CurrentRegulatorItem extends Item implements IComponent
{
	private int m_targetCurrent;
	private int m_overloadThreshold;
	
	
		
	public CurrentRegulatorItem(Properties properties, int targetCurrent, int overloadThreshold)
	{
		super(properties);
		
		this.m_targetCurrent = targetCurrent;
		this.m_overloadThreshold = overloadThreshold;
	} // end constructor

	

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new CurrentRegulatorItemStack(stack, this.m_targetCurrent, this.m_overloadThreshold);
	} // end initCapabilities()
	
} // end class