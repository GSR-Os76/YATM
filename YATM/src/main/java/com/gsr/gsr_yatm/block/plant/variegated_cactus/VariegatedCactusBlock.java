package com.gsr.gsr_yatm.block.plant.variegated_cactus;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.plant.IYATMPlantable;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

public class VariegatedCactusBlock extends CactusBlock implements IYATMPlantable
{

	public VariegatedCactusBlock(@NotNull Properties properties)
	{
		super(properties);
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