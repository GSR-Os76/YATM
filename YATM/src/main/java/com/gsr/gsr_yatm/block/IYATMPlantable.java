package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

public interface IYATMPlantable
{
	public static final PlantType PLANT_TYPE = PlantType.get("yatm_custom");
			
	public boolean canPlantOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Direction face);
	
} // end interface