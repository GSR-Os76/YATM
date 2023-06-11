package com.gsr.gsr_yatm.item.component;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.IComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

// figure better name, maybe
// TODO, set up capability, refine down parameters on the interface methods, maybe remove some methods too, or otherwise refine
public class CurrentRegulatorItem extends Item implements IComponent
{
	private int m_targetCurrent;
	private int m_overloadThreshold;
	
	
		
	public CurrentRegulatorItem(Properties properties, int targetCurrent, int overloadThreshold)
	{
		super(properties);
		
		m_targetCurrent = targetCurrent;
		m_overloadThreshold = overloadThreshold;
	} // end constructor

	

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		if(this.getClass() == CurrentRegulatorItem.class) 
		{
			return new CurrentRegulatorItemStack(stack, this.m_targetCurrent, this.m_overloadThreshold);
		}
		return super.initCapabilities(stack, nbt);
	} // end initCapabilities()
	
} // end class