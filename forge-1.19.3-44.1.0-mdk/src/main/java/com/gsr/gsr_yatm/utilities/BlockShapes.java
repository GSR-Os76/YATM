package com.gsr.gsr_yatm.utilities;

import com.gsr.gsr_yatm.block.conduit.IConduit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockShapes
{
	// maybe refine shape further
	public static final VoxelShapeGetter STEEL_FLUID_CONDUIT_SHAPE = new VoxelShapeGetter()
	{		
		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			VoxelShape centerShape = Block.box(6d, 6d, 6d, 10d, 10d, 10d);
			VoxelShape branchShapeNorth = Block.box(5d, 5d, 0d, 11d, 11d, 6d);
			VoxelShape branchShapeSouth = Block.box(5d, 5d, 10d, 11d, 11d, 16d);
			VoxelShape branchShapeEast = Block.box(10d, 5d, 5d, 16d, 11d, 11d);
			VoxelShape branchShapeWest = Block.box(0d, 5d, 5d, 6d, 11d, 11d);
			VoxelShape branchShapeUp = Block.box(5d, 10d, 5d, 11d, 16d, 11d);
			VoxelShape branchShapeDown = Block.box(5d, 0d, 5d, 11d, 6d, 11d);

			VoxelShape shape = centerShape;

			if (blockState.getValue(IConduit.NORTH))
			{
				shape = Shapes.or(shape, branchShapeNorth);// HopperBlock h;
			}
			if (blockState.getValue(IConduit.SOUTH))
			{
				shape = Shapes.or(shape, branchShapeSouth);
			}
			if (blockState.getValue(IConduit.EAST))
			{
				shape = Shapes.or(shape, branchShapeEast);
			}
			if (blockState.getValue(IConduit.WEST))
			{
				shape = Shapes.or(shape, branchShapeWest);
			}
			if (blockState.getValue(IConduit.UP))
			{
				shape = Shapes.or(shape, branchShapeUp);
			}
			if (blockState.getValue(IConduit.DOWN))
			{
				shape = Shapes.or(shape, branchShapeDown);
			}

			return shape;
		} // end getShape

	};

	public static final VoxelShapeGetter WIRE_SHAPE = new VoxelShapeGetter()
	{
		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			VoxelShape centerShape = Block.box(7d, 7d, 7d, 9d, 9d, 9d);
			//make shapes correctly, not done typing, half copied half reasoned garbaged
			VoxelShape branchShapeNorth = Block.box(7d, 7d, 0d, 9d, 9d, 7d);
			VoxelShape branchShapeSouth = Block.box(7d, 7d, 9d, 9d, 9d, 16d);
			VoxelShape branchShapeEast = Block.box(9d, 7d, 7d, 16d, 9d, 9d);
			VoxelShape branchShapeWest = Block.box(0d, 7d, 7d, 7d, 9d, 9d);
			VoxelShape branchShapeUp = Block.box(7d, 9d, 7d, 9d, 16d, 9d);
			VoxelShape branchShapeDown = Block.box(7d, 0d, 7d, 9d, 7d, 9d);

			VoxelShape shape = centerShape;

			if (blockState.getValue(IConduit.NORTH))
			{
				shape = Shapes.or(shape, branchShapeNorth);// HopperBlock h;
			}
			if (blockState.getValue(IConduit.SOUTH))
			{
				shape = Shapes.or(shape, branchShapeSouth);
			}
			if (blockState.getValue(IConduit.EAST))
			{
				shape = Shapes.or(shape, branchShapeEast);
			}
			if (blockState.getValue(IConduit.WEST))
			{
				shape = Shapes.or(shape, branchShapeWest);
			}
			if (blockState.getValue(IConduit.UP))
			{
				shape = Shapes.or(shape, branchShapeUp);
			}
			if (blockState.getValue(IConduit.DOWN))
			{
				shape = Shapes.or(shape, branchShapeDown);
			}

			return shape;
		} // end getShape

	};

	public static final VoxelShapeGetter INSULATED_WIRE_SHAPE = new VoxelShapeGetter()
	{
		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			VoxelShape centerShape = Block.box(6d, 6d, 6d, 10d, 10d, 10d);
			VoxelShape branchShapeNorth = Block.box(6d, 6d, 0d, 10d, 10d, 6d);
			VoxelShape branchShapeSouth = Block.box(6d, 6d, 10d, 10d, 10d, 16d);
			VoxelShape branchShapeEast = Block.box(10d, 6d, 6d, 16d, 10d, 10d);
			VoxelShape branchShapeWest = Block.box(0d, 6d, 6d, 6d, 10d, 10d);
			VoxelShape branchShapeUp = Block.box(6d, 10d, 6d, 10d, 16d, 10d);
			VoxelShape branchShapeDown = Block.box(6d, 0d, 6d, 10d, 6d, 10d);

			VoxelShape shape = centerShape;

			if (blockState.getValue(IConduit.NORTH))
			{
				shape = Shapes.or(shape, branchShapeNorth);// HopperBlock h;
			}
			if (blockState.getValue(IConduit.SOUTH))
			{
				shape = Shapes.or(shape, branchShapeSouth);
			}
			if (blockState.getValue(IConduit.EAST))
			{
				shape = Shapes.or(shape, branchShapeEast);
			}
			if (blockState.getValue(IConduit.WEST))
			{
				shape = Shapes.or(shape, branchShapeWest);
			}
			if (blockState.getValue(IConduit.UP))
			{
				shape = Shapes.or(shape, branchShapeUp);
			}
			if (blockState.getValue(IConduit.DOWN))
			{
				shape = Shapes.or(shape, branchShapeDown);
			}

			return shape;
		} // end getShape

	};

} // end class