package com.gsr.gsr_yatm;

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
	public static final IntegerProperty AGE_FIVE = IntegerProperty.create("age", 0, 4);
	
	public static final DirectionProperty FACING = DirectionProperty.create("facing");
	public static final DirectionProperty FACING_HORIZONTAL = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	public static final EnumProperty<OnceFruitingPlantStages> ONCE_FRUITING_STAGE =  EnumProperty.create("stage", OnceFruitingPlantStages.class);
	public static final EnumProperty<DoubleBlockHalf> DOUBLE_BLOCK_HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	
} // end class