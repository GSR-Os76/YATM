package com.gsr.gsr_yatm.utilities;

import net.minecraft.core.Direction;

public class DirectionUtil
{

	public static Direction positiveDirectionOnAxis(Direction.Axis axis)
	{
		return switch(axis) 
		{
			case X -> Direction.WEST;
			case Y -> Direction.UP;
			case Z -> Direction.NORTH;
			default -> throw new IllegalArgumentException("Unexpected value of: " + axis);
		};
	} // end positiveDirectionOnAxis()

} // end class