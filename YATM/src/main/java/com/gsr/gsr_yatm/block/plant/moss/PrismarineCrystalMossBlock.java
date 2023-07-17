package com.gsr.gsr_yatm.block.plant.moss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.gsr.gsr_yatm.block.plant.CustomSeedCropBlock;
import com.gsr.gsr_yatm.utilities.VoxelShapeProvider;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PrismarineCrystalMossBlock extends CustomSeedCropBlock implements SimpleWaterloggedBlock 
{
	public static final int MAX_AGE = 1;
	
	public static final ImmutableList<BlockPos> SPREADABLE_RELATIVE_POSITIONS = ImmutableList.of(BlockPos.ZERO, BlockPos.ZERO.above(), BlockPos.ZERO.below(), BlockPos.ZERO.north(), BlockPos.ZERO.south(), BlockPos.ZERO.east(), BlockPos.ZERO.west());
	
	
	
	public static final BooleanProperty CAN_SPREAD = BooleanProperty.create("can_spread");
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public static final IntegerProperty UP_AGE = IntegerProperty.create("up_age", 0, MAX_AGE);
	public static final IntegerProperty DOWN_AGE = IntegerProperty.create("down_age", 0, MAX_AGE);
	public static final IntegerProperty NORTH_AGE = IntegerProperty.create("north_age", 0, MAX_AGE);
	public static final IntegerProperty SOUTH_AGE = IntegerProperty.create("south_age", 0, MAX_AGE);
	public static final IntegerProperty EAST_AGE = IntegerProperty.create("east_age", 0, MAX_AGE);
	public static final IntegerProperty WEST_AGE = IntegerProperty.create("west_age", 0, MAX_AGE);
	
	public static final Map<Direction, BooleanProperty> HAS_FACE_PROPERTIES_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;
	public static final Map<Direction, IntegerProperty> AGE_PROPERTIES_BY_DIRECTION = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), (em) -> {
		em.put(Direction.UP, UP_AGE);
		em.put(Direction.DOWN, DOWN_AGE);
	    em.put(Direction.NORTH, NORTH_AGE);
		em.put(Direction.SOUTH, SOUTH_AGE);
		em.put(Direction.EAST, EAST_AGE);
		em.put(Direction.WEST, WEST_AGE);
		}));
	private final VoxelShapeProvider m_shape;
	
	   
	
	public PrismarineCrystalMossBlock(Properties properties, VoxelShapeProvider shape, Supplier<ItemLike> seed)
	{
		super(properties, seed);
		
		this.m_shape = shape;
		
		BlockState def = this.stateDefinition.any().setValue(PrismarineCrystalMossBlock.WATERLOGGED, false);	
		def = def.setValue(CAN_SPREAD, true);
		for(BooleanProperty p : HAS_FACE_PROPERTIES_BY_DIRECTION.values()) 
		{
			def = def.setValue(p, false);
		}
		for(IntegerProperty p : AGE_PROPERTIES_BY_DIRECTION.values()) 
		{
			def = def.setValue(p, 0);
		}

		this.registerDefaultState(def);		
	} // end constructor	
	
	
	
	@Override
	protected IntegerProperty getAgeProperty()
	{
		return PrismarineCrystalMossBlock.DOWN_AGE;
	} // end getAgeProperty()

	@Override
	public int getMaxAge()
	{
		// TODO Auto-generated method stub
		return PrismarineCrystalMossBlock.MAX_AGE;
	} // end getMaxAge()



	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context)
	{
		return state.is(this);
	} // end canBeReplaced()

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos)
	{
		return this.canPlaceOn(blockGetter, pos, state, Direction.DOWN);
	} // end mayPlaceOn()
	
	


	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos position)
	{
		return true;
	} // end canSurvive()
	
	



	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		PrismarineCrystalMossBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.values().forEach((p) -> builder.add(p));
		PrismarineCrystalMossBlock.AGE_PROPERTIES_BY_DIRECTION.values().forEach((p) -> builder.add(p));
		builder.add(CAN_SPREAD);
		builder.add(WATERLOGGED);
	} // end createBlockStateDefinition()
	
	@Override @Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState bs = level.getBlockState(pos);
		
//		BlockState res = bs.is(this) ? bs : this.defaultBlockState();
//		res = res.setValue(PrismarineCrystalMossBlock.WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
//		
		Direction d = context.getClickedFace().getOpposite();
//		BlockPos willBeOnPos = pos.relative(d);
//		if(this.canPlaceOn(level, willBeOnPos, level.getBlockState(willBeOnPos), d.getOpposite())) 
//		{
//			BooleanProperty prop = PrismarineCrystalMossBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(d);
//			if(!res.getValue(prop)) 
//			{
//				res = res.setValue(prop, true);
//			}
//			else 
//			{
//				return null;
//			}
//		}
//		else 
//		{
//			return null;
//		}
//		return res;
		return this.getStateForPlacement(level, pos, bs, d);
	} // end getStateForPlacement()
	
	public @Nullable BlockState getStateForPlacement(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState replacing, @NotNull Direction clickedFace) 
	{
		BlockState res = replacing.is(this) ? replacing : this.defaultBlockState();
		res = res.setValue(PrismarineCrystalMossBlock.WATERLOGGED, level.getFluidState(position).getType() == Fluids.WATER);
		
		BlockPos willBeOnPos = position.relative(clickedFace);
		if(this.canPlaceOn(level, willBeOnPos, level.getBlockState(willBeOnPos), clickedFace.getOpposite())) 
		{
			BooleanProperty prop = PrismarineCrystalMossBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(clickedFace);
			if(!res.getValue(prop)) 
			{
				res = res.setValue(prop, true);
			}
			else 
			{
				return null;
			}
		}
		else 
		{
			return null;
		}
		return res;
	} // end getStateForPlacement()

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockStateSecond, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPosSecond)
	{
		if (blockState.getValue(WATERLOGGED))
		{
			levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}

		return super.updateShape(blockState, direction, blockStateSecond, levelAccessor, blockPos, blockPosSecond);
	} // end updateShape()

	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState updateShape)
	{
		return updateShape.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(updateShape);
	} // end getFluidState

	
	

	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()

	
	
	
	
	
	
	

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState startState, ServerLevel level, BlockPos pos, RandomSource random)
	{
		if (!level.isAreaLoaded(pos, 1)) 
		{
			return;
		}
		if (level.getRawBrightness(pos, 0) <= 7 && startState.getValue(PrismarineCrystalMossBlock.WATERLOGGED))
		{
			for(Direction face : this.getGrowingFacesFor(startState)) 
			{
				int startVal = startState.getValue(PrismarineCrystalMossBlock.AGE_PROPERTIES_BY_DIRECTION.get(face));
				if(startVal < this.getMaxAge()) 
				{
					if(random.nextInt(12) == 0)
					{
						level.setBlock(pos, startState.cycle(PrismarineCrystalMossBlock.AGE_PROPERTIES_BY_DIRECTION.get(face)), 3);
					}
				}
				else if(random.nextInt(12) == 0)
				{
					this.tryPropagate(startState, level, pos, random);
				}
				
			}
		}
	} // end randomTick()
	
	
	
	private boolean canPlaceOn(BlockGetter getter, BlockPos onBlock, BlockState onState, Direction cansface) 
	{
		return Block.isFaceFull(onState.getBlockSupportShape(getter, onBlock), cansface);
	} // end canPlaceOn()
	
	private List<Direction> getGrowingFacesFor(BlockState f)
	{
		List<Direction> has = new ArrayList<>();
		for(Map.Entry<Direction, BooleanProperty> e : PrismarineCrystalMossBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.entrySet()) 
		{
			if(f.getValue(e.getValue())) 
			{
				has.add(e.getKey());
			}
		}
		return has;
	} // end getGrowingFacesFor()
	
	protected void tryPropagate(BlockState startState, ServerLevel level, BlockPos pos, RandomSource random) 
	{
		for(int i = 0; i < 6; i++) 
		{
			BlockPos toCheck = pos.offset(SPREADABLE_RELATIVE_POSITIONS.get(random.nextIntBetweenInclusive(0, SPREADABLE_RELATIVE_POSITIONS.size() - 1)));
			BlockState toReplace = level.getBlockState(toCheck);
			if(toReplace.canBeReplaced()) 
			{
				for(Direction simulatedClickedFace : Direction.allShuffled(random))
				{					
					BlockState setRes = this.getStateForPlacement(level, toCheck, toReplace, simulatedClickedFace);
					if(setRes != null) 
					{
						level.setBlock(toCheck, setRes.setValue(CAN_SPREAD, false), 3);
						return;
					}
				}
			}
		}
		
	} // end tryPropagate()
	
} // end class