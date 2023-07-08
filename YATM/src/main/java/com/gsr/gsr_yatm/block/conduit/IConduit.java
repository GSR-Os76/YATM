package com.gsr.gsr_yatm.block.conduit;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.gsr.gsr_yatm.block.conduit.network_manager.IConduitNetwork;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

// a heaping pile of helpers and logic for conduits, and handling their overarching networks
// TODO, investigate PipeBlock class
public interface IConduit<T>
{
	public static final Direction[] ALL_DIRECTIONS = new Direction[]
			{ Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN };
	
	public static final BooleanProperty NORTH = BooleanProperty.create("north");
	public static final BooleanProperty SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty EAST = BooleanProperty.create("east");
	public static final BooleanProperty WEST = BooleanProperty.create("west");
	public static final BooleanProperty UP = BooleanProperty.create("up");
	public static final BooleanProperty DOWN = BooleanProperty.create("down");
	
	public static final Map<Direction, BooleanProperty> DIRECTION_PROPERTIES_BY_DIRECTION = ImmutableMap.of(
			Direction.UP, UP, 
			Direction.DOWN, DOWN, 
			Direction.NORTH, NORTH, 
			Direction.EAST, EAST, 
			Direction.SOUTH, SOUTH, 
			Direction.WEST, WEST);

	
	public IConduitNetwork<T> getNetwork();

	public void recieveLoad(int load);
	
	public void addData(String name, Tag data);
	
	/**
	 * 
	 * @return The data tag used for loading in the conduit, should never be null reference.
	 */
	public CompoundTag getData(); 
	
	public T[] getDevices();
	
	public void propose(IConduitNetwork<T> propositioner);
	
	
	
	public static void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
	} // end createBlockStateDefinition()
	
	public static BlockState getStateForPlacement(BlockState blockState, BlockPlaceContext context)
	{
		return blockState.setValue(NORTH, false).setValue(SOUTH, false).setValue(EAST, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false);
	} // end getStateForPlacement()
	
	
	
	public static boolean getValueForDirection(Direction direction, BlockState blockState)
	{
		return blockState.getValue(getPropertyForDirection(direction));
	} // end getValueForDirection()

	public static BlockState setValueForDirection(Direction direction, BlockState blockState, boolean b)
	{
		return blockState.setValue(getPropertyForDirection(direction), b);
	} // end setValueForDirection()

	public static BooleanProperty getPropertyForDirection(Direction direction)
	{
		switch (direction)
		{
			case NORTH:
				return NORTH;
			case SOUTH:
				return SOUTH;
			case EAST:
				return EAST;
			case WEST:
				return WEST;
			case DOWN:
				return DOWN;
			case UP:
				return UP;
			default:
				return null;
		}
	} // end getPropertyForDirection()

} // end interface