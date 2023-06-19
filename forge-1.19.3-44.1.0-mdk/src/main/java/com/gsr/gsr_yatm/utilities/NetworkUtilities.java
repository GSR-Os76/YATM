package com.gsr.gsr_yatm.utilities;

import java.util.Map.Entry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public class NetworkUtilities
{
	// can we confirm that client and server will list these in the exact same order?
	public static int getFluidIndex(Fluid fluid) 
	{
		int fIndex = -1;
		int i = 0;
		for(Entry<ResourceKey<Fluid>, Fluid> enty : ForgeRegistries.FLUIDS.getEntries()) 
		{
			if(enty.getValue() == fluid) 
			{
				fIndex = i;
				break;
			}
			i++;
		}
		return fIndex;
	} // end getFluidIndex
	
	public static Fluid getFluid(int index) 
	{
		int i = 0;
		for(Entry<ResourceKey<Fluid>, Fluid> enty : ForgeRegistries.FLUIDS.getEntries()) 
		{
			if(i++ == index) 
			{
				return enty.getValue();
			}
		}
		return Fluids.EMPTY;
	} // end getFluid

	
	
	
	public static int splitInt(int i, boolean high)
	{
		int v = high ? i >>> 16 : i;
		return v & 0b0000_0000_0000_0000_1111_1111_1111_1111;
	} // end splintInt()

	public static int composeInt(int lowHalf, int highHalf)
	{
		return (lowHalf | (highHalf << 16));
	} // end composeInt()
	
} // end class