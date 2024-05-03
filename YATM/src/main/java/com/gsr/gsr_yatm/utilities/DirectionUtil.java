package com.gsr.gsr_yatm.utilities;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.generic.SetUtil;

import net.minecraft.core.Direction;

public class DirectionUtil
{

	public static final @NotNull Set<Direction> ALL_AND_NULL = SetUtil.of((Direction)null, Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);



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