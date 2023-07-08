package com.gsr.gsr_yatm.utilities.network;

import java.util.Map.Entry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.ContainerData;
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

	
	
	public static float getProgess(AccessSpecification progressAccessSpecification, ContainerData data)
	{
		return 1f - NetworkUtilities.getRemaining(progressAccessSpecification, data);
	} // end getProgess()
	
	public static float getRemaining(AccessSpecification progressAccessSpecification, ContainerData data)
	{
		int numerator = data.get(progressAccessSpecification.startIndex());
		int denominator = data.get(progressAccessSpecification.endIndex());
		return denominator == 0 ? 0f : (((float) numerator / ((float) denominator)));
	} // end getProgess()
	
} // end class