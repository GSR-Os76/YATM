package com.gsr.gsr_yatm.utilities.shape;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.conduit.IConduit;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
import com.gsr.gsr_yatm.block.device.spinning_wheel.SpinningWheelBlock;
import com.gsr.gsr_yatm.block.plant.basin_of_tears.BasinOfTearsVegetationBlock;
import com.gsr.gsr_yatm.block.plant.carbum.CarbumBlock;
import com.gsr.gsr_yatm.block.plant.ferrum.FerrumBlock;
import com.gsr.gsr_yatm.block.plant.fire_eater_lily.FireEaterLilyBlock;
import com.gsr.gsr_yatm.block.plant.fungi.PhantasmalShelfFungiBlock;
import com.gsr.gsr_yatm.block.plant.ice_coral.IceCoralBlock;
import com.gsr.gsr_yatm.block.plant.parasite.ShulkwartBlock;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;

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
	public static final VoxelShape ICE_CORAL_POLYP = Block.box(5d, 0d, 5d, 11d, 3d, 11d);
	public static final VoxelShape ICE_CORAL_YOUNG = Block.box(3d, 0d, 3d, 13d, 7d, 13d);
	public static final VoxelShape ICE_CORAL_ADOLESCENT = Block.box(1d, 0d, 1d, 15d, 9d, 15d);
	public static final VoxelShape ICE_CORAL_OLD = Block.box(0d, 0d, 0d, 16d, 16d, 16d);

	
	
	public static final ICollisionVoxelShapeProvider CUBE = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape CUBE = Block.box(0d, 0d, 0d, 16d, 16d, 16d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			return CUBE;
		} // end getShape()
	};
	
	
	
	public static final ICollisionVoxelShapeProvider DOWNWARD_LICHEN_LIKE = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape SHAPE = Block.box(0d, 0d, 0d, 16d, 1d, 16d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			return SHAPE;
		} // end getShape()
	};
	
	public static final ICollisionVoxelShapeProvider BASIN_OF_TEARS_VEGETATION = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape SPROUT = Block.box(1d, 0d, 1d, 15d, 6d, 15d);
		private static final VoxelShape YOUNG = Block.box(0d, 0d, 0d, 16d, 9d, 16d);
		private static final VoxelShape ADOLESCENT = Block.box(0d, 0d, 0d, 16d, 15d, 16d);
		private static final VoxelShape OLD = Block.box(0d, 0d, 0d, 16d, 16d, 16d);

		@Override
		public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos position, @NotNull CollisionContext collisionContext)
		{
			return switch(state.getValue(BasinOfTearsVegetationBlock.AGE)) 
			{
				case 0 -> SPROUT;
				case 1 -> YOUNG;
				case 2 -> ADOLESCENT;
				case 3 -> OLD;
				default -> throw new IllegalArgumentException("Unexpected value of: " + state.getValue(BasinOfTearsVegetationBlock.AGE));
			};
		} // end getShape()
	};	
	
	
	public static final ICollisionVoxelShapeProvider CARBUM = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape MERISTEM = Block.box(2d, 0d, 2d, 14d, 7d, 14d);
		private static final VoxelShape YOUNG = Block.box(0d, 0d, 0d, 16d, 9d, 16d);
		private static final VoxelShape ADOLESCENT = Block.box(0d, 0d, 0d, 16d, 11d, 16d);
		private static final VoxelShape OLD = Block.box(0d, 0d, 0d, 16d, 11d, 16d);

		@Override
		public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos position, @NotNull CollisionContext collisionContext)
		{
			return switch(state.getValue(CarbumBlock.AGE)) 
			{
				case 0, 1 -> MERISTEM;
				case 2, 3 -> YOUNG;
				case 4, 5, 6 -> ADOLESCENT;
				case 7 -> OLD;
				default -> throw new IllegalArgumentException("Unexpected value of: " + state.getValue(CarbumBlock.AGE));
			};
		} // end getShape()
	};	
	
	public static final ICollisionVoxelShapeProvider FERRUM = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape YOUNG = Block.box(4d, 0d, 4d, 12d, 7d, 12d);
		private static final VoxelShape ADOLESCENT = Block.box(4d, 0d, 4d, 12d, 8d, 12d);
		private static final VoxelShape MATURE = Block.box(2d, 0d, 2d, 14d, 9d, 14d);
		private static final VoxelShape OLD = Block.box(2d, 0d, 2d, 14d, 11d, 14d);

		@Override
		public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos position, @NotNull CollisionContext collisionContext)
		{
			return switch(state.getValue(FerrumBlock.AGE)) 
			{
				case 0, 1 -> YOUNG;
				case 2, 3 -> ADOLESCENT;
				case 4, 5, 6 -> MATURE;
				case 7 -> OLD;
				default -> throw new IllegalArgumentException("Unexpected value of: " + state.getValue(FerrumBlock.AGE));
			};
		} // end getShape()
	};	
	
	public static final ICollisionVoxelShapeProvider FIRE_EATER_LILY = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape BULB = Block.box(6d, 0d, 6d, 10d, 3d, 10d);
		private static final VoxelShape YOUNG = Block.box(5d, 0d, 5d, 11d, 4d, 11d);
		private static final VoxelShape ADOLESCENT_LIT = Block.box(5d, 0d, 5d, 11d, 5d, 11d);
		private static final VoxelShape ADOLESCENT_UNLIT = Block.box(4d, 0d, 4d, 12d, 5d, 12d);
		private static final VoxelShape OLD_LIT = Block.box(3d, 0d, 3d, 13d, 10d, 13d);
		private static final VoxelShape OLD_UNLIT = Block.box(1d, 0d, 1d, 15d, 9d, 15d);

		@Override
		public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos position, @NotNull CollisionContext collisionContext)
		{
			boolean lit = state.getValue(FireEaterLilyBlock.LIT);
			return switch(state.getValue(FireEaterLilyBlock.AGE)) 
			{
				case 0, 1 -> BULB;
				case 2, 3 -> YOUNG;
				case 4, 5, 6 -> lit ? ADOLESCENT_LIT : ADOLESCENT_UNLIT;
				case 7 -> lit ? OLD_LIT : OLD_UNLIT;
				default -> throw new IllegalArgumentException("Unexpected of value: " + state.getValue(FireEaterLilyBlock.AGE));
			};
		} // end getShape()
	};	
		
	
	public static final ICollisionVoxelShapeProvider ICE_CORAL = new ICollisionVoxelShapeProvider() 
	{
		@Override
		public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos position, @NotNull CollisionContext collisionContext)
		{
			return switch(state.getValue(IceCoralBlock.AGE)) 
			{
				case 0, 1 -> ICE_CORAL_POLYP;
				case 2, 3 -> ICE_CORAL_YOUNG;
				case 4, 5, 6 -> ICE_CORAL_ADOLESCENT;
				case 7 -> ICE_CORAL_OLD;
				default -> throw new IllegalArgumentException("Unexpected value of: " + state.getValue(IceCoralBlock.AGE));
			};
		} // end getShape()
	};	
	
	public static final ICollisionVoxelShapeProvider PHANTASMAL_SHELF_FUNGUS = new ICollisionVoxelShapeProvider() 
	{
		private static final IVoxelShapeBuilder SMALL = IVoxelShapeBuilder.MutableWrapper.of((VoxelShapeBuilder
				.box(11d, 12d, 14d, 14d, 14d, 16d)
				.or(VoxelShapeBuilder.box(5d, 8d, 14d, 9d, 10d, 16d))
				.or(VoxelShapeBuilder.box(10d, 5d, 15d, 12d, 6d, 16d))));
		private static final VoxelShape SMALL_NORTH = SMALL.toMCVoxelShape();
		private static final VoxelShape SMALL_WEST = SMALL.yRotateLookingDownCounterClockwise().toMCVoxelShape();
		private static final VoxelShape SMALL_SOUTH = SMALL.yRotateLookingDownCounterClockwise().toMCVoxelShape();
		private static final VoxelShape SMALL_EAST = SMALL.yRotateLookingDownCounterClockwise().toMCVoxelShape();

		private static final IVoxelShapeBuilder MEDIUM = IVoxelShapeBuilder.MutableWrapper.of((VoxelShapeBuilder
				.box(10d, 12d, 14d, 14d, 14d, 16d)
				.or(VoxelShapeBuilder.box(3d, 12d, 15d, 5d, 13d, 16d))
				.or(VoxelShapeBuilder.box(4d, 9d, 12d, 10d, 11d, 16d))
				.or(VoxelShapeBuilder.box(9d, 4d, 14d, 12d, 6d, 16d))));
		private static final VoxelShape MEDIUM_NORTH = MEDIUM.toMCVoxelShape();
		private static final VoxelShape MEDIUM_WEST = MEDIUM.yRotateLookingDownCounterClockwise().toMCVoxelShape();
		private static final VoxelShape MEDIUM_SOUTH = MEDIUM.yRotateLookingDownCounterClockwise().toMCVoxelShape();
		private static final VoxelShape MEDIUM_EAST = MEDIUM.yRotateLookingDownCounterClockwise().toMCVoxelShape();

		private static final IVoxelShapeBuilder LARGE = IVoxelShapeBuilder.MutableWrapper.of((VoxelShapeBuilder
				.box(8d, 13d, 12d, 14d, 15d, 16d)
				.or(VoxelShapeBuilder.box(3d, 12d, 14d, 7d, 14d, 16d))
				.or(VoxelShapeBuilder.box(2d, 7d, 9d, 14d, 11d, 16d))
				.or(VoxelShapeBuilder.box(6d, 3d, 11d, 14d, 6d, 16d))
				.or(VoxelShapeBuilder.box(2d, 2d, 14d, 5d, 4d, 16d))));
		private static final VoxelShape LARGE_NORTH = LARGE.toMCVoxelShape();
		private static final VoxelShape LARGE_WEST = LARGE.yRotateLookingDownCounterClockwise().toMCVoxelShape();
		private static final VoxelShape LARGE_SOUTH = LARGE.yRotateLookingDownCounterClockwise().toMCVoxelShape();
		private static final VoxelShape LARGE_EAST = LARGE.yRotateLookingDownCounterClockwise().toMCVoxelShape();

		@Override
		public @NotNull VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			return switch(state.getValue(PhantasmalShelfFungiBlock.AGE)) 
			{
				// TODO, these ranges and the one in the YATMBlockStateProvider should be variable, not hard coded off in distant places, it's poorly resilient
				case 0, 1, 2 -> switch (state.getValue(PhantasmalShelfFungiBlock.FACING)) 
				{
					case NORTH -> SMALL_NORTH;
					case WEST -> SMALL_WEST;
					case SOUTH -> SMALL_SOUTH;
					case EAST -> SMALL_EAST;
					default -> throw new IllegalArgumentException("Unexpected value of: " + state.getValue(PhantasmalShelfFungiBlock.FACING));	
				};
				case 3, 4, 5, 6 -> switch (state.getValue(PhantasmalShelfFungiBlock.FACING)) 
				{
					case NORTH -> MEDIUM_NORTH;
					case WEST -> MEDIUM_WEST;
					case SOUTH -> MEDIUM_SOUTH;
					case EAST -> MEDIUM_EAST;
					default -> throw new IllegalArgumentException("Unexpected value of: " + state.getValue(PhantasmalShelfFungiBlock.FACING));	
				};
				case 7 -> switch (state.getValue(PhantasmalShelfFungiBlock.FACING)) 
				{
					case NORTH -> LARGE_NORTH;
					case WEST -> LARGE_WEST;
					case SOUTH -> LARGE_SOUTH;
					case EAST -> LARGE_EAST;
					default -> throw new IllegalArgumentException("Unexpected value of: " + state.getValue(PhantasmalShelfFungiBlock.FACING));	
				};
				default -> throw new IllegalArgumentException("Unexpected value of: " + state.getValue(PhantasmalShelfFungiBlock.AGE));
			};
		} // end getShape()
	};
	
	public static final ICollisionVoxelShapeProvider PRISMARINE_CRYSTAL_MOSS = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape HAS_UP = Block.box(0d, 15d, 0d, 16d, 16d, 16d);
		private static final VoxelShape HAS_DOWN = Block.box(0d, 0d, 0d, 16d, 1d, 16d);
		private static final VoxelShape HAS_NORTH = Block.box(0d, 0d, 0d, 16d, 16d, 1d);
		private static final VoxelShape HAS_SOUTH = Block.box(0d, 0d, 15d, 16d, 16d, 16d);
		private static final VoxelShape HAS_EAST = Block.box(15d, 0d, 0d, 16d, 16d, 16d);
		private static final VoxelShape HAS_WEST = Block.box(0d, 0d, 0d, 1d, 16d, 16d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
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
	
	public static final ICollisionVoxelShapeProvider SHULKWART = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape SHAPE_ZERO = Block.box(6, 15d, 6d, 10d, 16d, 10d);
		private static final VoxelShape SHAPE_ONE = Block.box(7d, 8d, 7d, 9d, 16d, 9d);
		private static final VoxelShape SHAPE_TWO = Block.box(4d, 3d, 4d, 12d, 16d, 12d);
		private static final VoxelShape SHAPE_THREE = Block.box(1d, 0d, 1d, 15d, 16d, 15d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			return switch(blockState.getValue(ShulkwartBlock.AGE)) 
			{
				case 0, 1, 2, 3 -> SHAPE_ZERO;
				case 4 -> SHAPE_ONE;
				case 5 -> SHAPE_TWO;
				case 6, 7 -> SHAPE_THREE;
				default -> throw new IllegalArgumentException("Unexpected value of: " + blockState.getValue(ShulkwartBlock.AGE));
			};
		} // end getShape()
	};

	
	
	
	public static final ICollisionVoxelShapeProvider HANGING_POT_HOOK = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape TOP_PIECE = Block.box(6d, 15d, 6d, 10d, 16d, 10d);
		private static final VoxelShape CHAIN_LINK = Block.box(6.5d, 12d, 6.5d, 9.5d, 15d, 9.5d);//Block.box(6d, 12d, 6d, 9d, 15d, 9d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			return Shapes.or(TOP_PIECE, CHAIN_LINK);
		} // end getShape()
	};
	
	public static final ICollisionVoxelShapeProvider SAP_COLLECTOR_SHAPE = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape SAP_COLLECTOR_SHAPE = Block.box(0d, 0d, 0d, 16d, 8d, 16d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			return SAP_COLLECTOR_SHAPE;
		} // end getShape()
	};
	
	
	public static final ICollisionVoxelShapeProvider SPINNING_WHEEL = new ICollisionVoxelShapeProvider() 
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
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
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
	
	public static final ICollisionVoxelShapeProvider BOILER_SHAPE = new ICollisionVoxelShapeProvider()
	{		
		private static final VoxelShape BASE_SHAPE = Block.box(0d, 0d, 0d, 16d, 14d, 16d);
		private static final VoxelShape HAS_TANK_SHAPE = Block.box(1d, 14d, 1d, 15d, 16d, 15d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			
			VoxelShape shape = BASE_SHAPE;
			
			if (blockState.getValue(BoilerBlock.HAS_TANK))
			{
				shape = Shapes.or(shape, HAS_TANK_SHAPE);
			}

			return shape;
		} // end getShape()

	};

	public static final ICollisionVoxelShapeProvider SOLAR_PANEL = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape SOLAR_PANEL = Block.box(0d, 0d, 0d, 16d, 2d, 16d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			return SOLAR_PANEL;
		} // end getShape()
	};
	
	
	public static final ICollisionVoxelShapeProvider CONDUIT_VINES = new ICollisionVoxelShapeProvider() 
	{
		private static final VoxelShape HAS_UP = Block.box(0d, 15d, 0d, 16d, 16d, 16d);
		private static final VoxelShape HAS_DOWN = Block.box(0d, 0d, 0d, 16d, 1d, 16d);
		private static final VoxelShape HAS_NORTH = Block.box(0d, 0d, 0d, 16d, 16d, 1d);
		private static final VoxelShape HAS_SOUTH = Block.box(0d, 0d, 15d, 16d, 16d, 16d);
		private static final VoxelShape HAS_EAST = Block.box(15d, 0d, 0d, 16d, 16d, 16d);
		private static final VoxelShape HAS_WEST = Block.box(0d, 0d, 0d, 1d, 16d, 16d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
		{
			VoxelShape result = Shapes.empty();

			if (blockState.getValue(YATMBlockStateProperties.HAS_UP))
			{
				result = Shapes.or(result, HAS_UP);
			}
			if (blockState.getValue(YATMBlockStateProperties.HAS_DOWN))
			{
				result = Shapes.or(result, HAS_DOWN);
			}
			if (blockState.getValue(YATMBlockStateProperties.HAS_NORTH))
			{
				result = Shapes.or(result, HAS_NORTH);
			}
			if (blockState.getValue(YATMBlockStateProperties.HAS_SOUTH))
			{
				result = Shapes.or(result, HAS_SOUTH);
			}
			if (blockState.getValue(YATMBlockStateProperties.HAS_EAST))
			{
				result = Shapes.or(result, HAS_EAST);
			}
			if (blockState.getValue(YATMBlockStateProperties.HAS_WEST))
			{
				result = Shapes.or(result, HAS_WEST);
			}
			
			// TODO, add parallel crosslink shapes in once designed
			
			return result;
		} // end getShape()
	};
	
		
	public static final ICollisionVoxelShapeProvider STEEL_FLUID_CONDUIT_SHAPE = new ICollisionVoxelShapeProvider()
	{		
		private static final VoxelShape CENTER_SHAPE = Block.box(6d, 6d, 6d, 10d, 10d, 10d);
		
		private static final VoxelShape BRANCH_SHAPE_UP = Block.box(5d, 10d, 5d, 11d, 16d, 11d);
		private static final VoxelShape BRANCH_SHAPE_DOWN = Block.box(5d, 0d, 5d, 11d, 6d, 11d);
		private static final VoxelShape BRANCH_SHAPE_NORTH = Block.box(5d, 5d, 0d, 11d, 11d, 6d);
		private static final VoxelShape BRANCH_SHAPE_SOUTH = Block.box(5d, 5d, 10d, 11d, 11d, 16d);
		private static final VoxelShape BRANCH_SHAPE_EAST = Block.box(10d, 5d, 5d, 16d, 11d, 11d);
		private static final VoxelShape BRANCH_SHAPE_WEST = Block.box(0d, 5d, 5d, 6d, 11d, 11d);
		
		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
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

	public static final ICollisionVoxelShapeProvider WIRE_SHAPE = new ICollisionVoxelShapeProvider()
	{
		private static final VoxelShape CENTER_SHAPE = Block.box(7d, 7d, 7d, 9d, 9d, 9d);
		private static final VoxelShape BRANCH_SHAPE_UP = Block.box(7d, 9d, 7d, 9d, 16d, 9d);
		private static final VoxelShape BRANCH_SHAPE_DOWN = Block.box(7d, 0d, 7d, 9d, 7d, 9d);
		private static final VoxelShape BRANCH_SHAPE_NORTH = Block.box(7d, 7d, 0d, 9d, 9d, 7d);
		private static final VoxelShape BRANCH_SHAPE_SOUTH = Block.box(7d, 7d, 9d, 9d, 9d, 16d);
		private static final VoxelShape BRANCH_SHAPE_EAST = Block.box(9d, 7d, 7d, 16d, 9d, 9d);
		private static final VoxelShape BRANCH_SHAPE_WEST = Block.box(0d, 7d, 7d, 7d, 9d, 9d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
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

	public static final ICollisionVoxelShapeProvider INSULATED_WIRE_SHAPE = new ICollisionVoxelShapeProvider()
	{
		private static final VoxelShape CENTER_SHAPE = Block.box(6d, 6d, 6d, 10d, 10d, 10d);
		private static final VoxelShape BRANCH_SHAPE_UP = Block.box(6d, 10d, 6d, 10d, 16d, 10d);
		private static final VoxelShape BRANCH_SHAPE_DOWN = Block.box(6d, 0d, 6d, 10d, 6d, 10d);
		private static final VoxelShape BRANCH_SHAPE_NORTH = Block.box(6d, 6d, 0d, 10d, 10d, 6d);
		private static final VoxelShape BRANCH_SHAPE_SOUTH = Block.box(6d, 6d, 10d, 10d, 10d, 16d);
		private static final VoxelShape BRANCH_SHAPE_EAST = Block.box(10d, 6d, 6d, 16d, 10d, 10d);
		private static final VoxelShape BRANCH_SHAPE_WEST = Block.box(0d, 6d, 6d, 6d, 10d, 10d);

		@Override
		public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
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

	
	
	public static final BlockShapesProvider SAP_COLLECTOR_SHAPES = BlockShapesProvider.Builder.of(YATMBlockShapes.SAP_COLLECTOR_SHAPE).build();

} // end class