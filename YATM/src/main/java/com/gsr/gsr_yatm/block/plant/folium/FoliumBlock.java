package com.gsr.gsr_yatm.block.plant.folium;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

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
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

public class FoliumBlock extends ShapeBlock implements IAgingBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_FIVE;
	public static final EnumProperty<DoubleBlockHalf> HALF = YATMBlockStateProperties.DOUBLE_BLOCK_HALF;
	
	public static final int GROWTH_RARITY = 36;
	
	
	
	public FoliumBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(FoliumBlock.AGE, 0).setValue(FoliumBlock.HALF, DoubleBlockHalf.LOWER));
	} // end constructor



	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return FoliumBlock.AGE;
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
				
				float damage = (((float) vecLength) * (6.0f * (age + 1)));
				if (vecLength > 0.1d)
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
		super.createBlockStateDefinition(builder.add(FoliumBlock.AGE, FoliumBlock.HALF));
	} // end createBlockStateDefinition()
	


	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{	
		BlockState below = level.getBlockState(position.below());
		return state.getValue(FoliumBlock.HALF) == DoubleBlockHalf.LOWER 
				? below.is(YATMBlockTags.FOLIUM_CAN_GROW_ON_KEY)
				: (			below.is(this) 
						&& (below.getValue(FoliumBlock.HALF) == DoubleBlockHalf.LOWER) 
						&& (below.getValue(FoliumBlock.AGE) == state.getValue(FoliumBlock.AGE)));
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
				&& level.getBlockState(position.above()).is(YATMBlockTags.FOLIUM_CAN_GROW_IN_KEY);
	} // end canPlantOn()
	
	

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return state.getValue(FoliumBlock.HALF) == DoubleBlockHalf.LOWER && this.getAge(state) < this.getMaxAge();
	} // end isRandomlyTicking()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age < maxAge)
		{
			Boolean shouldBeDouble = (age + 1) > 2;
			BlockPos abovePos = position.above();
			BlockState above = level.getBlockState(abovePos);
			if ((!shouldBeDouble || (above.is(YATMBlockTags.FOLIUM_CAN_GROW_IN_KEY) || above.is(this)))
					&& ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(FoliumBlock.GROWTH_RARITY) == 0)) 
			{
				BlockState nextAge = this.getStateForAge(state, age + 1);
				level.setBlock(position, nextAge, Block.UPDATE_CLIENTS);
				if(shouldBeDouble) 
				{
					level.setBlock(abovePos, nextAge.setValue(FoliumBlock.HALF, DoubleBlockHalf.UPPER), above.is(this) ? Block.UPDATE_CLIENTS : Block.UPDATE_ALL);
				}
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()

} // end class