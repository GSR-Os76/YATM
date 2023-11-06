package com.gsr.gsr_yatm.block.plant.cuprum;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.block.plant.infernalum.InfernalumBlock;
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
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

public class CuprumBlock extends ShapeBlock implements IAgingBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	public static final EnumProperty<DoubleBlockHalf> HALF = YATMBlockStateProperties.DOUBLE_BLOCK_HALF;
	
	public static final float DAMAGE_FACTOR = 8.0f;
	public static final double DAMAGE_TRIGGER_TOLERANCE = 0.1d;
	public static final int GROWTH_RARITY = 36;
	
	
	
	public CuprumBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0).setValue(CuprumBlock.HALF, DoubleBlockHalf.LOWER));
	} // end constructor



	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return CuprumBlock.AGE;
	} // end getAgeProperty()
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos position, Entity entity)
	{
		if (entity instanceof LivingEntity)
		{
			if (!level.isClientSide)
			{
				Vec3 vector = new Vec3(Math.abs(entity.getX() - entity.xOld), Math.abs(entity.getY() - entity.yOld), Math.abs(entity.getZ() - entity.zOld));
				double vecLength = vector.length();
				int age = this.getAge(state);
				
				float damage = (((float) vecLength) * (InfernalumBlock.DAMAGE_FACTOR * (age + 1)));
				if (vecLength > InfernalumBlock.DAMAGE_TRIGGER_TOLERANCE)
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
		super.createBlockStateDefinition(builder.add(this.getAgeProperty(), CuprumBlock.HALF));
	} // end createBlockStateDefinition()
	


	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{	
		BlockState below = level.getBlockState(position.below());
		return state.getValue(CuprumBlock.HALF) == DoubleBlockHalf.LOWER 
				? below.is(YATMBlockTags.CUPRUM_CAN_GROW_ON_KEY)
				: (			below.is(this) 
						&& (below.getValue(CuprumBlock.HALF) == DoubleBlockHalf.LOWER) 
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
				&& level.getBlockState(position.above()).is(YATMBlockTags.CUPRUM_CAN_GROW_IN_KEY);
	} // end canPlantOn()
	
	

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return state.getValue(CuprumBlock.HALF) == DoubleBlockHalf.LOWER && this.getAge(state) < this.getMaxAge();
	} // end isRandomlyTicking()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age < maxAge)
		{
			Boolean shouldBeDouble = (age + 1) == maxAge;
			BlockPos abovePos = position.above();
			BlockState above = level.getBlockState(abovePos);
			if ((!shouldBeDouble || (above.is(YATMBlockTags.CUPRUM_CAN_GROW_IN_KEY) || above.is(this)))
					&& ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(CuprumBlock.GROWTH_RARITY) == 0)) 
			{
				BlockState nextAge = this.getStateForAge(state, age + 1);
				level.setBlock(position, nextAge, Block.UPDATE_CLIENTS);
				if(shouldBeDouble) 
				{
					level.setBlock(abovePos, nextAge.setValue(CuprumBlock.HALF, DoubleBlockHalf.UPPER), above.is(this) ? Block.UPDATE_CLIENTS : Block.UPDATE_ALL);
				}
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()
	
} // end class