package com.gsr.gsr_yatm.utilities.shape;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

@FunctionalInterface
public interface IVoxelShapeProvider
{
	
	// TODO, explicitly contract parameters
	public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos);

} // end interface