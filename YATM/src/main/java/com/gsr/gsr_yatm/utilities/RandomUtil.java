package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.util.RandomSource;

public class RandomUtil
{
	public static int nextSign(@NotNull RandomSource random) 
	{
		return random.nextInt(2) == 0 ? -1 : 1;
	} // end nextSign()
	
} // end class