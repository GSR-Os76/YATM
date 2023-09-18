package com.gsr.gsr_yatm.block.plant.fire_eater_lily;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.IAging;
import com.gsr.gsr_yatm.block.IYATMPlantable;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

// TODO, maybe make FlowerBlock
public class FireEaterLilyBlock extends Block implements IAging, BonemealableBlock, IYATMPlantable
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;
	
	private static final int LIT_TEMPERATURE_LEVEL_CUTOFF = 2;
	
	
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	
	
	
	public FireEaterLilyBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties));
		this.m_shape = Objects.requireNonNull(shape);
		
		this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0).setValue(FireEaterLilyBlock.LIT, false));
	} // end constructor



	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return FireEaterLilyBlock.AGE;
	} // end getAgeProperty()

	@Override
	public boolean canPlantOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Direction face)
	{
		return state.is(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_ON_KEY);
	} // end canPlantOn()


	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FireEaterLilyBlock.AGE, FireEaterLilyBlock.LIT));
	} // end createBlockStateDefinition()
	
	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos();
		return this.getStateForPlacement(level, position);
	} // end getStateForPlacement()
	
	protected @Nullable BlockState getStateForPlacement(@NotNull LevelReader level, @NotNull BlockPos position) 
	{

		if(!level.getBlockState(position.below()).is(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_ON_KEY)) 
		{
			return null;
		}
		
		return this.defaultBlockState().setValue(FireEaterLilyBlock.LIT, this.shouldBeLit(level, position));
	} // end getStateForPlacement()
	
	
	
	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		YetAnotherTechMod.LOGGER.info("canSurvive: " + level.getBlockState(position.below()).is(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_ON_KEY));
		return level.getBlockState(position.below()).is(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_ON_KEY);
	} // end canSurvive()
	



	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos position, Block from, BlockPos neighborPos, boolean p_60514_)
	{
		if(!this.canSurvive(state, level, position)) 
		{
			level.setBlock(position, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
			Block.dropResources(state, level, position);
			// TODO, particles
			return;
		}
		super.neighborChanged(state, level, position, from, neighborPos, p_60514_);
		
	} // end neighborChanged()
	
	
	



	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		if(!this.canSurvive(state, level, position)) 
		{
			level.setBlock(position, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
			Block.dropResources(state, level, position);
			// TODO, particles
			return;
		}
		
		int heat = this.getHeatLevel(level, position);
		boolean isHeat = state.getValue(FireEaterLilyBlock.LIT);
		boolean shouldHeat = this.shouldBeLit(level, position);
		BlockState toState = state.setValue(FireEaterLilyBlock.LIT, shouldHeat);
		
		
		
		int i = this.getAge(state);
		if ((isHeat && shouldHeat)
				&& (i < this.getMaxAge()) 
				&& ForgeHooks.onCropsGrowPre(level, position, state, heat == 0 ? false : random.nextInt(Math.min(1, Math.abs((int) (52.0F / ((float)heat))))) == 0))
		{
			level.setBlock(position, this.getStateForAge(toState, i + 1), Block.UPDATE_CLIENTS);
			ForgeHooks.onCropsGrowPost(level, position, state);
		}
		else if (shouldHeat != isHeat) 
		{
			level.setBlock(position, toState, Block.UPDATE_CLIENTS);
		}		
	} // end randomTick()

	protected int getHeatLevel(@NotNull LevelReader level, @NotNull BlockPos position) 
	{
		int heatLevel = 0;
		
		if(level.dimensionType().ultraWarm()) 
		{
			heatLevel += 4;
		}
//		else
//		{
//			//check if biome temp unit
//			heatLevel += Math.round(level.getBiome(position).get().getBaseTemperature() * 2f);
//		}

		if(level.getBlockState(position.below()).is(YATMBlockTags.HEAT_BLOCKS_KEY)) 
		{
			heatLevel += 2;
		}
		
		// TODO, probably, check surrounding blocks against tag, be sure not to load chunks in
		
		return heatLevel;
	} // end getHeatLevel()
	
	/** That minimum heat level for which all higher values and itself produce a lit state.*/
	protected int getLitHeatLevelCutoff() 
	{
		return FireEaterLilyBlock.LIT_TEMPERATURE_LEVEL_CUTOFF;
	} // end getLitHeatLevelCutoff()

	protected boolean shouldBeLit(@NotNull LevelReader level, @NotNull BlockPos position)
	{
		return this.getHeatLevel(level, position) >= this.getLitHeatLevelCutoff();
	} // end shouldBeLit()
	
	
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext collisionContext)
	{
		return this.m_shape.getShape(state, blockGetter, position, collisionContext);
	} // end getShape()



	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state, boolean p_50900_)
	{
		return this.shouldBeLit(level, position);
	} // end isValidBonemealTarget()

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return true;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		if(!state.getValue(FireEaterLilyBlock.LIT)) 
		{
			level.setBlock(position, state.setValue(FireEaterLilyBlock.LIT, this.shouldBeLit(level, position)), Block.UPDATE_CLIENTS);
			return;
		}
		
		if(this.getAge(state) != this.getMaxAge()) 
		{
			level.setBlock(position, this.getStateForAge(state, Math.min(this.getMaxAge(), this.getAge(state) + random.nextIntBetweenInclusive(1, 3))), Block.UPDATE_CLIENTS);
		}
		else
		{
			int placed = 0;
			for(int i = 0; i < 12; i++) 
			{
				BlockPos toCheck = position.offset(random.nextIntBetweenInclusive(-2, 2), random.nextIntBetweenInclusive(-1, 1), random.nextIntBetweenInclusive(-2, 2));
				if(level.getBlockState(toCheck).is(Blocks.AIR) && level.getBlockState(toCheck.below()).is(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_ON_KEY)) 
				{
					level.setBlock(toCheck, this.getStateForPlacement(level, toCheck), Block.UPDATE_ALL);
					placed++;
				}
				if(placed >= 5) 
				{
					break;
				}
			}
			
		}
	} // end performBonemeal()

} // end class