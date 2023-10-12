package com.gsr.gsr_yatm.utilities;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.gsr.gsr_yatm.block.plant.OnceFruitingPlantStages;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class YATMBlockStateProperties
{
	public static final IntegerProperty AGE_TWO = IntegerProperty.create("age", 0, 1);
	public static final IntegerProperty AGE_FOUR = IntegerProperty.create("age", 0, 3);
	public static final IntegerProperty AGE_FIVE = IntegerProperty.create("age", 0, 4);
	public static final IntegerProperty AGE_EIGHT = IntegerProperty.create("age", 0, 7);
	
	
	// TODO, make sure nothing is using this when they mean can spread, semantics are important.
	public static final BooleanProperty CAN_GROW = BooleanProperty.create("can_grow");	
	public static final BooleanProperty CAN_SPREAD = BooleanProperty.create("can_spread");	

	public static final EnumProperty<DoubleBlockHalf> DOUBLE_BLOCK_HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	
	public static final DirectionProperty FACING = DirectionProperty.create("facing");
	public static final DirectionProperty FACING_HORIZONTAL = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
	public static final DirectionProperty FACING_NOT_UP = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL.or((d) -> d != null && d == Direction.DOWN));
	
	public static final IntegerProperty FLOWER_COUNT_FOUR = IntegerProperty.create("flower_count", 1, 4);
	
	public static final BooleanProperty FLOWING = BooleanProperty.create("flowing");
	
	public static final BooleanProperty HAS_NORTH = BooleanProperty.create("north");
	public static final BooleanProperty HAS_SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty HAS_EAST = BooleanProperty.create("east");
	public static final BooleanProperty HAS_WEST = BooleanProperty.create("west");
	public static final BooleanProperty HAS_UP = BooleanProperty.create("up");
	public static final BooleanProperty HAS_DOWN = BooleanProperty.create("down");	
	public static final BiMap<Direction, BooleanProperty> HAS_DIRECTION_PROPERTIES_BY_DIRECTION = ImmutableBiMap.of(
			Direction.NORTH, YATMBlockStateProperties.HAS_NORTH, 
			Direction.SOUTH, YATMBlockStateProperties.HAS_SOUTH, 
			Direction.EAST, YATMBlockStateProperties.HAS_EAST, 
			Direction.WEST, YATMBlockStateProperties.HAS_WEST, 
			Direction.UP, YATMBlockStateProperties.HAS_UP, 
			Direction.DOWN, YATMBlockStateProperties.HAS_DOWN);
	
	public static final BooleanProperty HAS_TANK = BooleanProperty.create("has_tank");
	
	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	
	public static final EnumProperty<OnceFruitingPlantStages> ONCE_FRUITING_STAGE =  EnumProperty.create("stage", OnceFruitingPlantStages.class);
	
	public static final BooleanProperty NECTAR_FULL = BooleanProperty.create("nectar_full");
	
} // end class