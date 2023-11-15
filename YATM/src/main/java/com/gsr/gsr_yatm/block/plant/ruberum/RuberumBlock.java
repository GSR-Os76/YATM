package com.gsr.gsr_yatm.block.plant.ruberum;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.joml.Math;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

public class RuberumBlock extends ShapeBlock implements IAgingBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;
	
	
	
	public RuberumBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0).setValue(RuberumBlock.LIT, false));
	} // end constructor

	

	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return RuberumBlock.AGE;
	} // end getAgeProperty()
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(this.getAgeProperty(), RuberumBlock.LIT));
	} // end createBlockStateDefinition()
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void attack(BlockState state, Level level, BlockPos position, Player player)
	{
		if(!state.getValue(RuberumBlock.LIT)) 
		{
			level.setBlock(position, state.setValue(RuberumBlock.LIT, true), Block.UPDATE_ALL);
		}
		super.attack(state, level, position, player);;
	} // end attach()

	@Override
	public void entityInside(BlockState state, Level level, BlockPos position, Entity entity)
	{
		if (!level.isClientSide)
		{
			double speed = new Vec3(Math.abs(entity.getX() - entity.xOld), Math.abs(entity.getY() - entity.yOld), Math.abs(entity.getZ() - entity.zOld)).length();

			if (entity instanceof LivingEntity)
			{
				double damageFactor = YATMConfigs.RUBERUM_DAMAGE_FACTOR.get();
				float damage = (((float) speed) * (((float)damageFactor) * (this.getAge(state) + 1)));
				if (speed > YATMConfigs.RUBERUM_DAMAGE_TRIGGER_TOLERANCE.get())
				{
					// TODO, create custom damage source for this too
					entity.hurt(level.damageSources().thorns((Entity) null), damage);
				}
			}
			
			if(!state.getValue(RuberumBlock.LIT) && (speed > YATMConfigs.RUBERUM_SIGNAL_TRIGGER_TOLERANCE.get())) 
			{
				level.setBlock(position, state.setValue(RuberumBlock.LIT, true), Block.UPDATE_ALL);
			}			
		}		
	} // end entityInside()
	
	@Override
	public int getSignal(BlockState state, BlockGetter level, BlockPos position, Direction d)
	{
		return state.getValue(RuberumBlock.LIT) ? (state.getValue(RuberumBlock.AGE) + 1) * YATMConfigs.RUBERUM_SIGNAL_FACTOR.get() : 0;
	} // end randomTick()
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos position, RandomSource random)
	{
		if(state.getValue(RuberumBlock.LIT) && random.nextInt(YATMConfigs.RUBERUM_PARTICLE_RARITY.get()) == 0) 
		{
			for(int i = 0; i < random.nextIntBetweenInclusive(YATMConfigs.RUBERUM_MIN_PARTICLES.get(), YATMConfigs.RUBERUM_MAX_PARTICLES.get()); i++) 
			{
				int age = this.getAge(state);
				int hAgeFactor = age + 2;
				level.addParticle(DustParticleOptions.REDSTONE, 
						((double)position.getX()) + .5d + ((random.nextBoolean() ? -1d : 1d) * ((((double)random.nextInt(hAgeFactor)) / 2d) / 16d)), 
						((double)position.getY()) + ((double)random.nextInt(((age / 2) + 3)) / 16d), 
						((double)position.getZ()) + .5d + ((random.nextBoolean() ? -1d : 1d) * ((((double)random.nextInt(hAgeFactor)) / 2d) / 16d)), 
						0.0D, 0.0D, 0.0D);
			}
		}
	} // end animateTick()
	


	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.RUBERUM_CAN_GROW_ON_KEY);
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
				&& level.getBlockState(position.above()).is(YATMBlockTags.RUBERUM_CAN_GROW_IN_KEY);
	} // end canPlantOn()
	
	

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return state.getValue(RuberumBlock.LIT) || (this.getAge(state) < this.getMaxAge());
	} // end isRandomlyTicking()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		if(state.getValue(RuberumBlock.LIT)) 
		{
			state = state.setValue(RuberumBlock.LIT, false);
			level.setBlock(position, state, Block.UPDATE_ALL);
		}
		
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age < maxAge)
		{
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.RUBERUM_GROWTH_RARITY.get()) == 0)) 
			{
				level.setBlock(position, this.getStateForAge(state, age + 1), Block.UPDATE_CLIENTS);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()
	
} // end class