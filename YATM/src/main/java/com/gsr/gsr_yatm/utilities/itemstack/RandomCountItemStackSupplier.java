package com.gsr.gsr_yatm.utilities.itemstack;

import java.util.function.Supplier;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// TODO, very wordy name
public class RandomCountItemStackSupplier implements Supplier<ItemStack>
{
	private final Supplier<Item> m_item;
	private final int m_inclusiveMin;
	private final int m_inclusiveMax;
	private final RandomSource m_random;
	
	
	
	public RandomCountItemStackSupplier(Supplier<Item> item, int inclusiveMin, int inclusiveMax, RandomSource random) 
	{
		this.m_item = item;
		this.m_inclusiveMin = inclusiveMin;
		this.m_inclusiveMax = inclusiveMax;
		this.m_random = random;
	} // end constructor

	
	
	@Override
	public ItemStack get()
	{
		return new ItemStack(this.m_item.get(), this.m_random.nextIntBetweenInclusive(this.m_inclusiveMin, this.m_inclusiveMax));
	} // end get()
	
} // end class