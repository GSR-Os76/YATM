package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class BlockUtil
{
	@SuppressWarnings("deprecation")
	public static boolean isLightLevelBelow(@NotNull Level level, @NotNull BlockPos position, int lightLevel) 
	{
		if ((!level.isAreaLoaded(position, 1)) || level.getRawBrightness(position, 0) < lightLevel) 
		{
			return true;
		}
		return false;
	} // end isLightLevelBelow()

	@SuppressWarnings("deprecation")
	public static boolean isLightLevelAbove(@NotNull ServerLevel level, @NotNull BlockPos position, int lightLevel)
	{
		if ((!level.isAreaLoaded(position, 1)) || level.getRawBrightness(position, 0) > lightLevel) 
		{
			return true;
		}
		return false;
	} // end isLightLevelAbove()
	
} // end class