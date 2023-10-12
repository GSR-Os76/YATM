package com.gsr.gsr_yatm.block.plant.carcass_root;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.IOccasionallySpreadableBlock;
import com.gsr.gsr_yatm.block.ISpreadabilitySettableBlock;
import com.gsr.gsr_yatm.command.PlantData;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class CarcassRootRootBlock extends Block implements BonemealableBlock, IOccasionallySpreadableBlock, ISpreadabilitySettableBlock
{
	public static final int MAX_GROW_DISTANCE = 3;
	
	public static final BooleanProperty CAN_SPREAD = YATMBlockStateProperties.CAN_SPREAD;
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_TWO;
	private final ICollisionVoxelShapeProvider m_shape;
	private final Supplier<BlockState> m_plant;

	
	
	public CarcassRootRootBlock(Properties properties, ICollisionVoxelShapeProvider shape, Supplier<BlockState> plant)
	{
		super(properties);
		
		this.registerDefaultState(this.defaultBlockState().setValue(CarcassRootRootBlock.CAN_SPREAD, true).setValue(CarcassRootRootBlock.AGE, 0));
	
		this.m_shape = shape;
		this.m_plant = plant;
	} // end constructor
	
	
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(CarcassRootRootBlock.CAN_SPREAD, CarcassRootRootBlock.AGE));
	} // end createBlockStateDefinition()

	protected int getAge(BlockState state) 
	{
		return state.getValue(CarcassRootRootBlock.AGE);
	} // end getAge()
	
	protected BlockState getStateForAge(BlockState state, int age) 
	{
		return state.setValue(CarcassRootRootBlock.AGE, age);
	} // end getStateForAge()
	
	protected int maxAge() 
	{
		// TODO, ideal such values wouldn't be hard coded.
		return 1;
	} // end maxAge()
	

	
	@Override
	public boolean isRandomlyTicking(BlockState state)
	{
		return this.getAge(state) < 1;
	} // end isRandomlyTicking()
	



	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		int goingToAge = this.getAge(state) + 1;				
		if (goingToAge <= 1)
		{
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(128) == 0))
			{
				level.setBlock(position, this.getStateForAge(state, goingToAge), 2);				
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
		else
		{
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(12) == 0))
			{
				// TODO, maybe add more restrictions to this kind of growth, like limiting density of plants
				Runnable s = this.simulateSendUpPlant(level, state, position);
				if(s != null) 
				{
					s.run();
				}
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()
	
	
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()
	
	
	



	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos position, BlockState state, boolean p_50900_)
	{
		Boolean canidateFound = false;
		for(int y = 1; y <= CarcassRootRootBlock.MAX_GROW_DISTANCE; y++) 
		{
			if(this.isPositionValid(levelReader, position.above(y))) 
			{
				canidateFound = true;
				break;
			}
		}
		return this.getAge(state) < this.maxAge() || canidateFound;
	} // end isValidBonemealTarget()
	
	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos position, BlockState state)
	{
		return true;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos position, BlockState state)
	{
		int age = this.getAge(state);
		if(age < this.maxAge()) 
		{
			level.setBlock(position, this.getStateForAge(state, age + 1), Block.UPDATE_CLIENTS);
		}
		else 
		{
			Runnable plantPlacer = this.simulateSendUpPlant(level, state, position);
			if(plantPlacer != null) 
			{
				plantPlacer.run();
			}
		}
	} // end performBonemeal()
	
	
	
	@Override
	public boolean canSpread(Level level, BlockState state, BlockPos pos)
	{
		return state.getValue(CarcassRootFoliageBlock.CAN_SPREAD);
	} // end canSpread

	@Override
	public @NotNull BlockState setSpreadability(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, boolean isSpreadable)
	{
		return state.setValue(CarcassRootFoliageBlock.CAN_SPREAD, isSpreadable);
	} // end setSpreadability()
	
	
	
	public @Nullable Runnable simulateSendUpPlant(ServerLevel level, BlockState state, BlockPos position) 
	{
		if(!this.canSpread(level, state, position)) 
		{
			return null;
		}
		
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos().set(position);
		for(int y = 1; y <= CarcassRootRootBlock.MAX_GROW_DISTANCE; y++) 
		{
			pos.move(0, 1, 0);
			if(this.isPositionValid(level, pos)) 
			{
				BlockState placingState = this.m_plant.get();
				if(placingState.getBlock() instanceof ISpreadabilitySettableBlock ss) 
				{
					// TODO, probably add unbound horizontal spread as a game rule
					return () -> level.setBlock(pos, ss.setSpreadability(level, placingState, pos, PlantData.isHorizontalGrowthUnbound(level)), Block.UPDATE_ALL);
				}				
				return () -> level.setBlock(pos, placingState, Block.UPDATE_ALL);
			}
		}
		return null;
	} // end simulateSendUpPlant()
	
	protected boolean isPositionValid(LevelReader level, BlockPos position) 
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.CARCASS_ROOT_CAN_GROW_ON_KEY) && level.getBlockState(position).isAir(); 
	} // end isPositionValid()
	
} // end class