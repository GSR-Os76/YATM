package com.gsr.gsr_yatm.block.plant.fire_eater_lily;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FireEaterLilyDecorativeBlock extends ShapeBlock implements BonemealableBlock
{

	public FireEaterLilyDecorativeBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
	} // end constructor
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		this.tryReconsiderLit(level, position);
		super.randomTick(state, level, position, random);
	} // end randomTick()



	protected boolean tryReconsiderLit(@NotNull Level level, @NotNull BlockPos position) 
	{
		if(!FireEaterLilyBlock.shouldBeLit(level, position)) 
		{
			level.setBlock(position, YATMBlocks.FIRE_EATER_LILY_UNLIT_DECORATIVE.get().defaultBlockState(), Block.UPDATE_ALL);
			return true;
		}
		return false;
	} // end tryReconsiderLit()



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
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state, boolean p_50900_)
	{
		return true;
	} // end isValidBonemealTarget()

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return random.nextInt(YATMConfigs.FIRE_EATER_LILY_LIT_DECORATIVE_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		if (this.tryReconsiderLit(level, position))
		{
			return;
		}


		int placed = 0;
		for (int i = 0; i < YATMConfigs.FIRE_EATER_LILY_MAX_SPREAD_ATTEMPTS.get(); i++)
		{
			BlockPos toCheck = position.offset(random.nextIntBetweenInclusive(-YATMConfigs.FIRE_EATER_LILY_MAX_HORIZONTAL_SPREAD.get(), YATMConfigs.FIRE_EATER_LILY_MAX_HORIZONTAL_SPREAD.get()), 
					random.nextIntBetweenInclusive(-YATMConfigs.FIRE_EATER_LILY_MAX_VERTICAL_SPREAD.get(), YATMConfigs.FIRE_EATER_LILY_MAX_VERTICAL_SPREAD.get()), 
					random.nextIntBetweenInclusive(-YATMConfigs.FIRE_EATER_LILY_MAX_HORIZONTAL_SPREAD.get(), YATMConfigs.FIRE_EATER_LILY_MAX_HORIZONTAL_SPREAD.get()));
			if (level.isLoaded(toCheck) && level.getBlockState(toCheck).is(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_IN_KEY) && level.getBlockState(toCheck.below()).is(YATMBlockTags.FIRE_EATER_LILY_CAN_GROW_ON_KEY))
			{
				level.setBlock(toCheck, YATMBlocks.FIRE_EATER_LILY.get().getStateForPlacement(level, toCheck), Block.UPDATE_ALL);
				placed++;
			}
			if (placed >= YATMConfigs.FIRE_EATER_LILY_MAX_SPREADS.get())
			{
				break;
			}
		}

	} // end performBonemeal()

} // end class