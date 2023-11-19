package com.gsr.gsr_yatm.block;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShapeBlock extends Block
{
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	
	
	
	public ShapeBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties));
		
		this.m_shape = Objects.requireNonNull(shape);
	} // end constructor



	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos position, @NotNull CollisionContext context)
	{
		return this.m_shape.getShape(state, blockGetter, position, context);
	} // end getShape()
	
} // end class