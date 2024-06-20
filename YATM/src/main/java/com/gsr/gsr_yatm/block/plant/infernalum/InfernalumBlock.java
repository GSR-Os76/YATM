package com.gsr.gsr_yatm.block.plant.infernalum;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

public class InfernalumBlock extends ShapeBlock implements IAgingBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	public static final IntegerProperty MAJOR_AGE = YATMBlockStateProperties.AGE_EIGHT_MAJOR;
	public static final BooleanProperty HAS_FRUIT = YATMBlockStateProperties.HAS_FRUIT;
	
	public static final int MAX_MAJOR_AGE = 7;
	
	
	public InfernalumBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0).setValue(InfernalumBlock.MAJOR_AGE, 0));
	} // end constructor



	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return InfernalumBlock.AGE;
	} // end getAgeProperty()

	@Override
	public void entityInside(BlockState state, Level level, BlockPos position, Entity entity)
	{
		if (entity instanceof LivingEntity)
		{
			if (!level.isClientSide)
			{
				double speed = new Vec3(Math.abs(entity.getX() - entity.xOld), Math.abs(entity.getY() - entity.yOld), Math.abs(entity.getZ() - entity.zOld)).length();

				double damageFactor = YATMConfigs.INFERNALUM_DAMAGE_FACTOR.get();
				float damage = (((float) speed) * (((float)damageFactor) * (this.getAge(state) + 1)));
				if (speed > YATMConfigs.INFERNALUM_DAMAGE_TRIGGER_TOLERANCE.get())
				{
					// TODO, create custom damage source for this too
					entity.hurt(level.damageSources().thorns((Entity) null), damage);
				}
			}
		}
	} // end entityInside()
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(this.getAgeProperty(), InfernalumBlock.MAJOR_AGE));
	} // end createBlockStateDefinition()
	


	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		BlockState below = level.getBlockState(position.below());
		return below.is(YATMBlockTags.INFERNALUM_CAN_GROW_ON_KEY) 
				|| (below.is(this) && this.getAge(below) == this.getMaxAge());
	} // end canSurvive()

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
				&& level.getBlockState(above).is(YATMBlockTags.INFERNALUM_CAN_GROW_IN_KEY);
	} // end canPlantOn()
	


	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return this.getAge(state) < this.getMaxAge() 
				&& state.getValue(InfernalumBlock.MAJOR_AGE) < InfernalumBlock.MAX_MAJOR_AGE;
	} // end isRandomlyTicking()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age < maxAge && state.getValue(InfernalumBlock.MAJOR_AGE) < InfernalumBlock.MAX_MAJOR_AGE)
		{
			int dAge = age + 1;
			BlockState dState = this.getStateForAge(state, dAge);
			// don't grow up if blocked.
			if(dAge == maxAge && !level.getBlockState(position.above()).is(YATMBlockTags.INFERNALUM_CAN_GROW_IN_KEY))
			{
				return;
			} 
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.INFERNALUM_GROWTH_RARITY.get()) == 0)) 
			{
				level.setBlock(position, dState, Block.UPDATE_CLIENTS);
				ForgeHooks.onCropsGrowPost(level, position, state);
				if(dAge == maxAge) 
				{
					level.setBlock(position.above(), this.getStateForAge(dState.setValue(InfernalumBlock.MAJOR_AGE, Math.min(InfernalumBlock.MAX_MAJOR_AGE, dState.getValue(InfernalumBlock.MAJOR_AGE) + (1 + random.nextIntBetweenInclusive(YATMConfigs.INFERNALUM_MIN_MAJOR_AGE_INCREASE.get(), YATMConfigs.INFERNALUM_MAX_MAJOR_AGE_INCREASE.get())))), 0), Block.UPDATE_ALL);
				}
			}
		}
	} // end randomTick()
	
} // end class