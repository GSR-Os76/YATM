package com.gsr.gsr_yatm.block.plant.variegated_cactus;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.plant.IYATMPlantable;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.util.NonNullSupplier;

public class VariegatedCactusBlock extends CactusBlock implements IYATMPlantable
{
	private final @NotNull NonNullSupplier<BlockState> m_revertsInto;
	// private final float m_reversionChance;
	
	
	
	public VariegatedCactusBlock(@NotNull Properties properties, @NotNull NonNullSupplier<BlockState> revertsInto)
	{
		super(Objects.requireNonNull(properties));
		this.m_revertsInto = Objects.requireNonNull(revertsInto);
	} // end constructor
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos position)
	{
		for (Direction direction : Direction.Plane.HORIZONTAL)
		{
			BlockPos neighborPos = position.relative(direction);
			BlockState neighbor = levelReader.getBlockState(neighborPos);
			if (neighbor.isSolid()// Block.isFaceFull(neighbor.getBlockSupportShape(levelReader, neighborPos),
									// direction.getOpposite())
					|| levelReader.getFluidState(position.relative(direction)).is(FluidTags.LAVA))
			{
				return false;
			}
		}

		return this.canPlantOn(levelReader.getBlockState(position.below()));
	} // end canSurvive()
		
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		BlockPos target = position.above();
		if (level.isEmptyBlock(target))
		{
			int cactusMaxHeight = 3;
			int cactusBelow;
			for (cactusBelow = 1; level.getBlockState(position.below(cactusBelow)).is(YATMBlockTags.FORGE_CACTUSES_KEY) && cactusBelow <= cactusMaxHeight; ++cactusBelow);

			if (cactusBelow < cactusMaxHeight)
			{
				int age = state.getValue(AGE);
				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, target, state, true))
				{
					if (age == 15)
					{
						BlockState blockToGrow = random.nextInt(128) == 0 ? this.defaultBlockState() : this.m_revertsInto.get();
						level.setBlockAndUpdate(target, blockToGrow);
						BlockState blockstate = state.setValue(AGE, 0);
						level.setBlock(position, blockstate, 4);
						level.neighborChanged(blockstate, target, this, position, false);
					}
					else
					{
						level.setBlock(position, state.setValue(AGE, age + 1), 4);
					}
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, position, state);
				}
			}
		}
	} // end randomTick()
	

	


	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable)
	{
		return super.canSustainPlant(state, world, pos, facing, plantable) || state.is(YATMBlockTags.FORGE_CACTUSES_KEY);
	} // end canSustainPlant



	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos)
	{
		return IYATMPlantable.PLANT_TYPE;
	} // end getPlantType()



	@Override
	public boolean canPlantOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Direction face)
	{
		return face == Direction.UP && this.canPlantOn(state);
	} // end canPlantOn()
	
	private boolean canPlantOn(@NotNull BlockState state) 
	{
		return state.is(YATMBlockTags.VERIEGATED_CACTUS_CAN_GROW_ON_KEY);
	} // end canPlantOn()
	
} // end class