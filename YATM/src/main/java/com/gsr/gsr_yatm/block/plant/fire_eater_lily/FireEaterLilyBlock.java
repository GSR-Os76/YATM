package com.gsr.gsr_yatm.block.plant.fire_eater_lily;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.ForgeHooks;

// TODO, extract more constants, implement heat equations
public class FireEaterLilyBlock extends ShapeBlock implements IAgingBlock, BonemealableBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;
	
	private static final int LIT_TEMPERATURE_LEVEL_CUTOFF = 2;
	
	
	
	public FireEaterLilyBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0).setValue(FireEaterLilyBlock.LIT, false));
	} // end constructor



	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return FireEaterLilyBlock.AGE;
	} // end getAgeProperty()


	
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
	
	public @Nullable BlockState getStateForPlacement(@NotNull LevelReader level, @NotNull BlockPos position) 
	{
		return this.defaultBlockState().setValue(FireEaterLilyBlock.LIT, FireEaterLilyBlock.shouldBeLit(level, position));
	} // end getStateForPlacement()
	
	
	
	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_ON_KEY);
	} // end canSurvive()

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Block formerNeighbor, @NotNull BlockPos neighborPos, boolean p_60514_)
	{
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
		return face == Direction.UP 
				&& this.canSurvive(level.getBlockState(above), level, above) 
				&& level.getBlockState(position.above()).is(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_IN_KEY);
	} // end canPlantOn()
	
	

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		// theoretically is obselete
//		if(!this.canSurvive(state, level, position)) 
//		{
//			level.setBlock(position, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
//			Block.dropResources(state, level, position);
//			// TODO, particles
//			return;
//		}
		
		int heat = FireEaterLilyBlock.getHeatLevel(level, position);
		boolean isHeat = state.getValue(FireEaterLilyBlock.LIT);
		boolean shouldHeat = FireEaterLilyBlock.shouldBeLit(level, position);
		BlockState toState = state.setValue(FireEaterLilyBlock.LIT, shouldHeat);
		
		int age = this.getAge(state);
		if(age == this.getMaxAge()) 
		{
			level.setBlock(position, state.getValue(FireEaterLilyBlock.LIT) ? YATMBlocks.FIRE_EATER_LILY_DECORATIVE.get().defaultBlockState() : YATMBlocks.FIRE_EATER_LILY_UNLIT_DECORATIVE.get().defaultBlockState(), Block.UPDATE_ALL);
		}
		
		if ((isHeat && shouldHeat)
				&& (age < this.getMaxAge()) 
				&& ForgeHooks.onCropsGrowPre(level, position, state, heat == 0 ? false : random.nextInt(Math.min(1, Math.abs((int) (52.0F / ((float)heat))))) == 0))
		{
			level.setBlock(position, this.getStateForAge(toState, age + 1), Block.UPDATE_CLIENTS);
			ForgeHooks.onCropsGrowPost(level, position, state);
		}
		else if (shouldHeat != isHeat) 
		{
			level.setBlock(position, toState, Block.UPDATE_CLIENTS);
		}		
	} // end randomTick()

	
	
	public static int getHeatLevel(@NotNull LevelReader level, @NotNull BlockPos position) 
	{
		int heatLevel = 0;
		
		if(level.dimensionType().ultraWarm()) 
		{
			heatLevel += 4;
		}

		if(level.getBlockState(position.below()).is(YATMBlockTags.HEAT_BLOCKS_KEY)) 
		{
			heatLevel += 2;
		}
		
		// TODO, probably, check surrounding blocks against tag, be sure not to load chunks in
		// TODO, possibly, check biome heat, but probably not this one.
		
		return heatLevel;
	} // end getHeatLevel()
	
	/** That minimum heat level for which all higher values and itself produce a lit state.*/
	public static int getLitHeatLevelCutoff() 
	{
		return FireEaterLilyBlock.LIT_TEMPERATURE_LEVEL_CUTOFF;
	} // end getLitHeatLevelCutoff()

	public static boolean shouldBeLit(@NotNull LevelReader level, @NotNull BlockPos position)
	{
		return FireEaterLilyBlock.getHeatLevel(level, position) >= FireEaterLilyBlock.getLitHeatLevelCutoff();
	} // end shouldBeLit()



	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return (FireEaterLilyBlock.shouldBeLit(level, position) != state.getValue(FireEaterLilyBlock.LIT)) 
				|| (state.getValue(FireEaterLilyBlock.LIT) && (this.getAge(state) < this.getMaxAge()));
	} // end isValidBonemealTarget()

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return random.nextInt(YATMConfigs.FIRE_EATER_LILY_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		if(FireEaterLilyBlock.shouldBeLit(level, position) != state.getValue(FireEaterLilyBlock.LIT)) 
		{
			level.setBlock(position, state.setValue(FireEaterLilyBlock.LIT, FireEaterLilyBlock.shouldBeLit(level, position)), Block.UPDATE_CLIENTS);
			return;
		}
		
		level.setBlock(position, this.getStateForAge(state, Math.min(this.getMaxAge(), this.getAge(state) + random.nextIntBetweenInclusive(1, 3))), Block.UPDATE_CLIENTS);
	} // end performBonemeal()

} // end class