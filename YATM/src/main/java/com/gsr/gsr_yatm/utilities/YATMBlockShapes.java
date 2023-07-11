package com.gsr.gsr_yatm.utilities;

import com.gsr.gsr_yatm.block.conduit.IConduit;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
import com.gsr.gsr_yatm.block.device.spinning_wheel.SpinningWheelBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class YATMBlockShapes
{	
	public static final VoxelShapeProvider CUBE = new VoxelShapeProvider() 
	{
		private static final VoxelShape CUBE = Block.box(0, 0, 0, 16, 16, 16);

		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			return CUBE;
		} // end getShape()
	};
	
	
	
	public static final VoxelShapeProvider PRISMARINE_CRYSTAL_MOSS = new VoxelShapeProvider() 
	{
		private static final VoxelShape HAS_UP = Block.box(0, 15, 0, 16, 16, 16);
		private static final VoxelShape HAS_DOWN = Block.box(0, 0, 0, 16, 1, 16);
		private static final VoxelShape HAS_NORTH = Block.box(0, 0, 0, 16, 16, 1);
		private static final VoxelShape HAS_SOUTH = Block.box(0, 0, 15, 16, 16, 16);
		private static final VoxelShape HAS_EAST = Block.box(15, 0, 0, 16, 16, 16);
		private static final VoxelShape HAS_WEST = Block.box(0, 0, 0, 1, 16, 16);

		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			VoxelShape res = Shapes.empty();

			if (blockState.getValue(PipeBlock.UP))
			{
				res = Shapes.or(res, HAS_UP);
			}
			if (blockState.getValue(PipeBlock.DOWN))
			{
				res = Shapes.or(res, HAS_DOWN);
			}
			if (blockState.getValue(PipeBlock.NORTH))
			{
				res = Shapes.or(res, HAS_NORTH);
			}
			if (blockState.getValue(PipeBlock.SOUTH))
			{
				res = Shapes.or(res, HAS_SOUTH);
			}
			if (blockState.getValue(PipeBlock.EAST))
			{
				res = Shapes.or(res, HAS_EAST);
			}
			if (blockState.getValue(PipeBlock.WEST))
			{
				res = Shapes.or(res, HAS_WEST);
			}
			
			return res;
		} // end getShape()
	};
	
	
	
	public static final VoxelShapeProvider SPINNING_WHEEL = new VoxelShapeProvider() 
	{
		private static final VoxelShape BODY_NORTH = Block.box(3, 0, 0, 10, 15, 15);
		private static final VoxelShape HANDLE_NORTH = Block.box(10, 8, 11, 14, 12, 12);
		
		private static final VoxelShape BODY_EAST = Block.box(1, 0, 3, 16, 15, 10);
		private static final VoxelShape HANDLE_EAST = Block.box(4, 8, 10, 5, 12, 14);
		
		private static final VoxelShape BODY_SOUTH = Block.box(6, 0, 1, 13, 15, 16);
		private static final VoxelShape HANDLE_SOUTH = Block.box(2, 8, 4, 6, 12, 5);
		
		private static final VoxelShape BODY_WEST = Block.box(0, 0, 6, 15, 15, 13);
		private static final VoxelShape HANDLE_WEST = Block.box(11, 8, 2, 12, 12, 6);
		
		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			VoxelShape ret = switch(blockState.getValue(SpinningWheelBlock.FACING)) 
					{
				case NORTH -> Shapes.or(BODY_NORTH, HANDLE_NORTH);
				case EAST -> Shapes.or(BODY_EAST, HANDLE_EAST);
				case SOUTH -> Shapes.or(BODY_SOUTH, HANDLE_SOUTH);
				case WEST -> Shapes.or(BODY_WEST, HANDLE_WEST);
				default -> throw new IllegalArgumentException("Unexpected value of: " + blockState.getValue(SpinningWheelBlock.FACING));
					};
			return ret;
		} // end getShape()
	};
	
	public static final VoxelShapeProvider BOILER_SHAPE = new VoxelShapeProvider()
	{		
		private static final VoxelShape BASE_SHAPE = Block.box(0, 0, 0, 16, 14, 16);
		private static final VoxelShape HAS_TANK_SHAPE = Block.box(1, 14, 1, 15, 16, 15);

		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			
			VoxelShape shape = BASE_SHAPE;
			
			if (blockState.getValue(BoilerBlock.HAS_TANK))
			{
				shape = Shapes.or(shape, HAS_TANK_SHAPE);
			}

			return shape;
		} // end getShape()

	};

	public static final VoxelShapeProvider SOLAR_PANEL = new VoxelShapeProvider() 
	{
		private static final VoxelShape SOLAR_PANEL = Block.box(0, 0, 0, 16, 2, 16);

		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			return SOLAR_PANEL;
		} // end getShape()
	};
	
	
		
	public static final VoxelShapeProvider STEEL_FLUID_CONDUIT_SHAPE = new VoxelShapeProvider()
	{		
		private static final VoxelShape CENTER_SHAPE = Block.box(6d, 6d, 6d, 10d, 10d, 10d);
		
		private static final VoxelShape BRANCH_SHAPE_UP = Block.box(5d, 10d, 5d, 11d, 16d, 11d);
		private static final VoxelShape BRANCH_SHAPE_DOWN = Block.box(5d, 0d, 5d, 11d, 6d, 11d);
		private static final VoxelShape BRANCH_SHAPE_NORTH = Block.box(5d, 5d, 0d, 11d, 11d, 6d);
		private static final VoxelShape BRANCH_SHAPE_SOUTH = Block.box(5d, 5d, 10d, 11d, 11d, 16d);
		private static final VoxelShape BRANCH_SHAPE_EAST = Block.box(10d, 5d, 5d, 16d, 11d, 11d);
		private static final VoxelShape BRANCH_SHAPE_WEST = Block.box(0d, 5d, 5d, 6d, 11d, 11d);
		
		@Override
		public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			VoxelShape shape = CENTER_SHAPE;

			if (blockState.getValue(IConduit.UP))
			{
				shape = Shapes.or(shape, BRANCH_SHAPE_UP);
			}
			if (blockState.getValue(IConduit.DOWN))
			{
				shape = Shapes.or(shape, BRANCH_SHAPE_DOWN);
			}if (blockState.getValue(IConduit.NORTH))
			{
				shape = Shapes.or(shape, BRANCH_SHAPE_NORTH);
			}
			if (blockState.getValue(IConduit.SOUTH))
			{
				shape = Shapes.or(shape, BRANCH_SHAPE_SOUTH);
			}
			if (blockState.getValue(IConduit.EAST))
			{
				shape = Shapes.or(shape, BRANCH_SHAPE_EAST);
			}
			if (blockState.getValue(IConduit.WEST))
			{
				shape = Shapes.or(shape, BRANCH_SHAPE_WEST);
			}

			return shape;
		} // end getShape()
	};

	public static final VoxelShapeProvider WIRE_SHAPE = new VoxelShapeProvider()
	{
			private static final VoxelShape CENTER_SHAPE =  Block.box(7d, 7d, 7d, 9d, 9d, 9d);
			private static final VoxelShape BRANCH_SHAPE_UP = Block.box(7d, 9d, 7d, 9d, 16d, 9d);
			private static final VoxelShape BRANCH_SHAPE_DOWN = Block.box(7d, 0d, 7d, 9d, 7d, 9d);
			private static final VoxelShape BRANCH_SHAPE_NORTH = Block.box(7d, 7d, 0d, 9d, 9d, 7d);
			private static final VoxelShape BRANCH_SHAPE_SOUTH = Block.box(7d, 7d, 9d, 9d, 9d, 16d);
			private static final VoxelShape BRANCH_SHAPE_EAST = Block.box(9d, 7d, 7d, 16d, 9d, 9d);
			private static final VoxelShape BRANCH_SHAPE_WEST = Block.box(0d, 7d, 7d, 7d, 9d, 9d);
			
			@Override
			public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
			{
				VoxelShape shape = CENTER_SHAPE;

				if (blockState.getValue(IConduit.UP))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_UP);
				}
				if (blockState.getValue(IConduit.DOWN))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_DOWN);
				}
				if (blockState.getValue(IConduit.NORTH))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_NORTH);
				}
				if (blockState.getValue(IConduit.SOUTH))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_SOUTH);
				}
				if (blockState.getValue(IConduit.EAST))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_EAST);
				}
				if (blockState.getValue(IConduit.WEST))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_WEST);
				}
				
				return shape;
			} // end getShape()
	};

	public static final VoxelShapeProvider INSULATED_WIRE_SHAPE = new VoxelShapeProvider()
	{
			private static final VoxelShape CENTER_SHAPE =  Block.box(6d, 6d, 6d, 10d, 10d, 10d);
			private static final VoxelShape BRANCH_SHAPE_UP = Block.box(6d, 10d, 6d, 10d, 16d, 10d);
			private static final VoxelShape BRANCH_SHAPE_DOWN = Block.box(6d, 0d, 6d, 10d, 6d, 10d);
			private static final VoxelShape BRANCH_SHAPE_NORTH = Block.box(6d, 6d, 0d, 10d, 10d, 6d);
			private static final VoxelShape BRANCH_SHAPE_SOUTH = Block.box(6d, 6d, 10d, 10d, 10d, 16d);
			private static final VoxelShape BRANCH_SHAPE_EAST = Block.box(10d, 6d, 6d, 16d, 10d, 10d);
			private static final VoxelShape BRANCH_SHAPE_WEST = Block.box(0d, 6d, 6d, 6d, 10d, 10d);
			
			@Override
			public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
			{
				VoxelShape shape = CENTER_SHAPE;

				if (blockState.getValue(IConduit.UP))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_UP);
				}
				if (blockState.getValue(IConduit.DOWN))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_DOWN);
				}
				if (blockState.getValue(IConduit.NORTH))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_NORTH);
				}
				if (blockState.getValue(IConduit.SOUTH))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_SOUTH);
				}
				if (blockState.getValue(IConduit.EAST))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_EAST);
				}
				if (blockState.getValue(IConduit.WEST))
				{
					shape = Shapes.or(shape, BRANCH_SHAPE_WEST);
				}

				return shape;
			} // end getShape()
	};

} // end class