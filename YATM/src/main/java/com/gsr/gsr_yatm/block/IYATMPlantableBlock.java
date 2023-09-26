package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

public interface IYATMPlantableBlock
{
	public static final PlantType PLANT_TYPE = PlantType.get("yatm_custom");
			
	public boolean canPlantOn(@NotNull LevelReader level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull Direction face);
	
} // end interface