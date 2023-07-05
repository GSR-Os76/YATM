package com.gsr.gsr_yatm.item;

import net.minecraft.world.item.ItemStack;

public class DamagableItemStack
{
	protected final ItemStack m_container;
	
	
	
	public DamagableItemStack(ItemStack container) 
	{
		this.m_container = container;
	} // end constructor
	
	
	
	protected void damageComponent()
	{
		this.m_container.setDamageValue(this.m_container.getDamageValue() + 1);
	} // end damageComponent()

	protected boolean isDamaged()
	{
		return this.m_container.getDamageValue() == this.m_container.getMaxDamage();
	} // end getDamage()
} // end class