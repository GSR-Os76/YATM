package com.gsr.gsr_yatm.block.plant.tree.soul_afflicted_rubber_bush;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.block.plant.tree.SelfLayeringSaplingBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SoulAfflictedRubberBushSaplingBlock extends SelfLayeringSaplingBlock
{
	public static final BooleanProperty RECENTLY_AFFLICTED = BooleanProperty.create("recently_afflicted");
	
	
	
	public SoulAfflictedRubberBushSaplingBlock(Properties properties)
	{
		super(ImmutableList.of(new ApicalSoulAfflictedRubberBushGrower()), ImmutableList.of(new FirstLateralSoulAfflictedRubberBushGrower(), new SecondLateralSoulAfflictedRubberBushGrower()), YATMBlockTags.RUBBER_MERISTEM_CAN_GROW_ON, properties);
		this.registerDefaultState(this.defaultBlockState().setValue(RECENTLY_AFFLICTED, false));
	} // end constructor

	
	
	@Override
	public boolean isBonemealSuccess(Level level, RandomSource tandomSource, BlockPos blockPos, BlockState blockState)
	{
		return true;
	} // end isBonemealSuccess()

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(RECENTLY_AFFLICTED));
	} // end CreateBlockStateDefinition()

	@Override
	public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource)
	{
		// TODO, this replays once every load, this isn't intentional or expect
		if(blockState.getValue(RECENTLY_AFFLICTED)) 
		{
			for(int i = 0; i < randomSource.nextInt(12, 26); i++) 
			{
				level.addParticle(ParticleTypes.SOUL, 
					(double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, 
					(double)((randomSource.nextFloat() / 12F) * (randomSource.nextIntBetweenInclusive(0, 1) == 0 ? -1 : 1)), (double)(randomSource.nextFloat() / 12F)* (randomSource.nextIntBetweenInclusive(0, 1) == 0 ? -1 : 1), (double)(randomSource.nextFloat() / 12F)* (randomSource.nextIntBetweenInclusive(0, 1) == 0 ? -1 : 1));
				
			}	
			level.playLocalSound((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS
					// TODO, figure out better what these floats represent, first's seemingly volume, second's a mystery to me
					, 12f * randomSource.nextFloat(), 1f
					, false);		
			//level.playLocalSound(blockPos, null, null, friction, explosionResistance, dynamicShape)
			level.setBlock(blockPos, blockState.setValue(RECENTLY_AFFLICTED, false), UPDATE_ALL);
		}
					
	} // end animateTick
	
} // end class