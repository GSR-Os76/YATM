package com.gsr.gsr_yatm.utilities;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.StringRepresentable;

public enum OptionalAxis implements StringRepresentable, Predicate<Direction>
{
	X(Axis.X, Axis.X.getName(), ImmutableList.of(Direction.EAST, Direction.WEST)),
	Y(Axis.Y, Axis.Y.getName(), ImmutableList.of(Direction.UP, Direction.DOWN)),
	Z(Axis.Z, Axis.Z.getName(), ImmutableList.of(Direction.NORTH, Direction.SOUTH)),
	NONE(null, "none", ImmutableList.of());
	
	
	
	private final @Nullable Axis m_match;
	private final @NotNull String m_represenetation;
	private final @NotNull List<Direction> m_directions;
	OptionalAxis(@Nullable Axis match, @NotNull String represenetation, @NotNull List<Direction> directions) 
	{
		this.m_match = match;
		this.m_represenetation = Objects.requireNonNull(represenetation);
		this.m_directions = Objects.requireNonNull(directions);
		if(!this.m_directions.stream().allMatch((d) -> d.getAxis() == this.m_match)) 
		{
			throw new IllegalArgumentException("All directions must match with the axis.");
		}
	} // end constructor
	
	public static OptionalAxis of(@Nullable Axis axis)
	{
		return switch(axis) 
		{
			case X -> OptionalAxis.X;
			case Y -> OptionalAxis.Y;
			case Z -> OptionalAxis.Z;
			default -> OptionalAxis.NONE;
		};
	} // end of()
	

	public @NotNull List<Direction> getDirections()
	{
		return this.m_directions;
	} // end getDirections()
	
	public @Nullable Axis getMinecraftEquivalent()
	{
		return this.m_match;
	} // end getMinecraftEquivalent()
	


	@Override
	public @NotNull String getSerializedName()
	{
		return this.m_represenetation;
	} // end getSerializedName()
	
	@Override
	public boolean test(@Nullable Direction t)
	{
		return t == null || this.m_match == null ? false : this.m_match.test(t);
	} // end test()
	
} // end enum