package com.gsr.gsr_yatm.block.plant.parasite;

import java.util.function.Supplier;

import com.gsr.gsr_yatm.block.plant.CustomSeedCropBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.VoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class ShulkWartBlock extends CustomSeedCropBlock
{
	private final VoxelShapeProvider m_shape;
	

	
	public ShulkWartBlock(Properties properties, VoxelShapeProvider shape, Supplier<ItemLike> seed)
	{
		super(properties, seed);
		this.m_shape = shape;
	} // end constructor



	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos position)
	{
		return YATMBlockTags.SHULKWART_GROWS_ON.contains(state.getBlock());
	} // end mayPlaceOn()

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos position)
	{
		BlockPos check = position.above();
		return this.mayPlaceOn(level.getBlockState(check), level, check);
	} // end canSurvive()

	

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		int i = this.getAge(state);
		if (i < this.getMaxAge())
		{
			float f = (3.0f * ((float)state.getValue(this.getAgeProperty()))) + 1.0f;
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt((int) (25.0F / f) + 1) == 0))
			{
				level.setBlock(position, this.getStateForAge(i + 1), 2);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()

	
	
	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos position, BlockState state, boolean p_52261_)
	{
		return false;
	} // end isValidBonemealTarget()
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()
	
} // end class