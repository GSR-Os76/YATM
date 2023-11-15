package com.gsr.gsr_yatm.block.plant.aurum;

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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

public class AurumBlock extends ShapeBlock implements IAgingBlock, IYATMPlantableBlock
{
	public static final int DOUBLES_PAST_THRESHOLD = 2;
	public static final EnumProperty<DoubleBlockHalf> HALF = YATMBlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_FIVE;
	
	
	
	public AurumBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0).setValue(AurumBlock.HALF, DoubleBlockHalf.LOWER));
	} // end constructor

	
	
	@Override
	public IntegerProperty getAgeProperty()
	{
		return AurumBlock.AGE;
	} // end getAgeProperty()
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos position, Entity entity)
	{
		if (entity instanceof LivingEntity)
		{
			if (!level.isClientSide)
			{
				double speed = new Vec3(Math.abs(entity.getX() - entity.xOld), Math.abs(entity.getY() - entity.yOld), Math.abs(entity.getZ() - entity.zOld)).length();

				double damageFactor = YATMConfigs.AURUM_DAMAGE_FACTOR.get();
				float damage = (((float) speed) * (((float)damageFactor) * (this.getAge(state) + 1)));
				if (speed > YATMConfigs.AURUM_DAMAGE_TRIGGER_TOLERANCE.get())
				{
					// TODO, create custom damage source for this too
					entity.hurt(level.damageSources().thorns((Entity) null), damage);
				}
			}
		}
	} // end entityInside()

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(AurumBlock.AGE, AurumBlock.HALF);
	} // end createBlockStateDefinition()
	
	
	
	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{	
		BlockState below = level.getBlockState(position.below());
		return state.getValue(AurumBlock.HALF) == DoubleBlockHalf.LOWER 
				? below.is(YATMBlockTags.AURUM_CAN_GROW_ON_KEY)
				: (			below.is(this) 
						&& (below.getValue(AurumBlock.HALF) == DoubleBlockHalf.LOWER) 
						&& (this.getAge(below) == this.getAge(state)));
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
				&& level.getBlockState(position.above()).is(YATMBlockTags.AURUM_CAN_GROW_IN_KEY);
	} // end canPlantOn()



	@Override
	public boolean isRandomlyTicking(BlockState state)
	{
		return super.isRandomlyTicking(state) && state.getValue(AurumBlock.HALF) == DoubleBlockHalf.LOWER;
	} // end isRandomlyTicking()

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		int goingToAge = this.getAge(state) + 1;				
		if (goingToAge <= this.getMaxAge() && !this.isGrowthBlocked(level, state, position))
		{
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.AURUM_GROWTH_RARITY.get()) == 0))
			{
				level.setBlock(position, this.getStateForAge(state, goingToAge), 2);
				if(goingToAge > AurumBlock.DOUBLES_PAST_THRESHOLD) 
				{
					BlockState above = level.getBlockState(position.above());
					if(above.is(this)) 
					{
						level.setBlock(position.above(), above.setValue(this.getAgeProperty(), goingToAge).setValue(AurumBlock.HALF, DoubleBlockHalf.UPPER), 2);
					}
					else
					{
						level.setBlock(position.above(), this.getStateForAge(state, goingToAge).setValue(AurumBlock.HALF, DoubleBlockHalf.UPPER), 3);
					}
				}
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()



	// should only be called with lower state
	protected void setToYoungest(Level level, BlockState state, BlockPos position) 
	{
		if(level.isClientSide) 
		{
			return;
		}
		
		BlockPos above = position.above();
		if(level.getBlockState(above).is(this)) 
		{
			level.setBlock(above, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
		}
		level.setBlock(position, state.setValue(AurumBlock.HALF, DoubleBlockHalf.LOWER).setValue(this.getAgeProperty(), 0), Block.UPDATE_CLIENTS);
	} // end setToYoungest
	
	protected boolean isTopSupport(BlockState state) 
	{
		return state.is(this) && state.getValue(AurumBlock.HALF) == DoubleBlockHalf.LOWER && this.isPastDoubleBlockThreshold(state);
	} // end isTopSupport()
	
	protected boolean isPastDoubleBlockThreshold(BlockState state) 
	{
		return state.getValue(this.getAgeProperty()) > DOUBLES_PAST_THRESHOLD;
	} // end isPastDoubleBlockThreshold()
	
	protected boolean isGrowthBlocked(Level level, BlockState state, BlockPos position) 
	{
		int nextAgeUp = Math.min(state.getValue(this.getAgeProperty()) + 1, this.getMaxAge());
		BlockState abvState = level.getBlockState(position.above());
		return this.isPastDoubleBlockThreshold(state.setValue(this.getAgeProperty(), nextAgeUp)) && !(abvState.canBeReplaced() || abvState.is(this)); 
	} // end isGrowthBlocked()
	
} // end class