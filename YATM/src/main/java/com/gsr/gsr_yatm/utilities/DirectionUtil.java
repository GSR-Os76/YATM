package com.gsr.gsr_yatm.utilities;

import java.util.List;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;

public class DirectionUtil
{

	public static @NotNull Direction positiveDirectionOnAxis(@NotNull Direction.Axis axis)
	{
		return switch(axis) 
		{
			case X -> Direction.WEST;
			case Y -> Direction.UP;
			case Z -> Direction.NORTH;
			default -> throw new IllegalArgumentException("Unexpected value of: " + axis);
		};
	} // end positiveDirectionOnAxis()

	
	
	public static @NotNull List<Direction> perpendicularPlane(@NotNull Direction face)
	{
		return Stream.of(Direction.values()).filter((d) -> d.getAxis() != face.getAxis()).toList();
	} // end perpendicularPlane()

} // end class