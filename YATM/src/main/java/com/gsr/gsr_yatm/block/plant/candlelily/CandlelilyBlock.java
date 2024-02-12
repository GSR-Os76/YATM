package com.gsr.gsr_yatm.block.plant.candlelily;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CandlelilyBlock extends ShapeBlock implements IYATMPlantableBlock, BonemealableBlock
{
	public CandlelilyBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
	} // end constructor
	
	

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.CANDLELILY_CAN_GROW_ON_KEY);
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
				&& level.getBlockState(position.above()).is(YATMBlockTags.CANDLELILY_CAN_GROW_IN_KEY);
	} // end canPlantOn()

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos)
	{
		return 11;
	} // end getLightEmission()



	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return true;
	} // end isValidBonemealTarget()
	
	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return random.nextInt(YATMConfigs.CANDLELILY_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		int placed = 0;
		for(int i = 0; i < YATMConfigs.CANDLELILY_SPREAD_ATTEMPTS.get(); i++) 
		{
			BlockPos toCheck = position.offset(random.nextIntBetweenInclusive(-YATMConfigs.CANDLELILY_MAX_HORIZONTAL_SPREAD_ATTEMPTS.get(), YATMConfigs.CANDLELILY_MAX_HORIZONTAL_SPREAD_ATTEMPTS.get()), random.nextIntBetweenInclusive(-YATMConfigs.CANDLELILY_MAX_VERTICAL_SPREAD_EXTENT.get(), YATMConfigs.CANDLELILY_MAX_VERTICAL_SPREAD_EXTENT.get()), random.nextIntBetweenInclusive(-YATMConfigs.CANDLELILY_MAX_HORIZONTAL_SPREAD_ATTEMPTS.get(), YATMConfigs.CANDLELILY_MAX_HORIZONTAL_SPREAD_ATTEMPTS.get()));
			BlockPos belowToCheck = toCheck.below();
			if(level.isLoaded(toCheck) && this.canPlantOn(level, level.getBlockState(belowToCheck), belowToCheck, Direction.UP))
			{
				level.setBlock(toCheck, this.defaultBlockState(), Block.UPDATE_ALL);
				placed++;
			}
			if(placed >= YATMConfigs.CANDLELILY_MAX_PLACEMENTS.get()) 
			{
				break;
			}
		}
	} // end performBonemeal()
	
} // end class