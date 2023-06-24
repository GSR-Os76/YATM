package com.gsr.gsr_yatm;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class YATMBlockStateProperties
{
	public static final DirectionProperty FACING = DirectionProperty.create("facing");
	public static final DirectionProperty FACING_HORIZONTAL = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
	public static final BooleanProperty LIT = BooleanProperty.create("lit");

} // end class