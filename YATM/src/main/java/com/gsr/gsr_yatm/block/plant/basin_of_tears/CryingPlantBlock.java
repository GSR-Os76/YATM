package com.gsr.gsr_yatm.block.plant.basin_of_tears;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.ForgeHooks;

public class CryingPlantBlock extends ShapeBlock implements IAgingBlock, IYATMPlantableBlock, BonemealableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_FOUR;
	
	private final @NotNull Supplier<BlockState> m_flower;
	
	
	
	public CryingPlantBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, @NotNull Supplier<BlockState> flower)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(CryingPlantBlock.AGE, 0));
		
		this.m_flower = Objects.requireNonNull(flower);
	} // end constructor

	
	
	
	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return CryingPlantBlock.AGE;
	} // end getAgeProperty()

	
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(CryingPlantBlock.AGE));
	} // end createBlockStateDefinition()



	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		BlockState below = level.getBlockState(position.below());
		return below.is(YATMBlockTags.CRYING_PLANT_CAN_GROW_ON_KEY) 
				|| below == this.getStateForAge(this.defaultBlockState(), this.getMaxAge());
	} // end canSurvive()

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Block formerNeighbor, @NotNull BlockPos neighborPos, boolean p_60514_)
	{
		// spatially stack blocks aren't breaking when unsupported. nor are single, require extra update.
		// never becomes flower(s)
		if(!this.canSurvive(state, level, position)) 
		{
			BlockUtilities.breakBlock(level, state, position);
		}
		super.neighborChanged(state, level, position, formerNeighbor, neighborPos, p_60514_);		
	} // end neighborChanged()
	
	@Override
	public boolean canPlantOn(@NotNull LevelReader level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull Direction face)
	{
		BlockPos above = position.above();
		BlockState aboveState = level.getBlockState(above);
		return face == Direction.UP 
				&& this.canGrowInto(aboveState)
				&& this.canSurvive(aboveState, level, above);
	} // end canPlantOn()
	
	protected boolean canGrowInto(@NotNull BlockState state) 
	{
		return state.is(YATMBlockTags.CRYING_PLANT_CAN_GROW_IN_KEY);
	} // end canGrowInto()

	
	
	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return this.getAge(state) < this.getMaxAge();
	} // end isRandomlyTicking()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();

		if ((age + 1) == maxAge)
		{
			BlockPos above = position.above();
			// if full height die back plant and grow flower(s)
			if (this.stackCount(level, position) >= YATMConfigs.CRYING_PLANT_MAX_HEIGHT.get())
			{
				if(random.nextInt(YATMConfigs.CRYING_PLANT_FLOWER_RARITY.get()) == 0) 
				{
				this.plantFlower(level, position);
				ForgeHooks.onCropsGrowPost(level, position, state);
				}
			}
			// otherwise grow up a block
			else if (this.canGrowInto(level.getBlockState(above)) 
					&& ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.CRYING_PLANT_GROWTH_RARITY.get()) == 0))
			{
				level.setBlock(position, this.getStateForAge(state, age + 1), Block.UPDATE_CLIENTS);
				level.setBlock(above, this.getStateForAge(state, 0), Block.UPDATE_ALL);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
		// standard intermediary growth
		else if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.CRYING_PLANT_GROWTH_RARITY.get()) == 0))
		{
			level.setBlock(position, this.getStateForAge(state, age + 1), Block.UPDATE_CLIENTS);
			ForgeHooks.onCropsGrowPost(level, position, state);
		}
	} // end randomTick()
	
	private @NotNegative int stackCount(@NotNull Level level, @NotNull BlockPos position) 
	{
		int count = 0;
		BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos().set(position);
		while(level.getBlockState(mpos).is(YATMBlockTags.CRYING_PLANT_VEGETATION_KEY)) 
		{
			++count;
			mpos.setWithOffset(mpos, Direction.DOWN);
		}
		return count;
	} // end stackBelowCount()

	private void plantFlower(@NotNull Level level, @NotNull BlockPos position) 
	{
		BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos().set(position);
		while(level.getBlockState(mpos).is(YATMBlockTags.CRYING_PLANT_VEGETATION_KEY)) 
		{
			mpos.setWithOffset(mpos, Direction.DOWN);
		}
		level.setBlock(mpos.above(), this.m_flower.get(), Block.UPDATE_ALL);
	} // end plantFlower()
	


	// TODO, consider: maybe bonemealing a fully grown block should grow any above it if possible, out of convenince and logic
	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return state.getValue(this.getAgeProperty()) < this.getMaxAge();
	} // end isValidBonemealTarget()

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return random.nextInt(YATMConfigs.CRYING_PLANT_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		// NOTE: this must coordinate with randomTick()
		int destinedAge = this.getAge(state) + 1;
		if (destinedAge == this.getMaxAge())
		{
			if (this.stackCount(level, position) >= YATMConfigs.CRYING_PLANT_MAX_HEIGHT.get())
			{
				this.plantFlower(level, position);
			}
			else
			{
				BlockPos above = position.above();
				if (this.canGrowInto(level.getBlockState(above)))
				{
					level.setBlock(position, this.getStateForAge(state, destinedAge), Block.UPDATE_CLIENTS);
					level.setBlock(above, this.getStateForAge(state, 0), Block.UPDATE_ALL);
				}
			}
		}
		else
		{
			level.setBlock(position, this.getStateForAge(state, destinedAge), Block.UPDATE_CLIENTS);
		}
	} // end performBonemeal()
	
} // end class