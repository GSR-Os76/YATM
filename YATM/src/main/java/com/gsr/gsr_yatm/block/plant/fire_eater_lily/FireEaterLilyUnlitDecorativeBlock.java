package com.gsr.gsr_yatm.block.plant.fire_eater_lily;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FireEaterLilyUnlitDecorativeBlock extends FireEaterLilyDecorativeBlock
{

	public FireEaterLilyUnlitDecorativeBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
	} // end constructor


	
	@Override
	protected boolean tryReconsiderLit(@NotNull Level level, @NotNull BlockPos position) 
	{
		if(FireEaterLilyBlock.shouldBeLit(level, position)) 
		{
			level.setBlock(position, YATMBlocks.FIRE_EATER_LILY_DECORATIVE.get().defaultBlockState(), Block.UPDATE_ALL);
			return true;
		}
		return false;
	} // end tryReconsiderLit()


	
	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return FireEaterLilyBlock.shouldBeLit(level, position);
	} // end isValidBonemealTarget()



	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return random.nextInt(YATMConfigs.FIRE_EATER_LILY_LIT_DECORATIVE_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()

} // end class