package com.gsr.gsr_yatm.utilities.network;

import java.util.Map.Entry;

import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public class NetworkUtil
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

	
	
	public static float getProgess(@NotNull AccessSpecification progressAccessSpecification, @NotNull ContainerData data)
	{
		return 1f - NetworkUtil.getRemaining(progressAccessSpecification, data);
	} // end getProgess()
	
	public static float getRemaining(@NotNull AccessSpecification progressAccessSpecification, @NotNull ContainerData data)
	{
		int numerator = data.get(progressAccessSpecification.startIndex());
		int denominator = data.get(progressAccessSpecification.endIndex());
		return denominator == 0 ? 1f : (((float) numerator / ((float) denominator)));
	} // end getRemaining()
	
	public static float getRemainingZeroIfNotRunning(@NotNull AccessSpecification progressAccessSpecification, @NotNull ContainerData data)
	{
		int numerator = data.get(progressAccessSpecification.startIndex());
		int denominator = data.get(progressAccessSpecification.endIndex());
		return denominator == 0 ? 0f : (((float) numerator / ((float) denominator)));
	} // end getRemaining()	
	
	public static int getPropertyValue(@NotNull AccessSpecification propertyAccessSpecification, @NotNull ContainerData data)
	{
		return data.get(propertyAccessSpecification.startIndex());
	} // end getPropertyValue()
	
	public static void setPropertyValue(@NotNull AccessSpecification propertyAccessSpecification, @NotNull ContainerData data, int to)
	{
		data.set(propertyAccessSpecification.startIndex(), to);
	} // end getPropertyValue()
	
} // end class