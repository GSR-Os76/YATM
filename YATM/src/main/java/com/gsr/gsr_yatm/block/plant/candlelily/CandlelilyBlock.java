package com.gsr.gsr_yatm.block.plant.candlelily;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

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
	private static final int BONEMEAL_SUCCESS_RARITY = 3;
	private static final int SPREAD_ATTEMPTS = 12;
	private static final int HORIZONTAL_RADIUS = 2;
	private static final int VERICAL_RADIUS = 1;
	private static final int MAX_PLACEMENTS = 5;
	
	
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
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state, boolean p_50900_)
	{
		return true;
	} // end isValidBonemealTarget()
	
	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return random.nextInt(CandlelilyBlock.BONEMEAL_SUCCESS_RARITY) == 0;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		int placed = 0;
		for(int i = 0; i < CandlelilyBlock.SPREAD_ATTEMPTS; i++) 
		{
			BlockPos toCheck = position.offset(random.nextIntBetweenInclusive(-CandlelilyBlock.HORIZONTAL_RADIUS, CandlelilyBlock.HORIZONTAL_RADIUS), random.nextIntBetweenInclusive(-CandlelilyBlock.VERICAL_RADIUS, CandlelilyBlock.VERICAL_RADIUS), random.nextIntBetweenInclusive(-CandlelilyBlock.HORIZONTAL_RADIUS, CandlelilyBlock.HORIZONTAL_RADIUS));
			BlockPos belowToCheck = toCheck.below();
			if(this.canPlantOn(level, level.getBlockState(belowToCheck), belowToCheck, Direction.UP))
			{
				level.setBlock(toCheck, this.defaultBlockState(), Block.UPDATE_ALL);
				placed++;
			}
			if(placed >= CandlelilyBlock.MAX_PLACEMENTS) 
			{
				break;
			}
		}
	} // end performBonemeal()
	
} // end class