package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.block.plant.OnceFruitingPlantStages;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class YATMBlockStateProperties
{
	public static final DirectionProperty FACING = DirectionProperty.create("facing");
	public static final DirectionProperty FACING_HORIZONTAL = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	public static final EnumProperty<OnceFruitingPlantStages> ONCE_FRUITING_STAGE =  EnumProperty.create("stage", OnceFruitingPlantStages.class);

} // end class