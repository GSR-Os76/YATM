package com.gsr.gsr_yatm.block.plant.tree.soul_afflicted_rubber_bush;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.RandomUtil;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SoulAfflictedRubberTreeMeristemBlock extends SaplingBlock
{
	public static final BooleanProperty RECENTLY_AFFLICTED = YATMBlockStateProperties.RECENTLY_AFFLICTED;
	
	
	
	public SoulAfflictedRubberTreeMeristemBlock(@NotNull Properties properties)
	{
		super(new SoulAfflictedRubberTreeGrower(), Objects.requireNonNull(properties));
		this.registerDefaultState(this.defaultBlockState().setValue(SoulAfflictedRubberTreeMeristemBlock.RECENTLY_AFFLICTED, false));
	} // end constructor

	
	
	@Override
	public void animateTick(BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		if(state.getValue(SoulAfflictedRubberTreeMeristemBlock.RECENTLY_AFFLICTED)){
			for(int i = 0; i < random.nextInt(YATMConfigs.SOUL_AFFLICTED_RUBBER_TREE_ON_CONVERT_MIN_PARTICLES.get(), YATMConfigs.SOUL_AFFLICTED_RUBBER_TREE_ON_CONVERT_MAX_PARTICLES.get()); i++) 
			{
				level.addParticle(ParticleTypes.SOUL, 
					(double)position.getX() + 0.5D, (double)position.getY() + 0.5D, (double)position.getZ() + 0.5D, 
					(double)((random.nextFloat() / 12F) * RandomUtil.nextSign(random)), (double)((random.nextFloat() / 12F) * RandomUtil.nextSign(random)), (double)((random.nextFloat() / 12F)* RandomUtil.nextSign(random)));	
			}	
			level.playLocalSound(position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 12f * random.nextFloat(), 1f, true);		
			// might be recurring if this isn't synchronized to server. but the particle and sound code's working nowhere else.
			level.setBlock(position, state.setValue(SoulAfflictedRubberTreeMeristemBlock.RECENTLY_AFFLICTED, false), Block.UPDATE_CLIENTS);
		}
		super.animateTick(state, level, position, random);
	} // end animateTick()

	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{

		super.createBlockStateDefinition(builder.add(SoulAfflictedRubberTreeMeristemBlock.RECENTLY_AFFLICTED));
	} // end CreateBlockStateDefinition()

	@Override
	public boolean isBonemealSuccess(Level level, @NotNull RandomSource random, BlockPos position, BlockState state)
	{
		return random.nextInt(YATMConfigs.SOUL_AFFLICTED_RUBBER_TREE_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()
	
	@Override
	protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos position)
	{
		return state.is(YATMBlockTags.RUBBER_MERISTEM_CAN_GROW_ON_KEY);
	} // end mayPlaceOn()
	
} // end class